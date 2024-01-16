package az.spring.dto.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {
    //patient and doctor
    private String email;
    private String password;
}
