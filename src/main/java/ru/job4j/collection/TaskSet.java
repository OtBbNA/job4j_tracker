package ru.job4j.collection;

public class TaskSet {
    private String number;
    private String description;

    public TaskSet(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }
}
