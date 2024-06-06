package com.petopia.petopia.models.request_models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserProfileRequest {
    String address;
    String gender;
    String phone;
    String realName;
}
