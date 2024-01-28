package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.entity.Expense;
import com.example.demo.entity.Income;
import com.example.demo.entity.PaymentType;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.expense.ExpenseService;
import com.example.demo.service.income.IncomeService;
import com.example.demo.service.paymentType.PaymentTypeService;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CalendarController {

  private final ExpenseService expenseService;
  private final IncomeService incomeService;
  private final CategoryService categoryService;
  private final PaymentTypeService paymentTypeService;

  @Autowired
  public CalendarController(
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

  @GetMapping("/")
  public String showCalendar(Model model) {
    model.addAttribute("expense", new Expense());
    model.addAttribute("categories", categoryService.getAllCategories());
    model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
    return "calendar";
  }

  @GetMapping("/expense")
  public String showExpense(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "8") int size
  ) {
    Page<Expense> expensePage = expenseService.getExpensesByPage(page, size);
    model.addAttribute("expense", new Expense());
    model.addAttribute("expenses", expensePage.getContent());
    model.addAttribute("categories", categoryService.getAllCategories());
    model.addAttribute("paymentTypes", paymentTypeService.getAllPaymentTypes());
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", expensePage.getTotalPages());
    return "expense";
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
    return "redirect:/expense";
  }

  @PostMapping("/edit/expense/{expenseId}")
  public String editExpense(
    @PathVariable String expenseId,
    @RequestParam(name = "editExpenseDate") LocalDate editExpenseDate,
    @RequestParam(name = "editName") String editName,
    @RequestParam(name = "editExpenseCategoryId") String editExpenseCategoryId,
    @RequestParam(
      name = "editExpensePaymentTypeId"
    ) String editExpensePaymentTypeId,
    @RequestParam(name = "editExpenseAmount") Integer editExpenseAmount
  ) {
    Expense expense = expenseService.getExpenseById(UUID.fromString(expenseId));
    expense.setDate(editExpenseDate);
    expense.setName(editName);
    expense.setCategoryId(UUID.fromString(editExpenseCategoryId));
    expense.setPaymentTypeId(UUID.fromString(editExpensePaymentTypeId));
    expense.setPrice(editExpenseAmount);
    expenseService.saveExpense(expense);
    return "redirect:/expense";
  }

  @PostMapping("/delete/expense/{expenseId}")
  public String deleteExpense(
    @PathVariable String expenseId
  ) {
    expenseService.deleteExpense(UUID.fromString(expenseId));
    return "redirect:/expense";
  }

  @GetMapping("/income")
  public String showIncome(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "8") int size
  ) {
    Page<Income> incomePage = incomeService.getIncomesByPage(page, size);
    model.addAttribute("income", new Income());
    model.addAttribute("incomes", incomePage.getContent());
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", incomePage.getTotalPages());
    return "income";
  }

  @PostMapping("/income")
  public String addIncome(@ModelAttribute Income income) {
    incomeService.saveIncome(income);
    return "redirect:/income";
  }

  @PostMapping("/edit/income/{incomeId}")
  public String editIncome(
    @PathVariable String incomeId,
    @RequestParam(name = "editIncomeDate") LocalDate editIncomeDate,
    @RequestParam(name = "editName") String editName,
    @RequestParam(name = "editIncomeAmount") Integer editIncomeAmount
  ) {
    Income income = incomeService.getIncomeById(UUID.fromString(incomeId));
    income.setDate(editIncomeDate);
    income.setName(editName);
    income.setPrice(editIncomeAmount);
    incomeService.saveIncome(income);
    return "redirect:/income";
  }

  @PostMapping("/delete/income/{incomeId}")
  public String deleteIncome(
          @PathVariable String incomeId
  ) {
    incomeService.deleteIncome(UUID.fromString(incomeId));
    return "redirect:/income";
  }

  @GetMapping("/category")
  public String showCategory(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "8") int size
  ) {
    Page<Category> categoryPage = categoryService.getCategoriesByPage(
      page,
      size
    );
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", categoryPage.getTotalPages());
    model.addAttribute("category", new Category());
    model.addAttribute("categories", categoryPage.getContent());
    return "category";
  }

  @PostMapping("/category")
  public String addCategory(@ModelAttribute Category category) {
    categoryService.saveCategory(category);
    return "redirect:/category";
  }

  @PostMapping("/edit/category/{categoryId}")
  public String editCategory(
    @PathVariable String categoryId,
    @RequestParam(name = "editCategoryName") String editCategoryName
  ) {
    Category category = categoryService.getCategoryById(
      UUID.fromString(categoryId)
    );
    category.setName(editCategoryName);
    categoryService.saveCategory(category);
    return "redirect:/category";
  }

  @GetMapping("/payment-type")
  public String showPaymentType(
    Model model,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "8") int size
  ) {
    Page<PaymentType> paymentTypePage = paymentTypeService.getpaymentTypesByPage(
      page,
      size
    );
    model.addAttribute("currentPage", page);
    model.addAttribute("size", size);
    model.addAttribute("totalPages", paymentTypePage.getTotalPages());
    model.addAttribute("paymentType", new PaymentType());
    model.addAttribute("paymentTypes", paymentTypePage.getContent());
    return "paymentType";
  }

  @PostMapping("/payment-type")
  public String addPaymentType(@ModelAttribute PaymentType paymentType) {
    paymentTypeService.savePaymentType(paymentType);
    return "redirect:/payment-type";
  }

  @PostMapping("/edit/payment-type/{paymentTypeId}")
  public String editPaymentType(
    @PathVariable String paymentTypeId,
    @RequestParam(name = "editPaymentTypeName") String editPaymentTypeName
  ) {
    PaymentType paymentType = paymentTypeService.getPaymentTypeById(
      UUID.fromString(paymentTypeId)
    );
    paymentType.setName(editPaymentTypeName);
    paymentTypeService.savePaymentType(paymentType);
    return "redirect:/payment-type";
  }
}
