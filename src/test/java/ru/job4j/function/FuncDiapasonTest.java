package ru.job4j.function;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FuncDiapasonTest {

    @Test
    public void whenDiapason5To8Linear() {
        List<Double> result = FuncDiapason.diapason(5, 8, x -> 2 * x + 1);
        List<Double> expected = Arrays.asList(11D, 13D, 15D, 17D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenDiapason4To9Squared() {
        List<Double> result = FuncDiapason.diapason(4, 9, x -> x * x + 7);
        List<Double> expected = Arrays.asList(23D, 32D, 43D, 56D, 71D, 88D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenDiapason4To8Expo() {
        List<Double> result = FuncDiapason.diapason(2, 6, x -> Math.pow(x, x));
        List<Double> expected = Arrays.asList(4D, 27D, 256D, 3125D, 46656D);
        assertThat(result, is(expected));
    }

}
