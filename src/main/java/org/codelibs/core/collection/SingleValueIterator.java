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

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * An {@link Iterator} that returns a single value.
 *
 * @author koichik
 * @param <E> the element type
 */
public class SingleValueIterator<E> implements Iterator<E> {

    /** The only value returned by the iterator. */
    protected final E value;

    /** {@literal true} if the iterator has more elements. */
    protected boolean hasNext = true;

    /**
     * Returns an {@link Iterable} that wraps a {@link SingleValueIterator} for use in a for-each statement.
     *
     * @param <E> the element type
     * @param value the single value returned by the iterator
     * @return an {@link Iterable} wrapping a {@link SingleValueIterator}
     */
    public static <E> Iterable<E> iterable(final E value) {
        return () -> new SingleValueIterator<>(value);
    }

    /**
     * Constructs an instance.
     *
     * @param value the single value returned by the iterator
     */
    public SingleValueIterator(final E value) {
        this.value = value;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public E next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        hasNext = false;
        return value;
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
