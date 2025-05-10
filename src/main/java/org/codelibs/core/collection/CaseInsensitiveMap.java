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

import java.util.Map;

/**
 * キーで大文字小文字を気にしない {@link ArrayMap}です。
 *
 * @author higa 値の型
 * @param <V>
 *            値の型
 */
public class CaseInsensitiveMap<V> extends ArrayMap<String, V> {

    private static final long serialVersionUID = 1L;

    /**
     * {@link CaseInsensitiveMap}を作成します。
     */
    public CaseInsensitiveMap() {
    }

    /**
     * {@link CaseInsensitiveMap}を作成します。
     *
     * @param capacity
     *            初期容量
     */
    public CaseInsensitiveMap(final int capacity) {
        super(capacity);
    }

    /**
     * キーが含まれているかどうかを返します。
     *
     * @param key
     *            キー
     * @return キーが含まれているかどうか
     */
    public boolean containsKey(final String key) {
        return super.containsKey(convertKey(key));
    }

    @Override
    public V get(final Object key) {
        return super.get(convertKey(key));
    }

    @Override
    public final V put(final String key, final V value) {
        return super.put(convertKey(key), value);
    }

    @Override
    public void putAll(final Map<? extends String, ? extends V> map) {
        for (final Map.Entry<? extends String, ? extends V> entry : map.entrySet()) {
            put(convertKey(entry.getKey()), entry.getValue());
        }
    }

    @Override
    public final V remove(final Object key) {
        return super.remove(convertKey(key));
    }

    @Override
    public boolean containsKey(final Object key) {
        return super.containsKey(convertKey(key));
    }

    private static String convertKey(final Object key) {
        return key.toString().toLowerCase();
    }

}
