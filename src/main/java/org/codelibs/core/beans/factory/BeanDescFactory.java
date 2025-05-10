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
package org.codelibs.core.beans.factory;

import static org.codelibs.core.collection.CollectionsUtil.newConcurrentHashMap;
import static org.codelibs.core.collection.CollectionsUtil.putIfAbsent;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.concurrent.ConcurrentMap;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.impl.BeanDescImpl;
import org.codelibs.core.misc.DisposableUtil;

/**
 * This class generates {@link BeanDesc}.
 * <p>
 * It returns a {@link BeanDesc} that handles the metadata of the specified JavaBeans.
 * </p>
 *
 * <pre>
 * BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Foo.class);
 * </pre>
 * <p>
 * {@link BeanDesc} is cached. To clear the cache, call {@link DisposableUtil#dispose()}.
 * </p>
 *
 * @author higa
 * @see BeanDesc
 * @see DisposableUtil
 */
public abstract class BeanDescFactory {

    /** True if initialized */
    private static volatile boolean initialized;

    /** Cache of {@link BeanDesc} */
    private static final ConcurrentMap<Class<?>, BeanDesc> beanDescCache = newConcurrentHashMap(1024);

    static {
        initialize();
    }

    /**
     * Returns a {@link BeanDesc}.
     *
     * @param clazz
     *            the Bean class. Must not be {@literal null}
     * @return {@link BeanDesc}
     */
    public static BeanDesc getBeanDesc(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        if (!initialized) {
            initialize();
        }
        BeanDesc beanDesc = beanDescCache.get(clazz);
        if (beanDesc == null) {
            beanDesc = putIfAbsent(beanDescCache, clazz, new BeanDescImpl(clazz));
        }
        return beanDesc;
    }

    /**
     * Performs initialization.
     */
    public static void initialize() {
        synchronized (BeanDescFactory.class) {
            if (!initialized) {
                DisposableUtil.add(BeanDescFactory::clear);
                initialized = true;
            }
        }
    }

    /**
     * Clears the cache.
     */
    public static void clear() {
        beanDescCache.clear();
        initialized = false;
    }

}
