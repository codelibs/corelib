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
 * ログ出力を提供するクラスです。
 *
 * @author higa
 */
public class Logger {

    /**
     * ログの出力レベルです。
     */
    public enum LogLevel {
        /** デバッグ */
        DEBUG,
        /** 情報 */
        INFO,
        /** 警告 */
        WARN,
        /** エラー */
        ERROR,
        /** 致命的 */
        FATAL,
    }

    /** ロガーアダプタのファクトリ */
    protected static final LoggerAdapterFactory factory = getLoggerAdapterFactory();

    /** クラスをキーとするロガー のマップ */
    protected static final Map<Class<?>, Logger> loggers = newHashMap();

    /** 初期化済みを示すフラグ */
    private static boolean initialized;

    /** ロガーアダプタ */
    private final LoggerAdapter log;

    /**
     * {@link Logger}を返します。
     *
     * @param clazz
     *            ロガーのカテゴリとなるクラス。{@literal null}であってはいけません
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
     * フォーマットされたメッセージ文字列を返します。
     *
     * @param messageCode
     *            メッセージコード。{@literal null}や空文字列であってはいけません
     * @param args
     *            引数
     * @return フォーマットされたメッセージ文字列
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
     * {@link Logger}を初期化します。
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
     * ログアダプタのファクトリを返します。
     * <p>
     * Commons Loggingが使える場合はCommons Loggingを利用するためのファクトリを返します。
     * 使えない場合はjava.util.loggingロガーを利用するためのファクトリを返します。
     * </p>
     *
     * @return ログアダプタのファクトリ
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
     * インスタンスを構築します。
     *
     * @param clazz
     *            ログ出力のカテゴリとなるクラス
     */
    protected Logger(final Class<?> clazz) {
        log = factory.getLoggerAdapter(clazz);
    }

    /**
     * DEBUG情報が出力されるかどうかを返します。
     *
     * @return DEBUG情報が出力されるかどうか
     */
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /**
     * DEBUG情報を出力します。
     *
     * @param message
     *            メッセージ
     * @param throwable
     *            例外
     */
    public void debug(final Object message, final Throwable throwable) {
        if (isDebugEnabled()) {
            log.debug(toString(message), throwable);
        }
    }

    /**
     * DEBUG情報を出力します。
     *
     * @param message
     *            メッセージ
     */
    public void debug(final Object message) {
        if (isDebugEnabled()) {
            log.debug(toString(message));
        }
    }

    /**
     * INFO情報が出力されるかどうかを返します。
     *
     * @return INFO情報が出力されるかどうか
     */
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    /**
     * INFO情報を出力します。
     *
     * @param message
     *            メッセージ
     * @param throwable
     *            例外
     */
    public void info(final Object message, final Throwable throwable) {
        if (isInfoEnabled()) {
            log.info(toString(message), throwable);
        }
    }

    /**
     * INFO情報を出力します。
     *
     * @param message
     *            メッセージ
     */
    public void info(final Object message) {
        if (isInfoEnabled()) {
            log.info(toString(message));
        }
    }

    /**
     * WARN情報を出力します。
     *
     * @param message
     *            メッセージ
     * @param throwable
     *            例外
     */
    public void warn(final Object message, final Throwable throwable) {
        log.warn(toString(message), throwable);
    }

    /**
     * WARN情報を出力します。
     *
     * @param message
     *            メッセージ
     */
    public void warn(final Object message) {
        log.warn(message.toString());
    }

    /**
     * ERROR情報を出力します。
     *
     * @param message
     *            メッセージ
     * @param throwable
     *            例外
     */
    public void error(final Object message, final Throwable throwable) {
        log.error(message.toString(), throwable);
    }

    /**
     * ERROR情報を出力します。
     *
     * @param message
     *            メッセージ
     */
    public void error(final Object message) {
        log.error(message.toString());
    }

    /**
     * FATAL情報を出力します。
     *
     * @param message
     *            メッセージ
     * @param throwable
     *            例外
     */
    public void fatal(final Object message, final Throwable throwable) {
        log.fatal(message.toString(), throwable);
    }

    /**
     * FATAL情報を出力します。
     *
     * @param message
     *            メッセージ
     */
    public void fatal(final Object message) {
        log.fatal(message.toString());
    }

    /**
     * ログを出力します。
     *
     * @param throwable
     *            例外。{@literal null}であってはいけません
     */
    public void log(final Throwable throwable) {
        assertArgumentNotNull("throwable", throwable);

        error(throwable.getMessage(), throwable);
    }

    /**
     * ログを出力します。
     *
     * @param messageCode
     *            メッセージコード。{@literal null}や空文字列であってはいけません
     * @param args
     *            引数
     */
    public void log(final String messageCode, final Object... args) {
        assertArgumentNotEmpty("messageCode", messageCode);

        log(format(messageCode, args));
    }

    /**
     * ログを出力します。
     * <p>
     * ログメッセージは{@link #format(String, Object...)}メソッドで作成します。
     * {@link #format(String, Object...)}を{@literal static import}しておくと便利です。
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
     *            ログメッセージ。{@literal null}であってはいけません
     */
    public void log(final LogMessage logMessage) {
        assertArgumentNotNull("logMessage", logMessage);

        log(logMessage, null);
    }

    /**
     * ログを出力します。
     * <p>
     * ログメッセージは{@link #format(String, Object...)}メソッドで作成します。
     * {@link #format(String, Object...)}を{@literal static import}しておくと便利です。
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
     *            ログメッセージ。{@literal null}であってはいけません
     * @param throwable
     *            例外
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
     * 指定のログレベルが有効なら{@literal true}を返します．
     *
     * @param logLevel
     *            ログレベル
     * @return 指定のログレベルが有効なら{@literal true}
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
     * メッセージオブジェクトの文字列表現を返します。
     *
     * @param message
     *            メッセージオブジェクト
     * @return メッセージオブジェクトの文字列表現
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
     * ログ出力するメッセージです。
     *
     * @author koichik
     */
    public static class LogMessage {
        /** ログレベル */
        protected final LogLevel level;

        /** ログメッセージ */
        protected final String message;

        /**
         * インスタンスを構築します。
         *
         * @param level
         *            ログレベル。{@literal null}であってはいけません
         * @param message
         *            ログメッセージ
         */
        public LogMessage(final LogLevel level, final String message) {
            assertArgumentNotNull("level", level);

            this.level = level;
            this.message = message;
        }

        /**
         * 出力レベルを返します。
         *
         * @return 出力レベル
         */
        public LogLevel getLevel() {
            return level;
        }

        /**
         * メッセージを返します。
         *
         * @return メッセージ
         */
        public String getMessage() {
            return message;
        }

    }

}
