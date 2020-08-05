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
package org.codelibs.core.xml;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codelibs.core.exception.ParserConfigurationRuntimeException;

/**
 * {@link DocumentBuilderFactory}の用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class DocumentBuilderFactoryUtil {

    /**
     * 新しい {@link DocumentBuilderFactory}のインスタンスを返します。
     *
     * @return 新しい {@link DocumentBuilderFactory}のインスタンス
     */
    public static DocumentBuilderFactory newInstance() {
        return newInstance(false);
    }

    public static DocumentBuilderFactory newInstance(final boolean external) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        if (!external) {
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        }
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        }
        return factory;
    }

    /**
     * 新しい {@link DocumentBuilder}を作成します。
     *
     * @return 新しい {@link DocumentBuilder}
     */
    public static DocumentBuilder newDocumentBuilder() {
        try {
            return newInstance().newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        }
    }
}
