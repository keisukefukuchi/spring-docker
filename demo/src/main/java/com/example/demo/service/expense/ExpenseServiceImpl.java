package com.example.demo.service.expense;

// ExpenseServiceImpl.java
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.repository.ExpenseRepository;
import com.example.demo.service.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));
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

//    @Override
//    public List<Expense> getExpensesByMonth(Month month) {
//        return expenseRepository.findByDateYearMonth(month);
//    }

    @Override
    public int getTotalExpenseByMonth(int year, int month) {
        return expenseRepository.getTotalExpenseByMonth(year,month);
    }

    @Override
    public void saveExpense(Expense expense) {
        // Set createdAt and updatedAt
        expense.setCreatedAt(LocalDate.now());
        expense.setUpdatedAt(LocalDate.now());
        expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }
}
