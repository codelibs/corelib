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
package org.codelibs.core.message;

import static org.codelibs.core.lang.StringUtil.isEmpty;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.codelibs.core.io.ResourceBundleUtil;
import org.codelibs.core.misc.DisposableUtil;
import org.codelibs.core.misc.LocaleUtil;

/**
 * Class for assembling messages from message codes and arguments.
 *
 * @author higa
 */
public abstract class MessageFormatter {

    /**
     * Do not instantiate.
     */
    protected MessageFormatter() {
    }

    /** Length of the numeric part of the message code */
    protected static final int CODE_NUMBER_LENGTH = 4;

    /** Suffix of the resource bundle name corresponding to the message code */
    protected static final String MESSAGES = "Messages";

    /** Flag indicating initialization */
    protected static volatile boolean initialized;

    /**
     * Returns the message.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Arguments
     * @return Message
     */
    public static String getMessage(final String messageCode, final Object... args) {
        return getFormattedMessage(messageCode == null ? "" : messageCode, getSimpleMessage(messageCode, args));
    }

    /**
     * Returns the message with the message code.
     *
     * @param messageCode
     *            Message code
     * @param simpleMessage
     *            Simple message with arguments expanded
     * @return Message with message code
     */
    public static String getFormattedMessage(final String messageCode, final String simpleMessage) {
        return "[" + messageCode + "]" + simpleMessage;
    }

    /**
     * Expands arguments and returns a simple message without a message code.
     *
     * @param messageCode
     *            Message code
     * @param args
     *            Arguments
     * @return Simple message without a message code
     */
    public static String getSimpleMessage(final String messageCode, final Object... args) {
        try {
            final String pattern = getPattern(messageCode);
            if (pattern != null) {
                return MessageFormat.format(pattern, args);
            }
            return getNoPatternMessage(args);
        } catch (final Throwable ignore) {
            return getNoPatternMessage(args);
        }
    }

    /**
     * Returns the pattern string corresponding to the message code.
     *
     * @param messageCode
     *            Message code
     * @return Pattern string
     */
    protected static String getPattern(final String messageCode) {
        if (isEmpty(messageCode)) {
            return null;
        }
        final ResourceBundle resourceBundle = getResourceBundle(getSystemName(messageCode));
        if (resourceBundle == null) {
            return null;
        }

        final int length = messageCode.length();
        if (length > CODE_NUMBER_LENGTH) {
            final String key = messageCode.charAt(0) + messageCode.substring(length - CODE_NUMBER_LENGTH);
            final String pattern = ResourceBundleUtil.getString(resourceBundle, key);
            if (pattern != null) {
                return pattern;
            }
        }
        return resourceBundle.getString(messageCode);
    }

    /**
     * Returns the system name.
     *
     * @param messageCode
     *            Message code
     * @return System name
     */
    protected static String getSystemName(final String messageCode) {
        return messageCode.substring(1, Math.max(1, messageCode.length() - CODE_NUMBER_LENGTH));
    }

    /**
     * Returns the resource bundle.
     *
     * @param systemName
     *            System name
     * @return Resource bundle
     */
    protected static ResourceBundle getResourceBundle(final String systemName) {
        if (!initialized) {
            initialize();
        }
        return ResourceBundleUtil.getBundle(systemName + MESSAGES, LocaleUtil.getDefault());
    }

    /**
     * Returns a message with arguments lined up without using a pattern.
     *
     * @param args
     *            Arguments
     * @return Message with arguments lined up
     */
    protected static String getNoPatternMessage(final Object... args) {
        if (args == null || args.length == 0) {
            return "";
        }
        final StringBuilder buffer = new StringBuilder();
        for (final Object arg : args) {
            buffer.append(arg + ", ");
        }
        buffer.setLength(buffer.length() - ", ".length());
        return new String(buffer);
    }

    /**
     * Initializes the class.
     */
    protected static synchronized void initialize() {
        if (!initialized) {
            DisposableUtil.add(() -> {
                ResourceBundle.clearCache();
                initialized = false;
            });
            initialized = true;
        }
    }

}
