package com.petopia.petopia.models.entity_models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`post`")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`user_id`")
    private User user;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private PostStatus postStatus;

    private boolean canComment;

    private String content;

    @Column(name = "`post_date`")
    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime postDate;

    @Transient
    private List<String> imgLinkList;

    @Transient
    private List<Integer> likedUserIdList;

    @OneToMany(mappedBy = "post")
    @ToString.Exclude
    private List<Comment> commentList;

}
