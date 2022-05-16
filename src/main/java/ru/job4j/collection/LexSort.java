package ru.job4j.collection;

import java.util.Comparator;

public class LexSort implements Comparator<String> {

    @Override
    public int compare(String left, String right) {
        String[] leftList = left.split("\\.");
        String[] rightList = right.split("\\.");
        return Integer.compare(Integer.parseInt(leftList[0]), Integer.parseInt(rightList[0]));
    }
}
