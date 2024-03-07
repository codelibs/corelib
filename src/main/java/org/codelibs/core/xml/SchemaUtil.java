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

import java.io.File;
import java.net.URL;

import javax.xml.transform.Source;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.codelibs.core.exception.SAXRuntimeException;
import org.xml.sax.SAXException;

/**
 * {@link Schema}のためのユーティリティ・クラスです。
 *
 * @author koichik
 */
public abstract class SchemaUtil {

    /**
     * ファイルからW3C XML Schemaのための{@link Schema}を生成します。
     *
     * @param schema
     *            W3C XML Schemaファイル。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final File schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * {@link Source}からW3C XML Schemaのための{@link Schema}を生成します。
     *
     * @param schema
     *            W3C XML Schemaを読み込むための{@link Source}。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final Source schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * URLからW3C XML Schemaのための{@link Schema}を生成します。
     *
     * @param schema
     *            W3C XML SchemaのURL。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newW3cXmlSchema(final URL schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newW3cXmlSchemaFactory(), schema);
    }

    /**
     * ファイルからRELAX NGのための{@link Schema}を生成します。
     *
     * @param schema
     *            RELAX NGファイル。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final File schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * {@link Source}からRELAX NGのための{@link Schema}を生成します。
     *
     * @param schema
     *            RELAX NGを読み込むための{@link Source}。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final Source schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * URLからRELAX NGのための{@link Schema}を生成します。
     *
     * @param schema
     *            RELAX NGのURL。{@literal null}であってはいけません
     * @return {@link Schema}
     */
    public static Schema newRelaxNgSchema(final URL schema) {
        assertArgumentNotNull("schema", schema);
        return newSchema(SchemaFactoryUtil.newRelaxNgSchemaFactory(), schema);
    }

    /**
     * 指定の{@link SchemaFactory}を使用して{@link Schema}を作成します。
     *
     * @param factory
     *            {@link SchemaFactory}。{@literal null}であってはいけません
     * @param schema
     *            スキーマファイル。{@literal null}であってはいけません
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
     * 指定の{@link SchemaFactory}を使用して{@link Schema}を作成します。
     *
     * @param factory
     *            {@link SchemaFactory}。{@literal null}であってはいけません
     * @param schema
     *            スキーマを読み込むための{@link Source}。{@literal null}であってはいけません
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
     * 指定の{@link SchemaFactory}を使用して{@link Schema}を作成します。
     *
     * @param factory
     *            {@link SchemaFactory}。{@literal null}であってはいけません
     * @param schema
     *            スキーマのURL。{@literal null}であってはいけません
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
