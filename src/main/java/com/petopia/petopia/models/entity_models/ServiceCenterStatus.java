package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`sc_status`")
public class ServiceCenterStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "serviceCenterStatus")
    @ToString.Exclude
    private List<ServiceCenter> serviceCenterList;

    private String status;
}
