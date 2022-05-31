package account.controller;

import account.Exception.NotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler
    public ResponseEntity<NotValidException> handlerNotValidPayment(ConstraintViolationException ex, WebRequest request) {
        NotValidException exception = new NotValidException(HttpStatus.BAD_REQUEST, request.getDescription(false).substring(4), ex.getMessage().replaceAll(".*\\.",""));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<NotValidException> handlerNotValidSQL(org.hibernate.exception.ConstraintViolationException ex, WebRequest request) {
        NotValidException exception = new NotValidException(HttpStatus.BAD_REQUEST, request.getDescription(false).substring(4), ex.getMessage().replaceAll(".*\\.",""));
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

}