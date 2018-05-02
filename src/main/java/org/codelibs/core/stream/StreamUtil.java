/*
 * Copyright 2012-2016 CodeLibs Project and the Others.
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

public class StreamUtil {
    private StreamUtil() {
        // nothing
    }

    @SafeVarargs
    public static <T> StreamOf<T> stream(final T... values) {
        return new StreamOf<>(() -> values != null ? Arrays.stream(values) : Collections.<T> emptyList().stream());
    }

    public static StreamOf<String> split(final String value, final String regex) {
        return stream(value == null ? null : value.split(regex));
    }

    public static <K, V> StreamOf<Map.Entry<K, V>> stream(final Map<K, V> map) {
        return new StreamOf<>(() -> map != null ? map.entrySet().stream() : Collections.<K, V> emptyMap().entrySet().stream());
    }

    public static class StreamOf<T> {

        private final Supplier<Stream<T>> supplier;

        public StreamOf(final Supplier<Stream<T>> supplier) {
            this.supplier = supplier;
        }

        public void of(final Consumer<Stream<T>> stream) {
            try (Stream<T> s = supplier.get()) {
                stream.accept(s);
            }
        }

        public <R> R get(final Function<Stream<T>, R> stream) {
            try (Stream<T> s = supplier.get()) {
                return stream.apply(s);
            }
        }
    }
}
