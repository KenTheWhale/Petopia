package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "`provider_id`")
    private ServiceProvider serviceProvider;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private AppointmentStatus appointmentStatus;

    @ManyToOne
    @JoinColumn(name = "`payment_id`")
    private PaymentMethod paymentMethod;

    @ManyToMany(mappedBy = "appointmentList")
    @ToString.Exclude
    private List<Substitute> substituteList;

    private LocalDateTime date;

    private Integer centerId;

    private double fee;

    private String type;

    private String location;

    @Column(name = "`extra_information`")
    private String extraInformation;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private ServiceReport serviceReport;

    @ManyToMany
    @JoinTable(
            name = "appointment_service",
            joinColumns = @JoinColumn(name = "`appointment_id`"),
            inverseJoinColumns = @JoinColumn(name = "`service_id`")
    )
    private List<Services> servicesList;
}
