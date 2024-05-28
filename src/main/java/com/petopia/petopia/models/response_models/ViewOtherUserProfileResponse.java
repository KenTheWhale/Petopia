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

    private String userName;
    private String gender;
    private String phoneNumber;
    private String address;

}
