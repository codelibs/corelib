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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link HashMap} with an upper limit on the number of entries. When a new entry is added, the oldest entry is discarded using LRU if the limit is exceeded.
 *
 * @author koichik
 * @param <K> the key type
 * @param <V> the value type
 */
public class LruHashMap<K, V> extends LinkedHashMap<K, V> {

    private static final long serialVersionUID = 1L;

    /**
     * Default initial capacity.
     */
    protected static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * Default load factor.
     */
    protected static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Upper limit on the number of entries.
     */
    protected final int limitSize;

    /**
     * Creates an {@link LruHashMap}.
     *
     * @param limitSize the upper limit on the number of entries
     */
    public LruHashMap(final int limitSize) {
        this(limitSize, DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Creates an {@link LruHashMap}.
     *
     * @param limitSize the upper limit on the number of entries
     * @param initialCapacity the initial capacity
     * @param loadFactor the load factor
     */
    public LruHashMap(final int limitSize, final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor, true);
        this.limitSize = limitSize;
    }

    /**
     * Returns the upper limit on the number of entries.
     *
     * @return the upper limit on the number of entries
     */
    public int getLimitSize() {
        return limitSize;
    }

    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> entry) {
        return size() > limitSize;
    }

}
