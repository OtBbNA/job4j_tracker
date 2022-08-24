package ru.job4j.early;

import java.util.Arrays;
import java.util.Locale;

public class PasswordValidator {

    public static String validate(String password) {
        int passLength = password.length();
        if (password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (passLength < 8 || passLength > 32) {
            return "Password length must be between 8 and 32 characters";
        }
        boolean passDigit = false;
        boolean passLowerCase = false;
        boolean passUpperCase = false;
        boolean passSpecSymbol = false;
        char[] passChar = password.toCharArray();
        for (int i = 1; i < passLength; i++) {
            if (Character.isDigit(passChar[i]) && !passDigit) {
                passDigit = true;
            }
            if (Character.isLowerCase(passChar[i]) && !passLowerCase) {
                passLowerCase = true;
            }
            if (Character.isUpperCase(passChar[i]) && !passUpperCase) {
                passUpperCase = true;
            }
            if ((PasswordValidator.unicodeValidate(passChar[i], 33, 47)
                    || PasswordValidator.unicodeValidate(passChar[i], 58, 64)
                    || PasswordValidator.unicodeValidate(passChar[i], 91, 96)
                    || PasswordValidator.unicodeValidate(passChar[i], 123, 126))
                    && !passSpecSymbol) {
                passSpecSymbol = true;
            }
        }
        if (!passDigit) {
            return "Password must contain numbers";
        }
        if (!passLowerCase) {
            return "Password must contain lower characters";
        }
        if (!passUpperCase) {
            return "Password must contain upper characters";
        }
        if (!passSpecSymbol) {
            return "Password must contain at least one special character:"
                    + "! \" # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _` { | } ~";
        }
        String lowerCharPass = password.toLowerCase(Locale.ROOT);
        if (lowerCharPass.contains("qwerty") || lowerCharPass.contains("12345")
                || lowerCharPass.contains("password") || lowerCharPass.contains("admin")
                || lowerCharPass.contains("user")) {
            return ("The password must not contain the substrings "
                    + "\"12345\", \"qwerty\", \"admin\" and etc. Take care "
                    + "of the security of your account!");
        }
        return "Go to next step";
    }

    public static boolean unicodeValidate(char symbol, int left, int right) {
        return symbol >= left && symbol <= right;
    }
}