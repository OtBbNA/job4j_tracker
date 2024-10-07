package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class HbmTracker implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean rsl = false;
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Item SET name = :fName WHERE id = :fId")
                    .setParameter("fId", id)
                    .setParameter("fName", item.getName())
                    .executeUpdate();
            rsl = true;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rsl;
    }

    @Override
    public void delete(int id) {
        Session session = sf.getCurrentSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Item> findAll() {
        List<Item> rsl = new ArrayList<>();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<Item> query = session.createQuery("FROM Item", Item.class);
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rsl;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> rsl = new ArrayList<>();
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<Item> query = session.createQuery("FROM Item WHERE name = :fName", Item.class).setParameter("fName", key);
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rsl;
    }

    @Override
    public Item findById(int id) {
        Item rsl = null;
        Session session = sf.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query<Item> query = session.createQuery("FROM Item AS i WHERE i.id = :fId", Item.class).setParameter("fId", id);
            rsl = query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return rsl;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}