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
public class Services {

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

    private String description;

    @OneToMany(mappedBy = "services")
    @ToString.Exclude
    private List<ServiceImage> serviceImageList;

    private double fee;

    private double rating;

    @Column(name = "`can_be_done_on_site`")
    private boolean canBeDoneOnSite;

    @ManyToMany(mappedBy = "servicesList")
    @ToString.Exclude
    private List<Appointment> appointmentList;
}
