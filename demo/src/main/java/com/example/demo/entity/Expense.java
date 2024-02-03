package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "expenses")
public class Expense {

  @Id
  @Column(name = "expense_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID expenseId;

  @Column(name = "category_id")
  private UUID categoryId;

  @ManyToOne
  @JoinColumn(
    name = "category_id",
    referencedColumnName = "category_id",
    insertable = false,
    updatable = false
  )
  private Category category;

  @Column(name = "payment_type_id")
  private UUID paymentTypeId;

  @ManyToOne
  @JoinColumn(
    name = "payment_type_id",
    referencedColumnName = "payment_type_id",
    insertable = false,
    updatable = false
  )
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
