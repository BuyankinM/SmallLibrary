package ru.buyankin.spring.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Reader {
    private int id;

    @NotEmpty(message = "Name should not be empty!")
    @Size(min = 20, max = 60, message = "Name should be between 20 and 60 characters")
    private String name;

    @Min(value = 1900, message = "Birth year should be greater than 1900")
    private int birthYear;

    public Reader(int id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Reader() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
