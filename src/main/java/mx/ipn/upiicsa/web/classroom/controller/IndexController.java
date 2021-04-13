package mx.ipn.upiicsa.web.classroom.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController {
    private static final String ERROR_MAPPING = "/error";

    @GetMapping(value = ERROR_MAPPING)
    public ResponseEntity<String> error() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_MAPPING;
    }
}
