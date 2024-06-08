package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AccountResponse{
        private String status;

        private String name;

        private String email;

        private String role;

        private String avatar;

        private String background;

        private String address;

        private String accessToken;
    }

    private String status;

    private String message;

    private AccountResponse account;
}
