package az.spring.repository;

import az.spring.dto.entities.AppointmentDto;
import az.spring.dto.response.AppointmentResponse;
import az.spring.model.Appointment;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentDto> findAppointmentByUserEmail(String email);

    List<Appointment> findAppointmentByDoctorId(Long id);
    List<Appointment> findAppointmentByStatus(Appointment.Status status);

}
