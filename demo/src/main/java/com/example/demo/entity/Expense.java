package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "expenses")
public class Expense {
    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID expenseId;

    @ManyToOne()
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @ManyToOne()
    @JoinColumn(name = "payment_type_id", referencedColumnName = "payment_type_id")
    private PaymentType paymentType;

    @Column(name = "price")
    private int price;

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}