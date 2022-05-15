package ru.job4j.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserTreeSetTest {
    @Test
    public void whenAsc() {
        Set<UserTreeSet> users = new TreeSet<>();
        users.add(new UserTreeSet("Petr", 32));
        users.add(new UserTreeSet("Ivan", 31));
        Iterator<UserTreeSet> it = users.iterator();
        assertThat(it.next(), is(new UserTreeSet("Ivan", 31)));
        assertThat(it.next(), is(new UserTreeSet("Petr", 32)));
    }

    @Test
    public void whenAscForUsersWithEqualNameButDifferentAge() {
        Set<UserTreeSet> users = new TreeSet<>();
        users.add(new UserTreeSet("Petr", 35));
        users.add(new UserTreeSet("Ivan", 31));
        users.add(new UserTreeSet("Petr", 25));
        users.add(new UserTreeSet("Ivan", 29));
        users.add(new UserTreeSet("Petr", 33));
        users.add(new UserTreeSet("Ivan", 27));
        Set<UserTreeSet> expected = new TreeSet<>(
                Arrays.asList(
                        new UserTreeSet("Ivan", 27),
                        new UserTreeSet("Ivan", 29),
                        new UserTreeSet("Ivan", 31),
                        new UserTreeSet("Petr", 25),
                        new UserTreeSet("Petr", 33),
                        new UserTreeSet("Petr", 35)
                )
        );
        assertThat(users, is(expected));
    }

    @Test
    public void whenComparePetrVSIvan() {
        int rsl = new UserTreeSet("Petr", 32)
                .compareTo(
                        new UserTreeSet("Ivan", 31)
                );
        assertThat(rsl, greaterThan(0));
    }

    @Test
    public void whenComparePetrVSPetrWithDifferentAge() {
        int rsl = new UserTreeSet("Petr", 31)
                .compareTo(
                        new UserTreeSet("Petr", 32)
                );
        assertThat(rsl, lessThan(0));
    }
}