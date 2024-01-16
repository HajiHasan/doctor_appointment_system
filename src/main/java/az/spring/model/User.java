package az.spring.model;

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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(length = 25, nullable = false)
    String name;
    @Column(length = 25,nullable = false)
    String surname;
    @Column(length = 35,nullable = false)
    String email;
    @Column(length = 10,nullable = false)
    String bloodGroup;
    @Column(length = 10,nullable = false)
    String gender;
    @Column(length = 100,nullable = false)
    String problems;
    @Column(length = 50,nullable = false)
    String password;

    @ManyToOne
    Roles role;  // role_id

    @OneToMany(mappedBy = "patientId")
    List<Appointment> appointments;



}
