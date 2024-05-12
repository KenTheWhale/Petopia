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

    private List<BlockedUser> blockedUsers;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BlockedUser {

            private Integer id;
            private String name;
            private String avatarLink;
        }

}
