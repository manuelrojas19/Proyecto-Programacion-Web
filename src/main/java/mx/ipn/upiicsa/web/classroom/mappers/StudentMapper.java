package mx.ipn.upiicsa.web.classroom.mappers;

import mx.ipn.upiicsa.web.classroom.model.Student;
import mx.ipn.upiicsa.web.classroom.dto.StudentDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DataMapper.class})
public interface StudentMapper {
    StudentDto studentToStudentDto(Student student);
    Student studentDtoToStudent(StudentDto studentDto);
}
