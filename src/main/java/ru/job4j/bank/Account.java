package ru.job4j.bank;

import java.util.Objects;

/**
 * Модель данных описывает счет в банке
 * @author Anton
 * @version 1.0
 */
public class Account {

    /**
     * Поле содержит уникальное значение реквизита счета
     */
    private String requisite;

    /**
     * Поле содержит количество средств на счету
     */
    private double balance;

    /**
     * Конструктор при создании объекта Account автоматически инициализирует поля
     * @param balance
     * @param requisite
     */
    public Account(String requisite, double balance) {
        this.requisite = requisite;
        this.balance = balance;
    }

    /**
     * Геттер поля requisite
     * @return возвращает значение поля requisite
     */
    public String getRequisite() {
        return requisite;
    }

    /**
     * Сеттер поля requisite
     */
    public void setRequisite(String requisite) {
        this.requisite = requisite;
    }

    /**
     * Геттер поля balance
     * @return возвращает значение поля balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Сеттер поля balance
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Переопределения метода equals для корректного сравнения двух объектов Account
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(requisite, account.requisite);
    }

    /**
     * Переопределение метода HashCode для корректной работы коллекции HashMap
     */
    @Override
    public int hashCode() {
        return Objects.hash(requisite);
    }
}
