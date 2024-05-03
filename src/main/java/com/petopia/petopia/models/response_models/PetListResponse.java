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
public class PetListResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PetResponse{
        private Integer id;

        private String name;

        private String gender;

        private int age;

        private String type;

        private String necklaceId;

        private String description;

        private List<String> imgLinkList;
    }

    private String status;

    private String message;

    private List<PetResponse> petList;
}
