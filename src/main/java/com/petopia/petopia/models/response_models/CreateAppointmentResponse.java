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
public class CreateAppointmentResponse {

    private String status;

    private String message;

    private appointmentDraft appointment;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Servicee{
        private int id;
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class appointmentDraft {
        private String petName;

        private String status;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date;

        private String location;

        private List<Servicee> services;

        private String type;

        private String extraInformation;

        private List<SubstituteList> substitute;

        private double fee;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SubstituteList {
        private String name;
        private String phone;
    }
}
