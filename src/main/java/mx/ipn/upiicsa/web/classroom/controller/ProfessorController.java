package mx.ipn.upiicsa.web.classroom.controller;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.ExceptionResponse;
import mx.ipn.upiicsa.web.classroom.exception.ProfesorNotFoundException;
import mx.ipn.upiicsa.web.classroom.model.Professor;
import mx.ipn.upiicsa.web.classroom.service.ProfessorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/professor")
@AllArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ExceptionResponse> emailAlreadyTaken(EmailTakenException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProfesorNotFoundException.class)
    public ResponseEntity<ExceptionResponse> studentNotFound(ProfesorNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Professor>> getAllProfessors() {
        List<Professor> professors = professorService.getAllProfessors();
        return new ResponseEntity<>(professors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getStudentById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        return new ResponseEntity<>(professor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Professor> addStudent(@Valid @RequestBody Professor professor) {
        Professor addedProfessor;
        addedProfessor = professorService.addProfessor(professor);
        return new ResponseEntity<>(addedProfessor, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Professor> updateProfessor(@Valid @RequestBody Professor professor) {
        Professor updatedProfessor;
        updatedProfessor = professorService.updateProfessor(professor);
        return new ResponseEntity<>(updatedProfessor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
