package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`service_report_status`")
public class ServiceReportStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @OneToMany(mappedBy = "serviceReportStatus")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ServiceReport> serviceReports;

}
