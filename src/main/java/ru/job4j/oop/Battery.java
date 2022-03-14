package ru.job4j.oop;

public class Battery {

    private int load;

    public Battery(int load) {
        this.load = load;
    }

    public void exchange(Battery another) {
        this.load = another.load + this.load;
        another.load = 0;
    }

    public int getLoad() {
        return load;
    }
}
