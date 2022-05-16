package ru.job4j.bank;

import java.util.Objects;

/**
 * Модель данных описывает пользователя банка
 * @author Anton
 * @version 1.0
 */
public class User {

    /**
     * Поле содержит уникальное значение поспорта пользователя
     */
    private String passport;
    /**
     * Поле содержит имя пользователя
     */
    private String username;

    /**
     * Конструктор при создании объекта User автоматически инициализирует поля
     * @param passport
     * @param username
     */
    public User(String passport, String username) {
        this.passport = passport;
        this.username = username;
    }

    /**
     * Геттер поля passport
     * @return возвращает значение поля passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * Сеттер поля passport
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * Геттер поля username
     * @return возвращает значение поля username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Сеттер поля username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Переопределения метода equals для корректного сравнения двух объектов User
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(passport, user.passport);
    }

    /**
     * Переопределение метода HashCode для корректной работы коллекции HashMap
     */
    @Override
    public int hashCode() {
        return Objects.hash(passport);
    }
}
