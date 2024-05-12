package com.petopia.petopia.models.response_models;

import com.fasterxml.jackson.annotation.JsonFormat;
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


        private String email;

        private String avatarLink;

        private String status;


        private String accessToken;

//        @Data
//        @AllArgsConstructor
//        @NoArgsConstructor
//        @Builder
//        public static class appointmentResponse {
//            private int id;
//            private Pet pet;
//            @Data
//            @AllArgsConstructor
//            @NoArgsConstructor
//            @Builder
//            public static class appointmentStatusResponse {
//                private String status;
//            }
//            private appointmentStatusResponse appointmentStatus;
//            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//            private LocalDateTime date;
//            private String location;
//            private double fee;
//            private String type;
//            private ServiceReport serviceReport;
//        }
//
//        private List<appointmentResponse> appointmentList;

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
