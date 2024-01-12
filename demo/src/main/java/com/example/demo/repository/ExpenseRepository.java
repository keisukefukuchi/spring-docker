package com.example.demo.repository;

import com.example.demo.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
//    List<Expense> findByDateYearMonth(Month month);

    @Query("SELECT COALESCE(SUM(e.price), 0) FROM Expense e WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month")
    int getTotalExpenseByMonth(@Param("year") int year, @Param("month") int month);
}
