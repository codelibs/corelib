/*
 * Copyright 2012-2024 CodeLibs Project and the Others.
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
package org.codelibs.core.xml;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Method;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codelibs.core.exception.ParserConfigurationRuntimeException;
import org.codelibs.core.exception.SAXRuntimeException;
import org.codelibs.core.lang.ClassUtil;
import org.codelibs.core.lang.MethodUtil;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * {@link SAXParser}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class SAXParserFactoryUtil {

    /**
     * {@link SAXParserFactory}の新しいインスタンスを作成します。
     *
     * @return {@link SAXParserFactory}の新しいインスタンス
     */
    public static SAXParserFactory newInstance() {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        } catch (SAXNotRecognizedException | SAXNotSupportedException e) {
            throw new SAXRuntimeException(e);
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        }
        return factory;
    }

    /**
     * デフォルト構成の{@link SAXParserFactory}を使って{@link SAXParser}の新しいインスタンスを作成します。
     *
     * @return {@link SAXParser}の新しいインスタンス
     */
    public static SAXParser newSAXParser() {
        return newSAXParser(newInstance());
    }

    /**
     * 指定の{@link SAXParserFactory}を使って{@link SAXParser}の新しいインスタンスを作成します。
     *
     * @param factory
     *            {@link SAXParserFactory}。{@literal null}であってはいけません
     * @return {@link SAXParser}の新しいインスタンス
     */
    public static SAXParser newSAXParser(final SAXParserFactory factory) {
        assertArgumentNotNull("factory", factory);

        try {
            return factory.newSAXParser();
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        }
    }

    /**
     * XIncludeの有効／無効を設定します。
     *
     * @param spf
     *            {@link SAXParserFactory}。{@literal null}であってはいけません
     * @param state
     *            XIncludeを有効にするなら<code>true</code>
     * @return XIncludeの有効／無効を設定できた場合は<code>true</code>
     */
    public static boolean setXIncludeAware(final SAXParserFactory spf, final boolean state) {
        assertArgumentNotNull("spf", spf);

        try {
            spf.setXIncludeAware(state);
            return true;
        } catch (final RuntimeException e) {
            return false;
        }
    }

}
