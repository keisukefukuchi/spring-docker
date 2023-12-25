package com.example.demo.repository;

import com.example.demo.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
}