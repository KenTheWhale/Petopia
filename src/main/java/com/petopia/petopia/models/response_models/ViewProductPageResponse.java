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
        String name;
        String categoryName;
        float price;
        Integer soldQuantity;
        float rating;

    }
}