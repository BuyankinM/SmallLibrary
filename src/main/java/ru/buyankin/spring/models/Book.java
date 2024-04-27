package ru.buyankin.spring.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {

    private int id;
    private Integer reader_id;

    @NotEmpty(message = "Title should not be empty!")
    private String title;

    @NotEmpty(message = "Author should not be empty!")
    private String author;

    @Min(value = 1, message = "Year should be greater than 0")
    private int year;

    public Book(int id, Integer reader_id, String title, String author, int year) {
        this.id = id;
        this.reader_id = reader_id;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getReader_id() {
        return reader_id;
    }

    public void setReader_id(Integer reader_id) {
        this.reader_id = reader_id;
    }
}
