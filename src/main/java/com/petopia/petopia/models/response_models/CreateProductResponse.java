package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductResponse {

    private String status;

    private String message;

    private Integer id;

    private String category;

    private String name;

    private List<Images> images;

    private String shop;

    private String productStatus;

    private List<AttributeCombou> attributeCombos;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AttributeCombou {

        private String mainAttributeValue;

        private String subAttributeValue;

        private float price;

        private int stockQuantity;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Shop {

        private Integer id;

        private String name;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Images {

        private String link;

    }



}
