package ru.job4j.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Card {

    private Suit suit;
    private Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public static void main(String[] args) {
        Stream.of(Value.values())
                .flatMap(value -> Stream.of(Suit.values())
                    .map(suit -> new Card(suit, value)))
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return value + " " + suit;
    }
}
