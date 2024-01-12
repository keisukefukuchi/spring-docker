package com.example.demo.controller;

import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.model.DailySummary;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.expense.ExpenseService;
import com.example.demo.service.income.IncomeService;
import com.example.demo.service.paymentType.PaymentTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
class EventCalendarController {

    private final ExpenseService expenseService;
    private final IncomeService incomeService;


    @Autowired
    public EventCalendarController(
            ExpenseService expenseService,
            IncomeService incomeService
    ) {
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @GetMapping(value = "/all")
    public String getEvents() {
        String jsonMsg = null;
        try {
            List<DailySummary> events = new ArrayList<DailySummary>();

            // List<Expense> expenseList = expenseService.getExpensesByMonth(Month month);
            List<Expense> expenseList = expenseService.getAllExpenses();
            Map<LocalDate, List<Expense>> expensesByDate = expenseList.stream()
                    .collect(Collectors.groupingBy(Expense::getDate));
            List<List<Expense>> groupedExpenses = new ArrayList<>(expensesByDate.values());

            for (List<Expense> group : groupedExpenses) {
                LocalDate date = group.get(0).getDate();
                int total = group.stream()
                        .mapToInt(Expense::getPrice)
                        .sum();

                DailySummary dailySummary = new DailySummary();
                dailySummary.setId("sum-expense");
                dailySummary.setStart(date.toString());
                dailySummary.setTitle("支出　" + total + "円");
                events.add(dailySummary);
            }

            List<Income> incomeList = incomeService.getAllIncomes();
            Map<LocalDate, List<Income>> incomesByDate = incomeList.stream()
                    .collect(Collectors.groupingBy(Income::getDate));
            List<List<Income>> groupedIncomes = new ArrayList<>(incomesByDate.values());

            for (List<Income> group : groupedIncomes) {
                LocalDate date = group.get(0).getDate();
                int total = group.stream()
                        .mapToInt(Income::getPrice)
                        .sum();

                DailySummary dailySummary = new DailySummary();
                dailySummary.setId("sum-income");
                dailySummary.setStart(date.toString());
                dailySummary.setTitle("収入　" + total + "円");
                events.add(dailySummary);
            }

            // FullCalendarにエンコード済み文字列を渡す
            ObjectMapper mapper = new ObjectMapper();
            jsonMsg =  mapper.writerWithDefaultPrettyPrinter().writeValueAsString(events);
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
        // 該当月の収入合計を取得
        int totalIncome = incomeService.getTotalIncomeByMonth(year, month);

        // 該当月の支出合計を取得
        int totalExpense = expenseService.getTotalExpenseByMonth(year, month);

        // 合計金額を文字列として返す（ここで加工や他の処理を行う）
        System.out.println("Total Income: " + totalIncome + ", Total Expense: " + totalExpense);
        // JSON形式でデータを返す
        Map<String, Integer> result = new HashMap<>();
        result.put("totalIncome", totalIncome);
        result.put("totalExpense", totalExpense);

        return result;
    }
}
