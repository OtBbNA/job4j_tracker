package ru.job4j.oop;

public class Calculator {

    private static int x = 5;

    public static int sum(int b) {
        return x + b;
    }

    public static int minus(int c) {
        return x + c;
    }

    public double divide(int g) {
        double d = g;
        return d / x;
    }

    public int multiply(int a) {
        return x * a;
    }

    public double  sumAllOperation(int y) {
        return sum(y) + minus(y) + divide(y) + multiply(y);
    }

    public static void main(String[] args) {
        Calculator calculatormult = new Calculator();
        int rslmult = calculatormult.multiply(5);
        Calculator calculatordiv = new Calculator();
        double rsldiv = calculatordiv.divide(2);
        Calculator calculatorAll = new Calculator();
        double rslAll = calculatorAll.sumAllOperation(2);
        int rslsum = sum(5);
        int rslmin = minus(2);
        System.out.println(rslmult);
        System.out.println(rsldiv);
        System.out.println(rslsum);
        System.out.println(rslmin);
        System.out.println(rslAll);
    }
}

