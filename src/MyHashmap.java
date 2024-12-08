import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyHashmap<K, V> {
    private static final int INITIAL_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.5;

    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashmap() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public void put(K key, V value) {
        if ((double) size / table.length >= LOAD_FACTOR) {
            resize();
        }

        int index = findSlot(key, false);
        if (index == -1) {
            throw new IllegalStateException("Hashmap is full.");
        }

        if (table[index] == null) {
            size++;
        }
        table[index] = new Entry<>(key, value);
    }

    public V get(K key) {
        int index = findSlot(key, true);
        if (index == -1 || table[index] == null) {
            return null;
        }
        return table[index].value;
    }

    public V getOrDefault(K key, V defaultValue) {
        V value = get(key);
        return (value == null) ? defaultValue : value;
    }

    public List<Map.Entry<K, V>> entrySet() {
        List<Map.Entry<K, V>> entries = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                entries.add(new Map.Entry<K, V>() {
                    @Override
                    public K getKey() {
                        return entry.key;
                    }

                    @Override
                    public V getValue() {
                        return entry.value;
                    }

                    @Override
                    public V setValue(V value) {
                        throw new UnsupportedOperationException("setValue not supported");
                    }
                });
            }
        }
        return entries;
    }

    public boolean delete(K key) {
        int index = findSlot(key, true);
        if (index == -1 || table[index] == null) {
            return false;
        }

        table[index] = null;
        size--;

        // Rehash to maintain integrity
        rehash();
        return true;
    }


    private int findSlot(K key, boolean forSearch) {
        int hash = Math.abs(key.hashCode());
        int index = hash % table.length;
        int step = 1;

        while (table[index] != null) {
            if (forSearch && table[index].key.equals(key)) {
                return index;
            } else if (!forSearch && (table[index].key == null || table[index].key.equals(key))) {
                return index;
            }
            //solve the problem with collisions
            index = (index + step * step) % table.length;
            step++;
        }

        return forSearch ? -1 : index;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }


    private void rehash() {
        Entry<K, V>[] oldTable = table;
        table = new Entry[table.length];
        size = 0;

        for (Entry<K, V> entry : oldTable) {
            if (entry != null) {
                put(entry.key, entry.value);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
}
