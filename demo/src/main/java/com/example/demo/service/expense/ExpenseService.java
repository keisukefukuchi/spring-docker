package com.example.demo.service.expense;

// ExpenseService.java
import com.example.demo.entity.Expense;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

public interface ExpenseService {
    List<Expense> getAllExpenses();

    Expense getExpenseById(UUID id);

//    List<Expense> getExpensesByMonth(Month month);


    void saveExpense(Expense expense);

    void deleteExpense(UUID id);
}
