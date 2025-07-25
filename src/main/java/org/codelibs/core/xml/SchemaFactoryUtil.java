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
import javax.xml.validation.SchemaFactory;

import org.codelibs.core.log.Logger;

/**
 * Utility class for {@link SchemaFactory}.
 *
 * @author koichik
 */
public abstract class SchemaFactoryUtil {

    private static final Logger logger = Logger.getLogger(SchemaFactoryUtil.class);

    /**
     * Do not instantiate.
     */
    private SchemaFactoryUtil() {
    }

    /**
     * Creates a {@link SchemaFactory} for W3C XML Schema.
     *
     * @return a {@link SchemaFactory} for W3C XML Schema
     */
    public static SchemaFactory newW3cXmlSchemaFactory() {
        return newW3cXmlSchemaFactory(false);
    }

    /**
     * Creates a {@link SchemaFactory} for W3C XML Schema.
     *
     * @param external
     *            If {@code true}, external access is allowed.
     * @return a {@link SchemaFactory} for W3C XML Schema
     */
    public static SchemaFactory newW3cXmlSchemaFactory(final boolean external) {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        if (!external) {
            disableExternalResources(schemaFactory);
        }
        return schemaFactory;
    }

    /**
     * Creates a {@link SchemaFactory} for RELAX NG.
     *
     * @return a {@link SchemaFactory} for RELAX NG
     */
    public static SchemaFactory newRelaxNgSchemaFactory() {
        return newRelaxNgSchemaFactory(false);
    }

    /**
     * Creates a {@link SchemaFactory} for RELAX NG.
     *
     * @param external
     *            If {@code true}, external access is allowed.
     * @return a {@link SchemaFactory} for RELAX NG
     */
    public static SchemaFactory newRelaxNgSchemaFactory(final boolean external) {
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI);
        if (!external) {
            disableExternalResources(schemaFactory);
        }
        return schemaFactory;
    }

    private static void disableExternalResources(final SchemaFactory schemaFactory) {
        try {
            schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        } catch (final Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Failed to set a property.", e);
            }
        }
    }
}
