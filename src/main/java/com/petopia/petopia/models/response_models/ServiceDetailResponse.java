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
public class ServiceDetailResponse {

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class ImageResponse{
                private String link;
        }

        private String status;

        private String message;

        private String name;

        private String description;

        private List<ImageResponse> images;
}
