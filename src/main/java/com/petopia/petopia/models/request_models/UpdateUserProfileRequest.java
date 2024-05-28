package com.petopia.petopia.models.request_models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UpdateUserProfileRequest {
    private String address;
    private String gender;
    private String phone;
}
