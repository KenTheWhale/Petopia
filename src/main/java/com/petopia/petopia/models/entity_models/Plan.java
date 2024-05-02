package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`plan`")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private PlanStatus planStatus;

    private String name;

    private double fee;

    private int duration;

    private String description;

    @OneToMany(mappedBy = "plan")
    @ToString.Exclude
    private List<Shop> shopList;
}
