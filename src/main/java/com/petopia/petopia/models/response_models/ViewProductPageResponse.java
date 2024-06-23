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
public class ViewProductPageResponse{
    private String status;
    private String message;
    private List<ProductsResponse> productsResponses;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductsResponse{
        private String name;
        private String categoryName;
        private float price;
        private Integer soldQuantity;
        private float rating;

    }
}