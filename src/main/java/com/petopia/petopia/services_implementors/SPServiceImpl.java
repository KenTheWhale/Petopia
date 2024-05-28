package com.petopia.petopia.services_implementors;

import com.petopia.petopia.enums.Const;
import com.petopia.petopia.models.entity_models.*;
import com.petopia.petopia.models.request_models.ConfirmDraftReportRequest;
import com.petopia.petopia.models.request_models.DraftReportRequest;
import com.petopia.petopia.models.request_models.EditDraftReportRequest;
import com.petopia.petopia.models.response_models.DraftReportResponse;
import com.petopia.petopia.repositories.AppointmentRepo;
import com.petopia.petopia.repositories.PetRepo;
import com.petopia.petopia.repositories.ServiceReportRepo;
import com.petopia.petopia.repositories.ServiceReportStatusRepo;
import com.petopia.petopia.services.AuthenticationService;
import com.petopia.petopia.services.SPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SPServiceImpl implements SPService {

    private final AuthenticationService authenticationService;

    private final PetRepo petRepo;

    private final AppointmentRepo appointmentRepo;

    private final ServiceReportRepo serviceReportRepo;

    private final ServiceReportStatusRepo serviceReportStatusRepo;

    @Override
    public DraftReportResponse createDraftReport(DraftReportRequest request) {

        ServiceReportStatus draftingReport = serviceReportStatusRepo.findByStatus(Const.SERVICE_REPORT_STATUS_DRAFTING);

        Appointment appointment = appointmentRepo.findById(request.getAppointmentId()).orElse(null);
        if (appointment == null) {
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Không tìm thấy cuộc hẹn")
                    .build();
        }

        // kiem tra xem cuoc hen nay da co bao cao chua, neu da co thi thong bao rang cuoc hen nay da co bao cao
        ServiceReport existingReport = serviceReportRepo.findByAppointment_Id(request.getAppointmentId());
        if (existingReport != null) {
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Cuộc hẹn này đã có báo cáo")
                    .build();
        }

        ServiceReport draftReport = ServiceReport.builder()
                .appointment(appointment)
                .date(LocalDateTime.now())
                .report(request.getReport())
                .extraContent(request.getExtraContent())
                .location(appointment.getServiceProvider().getServiceCenter().getAddress())
                .serviceReportStatus(draftingReport)
                .build();

        serviceReportRepo.save(draftReport);
        if(serviceReportRepo.save(draftReport) == null) {
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Tạo báo cáo thất bại")
                    .build();
        }
        return DraftReportResponse.builder()
                .status("200")
                .message("Tạo báo cáo thành công")
                .detail(DraftReportResponse.DraftResponse.builder()
                        .ownerName(appointment.getPet().getUser().getAccount().getName())
                        .ownerAddress(appointment.getPet().getUser().getAddress())
                        .ownerPhone(appointment.getPet().getUser().getPhone())
                        .petName(appointment.getPet().getName())
                        .petAge(appointment.getPet().getAge())
                        .petGender(appointment.getPet().getGender())
                        .petType(appointment.getPet().getType())
                        .doctorName(appointment.getServiceProvider().getAccount().getName())
                        .date(appointment.getDate())
                        .report(draftReport.getReport())
                        .extraDetail(draftReport.getExtraContent())
                        .location(draftReport.getLocation())
                        .fee(appointment.getFee())
                        .build())
                .build();
    }

    @Override
    public DraftReportResponse editDraftReport(EditDraftReportRequest request) {
        ServiceReport existingDraftReport = serviceReportRepo.findById(request.getDraftReportId()).orElse(null);
        if (existingDraftReport == null) {
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Không tìm thấy báo cáo")
                    .detail(null)
                    .build();
        }
        existingDraftReport.setReport(request.getReport());
        existingDraftReport.setExtraContent(request.getExtraContent());
        existingDraftReport.setLocation(LocalDateTime.now().toString());
        ServiceReport saveReport = serviceReportRepo.save(existingDraftReport);

        if (saveReport == null) return DraftReportResponse.builder()
                .status("400")
                .message("Chỉnh sửa báo cáo thất bại")
                .detail(null)
                .build();

        return DraftReportResponse.builder()
                .status("200")
                .message("Chỉnh sửa báo cáo thành công")
                .detail(DraftReportResponse.DraftResponse.builder()
                        .ownerName(existingDraftReport.getAppointment().getPet().getUser().getAccount().getName())
                        .ownerAddress(existingDraftReport.getAppointment().getPet().getUser().getAddress())
                        .ownerPhone(existingDraftReport.getAppointment().getPet().getUser().getPhone())
                        .petName(existingDraftReport.getAppointment().getPet().getName())
                        .petAge(existingDraftReport.getAppointment().getPet().getAge())
                        .petGender(existingDraftReport.getAppointment().getPet().getGender())
                        .petType(existingDraftReport.getAppointment().getPet().getType())
                        .doctorName(existingDraftReport.getAppointment().getServiceProvider().getAccount().getName())
                        .date(existingDraftReport.getAppointment().getDate())
                        .report(existingDraftReport.getReport())
                        .extraDetail(existingDraftReport.getExtraContent())
                        .location(existingDraftReport.getLocation())
                        .fee(existingDraftReport.getAppointment().getFee())
                        .build())
                .build();
    }

    @Override
    public DraftReportResponse confirmDraftReport(ConfirmDraftReportRequest request) {

        ServiceReport existingDraftReport = serviceReportRepo.findById(request.getDraftReportId()).orElse(null);

        if (existingDraftReport == null) {
            return DraftReportResponse.builder()
                    .status("400")
                    .message("Không tìm thấy báo cáo")
                    .detail(null)
                    .build();
        }

        existingDraftReport.setServiceReportStatus(serviceReportStatusRepo.findByStatus(Const.SERVICE_REPORT_STATUS_CONFIRMED));

        serviceReportRepo.save(existingDraftReport);

        return DraftReportResponse.builder()
                .status("200")
                .message("Chỉnh sửa báo cáo thành công")
                .detail(DraftReportResponse.DraftResponse.builder()
                        .ownerName(existingDraftReport.getAppointment().getPet().getUser().getAccount().getName())
                        .ownerAddress(existingDraftReport.getAppointment().getPet().getUser().getAddress())
                        .ownerPhone(existingDraftReport.getAppointment().getPet().getUser().getPhone())
                        .petName(existingDraftReport.getAppointment().getPet().getName())
                        .petAge(existingDraftReport.getAppointment().getPet().getAge())
                        .petGender(existingDraftReport.getAppointment().getPet().getGender())
                        .petType(existingDraftReport.getAppointment().getPet().getType())
                        .doctorName(existingDraftReport.getAppointment().getServiceProvider().getAccount().getName())
                        .date(existingDraftReport.getAppointment().getDate())
                        .report(existingDraftReport.getReport())
                        .extraDetail(existingDraftReport.getExtraContent())
                        .location(existingDraftReport.getLocation())
                        .fee(existingDraftReport.getAppointment().getFee())
                        .build())
                .build();
    }


}
