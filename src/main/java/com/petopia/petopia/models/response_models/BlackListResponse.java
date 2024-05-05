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
public class BlackListResponse {

    private String status;

    private String message;

    private List<blackList> blackList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class blackList {
        private int id;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class userResponse{
            private int id;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            @Builder
            public static class accountResponse {
                private String name;
            }
            private accountResponse account;

            private String avatarLink;

        }
        private userResponse userData;

    }

}
