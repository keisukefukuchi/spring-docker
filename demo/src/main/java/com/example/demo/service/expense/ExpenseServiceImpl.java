package com.example.demo.service.expense;

import com.example.demo.entity.Expense;
import com.example.demo.repository.ExpenseRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;

  @Autowired
  public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
    this.expenseRepository = expenseRepository;
  }

  @Override
  public List<Expense> getAllExpenses() {
    return expenseRepository.findAll();
  }

  @Override
  public Page<Expense> getExpensesByPage(int page, int size) {
    PageRequest pageRequest = PageRequest.of(
      page,
      size,
      Sort.by(Sort.Direction.DESC, "date")
    );
    return expenseRepository.findAll(pageRequest);
  }

  @Override
  public Expense getExpenseById(UUID id) {
    return expenseRepository.findById(id).orElse(null);
  }

  @Override
  public List<Expense> getExpenseByDate(int year, int month, int day) {
    return expenseRepository.findByDate(year, month, day);
  }

  @Override
  public int getTotalExpenseByMonth(int year, int month) {
    return expenseRepository.getTotalExpenseByMonth(year, month);
  }

  @Override
  public void saveExpense(Expense expense) {
    expense.setCreatedAt(LocalDate.now());
    expense.setUpdatedAt(LocalDate.now());
    expenseRepository.save(expense);
  }

  @Override
  public void deleteExpense(UUID id) {
    expenseRepository.deleteById(id);
  }
}
