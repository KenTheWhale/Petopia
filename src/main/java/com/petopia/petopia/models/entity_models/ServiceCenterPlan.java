package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`sc_plan`")
public class ServiceCenterPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "serviceCenterPlan")
    @ToString.Exclude
    private List<ServiceCenter> serviceCenterList;

    private String name;

    private double fee;

    private int duration;

    private String description;
}
