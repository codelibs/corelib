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
package org.codelibs.core.nio;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.codelibs.core.exception.IORuntimeException;

/**
 * {@link Channel}用のユーティリティです。
 *
 * @author koichik
 */
public abstract class ChannelUtil {

    /**
     * ファイルチャネルをメモリにマップした{@link ByteBuffer}を返します。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @param mode
     *            モード。{@literal null}であってはいけません
     * @return ファイルチャネルをメモリにマップした{@link ByteBuffer}
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
     * ファイルのサイズを返します。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @return ファイルのサイズ
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
     * ファイルチャネルの内容をバイトバッファに読み込みます。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @param buffer
     *            バイトバッファ。{@literal null}であってはいけません
     * @return 読み込んだバイト数
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
     * ファイルチャネルの内容をバイトバッファに読み込みます。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @param buffer
     *            バイトバッファ。{@literal null}であってはいけません
     * @param position
     *            読み込みを開始する位置
     * @return 読み込んだバイト数
     */
    public static int read(final FileChannel channel, final ByteBuffer buffer,
            final long position) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.read(buffer, position);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * バイトバッファの内容をファイルチャネルに書き込みます。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @param buffer
     *            バイトバッファ。{@literal null}であってはいけません
     * @return 書き込んだバイト数
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
     * バイトバッファの内容をファイルチャネルに書き込みます。
     *
     * @param channel
     *            ファイルチャネル。{@literal null}であってはいけません
     * @param buffer
     *            バイトバッファ。{@literal null}であってはいけません
     * @param position
     *            読み込みを開始する位置
     * @return 書き込んだバイト数
     */
    public static int write(final FileChannel channel, final ByteBuffer buffer,
            final long position) {
        assertArgumentNotNull("channel", channel);
        assertArgumentNotNull("buffer", buffer);

        try {
            return channel.write(buffer, position);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * ファイルチャネル{@literal from}を{@literal to}へ転送します。
     *
     * @param from
     *            転送元のファイルチャネル。{@literal null}であってはいけません
     * @param to
     *            転送先のファイルチャネル。{@literal null}であってはいけません
     * @return 転送されたバイト数
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
