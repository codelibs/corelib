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
package org.codelibs.core.misc;

import static org.codelibs.core.collection.CollectionsUtil.newLinkedList;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.beans.Introspector;
import java.util.Deque;

/**
 * アプリケーションの終了時にリソースを破棄するためのユーティリティクラスです。
 * <p>
 * アプリケーションの終了時に破棄しなければならないリソースがある場合は、 {@link Disposable}を実装したクラスを作成し、
 * このクラスに登録します。
 * </p>
 *
 * @author koichik
 */
public abstract class DisposableUtil {

    /** 登録済みの{@link Disposable} */
    protected static final Deque<Disposable> disposables = newLinkedList();

    /**
     * 破棄可能なリソースを登録します。
     *
     * @param disposable
     *            破棄可能なリソース。{@literal null}であってはいけません
     */
    public static synchronized void add(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.addLast(disposable);
    }

    /**
     * 破棄可能なリソースを先頭に登録します。
     * <p>
     * リソースは登録された逆順に破棄されるため、先頭に登録されたリソースは最後に破棄されることになります。
     * </p>
     *
     * @param disposable
     *            破棄可能なリソース。{@literal null}であってはいけません
     */
    public static synchronized void addFirst(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.addFirst(disposable);
    }

    /**
     * 破棄可能なリソースを登録解除します。
     *
     * @param disposable
     *            破棄可能なリソース。{@literal null}であってはいけません
     */
    public static synchronized void remove(final Disposable disposable) {
        assertArgumentNotNull("disposable", disposable);
        disposables.remove(disposable);
    }

    /**
     * 登録済みのリソースを全て破棄します。
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
