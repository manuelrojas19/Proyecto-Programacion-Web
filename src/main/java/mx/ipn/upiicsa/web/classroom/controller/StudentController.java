package mx.ipn.upiicsa.web.classroom.controller;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.dto.StudentDto;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.ExceptionResponse;
import mx.ipn.upiicsa.web.classroom.exception.StudentNotFoundException;
import mx.ipn.upiicsa.web.classroom.model.Student;
import mx.ipn.upiicsa.web.classroom.mappers.StudentMapper;
import mx.ipn.upiicsa.web.classroom.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ExceptionResponse> emailAlreadyTaken(EmailTakenException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ExceptionResponse> studentNotFound(StudentNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> studentsDto = studentService.getAllStudents()
                .stream().map(studentMapper::studentToStudentDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(studentsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        StudentDto studentDto = studentMapper.studentToStudentDto(student);
        return new ResponseEntity<>(studentDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody StudentDto studentDto) {
        Student studentToAdd = studentMapper.studentDtoToStudent(studentDto);
        Student addedStudent = studentService.addStudent(studentToAdd);
        return new ResponseEntity<>(studentMapper.studentToStudentDto(addedStudent), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<StudentDto> updateStudent(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentMapper.studentDtoToStudent(studentDto);
        Student updatedStudent = studentService.updateStudent(student);
        return new ResponseEntity<>(studentMapper.studentToStudentDto(updatedStudent), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
