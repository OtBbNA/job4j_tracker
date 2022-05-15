package ru.job4j.collection;

import java.util.ArrayList;
import java.util.Comparator;

public class StringCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int rsl = 0;
        int size = left.length();
        if (size > right.length()) {
            size = right.length();
        }
        for (int i = 0; i < size; i++) {
            if (left.charAt(i) != right.charAt(i)) {
                    rsl = Character.compare(left.charAt(i), right.charAt(i));
                    break;
            }
        }
        return rsl != 0 ? rsl : Integer.compare(left.length(), right.length());
    }
}
