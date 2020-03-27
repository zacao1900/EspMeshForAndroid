package libs.espressif.collection;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import libs.espressif.function.EspConsumer;
import libs.espressif.function.EspFunction;

public class EspCollection<E> implements Collection<E> {
    private final Collection<E> mImpl;

    public EspCollection( Collection<E> collection) {
        mImpl = collection;
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
    public boolean contains(Object o) {
        return mImpl.contains(o);
    }

    
    @Override
    public Iterator<E> iterator() {
        return mImpl.iterator();
    }

    @Override
    public Object[] toArray() {
        return mImpl.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return mImpl.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return mImpl.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return mImpl.remove(o);
    }

    @Override
    public boolean containsAll( Collection<?> c) {
        return mImpl.containsAll(c);
    }

    @Override
    public boolean addAll( Collection<? extends E> c) {
        return mImpl.addAll(c);
    }

    @Override
    public boolean removeAll( Collection<?> c) {
        return mImpl.removeAll(c);
    }

    @Override
    public boolean retainAll( Collection<?> c) {
        return mImpl.retainAll(c);
    }

    @Override
    public void clear() {
        mImpl.clear();
    }

    public void foreach(EspConsumer<E> consumer) {
        EspCollections.forEach(mImpl, consumer);
    }

    public <K> Map<K, List<E>> groupBy(EspFunction<E, K> function) {
        return EspCollections.groupBy(mImpl, function);
    }
}
