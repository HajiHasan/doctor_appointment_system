package az.spring.services.service;

import az.spring.dto.response.DoctorResponse;
import az.spring.model.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {

//    GenericResponse<String> registerDoctor(DoctorDto doctorDto);
//
//    GenericResponse<LoginResponse> loginDoctor(DoctorLoginRequest doctorLoginRequest);
    DoctorResponse getAllDoctors();
    DoctorResponse getAvailableDoctors();
    Doctor getDoctorById(long id);
    Doctor getDoctorByEmail(String email);
}
