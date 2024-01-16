package az.spring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Data
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private Doctor doctor;

    @ManyToOne
    @ToString.Exclude
    private User user;

    private Date startDate;

    private Date endDate;

    private String problems;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status{
        PENDING,
        CONFIRMED,
        CANCELLED
    }


    //boolean isBooked;
}
