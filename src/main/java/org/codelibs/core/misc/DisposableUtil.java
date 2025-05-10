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
package org.codelibs.core.misc;

import static org.codelibs.core.collection.CollectionsUtil.newLinkedList;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.beans.Introspector;
import java.util.Deque;

/**
 * Utility class for disposing of resources at the end of an application.
 * <p>
 * If there are resources that must be disposed of at the end of the application,
 * create a class that implements {@link Disposable} and register it with this class.
 * </p>
 *
 * @author koichik
 */
public abstract class DisposableUtil {

    /** Registered {@link Disposable} */
    protected static final Deque<Disposable> disposables = newLinkedList();

    /**
     * Registers a disposable resource.
     *
     * @param disposable
     *            A disposable resource. Must not be {@literal null}.
     */
    public static synchronized void add(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.addLast(disposable);
    }

    /**
     * Registers a disposable resource at the beginning.
     * <p>
     * Resources are disposed of in the reverse order of their registration, so resources registered at the beginning will be disposed of last.
     * </p>
     *
     * @param disposable
     *            A disposable resource. Must not be {@literal null}.
     */
    public static synchronized void addFirst(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.addFirst(disposable);
    }

    /**
     * Unregisters a disposable resource.
     *
     * @param disposable
     *            A disposable resource. Must not be {@literal null}.
     */
    public static synchronized void remove(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.remove(disposable);
    }

    /**
     * Disposes of all registered resources.
     */
    public static synchronized void dispose() {
        while (!disposables.isEmpty()) {
            final Disposable disposable = disposables.removeLast();
            try {
                disposable.dispose();
            } catch (final Throwable t) {
                t.printStackTrace(); // must not use Logger.
            }
        }
        disposables.clear();
        Introspector.flushCaches();
    }

}
