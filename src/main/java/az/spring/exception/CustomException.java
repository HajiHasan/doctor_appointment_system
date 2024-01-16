package az.spring.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private final String message;


    public CustomException(Enums enums) {
        this.message = enums.getMessage();
    }
}
