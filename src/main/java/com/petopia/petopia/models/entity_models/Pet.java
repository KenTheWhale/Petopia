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
@Table(name = "`pet`")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`owner_id`")
    private User user;

    private String name;

    private String gender;

    private int age;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    private String type;

    private String necklaceId;

    private String description;

    @Transient
    private List<String> imgLinkList;

    @OneToMany(mappedBy = "pet")
    @ToString.Exclude
    private List<Appointment> appointmentList;
}
