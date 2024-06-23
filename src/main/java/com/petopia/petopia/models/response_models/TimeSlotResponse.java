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
public class TimeSlotResponse {
    private String status;
    private String message;
    private List<TimeSlot> timeSlots;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TimeSlot {
        private Integer id;
        private String name;
        private String startTime;
        private String endTime;
        private String status;
    }
}
