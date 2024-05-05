package com.petopia.petopia.models.response_models;

import com.petopia.petopia.enums.Role;
import com.petopia.petopia.models.entity_models.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentUserResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class userResponse{
        private int id;

        private String name;

//        private String password;

        private String email;

        private String avatarLink;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class accountStatusResponse {
            private String status;
        }

        private accountStatusResponse accountStatus;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class tokenListResponse {
            private int id;
            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            public static class tokenStatusResponse {
                private String status;
            }
            private tokenStatusResponse tokenStatus;
            private String value;
            private String type;
        }

        private List<tokenListResponse> tokenList;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class appointmentResponse {
            private int id;
            private Pet pet;
            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            public static class appointmentStatusResponse {
                private String status;
            }
            private appointmentStatusResponse appointmentStatus;
            private LocalDateTime date;
            private String location;
            private double fee;
            private String type;
            private ServiceReport serviceReport;
        }

        private List<appointmentResponse> appointmentList;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class shopResponse {
            private int id;
            private String name;
            private String address;
        }

        private shopResponse shop;

        private Role role;
    }

    private String status;

    private String message;

    private userResponse user;
}
