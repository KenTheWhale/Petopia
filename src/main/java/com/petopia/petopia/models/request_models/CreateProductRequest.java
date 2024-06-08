package com.petopia.petopia.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {

    private Integer categoryId;

    private String name;

    private List<String> images;

    private String mainAttributeName;

    private List<Value> mainAttributeValues;

    private String subAttributeName;

    private List<Value> subAttributeValues;

    List<AttributeCombo> attributeCombos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Value {
        private String valueName;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AttributeCombo {
        private String mainAttributeValue;
        private String subAttributeValue;
        private float price;
        private int stockQuantity;
    }

}
