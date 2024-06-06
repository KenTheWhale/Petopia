package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`attribute_combo`")
public class AttributeCombo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`product_id`")
    private Product product;

    @Column(name = "`main_attribute_value_id`")
    private int MAVId; //main attribute value id

    @Column(name = "`main_attribute_name`")
    private String MAN; // main attribute name

    @Column(name = "`main_value_name`")
    private String MAVN; // main attribute value name

    @Column(name = "`sub_attribute_value_id`")
    private Integer SAVId; // sub attribute value id

    @Column(name = "`sub_attribute_name`")
    private String SAN; // sub attribute name

    @Column(name = "`sub_value_name`")
    private String SAVN; // sub attribute value name

    private int quantity;

    private float price;
}
