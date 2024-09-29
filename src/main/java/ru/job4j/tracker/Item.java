package ru.job4j.tracker;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Item implements Comparable<Item> {

    private int id;
    private String name;
    private LocalDateTime created = LocalDateTime.now();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-EEEE-yyyy HH:mm:ss");

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Item(int id, String name, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.created = created.withNano(0);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id = " + id
                + ", name = '" + name + '\''
                + ", created: " + created.format(FORMATTER)
                + '}';
    }

    @Override
    public int compareTo(Item another) {
        return String.CASE_INSENSITIVE_ORDER.compare(name, another.name);
    }
}