package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`cart`")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`user_id`")
    private User user;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private CartStatus cartStatus;

    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    private List<CartItem> cartItemList;
}
