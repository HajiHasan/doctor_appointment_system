package az.spring.dto.request;
import az.spring.model.User;
import lombok.*;
import lombok.experimental.FieldDefaults;



@Data
@Builder
public class RefreshTokenDto {

   public boolean rememberMe;
   public User user;

    public RefreshTokenDto(boolean rememberMe, User user) {
        this.rememberMe = rememberMe;
        this.user = user;
    }
}
