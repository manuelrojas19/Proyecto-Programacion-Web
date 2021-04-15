package mx.ipn.upiicsa.web.classroom.mappers;

import mx.ipn.upiicsa.web.classroom.dto.ProfessorDto;
import mx.ipn.upiicsa.web.classroom.model.Professor;
import org.mapstruct.Mapper;


@Mapper(uses = {DataMapper.class})
public interface ProfessorMapper {
    ProfessorDto professorToProfessorDto(Professor professor);
    Professor professorDtoToProfessor(ProfessorDto professorDto);
}
