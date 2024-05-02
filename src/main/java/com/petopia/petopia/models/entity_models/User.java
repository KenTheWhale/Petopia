package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;

    private String name;

    private String gender;

    private String address;

    private String phone;

    @Transient
    private List<String> imgLinkList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Group> groupList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Post> postList;

}
