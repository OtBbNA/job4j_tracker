package ru.job4j.tracker;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeAll
    public static void initConnection() {
        try (InputStream in = new FileInputStream("src/main/resources/db/liquibase_test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterAll
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @AfterEach
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenIdMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()).getId()).isEqualTo(item.getId());
    }

    @Test
    public void whenSaveItemAndReplaceAndFindByGeneratedIdThenIdMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        Item replace = new Item("replace");
        tracker.add(item);
        int unreplaceId = tracker.findById(item.getId()).getId();
        tracker.replace(item.getId(), replace);
        assertThat(unreplaceId).isEqualTo(tracker.findById(replace.getId()).getId());
    }

    @Test
    public void whenSaveItemAndDeleteThenFindByGeneratedIdMustBeNull() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        int itemId = tracker.findById(item.getId()).getId();
        tracker.delete(itemId);
        assertThat(tracker.findById(itemId)).isEqualTo(null);
    }

    @Test
    public void whenDontSaveItemAndFindAllThenListMustBeEmpty() {
        SqlTracker tracker = new SqlTracker(connection);
        assertThat(tracker.findAll().size()).isEqualTo(0);
    }

    @Test
    public void whenSaveThreeItemsAndFindAllThenListMustHaveSizeThree() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.add(item);
        tracker.add(item);
        assertThat(tracker.findAll().size()).isEqualTo(3);
    }

    @Test
    public void whenSaveThreeItemWithNameItemAndTwoWithAnotherNameAndFindByNameItemThenListMustBeSameToExpected() {
        SqlTracker tracker = new SqlTracker(connection);
        List<Item> expected = new ArrayList<>();
        Item item = new Item("item");
        Item another = new Item("another");
        for (int i = 0; i < 3; i++) {
            tracker.add(item);
            expected.add(tracker.findById(item.getId()));
            tracker.add(another);
        }
        assertThat(tracker.findByName(item.getName())).isEqualTo(expected);
    }

    @Test
    public void whenSaveItemAndFindByNameWhereAnotherNameThenListMustBeEmpty() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        tracker.add(item);
        tracker.add(item);
        assertThat(tracker.findByName("another").size()).isEqualTo(0);
    }

}