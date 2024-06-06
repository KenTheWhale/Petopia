package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`time_slot`")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`center_id`")
    private ServiceCenter serviceCenter;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private TimeSlotStatus timeSlotStatus;

    private String name;

    @Column(name = "`start_time`")
    private LocalTime startTime;

    @Column(name = "`end_time`")
    private LocalTime endTime;


}
