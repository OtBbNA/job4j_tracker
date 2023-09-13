package ru.job4j.tracker;

import ru.job4j.tracker.Item;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

    private Connection cn;

    public SqlTracker() {
        init();
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    private void init() {
        try (InputStream in = new FileInputStream("db/liquibase.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws SQLException {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement =
                     cn.prepareStatement("INSERT INTO items(name, created) VALUES (?, ?);")) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, timeConverterToTimestamp(item.getCreated()));
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE created = ?")) {
            statement.setTimestamp(1, timeConverterToTimestamp(item.getCreated()));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    item.setId(resultSet.getInt("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        try (PreparedStatement statement =
                     cn.prepareStatement("UPDATE items SET name = ? WHERE id = ?;")) {
            statement.setString(1, item.getName());
            statement.setInt(2, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement statement =
                     cn.prepareStatement("DELETE FROM items WHERE id = ?;")) {
            statement.setInt(1, id);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items;")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(itemCreator(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE name = ?;")) {
            statement.setString(1, key);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(itemCreator(resultSet.getInt("id"), resultSet.getString("name"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Item findById(int id) {
        Item item = new Item();
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    item = itemCreator(resultSet.getInt("id"), resultSet.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private Item itemCreator(int id, String name) {
        return new Item(id, name);
    }

    private Timestamp timeConverterToTimestamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}