package mx.ipn.upiicsa.web.classroom.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 962461950285130579L;

    private Long id;
    private String firstName;
    private String secondName;
    private String firstSurname;
    private String secondSurname;
    private String email;
}
