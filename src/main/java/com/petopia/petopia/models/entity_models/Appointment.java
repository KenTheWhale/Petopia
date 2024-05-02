package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

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

    private Date date;

    private String location;

    private double fee;

    @OneToOne(mappedBy = "appointment")
    @ToString.Exclude
    private HealthReport healthReport;

    @ManyToOne
    @JoinColumn(name = "`type_id`")
    private AppointmentType appointmentType;
}
