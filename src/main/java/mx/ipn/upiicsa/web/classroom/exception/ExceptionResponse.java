package mx.ipn.upiicsa.web.classroom.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ExceptionResponse implements Serializable {
    @JsonIgnore
    private static final long serialVersionUID = 7645574770781355007L;
    private String errorMessage;
}
