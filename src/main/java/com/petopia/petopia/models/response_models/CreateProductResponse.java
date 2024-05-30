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

    private double price;

    private int availableQuantity;

    private int soldQuantity;

    private int rating;

    private List<Images> images;

    private List<FeedBack> feedbacks;

    private Shop shop;

    private String productStatus;

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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class FeedBack {

        private String content;

        private String userName;

    }

}
