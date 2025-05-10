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
package org.codelibs.core.io;

import static org.codelibs.core.io.CopyUtil.copy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import org.codelibs.core.net.URLUtil;
import org.junit.Test;

/**
 * @author koichik
 */
public class CopyUtilTest {

    static byte[] srcBytes = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    static String srcString = "ABCDEFGHIJKLMN";

    static String urlString = "あいうえお";

    InputStream is = new ByteArrayInputStream(srcBytes);

    ByteArrayOutputStream os = new ByteArrayOutputStream();

    Reader reader = new StringReader(srcString);

    StringWriter writer = new StringWriter();

    StringBuilder builder = new StringBuilder();

    URL url = ResourceUtil.getResource(getClass().getName().replace('.', '/') + ".txt");

    File inputFile = URLUtil.toFile(url);

    File outputFile = new File(inputFile.getParentFile(), ".out");

    /**
     * @throws Exception
     */
    @Test
    public void testIsToOs() throws Exception {
        final int result = copy(is, os);
        assertThat(result, is(srcBytes.length));
        assertThat(os.toByteArray(), is(srcBytes));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testReaderToWriter() throws Exception {
        final int result = copy(reader, writer);
        assertThat(result, is(srcString.length()));
        assertThat(writer.toString(), is(srcString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testBytesToOs() throws Exception {
        final int result = copy(srcBytes, os);
        assertThat(result, is(srcBytes.length));
        assertThat(os.toByteArray(), is(srcBytes));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testStringToWriter() throws Exception {
        final int result = copy(srcString, writer);
        assertThat(result, is(srcString.length()));
        assertThat(writer.toString(), is(srcString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testReaderToStringBuilder() throws Exception {
        final int result = copy(reader, builder);
        assertThat(result, is(srcString.length()));
        assertThat(new String(builder), is(srcString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testUrlToWriter() throws Exception {
        final int result = copy(url, "UTF-8", writer);
        assertThat(result, is(urlString.length()));
        assertThat(writer.toString(), is(urlString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFileToWriter() throws Exception {
        final int result = copy(inputFile, "UTF-8", writer);
        assertThat(result, is(urlString.length()));
        assertThat(writer.toString(), is(urlString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFileToFile() throws Exception {
        int result = copy(inputFile, outputFile);
        assertThat(result, is(urlString.getBytes("UTF-8").length));

        result = copy(outputFile, "UTF-8", writer);
        assertThat(writer.toString(), is(urlString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testFileToFile_Encoding() throws Exception {
        int result = copy(inputFile, "UTF-8", outputFile, "Shift_JIS");
        assertThat(result, is(urlString.length()));

        result = copy(outputFile, "Shift_JIS", writer);
        assertThat(writer.toString(), is(urlString));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testUrlToFile() throws Exception {
        int result = copy(url, outputFile);
        assertThat(result, is(urlString.getBytes("UTF-8").length));

        result = copy(outputFile, "UTF-8", writer);
        assertThat(writer.toString(), is(urlString));
    }

}
