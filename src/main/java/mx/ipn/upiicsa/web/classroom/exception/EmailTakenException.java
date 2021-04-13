package mx.ipn.upiicsa.web.classroom.exception;

public class EmailTakenException extends RuntimeException {
    public EmailTakenException(String s) {
        super(s);
    }
}
