/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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
import java.util.Map;
import java.util.PriorityQueue;
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
 * コレクションのためのユーティリティです。
 *
 * @author koichik
 */
public abstract class CollectionsUtil {

    /**
     * {@link ArrayBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayBlockingQueue}の要素型
     * @param capacity
     *            キューの容量
     * @return {@link ArrayBlockingQueue}の新しいインスタンス
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity) {
        return new ArrayBlockingQueue<>(capacity);
    }

    /**
     * {@link ArrayBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayBlockingQueue}の要素型
     * @param capacity
     *            キューの容量
     * @param fair
     *            {@code true}の場合、挿入または削除時にブロックされたスレッドに対するキューアクセス
     * @return {@link ArrayBlockingQueue}の新しいインスタンス
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int, boolean)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity, final boolean fair) {
        return new ArrayBlockingQueue<>(capacity, fair);
    }

    /**
     * {@link ArrayBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayBlockingQueue}の要素型
     * @param capacity
     *            キューの容量
     * @param fair
     *            {@code true}の場合、挿入または削除時にブロックされたスレッドに対するキューアクセス
     * @param c
     *            最初に含む要素のコレクション
     * @return {@link ArrayBlockingQueue}の新しいインスタンス
     * @see ArrayBlockingQueue#ArrayBlockingQueue(int, boolean, Collection)
     */
    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(final int capacity, final boolean fair, final Collection<? extends E> c) {
        return new ArrayBlockingQueue<>(capacity, fair, c);
    }

    /**
     * {@link ArrayDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayDeque}の要素型
     * @return {@link ArrayDeque}の新しいインスタンス
     * @see ArrayDeque#ArrayDeque()
     */
    public static <E> ArrayDeque<E> newArrayDeque() {
        return new ArrayDeque<>();
    }

    /**
     * {@link ArrayDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayDeque}の要素型
     * @param c
     *            要素が両端キューに配置されるコレクション
     * @return {@link ArrayDeque}の新しいインスタンス
     * @see ArrayDeque#ArrayDeque(Collection)
     */
    public static <E> ArrayDeque<E> newArrayDeque(final Collection<? extends E> c) {
        return new ArrayDeque<>(c);
    }

    /**
     * {@link ArrayDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayDeque}の要素型
     * @param numElements
     *            両端キューの初期容量の範囲の下限
     * @return {@link ArrayDeque}の新しいインスタンス
     * @see ArrayDeque#ArrayDeque(int)
     */
    public static <E> ArrayDeque<E> newArrayDeque(final int numElements) {
        return new ArrayDeque<>(numElements);
    }

    /**
     * {@link ArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayList}の要素型
     * @return {@link ArrayList}の新しいインスタンス
     * @see ArrayList#ArrayList()
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * {@link ArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayList}の要素型
     * @param c
     *            要素がリストに配置されるコレクション
     * @return {@link ArrayList}の新しいインスタンス
     * @see ArrayList#ArrayList(Collection)
     */
    public static <E> ArrayList<E> newArrayList(final Collection<? extends E> c) {
        return new ArrayList<>(c);
    }

    /**
     * {@link ArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ArrayList}の要素型
     * @param initialCapacity
     *            リストの初期容量
     * @return {@link ArrayList}の新しいインスタンス
     * @see ArrayList#ArrayList(int)
     */
    public static <E> ArrayList<E> newArrayList(final int initialCapacity) {
        return new ArrayList<>(initialCapacity);
    }

    /**
     * {@link ConcurrentHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentHashMap}のキーの型
     * @param <V>
     *            {@link ConcurrentHashMap}の値の型
     * @return {@link ConcurrentHashMap}の新しいインスタンス
     * @see ConcurrentHashMap#ConcurrentHashMap()
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
        return new ConcurrentHashMap<>();
    }

    /**
     * {@link ConcurrentHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentHashMap}のキーの型
     * @param <V>
     *            {@link ConcurrentHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @return {@link ConcurrentHashMap}の新しいインスタンス
     * @see ConcurrentHashMap#ConcurrentHashMap(int)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final int initialCapacity) {
        return new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * {@link ConcurrentHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentHashMap}のキーの型
     * @param <V>
     *            {@link ConcurrentHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            サイズ変更の制御に使用される負荷係数のしきい値
     * @param concurrencyLevel
     *            同時更新を行うスレッドの推定数
     * @return {@link ConcurrentHashMap}の新しいインスタンス
     * @see ConcurrentHashMap#ConcurrentHashMap(int, float, int)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final int initialCapacity, final float loadFactor,
            final int concurrencyLevel) {
        return new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * {@link ConcurrentHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentHashMap}のキーの型
     * @param <V>
     *            {@link ConcurrentHashMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link ConcurrentHashMap}の新しいインスタンス
     * @see ConcurrentHashMap#ConcurrentHashMap(Map)
     */
    public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap(final Map<? extends K, ? extends V> m) {
        return new ConcurrentHashMap<>(m);
    }

    /**
     * {@link ConcurrentLinkedQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentLinkedQueue}の要素型
     * @return {@link ConcurrentLinkedQueue}の新しいインスタンス
     * @see ConcurrentLinkedQueue#ConcurrentLinkedQueue()
     */
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    /**
     * {@link ConcurrentLinkedQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentLinkedQueue}の要素型
     * @param c
     *            最初に含む要素のコレクション
     * @return {@link ConcurrentLinkedQueue}の新しいインスタンス
     * @see ConcurrentLinkedQueue#ConcurrentLinkedQueue(Collection)
     */
    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(final Collection<? extends E> c) {
        return new ConcurrentLinkedQueue<>(c);
    }

    /**
     * {@link ConcurrentSkipListMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentSkipListMap}のキーの型
     * @param <V>
     *            {@link ConcurrentSkipListMap}の値の型
     * @return {@link ConcurrentSkipListMap}の新しいインスタンス
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap()
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap() {
        return new ConcurrentSkipListMap<>();
    }

    /**
     * {@link ConcurrentSkipListMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentSkipListMap}のキーの型
     * @param <V>
     *            {@link ConcurrentSkipListMap}の値の型
     * @param c
     *            {@link Comparator}
     * @return {@link ConcurrentSkipListMap}の新しいインスタンス
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(Comparator)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final Comparator<? super K> c) {
        return new ConcurrentSkipListMap<>(c);
    }

    /**
     * {@link ConcurrentSkipListMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentSkipListMap}のキーの型
     * @param <V>
     *            {@link ConcurrentSkipListMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link ConcurrentSkipListMap}の新しいインスタンス
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(Map)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final Map<? extends K, ? extends V> m) {
        return new ConcurrentSkipListMap<>(m);
    }

    /**
     * {@link ConcurrentSkipListMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link ConcurrentSkipListMap}のキーの型
     * @param <V>
     *            {@link ConcurrentSkipListMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link ConcurrentSkipListMap}の新しいインスタンス
     * @see ConcurrentSkipListMap#ConcurrentSkipListMap(SortedMap)
     */
    public static <K, V> ConcurrentSkipListMap<K, V> newConcurrentSkipListMap(final SortedMap<K, ? extends V> m) {
        return new ConcurrentSkipListMap<>(m);
    }

    /**
     * {@link ConcurrentSkipListSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentSkipListSet}の要素型
     * @return {@link ConcurrentSkipListSet}の新しいインスタンス
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet()
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet() {
        return new ConcurrentSkipListSet<>();
    }

    /**
     * {@link ConcurrentSkipListSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentSkipListSet}の要素型
     * @param c
     *            要素がセットに配置されるコレクション
     * @return {@link ConcurrentSkipListSet}の新しいインスタンス
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(Collection)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final Collection<? extends E> c) {
        return new ConcurrentSkipListSet<>(c);
    }

    /**
     * {@link ConcurrentSkipListSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentSkipListSet}の要素型
     * @param c
     *            このセットをソートするために使用されるコンパレータ
     * @return {@link ConcurrentSkipListSet}の新しいインスタンス
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(Comparator)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final Comparator<? super E> c) {
        return new ConcurrentSkipListSet<>(c);
    }

    /**
     * {@link ConcurrentSkipListSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link ConcurrentSkipListSet}の要素型
     * @param s
     *            要素がセットに配置されるコレクション
     * @return {@link ConcurrentSkipListSet}の新しいインスタンス
     * @see ConcurrentSkipListSet#ConcurrentSkipListSet(SortedSet)
     */
    public static <E> ConcurrentSkipListSet<E> newConcurrentSkipListSet(final SortedSet<? extends E> s) {
        return new ConcurrentSkipListSet<E>(s);
    }

    /**
     * {@link CopyOnWriteArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArrayList}の要素型
     * @return {@link CopyOnWriteArrayList}の新しいインスタンス
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList()
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<>();
    }

    /**
     * {@link CopyOnWriteArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArrayList}の要素型
     * @param c
     *            最初に保持していた要素のコレクション
     * @return {@link CopyOnWriteArrayList}の新しいインスタンス
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList(Collection)
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(final Collection<? extends E> c) {
        return new CopyOnWriteArrayList<>(c);
    }

    /**
     * {@link CopyOnWriteArrayList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArrayList}の要素型
     * @param toCopyIn
     *            配列 (この配列のコピーは内部配列として使用される)
     * @return {@link CopyOnWriteArrayList}の新しいインスタンス
     * @see CopyOnWriteArrayList#CopyOnWriteArrayList(Object[])
     */
    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(final E[] toCopyIn) {
        return new CopyOnWriteArrayList<>(toCopyIn);
    }

    /**
     * {@link CopyOnWriteArraySet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArraySet}の要素型
     * @return {@link CopyOnWriteArraySet}の新しいインスタンス
     * @see CopyOnWriteArraySet#CopyOnWriteArraySet()
     */
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet<>();
    }

    /**
     * {@link CopyOnWriteArraySet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArraySet}の要素型
     * @param c
     *            コレクション
     * @return {@link CopyOnWriteArraySet}の新しいインスタンス
     * @see CopyOnWriteArraySet#CopyOnWriteArraySet(Collection)
     */
    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(final Collection<? extends E> c) {
        return new CopyOnWriteArraySet<>(c);
    }

    /**
     * {@link DelayQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArraySet}の要素型
     * @return {@link DelayQueue}の新しいインスタンス
     * @see DelayQueue#DelayQueue()
     */
    public static <E extends Delayed> DelayQueue<E> newDelayQueue() {
        return new DelayQueue<>();
    }

    /**
     * {@link DelayQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link CopyOnWriteArraySet}の要素型
     * @param c
     *            コレクション
     * @return {@link DelayQueue}の新しいインスタンス
     * @see DelayQueue#DelayQueue(Collection)
     */
    public static <E extends Delayed> DelayQueue<E> newDelayQueue(final Collection<? extends E> c) {
        return new DelayQueue<>(c);
    }

    /**
     * {@link EnumMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link EnumMap}のキーの型
     * @param <V>
     *            {@link EnumMap}の値の型
     * @param keyType
     *            この {@literal enum} マップ用のキー型のクラスオブジェクト
     * @return {@link EnumMap}の新しいインスタンス
     * @see EnumMap#EnumMap(Class)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Class<K> keyType) {
        return new EnumMap<>(keyType);
    }

    /**
     * {@link EnumMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link EnumMap}のキーの型
     * @param <V>
     *            {@link EnumMap}の値の型
     * @param m
     *            この {@literal enum} マップの初期化元の {@literal enum} マップ
     * @return {@link EnumMap}の新しいインスタンス
     * @see EnumMap#EnumMap(EnumMap)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final EnumMap<K, ? extends V> m) {
        return new EnumMap<>(m);
    }

    /**
     * {@link EnumMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link EnumMap}のキーの型
     * @param <V>
     *            {@link EnumMap}の値の型
     * @param m
     *            この {@literal enum} マップの初期化元のマップ
     * @return {@link EnumMap}の新しいインスタンス
     * @see EnumMap#EnumMap(Map)
     */
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Map<K, ? extends V> m) {
        return new EnumMap<>(m);
    }

    /**
     * {@link HashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link HashMap}のキーの型
     * @param <V>
     *            {@link HashMap}の値の型
     * @return {@link HashMap}の新しいインスタンス
     * @see HashMap#HashMap()
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * {@link HashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link HashMap}のキーの型
     * @param <V>
     *            {@link HashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @return {@link HashMap}の新しいインスタンス
     * @see HashMap#HashMap(int)
     */
    public static <K, V> HashMap<K, V> newHashMap(final int initialCapacity) {
        return new HashMap<>(initialCapacity);
    }

    /**
     * {@link HashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link HashMap}のキーの型
     * @param <V>
     *            {@link HashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            サイズ変更の制御に使用される負荷係数のしきい値
     * @return {@link HashMap}の新しいインスタンス
     * @see HashMap#HashMap(int, float)
     */
    public static <K, V> HashMap<K, V> newHashMap(final int initialCapacity, final float loadFactor) {
        return new HashMap<>(initialCapacity, loadFactor);
    }

    /**
     * {@link HashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link HashMap}のキーの型
     * @param <V>
     *            {@link HashMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link HashMap}の新しいインスタンス
     * @see HashMap#HashMap(int, float)
     */
    public static <K, V> HashMap<K, V> newHashMap(final Map<? extends K, ? extends V> m) {
        return new HashMap<>(m);
    }

    /**
     * {@link HashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link HashSet}の要素型
     * @return {@link HashSet}の新しいインスタンス
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet() {
        return new HashSet<>();
    }

    /**
     * {@link HashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link HashSet}の要素型
     * @param c
     *            要素がセットに配置されるコレクション
     * @return {@link HashSet}の新しいインスタンス
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final Collection<? extends E> c) {
        return new HashSet<>(c);
    }

    /**
     * {@link HashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link HashSet}の要素型
     * @param initialCapacity
     *            初期容量
     * @return {@link HashSet}の新しいインスタンス
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final int initialCapacity) {
        return new HashSet<>(initialCapacity);
    }

    /**
     * {@link HashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link HashSet}の要素型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            負荷係数
     * @return {@link HashSet}の新しいインスタンス
     * @see HashSet#HashSet()
     */
    public static <E> HashSet<E> newHashSet(final int initialCapacity, final float loadFactor) {
        return new HashSet<>(initialCapacity, loadFactor);
    }

    /**
     * {@link Hashtable}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link Hashtable}のキーの型
     * @param <V>
     *            {@link Hashtable}の値の型
     * @return {@link Hashtable}の新しいインスタンス
     * @see Hashtable#Hashtable()
     */
    public static <K, V> Hashtable<K, V> newHashtable() {
        return new Hashtable<>();
    }

    /**
     * {@link Hashtable}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link Hashtable}のキーの型
     * @param <V>
     *            {@link Hashtable}の値の型
     * @param initialCapacity
     *            ハッシュテーブルの初期容量
     * @return {@link Hashtable}の新しいインスタンス
     * @see Hashtable#Hashtable(int)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final int initialCapacity) {
        return new Hashtable<>(initialCapacity);
    }

    /**
     * {@link Hashtable}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link Hashtable}のキーの型
     * @param <V>
     *            {@link Hashtable}の値の型
     * @param initialCapacity
     *            ハッシュテーブルの初期容量
     * @param loadFactor
     *            ハッシュテーブルの負荷係数
     * @return {@link Hashtable}の新しいインスタンス
     * @see Hashtable#Hashtable(int, float)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final int initialCapacity, final float loadFactor) {
        return new Hashtable<>(initialCapacity, loadFactor);
    }

    /**
     * {@link Hashtable}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link Hashtable}のキーの型
     * @param <V>
     *            {@link Hashtable}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link Hashtable}の新しいインスタンス
     * @see Hashtable#Hashtable(Map)
     */
    public static <K, V> Hashtable<K, V> newHashtable(final Map<? extends K, ? extends V> m) {
        return new Hashtable<>(m);
    }

    /**
     * {@link IdentityHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link IdentityHashMap}のキーの型
     * @param <V>
     *            {@link IdentityHashMap}の値の型
     * @return {@link IdentityHashMap}の新しいインスタンス
     * @see IdentityHashMap#IdentityHashMap()
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    /**
     * {@link IdentityHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link IdentityHashMap}のキーの型
     * @param <V>
     *            {@link IdentityHashMap}の値の型
     * @param expectedMaxSize
     *            マップの予想最大サイズ
     * @return {@link IdentityHashMap}の新しいインスタンス
     * @see IdentityHashMap#IdentityHashMap(int)
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(final int expectedMaxSize) {
        return new IdentityHashMap<>(expectedMaxSize);
    }

    /**
     * {@link IdentityHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link IdentityHashMap}のキーの型
     * @param <V>
     *            {@link IdentityHashMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link IdentityHashMap}の新しいインスタンス
     * @see IdentityHashMap#IdentityHashMap(Map)
     */
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap(final Map<? extends K, ? extends V> m) {
        return new IdentityHashMap<>(m);
    }

    /**
     * {@link LinkedBlockingDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingDeque}の要素型
     * @return {@link LinkedBlockingDeque}の新しいインスタンス
     * @see LinkedBlockingDeque#LinkedBlockingDeque()
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
        return new LinkedBlockingDeque<>();
    }

    /**
     * {@link LinkedBlockingDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingDeque}の要素型
     * @param c
     *            要素がリストに配置されるコレクション
     * @return {@link LinkedBlockingDeque}の新しいインスタンス
     * @see LinkedBlockingDeque#LinkedBlockingDeque(Collection)
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(final Collection<? extends E> c) {
        return new LinkedBlockingDeque<>(c);
    }

    /**
     * {@link LinkedBlockingDeque}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingDeque}の要素型
     * @param initialCapacity
     *            リストの初期容量
     * @return {@link LinkedBlockingDeque}の新しいインスタンス
     * @see LinkedBlockingDeque#LinkedBlockingDeque(int)
     */
    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(final int initialCapacity) {
        return new LinkedBlockingDeque<>(initialCapacity);
    }

    /**
     * {@link LinkedBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingQueue}の要素型
     * @return {@link LinkedBlockingQueue}の新しいインスタンス
     * @see LinkedBlockingQueue#LinkedBlockingQueue()
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
        return new LinkedBlockingQueue<>();
    }

    /**
     * {@link LinkedBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingQueue}の要素型
     * @param c
     *            最初に含む要素のコレクション
     * @return {@link LinkedBlockingQueue}の新しいインスタンス
     * @see LinkedBlockingQueue#LinkedBlockingQueue(Collection)
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(final Collection<? extends E> c) {
        return new LinkedBlockingQueue<>(c);
    }

    /**
     * {@link LinkedBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedBlockingQueue}の要素型
     * @param initialCapacity
     *            このキューの容量
     * @return {@link LinkedBlockingQueue}の新しいインスタンス
     * @see LinkedBlockingQueue#LinkedBlockingQueue(int)
     */
    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(final int initialCapacity) {
        return new LinkedBlockingQueue<>(initialCapacity);
    }

    /**
     * {@link LinkedHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link LinkedHashMap}のキーの型
     * @param <V>
     *            {@link LinkedHashMap}の値の型
     * @return {@link LinkedHashMap}の新しいインスタンス
     * @see LinkedHashMap#LinkedHashMap()
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * {@link LinkedHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link LinkedHashMap}のキーの型
     * @param <V>
     *            {@link LinkedHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @return {@link LinkedHashMap}の新しいインスタンス
     * @see LinkedHashMap#LinkedHashMap(int)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final int initialCapacity) {
        return new LinkedHashMap<>(initialCapacity);
    }

    /**
     * {@link LinkedHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link LinkedHashMap}のキーの型
     * @param <V>
     *            {@link LinkedHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            負荷係数
     * @return {@link LinkedHashMap}の新しいインスタンス
     * @see LinkedHashMap#LinkedHashMap(int, float)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final int initialCapacity, final float loadFactor) {
        return new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * {@link LinkedHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link LinkedHashMap}のキーの型
     * @param <V>
     *            {@link LinkedHashMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link LinkedHashMap}の新しいインスタンス
     * @see LinkedHashMap#LinkedHashMap(Map)
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final Map<? extends K, ? extends V> m) {
        return new LinkedHashMap<>(m);
    }

    /**
     * {@link LinkedHashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedHashSet}の要素型
     * @return {@link LinkedHashSet}の新しいインスタンス
     * @see LinkedHashSet#LinkedHashSet()
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet<>();
    }

    /**
     * {@link LinkedHashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedHashSet}の要素型
     * @param c
     *            要素がセットに配置されるコレクション
     * @return {@link LinkedHashSet}の新しいインスタンス
     * @see LinkedHashSet#LinkedHashSet(Collection)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final Collection<? extends E> c) {
        return new LinkedHashSet<>(c);
    }

    /**
     * {@link LinkedHashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedHashSet}の要素型
     * @param initialCapacity
     *            初期容量
     * @return {@link LinkedHashSet}の新しいインスタンス
     * @see LinkedHashSet#LinkedHashSet(int)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final int initialCapacity) {
        return new LinkedHashSet<>(initialCapacity);
    }

    /**
     * {@link LinkedHashSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedHashSet}の要素型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            負荷係数
     * @return {@link LinkedHashSet}の新しいインスタンス
     * @see LinkedHashSet#LinkedHashSet(int, float)
     */
    public static <E> LinkedHashSet<E> newLinkedHashSet(final int initialCapacity, final float loadFactor) {
        return new LinkedHashSet<>(initialCapacity, loadFactor);
    }

    /**
     * {@link LinkedList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedList}の要素型
     * @return {@link LinkedList}の新しいインスタンス
     * @see LinkedList#LinkedList()
     */
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<>();
    }

    /**
     * {@link LinkedList}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link LinkedList}の要素型
     * @param c
     *            要素がリストに配置されるコレクション
     * @return {@link LinkedList}の新しいインスタンス
     * @see LinkedList#LinkedList(Collection)
     */
    public static <E> LinkedList<E> newLinkedList(final Collection<? extends E> c) {
        return new LinkedList<>(c);
    }

    /**
     * {@link PriorityBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityBlockingQueue}の要素型
     * @return {@link PriorityBlockingQueue}の新しいインスタンス
     * @see PriorityBlockingQueue#PriorityBlockingQueue()
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * {@link PriorityBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityBlockingQueue}の要素型
     * @param c
     *            最初に含む要素のコレクション
     * @return {@link PriorityBlockingQueue}の新しいインスタンス
     * @see PriorityBlockingQueue#PriorityBlockingQueue(Collection)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final Collection<? extends E> c) {
        return new PriorityBlockingQueue<>(c);
    }

    /**
     * {@link PriorityBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityBlockingQueue}の要素型
     * @param initialCapacity
     *            この優先度キューの初期容量
     * @return {@link PriorityBlockingQueue}の新しいインスタンス
     * @see PriorityBlockingQueue#PriorityBlockingQueue(int)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final int initialCapacity) {
        return new PriorityBlockingQueue<>(initialCapacity);
    }

    /**
     * {@link PriorityBlockingQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityBlockingQueue}の要素型
     * @param initialCapacity
     *            この優先度キューの初期容量
     * @param comparator
     *            この優先度キューの順序付けに使用するコンパレータ
     * @return {@link PriorityBlockingQueue}の新しいインスタンス
     * @see PriorityBlockingQueue#PriorityBlockingQueue(int, Comparator)
     */
    public static <E> PriorityBlockingQueue<E> newPriorityBlockingQueue(final int initialCapacity, final Comparator<? super E> comparator) {
        return new PriorityBlockingQueue<>(initialCapacity, comparator);
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue()
     */
    public static <E> PriorityQueue<E> newPriorityQueue() {
        return new PriorityQueue<>();
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @param c
     *            要素が優先度キューに配置されるコレクション
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue(Collection)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final Collection<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @param initialCapacity
     *            この優先度キューの初期容量
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue(int)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final int initialCapacity) {
        return new PriorityQueue<>(initialCapacity);
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @param initialCapacity
     *            この優先度キューの初期容量
     * @param comparator
     *            この優先度キューの順序付けに使用するコンパレータ
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue(int, Comparator)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final int initialCapacity, final Comparator<? super E> comparator) {
        return new PriorityQueue<>(initialCapacity, comparator);
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @param c
     *            要素が優先度キューに配置されるコレクション
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue(PriorityQueue)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final PriorityQueue<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * {@link PriorityQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link PriorityQueue}の要素型
     * @param c
     *            要素が優先度キューに配置されるコレクション
     * @return {@link PriorityQueue}の新しいインスタンス
     * @see PriorityQueue#PriorityQueue(SortedSet)
     */
    public static <E> PriorityQueue<E> newPriorityQueue(final SortedSet<? extends E> c) {
        return new PriorityQueue<>(c);
    }

    /**
     * {@link Stack}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link Stack}の要素型
     * @return {@link Stack}の新しいインスタンス
     * @see Stack#Stack()
     */
    public static <E> Stack<E> newStack() {
        return new Stack<>();
    }

    /**
     * {@link SynchronousQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link SynchronousQueue}の要素型
     * @return {@link SynchronousQueue}の新しいインスタンス
     * @see SynchronousQueue#SynchronousQueue()
     */
    public static <E> SynchronousQueue<E> newSynchronousQueue() {
        return new SynchronousQueue<>();
    }

    /**
     * {@link SynchronousQueue}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link SynchronousQueue}の要素型
     * @param fair
     *            {@literal true} の場合、待機中のスレッドは FIFO
     *            の順序でアクセスが決定される。そうでない場合、順序は未指定
     * @return {@link SynchronousQueue}の新しいインスタンス
     * @see SynchronousQueue#SynchronousQueue()
     */
    public static <E> SynchronousQueue<E> newSynchronousQueue(final boolean fair) {
        return new SynchronousQueue<>(fair);
    }

    /**
     * {@link TreeMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link TreeMap}のキーの型
     * @param <V>
     *            {@link TreeMap}の値の型
     * @return {@link TreeMap}の新しいインスタンス
     * @see TreeMap#TreeMap()
     */
    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    /**
     * {@link TreeMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link TreeMap}のキーの型
     * @param <V>
     *            {@link TreeMap}の値の型
     * @param c
     *            {@link Comparator}
     * @return {@link TreeMap}の新しいインスタンス
     * @see TreeMap#TreeMap(Comparator)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final Comparator<? super K> c) {
        return new TreeMap<>(c);
    }

    /**
     * {@link TreeMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link TreeMap}のキーの型
     * @param <V>
     *            {@link TreeMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link TreeMap}の新しいインスタンス
     * @see TreeMap#TreeMap(Map)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final Map<? extends K, ? extends V> m) {
        return new TreeMap<>(m);
    }

    /**
     * {@link TreeMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link TreeMap}のキーの型
     * @param <V>
     *            {@link TreeMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link TreeMap}の新しいインスタンス
     * @see TreeMap#TreeMap(SortedMap)
     */
    public static <K, V> TreeMap<K, V> newTreeMap(final SortedMap<K, ? extends V> m) {
        return new TreeMap<>(m);
    }

    /**
     * {@link TreeSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link TreeSet}の要素型
     * @return {@link TreeSet}の新しいインスタンス
     * @see TreeSet#TreeSet()
     */
    public static <E> TreeSet<E> newTreeSet() {
        return new TreeSet<>();
    }

    /**
     * {@link TreeSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link TreeSet}の要素型
     * @param c
     *            要素がセットに配置されるコレクション
     * @return {@link TreeSet}の新しいインスタンス
     * @see TreeSet#TreeSet(Collection)
     */
    public static <E> TreeSet<E> newTreeSet(final Collection<? extends E> c) {
        return new TreeSet<>(c);
    }

    /**
     * {@link TreeSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link TreeSet}の要素型
     * @param c
     *            このセットをソートするために使用されるコンパレータ
     * @return {@link TreeSet}の新しいインスタンス
     * @see TreeSet#TreeSet(Comparator)
     */
    public static <E> TreeSet<E> newTreeSet(final Comparator<? super E> c) {
        return new TreeSet<>(c);
    }

    /**
     * {@link TreeSet}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link TreeSet}の要素型
     * @param s
     *            要素がセットに配置されるコレクション
     * @return {@link TreeSet}の新しいインスタンス
     * @see TreeSet#TreeSet(SortedSet)
     */
    public static <E> TreeSet<E> newTreeSet(final SortedSet<? extends E> s) {
        return new TreeSet<E>(s);
    }

    /**
     * {@link Vector}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link Vector}の要素型
     * @return {@link Vector}の新しいインスタンス
     * @see Vector#Vector()
     */
    public static <E> Vector<E> newVector() {
        return new Vector<>();
    }

    /**
     * {@link Vector}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link Vector}の要素型
     * @param c
     *            要素がセットに配置されるコレクション
     * @return {@link Vector}の新しいインスタンス
     * @see Vector#Vector(Collection)
     */
    public static <E> Vector<E> newVector(final Collection<? extends E> c) {
        return new Vector<>(c);
    }

    /**
     * {@link Vector}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link Vector}の要素型
     * @param initialCapacity
     *            {@link Vector}の初期容量
     * @return {@link Vector}の新しいインスタンス
     * @see Vector#Vector(int)
     */
    public static <E> Vector<E> newVector(final int initialCapacity) {
        return new Vector<>(initialCapacity);
    }

    /**
     * {@link Vector}の新しいインスタンスを作成して返します。
     *
     * @param <E>
     *            {@link Vector}の要素型
     * @param initialCapacity
     *            {@link Vector}の初期容量
     * @param capacityIncrement
     *            {@link Vector}があふれたときに増加される容量
     * @return {@link Vector}の新しいインスタンス
     * @see Vector#Vector(int, int)
     */
    public static <E> Vector<E> newVector(final int initialCapacity, final int capacityIncrement) {
        return new Vector<>(initialCapacity, capacityIncrement);
    }

    /**
     * {@link WeakHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link WeakHashMap}のキーの型
     * @param <V>
     *            {@link WeakHashMap}の値の型
     * @return {@link WeakHashMap}の新しいインスタンス
     * @see WeakHashMap#WeakHashMap()
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap() {
        return new WeakHashMap<>();
    }

    /**
     * {@link WeakHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link WeakHashMap}のキーの型
     * @param <V>
     *            {@link WeakHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @return {@link WeakHashMap}の新しいインスタンス
     * @see WeakHashMap#WeakHashMap(int)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final int initialCapacity) {
        return new WeakHashMap<>(initialCapacity);
    }

    /**
     * {@link WeakHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link WeakHashMap}のキーの型
     * @param <V>
     *            {@link WeakHashMap}の値の型
     * @param initialCapacity
     *            初期容量
     * @param loadFactor
     *            サイズ変更の制御に使用される負荷係数のしきい値
     * @return {@link WeakHashMap}の新しいインスタンス
     * @see WeakHashMap#WeakHashMap(int, float)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final int initialCapacity, final float loadFactor) {
        return new WeakHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * {@link WeakHashMap}の新しいインスタンスを作成して返します。
     *
     * @param <K>
     *            {@link WeakHashMap}のキーの型
     * @param <V>
     *            {@link WeakHashMap}の値の型
     * @param m
     *            作成されるマップに配置されるマップ
     * @return {@link WeakHashMap}の新しいインスタンス
     * @see WeakHashMap#WeakHashMap(Map)
     */
    public static <K, V> WeakHashMap<K, V> newWeakHashMap(final Map<? extends K, ? extends V> m) {
        return new WeakHashMap<>(m);
    }

    /**
     * マップが指定されたキーを含んでいない場合は、キーを指定された値に関連付けます。
     * <p>
     * マップがすでに指定されたキーを含んでいる場合は、 キーに関連づけられている値を返します。 マップは変更されず、 指定された値は使われません。
     * マップがまだ指定されたキーを含んでいない場合は、 指定された値を値を返します。 マップは変更され、指定されたキーと指定された値が関連づけられます。
     * いずれの場合も、返される値はマップがその時点でキーと関連づけている値です。
     * </p>
     *
     * @param <K>
     *            {@link HashMap}のキーの型
     * @param <V>
     *            {@link HashMap}の値の型
     * @param map
     *            マップ
     * @param key
     *            指定される値が関連付けられるキー
     * @param value
     *            指定されるキーに関連付けられる値
     * @return 指定されたキーと関連付けられていた以前の値または、キーに関連付けられる値
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
     * {@link Collection}が{@literal null}または要素が無い場合は{@literal true}を返します。
     *
     * @param collection
     *            コレクション
     * @return コレクションが{@literal null}または要素が無い場合は{@literal true}
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * {@link Collection}が{@literal null}でも要素が無いわけでもない場合は{@literal true}を返します。
     *
     * @param collection
     *            コレクション
     * @return コレクションが{@literal null}でも要素が無いわけでもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * {@link Map}が{@literal null}または要素が無い場合は{@literal true}を返します。
     *
     * @param map
     *            マップ
     * @return マップが{@literal null}または要素が無い場合は{@literal true}
     */
    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * {@link Map}が{@literal null}でも要素が無いわけでもない場合は{@literal true}を返します。
     *
     * @param map
     *            マップ
     * @return マップが{@literal null}でも要素が無いわけでもない場合は{@literal true}
     */
    public static boolean isNotEmpty(final Map<?, ?> map) {
        return !isEmpty(map);
    }

}
