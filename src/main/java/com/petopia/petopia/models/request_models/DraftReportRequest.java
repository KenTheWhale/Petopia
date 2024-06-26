package com.petopia.petopia.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DraftReportRequest {

    private Integer appointmentId;

    private String report;

    private String extraContent;

}
