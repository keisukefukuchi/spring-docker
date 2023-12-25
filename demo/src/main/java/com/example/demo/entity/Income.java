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
public class Income {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private UUID id;
    private int price;
    private String name;
    private LocalDate date;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
