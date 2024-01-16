package az.spring.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterRequest {
    //Patient
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @NotBlank
    String email;
    @NotBlank
    String bloodGroup;
    @NotBlank
    String gender;
    @NotBlank
    String password;

}
