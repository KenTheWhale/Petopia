package com.petopia.petopia.models.response_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewOtherUserProfileResponse {
    private String status;
    private String message;
    UserProfile userProfile;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserProfile {
        private String userName;
        private String avatar;
        private String gender;
        private String phoneNumber;
        private String address;
    }


}
