package com.petopia.petopia.models.response_models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUserProfileResponse {
    String address;
    String gender;
    String phone;
    String status;
    String message;
}
