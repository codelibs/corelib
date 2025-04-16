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

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.exception.SAXRuntimeException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Utility class for {@link DocumentBuilder}.
 *
 * @author higa
 */
public abstract class DocumentBuilderUtil {

    /**
     * Parses the XML.
     *
     * @param builder
     *            {@link DocumentBuilder}; must not be {@literal null}.
     * @param is
     *            Input stream; must not be {@literal null}.
     * @return {@link Document}
     */
    public static Document parse(final DocumentBuilder builder, final InputStream is) {
        assertArgumentNotNull("builder", builder);
        assertArgumentNotNull("is", is);

        try {
            return builder.parse(is);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

}
