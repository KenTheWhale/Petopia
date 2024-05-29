package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`product_attribute`")
public class ProductAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "productAttribute")
    @ToString.Exclude
    private List<AttributeValue> attributeValueList;

    @ManyToOne
    @JoinColumn(name = "`product_id`")
    private Product product;
}
