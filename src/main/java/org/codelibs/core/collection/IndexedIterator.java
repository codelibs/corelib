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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Iterator;

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * インデックス付きの{@link Iterator}です。インデックスは{@literal 0}から始まります。
 *
 * <p>
 * 次のように使います．
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.IndexedIterator.*;
 *
 * List&lt;String&gt; list = ...;
 * for (Indexed&lt;String%gt; indexed : indexed(list)) {
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
 * for (Indexed&lt;String%gt; indexed : indexed(iterable(reader))) {
 *     System.out.println(indexed.getIndex());
 *     System.out.println(indexed.getElement());
 * }
 * </pre>
 *
 * @author wyukawa
 * @param <T>
 *            イテレートする要素の型
 * @see Indexed
 */
public class IndexedIterator<T> implements Iterator<Indexed<T>> {

    /** {@link Iterator} */
    protected final Iterator<T> iterator;

    /** イテレータのインデックス */
    protected int index;

    /**
     * for each構文で使用するために{@link IndexedIterator}をラップした{@link Iterable}を返します。
     *
     * @param <T>
     *            {@link Iterable}の要素型
     * @param iterable
     *            {@link Iterable}。{@literal null}であってはいけません
     * @return {@link IndexedIterator}をラップした{@link Iterable}
     */
    public static <T> Iterable<Indexed<T>> indexed(final Iterable<T> iterable) {
        assertArgumentNotNull("iterable", iterable);
        return indexed(iterable.iterator());
    }

    /**
     * for each構文で使用するために{@link IndexedIterator}をラップした{@link Iterable}を返します。
     *
     * @param <T>
     *            {@link Iterable}の要素型
     * @param iterator
     *            {@link Iterator}。{@literal null}であってはいけません
     * @return {@link IndexedIterator}をラップした{@link Iterable}
     */
    public static <T> Iterable<Indexed<T>> indexed(final Iterator<T> iterator) {
        assertArgumentNotNull("iterator", iterator);

        return () -> new IndexedIterator<>(iterator);
    }

    /**
     *
     * インスタンスを構築します。
     *
     * @param iterator
     *            イテレータ。{@literal null}であってはいけません
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
