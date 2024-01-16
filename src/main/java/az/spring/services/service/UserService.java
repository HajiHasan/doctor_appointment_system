package az.spring.services.service;

import az.spring.baseresponse.GenericResponse;
import az.spring.dto.login.UserLoginRequest;
import az.spring.dto.request.DoctorRegisterRequest;
import az.spring.dto.request.UserRegisterRequest;
import az.spring.dto.response.LoginResponse;
import az.spring.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    GenericResponse<String> registerPatient(UserRegisterRequest userRegisterRequest);

    GenericResponse<String> registerDoctor(DoctorRegisterRequest doctorRegisterRequest);

    GenericResponse<LoginResponse> loginUser(UserLoginRequest userLoginRequest);

    User getUserByEmail(String email);

    User getUserById(long id);

    List<User> getAllUsers();

}
