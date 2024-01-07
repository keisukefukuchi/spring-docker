package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.PaymentType;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.paymentType.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

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
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
        return "calendar";
    }

    @PostMapping("/expense")
    public String handleDateClick() {
        return "aaaa";
    }

    @GetMapping("/category")
    public String showCategory(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category";
    }

    @PostMapping("/category")
    public String addCategory(@ModelAttribute Category category) {
        category.setCreatedAt(LocalDate.now());
        category.setUpdatedAt(LocalDate.now());
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
        paymentType.setCreatedAt(LocalDate.now());
        paymentType.setUpdatedAt(LocalDate.now());
        paymentTypeService.savePaymentType(paymentType);
        return "redirect:/payment-type";
    }
}

