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
public class ServiceListResponse {
    private String status;
    private String message;
    private List<ServiceList> serviceList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ServiceList {
        private int id;
        private String serviceName;
        private String serviceType;
        private String location;
        private double servicePrice;
    }
}
