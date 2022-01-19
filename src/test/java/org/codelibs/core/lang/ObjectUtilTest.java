/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * @author wyukawa
 *
 */
public class ObjectUtilTest {

    /**
     * Test method for
     * {@link org.codelibs.core.lang.ObjectUtil#equals(java.lang.Object, java.lang.Object)}
     * .
     */
    @Test
    public void testEqualsObjectObject() {
        assertThat(ObjectUtil.equals(null, null), is(true));
        assertThat(ObjectUtil.equals(null, ""), is(false));
        assertThat(ObjectUtil.equals("", null), is(false));
        assertThat(ObjectUtil.equals("", ""), is(true));
        assertThat(ObjectUtil.equals(Boolean.TRUE, null), is(false));
        assertThat(ObjectUtil.equals(Boolean.TRUE, "true"), is(false));
        assertThat(ObjectUtil.equals(Boolean.TRUE, Boolean.TRUE), is(true));
        assertThat(ObjectUtil.equals(Boolean.TRUE, Boolean.FALSE), is(false));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.lang.ObjectUtil#defaultValue(Object, Object)} .
     */
    @Test
    public void testDefaultValue() {
        final Hoge hoge = new Hoge();
        assertSame(ObjectUtil.defaultValue(null, hoge), hoge);
        assertSame(ObjectUtil.defaultValue(hoge, null), hoge);
        assertSame(ObjectUtil.defaultValue(hoge, hoge), hoge);
        assertSame(ObjectUtil.defaultValue(null, null), null);
        assertThat(ObjectUtil.defaultValue(null, "NULL"), is("NULL"));
        assertThat(ObjectUtil.defaultValue(null, 1), is(1));
        assertThat(ObjectUtil.defaultValue(Boolean.TRUE, true), is(Boolean.TRUE));
        assertThat(ObjectUtil.defaultValue(null, null), is(nullValue()));
    }

    class Hoge {
        private int i;

        private String s;

        /**
         * @param i
         *            the i to set
         */
        public void setI(final int i) {
            this.i = i;
        }

        /**
         * @return the i
         */
        public int getI() {
            return i;
        }

        /**
         * @param s
         *            the s to set
         */
        public void setS(final String s) {
            this.s = s;
        }

        /**
         * @return the s
         */
        public String getS() {
            return s;
        }
    }

}
