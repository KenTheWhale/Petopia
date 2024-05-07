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
@Table(name = "`service_center`")
public class ServiceCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`manager_id`")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "`plan_id`")
    private ServiceCenterPlan serviceCenterPlan;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ServiceCenterStatus serviceCenterStatus;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    private List<ServiceProvider> serviceProviderList;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    private List<Service> serviceList;

    private LocalDateTime planPurchasedDate;

    private String name;

    private String address;

    private int rating;
}
