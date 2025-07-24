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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Utility class for collections.
 *
 * @author koichik
 */
public abstract class CollectionsUtil {

    /**
     * Do not instantiate.
     */
    private CollectionsUtil() {
    }

    /**
     * Creates and returns a new instance of {@link ArrayBlockingQueue}.
     *
     * @param <E> the element type of {@link ArrayBlockingQueue}
     * @param capacity the queue capacity
     * @return a new instance of {@link ArrayBlockingQueue}
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity) {
        return new ArrayBlockingQueue<>(capacity);
    }

    /**
     * Creates and returns a new instance of {@link ArrayBlockingQueue}.
     *
     * @param <E> the element type of {@link ArrayBlockingQueue}
     * @param capacity the queue capacity
     * @param fair whether the queue is fair
     * @return a new instance of {@link ArrayBlockingQueue}
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int, boolean)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity, final boolean fair) {
        return new ArrayBlockingQueue<>(capacity, fair);
    }

    /**
     * Creates and returns a new instance of {@link ArrayBlockingQueue}.
     *
     * @param <E> the element type of {@link ArrayBlockingQueue}
     * @param capacity the queue capacity
     * @param fair whether the queue is fair
     * @param c the collection of initial elements
     * @return a new instance of {@link ArrayBlockingQueue}
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int, boolean, Collection)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity, final boolean fair, final Collection<? extends E> c) {
        return new ArrayBlockingQueue<>(capacity, fair, c);
    }

    /**
     * Creates and returns a new instance of {@link ArrayDeque}.
     *
     * @param <E> the element type of {@link ArrayDeque}
     * @return a new instance of {@link ArrayDeque}
     * @see ArrayDeque#ArrayDeque()
     */
    public static <E> ArrayDeque<E> newArrayDeque() {
        return new ArrayDeque<>();
    }

    /**
     * Creates and returns a new instance of {@link ArrayDeque}.
     *
     * @param <E> the element type of {@link ArrayDeque}
     * @param c the collection of elements to be placed in the deque
     * @return a new instance of {@link ArrayDeque}
     * @see ArrayDeque#ArrayDeque(Collection)
     */
    public static <E> ArrayDeque<E> newArrayDeque(final Collection<? extends E> c) {
        return new ArrayDeque<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ArrayDeque}.
     *
     * @param <E> the element type of {@link ArrayDeque}
     * @param numElements the lower bound of the initial capacity range for the deque
     * @return a new instance of {@link ArrayDeque}
     * @see ArrayDeque#ArrayDeque(int)
     */
    public static <E> ArrayDeque<E> newArrayDeque(final int numElements) {
        return new ArrayDeque<>(numElements);
    }

    /**
     * Creates and returns a new instance of {@link ArrayList}.
     *
     * @param <E> the element type of {@link ArrayList}
     * @return a new instance of {@link ArrayList}
     * @see ArrayList#ArrayList()
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Creates and returns a new instance of {@link ArrayList}.
     *
     * @param <E> the element type of {@link ArrayList}
     * @param c the collection of elements to be placed in the list
     * @return a new instance of {@link ArrayList}
     * @see ArrayList#ArrayList(Collection)
     */
    public static <E> ArrayList<E> newArrayList(final Collection<? extends E> c) {
        return new ArrayList<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ArrayList}.
     *
     * @param <E> the element type of {@link ArrayList}
     * @param initialCapacity the initial capacity of the list
     * @return a new instance of {@link ArrayList}
     * @see ArrayList#ArrayList(int)
     */
    public static <E> ArrayList<E> newArrayList(final int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentHashMap}.
     *
     * @param <K> the key type of {@link ConcurrentHashMap}
     * @param <V> the value type of {@link ConcurrentHashMap}
     * @return a new instance of {@link ConcurrentHashMap}
     * @see ConcurrentHashMap#ConcurrentHashMap()
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentHashMap}.
     *
     * @param <K> the key type of {@link ConcurrentHashMap}
     * @param <V> the value type of {@link ConcurrentHashMap}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link ConcurrentHashMap}
     * @see ConcurrentHashMap#ConcurrentHashMap(int)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final int initialCapacity) {
        return new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentHashMap}.
     *
     * @param <K> the key type of {@link ConcurrentHashMap}
     * @param <V> the value type of {@link ConcurrentHashMap}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor threshold for resizing
     * @param concurrencyLevel the estimated number of threads for concurrent updates
     * @return a new instance of {@link ConcurrentHashMap}
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final int initialCapacity, final float loadFactor,
            final int concurrencyLevel) {
        return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentHashMap}.
     *
     * @param <K> the key type of {@link ConcurrentHashMap}
     * @param <V> the value type of {@link ConcurrentHashMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link ConcurrentHashMap}
     * @see ConcurrentHashMap#ConcurrentHashMap(Map)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final Map<? extends K, ? extends V> m) {
        return new ConcurrentHashMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentLinkedQueue}.
     *
     * @param <E> the element type of {@link ConcurrentLinkedQueue}
     * @return a new instance of {@link ConcurrentLinkedQueue}
     * @see ConcurrentLinkedQueue#ConcurrentLinkedQueue()
     */
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentLinkedQueue}.
     *
     * @param <E> the element type of {@link ConcurrentLinkedQueue}
     * @param c the collection of initial elements
     * @return a new instance of {@link ConcurrentLinkedQueue}
     * @see ConcurrentLinkedQueue#ConcurrentLinkedQueue(Collection)
     */
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(final Collection<? extends E> c) {
        return new ConcurrentLinkedQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListMap}.
     *
     * @param <K> the key type of {@link ConcurrentSkipListMap}
     * @param <V> the value type of {@link ConcurrentSkipListMap}
     * @return a new instance of {@link ConcurrentSkipListMap}
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap()
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap() {
        return new ConcurrentSkipListMap<>();
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListMap}.
     *
     * @param <K> the key type of {@link ConcurrentSkipListMap}
     * @param <V> the value type of {@link ConcurrentSkipListMap}
     * @param c the comparator for sorting the map
     * @return a new instance of {@link ConcurrentSkipListMap}
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(Comparator)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final Comparator<? super K> c) {
        return new ConcurrentSkipListMap<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListMap}.
     *
     * @param <K> the key type of {@link ConcurrentSkipListMap}
     * @param <V> the value type of {@link ConcurrentSkipListMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link ConcurrentSkipListMap}
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(Map)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final Map<? extends K, ? extends V> m) {
        return new ConcurrentSkipListMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListMap}.
     *
     * @param <K> the key type of {@link ConcurrentSkipListMap}
     * @param <V> the value type of {@link ConcurrentSkipListMap}
     * @param m the sorted map to be placed in the created map
     * @return a new instance of {@link ConcurrentSkipListMap}
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(SortedMap)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final SortedMap<K, ? extends V> m) {
        return new ConcurrentSkipListMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListSet}.
     *
     * @param <E> the element type of {@link ConcurrentSkipListSet}
     * @return a new instance of {@link ConcurrentSkipListSet}
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet()
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet() {
        return new ConcurrentSkipListSet<>();
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListSet}.
     *
     * @param <E> the element type of {@link ConcurrentSkipListSet}
     * @param c the collection of elements to be placed in the set
     * @return a new instance of {@link ConcurrentSkipListSet}
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(Collection)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final Collection<? extends E> c) {
        return new ConcurrentSkipListSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListSet}.
     *
     * @param <E> the element type of {@link ConcurrentSkipListSet}
     * @param c the comparator for sorting the set
     * @return a new instance of {@link ConcurrentSkipListSet}
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(Comparator)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final Comparator<? super E> c) {
        return new ConcurrentSkipListSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link ConcurrentSkipListSet}.
     *
     * @param <E> the element type of {@link ConcurrentSkipListSet}
     * @param s the sorted set to be placed in the created set
     * @return a new instance of {@link ConcurrentSkipListSet}
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(SortedSet)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final SortedSet<? extends E> s) {
        return new ConcurrentSkipListSet<E>(s);
    }

    /**
     * Creates and returns a new instance of {@link CopyOnWriteArrayList}.
     *
     * @param <E> the element type of {@link CopyOnWriteArrayList}
     * @return a new instance of {@link CopyOnWriteArrayList}
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList()
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * Creates and returns a new instance of {@link CopyOnWriteArrayList}.
     *
     * @param <E> the element type of {@link CopyOnWriteArrayList}
     * @param c the collection of initial elements
     * @return a new instance of {@link CopyOnWriteArrayList}
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList(Collection)
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(final Collection<? extends E> c) {
        return new CopyOnWriteArrayList<>(c);
    }

    /**
     * Creates and returns a new instance of {@link CopyOnWriteArrayList}.
     *
     * @param <E> the element type of {@link CopyOnWriteArrayList}
     * @param toCopyIn the array to be used as the internal array
     * @return a new instance of {@link CopyOnWriteArrayList}
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList(Object[])
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(final E[] toCopyIn) {
        return new CopyOnWriteArrayList<>(toCopyIn);
    }

    /**
     * Creates and returns a new instance of {@link CopyOnWriteArraySet}.
     *
     * @param <E> the element type of {@link CopyOnWriteArraySet}
     * @return a new instance of {@link CopyOnWriteArraySet}
     * @see CopyOnWriteArraySet#CopyOnWriteArraySet()
     */
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    /**
     * Creates and returns a new instance of {@link CopyOnWriteArraySet}.
     *
     * @param <E> the element type of {@link CopyOnWriteArraySet}
     * @param c the collection of initial elements
     * @return a new instance of {@link CopyOnWriteArraySet}
     * @see CopyOnWriteArraySet#CopyOnWriteArraySet(Collection)
     */
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(final Collection<? extends E> c) {
        return new CopyOnWriteArraySet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link DelayQueue}.
     *
     * @param <E> the element type of {@link CopyOnWriteArraySet}
     * @return a new instance of {@link DelayQueue}
     * @see DelayQueue#DelayQueue()
     */
    public static <E extends Delayed> DelayQueue<E> newDelayQueue() {
        return new DelayQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link DelayQueue}.
     *
     * @param <E> the element type of {@link CopyOnWriteArraySet}
     * @param c the collection of initial elements
     * @return a new instance of {@link DelayQueue}
     * @see DelayQueue#DelayQueue(Collection)
     */
    public static <E extends Delayed> DelayQueue<E> newDelayQueue(final Collection<? extends E> c) {
        return new DelayQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link EnumMap}.
     *
     * @param <K> the key type of {@link EnumMap}
     * @param <V> the value type of {@link EnumMap}
     * @param keyType the class object of the key type for this {@literal enum} map
     * @return a new instance of {@link EnumMap}
     * @see EnumMap#EnumMap(Class)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Class<K> keyType) {
        return new EnumMap<>(keyType);
    }

    /**
     * Creates and returns a new instance of {@link EnumMap}.
     *
     * @param <K> the key type of {@link EnumMap}
     * @param <V> the value type of {@link EnumMap}
     * @param m the {@literal enum} map to be used for initialization
     * @return a new instance of {@link EnumMap}
     * @see EnumMap#EnumMap(EnumMap)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final EnumMap<K, ? extends V> m) {
        return new EnumMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link EnumMap}.
     *
     * @param <K> the key type of {@link EnumMap}
     * @param <V> the value type of {@link EnumMap}
     * @param m the map to be used for initialization
     * @return a new instance of {@link EnumMap}
     * @see EnumMap#EnumMap(Map)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Map<K, ? extends V> m) {
        return new EnumMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link HashMap}.
     *
     * @param <K> the key type of {@link HashMap}
     * @param <V> the value type of {@link HashMap}
     * @return a new instance of {@link HashMap}
     * @see HashMap#HashMap()
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * Creates and returns a new instance of {@link HashMap}.
     *
     * @param <K> the key type of {@link HashMap}
     * @param <V> the value type of {@link HashMap}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link HashMap}
     * @see HashMap#HashMap(int)
     */
    public static <K, V> HashMap<K, V> newHashMap(final int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link HashMap}.
     *
     * @param <K> the key type of {@link HashMap}
     * @param <V> the value type of {@link HashMap}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link HashMap}
     * @see HashMap#HashMap(int, float)
     */
    public static <K, V> HashMap<K, V> newHashMap(final int initialCapacity, final float loadFactor) {
        return new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link HashMap}.
     *
     * @param <K> the key type of {@link HashMap}
     * @param <V> the value type of {@link HashMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link HashMap}
     * @see HashMap#HashMap(int, float)
     */
    public static <K, V> HashMap<K, V> newHashMap(final Map<? extends K, ? extends V> m) {
        return new HashMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link HashSet}.
     *
     * @param <E> the element type of {@link HashSet}
     * @return a new instance of {@link HashSet}
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    /**
     * Creates and returns a new instance of {@link HashSet}.
     *
     * @param <E> the element type of {@link HashSet}
     * @param c the collection of elements to be placed in the set
     * @return a new instance of {@link HashSet}
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final Collection<? extends E> c) {
        return new HashSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link HashSet}.
     *
     * @param <E> the element type of {@link HashSet}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link HashSet}
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link HashSet}.
     *
     * @param <E> the element type of {@link HashSet}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link HashSet}
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final int initialCapacity, final float loadFactor) {
        return new HashSet<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link Hashtable}.
     *
     * @param <K> the key type of {@link Hashtable}
     * @param <V> the value type of {@link Hashtable}
     * @return a new instance of {@link Hashtable}
     * @see Hashtable#Hashtable()
     */
    public static <K, V> Hashtable<K, V> newHashtable() {
        return new Hashtable<>();
    }

    /**
     * Creates and returns a new instance of {@link Hashtable}.
     *
     * @param <K> the key type of {@link Hashtable}
     * @param <V> the value type of {@link Hashtable}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link Hashtable}
     * @see Hashtable#Hashtable(int)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final int initialCapacity) {
        return new Hashtable<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link Hashtable}.
     *
     * @param <K> the key type of {@link Hashtable}
     * @param <V> the value type of {@link Hashtable}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link Hashtable}
     * @see Hashtable#Hashtable(int, float)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final int initialCapacity, final float loadFactor) {
        return new Hashtable<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link Hashtable}.
     *
     * @param <K> the key type of {@link Hashtable}
     * @param <V> the value type of {@link Hashtable}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link Hashtable}
     * @see Hashtable#Hashtable(Map)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final Map<? extends K, ? extends V> m) {
        return new Hashtable<>(m);
    }

    /**
     * Creates and returns a new instance of {@link IdentityHashMap}.
     *
     * @param <K> the key type of {@link IdentityHashMap}
     * @param <V> the value type of {@link IdentityHashMap}
     * @return a new instance of {@link IdentityHashMap}
     * @see IdentityHashMap#IdentityHashMap()
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    /**
     * Creates and returns a new instance of {@link IdentityHashMap}.
     *
     * @param <K> the key type of {@link IdentityHashMap}
     * @param <V> the value type of {@link IdentityHashMap}
     * @param expectedMaxSize the expected maximum size of the map
     * @return a new instance of {@link IdentityHashMap}
     * @see IdentityHashMap#IdentityHashMap(int)
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(final int expectedMaxSize) {
        return new IdentityHashMap<>(expectedMaxSize);
    }

    /**
     * Creates and returns a new instance of {@link IdentityHashMap}.
     *
     * @param <K> the key type of {@link IdentityHashMap}
     * @param <V> the value type of {@link IdentityHashMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link IdentityHashMap}
     * @see IdentityHashMap#IdentityHashMap(Map)
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(final Map<? extends K, ? extends V> m) {
        return new IdentityHashMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingDeque}.
     *
     * @param <E> the element type of {@link LinkedBlockingDeque}
     * @return a new instance of {@link LinkedBlockingDeque}
     * @see LinkedBlockingDeque#LinkedBlockingDeque()
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
        return new LinkedBlockingDeque<>();
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingDeque}.
     *
     * @param <E> the element type of {@link LinkedBlockingDeque}
     * @param c the collection of elements to be placed in the deque
     * @return a new instance of {@link LinkedBlockingDeque}
     * @see LinkedBlockingDeque#LinkedBlockingDeque(Collection)
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(final Collection<? extends E> c) {
        return new LinkedBlockingDeque<>(c);
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingDeque}.
     *
     * @param <E> the element type of {@link LinkedBlockingDeque}
     * @param initialCapacity the initial capacity of the deque
     * @return a new instance of {@link LinkedBlockingDeque}
     * @see LinkedBlockingDeque#LinkedBlockingDeque(int)
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(final int initialCapacity) {
        return new LinkedBlockingDeque<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingQueue}.
     *
     * @param <E> the element type of {@link LinkedBlockingQueue}
     * @return a new instance of {@link LinkedBlockingQueue}
     * @see LinkedBlockingQueue#LinkedBlockingQueue()
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingQueue}.
     *
     * @param <E> the element type of {@link LinkedBlockingQueue}
     * @param c the collection of initial elements
     * @return a new instance of {@link LinkedBlockingQueue}
     * @see LinkedBlockingQueue#LinkedBlockingQueue(Collection)
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(final Collection<? extends E> c) {
        return new LinkedBlockingQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link LinkedBlockingQueue}.
     *
     * @param <E> the element type of {@link LinkedBlockingQueue}
     * @param initialCapacity the capacity of the queue
     * @return a new instance of {@link LinkedBlockingQueue}
     * @see LinkedBlockingQueue#LinkedBlockingQueue(int)
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(final int initialCapacity) {
        return new LinkedBlockingQueue<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashMap}.
     *
     * @param <K> the key type of {@link LinkedHashMap}
     * @param <V> the value type of {@link LinkedHashMap}
     * @return a new instance of {@link LinkedHashMap}
     * @see LinkedHashMap#LinkedHashMap()
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashMap}.
     *
     * @param <K> the key type of {@link LinkedHashMap}
     * @param <V> the value type of {@link LinkedHashMap}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link LinkedHashMap}
     * @see LinkedHashMap#LinkedHashMap(int)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final int initialCapacity) {
        return new LinkedHashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashMap}.
     *
     * @param <K> the key type of {@link LinkedHashMap}
     * @param <V> the value type of {@link LinkedHashMap}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link LinkedHashMap}
     * @see LinkedHashMap#LinkedHashMap(int, float)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final int initialCapacity, final float loadFactor) {
        return new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashMap}.
     *
     * @param <K> the key type of {@link LinkedHashMap}
     * @param <V> the value type of {@link LinkedHashMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link LinkedHashMap}
     * @see LinkedHashMap#LinkedHashMap(Map)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final Map<? extends K, ? extends V> m) {
        return new LinkedHashMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashSet}.
     *
     * @param <E> the element type of {@link LinkedHashSet}
     * @return a new instance of {@link LinkedHashSet}
     * @see LinkedHashSet#LinkedHashSet()
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashSet}.
     *
     * @param <E> the element type of {@link LinkedHashSet}
     * @param c the collection of elements to be placed in the set
     * @return a new instance of {@link LinkedHashSet}
     * @see LinkedHashSet#LinkedHashSet(Collection)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final Collection<? extends E> c) {
        return new LinkedHashSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashSet}.
     *
     * @param <E> the element type of {@link LinkedHashSet}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link LinkedHashSet}
     * @see LinkedHashSet#LinkedHashSet(int)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final int initialCapacity) {
        return new LinkedHashSet<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link LinkedHashSet}.
     *
     * @param <E> the element type of {@link LinkedHashSet}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link LinkedHashSet}
     * @see LinkedHashSet#LinkedHashSet(int, float)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final int initialCapacity, final float loadFactor) {
        return new LinkedHashSet<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link LinkedList}.
     *
     * @param <E> the element type of {@link LinkedList}
     * @return a new instance of {@link LinkedList}
     * @see LinkedList#LinkedList()
     */
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * Creates and returns a new instance of {@link LinkedList}.
     *
     * @param <E> the element type of {@link LinkedList}
     * @param c the collection of elements to be placed in the list
     * @return a new instance of {@link LinkedList}
     * @see LinkedList#LinkedList(Collection)
     */
    public static <E> LinkedList<E> newLinkedList(final Collection<? extends E> c) {
        return new LinkedList<>(c);
    }

    /**
     * Creates and returns a new instance of {@link PriorityBlockingQueue}.
     *
     * @param <E> the element type of {@link PriorityBlockingQueue}
     * @return a new instance of {@link PriorityBlockingQueue}
     * @see PriorityBlockingQueue#PriorityBlockingQueue()
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link PriorityBlockingQueue}.
     *
     * @param <E> the element type of {@link PriorityBlockingQueue}
     * @param c the collection of initial elements
     * @return a new instance of {@link PriorityBlockingQueue}
     * @see PriorityBlockingQueue#PriorityBlockingQueue(Collection)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final Collection<? extends E> c) {
        return new PriorityBlockingQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link PriorityBlockingQueue}.
     *
     * @param <E> the element type of {@link PriorityBlockingQueue}
     * @param initialCapacity the initial capacity of the priority queue
     * @return a new instance of {@link PriorityBlockingQueue}
     * @see PriorityBlockingQueue#PriorityBlockingQueue(int)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final int initialCapacity) {
        return new PriorityBlockingQueue<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link PriorityBlockingQueue}.
     *
     * @param <E> the element type of {@link PriorityBlockingQueue}
     * @param initialCapacity the initial capacity of the priority queue
     * @param comparator the comparator for ordering the elements in the priority queue
     * @return a new instance of {@link PriorityBlockingQueue}
     * @see PriorityBlockingQueue#PriorityBlockingQueue(int, Comparator)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final int initialCapacity, final Comparator<? super E> comparator) {
        return new PriorityBlockingQueue<>(initialCapacity, comparator);
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue()
     */
    public static <E> PriorityQueue<E> newPriorityQueue() {
        return new PriorityQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @param c the collection of elements to be placed in the priority queue
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue(Collection)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final Collection<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @param initialCapacity the initial capacity of the priority queue
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue(int)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final int initialCapacity) {
        return new PriorityQueue<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @param initialCapacity the initial capacity of the priority queue
     * @param comparator the comparator for ordering the elements in the priority queue
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue(int, Comparator)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final int initialCapacity, final Comparator<? super E> comparator) {
        return new PriorityQueue<>(initialCapacity, comparator);
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @param c the collection of elements to be placed in the priority queue
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue(PriorityQueue)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final PriorityQueue<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link PriorityQueue}.
     *
     * @param <E> the element type of {@link PriorityQueue}
     * @param c the collection of elements to be placed in the priority queue
     * @return a new instance of {@link PriorityQueue}
     * @see PriorityQueue#PriorityQueue(SortedSet)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final SortedSet<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * Creates and returns a new instance of {@link Stack}.
     *
     * @param <E> the element type of {@link Stack}
     * @return a new instance of {@link Stack}
     * @see Stack#Stack()
     */
    public static <E> Stack<E> newStack() {
        return new Stack<>();
    }

    /**
     * Creates and returns a new instance of {@link SynchronousQueue}.
     *
     * @param <E> the element type of {@link SynchronousQueue}
     * @return a new instance of {@link SynchronousQueue}
     * @see SynchronousQueue#SynchronousQueue()
     */
    public static <E> SynchronousQueue<E> newSynchronousQueue() {
        return new SynchronousQueue<>();
    }

    /**
     * Creates and returns a new instance of {@link SynchronousQueue}.
     *
     * @param <E> the element type of {@link SynchronousQueue}
     * @param fair whether the access order is FIFO for waiting threads
     * @return a new instance of {@link SynchronousQueue}
     * @see SynchronousQueue#SynchronousQueue()
     */
    public static <E> SynchronousQueue<E> newSynchronousQueue(final boolean fair) {
        return new SynchronousQueue<>(fair);
    }

    /**
     * Creates and returns a new instance of {@link TreeMap}.
     *
     * @param <K> the key type of {@link TreeMap}
     * @param <V> the value type of {@link TreeMap}
     * @return a new instance of {@link TreeMap}
     * @see TreeMap#TreeMap()
     */
    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    /**
     * Creates and returns a new instance of {@link TreeMap}.
     *
     * @param <K> the key type of {@link TreeMap}
     * @param <V> the value type of {@link TreeMap}
     * @param c the comparator for sorting the map
     * @return a new instance of {@link TreeMap}
     * @see TreeMap#TreeMap(Comparator)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final Comparator<? super K> c) {
        return new TreeMap<>(c);
    }

    /**
     * Creates and returns a new instance of {@link TreeMap}.
     *
     * @param <K> the key type of {@link TreeMap}
     * @param <V> the value type of {@link TreeMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link TreeMap}
     * @see TreeMap#TreeMap(Map)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final Map<? extends K, ? extends V> m) {
        return new TreeMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link TreeMap}.
     *
     * @param <K> the key type of {@link TreeMap}
     * @param <V> the value type of {@link TreeMap}
     * @param m the sorted map to be placed in the created map
     * @return a new instance of {@link TreeMap}
     * @see TreeMap#TreeMap(SortedMap)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final SortedMap<K, ? extends V> m) {
        return new TreeMap<>(m);
    }

    /**
     * Creates and returns a new instance of {@link TreeSet}.
     *
     * @param <E> the element type of {@link TreeSet}
     * @return a new instance of {@link TreeSet}
     * @see TreeSet#TreeSet()
     */
    public static <E> TreeSet<E> newTreeSet() {
        return new TreeSet<>();
    }

    /**
     * Creates and returns a new instance of {@link TreeSet}.
     *
     * @param <E> the element type of {@link TreeSet}
     * @param c the collection of elements to be placed in the set
     * @return a new instance of {@link TreeSet}
     * @see TreeSet#TreeSet(Collection)
     */
    public static <E> TreeSet<E> newTreeSet(final Collection<? extends E> c) {
        return new TreeSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link TreeSet}.
     *
     * @param <E> the element type of {@link TreeSet}
     * @param c the comparator for sorting the set
     * @return a new instance of {@link TreeSet}
     * @see TreeSet#TreeSet(Comparator)
     */
    public static <E> TreeSet<E> newTreeSet(final Comparator<? super E> c) {
        return new TreeSet<>(c);
    }

    /**
     * Creates and returns a new instance of {@link TreeSet}.
     *
     * @param <E> the element type of {@link TreeSet}
     * @param s the sorted set to be placed in the created set
     * @return a new instance of {@link TreeSet}
     * @see TreeSet#TreeSet(SortedSet)
     */
    public static <E> TreeSet<E> newTreeSet(final SortedSet<? extends E> s) {
        return new TreeSet<E>(s);
    }

    /**
     * Creates and returns a new instance of {@link Vector}.
     *
     * @param <E> the element type of {@link Vector}
     * @return a new instance of {@link Vector}
     * @see Vector#Vector()
     */
    public static <E> Vector<E> newVector() {
        return new Vector<>();
    }

    /**
     * Creates and returns a new instance of {@link Vector}.
     *
     * @param <E> the element type of {@link Vector}
     * @param c the collection of elements to be placed in the vector
     * @return a new instance of {@link Vector}
     * @see Vector#Vector(Collection)
     */
    public static <E> Vector<E> newVector(final Collection<? extends E> c) {
        return new Vector<>(c);
    }

    /**
     * Creates and returns a new instance of {@link Vector}.
     *
     * @param <E> the element type of {@link Vector}
     * @param initialCapacity the initial capacity of the vector
     * @return a new instance of {@link Vector}
     * @see Vector#Vector(int)
     */
    public static <E> Vector<E> newVector(final int initialCapacity) {
        return new Vector<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link Vector}.
     *
     * @param <E> the element type of {@link Vector}
     * @param initialCapacity the initial capacity of the vector
     * @param capacityIncrement the capacity increment when the vector overflows
     * @return a new instance of {@link Vector}
     * @see Vector#Vector(int, int)
     */
    public static <E> Vector<E> newVector(final int initialCapacity, final int capacityIncrement) {
        return new Vector<>(initialCapacity, capacityIncrement);
    }

    /**
     * Creates and returns a new instance of {@link WeakHashMap}.
     *
     * @param <K> the key type of {@link WeakHashMap}
     * @param <V> the value type of {@link WeakHashMap}
     * @return a new instance of {@link WeakHashMap}
     * @see WeakHashMap#WeakHashMap()
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap() {
        return new WeakHashMap<>();
    }

    /**
     * Creates and returns a new instance of {@link WeakHashMap}.
     *
     * @param <K> the key type of {@link WeakHashMap}
     * @param <V> the value type of {@link WeakHashMap}
     * @param initialCapacity the initial capacity
     * @return a new instance of {@link WeakHashMap}
     * @see WeakHashMap#WeakHashMap(int)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final int initialCapacity) {
        return new WeakHashMap<>(initialCapacity);
    }

    /**
     * Creates and returns a new instance of {@link WeakHashMap}.
     *
     * @param <K> the key type of {@link WeakHashMap}
     * @param <V> the value type of {@link WeakHashMap}
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     * @return a new instance of {@link WeakHashMap}
     * @see WeakHashMap#WeakHashMap(int, float)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final int initialCapacity, final float loadFactor) {
        return new WeakHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * Creates and returns a new instance of {@link WeakHashMap}.
     *
     * @param <K> the key type of {@link WeakHashMap}
     * @param <V> the value type of {@link WeakHashMap}
     * @param m the map to be placed in the created map
     * @return a new instance of {@link WeakHashMap}
     * @see WeakHashMap#WeakHashMap(Map)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final Map<? extends K, ? extends V> m) {
        return new WeakHashMap<>(m);
    }

    /**
     * Associates the specified value with the specified key in the map if the map does not already contain the key.
     * <p>
     * If the map already contains the key, the value associated with the key is returned and the map is unchanged.
     * If the map does not contain the key, the specified value is associated with the key and the value is returned.
     * In either case, the returned value is the value currently associated with the key in the map.
     * </p>
     *
     * @param <K> the key type of {@link HashMap}
     * @param <V> the value type of {@link HashMap}
     * @param map the map
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with the specified key, or the specified value if the key was not present
     * @see ConcurrentHashMap#putIfAbsent(Object, Object)
     */
    public static <K, V> V putIfAbsent(final ConcurrentMap<K, V> map, final K key, final V value) {
        final V exists = map.putIfAbsent(key, value);
        if (exists != null) {
            return exists;
        }
        return value;
    }

    /**
     * Returns {@literal true} if the given {@link Collection} is {@literal null} or empty.
     *
     * @param collection the collection
     * @return {@literal true} if the collection is {@literal null} or empty
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Returns {@literal true} if the given {@link Collection} is not {@literal null} and not empty.
     *
     * @param collection the collection
     * @return {@literal true} if the collection is not {@literal null} and not empty
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Returns {@literal true} if the given {@link Map} is {@literal null} or empty.
     *
     * @param map the map
     * @return {@literal true} if the map is {@literal null} or empty
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Returns {@literal true} if the given {@link Map} is not {@literal null} and not empty.
     *
     * @param map the map
     * @return {@literal true} if the map is not {@literal null} and not empty
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

    // Java 21 Sequenced Collections support

    /**
     * Returns the first element of a sequenced collection.
     *
     * @param <E> the element type
     * @param collection the sequenced collection
     * @return the first element, or null if the collection is empty
     * @since Java 21
     */
    public static <E> E getFirst(final SequencedCollection<E> collection) {
        return collection.isEmpty() ? null : collection.getFirst();
    }

    /**
     * Returns the last element of a sequenced collection.
     *
     * @param <E> the element type
     * @param collection the sequenced collection
     * @return the last element, or null if the collection is empty
     * @since Java 21
     */
    public static <E> E getLast(final SequencedCollection<E> collection) {
        return collection.isEmpty() ? null : collection.getLast();
    }

    /**
     * Adds an element to the beginning of a sequenced collection.
     *
     * @param <E> the element type
     * @param collection the sequenced collection
     * @param element the element to add
     * @since Java 21
     */
    public static <E> void addFirst(final SequencedCollection<E> collection, final E element) {
        collection.addFirst(element);
    }

    /**
     * Adds an element to the end of a sequenced collection.
     *
     * @param <E> the element type
     * @param collection the sequenced collection
     * @param element the element to add
     * @since Java 21
     */
    public static <E> void addLast(final SequencedCollection<E> collection, final E element) {
        collection.addLast(element);
    }

    /**
     * Returns a reversed view of a sequenced collection.
     *
     * @param <E> the element type
     * @param collection the sequenced collection
     * @return a reversed view of the collection
     * @since Java 21
     */
    public static <E> SequencedCollection<E> reversed(final SequencedCollection<E> collection) {
        return collection.reversed();
    }

    /**
     * Returns the first entry of a sequenced map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the sequenced map
     * @return the first entry, or null if the map is empty
     * @since Java 21
     */
    public static <K, V> Map.Entry<K, V> firstEntry(final SequencedMap<K, V> map) {
        return map.isEmpty() ? null : map.firstEntry();
    }

    /**
     * Returns the last entry of a sequenced map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the sequenced map
     * @return the last entry, or null if the map is empty
     * @since Java 21
     */
    public static <K, V> Map.Entry<K, V> lastEntry(final SequencedMap<K, V> map) {
        return map.isEmpty() ? null : map.lastEntry();
    }

    /**
     * Returns a reversed view of a sequenced map.
     *
     * @param <K> the key type
     * @param <V> the value type
     * @param map the sequenced map
     * @return a reversed view of the map
     * @since Java 21
     */
    public static <K, V> SequencedMap<K, V> reversed(final SequencedMap<K, V> map) {
        return map.reversed();
    }

}
