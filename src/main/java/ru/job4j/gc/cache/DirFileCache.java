package ru.job4j.gc.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        List<String> list = new ArrayList<>();
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader files = new BufferedReader(new FileReader(String.format("%s/%s", cachingDir, key), Charset.forName("UTF-8")))) {
            list = files.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s: list) {
            rsl.append(s).append(" ");
        }
        return rsl.toString();
    }
}
