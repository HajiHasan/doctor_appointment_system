package az.spring.repository;

import az.spring.dto.response.DoctorResponse;
import az.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional <User> findUserByEmail(String email);
    Optional <User> getUserByEmail(String email);
}
