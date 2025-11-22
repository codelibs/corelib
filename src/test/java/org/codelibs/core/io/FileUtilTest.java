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

import static org.codelibs.core.io.FileUtil.readBytes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.net.URLUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author koichik
 *
 */
public class FileUtilTest {

    URL url = ResourceUtil.getResource(getClass().getName().replace('.', '/') + ".txt");

    File inputFile = URLUtil.toFile(url);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

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

    /**
     * Test isPathSafe with safe path
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_SafePath() throws Exception {
        final Path baseDir = tempFolder.getRoot().toPath();
        final Path safePath = baseDir.resolve("subdir/file.txt");

        assertTrue("Safe path should be allowed", FileUtil.isPathSafe(safePath, baseDir));
    }

    /**
     * Test isPathSafe with path traversal attempt
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_PathTraversalAttempt() throws Exception {
        final Path baseDir = tempFolder.getRoot().toPath();
        final Path traversalPath = baseDir.resolve("../../../etc/passwd");

        assertFalse("Path traversal should be blocked", FileUtil.isPathSafe(traversalPath, baseDir));
    }

    /**
     * Test isPathSafe with File objects
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_WithFiles() throws Exception {
        final File baseDir = tempFolder.getRoot();
        final File safeFile = new File(baseDir, "subdir/file.txt");
        final File unsafeFile = new File(baseDir, "../../../etc/passwd");

        assertTrue("Safe file should be allowed", FileUtil.isPathSafe(safeFile, baseDir));
        assertFalse("Unsafe file should be blocked", FileUtil.isPathSafe(unsafeFile, baseDir));
    }

    /**
     * Test isPathSafe with path inside base directory
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_PathInsideBase() throws Exception {
        final Path baseDir = tempFolder.getRoot().toPath();
        final Path subDir = Files.createDirectory(baseDir.resolve("subdir"));
        final Path file = Files.createFile(subDir.resolve("test.txt"));

        assertTrue("Path inside base should be allowed", FileUtil.isPathSafe(file, baseDir));
    }

    /**
     * Test isPathSafe with path outside base directory
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_PathOutsideBase() throws Exception {
        final Path baseDir = tempFolder.getRoot().toPath();
        final Path outsidePath = Paths.get("/tmp/outside.txt");

        assertFalse("Path outside base should be blocked", FileUtil.isPathSafe(outsidePath, baseDir));
    }

    /**
     * Test isPathSafe with same path as base
     *
     * @throws Exception
     */
    @Test
    public void testIsPathSafe_SameAsBase() throws Exception {
        final Path baseDir = tempFolder.getRoot().toPath();

        assertTrue("Base directory itself should be allowed", FileUtil.isPathSafe(baseDir, baseDir));
    }

    /**
     * Test readBytes with large file throws exception
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_LargeFile() throws Exception {
        final File largeFile = tempFolder.newFile("large.dat");

        // Create a file larger than MAX_BUF_SIZE (10MB)
        // Write 11MB of data
        try (FileOutputStream fos = new FileOutputStream(largeFile)) {
            final byte[] chunk = new byte[1024 * 1024]; // 1MB
            for (int i = 0; i < 11; i++) { // Write 11MB
                fos.write(chunk);
            }
        }

        try {
            FileUtil.readBytes(largeFile);
            fail("Expected IORuntimeException for large file");
        } catch (final IORuntimeException e) {
            assertTrue("Error message should mention file size", e.getMessage().contains("File too large"));
        }
    }

    /**
     * Test readBytes with file within size limit
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_NormalFile() throws Exception {
        final File normalFile = tempFolder.newFile("normal.txt");
        final String content = "Test content for normal file";

        try (FileOutputStream fos = new FileOutputStream(normalFile)) {
            fos.write(content.getBytes("UTF-8"));
        }

        final byte[] result = FileUtil.readBytes(normalFile);
        assertThat(new String(result, "UTF-8"), is(content));
    }

    /**
     * Test readBytes with empty file
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_EmptyFile() throws Exception {
        final File emptyFile = tempFolder.newFile("empty.txt");

        final byte[] result = FileUtil.readBytes(emptyFile);
        assertThat(result.length, is(0));
    }

    /**
     * Test readBytes with null file throws exception
     */
    @Test
    public void testReadBytes_NullFile() {
        try {
            FileUtil.readBytes(null);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Test isPathSafe with null path throws exception
     */
    @Test
    public void testIsPathSafe_NullPath() {
        final Path baseDir = tempFolder.getRoot().toPath();
        try {
            FileUtil.isPathSafe((Path) null, baseDir);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Test isPathSafe with null base directory throws exception
     */
    @Test
    public void testIsPathSafe_NullBase() {
        final Path path = Paths.get("/tmp/test.txt");
        try {
            FileUtil.isPathSafe(path, null);
            fail("Expected IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // Expected
        }
    }

    /**
     * Test readBytes with custom maxSize - file within limit
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_CustomMaxSize_WithinLimit() throws Exception {
        final File file = tempFolder.newFile("small.txt");
        final String content = "Small content";
        final FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(content.getBytes("UTF-8"));
        } finally {
            out.close();
        }

        // Set maxSize larger than file size
        final byte[] bytes = FileUtil.readBytes(file, 1024);
        assertThat(new String(bytes, "UTF-8"), is(content));
    }

    /**
     * Test readBytes with custom maxSize - file exceeds limit
     *
     * @throws Exception
     */
    @Test(expected = IORuntimeException.class)
    public void testReadBytes_CustomMaxSize_ExceedsLimit() throws Exception {
        final File file = tempFolder.newFile("medium.txt");
        final byte[] data = new byte[1024]; // 1KB
        final FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(data);
        } finally {
            out.close();
        }

        // Set maxSize smaller than file size (should throw exception)
        FileUtil.readBytes(file, 512);
    }

    /**
     * Test readBytes with custom maxSize of zero
     *
     * @throws Exception
     */
    @Test(expected = IORuntimeException.class)
    public void testReadBytes_CustomMaxSize_Zero() throws Exception {
        final File file = tempFolder.newFile("any.txt");
        final FileOutputStream out = new FileOutputStream(file);
        try {
            out.write("content".getBytes("UTF-8"));
        } finally {
            out.close();
        }

        // Set maxSize to zero (should throw exception for any non-empty file)
        FileUtil.readBytes(file, 0);
    }

    /**
     * Test readBytes with custom maxSize for empty file
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_CustomMaxSize_EmptyFile() throws Exception {
        final File file = tempFolder.newFile("empty.txt");

        // Empty file should work with any maxSize including 0
        final byte[] bytes = FileUtil.readBytes(file, 0);
        assertThat(bytes.length, is(0));
    }

    /**
     * Test readBytes with very large custom maxSize
     *
     * @throws Exception
     */
    @Test
    public void testReadBytes_CustomMaxSize_VeryLarge() throws Exception {
        final File file = tempFolder.newFile("test.txt");
        final String content = "Test content";
        final FileOutputStream out = new FileOutputStream(file);
        try {
            out.write(content.getBytes("UTF-8"));
        } finally {
            out.close();
        }

        // Set maxSize very large
        final byte[] bytes = FileUtil.readBytes(file, Long.MAX_VALUE);
        assertThat(new String(bytes, "UTF-8"), is(content));
    }

    private String getPath(final String fileName) {
        return getClass().getName().replace('.', '/').replaceFirst(getClass().getSimpleName(), fileName);
    }

}
