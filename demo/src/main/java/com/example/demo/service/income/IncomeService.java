package com.example.demo.service.income;

// IncomeService.java
import com.example.demo.entity.Income;

import java.util.List;
import java.util.UUID;

public interface IncomeService {
    List<Income> getAllIncomes();

    Income getIncomeById(UUID id);

    void saveIncome(Income income);

    void deleteIncome(UUID id);
}
