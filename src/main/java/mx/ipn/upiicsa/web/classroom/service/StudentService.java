package mx.ipn.upiicsa.web.classroom.service;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.StudentNotFoundException;
import mx.ipn.upiicsa.web.classroom.model.Student;
import mx.ipn.upiicsa.web.classroom.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    private static final String STUDENT_NOT_FOUND = "Student with id %d does not exists";
    private static final String EMAIL_TAKEN = "Email %s has already taken";


    public Page<Student> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }

    public Student addStudent(Student student) {
        if (studentRepository.existsEmail(student.getEmail())) {
            throw new EmailTakenException(
                    String.format(EMAIL_TAKEN, student.getEmail()));
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findStudentById(id).
                orElseThrow(() -> new StudentNotFoundException(String.format(STUDENT_NOT_FOUND, id)));
    }

    public Student updateStudent(Student student) {
        Student toUpdate = studentRepository.findStudentById(student.getId())
                .orElseThrow(() -> new StudentNotFoundException(
                        String.format(STUDENT_NOT_FOUND, student.getId())));
        if (studentRepository.existsEmail(student.getEmail())
                && !student.getEmail().equals(toUpdate.getEmail())) {
            throw new EmailTakenException(
                    String.format(EMAIL_TAKEN, student.getEmail())
            );
        }
        return studentRepository.save(student);
    }

    public void deleteStudentById(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(String.format(STUDENT_NOT_FOUND, studentId));
        }
        studentRepository.deleteStudentById(studentId);
    }
}
