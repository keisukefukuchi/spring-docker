package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "payment_types")
public class PaymentType {
    @Id
    @Column(name = "payment_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
