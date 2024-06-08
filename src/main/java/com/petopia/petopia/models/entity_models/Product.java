package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`product`")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`shop_id`")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private ProductStatus productStatus;

    @ManyToOne
    @JoinColumn(name = "`cate_id`")
    private ProductCategory productCategory;

    private String name;

    @Column(name = "`sold_quantity`")
    private int soldQty;

    private int rating;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ProductImage> productImageList;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Feedback> feedbackList;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ProductAttribute> productAttributeList;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<AttributeCombo> attributeComboList;
}
