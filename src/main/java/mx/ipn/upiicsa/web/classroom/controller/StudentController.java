package mx.ipn.upiicsa.web.classroom.controller;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.dto.StudentDto;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.ExceptionResponse;
import mx.ipn.upiicsa.web.classroom.exception.StudentNotFoundException;
import mx.ipn.upiicsa.web.classroom.model.Student;
import mx.ipn.upiicsa.web.classroom.mappers.StudentMapper;
import mx.ipn.upiicsa.web.classroom.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@AllArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final StudentMapper studentMapper;

    private static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";

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
    public ResponseEntity<List<StudentDto>> getAllStudents(
            @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentDto> studentDtoPage = studentService.getAllStudents(pageable)
                .map(studentMapper::studentToStudentDto);
        return new ResponseEntity<>(studentDtoPage.getContent(), HttpStatus.OK);
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
