package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`business_plan`")
public class BusinessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "businessPlan")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceCenter> serviceCenterList;

    @OneToMany(mappedBy = "businessPlan")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Shop> shopList;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private PlanStatus planStatus;

    @ManyToOne
    @JoinColumn(name = "`type_id`")
    private PlanType planType;

    private String name;

    private float fee;

    private int duration;

    private String description;
}
