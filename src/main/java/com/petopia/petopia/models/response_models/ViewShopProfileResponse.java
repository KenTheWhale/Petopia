package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewShopProfileResponse {
    private String status;
    private String message;
    private Shop shop;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Shop {
        private String ownerName;
        private String name;
        private int rating;
        private String address;
    }


}
