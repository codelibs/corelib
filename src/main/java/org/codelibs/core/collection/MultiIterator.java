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
 * An {@link Iterator} that iterates over multiple {@link Iterator}s as if they were a single {@link Iterator}.
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.MultiIterator.*;
 *
 * List&lt;String&gt; list = ...;
 * Set&lt;String&gt; set = ...;
 * Map&lt;String, Object&gt; map = ...;
 * for (String element : iterable(list, set, map.keySet())) {
 *     ...
 * }
 * </pre>
 *
 * @author koichik
 * @param <E> the element type
 */
public class MultiIterator<E> implements Iterator<E> {

    /** Array of {@link Iterator}s. */
    protected final Iterator<E>[] iterators;

    /** Index of the currently iterated {@link Iterator}. */
    protected int index;

    /**
     * Returns an {@link Iterable} that wraps a {@link MultiIterator} for use in a for-each statement.
     *
     * @param <E> the element type
     * @param iterables the array of {@link Iterable} (must not be {@literal null})
     * @return an {@link Iterable} wrapping a {@link MultiIterator}
     */
    public static <E> Iterable<E> iterable(final Iterable<E>... iterables) {
        assertArgumentNotNull("iterables", iterables);

        @SuppressWarnings("unchecked")
        final Iterator<E>[] iterators = new Iterator[iterables.length];
        for (int i = 0; i < iterables.length; ++i) {
            iterators[i] = iterables[i].iterator();
        }
        return iterable(iterators);
    }

    /**
     * Returns an {@link Iterable} that wraps a {@link MultiIterator} for use in a for-each statement.
     *
     * @param <E> the element type
     * @param iterators the array of {@link Iterator} (must not be {@literal null})
     * @return an {@link Iterable} wrapping a {@link MultiIterator}
     */
    public static <E> Iterable<E> iterable(final Iterator<E>... iterators) {
        assertArgumentNotNull("iterators", iterators);

        return () -> new MultiIterator<>(iterators);
    }

    /**
     * Constructs an instance.
     *
     * @param iterators the array of {@link Iterator}s (must not be {@literal null})
     */
    public MultiIterator(final Iterator<E>... iterators) {
        assertArgumentNotNull("iterators", iterators);
        this.iterators = iterators;
    }

    @Override
    public boolean hasNext() {
        for (; index < iterators.length; ++index) {
            if (iterators[index].hasNext()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public E next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterators[index].next();
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
