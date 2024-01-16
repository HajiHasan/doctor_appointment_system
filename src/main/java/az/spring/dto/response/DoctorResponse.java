package az.spring.dto.response;

import az.spring.dto.entities.DoctorDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorResponse {
    List<DoctorDto> doctors;
}
