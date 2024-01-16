package az.spring.dto.request;

import az.spring.model.Roles;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDto {
    @NotBlank
    String name;
    @NotBlank
    String surname;
    @NotBlank
    String email;
    @NotBlank
    String speciality;
    @NotBlank
    String password;

}
