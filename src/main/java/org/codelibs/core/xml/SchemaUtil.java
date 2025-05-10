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

import java.io.File;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.codelibs.core.exception.SAXRuntimeException;
import org.xml.sax.SAXException;

/**
 * Utility class for {@link Schema}.
 *
 * @author koichik
 */
public abstract class SchemaUtil {

    /**
     * Generates a {@link Schema} for W3C XML Schema from a file.
     *
     * @param schema
     *            W3C XML Schema file. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final File schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * Generates a {@link Schema} for W3C XML Schema from a {@link Source}.
     *
     * @param schema
     *            {@link Source} to load the W3C XML Schema. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final Source schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * Generates a {@link Schema} for W3C XML Schema from a URL.
     *
     * @param schema
     *            URL of the W3C XML Schema. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final URL schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * Generates a {@link Schema} for RELAX NG from a file.
     *
     * @param schema
     *            RELAX NG file. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final File schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * Generates a {@link Schema} for RELAX NG from a {@link Source}.
     *
     * @param schema
     *            {@link Source} to load the RELAX NG. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final Source schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * Generates a {@link Schema} for RELAX NG from a URL.
     *
     * @param schema
     *            URL of the RELAX NG. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final URL schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * Creates a {@link Schema} using the specified {@link SchemaFactory}.
     *
     * @param factory
     *            {@link SchemaFactory}. Must not be {@literal null}.
     * @param schema
     *            Schema file. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newSchema(final SchemaFactory factory, final File schema) {
        assertArgumentNotNull("factory", factory);
        assertArgumentNotNull("schema", schema);

        try {
            return factory.newSchema(schema);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        }
    }

    /**
     * Creates a {@link Schema} using the specified {@link SchemaFactory}.
     *
     * @param factory
     *            {@link SchemaFactory}. Must not be {@literal null}.
     * @param schema
     *            {@link Source} to load the schema. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newSchema(final SchemaFactory factory, final Source schema) {
        assertArgumentNotNull("factory", factory);
        assertArgumentNotNull("schema", schema);

        try {
            return factory.newSchema(schema);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        }
    }

    /**
     * Creates a {@link Schema} using the specified {@link SchemaFactory}.
     *
     * @param factory
     *            {@link SchemaFactory}. Must not be {@literal null}.
     * @param schema
     *            URL of the schema. Must not be {@literal null}.
     * @return {@link Schema}
     */
    public static Schema newSchema(final SchemaFactory factory, final URL schema) {
        assertArgumentNotNull("factory", factory);
        assertArgumentNotNull("schema", schema);

        try {
            return factory.newSchema(schema);
        } catch (final SAXException e) {
            throw new SAXRuntimeException(e);
        }
    }

}
