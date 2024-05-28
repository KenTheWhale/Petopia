package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`service_report`")
public class ServiceReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "`appointment_id`")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ServiceReportStatus serviceReportStatus;

    private LocalDateTime date;

    private String report;

    private String extraContent;

    private String location;
}
