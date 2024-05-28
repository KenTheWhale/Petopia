package com.petopia.petopia.models.request_models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EditDraftReportRequest {

    private Integer draftReportId;

    private String report;

    private String extraContent;
}
