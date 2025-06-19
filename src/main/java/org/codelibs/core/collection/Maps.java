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
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utility class for easily creating and setting values in {@link Map} instances.
 * <p>
 * By statically importing this class, you can easily initialize {@literal Map} instances as follows:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.Maps.*;
 *
 * Map&lt;String, Integer&gt; map = map("a", 1).$("b", 2).$("c", 3).$();
 * </pre>
 *
 * @author koichik
 * @param <K> the key type of the {@literal Map}
 * @param <V> the value type of the {@literal Map}
 */
public class Maps<K, V> {

    /** The target <code>Map</code> to be created. */
    protected Map<K, V> map;

    /**
     * Returns a {@literal Maps} for constructing a {@link Map} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link Map} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> map(final KEY key, final VALUE value) {
        return linkedHashMap(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link ConcurrentHashMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link ConcurrentHashMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> concurrentHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new ConcurrentHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link HashMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link HashMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> hashMap(final KEY key, final VALUE value) {
        return new Maps<>(new HashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link Hashtable} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link Hashtable} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> hashtable(final KEY key, final VALUE value) {
        return new Maps<>(new Hashtable<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link IdentityHashMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link IdentityHashMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> identityHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new IdentityHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link LinkedHashMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link LinkedHashMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> linkedHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new LinkedHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link TreeMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link TreeMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> treeMap(final KEY key, final VALUE value) {
        return new Maps<>(new TreeMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Returns a {@literal Maps} for constructing a {@link WeakHashMap} with the specified key and value.
     *
     * @param <KEY> the key type of the <code>Map</code>
     * @param <VALUE> the value type of the <code>Map</code>
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return a {@literal Maps} for constructing a {@link WeakHashMap} with the specified key and value
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> weakHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new WeakHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * Constructs an instance.
     *
     * @param map the <code>Map</code> to which keys and values are added
     */
    protected Maps(final Map<K, V> map) {
        this.map = map;
    }

    /**
     * Adds a key and value to the {@link Map}.
     *
     * @param key the key to be added to the <code>Map</code>
     * @param value the value to be added to the <code>Map</code>
     * @return this instance
     */
    public Maps<K, V> $(final K key, final V value) {
        map.put(key, value);
        return this;
    }

    /**
     * Returns the <code>Map</code>.
     *
     * @return the <code>Map</code> with the added keys and values
     */
    public Map<K, V> $() {
        return map;
    }

}
