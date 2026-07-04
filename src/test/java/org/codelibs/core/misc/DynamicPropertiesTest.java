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
package org.codelibs.core.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link DynamicProperties}.
 */
public class DynamicPropertiesTest {

    private File tempDir;

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        tempDir = Files.createTempDirectory("dynprops").toFile();
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        deleteRecursively(tempDir);
    }

    private static void deleteRecursively(final File file) {
        if (file == null || !file.exists()) {
            return;
        }
        final File[] children = file.listFiles();
        if (children != null) {
            for (final File child : children) {
                deleteRecursively(child);
            }
        }
        file.delete();
    }

    private DynamicProperties newProps() {
        return new DynamicProperties(new File(tempDir, "test.properties"));
    }

    /**
     * Repeated calls faster than the check interval must not slide the throttle window and
     * suppress the modification check forever.
     *
     * @throws Exception
     */
    @Test
    public void testIsUpdated_throttleWindowDoesNotSlide() throws Exception {
        final DynamicProperties props = newProps();
        props.checkInterval = 2000L;
        // Force the real check to report "updated" whenever it actually runs.
        props.lastModified = 0L;
        // Place the last check just inside the throttle window.
        props.lastChecked = System.currentTimeMillis() - 1800L;

        // Inside the throttle window: returns false without advancing lastChecked.
        assertFalse(props.isUpdated());

        // Once the interval elapses the real check must run, even though calls kept coming.
        Thread.sleep(400L);
        assertTrue(props.isUpdated());
    }

    /**
     * {@link DynamicProperties#forEach} must operate on the backing properties, not the empty super class.
     *
     * @throws Exception
     */
    @Test
    public void testForEachDelegatesToInternalProperties() throws Exception {
        final DynamicProperties props = newProps();
        props.setProperty("a", "1");
        props.setProperty("b", "2");

        final Map<Object, Object> collected = new HashMap<>();
        props.forEach(collected::put);

        assertEquals(2, collected.size());
        assertEquals("1", collected.get("a"));
        assertEquals("2", collected.get("b"));
    }

    /**
     * {@link DynamicProperties#getOrDefault} must read from the backing properties.
     *
     * @throws Exception
     */
    @Test
    public void testGetOrDefaultDelegatesToInternalProperties() throws Exception {
        final DynamicProperties props = newProps();
        props.setProperty("a", "1");

        assertEquals("1", props.getOrDefault("a", "z"));
        assertEquals("z", props.getOrDefault("missing", "z"));
    }

    /**
     * {@link DynamicProperties#computeIfAbsent} must write to the backing properties so that
     * {@link DynamicProperties#getProperty} can read the value back.
     *
     * @throws Exception
     */
    @Test
    public void testComputeIfAbsentDelegatesToInternalProperties() throws Exception {
        final DynamicProperties props = newProps();

        props.computeIfAbsent("k", k -> "v");

        assertEquals("v", props.getProperty("k"));
    }

    /**
     * A path whose intermediate directories do not exist must be created.
     *
     * @throws Exception
     */
    @Test
    public void testConstructorCreatesMissingNestedDirectories() throws Exception {
        final File nested = new File(tempDir, "a/b/c/test.properties");

        new DynamicProperties(nested);

        assertTrue(nested.exists());
    }

    /**
     * A bare file name (no parent directory component) must not raise a {@link NullPointerException}.
     *
     * @throws Exception
     */
    @Test
    public void testConstructorWithBareFilenameDoesNotThrowNpe() throws Exception {
        final File bare = new File("dynprops_" + System.nanoTime() + ".properties");
        try {
            new DynamicProperties(bare);
            assertTrue(bare.exists());
        } finally {
            bare.delete();
        }
    }
}
