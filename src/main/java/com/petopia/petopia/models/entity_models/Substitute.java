package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`substitute`")
public class Substitute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String phone;

    @ManyToMany
    @JoinTable(
            name = "appointment_substitute",
            joinColumns = @JoinColumn(name = "`substitute_id`"),
            inverseJoinColumns = @JoinColumn(name = "`appointment_id`")
    )
    private List<Appointment> appointmentList;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private SubstituteStatus substituteStatus;
}
