package ru.job4j.gc.leak;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface Generate  {

    void generate();

    default List<String> read(String path) throws IOException {
        List<String> text = new ArrayList<>();
        try (BufferedReader files = new BufferedReader(new FileReader(path, Charset.forName("UTF-8")))) {
            text = files.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}