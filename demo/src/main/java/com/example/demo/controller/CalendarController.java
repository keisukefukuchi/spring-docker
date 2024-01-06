package com.example.demo.controller;

import com.example.demo.entity.SearchForm;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.paymentType.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {

    private final CategoryService categoryService;
    private final PaymentTypeService paymentTypeService;

    @Autowired
    public CalendarController(
            CategoryService categoryService,
            PaymentTypeService paymentTypeService
    ) {
        this.categoryService = categoryService;
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping("/")
    public String showCalendar(Model model) {
        return "calendar";
    }

    @GetMapping("/expense")
    public String handleDateClick(@RequestParam String selectedDate, Model model) {
        // 日付が正常に受信されたときの処理
        System.out.println("受け取ったデータ: " + selectedDate);
        // ページに表示するデータを設定
        System.out.println(categoryService.getAllCategories());
        model.addAttribute("selectedDate", selectedDate);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        return "expense";
    }
}

