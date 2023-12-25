package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID id;
    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "id")
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