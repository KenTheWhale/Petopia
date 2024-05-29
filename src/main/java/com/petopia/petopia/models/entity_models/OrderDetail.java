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
@Table(name = "`order_detail`")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`cart_id`")
    private CartItem cartItem;

    @ManyToOne
    @JoinColumn(name = "`order_id`")
    private Order order;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`combo_price`")
    private float comboPrice;

    @Column(name = "`main_attribute_name`")
    private String MAN;

    @Column(name = "`main_value_name`")
    private String MAVN;

    @Column(name = "`sub_attribute_name`")
    private String SAN;

    @Column(name = "`sub_value_name`")
    private String SAVN;
}
