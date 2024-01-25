package com.example.demo.service.expense;

import com.example.demo.entity.Expense;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ExpenseService {
  List<Expense> getAllExpenses();

  Page<Expense> getExpensesByPage(int page, int size);

  Expense getExpenseById(UUID id);

  int getTotalExpenseByMonth(int year, int month);

  List<Expense> getExpenseByDate(int year, int month, int day);

  void saveExpense(Expense expense);

  void deleteExpense(UUID id);
}
