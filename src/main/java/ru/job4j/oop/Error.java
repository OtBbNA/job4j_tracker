package ru.job4j.oop;

public class Error {

    private boolean active;
    private int status;
    private String message;

    public Error() {
    }

    public Error(boolean active, int status, String message) {
        this.active = active;
        this.message = message;
        this.status = status;
    }

    public void output() {
        System.out.println(active);
        System.out.println(status);
        System.out.println(message);
    }

    public static void main(String[] args) {
        Error error = new Error();
        error.output();
        Error error1 = new Error(true, 2, "Play");
        error1.output();
        Error error2 = new Error(true, 1, "Off");
        error2.output();
        Error error3 = new Error(false, 0, "DeBug");
        error3.output();
    }
}

