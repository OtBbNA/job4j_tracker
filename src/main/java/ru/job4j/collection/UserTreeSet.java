package ru.job4j.collection;

import java.util.Objects;

public class UserTreeSet implements Comparable<UserTreeSet> {

    private String name;
    private int age;

    public UserTreeSet(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserTreeSet that = (UserTreeSet) o;
        return age == that.age && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public int compareTo(UserTreeSet another) {
        int status = this.name.compareTo(another.name);
        return status == 0 ? Integer.compare(this.age, another.age) : status;
    }
}
