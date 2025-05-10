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

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codelibs.core.exception.ParserConfigurationRuntimeException;
import org.codelibs.core.log.Logger;

/**
 * Utility class for {@link DocumentBuilderFactory}.
 *
 * @author higa
 */
public abstract class DocumentBuilderFactoryUtil {

    private static final Logger logger = Logger.getLogger(DocumentBuilderFactoryUtil.class);

    /**
     * Returns a new instance of {@link DocumentBuilderFactory}.
     *
     * @return A new instance of {@link DocumentBuilderFactory}.
     */
    public static DocumentBuilderFactory newInstance() {
        return newInstance(false);
    }

    public static DocumentBuilderFactory newInstance(final boolean external) {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        if (!external) {
            try {
                factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
                factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            } catch (final Exception e) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Failed to set a property.", e);
                }
            }
        }
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        }
        return factory;
    }

    /**
     * Creates a new {@link DocumentBuilder}.
     *
     * @return A new {@link DocumentBuilder}.
     */
    public static DocumentBuilder newDocumentBuilder() {
        try {
            return newInstance().newDocumentBuilder();
        } catch (final ParserConfigurationException e) {
            throw new ParserConfigurationRuntimeException(e);
        }
    }
}
