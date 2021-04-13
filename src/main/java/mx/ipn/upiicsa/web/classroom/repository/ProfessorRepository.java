package mx.ipn.upiicsa.web.classroom.repository;

import mx.ipn.upiicsa.web.classroom.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findProfesorById(Long id);
    void deleteProfesorById(Long id);
    // TODO Join with Student table
    @Query("" +
            "SELECT CASE WHEN COUNT(p) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Profesor p " +
            "WHERE p.email = ?1"
    )
    Boolean existsEmail(String email);
}
