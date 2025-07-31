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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;

import javax.xml.parsers.SAXParser;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.exception.SAXRuntimeException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Utility class for {@link SAXParser}.
 *
 * @author higa
 */
public abstract class SAXParserUtil {

    /**
     * Do not instantiate.
     */
    protected SAXParserUtil() {
    }

    /**
     * Parses the content of the specified {@link InputSource} as XML using the specified {@link DefaultHandler}.
     *
     * @param parser
     *            The {@link SAXParser} to use. Must not be {@literal null}.
     * @param inputSource
     *            The {@link InputSource} containing the content to be parsed. Must not be {@literal null}.
     * @param handler
     *            The SAX {@link DefaultHandler} to use. Must not be {@literal null}.
     */
    public static void parse(final SAXParser parser, final InputSource inputSource, final DefaultHandler handler) {
        assertArgumentNotNull("parser", parser);
        assertArgumentNotNull("inputSource", inputSource);
        assertArgumentNotNull("handler", handler);

        try {
            parser.parse(inputSource, handler);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * Sets a specific property on the base implementation of {@link XMLReader}.
     *
     * @param parser
     *            The {@link SAXParser} on which the property is to be set. Must not be {@literal null}.
     * @param name
     *            The name of the property to be set. Must not be {@literal null} or an empty string.
     * @param value
     *            The value of the property to be set.
     */
    public static void setProperty(final SAXParser parser, final String name, final String value) {
        assertArgumentNotNull("parser", parser);
        assertArgumentNotEmpty("name", name);

        try {
            parser.setProperty(name, value);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        }
    }

}
