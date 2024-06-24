package com.petopia.petopia.models.request_models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateUserProfileRequest {
    private String avatar;
    private String address;
    private String gender;
    private String phone;
    private String realName;
}
