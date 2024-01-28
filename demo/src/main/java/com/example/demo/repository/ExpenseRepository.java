package com.example.demo.repository;

import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
  @Query(
    "SELECT COALESCE(SUM(e.price), 0) FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month"
  )
  int getTotalExpenseByMonth(
    @Param("year") int year,
    @Param("month") int month
  );

  @Query(
    "SELECT e FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month AND DAY(e.date) = :day"
  )
  List<Expense> findByDate(
    @Param("year") int year,
    @Param("month") int month,
    @Param("day") int day
  );

  void deleteByCategoryId(UUID categoryId);
}
