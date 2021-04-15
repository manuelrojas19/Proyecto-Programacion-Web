package mx.ipn.upiicsa.web.classroom.service;

import lombok.AllArgsConstructor;
import mx.ipn.upiicsa.web.classroom.exception.EmailTakenException;
import mx.ipn.upiicsa.web.classroom.exception.ProfesorNotFoundException;
import mx.ipn.upiicsa.web.classroom.model.Professor;
import mx.ipn.upiicsa.web.classroom.repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    private static final String PROFESOR_NOT_FOUND = "Profesor with id %d does not exists";
    private static final String EMAIL_TAKEN = "Email %s has already taken";

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Page<Professor> getAllProfessors(Pageable pageable) {
        return professorRepository.findAll(pageable);
    }

    public Professor addProfessor(Professor professor) {
        if (professorRepository.existsEmail(professor.getEmail())) {
            throw new EmailTakenException(
                    String.format(EMAIL_TAKEN, professor.getEmail()));
        }
        return professorRepository.save(professor);
    }

    public Professor getProfessorById(Long id) {
        return professorRepository.findProfesorById(id).
                orElseThrow(() -> new ProfesorNotFoundException(String.format(PROFESOR_NOT_FOUND, id)));
    }

    public Professor updateProfessor(Professor professor) {
        Professor toUpdate = professorRepository.findProfesorById(professor.getId())
                .orElseThrow(() -> new ProfesorNotFoundException(
                        String.format(PROFESOR_NOT_FOUND, professor.getId())));
        if (professorRepository.existsEmail(professor.getEmail())
                && !professor.getEmail().equals(toUpdate.getEmail())) {
            throw new EmailTakenException(
                    String.format(EMAIL_TAKEN, professor.getEmail())
            );
        }
        return professorRepository.save(professor);
    }

    public void deleteProfessorById(Long professorId) {
        if(!professorRepository.existsById(professorId)) {
            throw new ProfesorNotFoundException(String.format(PROFESOR_NOT_FOUND, professorId));
        }
        professorRepository.deleteProfesorById(professorId);
    }
}
