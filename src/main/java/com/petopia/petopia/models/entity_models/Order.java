package com.petopia.petopia.models.entity_models;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "`status_id`")
    private OrderStatus orderStatus;

    @ManyToOne
    @JoinColumn(name = "`payment_method_id`")
    private PaymentMethod paymentMethod;

    @Column(name = "`order_date`")
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order")
    @ToString.Exclude
    private List<OrderDetail> orderDetailList;
}
