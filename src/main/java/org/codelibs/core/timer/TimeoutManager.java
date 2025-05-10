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
package org.codelibs.core.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.codelibs.core.collection.SLinkedList;
import org.codelibs.core.lang.StringUtil;
import org.codelibs.core.log.Logger;

/**
 * A class that handles timers.
 *
 * @author higa
 *
 */
public class TimeoutManager implements Runnable {

    private static final Logger logger = Logger.getLogger(TimeoutManager.class);

    /**
     * Instance for the singleton.
     */
    protected static final TimeoutManager instance = new TimeoutManager();

    /**
     * {@link Thread} for the timer.
     */
    protected Thread thread;

    /**
     * A list for managing {@link TimeoutTask}.
     */
    protected final SLinkedList<TimeoutTask> timeoutTaskList = new SLinkedList<>();

    private TimeoutManager() {
    }

    /**
     * Returns the instance for the singleton.
     *
     * @return the instance for the singleton
     */
    public static TimeoutManager getInstance() {
        return instance;
    }

    /**
     * Starts the process.
     */
    public synchronized void start() {
        if (thread == null) {
            thread = new Thread(this, "CoreLib-TimeoutManager");
            thread.setDaemon(true);
            thread.start();
            if (logger.isDebugEnabled()) {
                logger.debug("TimeoutManager started.");
            }
        }
    }

    /**
     * Stops the process.
     */
    public synchronized void stop() {
        if (thread != null) {
            thread.interrupt();
            thread = null;
            if (logger.isDebugEnabled()) {
                logger.debug("TimeoutManager stopped.");
            }
        }
    }

    /**
     * Interrupts the thread and waits for it to terminate.
     *
     * @param timeoutMillis
     *            The time to wait (in milliseconds)
     * @return <code>true</code> if the thread has terminated
     * @throws InterruptedException
     *             If interrupted while waiting
     */
    public boolean stop(final long timeoutMillis) throws InterruptedException {
        final Thread t = this.thread;
        synchronized (this) {
            if (t == null) {
                return true;
            }
            this.thread = null;
        }
        t.interrupt();
        if (logger.isDebugEnabled()) {
            logger.debug("TimeoutManager stopped.");
        }
        t.join(timeoutMillis);
        return !t.isAlive();
    }

    /**
     * Clears the managed {@link TimeoutTask}.
     */
    public synchronized void clear() {
        timeoutTaskList.clear();
    }

    /**
     * Adds a {@link TimeoutTarget}.
     *
     * @param timeoutTarget the target
     * @param timeout the timeout duration
     * @param permanent whether the task is permanent
     * @return the {@link TimeoutTask}
     */
    public synchronized TimeoutTask addTimeoutTarget(final TimeoutTarget timeoutTarget, final int timeout, final boolean permanent) {
        final TimeoutTask task = new TimeoutTask(timeoutTarget, timeout, permanent);
        timeoutTaskList.addLast(task);
        start();
        return task;
    }

    /**
     * Returns the number of managed {@link TimeoutTask}.
     *
     * @return the number of managed {@link TimeoutTask}
     */
    public synchronized int getTimeoutTaskCount() {
        return timeoutTaskList.size();
    }

    @Override
    public void run() {
        int nThreads = Runtime.getRuntime().availableProcessors() / 2;
        final String value = System.getProperty("corelib.timeout_task.num_of_threads");
        if (StringUtil.isNotBlank(value)) {
            try {
                nThreads = Integer.parseInt(value);
            } catch (final NumberFormatException e) {
                logger.warn("Failed to parse " + value, e);
            }
        }
        if (nThreads < 1) {
            nThreads = 1;
        }
        final ExecutorService executorService = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(nThreads), new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            while (!isInterrupted() && !stopIfLeisure()) {
                for (final TimeoutTask task : getExpiredTask()) {
                    processTask(executorService, task);
                }
                Thread.sleep(1000L);
            }
        } catch (final InterruptedException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Interrupted.", e);
            }
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("TimeoutManagerThread stopped.");
            }
            thread = null;
            try {
                executorService.shutdown();
                executorService.awaitTermination(60, TimeUnit.SECONDS);
            } catch (final InterruptedException e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Interrupted.", e);
                }
            } finally {
                executorService.shutdownNow();
            }
        }
    }

    private void processTask(final ExecutorService executorService, final TimeoutTask task) {
        try {
            executorService.execute(() -> {
                try {
                    task.expired();
                } catch (final Exception e) {
                    if (e instanceof InterruptedException) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Interrupted.", e);
                        }
                    } else {
                        logger.warn("Failed to process a task.", e);
                    }
                }
                if (task.isPermanent()) {
                    task.restart();
                }
            });
        } catch (final Exception e) {
            logger.warn("Failed to process a task.", e);
        }
    }

    private synchronized boolean isInterrupted() {
        if (thread != null) {
            return thread.isInterrupted();
        }
        return true;
    }

    /**
     * Returns the list of expired {@link TimeoutTask}.
     *
     * @return the list of expired {@link TimeoutTask}
     */
    protected synchronized List<TimeoutTask> getExpiredTask() {
        final List<TimeoutTask> expiredTask = new ArrayList<>();
        if (timeoutTaskList.isEmpty()) {
            return expiredTask;
        }
        for (SLinkedList<TimeoutTask>.Entry e = timeoutTaskList.getFirstEntry(); e != null; e = e.getNext()) {
            final TimeoutTask task = e.getElement();
            if (task.isCanceled()) {
                e.remove();
            } else if (!task.isStopped() && task.isExpired()) {
                expiredTask.add(task);
                if (!task.isPermanent()) {
                    e.remove();
                }
            }
        }
        return expiredTask;
    }

    /**
     * Stops the process if there are no managed tasks.
     *
     * @return whether the process was stopped
     */
    protected synchronized boolean stopIfLeisure() {
        if (timeoutTaskList.isEmpty()) {
            thread = null;
            return true;
        }
        return false;
    }

}
