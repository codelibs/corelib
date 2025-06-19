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

import static org.codelibs.core.misc.AssertionUtil.assertArgument;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.codelibs.core.exception.ClUnsupportedOperationException;
import org.codelibs.core.message.MessageFormatter;

/**
 * An {@link Iterator} that iterates through the inheritance hierarchy of a class towards its superclasses.
 * <p>
 * Usage example:
 * </p>
 *
 * <pre>
 * import static org.codelibs.core.lang.ClassIterator.*;
 *
 * Class&lt;?&gt; someClass = ...;
 * for (Class&lt;?&gt; clazz : iterable(someClass)) {
 *     ...
 * }
 * </pre>
 * <p>
 * By default, the {@link Object} class is also included in the iteration. If you do not want to include {@link Object},
 * specify {@literal false} for the second argument of {@link #iterable(Class, boolean)} or {@link #ClassIterator(Class, boolean)}.
 * </p>
 *
 * @author koichik
 */
public class ClassIterator implements Iterator<Class<?>> {

    /** The class */
    protected Class<?> clazz;

    /** If {@link Object} class should also be iterated, set to {@literal true} */
    protected final boolean includeObject;

    /**
     * Returns an {@link Iterable} that wraps a {@link ClassIterator} for use in a for-each statement.
     *
     * @param clazz the class (must not be {@literal null})
     * @return an {@link Iterable} wrapping a {@link ClassIterator}
     */
    public static Iterable<Class<?>> iterable(final Class<?> clazz) {
        return iterable(clazz, true);
    }

    /**
     * Returns an {@link Iterable} that wraps a {@link ClassIterator} for use in a for-each statement.
     *
     * @param clazz the class (must not be {@literal null})
     * @param includeObject if {@literal true}, includes the {@link Object} class in the iteration
     * @return an {@link Iterable} wrapping a {@link ClassIterator}
     */
    public static Iterable<Class<?>> iterable(final Class<?> clazz, final boolean includeObject) {
        return () -> new ClassIterator(clazz, includeObject);
    }

    /**
     * Constructs an instance.
     *
     * @param clazz
     *            the class (must not be {@literal null})
     */
    public ClassIterator(final Class<?> clazz) {
        this(clazz, true);
    }

    /**
     * Constructs an instance.
     *
     * @param clazz the class (must not be {@literal null})
     * @param includeObject if {@literal true}, includes the {@link Object} class in the iteration
     */
    public ClassIterator(final Class<?> clazz, final boolean includeObject) {
        assertArgumentNotNull("clazz", clazz);
        assertArgument("clazz", !clazz.isInterface(), MessageFormatter.getSimpleMessage("ECL0103", clazz));

        this.clazz = clazz;
        this.includeObject = includeObject;
    }

    @Override
    public boolean hasNext() {
        if (!includeObject && clazz == Object.class) {
            return false;
        }
        return clazz != null;
    }

    @Override
    public Class<?> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        final Class<?> result = clazz;
        clazz = clazz.getSuperclass();
        return result;
    }

    @Override
    public void remove() {
        throw new ClUnsupportedOperationException("remove");
    }

}
