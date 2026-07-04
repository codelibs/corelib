/*
 * Copyright 2012-2025 CodeLibs Project and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.codelibs.core.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;

/**
 * A {@link Set} implementation that stores its elements in a {@link LruHashMap}.
 * <p>
 * This set has a fixed maximum capacity. When the capacity is reached and a new element is added,
 * the least recently used element is removed.
 * </p>
 * @author shinsuke
 * @param <E> the type of elements maintained by this set
 */
public class LruHashSet<E> extends AbstractSet<E> implements Set<E>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The internal LRU hash map used to store elements.
     */
    private transient LruHashMap<E, Object> map;

    // Dummy value to associate with an Object in the backing Map
    private static final Object PRESENT = new Object();

    /**
     * Creates a new {@link LruHashSet} with the specified limit size.
     *
     * @param limitSize
     *            the maximum number of elements to retain in the set
     */
    public LruHashSet(final int limitSize) {
        map = new LruHashMap<>(limitSize);
    }

    /**
     * Returns an iterator over the elements in this set. The elements are
     * returned in no particular order.
     *
     * @return an Iterator over the elements in this set.
     * @see ConcurrentModificationException
     */
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     *
     * @return the number of elements in this set (its cardinality).
     */
    @Override
    public int size() {
        return map.size();
    }

    /**
     * Returns true if this set contains no elements.
     *
     * @return true if this set contains no elements.
     */
    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Returns true if this set contains the specified element.
     *
     * @param o
     *            element whose presence in this set is to be tested.
     * @return true if this set contains the specified element.
     */
    @Override
    public boolean contains(final Object o) {
        return map.containsKey(o);
    }

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * @param o
     *            element to be added to this set.
     * @return true if the set did not already contain the specified
     *         element.
     */
    @Override
    public boolean add(final E o) {
        return map.put(o, PRESENT) == null;
    }

    /**
     * Removes the specified element from this set if it is present.
     *
     * @param o
     *            object to be removed from this set, if present.
     * @return true if the set contained the specified element.
     */
    @Override
    public boolean remove(final Object o) {
        return map.remove(o) == PRESENT;
    }

    /**
     * Removes all of the elements from this set.
     */
    @Override
    public void clear() {
        map.clear();
    }

    /**
     * Serializes this set. The backing map is {@code transient} and its values are a
     * non-serializable sentinel, so only the limit size and the elements are written.
     *
     * @param s the stream to write to
     * @throws IOException if an I/O error occurs
     */
    private void writeObject(final ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(map.getLimitSize());
        s.writeInt(map.size());
        for (final E e : map.keySet()) {
            s.writeObject(e);
        }
    }

    /**
     * Deserializes this set by rebuilding the backing map from the limit size and the
     * elements written by {@link #writeObject(ObjectOutputStream)}.
     *
     * @param s the stream to read from
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of a serialized element cannot be found
     */
    private void readObject(final ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        final int limitSize = s.readInt();
        map = new LruHashMap<>(limitSize);
        final int size = s.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            final E e = (E) s.readObject();
            map.put(e, PRESENT);
        }
    }

}
