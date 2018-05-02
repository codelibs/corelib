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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 簡潔な記述で{@link Map}のインスタンスを生成して値を設定するためのユーティリティクラスです。
 * <p>
 * 本クラスをstatic importすることにより、次のように{@literal Map}のインスタンスを簡潔に初期化することができます。
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.collection.Maps.*;
 *
 * Map&lt;String, Integer&gt; map = map("a", 1).$("b", 2).$("c", 3).$();
 * </pre>
 *
 * @author koichik
 * @param <K>
 *            {@literal Map}のキーの型
 * @param <V>
 *            {@literal Map}の値の型
 */
public class Maps<K, V> {

    /** 作成対象の<code>Map</code> */
    protected Map<K, V> map;

    /**
     * 指定されたキーと値を持つ{@link Map}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link Map}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> map(final KEY key, final VALUE value) {
        return linkedHashMap(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link ConcurrentHashMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link ConcurrentHashMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> concurrentHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new ConcurrentHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link HashMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link HashMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> hashMap(final KEY key, final VALUE value) {
        return new Maps<>(new HashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link Hashtable}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link Hashtable}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> hashtable(final KEY key, final VALUE value) {
        return new Maps<>(new Hashtable<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link IdentityHashMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link IdentityHashMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> identityHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new IdentityHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link LinkedHashMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link LinkedHashMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> linkedHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new LinkedHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link TreeMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link TreeMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> treeMap(final KEY key, final VALUE value) {
        return new Maps<>(new TreeMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * 指定されたキーと値を持つ{@link WeakHashMap}を構築するための{@literal Maps}を返します。
     *
     * @param <KEY>
     *            <code>Map</code>のキーの型
     * @param <VALUE>
     *            <code>Map</code>の値ーの型
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return 指定されたキーと値を持つ{@link WeakHashMap}を構築するための{@literal Maps}
     */
    public static <KEY, VALUE> Maps<KEY, VALUE> weakHashMap(final KEY key, final VALUE value) {
        return new Maps<>(new WeakHashMap<KEY, VALUE>()).$(key, value);
    }

    /**
     * インスタンスを構築します。
     *
     * @param map
     *            キーと値を追加する対象の<code>Map</code>
     */
    protected Maps(final Map<K, V> map) {
        this.map = map;
    }

    /**
     * {@link Map}にキーと値を追加します。
     *
     * @param key
     *            <code>Map</code>に追加されるキー
     * @param value
     *            <code>Map</code>に追加される値
     * @return このインスタンス自身
     */
    public Maps<K, V> $(final K key, final V value) {
        map.put(key, value);
        return this;
    }

    /**
     * <code>Map</code>を返します。
     *
     * @return キーと値が追加された<code>Map</code>
     */
    public Map<K, V> $() {
        return map;
    }

}
