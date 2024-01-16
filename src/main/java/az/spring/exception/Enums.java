package az.spring.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Enums {
    EMAIL_NOT_FOUND("this email not exists!"),
    EMAIL_EXISTS("this email is already registered"),
    ID_NOT_FOUND("user with this id not exists"),
    ROLE_NOT_FOUND("Role has not registered"),
    TIME_ERROR("Please change time!"),
    OVERLAP_ERROR("Already booked, please choose another empty time");

    private final String message;

    Enums(String message) {
        this.message = message;
    }
}
