/*
 * Copyright 2012-2022 CodeLibs Project and the Others.
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

import static org.codelibs.core.io.FileUtil.readBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.net.URL;

import org.codelibs.core.net.URLUtil;
import org.junit.Test;

/**
 * @author koichik
 *
 */
public class FileUtilTest {

    URL url = ResourceUtil.getResource(getClass().getName().replace('.', '/') + ".txt");

    File inputFile = URLUtil.toFile(url);

    /**
     * @throws Exception
     */
    @Test
    public void testFileToFile() throws Exception {
        final byte[] bytes = readBytes(inputFile);
        assertThat(bytes, is("あいうえお".getBytes("UTF-8")));
    }

    /**
     * @throws Exception
     */
    @Test
    public void testReadUTF8() throws Exception {
        assertThat(FileUtil.readUTF8(getPath("hoge_utf8.txt")), is("あ"));
    }

    private String getPath(final String fileName) {
        return getClass().getName().replace('.', '/').replaceFirst(getClass().getSimpleName(), fileName);
    }

}
