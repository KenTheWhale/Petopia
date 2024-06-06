package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`account_id`")
    private Account account;

    private String gender;

    private String address;

    private String phone;

    @Column(name = "`real_name`")
    private String realName;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<UserImage> userImageList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Group> groupList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Post> postList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Comment> commentList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Pet> petList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<BlackList> blackList;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Notification> notificationList;

}
