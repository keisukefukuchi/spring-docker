package com.example.demo.controller;

import com.example.demo.entity.SearchForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        model.addAttribute("searchForm", new SearchForm());
        return populateCalendar(model, YearMonth.now());
    }

    @PostMapping("/calendar")
    public String searchCalendar(SearchForm searchForm, Model model) {
        YearMonth yearMonth = YearMonth.parse(searchForm.getYearMonth().toString());
        System.out.println(yearMonth);
        return populateCalendar(model, yearMonth);
    }

    private String populateCalendar(Model model, YearMonth yearMonth) {
        // カレンダーの日付をリストに追加
        List<String> dates = new ArrayList<>();
        LocalDate firstDay = yearMonth.atDay(1);
        LocalDate lastDay = yearMonth.atEndOfMonth();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (!firstDay.isAfter(lastDay)) {
            dates.add(firstDay.format(formatter));
            firstDay = firstDay.plusDays(1);
        }

        model.addAttribute("yearMonth", yearMonth);
        model.addAttribute("dates", dates);
        return "calendar";
    }
}

