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

import static org.codelibs.core.misc.AssertionUtil.assertIndex;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Array;
import java.util.NoSuchElementException;

/**
 * Linked list for Seasar2.
 *
 * @author higa
 * @param <E> the element type
 */
public class SLinkedList<E> implements Cloneable, Externalizable {

    static final long serialVersionUID = 1L;

    private transient Entry header = new Entry(null, null, null);

    private transient int size = 0;

    /**
     * Creates an {@link SLinkedList}.
     */
    public SLinkedList() {
        header.next = header;
        header.previous = header;
    }

    /**
     * Returns the first element.
     *
     * @return the first element
     */
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return getFirstEntry().element;
    }

    /**
     * Returns the last element.
     *
     * @return the last element
     */
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return getLastEntry().element;
    }

    /**
     * Returns the first entry.
     *
     * @return the first entry
     */
    public Entry getFirstEntry() {
        if (isEmpty()) {
            return null;
        }
        return header.next;
    }

    /**
     * Returns the last entry.
     *
     * @return the last entry
     */
    public Entry getLastEntry() {
        if (isEmpty()) {
            return null;
        }
        return header.previous;
    }

    /**
     * Removes the first element.
     *
     * @return the first element
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
     * Removes the last element.
     *
     * @return the last element
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
     * Adds an element at the beginning.
     *
     * @param element the object to be added
     */
    public void addFirst(final E element) {
        header.next.addBefore(element);
    }

    /**
     * Adds an element at the end.
     *
     * @param element the object to be added
     */
    public void addLast(final E element) {
        header.addBefore(element);
    }

    /**
     * Inserts an object at the specified position.
     *
     * @param index the position
     * @param element the element
     */
    public void add(final int index, final E element) {
        getEntry(index).addBefore(element);
    }

    /**
     * Returns the number of elements.
     *
     * @return the number of elements
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if an element is contained in the list.
     *
     * @param element the element
     * @return true if the element is contained in the list, false otherwise
     */
    public boolean contains(final E element) {
        return indexOf(element) != -1;
    }

    /**
     * Removes an element from the list.
     *
     * @param element the element
     * @return true if the element was removed, false otherwise
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
     * Removes the element at the specified position.
     *
     * @param index the position
     * @return the removed element
     */
    public Object remove(final int index) {
        final Entry e = getEntry(index);
        e.remove();
        return e.element;
    }

    /**
     * Empties the list.
     */
    public void clear() {
        header.next = header;
        header.previous = header;
        size = 0;
    }

    /**
     * Returns the entry at the specified position.
     *
     * @param index the index
     * @return the entry
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
     * Returns the element at the specified position.
     *
     * @param index the index
     * @return the element
     */
    public E get(final int index) {
        return getEntry(index).element;
    }

    /**
     * Sets the element at the specified position.
     *
     * @param index the index
     * @param element the element
     * @return the original element
     */
    public E set(final int index, final E element) {
        final Entry entry = getEntry(index);
        final E oldValue = entry.element;
        entry.element = element;
        return oldValue;
    }

    /**
     * Returns the position of the element.
     *
     * @param element the element
     * @return the position
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
     * Converts the list to an array.
     *
     * @return the array
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
     * Converts the list to an array.
     *
     * @param array the array to store the elements. A new array of the same runtime type is allocated if the array is not large enough.
     * @return the array
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
     * An entry that stores an element.
     */
    public class Entry {

        /** The element */
        protected E element;

        /** The next entry */
        protected Entry next;

        /** The previous entry */
        protected Entry previous;

        Entry(final E element, final Entry next, final Entry previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Returns the element.
         *
         * @return the element
         */
        public E getElement() {
            return element;
        }

        /**
         * Returns the next entry.
         *
         * @return the next entry
         */
        public Entry getNext() {
            if (next != SLinkedList.this.header) {
                return next;
            }
            return null;
        }

        /**
         * Returns the previous entry.
         *
         * @return the previous entry
         */
        public Entry getPrevious() {
            if (previous != SLinkedList.this.header) {
                return previous;
            }
            return null;
        }

        /**
         * Removes the element.
         */
        public void remove() {
            previous.next = next;
            next.previous = previous;
            --size;
        }

        /**
         * Adds an element before this entry.
         *
         * @param o the element
         * @return the added entry
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
