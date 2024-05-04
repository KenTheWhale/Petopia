package com.petopia.petopia.services;

import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;

public interface VetService {
    DraftReportResponse createDraftReport(DraftReportRequest request);
}
