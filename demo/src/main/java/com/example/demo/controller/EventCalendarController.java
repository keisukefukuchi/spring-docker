package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.entity.PaymentType;
import com.example.demo.model.DailySummary;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.expense.ExpenseService;
import com.example.demo.service.income.IncomeService;
import com.example.demo.service.paymentType.PaymentTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
class EventCalendarController {

  private final ExpenseService expenseService;
  private final IncomeService incomeService;
  private final CategoryService categoryService;
  private final PaymentTypeService paymentTypeService;

  @Autowired
  public EventCalendarController(
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

  @GetMapping(value = "/all")
  public String getEvents() {
    String jsonMsg = null;
    try {
      List<DailySummary> events = new ArrayList<DailySummary>();
      List<Expense> expenseList = expenseService.getAllExpenses();
      Map<LocalDate, List<Expense>> expensesByDate = expenseList
        .stream()
        .collect(Collectors.groupingBy(Expense::getDate));
      List<List<Expense>> groupedExpenses = new ArrayList<>(
        expensesByDate.values()
      );
      for (List<Expense> group : groupedExpenses) {
        LocalDate date = group.get(0).getDate();
        int total = group.stream().mapToInt(Expense::getPrice).sum();
        DailySummary dailySummary = new DailySummary();
        dailySummary.setId("sum-expense");
        dailySummary.setStart(date.toString());
        dailySummary.setTitle("支出　" + total + "円");
        events.add(dailySummary);
      }
      List<Income> incomeList = incomeService.getAllIncomes();
      Map<LocalDate, List<Income>> incomesByDate = incomeList
        .stream()
        .collect(Collectors.groupingBy(Income::getDate));
      List<List<Income>> groupedIncomes = new ArrayList<>(
        incomesByDate.values()
      );
      for (List<Income> group : groupedIncomes) {
        LocalDate date = group.get(0).getDate();
        int total = group.stream().mapToInt(Income::getPrice).sum();
        DailySummary dailySummary = new DailySummary();
        dailySummary.setId("sum-income");
        dailySummary.setStart(date.toString());
        dailySummary.setTitle("収入　" + total + "円");
        events.add(dailySummary);
      }
      ObjectMapper mapper = new ObjectMapper();
      jsonMsg =
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return jsonMsg;
  }

  @GetMapping(value = "/data/{year}/{month}")
  public Map<String, Integer> getAmountByMonth(
    @PathVariable int year,
    @PathVariable int month
  ) {
    int totalIncome = incomeService.getTotalIncomeByMonth(year, month);
    int totalExpense = expenseService.getTotalExpenseByMonth(year, month);
    Map<String, Integer> result = new HashMap<>();
    result.put("totalIncome", totalIncome);
    result.put("totalExpense", totalExpense);
    return result;
  }

  @GetMapping(value = "/data/{year}/{month}/{day}")
  public List<Map<String, String>> getDataByDay(
    @PathVariable int year,
    @PathVariable int month,
    @PathVariable int day
  ) {
    List<Map<String, String>> result = new ArrayList<>();
    List<Income> theDayIncomeDataList = incomeService.getIncomeByDate(
      year,
      month,
      day
    );
    List<Expense> theDayExpenseDataList = expenseService.getExpenseByDate(
      year,
      month,
      day
    );
    if (!theDayIncomeDataList.isEmpty()) {
      for (Income theDayIncomeData : theDayIncomeDataList) {
        Map<String, String> incomeMap = new HashMap<>();
        incomeMap.put("type", "income");
        incomeMap.put("theDayIncomeName", theDayIncomeData.getName());
        incomeMap.put(
          "theDayIncomePrice",
          String.valueOf(theDayIncomeData.getPrice())
        );
        result.add(incomeMap);
      }
    } else {
      Map<String, String> incomeMap = new HashMap<>();
      incomeMap.put("type", "income");
      incomeMap.put("noMessage", "データはありません。");
      result.add(incomeMap);
    }

    if (!theDayExpenseDataList.isEmpty()) {
      for (Expense theDayExpenseData : theDayExpenseDataList) {
        Map<String, String> expenseMap = new HashMap<>();
        expenseMap.put("type", "expense");
        Category category = categoryService.getCategoryById(
          theDayExpenseData.getCategoryId()
        );
        PaymentType paymentType = paymentTypeService.getPaymentTypeById(
          theDayExpenseData.getPaymentTypeId()
        );
        expenseMap.put("theDayExpenseName", theDayExpenseData.getName());
        expenseMap.put(
          "theDayExpensePrice",
          String.valueOf(theDayExpenseData.getPrice())
        );
        expenseMap.put("theDayExpenseCategory", category.getName());
        expenseMap.put("theDayExpensePaymentType", paymentType.getName());
        result.add(expenseMap);
      }
    } else {
      Map<String, String> expenseMap = new HashMap<>();
      expenseMap.put("type", "expense");
      expenseMap.put("noMessage", "データはありません。");
      result.add(expenseMap);
    }
    return result;
  }
}
