package com.petopia.petopia.models.response_models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestingAppointment {

    private String status;

    private String message;

    private List<requestingAppointment> requestingAppointmentList;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class requestingAppointment {
        private int id;
        private String petName;
        private String status;
        private String type;
        private String location;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date;
    }
}
