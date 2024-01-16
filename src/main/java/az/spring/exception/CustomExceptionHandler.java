package az.spring.exception;

import az.spring.baseresponse.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<GenericResponse<?>> handleCustomException(CustomException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.error(ex.getMessage()));
    }
    @ExceptionHandler
    public ResponseEntity<String> handleAllOtherException(Exception ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }


}
