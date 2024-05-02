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

    @ManyToOne
    @JoinColumn(name = "`order_id`")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "`product_id`")
    private Product product;

    @Column(name = "`product_name`")
    private String productName;

    @Column(name = "`product_price`")
    private double productPrice;

    @Column(name = "`product_quantity`")
    private int productQty;
}
