package az.spring.serviceimpl;

import az.spring.dto.entities.DoctorDto;
import az.spring.dto.response.DoctorResponse;
import az.spring.exception.CustomException;
import az.spring.exception.Enums;
import az.spring.mapper.DoctorMapper;
import az.spring.model.Doctor;
import az.spring.repository.DoctorRepository;
import az.spring.repository.RoleRepository;
import az.spring.services.jwttoken.JwtTokenService;
import az.spring.services.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

//    @Override
//    public GenericResponse<String> registerDoctor(DoctorDto doctorDto) {
//       log.info("Registering doctor: {}", doctorDto.getEmail());
//       if (doctorRepository.existsByEmail(doctorDto.getEmail())){
//           throw new CustomException(Enums.EMAIL_EXISTS);
//       }
//       Roles roles = roleRepository.findByName("DOCTOR")
//               .orElseThrow(()->new CustomException(Enums.ROLE_NOT_FOUND));
//        Doctor doctor = mapper.dtoToModel(doctorDto);
//        doctor.setName(doctorDto.getName());
//        doctor.setSurname(doctorDto.getSurname());
//        doctor.setEmail(doctorDto.getEmail());
//        doctor.setPassword(passwordEncoder.encode(doctorDto.getPassword()));
//        doctor.setRole(roles);
//        doctor.setSpeciality(doctorDto.getSpeciality());
//        doctorRepository.save(doctor);
//        return GenericResponse.success("Success");
//    }
//
//    @Override
//    public GenericResponse<LoginResponse> loginDoctor(DoctorLoginRequest doctorLoginRequest) {
//        log.info("Started to login doctor with email: {}", doctorLoginRequest.getDoctorEmail());
//        Doctor doctor = doctorRepository.findDoctorByEmail(doctorLoginRequest.getDoctorEmail())
//                .orElseThrow(() -> new CustomException(Enums.EMAIL_NOT_FOUND));
//        LoginRequestDto loginRequestDto = new LoginRequestDto();
//        loginRequestDto.setEmail(doctorLoginRequest.getDoctorEmail());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequestDto
//                , doctorLoginRequest.getPassword());
//        authentication = authenticationManager.authenticate(authentication);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        log.info("Successful login for Doctor with email: {}", doctorLoginRequest.getDoctorEmail());
//        String accessToken  = jwtTokenService.generateDoctorToken(doctor);
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setAccessToken(accessToken);
//        return GenericResponse.success(loginResponse);
//    }

    @Override
    public DoctorResponse getAllDoctors() {
        List<DoctorDto> doctorsList = doctorRepository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
        return DoctorResponse.builder()
                .doctors(doctorsList)
                .build();
    }

    @Override
    public DoctorResponse getAvailableDoctors() {
        return null; // TODO: 1/8/2024 fill here!!!!!!
    }

    @Override
    public Doctor getDoctorById(long id) {
        return doctorRepository.findById(id)
                .orElseThrow(()->new CustomException(Enums.ID_NOT_FOUND));
    }

    @Override
    public Doctor getDoctorByEmail(String email) {
        return doctorRepository.findDoctorByEmail(email)
                .orElseThrow(()->new CustomException(Enums.EMAIL_NOT_FOUND));
    }
}
