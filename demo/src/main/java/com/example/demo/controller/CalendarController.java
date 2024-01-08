package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.PaymentType;
import com.example.demo.model.DailySummary;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.expense.ExpenseService;
import com.example.demo.service.paymentType.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class CalendarController {

    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final PaymentTypeService paymentTypeService;

    @Autowired
    public CalendarController(
            ExpenseService expenseService,
            CategoryService categoryService,
            PaymentTypeService paymentTypeService
    ) {
        this.expenseService = expenseService;
        this.categoryService = categoryService;
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("/")
    public String showCalendar(Model model) {
        model.addAttribute("expense", new Expense());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        return "calendar";
    }

    @PostMapping("/expense")
    public String addExpense(
            @ModelAttribute Expense expense,
            @RequestParam("categoryId") UUID categoryId,
            @RequestParam("paymentTypeId") UUID paymentTypeId
    ) {
        expense.setCategoryId(categoryId);
        expense.setPaymentTypeId(paymentTypeId);
        expenseService.saveExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/category")
    public String showCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category";
    }

    @PostMapping("/category")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/category";
    }

    @GetMapping("/payment-type")
    public String showPaymentType(Model model) {
        model.addAttribute("paymentType", new PaymentType());
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        return "paymentType";
    }

    @PostMapping("/payment-type")
    public String addPaymentType(@ModelAttribute PaymentType paymentType) {
        paymentTypeService.savePaymentType(paymentType);
        return "redirect:/payment-type";
    }
}

