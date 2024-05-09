package com.petopia.petopia.models.response_models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthHistoryResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DoctorResponse{
        private Integer id;

        private String name;

        private String avatarLink;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HealthReportResponse{
        private Integer id;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime date;

        private String report;

        private String status;

        private String extraContent;

        private String place;

        private DoctorResponse doctor;
    }

    private String status;

    private String message;

    private int totalPage;

    private Integer petId;

    private String petName;

    private String petGender;

    private int petAge;

    private String petType;

    private String petNecklaceId;

    private String petDescription;

    private List<String> imgLinkList;

    private List<HealthReportResponse> appointments;
}
