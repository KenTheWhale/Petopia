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
public class    ServiceCenterDetailResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Images{
        private String link;
    }

    private String status;

    private String message;

    private String name;

    private String address;

    private String phone;

    private String website;

    private String description;

    private List<Images> images;
}
