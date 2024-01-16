package az.spring.serviceimpl;

import az.spring.dto.entities.AppointmentDto;
import az.spring.dto.response.AppointmentResponse;
import az.spring.exception.CustomException;
import az.spring.exception.Enums;
import az.spring.mapper.AppointmentMapper;
import az.spring.model.Appointment;
import az.spring.model.Doctor;
import az.spring.model.User;
import az.spring.repository.AppointmentRepository;
import az.spring.repository.DoctorRepository;
import az.spring.repository.UserRepository;
import az.spring.services.jwttoken.JwtTokenService;
import az.spring.services.service.AppointmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentMapper appointmentMapper;
    private final AppointmentRepository appointmentRepository;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    @Value("${app.time.min-duration}")
    private long MIN_DURATION;

    @Value("${app.time.max-duration}")
    private long MAX_DURATION;

    //this  method is for to create new appointment, here all status are going to be "PENDING" manually
    // todo : new method doctor confirms appointments
    @Override
    public void createAppointment(HttpServletRequest token, AppointmentDto appointmentDto) {

        String jwtFromRequest = jwtTokenService.getJWTFromRequest(token);
        Appointment appointment = appointmentMapper.dtoToModel(appointmentDto);
        appointment.setStartDate(appointmentDto.getStartDate());
        appointment.setEndDate(appointmentDto.getEndDate());

        if (appointment.getStartDate().compareTo(appointment.getEndDate()) >= 0) {
            throw new CustomException(Enums.TIME_ERROR);
        }

        appointment.setProblems(appointmentDto.getProblems());
        appointment.setStatus(Appointment.Status.PENDING);

        String userEmail = jwtTokenService.extractUsername(jwtFromRequest);
        log.info("Getting user email: {}", userEmail);
        User currentUser = userRepository.getUserByEmail(userEmail)
                .orElseThrow(() -> new CustomException(Enums.EMAIL_NOT_FOUND));
        log.info("Getting currentUser: {}", currentUser);
        appointment.setUser(currentUser);
        Doctor doctor = doctorRepository.findDoctorById(appointmentDto.getDoctorId())
                .orElseThrow(() -> new CustomException(Enums.ID_NOT_FOUND));
        appointment.setDoctor(doctor);


        long appointmentDuration = (appointmentDto.getEndDate().getTime() - appointmentDto.getStartDate().getTime());
        if (appointmentDuration < MIN_DURATION || appointmentDuration > MAX_DURATION) {
            log.info("appointment duration time must be between 15 and 60 minutes");
            throw new CustomException(Enums.TIME_ERROR);
        }
        List<Appointment> allAppsOfGivenDoctor = appointmentRepository
                .findAppointmentByDoctorId(appointmentDto.getDoctorId());
        log.info("Getting appointments of current doctor: {}", allAppsOfGivenDoctor);
        List<LocalDateTime[]> appointmentTime = new ArrayList<>();
        for (Appointment currenAppointment:allAppsOfGivenDoctor) {
            LocalDateTime localStartTime = getLocalDateTimeFromDate(currenAppointment.getStartDate());
            LocalDateTime localEndTime = getLocalDateTimeFromDate(currenAppointment.getEndDate());
            appointmentTime.add(new LocalDateTime[]{localStartTime,localEndTime});
        }
        if (isAppointmentOverLap(appointmentTime, appointment)){
            throw new CustomException(Enums.OVERLAP_ERROR);
        } else {
            appointmentRepository.save(appointment);
        }
    }

    @Override
    public AppointmentResponse getAppointmentByUserEmail(HttpServletRequest request) {
        String jwtFromRequest = jwtTokenService.getJWTFromRequest(request);
        String currentUserEmail = jwtTokenService.extractUsername(jwtFromRequest);
        List<AppointmentDto> appointments = appointmentRepository.findAppointmentByUserEmail(currentUserEmail);
        return AppointmentResponse.builder()
                .appointmentDetails(appointments)
                .build();
    }

    @Override
    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<Appointment> getAllBookedAppointments(Appointment.Status status) {
        return appointmentRepository.findAppointmentByStatus(status);
    }

    @Override
    public List<Appointment> getAllAppointmentByDoctorId(Long id) {
        return appointmentRepository.findAppointmentByDoctorId(id);
    }

    private static boolean isAppointmentOverLap(List<LocalDateTime[]> times, Appointment appointment) {
       times.add(new LocalDateTime[]{
               getLocalDateTimeFromDate(appointment.getStartDate()),
               getLocalDateTimeFromDate(appointment.getEndDate())
       });
       times.sort(Comparator.comparing(time -> time[0]));
       for (int i = 1; i < times.size(); i++) {
           if (times.get(i - 1)[1].isAfter(times.get(i)[0]))
               return true;
           }
           return false;
       }
   private static LocalDateTime getLocalDateTimeFromDate(Date date){
        return date
                .toInstant()
                .atZone(ZoneId.of("UTC+4"))
                .toLocalDateTime();
   }
}

