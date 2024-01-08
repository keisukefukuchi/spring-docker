package com.example.demo.model;

import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DailySummary implements Serializable {
    private String id;
    private String title;
    private String start;
    private String end;
}
