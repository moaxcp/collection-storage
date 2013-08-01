package collections;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author john
 */
public interface Map<K, V> {
    public void clear();
    public boolean containsKey(K key);
    public boolean containsValue(V value);
    public Set<Entry<K, V>> entrySet();
    boolean equals(Object o);
    public V get(K key);
    int hashCode();
    public boolean isEmpty();
    public Set<K> keySet();
    public V put(K key, V value);
    void putAll(Map<? extends K, ? extends V> m);
    public V remove(K key);
    public int size();
    public Set<V> values();
    
    public static interface Entry<K, V> {
        boolean equals(Object o);
        public K getKey();
        public V getValue();
        int hashCode();
        V setValue(V value);
    }
}
