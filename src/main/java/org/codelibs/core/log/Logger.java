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
package org.codelibs.core.log;

import static org.codelibs.core.collection.ArrayUtil.asArray;
import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.util.Map;

import org.codelibs.core.exception.ClIllegalArgumentException;
import org.codelibs.core.message.MessageFormatter;
import org.codelibs.core.misc.DisposableUtil;

/**
 * Logger interface.
 *
 * @author higa
 */
public class Logger {

    /**
     * Log output levels.
     */
    public enum LogLevel {
        /** Debug */
        DEBUG,
        /** Info */
        INFO,
        /** Warning */
        WARN,
        /** Error */
        ERROR,
        /** Fatal */
        FATAL,
    }

    /** Factory for LoggerAdapter */
    protected static final LoggerAdapterFactory factory = getLoggerAdapterFactory();

    /** Map of loggers keyed by class */
    protected static final Map<Class<?>, Logger> loggers = newHashMap();

    /** Flag indicating initialization is complete. */
    private static boolean initialized;

    /** Logger adapter. */
    private final LoggerAdapter log;

    /**
     * Returns a {@link Logger}.
     *
     * @param clazz
     *            Class to be used as the logger category. Must not be {@literal null}.
     * @return {@link Logger}
     */
    public static synchronized Logger getLogger(final Class<?> clazz) {
        assertArgumentNotNull("clazz", clazz);

        if (!initialized) {
            initialize();
        }
        Logger logger = loggers.get(clazz);
        if (logger == null) {
            logger = new Logger(clazz);
            loggers.put(clazz, logger);
        }
        return logger;
    }

    /**
     * Returns a formatted message string.
     *
     * @param messageCode
     *            Message code. Must not be {@literal null} or empty string.
     * @param args
     *            Arguments
     * @return Formatted message string
     */
    public static LogMessage format(final String messageCode, final Object... args) {
        assertArgumentNotEmpty("messageCode", messageCode);

        final char messageType = messageCode.charAt(0);
        final String message = MessageFormatter.getSimpleMessage(messageCode, args);
        switch (messageType) {
        case 'D':
            return new LogMessage(LogLevel.DEBUG, message);
        case 'I':
            return new LogMessage(LogLevel.INFO, message);
        case 'W':
            return new LogMessage(LogLevel.WARN, message);
        case 'E':
            return new LogMessage(LogLevel.ERROR, message);
        case 'F':
            return new LogMessage(LogLevel.FATAL, message);
        default:
            throw new ClIllegalArgumentException("messageCode", "ECL0009", asArray(messageCode, "messageCode : " + messageCode));
        }
    }

    /**
     * Initializes the {@link Logger}.
     */
    protected static synchronized void initialize() {
        DisposableUtil.addFirst(() -> {
            initialized = false;
            loggers.clear();
            factory.releaseAll();
        });
        initialized = true;
    }

    /**
     * Returns the logger adapter factory.
     * <p>
     * If Commons Logging is available, returns a factory for using Commons Logging.
     * If not available, returns a factory for using java.util.logging logger.
     * </p>
     *
     * @return the logger adapter factory
     */
    protected static LoggerAdapterFactory getLoggerAdapterFactory() {
        // TODO
        try {
            Class.forName("org.apache.commons.logging.LogFactory");
            return new JclLoggerAdapterFactory();
        } catch (final Throwable ignore) {
            return new JulLoggerAdapterFactory();
        }
    }

    /**
     * Constructs an instance.
     *
     * @param clazz
     *            The class to be used as the logging category.
     */
    protected Logger(final Class<?> clazz) {
        log = factory.getLoggerAdapter(clazz);
    }

    /**
     * Checks if debug level is enabled.
     *
     * @return true if debug is enabled, false otherwise
     */
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * Outputs DEBUG information.
     *
     * @param message
     *            Message
     * @param throwable
     *            Exception
     */
    public void debug(final Object message, final Throwable throwable) {
        if (isDebugEnabled()) {
            log.debug(toString(message), throwable);
        }
    }

    /**
     * Outputs DEBUG information.
     *
     * @param message
     *            Message
     */
    public void debug(final Object message) {
        if (isDebugEnabled()) {
            log.debug(toString(message));
        }
    }

    /**
     * Checks if info level is enabled.
     *
     * @return true if info is enabled, false otherwise
     */
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    /**
     * Outputs INFO information.
     *
     * @param message
     *            Message
     * @param throwable
     *            Exception
     */
    public void info(final Object message, final Throwable throwable) {
        if (isInfoEnabled()) {
            log.info(toString(message), throwable);
        }
    }

    /**
     * Outputs INFO information.
     *
     * @param message
     *            Message
     */
    public void info(final Object message) {
        if (isInfoEnabled()) {
            log.info(toString(message));
        }
    }

    /**
     * Outputs WARN information.
     *
     * @param message
     *            Message
     * @param throwable
     *            Exception
     */
    public void warn(final Object message, final Throwable throwable) {
        log.warn(toString(message), throwable);
    }

    /**
     * Outputs WARN information.
     *
     * @param message
     *            Message
     */
    public void warn(final Object message) {
        log.warn(message.toString());
    }

    /**
     * Outputs ERROR information.
     *
     * @param message
     *            Message
     * @param throwable
     *            Exception
     */
    public void error(final Object message, final Throwable throwable) {
        log.error(message.toString(), throwable);
    }

    /**
     * Outputs ERROR information.
     *
     * @param message
     *            Message
     */
    public void error(final Object message) {
        log.error(message.toString());
    }

    /**
     * Outputs FATAL information.
     *
     * @param message
     *            Message
     * @param throwable
     *            Exception
     */
    public void fatal(final Object message, final Throwable throwable) {
        log.fatal(message.toString(), throwable);
    }

    /**
     * Outputs FATAL information.
     *
     * @param message
     *            Message
     */
    public void fatal(final Object message) {
        log.fatal(message.toString());
    }

    /**
     * Outputs a log entry.
     *
     * @param throwable
     *            Exception. Must not be {@literal null}.
     */
    public void log(final Throwable throwable) {
        assertArgumentNotNull("throwable", throwable);

        error(throwable.getMessage(), throwable);
    }

    /**
     * Outputs a log entry.
     *
     * @param messageCode
     *            Message code. Must not be {@literal null} or empty string.
     * @param args
     *            Arguments
     */
    public void log(final String messageCode, final Object... args) {
        assertArgumentNotEmpty("messageCode", messageCode);

        log(format(messageCode, args));
    }

    /**
     * Outputs a log entry.
     * <p>
     * The log message should be created using the {@link #format(String, Object...)} method.
     * It is convenient to use a static import for {@link #format(String, Object...)}.
     * </p>
     *
     * <pre>
     * import static org.codelibs.core.log.Logger.format;
     *
     * Logger logger = Logger.getLogger(Xxx.class);
     * logger.log(format("DXXX0000", arg1, arg2, arg3));
     * </pre>
     *
     * @param logMessage
     *            Log message. Must not be {@literal null}.
     */
    public void log(final LogMessage logMessage) {
        assertArgumentNotNull("logMessage", logMessage);

        log(logMessage, null);
    }

    /**
     * Outputs a log entry.
     * <p>
     * The log message should be created using the {@link #format(String, Object...)} method.
     * It is convenient to use a static import for {@link #format(String, Object...)}.
     * </p>
     *
     * <pre>
     * import static org.codelibs.core.log.Logger.format;
     *
     * Logger logger = Logger.getLogger(Xxx.class);
     * logger.log(format("DXXX0000", arg1, arg2, arg3), t);
     * </pre>
     *
     * @param logMessage
     *            Log message. Must not be {@literal null}.
     * @param throwable
     *            Exception
     */
    public void log(final LogMessage logMessage, final Throwable throwable) {
        assertArgumentNotNull("logMessage", logMessage);

        final LogLevel level = logMessage.getLevel();
        if (isEnabledFor(level)) {
            final String message = logMessage.getMessage();
            switch (level) {
            case DEBUG:
                log.debug(message, throwable);
                break;
            case INFO:
                log.info(message, throwable);
                break;
            case WARN:
                log.warn(message, throwable);
                break;
            case ERROR:
                log.error(message, throwable);
                break;
            case FATAL:
                log.fatal(message, throwable);
                break;
            }
        }
    }

    /**
     * Returns {@literal true} if the specified log level is enabled.
     *
     * @param logLevel
     *            Log level
     * @return {@literal true} if the specified log level is enabled
     */
    protected boolean isEnabledFor(final LogLevel logLevel) {
        switch (logLevel) {
        case DEBUG:
            return log.isDebugEnabled();
        case INFO:
            return log.isInfoEnabled();
        case WARN:
            return log.isWarnEnabled();
        case ERROR:
            return log.isErrorEnabled();
        case FATAL:
            return log.isFatalEnabled();
        default:
            throw new ClIllegalArgumentException("logLevel", "ECL0009", asArray(logLevel, logLevel));
        }
    }

    /**
     * Returns the string representation of the message object.
     *
     * @param message
     *            Message object
     * @return String representation of the message object
     */
    protected static String toString(final Object message) {
        if (message == null) {
            return "null";
        }
        if (message instanceof String) {
            return (String) message;
        }
        return message.toString();
    }

    /**
     * The message to be logged.
     *
     * @author koichik
     */
    public static class LogMessage {
        /** Log level */
        protected final LogLevel level;

        /** Log message */
        protected final String message;

        /**
         * Constructs an instance.
         *
         * @param level
         *            Log level. Must not be {@literal null}.
         * @param message
         *            Log message.
         */
        public LogMessage(final LogLevel level, final String message) {
            assertArgumentNotNull("level", level);

            this.level = level;
            this.message = message;
        }

        /**
         * Returns the log level.
         *
         * @return the log level
         */
        public LogLevel getLevel() {
            return level;
        }

        /**
         * Returns the message.
         *
         * @return the message
         */
        public String getMessage() {
            return message;
        }

    }

}
