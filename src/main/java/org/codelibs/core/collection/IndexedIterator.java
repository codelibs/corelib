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

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * {@link Iterator} with index. The index starts from {@literal 0}.
 *
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.IndexedIterator.*;
 *
 * List&lt;String&gt; list = ...;
 * for (Indexed&lt;String&gt; indexed : indexed(list)) {
 *     System.out.println(indexed.getIndex());
 *     System.out.println(indexed.getElement());
 * }
 * </pre>
 *
 * <pre>
 * import static org.codelibs.core.collection.IndexedIterator.*;
 * import static org.codelibs.core.io.LineIterator.*;
 *
 * BufferedReader reader = ...;
 * for (Indexed&lt;String&gt; indexed : indexed(iterable(reader))) {
 *     System.out.println(indexed.getIndex());
 *     System.out.println(indexed.getElement());
 * }
 * </pre>
 *
 * @author wyukawa
 * @param <T> the element type
 * @see Indexed
 */
public class IndexedIterator<T> implements Iterator<Indexed<T>> {

    /** {@link Iterator} */
    protected final Iterator<T> iterator;

    /** The index of the iterator. */
    protected int index;

    /**
     * Returns an {@link Iterable} that wraps an {@link IndexedIterator} for use in a for-each statement.
     *
     * @param <T> the element type
     * @param iterable the iterable (must not be {@literal null})
     * @return an {@link Iterable} wrapping an {@link IndexedIterator}
     */
    public static <T> Iterable<Indexed<T>> indexed(final Iterable<T> iterable) {
        assertArgumentNotNull("iterable", iterable);
        return indexed(iterable.iterator());
    }

    /**
     * Returns an {@link Iterable} that wraps an {@link IndexedIterator} for use in a for-each statement.
     *
     * @param <T> the element type
     * @param iterator the iterator (must not be {@literal null})
     * @return an {@link Iterable} wrapping an {@link IndexedIterator}
     */
    public static <T> Iterable<Indexed<T>> indexed(final Iterator<T> iterator) {
        assertArgumentNotNull("iterator", iterator);

        return () -> new IndexedIterator<>(iterator);
    }

    /**
     * Constructs an instance.
     *
     * @param iterator the iterator (must not be {@literal null})
     */
    public IndexedIterator(final Iterator<T> iterator) {
        assertArgumentNotNull("iterator", iterator);

        this.iterator = iterator;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Indexed<T> next() {
        return new Indexed<>(iterator.next(), index++);
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
