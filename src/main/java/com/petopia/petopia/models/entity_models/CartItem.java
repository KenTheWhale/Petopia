package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`cart_item`")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`cart_id`")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "`attribute_combo_id`")
    private AttributeCombo attributeCombo;

    @Column(name = "`product_quantity`")
    private int quantity;

    private float price;

    @OneToOne(mappedBy = "cartItem", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private OrderDetail orderDetail;
}
