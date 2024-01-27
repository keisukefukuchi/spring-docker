package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.entity.PaymentType;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.expense.ExpenseService;
import com.example.demo.service.income.IncomeService;
import com.example.demo.service.paymentType.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {
    private final ExpenseService expenseService;
    private final IncomeService incomeService;
    private final CategoryService categoryService;
    private final PaymentTypeService paymentTypeService;

    @Autowired
    public RestApiController(
            ExpenseService expenseService,
            IncomeService incomeService,
            CategoryService categoryService,
            PaymentTypeService paymentTypeService
    ) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
        this.categoryService = categoryService;
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping(value = "/edit/expense/{expenseId}")
    public Map<String, Map<String, String>> getExpenseById(@PathVariable String expenseId) {
        Expense expenseById = expenseService.getExpenseById(UUID.fromString(expenseId));
        List<Category> categoryList = categoryService.getAllCategories();
        Map<String, String> categoryIdNameMap = new HashMap<>();
        for (Category category : categoryList) {
            String categoryIdString = category.getCategoryId().toString();
            categoryIdNameMap.put(categoryIdString, category.getName());
        }

        List<PaymentType> paymentTypeList = paymentTypeService.getAllPaymentTypes();
        Map<String, String>paymentTypeIdNameMap = new HashMap<>();
        for (PaymentType paymentType : paymentTypeList) {
            String paymentTypeIdString = paymentType.getPaymentTypeId().toString();
            paymentTypeIdNameMap.put(paymentTypeIdString, paymentType.getName());
        }

        Map<String, String> expenseMap = new HashMap<>();
        expenseMap.put("expenseId", String.valueOf(expenseById.getExpenseId()));
        expenseMap.put("date", String.valueOf(expenseById.getDate()));
        expenseMap.put("name", expenseById.getName());
        expenseMap.put("price", String.valueOf(expenseById.getPrice()));
        expenseMap.put("categoryName", expenseById.getCategory().getName());
        expenseMap.put("paymentTypeName", expenseById.getPaymentType().getName());

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("expenseDetails", expenseMap);
        result.put("categoryList", categoryIdNameMap);
        result.put("paymentTypeList", paymentTypeIdNameMap);

        return result;
    }

    @GetMapping(value = "/edit/income/{incomeId}")
    public Map<String, String> getIncomeById(@PathVariable String incomeId) {
        Income incomeById = incomeService.getIncomeById(UUID.fromString(incomeId));

        Map<String, String> incomeMap = new HashMap<>();
        incomeMap.put("incomeId", String.valueOf(incomeById.getIncomeId()));
        incomeMap.put("date", String.valueOf(incomeById.getDate()));
        incomeMap.put("name", incomeById.getName());
        incomeMap.put("price", String.valueOf(incomeById.getPrice()));

        return incomeMap;
    }
}
