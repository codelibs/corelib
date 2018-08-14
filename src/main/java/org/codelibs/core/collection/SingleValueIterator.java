/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
 * 一つの値を返す{@link Iterator}です。
 *
 * @author koichik
 * @param <E>
 *            要素の型
 */
public class SingleValueIterator<E> implements Iterator<E> {

    /** 反復子が返す唯一の値 */
    protected final E value;

    /** 反復子がさらに要素を持つ場合は{@literal true} */
    protected boolean hasNext = true;

    /**
     * for each構文で使用するために{@link SingleValueIterator}をラップした{@link Iterable}を返します。
     *
     * @param <E>
     *            要素の型
     * @param value
     *            反復子が返す唯一の値
     * @return {@link SingleValueIterator}をラップした{@link Iterable}
     */
    public static <E> Iterable<E> iterable(final E value) {
        return () -> new SingleValueIterator<>(value);
    }

    /**
     * インスタンスを構築します。
     *
     * @param value
     *            反復子が返す唯一の値
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
