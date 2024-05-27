package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`post_liked_user`")
public class PostLikedUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`user_id`")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "`post_id`")
    private Post post;
}
