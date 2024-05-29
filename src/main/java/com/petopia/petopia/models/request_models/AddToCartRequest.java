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
public class AddToCartRequest {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Attribute{
        private String name;
        private String value;
    }

    private int productId;
    private int quantity;
    private List<Attribute> attributes;
}
