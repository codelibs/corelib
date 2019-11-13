/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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
package org.codelibs.core.timer;

import org.codelibs.core.exception.ClIllegalStateException;

/**
 * タイムアウトを管理するクラスです。
 *
 * @author higa
 *
 */
public class TimeoutTask {

    private static final int ACTIVE = 0;

    private static final int STOPPED = 1;

    private static final int CANCELED = 2;

    private final TimeoutTarget timeoutTarget;

    private final long timeoutMillis;

    private final boolean permanent;

    private long startTime;

    private int status = ACTIVE;

    TimeoutTask(final TimeoutTarget timeoutTarget, final int timeout, final boolean permanent) {
        this.timeoutTarget = timeoutTarget;
        this.timeoutMillis = timeout * 1000L;
        this.permanent = permanent;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * 期限切れかどうかを返します。
     *
     * @return 期限切れかどうか
     */
    public boolean isExpired() {
        return System.currentTimeMillis() >= startTime + timeoutMillis;
    }

    /**
     * 永続的かどうかを返します。
     *
     * @return 永続的かどうか
     */
    public boolean isPermanent() {
        return permanent;
    }

    /**
     * キャンセルされているかどうかを返します。
     *
     * @return キャンセルされているか
     */
    public boolean isCanceled() {
        return status == CANCELED;
    }

    /**
     * キャンセルします。
     */
    public void cancel() {
        status = CANCELED;
    }

    /**
     * 止まっているかどうか返します。
     *
     * @return 止まっているかどうか
     */
    public boolean isStopped() {
        return status == STOPPED;
    }

    /**
     * タイマーをとめます。
     */
    public void stop() {
        if (status != ACTIVE) {
            throw new ClIllegalStateException(String.valueOf(status));
        }
        status = STOPPED;
    }

    /**
     * タイマーを再開始します。
     */
    public void restart() {
        status = ACTIVE;
        startTime = System.currentTimeMillis();
    }

    void expired() {
        timeoutTarget.expired();
    }
}
