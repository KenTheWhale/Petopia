package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceCenterDetailResponse {

    private String status;

    private String message;

    private String name;

    private String address;

    private String phone;

    private String website;

    private String description;

    private String imgLink;
}
