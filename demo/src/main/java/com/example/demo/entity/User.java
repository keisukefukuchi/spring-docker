package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
