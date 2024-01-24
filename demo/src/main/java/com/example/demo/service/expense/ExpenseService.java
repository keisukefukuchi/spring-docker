package com.example.demo.service.expense;

// ExpenseService.java
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense getExpenseById(UUID id);

//    List<Expense> getExpensesByMonth(Month month);
    int getTotalExpenseByMonth(int year, int month);

    List<Expense> getExpenseByDate(int year, int month, int day);

    void saveExpense(Expense expense);

    void deleteExpense(UUID id);
}
