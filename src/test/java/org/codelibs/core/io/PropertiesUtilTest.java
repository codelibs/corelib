/*
 * Copyright 2004-2012 the Seasar Foundation and the Others.
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
package org.codelibs.core.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.util.Properties;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.exception.NullArgumentException;
import org.codelibs.core.net.URLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

/**
 * @author wyukawa
 *
 */
public class PropertiesUtilTest {

    URL url = ResourceUtil.getResource(getClass().getName().replace('.', '/') + ".txt");

    File inputFile = URLUtil.toFile(url);

    /**
     *
     */
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    /**
     * @see org.junit.rules.ExpectedException
     */
    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.InputStream)}
     * .
     */
    @Test
    public void testLoadPropertiesInputStream() {
        final InputStream inputStream = ResourceUtil.getResourceAsStream("org/codelibs/core/io/test.properties");
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, inputStream);
        assertThat(properties.getProperty("hoge"), is("ほげ"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.InputStream)}
     * .
     */
    @Test
    public void testLoadPropertiesInputStreamPropsNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[props] is null."));
        final InputStream inputStream = ResourceUtil.getResourceAsStream("org/codelibs/core/io/test.properties");
        PropertiesUtil.load(null, inputStream);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.InputStream)}
     * .
     */
    @Test
    public void testLoadPropertiesInputStreamInNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[in] is null."));
        final Properties properties = new Properties();
        final InputStream inputStream = null;
        PropertiesUtil.load(properties, inputStream);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.Reader)}
     * .
     */
    @Test
    public void testLoadPropertiesReader() {
        final InputStreamReader inputStreamReader =
                ReaderUtil.create(ResourceUtil.getResourceAsStream("org/codelibs/core/io/test.properties"), "UTF-8");
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, inputStreamReader);
        assertThat(properties.getProperty("hoge"), is("ほげ"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.Reader)}
     * .
     */
    @Test
    public void testLoadPropertiesReaderPropsNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[props] is null."));
        final InputStreamReader inputStreamReader =
                ReaderUtil.create(ResourceUtil.getResourceAsStream("org/codelibs/core/io/test.properties"), "UTF-8");
        PropertiesUtil.load(null, inputStreamReader);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(java.util.Properties, java.io.Reader)}
     * .
     */
    @Test
    public void testLoadPropertiesReaderReaderNull() {
        exception.expect(NullArgumentException.class);
        exception.expectMessage(is("[ECL0008]argument[reader] is null."));
        final InputStreamReader inputStreamReader = null;
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, inputStreamReader);
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(Properties, File)} .
     */
    @Test
    public void testLoadPropertiesFile() {
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, inputFile);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#load(Properties, File, String)}
     * .
     */
    @Test
    public void testLoadPropertiesFileString() {
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, inputFile, "UTF-8");
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#store(Properties, java.io.OutputStream, String)}
     * .
     *
     * @throws IOException
     */
    @Test
    public void testStorePropertiesOutputStreamString() throws IOException {
        final Properties outProperties = new Properties();
        outProperties.setProperty("a", "A");
        final File file = tempFolder.newFile("hoge.properties");
        final FileOutputStream outputStream = OutputStreamUtil.create(file);
        PropertiesUtil.store(outProperties, outputStream, "comments");
        CloseableUtil.close(outputStream);
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, file);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#store(Properties, java.io.Writer, String)}
     * .
     *
     * @throws IOException
     */
    @Test
    public void testStorePropertiesWriterString() throws IOException {
        final Properties outProperties = new Properties();
        outProperties.setProperty("a", "A");
        final File file = tempFolder.newFile("hoge.properties");
        final Writer writer = WriterUtil.create(file);
        PropertiesUtil.store(outProperties, writer, "comments");
        CloseableUtil.close(writer);
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, file);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#store(Properties, File, String, String)}
     * .
     *
     * @throws IOException
     */
    @Test
    public void testStorePropertiesFileStringString() throws IOException {
        final Properties outProperties = new Properties();
        outProperties.setProperty("a", "A");
        final File file = tempFolder.newFile("hoge.properties");
        PropertiesUtil.store(outProperties, file, "UTF-8", "comments");
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, file);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * Test method for
     * {@link org.codelibs.core.io.PropertiesUtil#store(Properties, File, String)}
     * .
     *
     * @throws IOException
     */
    @Test
    public void testStorePropertiesFileString() throws IOException {
        final Properties outProperties = new Properties();
        outProperties.setProperty("a", "A");
        final File file = tempFolder.newFile("hoge.properties");
        PropertiesUtil.store(outProperties, file, "comments");
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, file);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * {@link org.codelibs.core.io.PropertiesUtil#load(Properties, URL)}
     */
    @Test
    public void testLoadPropertiesUrl() {
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, url);
        assertThat(properties.getProperty("a"), is("A"));
    }

    /**
     * {@link org.codelibs.core.io.PropertiesUtil#load(Properties, URL)}
     */
    @Test
    public void testLoadPropertiesUrlThrowIOException() {
        exception.expect(IORuntimeException.class);
        exception.expectMessage(is("[ECL0040]IOException occurred, because java.io.IOException: load"));
        final Properties properties = new IOExceptionOccurProperties();
        PropertiesUtil.load(properties, url);
    }

    private static class IOExceptionOccurProperties extends Properties {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public synchronized void load(final InputStream inStream) throws IOException {
            throw new IOException("load");
        }

    }

    /**
     * {@link org.codelibs.core.io.PropertiesUtil#load(Properties, String)}
     */
    @Test
    public void testLoadPropertiesPath() {
        final Properties properties = new Properties();
        PropertiesUtil.load(properties, "org/codelibs/core/io/test.properties");
        assertThat(properties.getProperty("hoge"), is("ほげ"));
    }

}
