package pl.opalka.Registration.Login.Service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    private String message;

    @ExceptionHandler
    public ResponseEntity<String> handleException(UserException e) {
        HttpStatus httpStatus;
        switch (e.getUserError()){
            case USERNAME_EXISTS:
                httpStatus = HttpStatus.CONFLICT;
            case EMAIL_EXISTS:
                httpStatus = HttpStatus.CONFLICT;
            default: httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        }
        message = e.getUserError().getMessage();
        return ResponseEntity.status(httpStatus).body(message);
    }
}
