package ru.job4j.early;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertThrows;

public class PasswordValidatorTest {

    @Test
    public void whenNoPassword() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("");
                });
    }

    @Test
    public void whenPasswordIsShorterThan8Char() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("Haru6");
                });
        assertThat(exception.getMessage()).isEqualTo("Password length must be between 8 and 32 characters");
    }

    @Test
    public void whenPasswordIsMoreThan32Char() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("1111111111111111-Haru6aTop-1111111111111111");
                });

    }

    @Test
    public void whenPasswordDnotContainsLowercaseChar() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("11-HARU6ATOP-11");
                });
        assertThat(exception.getMessage()).isEqualTo("Password must contain lower characters");
    }

    @Test
    public void whenPasswordDnotContainsUppercaseChar() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("11-haru6atop-11");
                });
        assertThat(exception.getMessage()).isEqualTo("Password must contain upper characters");
    }

    @Test
    public void whenPasswordDnotContainsDigitChar() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("-HarubaTop-");
                });
        assertThat(exception.getMessage()).isEqualTo("Password must contain numbers");
    }

    @Test
    public void whenPasswordDnotContainsSpecSymbols() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("11Haru6aTop11");
                });
        assertThat(exception.getMessage()).isEqualTo("Password must contain at least one special character:"
                + "! \" # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _` { | } ~");
    }

    @Test
    public void whenPasswordContainsQWERTY() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("Haru6aTop_qWeRtY");
                });
        assertThat(exception.getMessage()).isEqualTo("The password must not contain the substrings "
                + "\"12345\", \"qwerty\", \"admin\" and etc. Take care "
                + "of the security of your account!");
    }

    @Test
    public void whenPasswordContains12345() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    PasswordValidator.validate("Haru6aTop_12345");
                });
        assertThat(exception.getMessage()).isEqualTo("The password must not contain the substrings "
                + "\"12345\", \"qwerty\", \"admin\" and etc. Take care "
                + "of the security of your account!");
    }

    @Test
    public void whenPasswordIsOK() {
        String password = "11-Haru6aTop-11";
        String expected = "Go to next step";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }
}