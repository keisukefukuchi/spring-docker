package com.example.demo.service.income;

// IncomeService.java
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IncomeService {
    List<Income> getAllIncomes();

    Page<Income> getIncomesByPage(int page, int size);

    Income getIncomeById(UUID id);

    int getTotalIncomeByMonth(int year, int month);

    List<Income> getIncomeByDate(int year, int month, int day);

    void saveIncome(Income income);

    void deleteIncome(UUID id);
}
