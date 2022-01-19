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

/**
 * {@link IndexedIterator}でイテレートする要素です。
 *
 * @author wyukawa
 * @param <T>
 *            要素の型
 * @see IndexedIterator
 */
public class Indexed<T> {

    /** 要素 */
    private final T element;

    /** 要素のインデックス */
    private final int index;

    /**
     * コンストラクタ
     *
     * @param element
     *            要素
     * @param index
     *            要素のインデックス
     */
    public Indexed(final T element, final int index) {
        this.element = element;
        this.index = index;
    }

    /**
     * 要素を返します。
     *
     * @return 要素
     */
    public T getElement() {
        return element;
    }

    /**
     * インデックスを返します。
     *
     * @return インデックス
     */
    public int getIndex() {
        return index;
    }

}
