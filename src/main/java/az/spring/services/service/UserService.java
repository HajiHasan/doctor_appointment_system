package az.spring.services.myservices;

import az.spring.baseresponse.GenericResponse;
import az.spring.dto.request.UserDto;
import az.spring.mapper.UserMapper;
import az.spring.model.Roles;
import az.spring.model.User;
import az.spring.repository.RoleRepository;
import az.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public GenericResponse<String> registerUser(UserDto userDto) {

        log.info("Registering user with email: {}", userDto.getEmail());
        if (userRepository.existsByEmail(userDto.getEmail())) {
            log.warn("This email is already registered!");
        }
        Roles roles = roleRepository.findByName("USER").orElse(null);
        User user = mapper.dtoToModel(userDto);
        user.setName(userDto.getName());
        user.setSurname(user.getSurname());
        user.setGender(user.getGender());
        user.setBloodGroup(user.getBloodGroup());
        user.setEmail(user.getEmail());
        user.setRole(roles);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));


        userRepository.save(user);
        return GenericResponse.success("success");


    }
//    public GenericResponse <LoginResponse> loginUser(LoginRequest loginRequest){
//         User user = userRepository.findUserByEmail(loginRequest.getEmail())
//                 .orElseThrow(()->)
//
//
//    }
}
