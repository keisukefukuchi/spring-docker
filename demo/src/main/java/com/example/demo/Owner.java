package com.example.demo;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ownerId;
    private String firstname, lastname;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "car_owner", joinColumns = {
            @JoinColumn(name = "ownerid")}, inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<Car> cars = new HashSet<Car>();

    public Owner() {
    }

    public Owner(String firstname, String lastname) {
        super();
        this.firstname = firstname;
        this.lastname = lastname;
    }
}