/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * {@link Enumeration}を {@link Iterator}にするためのアダブタです。
 *
 * @author shot
 * @param <T>
 *            列挙する要素の型
 */
public class EnumerationIterator<T> implements Iterator<T> {

    private Enumeration<T> enumeration = null;

    /**
     * for each構文で使用するために{@link Enumeration}をラップした{@link Iterable}を返します。
     *
     * @param <T>
     *            列挙する要素の型
     * @param enumeration
     *            {@link Enumeration}。{@literal null}であってはいけません
     * @return {@link Enumeration}をラップした{@link Iterable}
     */
    public static <T> Iterable<T> iterable(final Enumeration<T> enumeration) {
        assertArgumentNotNull("enumeration", enumeration);

        return () -> new EnumerationIterator<>(enumeration);
    }

    /**
     * {@link Enumeration}をラップした{@link Iterator}のインスタンスを構築します。
     *
     * @param enumeration
     *            {@link Enumeration}。{@literal null}であってはいけません
     */
    public EnumerationIterator(final Enumeration<T> enumeration) {
        assertArgumentNotNull("enumeration", enumeration);
        this.enumeration = enumeration;
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

    @Override
    public boolean hasNext() {
        return enumeration.hasMoreElements();
    }

    @Override
    public T next() {
        return enumeration.nextElement();
    }

}
