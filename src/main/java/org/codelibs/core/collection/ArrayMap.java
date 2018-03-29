/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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

import static org.codelibs.core.misc.AssertionUtil.assertIndex;
import static org.codelibs.core.misc.AssertionUtil.assertState;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.exception.ClNoSuchElementException;

/**
 * 配列の性質を併せ持つ {@link Map}です。
 *
 * @author higa
 * @param <K>
 *            キーの型
 * @param <V>
 *            値の型
 *
 */
public class ArrayMap<K, V> extends AbstractMap<K, V> implements Map<K, V>,
        Cloneable, Externalizable {

    private static final long serialVersionUID = 1L;

    /** 初期容量のデフォルト値 */
    public static final int INITIAL_CAPACITY = 17;

    /** 負荷係数のデフォルト値 */
    public static final float LOAD_FACTOR = 0.75f;

    /** 負荷係数 */
    protected transient int threshold;

    /** マップとしてのエントリ */
    protected transient Entry<K, V>[] mapTable;

    /** 配列としてのエントリ */
    protected transient Entry<K, V>[] listTable;

    /** 要素数 */
    protected transient int size = 0;

    /** {@link Set}としてのビュー */
    protected transient Set<? extends Map.Entry<K, V>> entrySet = null;

    /**
     * デフォルトの初期容量を持つインスタンスを構築します。
     */
    public ArrayMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * 指定された初期容量を持つインスタンスを構築します。
     *
     * @param initialCapacity
     *            初期容量
     */
    @SuppressWarnings("unchecked")
    public ArrayMap(int initialCapacity) {
        if (initialCapacity <= 0) {
            initialCapacity = INITIAL_CAPACITY;
        }
        mapTable = new Entry[initialCapacity];
        listTable = new Entry[initialCapacity];
        threshold = (int) (initialCapacity * LOAD_FACTOR);
    }

    /**
     * 指定された{@link Map}と同じマッピングでインスタンスを構築します。
     *
     * @param map
     *            マッピングがこのマップに配置されるマップ
     */
    public ArrayMap(final Map<? extends K, ? extends V> map) {
        this((int) (map.size() / LOAD_FACTOR) + 1);
        putAll(map);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsValue(final Object value) {
        return indexOf(value) >= 0;
    }

    /**
     * 値に対するインデックスを返します。
     *
     * @param value
     *            値
     * @return 値に対するインデックス。値が含まれていない場合は{@literal -1}
     */
    public int indexOf(final Object value) {
        if (value != null) {
            for (int i = 0; i < size; i++) {
                if (value.equals(listTable[i].value)) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (listTable[i].value == null) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean containsKey(final Object key) {
        final Entry<K, V>[] tbl = mapTable;
        if (key != null) {
            final int hashCode = key.hashCode();
            final int index = (hashCode & 0x7FFFFFFF) % tbl.length;
            for (Entry<K, V> e = tbl[index]; e != null; e = e.next) {
                if (e.hashCode == hashCode && key.equals(e.key)) {
                    return true;
                }
            }
        } else {
            for (Entry<K, V> e = tbl[0]; e != null; e = e.next) {
                if (e.key == null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(final Object key) {
        final Entry<K, V>[] tbl = mapTable;
        if (key != null) {
            final int hashCode = key.hashCode();
            final int index = (hashCode & 0x7FFFFFFF) % tbl.length;
            for (Entry<K, V> e = tbl[index]; e != null; e = e.next) {
                if (e.hashCode == hashCode && key.equals(e.key)) {
                    return e.value;
                }
            }
        } else {
            for (Entry<K, V> e = tbl[0]; e != null; e = e.next) {
                if (e.key == null) {
                    return e.value;
                }
            }
        }
        return null;
    }

    /**
     * インデックスで指定された位置の値を返します。
     *
     * @param index
     *            インデックス
     * @return インデックスで指定された位置の値
     */
    public V getAt(final int index) {
        return getEntryAt(index).getValue();
    }

    /**
     * インデックスで指定された位置のキーを返します。
     *
     * @param index
     *            インデックス
     * @return インデックスで指定された位置のキー
     */
    public K getKeyAt(final int index) {
        return getEntryAt(index).getKey();
    }

    /**
     * インデックスで指定された位置の{@link java.util.Map.Entry}を返します。
     *
     * @param index
     *            インデックス
     * @return インデックスで指定された位置の{@link java.util.Map.Entry}
     */
    public Map.Entry<K, V> getEntryAt(final int index) {
        assertIndex(index < size, "Index:" + index + ", Size:" + size);
        return listTable[index];
    }

    @Override
    public V put(final K key, final V value) {
        int hashCode = 0;
        int index = 0;

        if (key != null) {
            hashCode = key.hashCode();
            index = (hashCode & 0x7FFFFFFF) % mapTable.length;
            for (Entry<K, V> e = mapTable[index]; e != null; e = e.next) {
                if (e.hashCode == hashCode && key.equals(e.key)) {
                    return swapValue(e, value);
                }
            }
        } else {
            for (Entry<K, V> e = mapTable[0]; e != null; e = e.next) {
                if (e.key == null) {
                    return swapValue(e, value);
                }
            }
        }
        ensureCapacity();
        index = (hashCode & 0x7FFFFFFF) % mapTable.length;
        final Entry<K, V> e = new Entry<>(hashCode, key, value,
                mapTable[index]);
        mapTable[index] = e;
        listTable[size++] = e;
        return null;
    }

    /**
     * インデックスで指定された位置に値を設定します。
     *
     * @param index
     *            インデックス
     * @param value
     *            値
     */
    public void setAt(final int index, final V value) {
        getEntryAt(index).setValue(value);
    }

    @Override
    public V remove(final Object key) {
        final Entry<K, V> e = removeMap(key);
        if (e != null) {
            final V value = e.value;
            removeList(entryIndexOf(e));
            e.clear();
            return value;
        }
        return null;
    }

    /**
     * インデックスで指定された位置のエントリを削除します。
     *
     * @param index
     *            インデックス
     * @return インデックスで指定された位置にあったエントリの値
     */
    public V removeAt(final int index) {
        final Entry<K, V> e = removeList(index);
        final V value = e.value;
        removeMap(e.key);
        e.clear();
        return value;
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> map) {
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < mapTable.length; i++) {
            mapTable[i] = null;
        }
        for (int i = 0; i < listTable.length; i++) {
            listTable[i] = null;
        }
        size = 0;
    }

    /**
     * 配列に変換します。
     *
     * @return 配列
     */
    public Object[] toArray() {
        final Object[] array = new Object[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = getAt(i);
        }
        return array;
    }

    /**
     * 配列に変換します。
     *
     * @param proto
     *            要素の格納先の配列。配列のサイズが十分でない場合は、同じ実行時の型で新しい配列が格納用として割り当てられる
     * @return 配列
     */
    public V[] toArray(final V[] proto) {
        @SuppressWarnings("unchecked")
        final V[] array = proto.length >= size ? proto : (V[]) Array
                .newInstance(proto.getClass().getComponentType(), size);
        for (int i = 0; i < array.length; i++) {
            array[i] = getAt(i);
        }
        if (array.length > size) {
            array[size] = null;
        }
        return array;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof ArrayMap)) {
            return false;
        }
        @SuppressWarnings("unchecked")
        final ArrayMap<K, V> e = (ArrayMap<K, V>) o;
        if (size != e.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!listTable[i].equals(e.listTable[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (int i = 0; i < size; i++) {
            h += listTable[i].hashCode();
        }
        return h;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        if (entrySet == null) {
            entrySet = new AbstractSet<Entry<K, V>>() {
                @Override
                public Iterator<Entry<K, V>> iterator() {
                    return new ArrayMapIterator();
                }

                @Override
                public boolean contains(final Object o) {
                    final Entry<K, V> entry = (Entry<K, V>) o;
                    final int index = (entry.hashCode & 0x7FFFFFFF)
                            % mapTable.length;
                    for (Entry<K, V> e = mapTable[index]; e != null; e = e.next) {
                        if (e.equals(entry)) {
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public boolean remove(final Object o) {
                    if (!(o instanceof Entry)) {
                        return false;
                    }
                    final Entry<K, V> entry = (Entry<K, V>) o;
                    return ArrayMap.this.remove(entry.key) != null;
                }

                @Override
                public int size() {
                    return size;
                }

                @Override
                public void clear() {
                    ArrayMap.this.clear();
                }
            };
        }
        return (Set<Map.Entry<K, V>>) entrySet;
    }

    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeInt(listTable.length);
        out.writeInt(size);
        for (int i = 0; i < size; i++) {
            out.writeObject(listTable[i].key);
            out.writeObject(listTable[i].value);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(final ObjectInput in) throws IOException,
            ClassNotFoundException {
        final int num = in.readInt();
        mapTable = new Entry[num];
        listTable = new Entry[num];
        threshold = (int) (num * LOAD_FACTOR);
        final int size = in.readInt();
        for (int i = 0; i < size; i++) {
            final K key = (K) in.readObject();
            final V value = (V) in.readObject();
            put(key, value);
        }
    }

    @Override
    public Object clone() {
        final ArrayMap<K, V> copy = new ArrayMap<>();
        copy.threshold = threshold;
        copy.mapTable = Arrays.copyOf(mapTable, size);
        copy.listTable = Arrays.copyOf(listTable, size);
        copy.size = size;
        return copy;
    }

    /**
     * エントリのインデックスを返します。
     *
     * @param entry
     *            エントリ
     * @return エントリのインデックス
     */
    protected int entryIndexOf(final Entry<K, V> entry) {
        for (int i = 0; i < size; i++) {
            if (listTable[i] == entry) {
                return i;
            }
        }
        return -1;
    }

    /**
     * キーで指定されたエントリをマップのエントリから削除します。
     *
     * @param key
     *            キー
     * @return 削除されたエントリ。キーに対応するエントリがなかった場合は{@literal null}
     */
    protected Entry<K, V> removeMap(final Object key) {
        int hashCode = 0;
        int index = 0;

        if (key != null) {
            hashCode = key.hashCode();
            index = (hashCode & 0x7FFFFFFF) % mapTable.length;
            for (Entry<K, V> e = mapTable[index], prev = null; e != null; prev = e, e = e.next) {
                if (e.hashCode == hashCode && key.equals(e.key)) {
                    if (prev != null) {
                        prev.next = e.next;
                    } else {
                        mapTable[index] = e.next;
                    }
                    return e;
                }
            }
        } else {
            for (Entry<K, V> e = mapTable[index], prev = null; e != null; prev = e, e = e.next) {
                if (e.hashCode == hashCode && e.key == null) {
                    if (prev != null) {
                        prev.next = e.next;
                    } else {
                        mapTable[index] = e.next;
                    }
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * インデックスで指定されたエントリをリストのエントリから削除します。
     *
     * @param index
     *            削除するエントリのインデックス
     * @return 削除されたエントリ
     */
    protected Entry<K, V> removeList(final int index) {
        final Entry<K, V> e = listTable[index];
        final int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(listTable, index + 1, listTable, index, numMoved);
        }
        listTable[--size] = null;
        return e;
    }

    /**
     * サイズが閾値を超えた場合に容量を確保します。
     */
    protected void ensureCapacity() {
        if (size >= threshold) {
            final Entry<K, V>[] oldTable = listTable;
            final int newCapacity = oldTable.length * 2 + 1;
            @SuppressWarnings("unchecked")
            final Entry<K, V>[] newMapTable = new Entry[newCapacity];
            @SuppressWarnings("unchecked")
            final Entry<K, V>[] newListTable = new Entry[newCapacity];
            threshold = (int) (newCapacity * LOAD_FACTOR);
            System.arraycopy(oldTable, 0, newListTable, 0, size);
            for (int i = 0; i < size; i++) {
                Entry<K, V> old = oldTable[i];
                final int index = (old.hashCode & 0x7FFFFFFF) % newCapacity;
                final Entry<K, V> e = old;
                old = old.next;
                e.next = newMapTable[index];
                newMapTable[index] = e;
            }
            mapTable = newMapTable;
            listTable = newListTable;
        }
    }

    /**
     * エントリの値を新しい値で置き換えます。
     *
     * @param entry
     *            エントリ
     * @param value
     *            エントリの新しい値
     * @return エントリの置き換えられる前の値
     */
    protected V swapValue(final Entry<K, V> entry, final V value) {
        final V old = entry.value;
        entry.value = value;
        return old;
    }

    /**
     * {@link ArrayMap}用の{@link Iterator}です。
     */
    protected class ArrayMapIterator implements Iterator<Entry<K, V>> {

        /** 現在のインデックス */
        protected int current = 0;

        /** 最後にアクセスした要素のインデックス */
        protected int last = -1;

        @Override
        public boolean hasNext() {
            return current != size;
        }

        @Override
        public Entry<K, V> next() {
            try {
                final Entry<K, V> n = listTable[current];
                last = current++;
                return n;
            } catch (final IndexOutOfBoundsException e) {
                throw new ClNoSuchElementException("current=" + current);
            }
        }

        @Override
        public void remove() {
            assertState(last != -1, "last == -1");
            ArrayMap.this.remove(last);
            if (last < current) {
                current--;
            }
            last = -1;
        }
    }

    /**
     * {@link ArrayMap}の{@link Map.Entry}です。
     *
     * @param <K>
     *            キーの型
     * @param <V>
     *            キーの値
     */
    protected static class Entry<K, V> implements Map.Entry<K, V>,
            Externalizable {

        private static final long serialVersionUID = -6625980241350717177L;

        /** ハッシュ値 */
        protected transient int hashCode;

        /** キー */
        protected transient K key;

        /** 値 */
        protected transient V value;

        /** 次のエントリ */
        protected transient Entry<K, V> next;

        /**
         * インスタンスを構築します。
         */
        public Entry() {
        }

        /**
         * インスタンスを構築します。
         *
         * @param hashCode
         *            ハッシュ値
         * @param key
         *            キー
         * @param value
         *            値
         * @param next
         *            次のエントリ
         */
        public Entry(final int hashCode, final K key, final V value,
                final Entry<K, V> next) {
            this.hashCode = hashCode;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(final V value) {
            final V oldValue = value;
            this.value = value;
            return oldValue;
        }

        /**
         * 状態をクリアします。
         */
        public void clear() {
            key = null;
            value = null;
            next = null;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Entry)) {
                return false;
            }
            @SuppressWarnings("unchecked")
            final Entry<K, V> e = (Entry<K, V>) o;
            return (key != null ? key.equals(e.key) : e.key == null)
                    && (value != null ? value.equals(e.value) : e.value == null);
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }

        @Override
        public void writeExternal(final ObjectOutput s) throws IOException {
            s.writeInt(hashCode);
            s.writeObject(key);
            s.writeObject(value);
            s.writeObject(next);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void readExternal(final ObjectInput s) throws IOException,
                ClassNotFoundException {
            hashCode = s.readInt();
            key = (K) s.readObject();
            value = (V) s.readObject();
            next = (Entry<K, V>) s.readObject();
        }
    }

}
