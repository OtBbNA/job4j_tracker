package ru.job4j.gc.leak;

import java.util.List;

public class Post {

    private Integer id;

    private String text;

    private Comment comments;

    public Post(Integer id, String text, Comment comments) {
        this.id = id;
        this.text = text;
        this.comments = comments;
    }

    public Post(String text, Comment comments) {
        this.text = text;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}