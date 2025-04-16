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
package org.codelibs.core.timer;

import org.codelibs.core.exception.ClIllegalStateException;

/**
 * This class manages timeouts.
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
     * Returns whether the task has expired.
     *
     * @return whether the task has expired
     */
    public boolean isExpired() {
        return System.currentTimeMillis() >= startTime + timeoutMillis;
    }

    /**
     * Returns whether the task is permanent.
     *
     * @return whether the task is permanent
     */
    public boolean isPermanent() {
        return permanent;
    }

    /**
     * Returns whether the task has been canceled.
     *
     * @return whether the task has been canceled
     */
    public boolean isCanceled() {
        return status == CANCELED;
    }

    /**
     * Cancels the task.
     */
    public void cancel() {
        status = CANCELED;
    }

    /**
     * Returns whether the task is stopped.
     *
     * @return whether the task is stopped
     */
    public boolean isStopped() {
        return status == STOPPED;
    }

    /**
     * Stops the timer.
     */
    public void stop() {
        if (status != ACTIVE) {
            throw new ClIllegalStateException(String.valueOf(status));
        }
        status = STOPPED;
    }

    /**
     * Restarts the timer.
     */
    public void restart() {
        status = ACTIVE;
        startTime = System.currentTimeMillis();
    }

    void expired() {
        timeoutTarget.expired();
    }
}
