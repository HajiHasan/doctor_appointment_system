package az.spring.serviceimpl;

import az.spring.baseresponse.GenericResponse;
import az.spring.dto.login.LoginRequestDto;
import az.spring.dto.login.UserLoginRequest;
import az.spring.dto.request.*;
import az.spring.dto.response.LoginResponse;
import az.spring.exception.Enums;
import az.spring.exception.CustomException;
import az.spring.mapper.DoctorMapper;
import az.spring.mapper.UserMapper;
import az.spring.model.Doctor;
import az.spring.model.Roles;
import az.spring.model.User;
import az.spring.repository.DoctorRepository;
import az.spring.repository.RoleRepository;
import az.spring.repository.UserRepository;
import az.spring.services.jwttoken.JwtTokenService;
import az.spring.services.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;


    @Override
    public GenericResponse<String> registerPatient(UserRegisterRequest userRegisterRequest) {

        log.info("Registering user with email: {}", userRegisterRequest.getEmail());
        if (userRepository.existsByEmail(userRegisterRequest.getEmail())) {
            log.warn("This email is already registered!");
            throw new CustomException(Enums.EMAIL_EXISTS);
        }
        Roles roles  = roleRepository.findByName("USER")
                .orElseThrow(()->new CustomException(Enums.ROLE_NOT_FOUND));
        User user = mapper.registerRequestToModel(userRegisterRequest);
        user.setName(userRegisterRequest.getName());
        user.setSurname(userRegisterRequest.getSurname());
        user.setGender(userRegisterRequest.getGender());
        user.setBloodGroup(userRegisterRequest.getBloodGroup());
        user.setEmail(userRegisterRequest.getEmail());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);
        return GenericResponse.success("success");
    }

    @Override
    public GenericResponse<String> registerDoctor(DoctorRegisterRequest doctorRegisterRequest) {
               log.info("Registering doctor: {}", doctorRegisterRequest.getEmail());
       if (doctorRepository.existsByEmail(doctorRegisterRequest.getEmail())){
           throw new CustomException(Enums.EMAIL_EXISTS);
       }
       Roles roles = roleRepository.findByName("DOCTOR")
               .orElseThrow(()->new CustomException(Enums.ROLE_NOT_FOUND));
        Doctor doctor = doctorMapper.registerRequestToModel(doctorRegisterRequest);
        doctor.setName(doctorRegisterRequest.getName());
        doctor.setSurname(doctorRegisterRequest.getSurname());
        doctor.setEmail(doctorRegisterRequest.getEmail());
        doctor.setPassword(passwordEncoder.encode(doctorRegisterRequest.getPassword()));
        doctor.setRole(roles);
        doctor.setSpeciality(doctorRegisterRequest.getSpeciality());
        doctorRepository.save(doctor);
        return GenericResponse.success("success");
    }


    @Override
    public GenericResponse<LoginResponse> loginUser(UserLoginRequest userLoginRequest) {
        log.info("Started to login : {}", userLoginRequest.getEmail());
        User user = userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> new CustomException(Enums.EMAIL_NOT_FOUND));
        log.info("Attempting login for user: {}", userLoginRequest.getEmail());
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(userLoginRequest.getEmail());
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto
                , userLoginRequest.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("Successful login for user: {}", userLoginRequest.getEmail());

        String accessToken = jwtTokenService.generateAccessToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(new RefreshTokenDto(true, user));
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        return GenericResponse.success(loginResponse);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(Enums.EMAIL_NOT_FOUND));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new CustomException(Enums.ID_NOT_FOUND));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

