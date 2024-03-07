/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
 * {@link Iterator}を {@link Enumeration}にするためのアダブタです。
 *
 * @author higa
 * @param <T>
 *            列挙する要素の型
 */
public class IteratorEnumeration<T> implements Enumeration<T> {

    /** 反復子 */
    protected final Iterator<T> iterator;

    /**
     * {@link IteratorEnumeration}を作成します。
     *
     * @param iterator
     *            反復子。{@literal null}であってはいけません
     */
    public IteratorEnumeration(final Iterator<T> iterator) {
        assertArgumentNotNull("iterator", iterator);
        this.iterator = iterator;
    }

    /**
     * {@link IteratorEnumeration}を作成します。
     *
     * @param iterable
     *            反復可能なオブジェクト。{@literal null}であってはいけません
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
