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
public class GetPlanTypeResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Type{
        private Integer id;
        private String name;
    }

    private String status;
    private String message;
    private List<Type> types;
}
