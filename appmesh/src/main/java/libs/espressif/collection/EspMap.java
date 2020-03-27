package libs.espressif.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import libs.espressif.function.EspBiConsumer;

public class EspMap<K, V> implements Map<K, V> {
    private final Map<K, V> mImpl;

    public EspMap() {
        this(new HashMap<>());
    }

    public EspMap( Map<K, V> map) {
        mImpl = map;
    }

    @Override
    public int size() {
        return mImpl.size();
    }

    @Override
    public boolean isEmpty() {
        return mImpl.isEmpty();
    }

    @Override
    public boolean containsKey( Object key) {
        return mImpl.containsKey(key);
    }

    @Override
    public boolean containsValue( Object value) {
        return mImpl.containsValue(value);
    }

    
    @Override
    public V get( Object key) {
        return mImpl.get(key);
    }

    
    @Override
    public V put( K key,  V value) {
        return mImpl.put(key, value);
    }

    
    @Override
    public V remove( Object key) {
        return mImpl.remove(key);
    }

    @Override
    public void putAll( Map<? extends K, ? extends V> m) {
        mImpl.putAll(m);
    }

    @Override
    public void clear() {
        mImpl.clear();
    }

    
    @Override
    public Set<K> keySet() {
        return mImpl.keySet();
    }

    
    @Override
    public Collection<V> values() {
        return mImpl.values();
    }

    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return mImpl.entrySet();
    }

    public void foreach(EspBiConsumer<K, V> consumer) {
        EspCollections.forEach(mImpl, consumer);
    }
}
