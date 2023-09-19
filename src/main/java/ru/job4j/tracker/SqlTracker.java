package ru.job4j.tracker;

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
        Timestamp time = timeConverterToTimestamp(item.getCreated());
        try (PreparedStatement statement =
                     cn.prepareStatement("INSERT INTO items (name, created) VALUES (?, ?);", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, time);
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
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
        item.setId(id);
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
                    items.add(itemCreator(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            timeConverterToLocalDateTime(resultSet.getTimestamp("created")))
                    );
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
                    items.add(itemCreator(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            timeConverterToLocalDateTime(resultSet.getTimestamp("created")))
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
        Item item = null;
        try (PreparedStatement statement = cn.prepareStatement("SELECT * FROM items WHERE id = ?;")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    item = itemCreator(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            timeConverterToLocalDateTime(resultSet.getTimestamp("created"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private Item itemCreator(int id, String name, LocalDateTime ldt) {
        return new Item(id, name, ldt);
    }

    private Timestamp timeConverterToTimestamp(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }

    private LocalDateTime timeConverterToLocalDateTime(Timestamp ts) {
        return ts.toLocalDateTime();
    }
}