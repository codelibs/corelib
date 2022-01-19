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

import java.util.Iterator;

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * 空の {@link Iterator}です。
 *
 * @author higa
 * @param <T>
 *            反復する要素の型
 */
public class EmptyIterator<T> implements Iterator<T> {

    /**
     * {@link EmptyIterator}を作成します。
     */
    public EmptyIterator() {
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new ClUnsupportedOperationException("next");
    }

}
