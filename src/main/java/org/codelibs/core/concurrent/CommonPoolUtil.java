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
package org.codelibs.core.concurrent;

import java.util.concurrent.ForkJoinPool;

import org.codelibs.core.log.Logger;

/**
 * Utility for common pool operations.
 */
public abstract class CommonPoolUtil {
    private static final Logger logger = Logger.getLogger(CommonPoolUtil.class);

    protected CommonPoolUtil() {
        // nothing
    }

    /**
     * Executes the given task in the common ForkJoinPool.
     *
     * @param task
     *            the task to execute
     */
    public static void execute(final Runnable task) {
        ForkJoinPool.commonPool().execute(() -> {
            final Thread currentThread = Thread.currentThread();
            final ClassLoader orignal = currentThread.getContextClassLoader();
            currentThread.setContextClassLoader(CommonPoolUtil.class.getClassLoader());
            try {
                task.run();
            } catch (final Exception e) {
                logger.error("Uncaught exception from " + task, e);
            } finally {
                currentThread.setContextClassLoader(orignal);
            }
        });
    }
}
