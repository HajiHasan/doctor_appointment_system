package az.spring.baseresponse;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenericResponse <T> {

    HttpStatus status;
    String message;
    T data;


    public static <T> GenericResponse <T> success(T data){
        return GenericResponse.<T>builder()
                .status(HttpStatus.OK)
                .message("Successfully")
                .data(data)
                .build();
    }
    public static <T> GenericResponse <T> error(T data){
        return GenericResponse.<T>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Error")
                .data(data)
                .build();
    }
}
