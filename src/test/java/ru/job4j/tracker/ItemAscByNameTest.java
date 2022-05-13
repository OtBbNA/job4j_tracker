package ru.job4j.tracker;

import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemAscByNameTest {

    @Test
    public void compare() {
        Item item1 = new Item(1, "Sasha");
        Item item2 = new Item(2, "Dasha");
        Item item3 = new Item(3, "Aleksey");
        Item item4 = new Item(4, "Jora");
        List<Item> items = Arrays.asList(item1, item2, item3, item4);
        Collections.sort(items, new ItemAscByName());
        List<Item> expected = Arrays.asList(item3, item2, item4, item1);
        Assert.assertArrayEquals(new List[]{expected}, new List[]{items});
    }
}