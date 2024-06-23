package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewProductDetailResponse {
    private String status;
    private String message;
    private ProductDetail productDetails;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDetail {
        private DetailResponse detailResponse;
        private Shop shop;
        private List<UserFeedBack> userFeedBacks;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DetailResponse {
        private List<Image> imgLink;
        private String name;
        private double rating;
        private int soldQuantity;
        private Map<String, List<String>> productAttributes;
        private List<Combo> attributeCombos;
        private String productStatus;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Image {
        private String link;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Combo {
        private AttributeCom mainAttribute;
        private AttributeCom subAttribute;
        private float price;
        private int quantity;
        private String comboStatus;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AttributeCom {
        private String name;
        private String value;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserFeedBack {
        private String avatar;
        private String username;
        private String content;
        private int rating;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Shop {
        private String name;
        private double shopRating;
        private int totalProduct;
    }
}
