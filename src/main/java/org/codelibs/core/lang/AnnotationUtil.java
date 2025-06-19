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

import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.MethodDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;

/**
 * Utility class for annotations.
 *
 * @author higa
 */
public abstract class AnnotationUtil {

    /**
     * Returns the elements of the annotation as a {@link Map} of names and values.
     *
     * @param annotation the annotation (must not be {@literal null})
     * @return a {@link Map} of annotation element names and values
     */
    public static Map<String, Object> getProperties(final Annotation annotation) {
        assertArgumentNotNull("annotation", annotation);

        final Map<String, Object> map = newHashMap();
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(annotation.annotationType());
        for (final String name : beanDesc.getMethodNames()) {
            final Object v = getProperty(beanDesc, annotation, name);
            if (v != null) {
                map.put(name, v);
            }
        }
        return map;
    }

    /**
     * Returns the value of an annotation element.
     *
     * @param beanDesc the {@link BeanDesc} representing the annotation
     * @param annotation the annotation
     * @param name the name of the element
     * @return the value of the annotation element
     */
    protected static Object getProperty(final BeanDesc beanDesc, final Annotation annotation, final String name) {
        final MethodDesc methodDesc = beanDesc.getMethodDescNoException(name);
        if (methodDesc == null) {
            return null;
        }
        final Object value = methodDesc.invoke(annotation);
        if (value == null || "".equals(value)) {
            return null;
        }
        return value;
    }

}
