package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`service`")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ServiceStatus serviceStatus;

    @ManyToOne
    @JoinColumn(name = "`sc_id`")
    private ServiceCenter serviceCenter;

    private String name;

    private double fee;

    @OneToMany(mappedBy = "service")
    @ToString.Exclude
    private List<AppointmentService> appointmentServiceList;
}
