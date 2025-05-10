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
 * 配列を{@link Iterator}にするAdaptorです。
 * <p>
 * 次のように使います．
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
 * @param <T>
 *            配列の要素の型
 */
public class ArrayIterator<T> implements Iterator<T> {

    /** イテレートする要素の配列 */
    protected final T[] items;

    /** 現在参照している要素のインデックス */
    protected int index = 0;

    /**
     * for each構文で使用するために配列をラップした{@link Iterable}を返します。
     *
     * @param <T>
     *            列挙する要素の型
     * @param items
     *            イテレートする要素の並び。{@literal null}であってはいけません
     * @return 配列をラップした{@link Iterable}
     */
    public static <T> Iterable<T> iterable(final T... items) {
        assertArgumentNotNull("items", items);

        return () -> new ArrayIterator<>(items);
    }

    /**
     * {@link ArrayIterator}を作成します。
     *
     * @param items
     *            イテレートする要素の並び。{@literal null}であってはいけません
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
