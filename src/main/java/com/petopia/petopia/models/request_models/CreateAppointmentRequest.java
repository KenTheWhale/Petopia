package com.petopia.petopia.models.request_models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAppointmentRequest {

    private Integer petId;

    private Integer centerId;

    private String substituteName;

    private String substitutePhone;

    private String extraInformation;

    private boolean onSite;

    private String phone;

    private LocalDate date;

    private Integer slotId;

    private List<Integer> serviceId;

}
