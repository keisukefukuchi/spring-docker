package com.example.demo.service.income;

// IncomeServiceImpl.java
import com.example.demo.entity.Income;
import com.example.demo.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    public Income getIncomeById(UUID id) {
        return incomeRepository.findById(id).orElse(null);
    }

    @Override
    public void saveIncome(Income income) {
        // 作成日時と更新日時を設定
        income.setCreatedAt(LocalDate.now());
        income.setUpdatedAt(LocalDate.now());

        incomeRepository.save(income);
    }

    @Override
    public void deleteIncome(UUID id) {
        incomeRepository.deleteById(id);
    }
}
