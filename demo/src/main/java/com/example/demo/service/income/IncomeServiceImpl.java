package com.example.demo.service.income;

import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.repository.IncomeRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class IncomeServiceImpl implements IncomeService {

  private final IncomeRepository incomeRepository;

  @Autowired
  public IncomeServiceImpl(IncomeRepository incomeRepository) {
    this.incomeRepository = incomeRepository;
  }

  @Override
  public List<Income> getAllIncomes() {
    return incomeRepository.findAll();
  }

  @Override
  public Page<Income> getIncomesByPage(int page, int size) {
    PageRequest pageRequest = PageRequest.of(
      page,
      size,
      Sort.by(Sort.Direction.DESC, "date")
    );
    return incomeRepository.findAll(pageRequest);
  }

  @Override
  public Income getIncomeById(UUID id) {
    return incomeRepository.findById(id).orElse(null);
  }

  @Override
  public List<Income> getIncomeByDate(int year, int month, int day) {
    return incomeRepository.findByDate(year, month, day);
  }

  @Override
  public void saveIncome(Income income) {
    income.setCreatedAt(LocalDate.now());
    income.setUpdatedAt(LocalDate.now());

    incomeRepository.save(income);
  }

  @Override
  public int getTotalIncomeByMonth(int year, int month) {
    return incomeRepository.getTotalIncomeByMonth(year, month);
  }

  @Override
  public void deleteIncome(UUID id) {
    incomeRepository.deleteById(id);
  }
}
