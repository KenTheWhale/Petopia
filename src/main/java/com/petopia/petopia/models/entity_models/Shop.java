package com.petopia.petopia.models.entity_models;

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
@Table(name = "`shop`")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`owner_id`")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "`planId`")
    private BusinessPlan businessPlan;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private ShopStatus shopStatus;

    @OneToMany(mappedBy = "shop")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Product> productList;

    private LocalDateTime planPurchasedDate;

    private String name;

    private String address;

    private int rating;
}
