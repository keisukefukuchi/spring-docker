package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "incomes")
public class Income {

  @Id
  @Column(name = "income_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID incomeId;

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
