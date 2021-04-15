package mx.ipn.upiicsa.web.classroom.controller;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.dto.ProfessorDto;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.ExceptionResponse;
import mx.ipn.upiicsa.web.classroom.exception.ProfesorNotFoundException;
import mx.ipn.upiicsa.web.classroom.mappers.ProfessorMapper;
import mx.ipn.upiicsa.web.classroom.model.Professor;
import mx.ipn.upiicsa.web.classroom.service.ProfessorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/professor")
@AllArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    private static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "20";

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
    public ResponseEntity<List<ProfessorDto>> getAllProfessors(
            @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProfessorDto> professorsDtoPage = professorService.getAllProfessors(pageable).map(professorMapper::professorToProfessorDto);
        return new ResponseEntity<>(professorsDtoPage.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorDto> getStudentById(@PathVariable Long id) {
        Professor professor = professorService.getProfessorById(id);
        ProfessorDto professorDto = professorMapper.professorToProfessorDto(professor);
        return new ResponseEntity<>(professorDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProfessorDto> addStudent(@Valid @RequestBody ProfessorDto professorDto) {
        Professor professorToAdd = professorMapper.professorDtoToProfessor(professorDto);
        Professor addedProfessor = professorService.addProfessor(professorToAdd);
        return new ResponseEntity<>(professorMapper.professorToProfessorDto(addedProfessor), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProfessorDto> updateProfessor(@Valid @RequestBody ProfessorDto professorDto) {
        Professor professorToUpdate = professorMapper.professorDtoToProfessor(professorDto);
        Professor updatedProfessor = professorService.updateProfessor(professorToUpdate);
        return new ResponseEntity<>(professorMapper.professorToProfessorDto(updatedProfessor), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessorById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
