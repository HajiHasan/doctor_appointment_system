package az.spring.controller;

import az.spring.baseresponse.GenericResponse;
import az.spring.dto.login.UserLoginRequest;
import az.spring.dto.request.DoctorRegisterRequest;
import az.spring.dto.request.UserRegisterRequest;
import az.spring.dto.response.LoginResponse;
import az.spring.model.User;
import az.spring.serviceimpl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/register/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<String> registerPatient(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return userService.registerPatient(userRegisterRequest);
    }
    @PostMapping("/register/doctor")
    public GenericResponse<String> registerDoctor(@RequestBody @Valid DoctorRegisterRequest doctorRegisterRequest){
        return userService.registerDoctor(doctorRegisterRequest);
    }

    @PostMapping("/login/patient")
    public GenericResponse<LoginResponse> loginUser(@RequestBody UserLoginRequest userLoginRequest){
        return userService.loginUser(userLoginRequest);
    }
    @GetMapping("/getuser/id/{id}")
    public User getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("/getuser/email/{email}")
    public User getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }
    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
}
