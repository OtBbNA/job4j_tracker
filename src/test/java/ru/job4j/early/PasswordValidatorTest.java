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
        String password = "Haru6";
        String expected = "Password length must be between 8 and 32 characters";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordIsMoreThan32Char() {
        String password = "1111111111111111-Haru6aTop-1111111111111111";
        String expected = "Password length must be between 8 and 32 characters";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordDnotContainsLowercaseChar() {
        String password = "11-HARU6ATOP-11";
        String expected = "Password must contain lower characters";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordDnotContainsUppercaseChar() {
        String password = "11-haru6atop-11";
        String expected = "Password must contain upper characters";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordDnotContainsDigitChar() {
        String password = "-HarubaTop-";
        String expected = "Password must contain numbers";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordDnotContainsSpecSymbols() {
        String password = "11Haru6aTop11";
        String expected = "Password must contain at least one special character:"
                + "! \" # $ % & ' ( ) * + , - . / : ; < = > ? @ [ \\ ] ^ _` { | } ~";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordContainsQWERTY() {
        String password = "Haru6aTop_qWeRtY";
        String expected = "The password must not contain the substrings "
                + "\"12345\", \"qwerty\", \"admin\" and etc. Take care "
                + "of the security of your account!";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordContains12345() {
        String password = "Haru6aTop_12345";
        String expected = "The password must not contain the substrings "
                + "\"12345\", \"qwerty\", \"admin\" and etc. Take care "
                + "of the security of your account!";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    public void whenPasswordIsOK() {
        String password = "11-Haru6aTop-11";
        String expected = "Go to next step";
        String result = PasswordValidator.validate(password);
        assertThat(result).isEqualTo(expected);
    }
}