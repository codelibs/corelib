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
package org.codelibs.core.nio;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.codelibs.core.exception.IORuntimeException;

/**
 * Utility for {@link Channel}.
 *
 * Author: koichik
 */
public abstract class ChannelUtil {

    /**
     * Do not instantiate.
     */
    protected ChannelUtil() {
    }

    /**
     * Returns a {@link ByteBuffer} that maps the file channel to memory.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @param mode
     *            The mode. Must not be {@literal null}.
     * @return A {@link ByteBuffer} that maps the file channel to memory.
     */
    public static ByteBuffer map(final FileChannel channel, final MapMode mode) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("mode", mode);

        try {
            return channel.map(mode, 0, channel.size());
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Returns the size of the file.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @return The size of the file.
     */
    public static long size(final FileChannel channel) {
        assertArgumentNotNull("channel", channel);

        try {
            return channel.size();
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Reads the contents of the file channel into a byte buffer.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @param buffer
     *            The byte buffer. Must not be {@literal null}.
     * @return The number of bytes read.
     */
    public static int read(final FileChannel channel, final ByteBuffer buffer) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.read(buffer);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Reads the contents of the file channel into a byte buffer.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @param buffer
     *            The byte buffer. Must not be {@literal null}.
     * @param position
     *            The position to start reading from.
     * @return The number of bytes read.
     */
    public static int read(final FileChannel channel, final ByteBuffer buffer, final long position) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.read(buffer, position);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Writes the contents of the byte buffer to the file channel.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @param buffer
     *            The byte buffer. Must not be {@literal null}.
     * @return The number of bytes written.
     */
    public static int write(final FileChannel channel, final ByteBuffer buffer) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.write(buffer);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Writes the contents of the byte buffer to the file channel.
     *
     * @param channel
     *            The file channel. Must not be {@literal null}.
     * @param buffer
     *            The byte buffer. Must not be {@literal null}.
     * @param position
     *            The position to start writing from.
     * @return The number of bytes written.
     */
    public static int write(final FileChannel channel, final ByteBuffer buffer, final long position) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.write(buffer, position);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Transfers the contents of the file channel {@literal from} to {@literal to}.
     *
     * @param from
     *            The source file channel. Must not be {@literal null}.
     * @param to
     *            The destination file channel. Must not be {@literal null}.
     * @return The number of bytes transferred.
     */
    public static long transfer(final FileChannel from, final FileChannel to) {
        assertArgumentNotNull("from", from);
        assertArgumentNotNull("to", to);

        try {
            return from.transferTo(0, from.size(), to);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
