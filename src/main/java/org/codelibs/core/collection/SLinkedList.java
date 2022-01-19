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

import static org.codelibs.core.misc.AssertionUtil.assertIndex;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * Seasar2用の連結リストです。
 *
 * @author higa
 * @param <E>
 *            要素の型
 *
 */
public class SLinkedList<E> implements Cloneable, Externalizable {

    static final long serialVersionUID = 1L;

    private transient Entry header = new Entry(null, null, null);

    private transient int size = 0;

    /**
     * {@link SLinkedList}を作成します。
     */
    public SLinkedList() {
        header.next = header;
        header.previous = header;
    }

    /**
     * 最初の要素を返します。
     *
     * @return 最初の要素
     */
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return getFirstEntry().element;
    }

    /**
     * 最後の要素を返します。
     *
     * @return 最後の要素
     */
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return getLastEntry().element;
    }

    /**
     * 最初のエントリを返します。
     *
     * @return 最初のエントリ
     */
    public Entry getFirstEntry() {
        if (isEmpty()) {
            return null;
        }
        return header.next;
    }

    /**
     * 最後のエントリを返します。
     *
     * @return 最後のエントリ
     */
    public Entry getLastEntry() {
        if (isEmpty()) {
            return null;
        }
        return header.previous;
    }

    /**
     * 最初の要素を削除します。
     *
     * @return 最初の要素
     */
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final E first = header.next.element;
        header.next.remove();
        return first;
    }

    /**
     * 最後の要素を削除します。
     *
     * @return 最後の要素
     */
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        final E last = header.previous.element;
        header.previous.remove();
        return last;
    }

    /**
     * 先頭に追加します。
     *
     * @param element
     *            追加するオブジェクト
     */
    public void addFirst(final E element) {
        header.next.addBefore(element);
    }

    /**
     * 最後に追加します。
     *
     * @param element
     *            追加するオブジェクト
     */
    public void addLast(final E element) {
        header.addBefore(element);
    }

    /**
     * 指定した位置にオブジェクトを追加します。
     *
     * @param index
     *            位置
     * @param element
     *            要素
     */
    public void add(final int index, final E element) {
        getEntry(index).addBefore(element);
    }

    /**
     * 要素の数を返します。
     *
     * @return 要素の数
     */
    public int size() {
        return size;
    }

    /**
     * 空かどうかを返します。
     *
     * @return 空かどうか
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 要素が含まれているかどうかを返します。
     *
     * @param element
     *            要素
     * @return 要素が含まれているかどうか
     */
    public boolean contains(final E element) {
        return indexOf(element) != -1;
    }

    /**
     * 要素を削除します。
     *
     * @param element
     *            要素
     * @return 削除されたかどうか
     */
    public boolean remove(final E element) {
        if (element == null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element == null) {
                    e.remove();
                    return true;
                }
            }
        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (element.equals(e.element)) {
                    e.remove();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 指定した位置の要素を削除します。
     *
     * @param index
     *            位置
     * @return 削除された要素
     */
    public Object remove(final int index) {
        final Entry e = getEntry(index);
        e.remove();
        return e.element;
    }

    /**
     * 要素を空にします。
     */
    public void clear() {
        header.next = header;
        header.previous = header;
        size = 0;
    }

    /**
     * インデックスで指定された位置のエントリを返します。
     *
     * @param index
     *            インデックス
     * @return エントリ
     */
    public Entry getEntry(final int index) {
        assertIndex(0 <= index && index < size, "Index: " + index + ", Size: " + size);
        Entry e = header;
        if (index < size / 2) {
            for (int i = 0; i <= index; i++) {
                e = e.next;
            }
        } else {
            for (int i = size; i > index; i--) {
                e = e.previous;
            }
        }
        return e;
    }

    /**
     * インデックスで指定された位置の要素を返します。
     *
     * @param index
     *            インデックス
     * @return 要素
     */
    public E get(final int index) {
        return getEntry(index).element;
    }

    /**
     * インデックスで指定された位置に要素を設定します。
     *
     * @param index
     *            インデックス
     * @param element
     *            要素
     * @return 元の要素
     */
    public E set(final int index, final E element) {
        final Entry entry = getEntry(index);
        final E oldValue = entry.element;
        entry.element = element;
        return oldValue;
    }

    /**
     * 位置を返します。
     *
     * @param element
     *            要素
     * @return 位置
     */
    public int indexOf(final E element) {
        int index = 0;
        if (element == null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element == null) {
                    return index;
                }
                index++;
            }
        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (element.equals(e.element)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    @Override
    public void writeExternal(final ObjectOutput s) throws IOException {
        s.writeInt(size);
        for (Entry e = header.next; e != header; e = e.next) {
            s.writeObject(e.element);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readExternal(final ObjectInput s) throws IOException, ClassNotFoundException {
        final int size = s.readInt();
        header = new Entry(null, null, null);
        header.next = header;
        header.previous = header;
        for (int i = 0; i < size; i++) {
            addLast((E) s.readObject());
        }
    }

    @Override
    public Object clone() {
        final SLinkedList<E> copy = new SLinkedList<>();
        for (Entry e = header.next; e != header; e = e.next) {
            copy.addLast(e.element);
        }
        return copy;
    }

    /**
     * 配列に変換します。
     *
     * @return 配列
     */
    public Object[] toArray() {
        final Object[] result = new Object[size];
        int i = 0;
        for (Entry e = header.next; e != header; e = e.next) {
            result[i++] = e.element;
        }
        return result;
    }

    /**
     * 配列に変換します。
     *
     * @param array
     *            要素の格納先の配列。配列のサイズが十分でない場合は、同じ実行時の型で新しい配列が格納用として割り当てられる
     * @return 配列
     */
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] array) {
        if (array.length < size) {
            array = (E[]) Array.newInstance(array.getClass().getComponentType(), size);
        }
        int i = 0;
        for (Entry e = header.next; e != header; e = e.next) {
            array[i++] = e.element;
        }
        for (i = size; i < array.length; ++i) {
            array[i] = null;
        }
        return array;
    }

    /**
     * 要素を格納するエントリです。
     */
    public class Entry {

        /** 要素 */
        protected E element;

        /** 次のエントリ */
        protected Entry next;

        /** 前のエントリ */
        protected Entry previous;

        Entry(final E element, final Entry next, final Entry previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        /**
         * 要素を返します。
         *
         * @return 要素
         */
        public E getElement() {
            return element;
        }

        /**
         * 次のエントリを返します。
         *
         * @return 次のエントリ
         */
        public Entry getNext() {
            if (next != SLinkedList.this.header) {
                return next;
            }
            return null;
        }

        /**
         * 前のエントリを返します。
         *
         * @return 前のエントリ
         */
        public Entry getPrevious() {
            if (previous != SLinkedList.this.header) {
                return previous;
            }
            return null;
        }

        /**
         * 要素を削除します。
         */
        public void remove() {
            previous.next = next;
            next.previous = previous;
            --size;
        }

        /**
         * 前に追加します。
         *
         * @param o
         *            要素
         * @return 追加されたエントリ
         */
        public Entry addBefore(final E o) {
            final Entry newEntry = new Entry(o, this, previous);
            previous.next = newEntry;
            previous = newEntry;
            ++size;
            return newEntry;
        }

    }

}
