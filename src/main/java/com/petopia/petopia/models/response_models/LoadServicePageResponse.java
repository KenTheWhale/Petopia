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
public class LoadServicePageResponse {
    private String status;

    private String message;

    private List<ServiceCenter> serviceCenters;

    private List<Service> services;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ServiceCenter{
        private int id;

        private String name;

        private String address;

        private double rating;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Service{
        private int id;

        private String serviceName;

        private double rating;

        private double servicePrice;
    }

}
