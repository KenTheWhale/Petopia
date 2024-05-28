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
public class ViewFeedbackListResponse {
    private String status;
    private String message;
    private List<UserResponse> user;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponse {
        private String username;
        private String avatar;
    }

}
