package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewShopResponse {
    private String shopOwnerName;
    private String shopName;
    private int shopRating;
    private String shopAddress;

}
