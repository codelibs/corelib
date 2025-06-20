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

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * {@link Set} that is case-insensitive.
 *
 * @author higa
 */
public class CaseInsensitiveSet extends AbstractSet<String> implements Set<String>, Serializable {

    static final long serialVersionUID = 0L;

    private transient Map<String, Object> map = new CaseInsensitiveMap<>();

    private static final Object PRESENT = new Object();

    /**
     * Creates a {@link CaseInsensitiveSet}.
     */
    public CaseInsensitiveSet() {
        map = new CaseInsensitiveMap<>();
    }

    /**
     * Creates a {@link CaseInsensitiveSet}.
     *
     * @param c the collection to copy from
     */
    public CaseInsensitiveSet(final Collection<String> c) {
        map = new CaseInsensitiveMap<>(Math.max((int) (c.size() / .75f) + 1, 16));
        addAll(c);
    }

    /**
     * Creates a {@link CaseInsensitiveSet}.
     *
     * @param initialCapacity the initial capacity
     */
    public CaseInsensitiveSet(final int initialCapacity) {
        map = new CaseInsensitiveMap<>(initialCapacity);
    }

    @Override
    public Iterator<String> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(final String o) {
        return map.put(o, PRESENT) == null;
    }

    @Override
    public boolean remove(final Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }

}
