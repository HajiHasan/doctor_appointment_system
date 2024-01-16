package az.spring.controller;

import az.spring.dto.entities.AppointmentDto;
import az.spring.dto.response.AppointmentResponse;
import az.spring.model.Appointment;
import az.spring.serviceimpl.AppointmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointment")
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createAppointment(@RequestBody @Valid AppointmentDto appointmentDto, HttpServletRequest token){
           appointmentService.createAppointment(token, appointmentDto);
    }

    @GetMapping("/getAppUser")
    public AppointmentResponse getAppointmentByUserEmail(HttpServletRequest request) {
        return appointmentService.getAppointmentByUserEmail(request);
    }
    @GetMapping("/getAll/ByDoctorId/{id}")
    public List<Appointment> getAllAppointmentByDoctorId(@PathVariable long id){
        return appointmentService.getAllAppointmentByDoctorId(id);
    }
    @DeleteMapping("/deleteApp")
    public void deleteAnAppointmentById(Long id){
        appointmentService.deleteAppointmentById(id);
    }
    @GetMapping("/booked")
    public List<Appointment> getAllBookedAppointments(Appointment.Status status){
        return appointmentService.getAllBookedAppointments(status);
    }


}
