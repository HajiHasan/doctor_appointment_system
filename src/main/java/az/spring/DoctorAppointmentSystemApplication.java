package az.spring;

import az.spring.services.jwttoken.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DoctorAppointmentSystemApplication {

    private final JwtTokenService accessToken;

    public static void main(String[] args) {
        SpringApplication.run(DoctorAppointmentSystemApplication.class, args);
     }



}
