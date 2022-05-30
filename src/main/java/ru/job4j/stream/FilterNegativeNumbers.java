package ru.job4j.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterNegativeNumbers {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(6, -4, -5, 0, -3, 10, 6, 2, -10, 1, 3);
        List<Integer> positive = numbers.stream().filter(numb -> numb > 0).collect(Collectors.toList());
        positive.forEach(System.out::println);
    }
}