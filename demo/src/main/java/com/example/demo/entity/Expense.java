package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "paymentType_id", referencedColumnName = "id")
    private PaymentType PaymentType;

    private int price;
    private String name;
    private LocalDate date;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}