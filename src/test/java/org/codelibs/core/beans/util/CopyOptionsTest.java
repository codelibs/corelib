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
package org.codelibs.core.beans.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.codelibs.core.beans.converter.DateConverter;
import org.codelibs.core.beans.converter.NumberConverter;
import org.codelibs.core.exception.ConverterRuntimeException;
import org.junit.Test;

/**
 * @author higa
 */
public class CopyOptionsTest {

    /**
     * @throws Exception
     */
    @Test
    public void testIncludes() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.include(BeanNames.hoge()), is(sameInstance(option)));
        assertThat(option.includePropertyNames.size(), is(1));
        assertThat(option.includePropertyNames.get(0), is("hoge"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testExcludes() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.exclude(BeanNames.hoge()), is(sameInstance(option)));
        assertThat(option.excludePropertyNames.size(), is(1));
        assertThat(option.excludePropertyNames.get(0), is("hoge"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPrefix() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.prefix(BeanNames.search_()), is(sameInstance(option)));
        assertThat(option.prefix, is("search_"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testBeanDelimiter() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.beanDelimiter('#'), is(sameInstance(option)));
        assertThat(option.beanDelimiter, is('#'));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testMapDelimiter() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.mapDelimiter('#'), is(sameInstance(option)));
        assertThat(option.mapDelimiter, is('#'));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.isTargetProperty("hoge"), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes() throws Exception {
        final CopyOptions option = new CopyOptions().include(BeanNames.hoge());
        assertThat(option.isTargetProperty("hoge"), is(true));
        assertThat(option.isTargetProperty("hoge2"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes_prefix() throws Exception {
        final CopyOptions option = new CopyOptions().include(BeanNames.search_aaa(), BeanNames.bbb()).prefix(BeanNames.search_());
        assertThat(option.isTargetProperty("search_aaa"), is(true));
        assertThat(option.isTargetProperty("bbb"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_excludes() throws Exception {
        final CopyOptions option = new CopyOptions().exclude(BeanNames.hoge());
        assertThat(option.isTargetProperty("hoge"), is(not(true)));
        assertThat(option.isTargetProperty("hoge2"), is(true));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_excludes_prefix() throws Exception {
        final CopyOptions option = new CopyOptions().prefix(BeanNames.abc_()).exclude(BeanNames.abc_exclude());
        assertThat(option.isTargetProperty("abc_value"), is(true));
        assertThat(option.isTargetProperty("abc_exclude"), is(not(true)));
        assertThat(option.isTargetProperty("ab"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_prefix() throws Exception {
        final CopyOptions option = new CopyOptions().prefix(BeanNames.search_());
        assertThat(option.isTargetProperty("search_aaa"), is(true));
        assertThat(option.isTargetProperty("bbb"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testIsTargetProperty_includes_excludes() throws Exception {
        final CopyOptions option =
                new CopyOptions().include(BeanNames.hoge(), BeanNames.hoge2()).exclude(BeanNames.hoge2(), BeanNames.hoge3());
        assertThat(option.isTargetProperty("hoge"), is(true));
        assertThat(option.isTargetProperty("hoge2"), is(not(true)));
        assertThat(option.isTargetProperty("hoge3"), is(not(true)));
        assertThat(option.isTargetProperty("hoge4"), is(not(true)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testTrimPrefix() throws Exception {
        final CopyOptions option = new CopyOptions();
        assertThat(option.trimPrefix("aaa"), is("aaa"));
        option.prefix(BeanNames.search_());
        assertThat(option.trimPrefix("search_aaa"), is("aaa"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_zeroConverter() throws Exception {
        assertThat(new CopyOptions().convertValue(Integer.valueOf(1), "aaa", null), is((Object) 1));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_propertyConverter_asString() throws Exception {
        assertThat(new CopyOptions().converter(new NumberConverter("##0"), "aaa").convertValue(Integer.valueOf(1), "aaa", null),
                is((Object) "1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_propertyConverter_asObject() throws Exception {
        assertThat(new CopyOptions().converter(new NumberConverter("##0"), BeanNames.aaa()).convertValue("1", "aaa", null),
                is((Object) 1L));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_typeConverter_asString() throws Exception {
        assertThat(new CopyOptions().converter(new NumberConverter("##0")).convertValue(Integer.valueOf(1), "aaa", null), is((Object) "1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_typeConverter_asObject() throws Exception {
        assertThat(new CopyOptions().converter(new NumberConverter("##0")).convertValue("1", "aaa", Integer.class), is((Object) 1L));
        assertThat(new CopyOptions().converter(new DateConverter("yyyyMMdd")).convertValue(new Timestamp(0), "aaa", String.class),
                is((Object) "19700101"));
    }

    /**
     * @throws Exception
     */
    @Test(expected = ConverterRuntimeException.class)
    public void testConvertValue_throwable() throws Exception {
        new CopyOptions().converter(new NumberConverter("##0")).convertValue("a", "aaa", Integer.class);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertValue_dateToDate() throws Exception {
        final Date date = new Date(1);
        assertThat(new CopyOptions().convertValue(date, "aaa", Date.class), is((Object) date));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testDateConverter() throws Exception {
        assertThat(new CopyOptions().dateConverter("yyyyMMdd").convertValue(new java.util.Date(0), "aaa", String.class),
                is((Object) "19700101"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSqlDateConverter() throws Exception {
        assertThat(new CopyOptions().sqlDateConverter("yyyyMMdd").convertValue(new java.sql.Date(0), "aaa", String.class),
                is((Object) "19700101"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testTimeConverter() throws Exception {
        assertThat(new CopyOptions().timeConverter("ss").convertValue(new java.sql.Time(0), "aaa", String.class), is((Object) "00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testTimestampConverter() throws Exception {
        assertThat(new CopyOptions().timestampConverter("yyyyMMdd ss").convertValue(new java.sql.Timestamp(0), "aaa", String.class),
                is((Object) "19700101 00"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFindDefaultConverter() throws Exception {
        assertThat(new CopyOptions().findDefaultConverter(Time.class), is(CopyOptions.DEFAULT_TIME_CONVERTER));
        assertThat(new CopyOptions().findDefaultConverter(Timestamp.class), is(CopyOptions.DEFAULT_TIMESTAMP_CONVERTER));
        assertThat(new CopyOptions().findDefaultConverter(java.util.Date.class), is(CopyOptions.DEFAULT_TIMESTAMP_CONVERTER));
        assertThat(new CopyOptions().findDefaultConverter(java.sql.Date.class), is(CopyOptions.DEFAULT_DATE_CONVERTER));
        assertThat(new CopyOptions().findDefaultConverter(Integer.class), is(nullValue()));
    }

    /**
     * @author kato
     */
    public static class BeanNames {

        /**
         * CharSequenceを作成します。
         *
         * @param name
         * @return CharSequence
         */
        protected static CharSequence createCharSequence(final String name) {
            return new CharSequence() {

                @Override
                public String toString() {
                    return name;
                }

                @Override
                public char charAt(final int index) {
                    return name.charAt(index);
                }

                @Override
                public int length() {
                    return name.length();
                }

                @Override
                public CharSequence subSequence(final int start, final int end) {
                    return name.subSequence(start, end);
                }

            };
        }

        /**
         * @return CharSequence
         */
        public static CharSequence aaa() {
            return createCharSequence("aaa");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence bbb() {
            return createCharSequence("bbb");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence ccc() {
            return createCharSequence("ccc");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence hoge() {
            return createCharSequence("hoge");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence hoge2() {
            return createCharSequence("hoge2");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence hoge3() {
            return createCharSequence("hoge3");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence search_aaa() {
            return createCharSequence("search_aaa");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence abc_exclude() {
            return createCharSequence("abc_exclude");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence search_() {
            return createCharSequence("search_");
        }

        /**
         * @return CharSequence
         */
        public static CharSequence abc_() {
            return createCharSequence("abc_");
        }

    }

}
