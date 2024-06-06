package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`attribute_value`")
public class AttributeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "`attribute_id`")
    @ToString.Exclude
    private ProductAttribute productAttribute;
}
