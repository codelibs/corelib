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

import java.util.ArrayList;
import java.util.List;

import org.codelibs.core.collection.SLinkedList;
import org.codelibs.core.log.Logger;

/**
 * Timerを扱うクラスです。
 *
 * @author higa
 *
 */
public class TimeoutManager implements Runnable {

    private static final Logger logger = Logger.getLogger(TimeoutManager.class);

    /**
     * シングルトンのためのインスタンスです。
     */
    protected static final TimeoutManager instance = new TimeoutManager();

    /**
     * Timerのための{@link Thread}です。
     */
    protected Thread thread;

    /**
     * {@link TimeoutTask}管理用のリストです。
     */
    protected final SLinkedList<TimeoutTask> timeoutTaskList = new SLinkedList<>();

    private TimeoutManager() {
    }

    /**
     * シングルトン用のインスタンスを返します。
     *
     * @return シングルトン用のインスタンス
     */
    public static TimeoutManager getInstance() {
        return instance;
    }

    /**
     * 処理を開始します。
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
     * 処理を停止します。
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
     * スレッドに割り込みを行い、終了するまで待機します。
     *
     * @param timeoutMillis
     *            待機する時間(ミリ秒単位)
     * @return スレッドが終了した場合は<code>true</code>
     * @throws InterruptedException
     *             待機中に割り込まれた場合
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
     * 管理している {@link TimeoutTask}をクリアします。
     */
    public synchronized void clear() {
        timeoutTaskList.clear();
    }

    /**
     * {@link TimeoutTarget}を追加します。
     *
     * @param timeoutTarget target
     * @param timeout timeout
     * @param permanent permanent
     * @return {@link TimeoutTask}
     */
    public synchronized TimeoutTask addTimeoutTarget(
            final TimeoutTarget timeoutTarget, final int timeout,
            final boolean permanent) {
        final TimeoutTask task = new TimeoutTask(timeoutTarget, timeout,
                permanent);
        timeoutTaskList.addLast(task);
        start();
        return task;
    }

    /**
     * 管理している {@link TimeoutTask}の数を返します。
     *
     * @return 管理している {@link TimeoutTask}の数
     */
    public synchronized int getTimeoutTaskCount() {
        return timeoutTaskList.size();
    }

    @Override
    public void run() {
        boolean interrupted = false;
        try {
            for (;;) {
                final List<TimeoutTask> expiredTask = getExpiredTask();
                for (final TimeoutTask timeoutTask : expiredTask) {
                    final TimeoutTask task = timeoutTask;
                    try {
                        task.expired();
                    } catch (Exception e) {
                        if (e instanceof InterruptedException) {
                            interrupted = true;
                        } else {
                            logger.warn("Failed to process a task.", e);
                        }
                    }
                    if (task.isPermanent()) {
                        task.restart();
                    }
                }
                if (interrupted || isInterrupted() || stopIfLeisure()) {
                    return;
                }
                try {
                    Thread.sleep(1000L);
                } catch (final InterruptedException e) {
                    interrupted = true;
                }
            }
        } finally {
            if (logger.isDebugEnabled()) {
                logger.debug("TimeoutManagerThread stopped.");
            }
            thread = null;
        }
    }

    private synchronized boolean isInterrupted() {
        if (thread != null) {
            return thread.isInterrupted();
        }
        return true;
    }

    /**
     * 期限の切れた {@link TimeoutTask}のリストを返します。
     *
     * @return 期限の切れた {@link TimeoutTask}のリスト
     */
    protected synchronized List<TimeoutTask> getExpiredTask() {
        final List<TimeoutTask> expiredTask = new ArrayList<>();
        if (timeoutTaskList.isEmpty()) {
            return expiredTask;
        }
        for (SLinkedList<TimeoutTask>.Entry e = timeoutTaskList
                .getFirstEntry(); e != null; e = e.getNext()) {
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
     * 管理しているタスクが無いなら処理を停止します。
     *
     * @return 停止したかどうか
     */
    protected synchronized boolean stopIfLeisure() {
        if (timeoutTaskList.isEmpty()) {
            thread = null;
            return true;
        }
        return false;
    }

}
