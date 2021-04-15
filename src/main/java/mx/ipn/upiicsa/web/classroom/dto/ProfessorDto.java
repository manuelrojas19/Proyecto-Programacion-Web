package mx.ipn.upiicsa.web.classroom.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.ipn.upiicsa.web.classroom.model.Gender;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDto implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = -2639550548553901069L;
    private Long id;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private Gender gender;
}
