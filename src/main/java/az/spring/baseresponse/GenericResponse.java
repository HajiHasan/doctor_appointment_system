package az.spring.response;

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
    Meta meta;
    T data;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Meta {

        String key;
        String message;


        public static Meta of(String key, String message) {
            return Meta.builder()
                    .key(key)
                    .message(message)
                    .build();
        }
    }


    public static <T> GenericResponse <T> success(T data){
        return GenericResponse.<T>builder()
                .status(HttpStatus.OK)
                .meta(Meta.of("sucess", "Successfully"))
                .data(data)
                .build();
    }
}
