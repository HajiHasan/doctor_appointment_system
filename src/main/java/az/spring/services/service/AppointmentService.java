package az.spring.services.service;

import az.spring.dto.entities.AppointmentDto;
import az.spring.dto.response.AppointmentResponse;
import az.spring.model.Appointment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    void createAppointment(HttpServletRequest token, AppointmentDto appointmentDto);
    AppointmentResponse getAppointmentByUserEmail(HttpServletRequest request);

    void deleteAppointmentById(Long id);

    List<Appointment> getAllAppointmentByDoctorId(Long id);
    List<Appointment> getAllBookedAppointments(Appointment.Status status);
}
