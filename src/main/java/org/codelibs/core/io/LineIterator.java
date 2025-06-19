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
package org.codelibs.core.io;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClUnsupportedOperationException;

/**
 * An {@link Iterator} that iterates over lines read from a {@link BufferedReader}.
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.io.LineIterator.*;
 *
 * BufferedReader reader = ...;
 * for (String line : iterable(reader)) {
 *     ...
 * }
 * </pre>
 *
 * @author koichik
 */
public class LineIterator implements Iterator<String> {

    /** String object indicating that {@link #line} is empty */
    protected static final String EMPTY = new String();

    /** {@link BufferedReader} */
    protected final BufferedReader reader;

    /** The line that has been read */
    protected String line = EMPTY;

    /**
     * Returns an {@link Iterable} that wraps a {@link LineIterator} for use in enhanced for-loops.
     *
     * @param reader
     *            The {@link Reader} to read strings from. Must not be {@literal null}.
     * @return An {@link Iterable} that wraps a {@link LineIterator}.
     */
    public static Iterable<String> iterable(final Reader reader) {
        assertArgumentNotNull("reader", reader);
        return iterable(new BufferedReader(reader));
    }

    /**
     * Returns an {@link Iterable} that wraps a {@link LineIterator} for use in enhanced for-loops.
     *
     * @param reader
     *            The {@link BufferedReader} to read strings from. Must not be {@literal null}.
     * @return An {@link Iterable} that wraps a {@link LineIterator}.
     */
    public static Iterable<String> iterable(final BufferedReader reader) {
        assertArgumentNotNull("reader", reader);

        return () -> new LineIterator(reader);
    }

    /**
     * Constructs an instance.
     *
     * @param reader
     *            The {@link Reader} to read strings from. Must not be {@literal null}.
     */
    public LineIterator(final Reader reader) {
        assertArgumentNotNull("reader", reader);
        this.reader = new BufferedReader(reader);
    }

    /**
     * Constructs an instance.
     *
     * @param reader
     *            The {@link BufferedReader} to read strings from. Must not be {@literal null}.
     */
    public LineIterator(final BufferedReader reader) {
        assertArgumentNotNull("reader", reader);
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        if (line == EMPTY) {
            line = ReaderUtil.readLine(reader);
        }
        return line != null;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        final String result = line;
        line = EMPTY;
        return result;
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
