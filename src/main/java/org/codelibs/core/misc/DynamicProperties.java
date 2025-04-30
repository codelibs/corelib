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
package org.codelibs.core.misc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Enumeration;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.codelibs.core.exception.FileAccessException;
import org.codelibs.core.exception.IORuntimeException;

/**
 * The {@code DynamicProperties} class extends {@link Properties} to provide dynamic
 * loading and storing of properties from a file. It monitors the file for changes
 * and reloads the properties if the file is updated. This class is thread-safe and
 * ensures that the properties are always up-to-date.
 *
 * <p>Key Features:
 * <ul>
 *   <li>Automatically reloads properties if the file is modified.</li>
 *   <li>Supports custom check intervals for file modification checks.</li>
 *   <li>Provides synchronized methods for loading and storing properties.</li>
 *   <li>Extends {@link Properties} and overrides its methods to work with dynamic properties.</li>
 * </ul>
 *
 * <p>Usage:
 * <pre>
 * DynamicProperties dynamicProperties = new DynamicProperties("path/to/properties/file");
 * String value = dynamicProperties.getProperty("key");
 * dynamicProperties.setProperty("key", "newValue");
 * dynamicProperties.store();
 * </pre>
 *
 * <p>Exceptions:
 * <ul>
 *   <li>{@link FileAccessException} - Thrown if there are issues accessing the file.</li>
 *   <li>{@link IORuntimeException} - Thrown if there are I/O errors during file operations.</li>
 * </ul>
 *
 * <p>Thread Safety:
 * This class uses synchronization to ensure thread-safe access to the properties.
 *
 * @see Properties
 */
public class DynamicProperties extends Properties {

    private static final long serialVersionUID = 1L;

    protected long checkInterval = 5000L;

    protected volatile long lastChecked = 0L;

    protected volatile long lastModified = 0L;

    protected volatile File propertiesFile;

    protected volatile Properties properties;

    public DynamicProperties(final String path) {
        this(path == null ? null : new File(path));
    }

    public DynamicProperties(final Path path) {
        this(path == null ? null : path.toFile());
    }

    public DynamicProperties(final File file) {
        // check path
        if (file == null) {
            throw new FileAccessException("ECL0108");
        }

        this.propertiesFile = file;
        if (!this.propertiesFile.exists()) {
            final File parentDir = this.propertiesFile.getParentFile();
            if (!parentDir.exists()) {
                if (!parentDir.mkdir()) {
                    throw new FileAccessException("ECL0109", new Object[] { file.getAbsolutePath() });
                }
            } else if (!parentDir.isDirectory()) {
                throw new FileAccessException("ECL0110", new Object[] { file.getAbsolutePath() });
            }
            properties = new Properties();
            store();
        } else if (!this.propertiesFile.isFile()) {
            throw new FileAccessException("ECL0111", new Object[] { file.getAbsolutePath() });
        }
        load();
    }

    public synchronized void reload(final String path) {
        final File file = new File(path);
        if (!file.exists()) {
            final File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                if (!parentDir.mkdir()) {
                    throw new FileAccessException("ECL0109", new Object[] { path });
                }
            } else if (!parentDir.isDirectory()) {
                throw new FileAccessException("ECL0110", new Object[] { path });
            }
            propertiesFile = file;
            store();
        } else if (!file.isFile()) {
            throw new FileAccessException("ECL0111", new Object[] { path });
        } else {
            propertiesFile = file;
        }
        load();
    }

    public boolean isUpdated() {
        final long now = System.currentTimeMillis();
        if (now - lastChecked < checkInterval) {
            lastChecked = now;
            return false;
        }
        lastChecked = now;

        final long timestamp = propertiesFile.lastModified();
        if (timestamp <= lastModified) {
            return false;
        }

        return true;
    }

    public synchronized void load() {
        final Properties prop = new Properties();
        try (final FileInputStream fis = new FileInputStream(propertiesFile)) {
            lastModified = propertiesFile.lastModified();
            prop.load(fis);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
        properties = prop;
    }

    public synchronized void store() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(propertiesFile);
            properties.store(fos, propertiesFile.getName());
        } catch (final IOException e) {
            throw new FileAccessException("ECL0112", new Object[] { propertiesFile.getAbsolutePath() }, e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    // ignore
                }
            }
        }
        lastModified = propertiesFile.lastModified();
    }

    protected Properties getProperties() {
        if (isUpdated()) {
            load();
        }
        return properties;
    }

    @Override
    public void clear() {
        getProperties().clear();
    }

    @Override
    public Object clone() {
        final DynamicProperties dynamicProperties = new DynamicProperties(propertiesFile.getAbsolutePath());
        dynamicProperties.checkInterval = checkInterval;
        return dynamicProperties;
    }

    @Override
    public boolean contains(final Object value) {
        return getProperties().contains(value);
    }

    @Override
    public boolean containsKey(final Object key) {
        return getProperties().containsKey(key);
    }

    @Override
    public boolean containsValue(final Object value) {
        return getProperties().containsValue(value);
    }

    @Override
    public Enumeration<Object> elements() {
        return getProperties().elements();
    }

    @Override
    public Set<java.util.Map.Entry<Object, Object>> entrySet() {
        return getProperties().entrySet();
    }

    @Override
    public boolean equals(final Object o) {
        return getProperties().equals(o);
    }

    @Override
    public Object get(final Object key) {
        return getProperties().get(key);
    }

    @Override
    public String getProperty(final String key, final String defaultValue) {
        return getProperties().getProperty(key, defaultValue);
    }

    @Override
    public String getProperty(final String key) {
        return getProperties().getProperty(key);
    }

    @Override
    public int hashCode() {
        return getProperties().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return getProperties().isEmpty();
    }

    @Override
    public Enumeration<Object> keys() {
        return getProperties().keys();
    }

    @Override
    public Set<Object> keySet() {
        return getProperties().keySet();
    }

    @Override
    public void list(final PrintStream out) {
        getProperties().list(out);
    }

    @Override
    public void list(final PrintWriter out) {
        getProperties().list(out);
    }

    @Override
    public synchronized void load(final InputStream inStream) throws IOException {
        final Properties prop = new Properties();
        lastModified = propertiesFile.lastModified();
        prop.load(inStream);
        properties = prop;
    }

    @Override
    public synchronized void load(final Reader reader) throws IOException {
        final Properties prop = new Properties();
        lastModified = propertiesFile.lastModified();
        prop.load(reader);
        properties = prop;
    }

    @Override
    public synchronized void loadFromXML(final InputStream in) throws IOException, InvalidPropertiesFormatException {
        final Properties prop = new Properties();
        lastModified = propertiesFile.lastModified();
        prop.loadFromXML(in);
        properties = prop;
    }

    @Override
    public Enumeration<?> propertyNames() {
        return getProperties().propertyNames();
    }

    @Override
    public Object put(final Object key, final Object value) {
        return getProperties().put(key, value);
    }

    @Override
    public void putAll(final Map<? extends Object, ? extends Object> t) {
        getProperties().putAll(t);
    }

    @Override
    public Object remove(final Object key) {
        return getProperties().remove(key);
    }

    @Override
    public void save(final OutputStream out, final String comments) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    public Object setProperty(final String key, final String value) {
        return getProperties().setProperty(key, value);
    }

    @Override
    public int size() {
        return getProperties().size();
    }

    @Override
    public void store(final OutputStream out, final String comments) throws IOException {
        getProperties().store(out, comments);
    }

    @Override
    public void store(final Writer writer, final String comments) throws IOException {
        getProperties().store(writer, comments);
    }

    @Override
    public void storeToXML(final OutputStream os, final String comment, final String encoding) throws IOException {
        getProperties().storeToXML(os, comment, encoding);
    }

    @Override
    public void storeToXML(final OutputStream os, final String comment) throws IOException {
        getProperties().storeToXML(os, comment);
    }

    @Override
    public Set<String> stringPropertyNames() {
        return getProperties().stringPropertyNames();
    }

    @Override
    public String toString() {
        return getProperties().toString();
    }

    @Override
    public Collection<Object> values() {
        return getProperties().values();
    }

    public void setCheckInterval(final long checkInterval) {
        this.checkInterval = checkInterval;
    }
}
