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
package org.codelibs.core.exception;

import static org.codelibs.core.collection.ArrayUtil.asArray;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * {@link SAXException}をラップする例外です。
 *
 * @author higa
 */
public class SAXRuntimeException extends ClRuntimeException {

    private static final long serialVersionUID = -4933312103385038765L;

    /**
     * {@link SAXRuntimeException}を作成します。
     *
     * @param cause
     *            原因となった例外
     */
    public SAXRuntimeException(final SAXException cause) {
        super("ECL0054", asArray(createMessage(cause)), cause);
    }

    /**
     * メッセージを作成します。
     *
     * @param cause
     *            原因
     * @return メッセージ
     */
    protected static String createMessage(final SAXException cause) {
        final StringBuilder buf = new StringBuilder(100);
        buf.append(cause);
        if (cause instanceof SAXParseException) {
            final SAXParseException e = (SAXParseException) cause;
            if (e.getSystemId() != null) {
                buf.append(" at ").append(e.getSystemId());
            }
            final int lineNumber = e.getLineNumber();
            final int columnNumber = e.getColumnNumber();
            buf.append("( lineNumber = ").append(lineNumber).append(", columnNumber = ").append(columnNumber).append(")");
        }
        return new String(buf);
    }

}
