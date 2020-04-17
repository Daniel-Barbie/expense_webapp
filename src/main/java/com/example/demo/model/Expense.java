package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public class Expense {

    private final UUID id;
    @NotBlank
    private final String name;
    private final double amount;
    private final UUID userid;
    private final LocalDate date;

    public Expense(@JsonProperty("id") UUID id,
                   @JsonProperty("name") String name,
                   @JsonProperty("amount") double amount,
                   @JsonProperty("userid") UUID userid,
                   @JsonProperty("date") LocalDate date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.userid = userid;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public UUID getUserid() {
        return userid;
    }

    public LocalDate getDate() {
        return date;
    }
}
