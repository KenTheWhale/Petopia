package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`service_provider`")
public class ServiceProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`account_id`")
    private Account account;

    @OneToMany(mappedBy = "serviceProvider")
    @ToString.Exclude
    private List<Appointment> appointmentList;

    @ManyToOne
    @JoinColumn(name = "`service_center_id`")
    private ServiceCenter serviceCenter;
}
