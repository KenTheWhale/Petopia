package com.petopia.petopia.models.request_models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountRequest {
    String name;
    String email;
    String phoneNumber;
    String password;
    String confirmPassword;
}
