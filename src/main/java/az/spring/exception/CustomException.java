package az.spring.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private final String message;


    public ResourceNotFoundException(Enums enums) {
        this.message = enums.getMessage();
    }
}
