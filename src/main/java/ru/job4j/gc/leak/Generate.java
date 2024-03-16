package ru.job4j.gc.leak;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface Generate<T> {

    Random RANDOM = new Random();

    T generate() throws IOException;

    default String read(String path) throws IOException {
        List<String> text = new ArrayList<>();
        Files.lines(Paths.get(path))
                .forEach(text::add);
        return text.get(RANDOM.nextInt(text.size()));
    }
}