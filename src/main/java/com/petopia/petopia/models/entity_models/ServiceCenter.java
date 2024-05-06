package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    private LocalDateTime planExpiredDate;

    private String name;

    private String address;

    private int rating;
}
