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
package org.codelibs.core.lang;

import org.codelibs.core.exception.InterruptedRuntimeException;
import org.codelibs.core.log.Logger;

/**
 * Utility class for Thread class
 *
 * @author shinsuke
 *
 */
public abstract class ThreadUtil {

    private static final Logger logger = Logger.getLogger(ThreadUtil.class);

    /**
     * Do not instantiate.
     */
    protected ThreadUtil() {
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
     *
     * @param millis
     *            the length of time to sleep in milliseconds
     * @throws InterruptedRuntimeException
     *             if any thread has interrupted the current thread.
     */
    public static void sleep(final long millis) {
        if (millis < 1L) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            throw new InterruptedRuntimeException(e);
        }
    }

    /**
     * Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds.
     * Any {@link InterruptedException} is caught and logged at debug level.
     *
     * @param millis
     *            the length of time to sleep in milliseconds
     */
    public static void sleepQuietly(final long millis) {
        if (millis < 1L) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (final InterruptedException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(Thread.currentThread().getName() + " is interrupted.", e);
            }
        }
    }
}
