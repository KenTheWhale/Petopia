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
    private BusinessPlan businessPlan;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ServiceCenterStatus serviceCenterStatus;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceProvider> serviceProviderList;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Services> servicesList;

    private LocalDateTime planPurchasedDate;

    private String name;

    private String description;

    private String phone;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceCenterImage> serviceCenterImageList;

    private String website;

    private String address;

    private double rating;

    private String type;

    @OneToMany(mappedBy = "serviceCenter")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TimeSlot> timeSlotList;
}
