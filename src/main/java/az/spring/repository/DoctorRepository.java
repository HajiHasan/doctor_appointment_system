package az.spring.repository;

import az.spring.model.Appointment;
import az.spring.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository <Doctor, Long> {
    boolean existsByEmail(String email);
    Optional<Doctor> findDoctorByEmail(String email);
    Optional<Doctor> findDoctorById(Long id);
}
