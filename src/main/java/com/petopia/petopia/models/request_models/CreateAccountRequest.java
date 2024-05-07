package com.petopia.petopia.models.request_models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountRequest {
    String avatarLink;
    String email;
    String password;
    String name;
}
