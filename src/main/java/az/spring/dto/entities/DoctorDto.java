package az.spring.dto.entities;

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

    String name;

    String surname;

    String email;

    String speciality;

    String password;

}
