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
package org.codelibs.core.xml;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.codelibs.core.exception.ParserConfigurationRuntimeException;
import org.codelibs.core.exception.SAXRuntimeException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * Utility class for {@link SAXParser}.
 *
 * @author higa
 */
public abstract class SAXParserFactoryUtil {

    /**
     * Do not instantiate.
     */
    protected SAXParserFactoryUtil() {
    }

    /**
     * Creates a new instance of {@link SAXParserFactory}.
     *
     * @return A new instance of {@link SAXParserFactory}.
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
     * Creates a new instance of {@link SAXParser} using the default configuration of {@link SAXParserFactory}.
     *
     * @return A new instance of {@link SAXParser}.
     */
    public static SAXParser newSAXParser() {
        return newSAXParser(newInstance());
    }

    /**
     * Creates a new instance of {@link SAXParser} using the specified {@link SAXParserFactory}.
     *
     * @param factory
     *            {@link SAXParserFactory}. Must not be {@literal null}.
     * @return A new instance of {@link SAXParser}.
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
     * Sets the enable/disable state of XInclude.
     *
     * @param spf
     *            {@link SAXParserFactory}. Must not be {@literal null}.
     * @param state
     *            <code>true</code> to enable XInclude.
     * @return <code>true</code> if the XInclude state was successfully set.
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
