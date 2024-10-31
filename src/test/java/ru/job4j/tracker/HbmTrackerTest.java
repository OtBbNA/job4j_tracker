package ru.job4j.tracker;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HbmTrackerTest {

    @AfterEach
    public void deleteItems() {
        try (var tracker = new HbmTracker()) {
            var items = tracker.findAll();
            for (Item item : items) {
                tracker.delete(item.getId());
            }
        }
    }

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item();
            item.setName("test1");
            tracker.add(item);

            Item result = tracker.findById(item.getId());

            assertThat(result.getName()).isEqualTo(item.getName());
        }
    }

    @Test
    void whenAddNewItemAndReplaceThenGetReplacedName() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item();
            item.setName("test1");
            tracker.add(item);
            Item updatingItem = new Item();
            updatingItem.setName("test2");

            tracker.replace(item.getId(), updatingItem);
            Item result = tracker.findById(item.getId());

            assertThat(result.getName()).isEqualTo(updatingItem.getName());
        }
    }

    @Test
    void whenDeleteItemThenFindEmpty() {
        try (var tracker = new HbmTracker()) {
            Item item = new Item();
            item.setName("test1");
            tracker.add(item);

            tracker.delete(item.getId());

            assertThat(tracker.findByName("test1")).isEmpty();
        }
    }

    @Test
    void whenAddListOfItemsThenFindAllListOfSameItems() {
        try (var tracker = new HbmTracker()) {
            Item item1 = new Item();
            Item item2 = new Item();
            Item item3 = new Item();
            item1.setName("test1");
            item2.setName("test2");
            item3.setName("test3");
            tracker.add(item1);
            tracker.add(item2);
            tracker.add(item3);
            List<Item> expected = List.of(item1, item2, item3);

            List<Item> result = tracker.findAll();

            assertThat(result).isEqualTo(expected);
        }
    }

    @Test
    void whenAddItemWithNameThenFindByNameListItemsWithSameName() {
        try (var tracker = new HbmTracker()) {
            Item item1 = new Item();
            Item item2 = new Item();
            String name = "test1";
            item1.setName(name);
            item2.setName(name);
            tracker.add(item1);
            tracker.add(item2);
            List<Item> expected = List.of(item1, item2);

            List<Item> result = tracker.findByName(name);

            assertThat(result).isEqualTo(expected);
        }
    }

    @Test
    void whenAddItemThenFindByIdSameItem() {
        try (var tracker = new HbmTracker()) {
            Item item1 = new Item();
            Item item2 = new Item();
            item1.setName("test1");
            item2.setName("test2");
            tracker.add(item1);
            tracker.add(item2);

            Item result = tracker.findById(item2.getId());

            assertThat(result).isEqualTo(item2);
        }
    }
}