package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`notification`")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`user_id`")
    private User user;

    private String content;

    @OneToMany(mappedBy = "notification")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<NotificationImage> notificationImageList;
}
