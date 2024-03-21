package ru.job4j.gc.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        cache.putIfAbsent(key, new SoftReference<V>(value));
    }

    public final V get(K key) {
        V s = load(key);
        put(key, s);
        return cache.get(key).get();
    }

    protected abstract V load(K key);
}
