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

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Adapter to convert an {@link Iterator} to an {@link Enumeration}.
 *
 * @author higa
 * @param <T> the element type
 */
public class IteratorEnumeration<T> implements Enumeration<T> {

    /** 反復子 */
    protected final Iterator<T> iterator;

    /**
     * Creates an {@link IteratorEnumeration}.
     *
     * @param iterator the iterator (must not be {@literal null})
     */
    public IteratorEnumeration(final Iterator<T> iterator) {
        assertArgumentNotNull("iterator", iterator);
        this.iterator = iterator;
    }

    /**
     * Creates an {@link IteratorEnumeration}.
     *
     * @param iterable the iterable object (must not be {@literal null})
     */
    public IteratorEnumeration(final Iterable<T> iterable) {
        assertArgumentNotNull("iterable", iterable);
        this.iterator = iterable.iterator();
    }

    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    @Override
    public T nextElement() {
        return iterator.next();
    }

}
