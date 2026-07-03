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
package org.codelibs.core.beans.impl;

import static org.codelibs.core.TestUtil.sameClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.junit.Test;

/**
 * Tests {@link BeanDescImpl} support for Java {@link Record} types.
 */
public class RecordBeanDescTest {

    /** A record with primitive components. */
    public record Point(int x, int y) {
    }

    /** A record mixing a reference-type component and a primitive component. */
    public record Person(String name, int age) {
    }

    /** A conventional JavaBean used to confirm non-record behavior is unchanged. */
    public static class Hello {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(final String message) {
            this.message = message;
        }
    }

    /**
     * Record accessors ({@code name()} form, without get/is/set prefixes) are detected as read-only properties.
     */
    @Test
    public void testRecordPropertyDetection() throws Exception {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Point.class);
        // Only x and y; the record branch must not double-register alongside the get/is/set scan.
        assertThat(beanDesc.getPropertyDescSize(), is(2));

        final PropertyDesc x = beanDesc.getPropertyDesc("x");
        assertThat(x.getPropertyName(), is("x"));
        assertThat(x.getPropertyType(), is(sameClass(int.class)));
        assertThat(x.getReadMethod(), is(notNullValue()));
        assertThat(x.getReadMethod().getName(), is("x"));
        assertThat(x.getWriteMethod(), is(nullValue()));
        assertThat(x.isReadable(), is(true));
        assertThat(x.isWritable(), is(false));
        assertThat(x.hasWriteMethod(), is(false));

        assertThat(beanDesc.hasPropertyDesc("y"), is(true));
    }

    /**
     * The property value is read through the record accessor.
     */
    @Test
    public void testRecordGetValue() throws Exception {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Point.class);
        final Point point = new Point(10, 20);
        assertThat(beanDesc.getPropertyDesc("x").getValue(point), is(10));
        assertThat(beanDesc.getPropertyDesc("y").getValue(point), is(20));
    }

    /**
     * A record component of reference type is read correctly.
     */
    @Test
    public void testRecordReferenceComponent() throws Exception {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Person.class);
        final PropertyDesc name = beanDesc.getPropertyDesc("name");
        assertThat(name.getPropertyType(), is(sameClass(String.class)));
        final Person person = new Person("Alice", 30);
        assertThat(name.getValue(person), is("Alice"));
        assertThat(beanDesc.getPropertyDesc("age").getValue(person), is(30));
    }

    /**
     * A record is instantiated through its canonical constructor, which is discovered via the existing
     * constructor scan; arguments are matched in declaration order.
     */
    @Test
    public void testRecordNewInstance() throws Exception {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Point.class);
        final Point point = beanDesc.newInstance(3, 4);
        assertThat(point, is(notNullValue()));
        assertThat(point.x(), is(3));
        assertThat(point.y(), is(4));
    }

    /**
     * A conventional JavaBean with getter/setter continues to expose a readable and writable property.
     */
    @Test
    public void testRegularBeanUnchanged() throws Exception {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(Hello.class);
        final PropertyDesc message = beanDesc.getPropertyDesc("message");
        assertThat(message.getPropertyType(), is(sameClass(String.class)));
        assertThat(message.getReadMethod(), is(notNullValue()));
        assertThat(message.getWriteMethod(), is(notNullValue()));
        assertThat(message.isReadable(), is(true));
        assertThat(message.isWritable(), is(true));

        final Hello hello = new Hello();
        message.setValue(hello, "world");
        assertThat(message.getValue(hello), is("world"));
    }
}
