package ru.job4j.gc.leak;

import java.io.IOException;

public class CommentGenerator implements Generate<Comment> {

    public static final String PATH_PHRASES = "src/main/java/ru/job4j/gc/leak/files/phrases.txt";

    public static final String SEPARATOR = System.lineSeparator();
    private UserGenerator userGenerator;

    public CommentGenerator(UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
    }

    @Override
    public Comment generate() throws IOException {
        String comment = String.format("%s%s%s%s%s",
                read(PATH_PHRASES), SEPARATOR,
                read(PATH_PHRASES), SEPARATOR,
                read(PATH_PHRASES));
        return new Comment(comment, userGenerator.generate());
    }
}