package az.spring.controller;

import az.spring.serviceimpl.DoctorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorServiceImpl doctorService;
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/registerDoctor")
//    public GenericResponse<String> registerDoctor(@RequestBody @Valid DoctorDto doctorDto) {
//        return doctorService.registerDoctor(doctorDto);
//    }
//
//    @PostMapping("/loginDoctor")
//    public GenericResponse<LoginResponse> loginDoctor(@RequestBody DoctorLoginRequest doctorLoginRequest){
//        return doctorService.loginDoctor(doctorLoginRequest);
//    }

}
