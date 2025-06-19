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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * Adaptor that makes an array into an {@link Iterator}.
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.ArrayIterator.*;
 *
 * String[] array = ...;
 * for (String element : iterable(array)) {
 *     ...
 * }
 * </pre>
 *
 * @author shot
 * @param <T> the type of array elements
 */
public class ArrayIterator<T> implements Iterator<T> {

    /** The array of elements to iterate over */
    protected final T[] items;

    /** The index of the currently referenced element */
    protected int index = 0;

    /**
     * Returns an {@link Iterable} that wraps the array for use in a for-each statement.
     *
     * @param <T> the type of elements
     * @param items the array of elements to iterate (must not be {@literal null})
     * @return an {@link Iterable} wrapping the array
     */
    public static <T> Iterable<T> iterable(final T... items) {
        assertArgumentNotNull("items", items);

        return () -> new ArrayIterator<>(items);
    }

    /**
     * Creates an {@link ArrayIterator}.
     *
     * @param items the array of elements to iterate (must not be {@literal null})
     */
    public ArrayIterator(final T... items) {
        this.items = items;
    }

    @Override
    public boolean hasNext() {
        return index < items.length;
    }

    @Override
    public T next() {
        try {
            final T o = items[index];
            index++;
            return o;
        } catch (final IndexOutOfBoundsException e) {
            throw new NoSuchElementException("index=" + index);
        }
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
