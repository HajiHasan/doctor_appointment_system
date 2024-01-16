package az.spring.dto.request;

import az.spring.model.Appointment;
import az.spring.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
@Data
@Builder
public class AppointmentDto {

   private Date startDate;

   private Date endDate;

   private Long doctorId;

   private String status;
   @NotBlank
   private String problems;

//    boolean isBooked;




}
