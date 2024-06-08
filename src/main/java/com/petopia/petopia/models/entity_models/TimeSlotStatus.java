package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`time_slot_status`")
public class TimeSlotStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @OneToMany(mappedBy = "timeSlotStatus")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<TimeSlot> timeSlotList;
}
