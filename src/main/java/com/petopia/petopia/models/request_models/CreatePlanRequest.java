package com.petopia.petopia.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlanRequest {
    private String name;
    private String type;
    private String description;
    private float fee;
    private int duration;
}
