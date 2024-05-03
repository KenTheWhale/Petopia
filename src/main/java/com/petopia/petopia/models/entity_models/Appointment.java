package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`appointment`")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`pet_id`")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "`doctor_id`")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private AppointmentStatus appointmentStatus;

    private LocalDateTime date;

    private String location;

    private double fee;

    private String type;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private ServiceReport serviceReport;
}
