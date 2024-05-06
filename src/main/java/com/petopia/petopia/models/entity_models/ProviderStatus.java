package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`provider_status`")
public class ProviderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @OneToMany(mappedBy = "providerStatus")
    @ToString.Exclude
    private List<ServiceProvider> serviceProviderList;
}
