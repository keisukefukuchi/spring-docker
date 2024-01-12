package com.example.demo.service.income;

// IncomeService.java
import com.example.demo.entity.Income;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface IncomeService {
    List<Income> getAllIncomes();

    Income getIncomeById(UUID id);

    int getTotalIncomeByMonth(int year, int month);

    void saveIncome(Income income);

    void deleteIncome(UUID id);
}
