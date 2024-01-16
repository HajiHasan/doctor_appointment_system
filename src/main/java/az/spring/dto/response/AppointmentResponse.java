package az.spring.dto.response;

import az.spring.dto.entities.AppointmentDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class AppointmentResponse {
    List<AppointmentDto> appointmentDetails;
}
