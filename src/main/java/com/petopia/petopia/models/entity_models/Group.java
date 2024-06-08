package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`Group`")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private GroupStatus groupStatus;

    @ManyToOne
    @JoinColumn(name = "`owner_id`")
    private User user;

    private String name;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GroupManager> groupManagerList;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GroupMember> groupMemberList;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<GroupImage> groupImageList;
}
