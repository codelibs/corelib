/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
package org.codelibs.core.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Utility class for {@link Stream}.
 */
public class StreamUtil {
    private StreamUtil() {
        // nothing
    }

    /**
     * Creates a {@link StreamOf} instance from the provided values.
     * If the input array is {@code null}, an empty stream is returned.
     *
     * @param <T> The type of elements in the stream.
     * @param values The elements to be included in the stream. Can be {@code null}.
     * @return A {@link StreamOf} instance containing the provided values, or an empty stream if {@code values} is {@code null}.
     */
    @SafeVarargs
    public static <T> StreamOf<T> stream(final T... values) {
        return new StreamOf<>(() -> values != null ? Arrays.stream(values) : Collections.<T> emptyList().stream());
    }

    /**
     * Splits the given string into an array of substrings based on the specified regular expression
     * and returns a stream of those substrings.
     *
     * @param value the string to be split; if {@code null}, the method returns an empty stream
     * @param regex the regular expression to use for splitting the string
     * @return a stream of substrings resulting from splitting the input string, or an empty stream if the input is {@code null}
     */
    public static StreamOf<String> split(final String value, final String regex) {
        return stream(value == null ? null : value.split(regex));
    }

    /**
     * Creates a stream of map entries from the given map.
     *
     * @param <K> the type of keys in the map
     * @param <V> the type of values in the map
     * @param map the map to create a stream from; if {@code null}, an empty stream is returned
     * @return a {@link StreamOf} containing the entries of the map, or an empty stream if the map is {@code null}
     */
    public static <K, V> StreamOf<Map.Entry<K, V>> stream(final Map<K, V> map) {
        return new StreamOf<>(() -> map != null ? map.entrySet().stream() : Collections.<K, V> emptyMap().entrySet().stream());
    }

    public static class StreamOf<T> {

        private final Supplier<Stream<T>> supplier;

        public StreamOf(final Supplier<Stream<T>> supplier) {
            this.supplier = supplier;
        }

        /**
         * Executes the provided {@link Consumer} with a {@link Stream} obtained from the supplier.
         * The stream is automatically closed after the consumer is executed.
         *
         * @param stream the {@link Consumer} to process the {@link Stream}
         */
        public void of(final Consumer<Stream<T>> stream) {
            try (Stream<T> s = supplier.get()) {
                stream.accept(s);
            }
        }

        /**
         * Applies the given function to a stream created by the supplier and returns the result.
         * The stream is automatically closed after the function is applied.
         *
         * @param <R> The type of the result returned by the function.
         * @param stream A function that processes the stream and produces a result.
         * @return The result produced by applying the function to the stream.
         */
        public <R> R get(final Function<Stream<T>, R> stream) {
            try (Stream<T> s = supplier.get()) {
                return stream.apply(s);
            }
        }
    }
}
