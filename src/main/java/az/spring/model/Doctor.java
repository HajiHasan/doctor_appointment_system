package az.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(length = 45)
    String name;
    @Column(length = 45)
    String surname;
    @Column(length = 45)
    String email;
    @Column(length = 45)
    String speciality;

    String password;

    @ManyToOne
    Roles role; // role_id

//    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
//    @ToString.Exclude
//    List<Appointment> appointments;
}
