package com.example.demo;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String brand;
    private String model;
    private String color;
    private String registerNumber;

    private int price;
    private int modelYear;

    @ManyToMany(mappedBy = "cars")
    private Set<Owner> owners = new HashSet<Owner>();

    public Car() {
    }

    public Car(String brand, String model, String color, String registerNumber, int modelYear,
               int price) {
        super();
        this.setBrand(brand);
        this.setModel(model);
        this.setColor(color);
        this.setPrice(price);
        this.setRegisterNumber(registerNumber);
        this.setModelYear(modelYear);
    }

}
