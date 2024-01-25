package com.example.demo.repository;

import com.example.demo.entity.Income;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeRepository extends JpaRepository<Income, UUID> {
  @Query(
    "SELECT COALESCE(SUM(i.price), 0) FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month"
  )
  int getTotalIncomeByMonth(@Param("year") int year, @Param("month") int month);

  @Query(
    "SELECT i FROM Income i WHERE YEAR(i.date) = :year AND MONTH(i.date) = :month AND DAY(i.date) = :day"
  )
  List<Income> findByDate(
    @Param("year") int year,
    @Param("month") int month,
    @Param("day") int day
  );
}
