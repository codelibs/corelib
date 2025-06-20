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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.beans.factory.BeanDescFactory;
import org.codelibs.core.beans.impl.sub.MogeBean;
import org.codelibs.core.beans.impl.sub.MogeBeanFactory;
import org.codelibs.core.exception.IllegalPropertyRuntimeException;
import org.junit.Test;

/**
 * @author higa
 *
 */
public class PropertyDescImplTest {

    /**
     * @throws Exception
     */
    @Test
    public void testSetValue() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        propDesc.setValue(myBean, new BigDecimal(2));
        assertThat(myBean.getFff(), is(2));
    }

    /**
     * @throws Exception
     */
    public void testSetValue_null() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        propDesc.setValue(myBean, null);
        assertThat(myBean.getFff(), is(0));
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testSetValue_notWritable() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("aaa");
        propDesc.setValue(myBean, null);
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testSetValue_notWritableWithField() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("jjj");
        propDesc.setValue(myBean, null);
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testSetValue_invalidType() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("url");
        propDesc.setValue(myBean, new Object());
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testGetValue_notReable() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("iii");
        propDesc.getValue(myBean);
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testGetValue_notReableWithField() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("kkk");
        propDesc.getValue(myBean);
    }

    /**
     * @throws Exception
     */
    @Test(expected = IllegalPropertyRuntimeException.class)
    public void testSetIllegalValue() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("fff");
        propDesc.setValue(myBean, "hoge");
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSetBigDecimalValue() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("ggg");
        propDesc.setValue(myBean, Integer.valueOf(1));
        assertThat(myBean.getGgg(), is(new BigDecimal(1)));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSetTimestampValue() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("hhh");
        propDesc.setValue(myBean, "2024-06-20 12:08:00");
        assertThat(myBean.getHhh(), is(notNullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSetCalendarValue() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("cal");
        final Date date = new Date();
        propDesc.setValue(myBean, date);
        assertThat(myBean.getCal().getTime(), is(date));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSetIntegerValueToString() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("str");
        propDesc.setValue(myBean, Integer.valueOf(1));
        assertThat(myBean.str, is("1"));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testSetNullToString() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("str");
        propDesc.setValue(myBean, null);
        assertThat(myBean.str, is(nullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testConvertWithStringConstructor() throws Exception {
        final MyBean myBean = new MyBean();
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("URL");
        propDesc.setValue(myBean, "http://www.seasar.org");
        assertThat(myBean.getURL(), is(notNullValue()));
    }

    /**
     *
     */
    public void testConvertWithString() {
        final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(MyDto.class);
        final PropertyDesc pd = beanDesc.getPropertyDesc("myEnum");
        final MyDto dto = new MyDto();
        pd.setValue(dto, "ONE");
        assertEquals(MyEnum.ONE, dto.myEnum);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testGetBeanDesc() throws Exception {
        final BeanDesc beanDesc = new BeanDescImpl(MyBean.class);
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("URL");
        assertThat(propDesc.getBeanDesc(), is(notNullValue()));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testPackagePrivateBean() throws Exception {
        final MogeBean moge = MogeBeanFactory.create("moge");
        final BeanDesc beanDesc = new BeanDescImpl(moge.getClass());
        final PropertyDesc propDesc = beanDesc.getPropertyDesc("name");
        assertThat(propDesc, is(notNullValue()));
        assertThat((String) propDesc.getValue(moge), is("moge"));
    }

    /**
     *
     */
    public static class MyBean {

        private int fff_;

        private BigDecimal ggg_;

        private Timestamp hhh_;

        private String jjj;

        String kkk;

        private URL url_;

        private Calendar cal;

        /**
         *
         */
        public String str;

        /**
         * @return String
         */
        public String getAaa() {
            return null;
        }

        /**
         * @param a
         * @return String
         */
        public String getBbb(final Object a) {
            return null;
        }

        /**
         * @return boolean
         */
        public boolean isCCC() {
            return true;
        }

        /**
         * @return Object
         */
        public Object isDdd() {
            return null;
        }

        /**
         * @return String
         */
        public String getEee() {
            return null;
        }

        /**
         * @param eee
         */
        public void setEee(final String eee) {
        }

        /**
         * @return int
         */
        public int getFff() {
            return fff_;
        }

        /**
         * @param fff
         */
        public void setFff(final int fff) {
            fff_ = fff;
        }

        /**
         * @return String
         */
        public String getJjj() {
            return jjj;
        }

        /**
         * @param kkk
         */
        public void setKkk(final String kkk) {
            this.kkk = kkk;
        }

        /**
         * @param arg1
         * @param arg2
         * @return Number
         */
        public Number add(final Number arg1, final Number arg2) {
            return Integer.valueOf(3);
        }

        /**
         * @return BigDecimal
         */
        public BigDecimal getGgg() {
            return ggg_;
        }

        /**
         * @param ggg
         */
        public void setGgg(final BigDecimal ggg) {
            ggg_ = ggg;
        }

        /**
         * @return Timestamp
         */
        public Timestamp getHhh() {
            return hhh_;
        }

        /**
         * @param hhh
         */
        public void setHhh(final Timestamp hhh) {
            hhh_ = hhh;
        }

        /**
         * @param iii
         */
        public void setIii(final String iii) {
        }

        /**
         * @return URL
         */
        public URL getURL() {
            return url_;
        }

        /**
         * @param url
         */
        public void setURL(final URL url) {
            url_ = url;
        }

        /**
         * @return Returns the cal.
         */
        public Calendar getCal() {
            return cal;
        }

        /**
         * @param cal
         *            The cal to set.
         */
        public void setCal(final Calendar cal) {
            this.cal = cal;
        }
    }

    /**
     *
     */
    public enum MyEnum {
        /**
         *
         */
        ONE,
        /**
         *
         */
        TWO
    }

    /**
     *
     */
    private class MyDto {

        /**
         *
         */
        public MyEnum myEnum;
    }

}
