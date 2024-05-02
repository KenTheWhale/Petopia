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
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private GroupStatus groupStatus;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    private String name;

    private String avatarLink;

    private String backgroundLink;

    @Transient
    private List<String> imgLinkList;

    @Transient
    private List<Integer> managerIdList;

    @Transient
    private List<Integer> memberIdList;
}
