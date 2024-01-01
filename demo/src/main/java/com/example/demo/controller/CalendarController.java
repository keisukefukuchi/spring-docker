package com.example.demo.controller;

import com.example.demo.entity.SearchForm;
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

    @GetMapping("/calendar")
    public String showCalendar(Model model) {
        model.addAttribute("searchForm", new SearchForm());
        return populateCalendar(model, YearMonth.now());
    }

    @PostMapping("/calendar")
    public String searchCalendar(SearchForm searchForm, Model model) {
        YearMonth yearMonth = YearMonth.parse(searchForm.getYearMonth().toString());
        return populateCalendar(model, yearMonth);
    }

    @PostMapping("/expense")
    public String handleDateClick(@RequestParam String selectedDate) {
        // 日付が正常に受信されたときの処理
        System.out.println("クリックされた日付: " + selectedDate);

        // ここで必要な処理を追加

        return "expense";
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

