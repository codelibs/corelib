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
package org.codelibs.core.xml;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.codelibs.core.exception.IORuntimeException;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Utility class for DOM operations.
 *
 * @author higa
 */
public abstract class DomUtil {

    /**
     * Retrieves the XML content as an {@link InputStream} using the platform's default encoding.
     *
     * @param contents
     *            The content. Must not be {@literal null}.
     * @return {@link InputStream}
     */
    public static InputStream getContentsAsStream(final String contents) {
        assertArgumentNotNull("contents", contents);
        return getContentsAsStream(contents, null);
    }

    /**
     * Retrieves the XML content as an {@link InputStream} with the specified encoding.
     *
     * @param contents
     *            The content. Must not be {@literal null}.
     * @param encoding
     *            The encoding. If {@literal null}, the platform's default encoding will be used.
     * @return {@link InputStream}
     */
    public static InputStream getContentsAsStream(final String contents, final String encoding) {
        assertArgumentNotNull("contents", contents);

        if (encoding == null) {
            return new ByteArrayInputStream(contents.getBytes());
        }
        try {
            return new ByteArrayInputStream(contents.getBytes(encoding));
        } catch (final UnsupportedEncodingException ex) {
            throw new IORuntimeException(ex);
        }
    }

    /**
     * Encodes the value of an attribute.
     *
     * @param s
     *            The attribute value.
     * @return The encoded attribute value.
     */
    public static String encodeAttrQuot(final String s) {
        if (s == null) {
            return null;
        }
        final char[] content = s.toCharArray();
        final StringBuilder buf = new StringBuilder(s.length() + 100);
        for (final char element : content) {
            switch (element) {
            case '<':
                buf.append("&lt;");
                break;
            case '>':
                buf.append("&gt;");
                break;
            case '&':
                buf.append("&amp;");
                break;
            case '"':
                buf.append("&quot;");
                break;
            default:
                buf.append(element);
            }
        }
        return buf.toString();
    }

    /**
     * Encodes the text.
     *
     * @param s
     *            The text.
     * @return The encoded text.
     */
    public static String encodeText(final String s) {
        if (s == null) {
            return null;
        }
        final char[] content = s.toCharArray();
        final StringBuilder buf = new StringBuilder(s.length() + 100);
        for (final char ch : content) {
            switch (ch) {
            case '<':
                buf.append("&lt;");
                break;
            case '>':
                buf.append("&gt;");
                break;
            case '&':
                buf.append("&amp;");
                break;
            default:
                buf.append(ch);
            }
        }
        return new String(buf);
    }

    /**
     * Converts a {@link Document} to a string.
     *
     * @param document
     *            The document. Must not be {@literal null}.
     * @return The converted string.
     */
    public static String toString(final Document document) {
        assertArgumentNotNull("document", document);

        final StringBuilder buf = new StringBuilder(1000);
        appendElement(document.getDocumentElement(), buf);
        return new String(buf);
    }

    /**
     * Converts an {@link Element} to a string.
     *
     * @param element
     *            The element. Must not be {@literal null}.
     * @return The converted string.
     */
    public static String toString(final Element element) {
        assertArgumentNotNull("element", element);

        final StringBuilder buf = new StringBuilder(1000);
        appendElement(element, buf);
        return new String(buf);
    }

    /**
     * Appends the string representation of an {@link Element}.
     *
     * @param element
     *            The element. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendElement(final Element element, final StringBuilder buf) {
        assertArgumentNotNull("element", element);
        assertArgumentNotNull("buf", buf);

        final String tag = element.getTagName();
        buf.append('<');
        buf.append(tag);
        appendAttrs(element.getAttributes(), buf);
        buf.append('>');
        appendChildren(element.getChildNodes(), buf);
        buf.append("</");
        buf.append(tag);
        buf.append('>');
    }

    /**
     * Appends the string representation of a {@link NodeList}.
     *
     * @param children
     *            The child nodes. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendChildren(final NodeList children, final StringBuilder buf) {
        assertArgumentNotNull("children", children);
        assertArgumentNotNull("buf", buf);

        final int length = children.getLength();
        for (int i = 0; i < length; ++i) {
            appendNode(children.item(i), buf);
        }
    }

    /**
     * Appends the string representation of a {@link NamedNodeMap}.
     *
     * @param attrs
     *            The attributes. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendAttrs(final NamedNodeMap attrs, final StringBuilder buf) {
        assertArgumentNotNull("attrs", attrs);
        assertArgumentNotNull("buf", buf);

        final int length = attrs.getLength();
        for (int i = 0; i < length; ++i) {
            final Attr attr = (Attr) attrs.item(i);
            buf.append(' ');
            appendAttr(attr, buf);
        }
    }

    /**
     * Appends the string representation of an {@link Attr}.
     *
     * @param attr
     *            The attribute. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendAttr(final Attr attr, final StringBuilder buf) {
        assertArgumentNotNull("attr", attr);
        assertArgumentNotNull("buf", buf);

        buf.append(attr.getName());
        buf.append("=\"");
        buf.append(encodeAttrQuot(attr.getValue()));
        buf.append('\"');
    }

    /**
     * Appends the string representation of a {@link Text}.
     *
     * @param text
     *            The text node. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendText(final Text text, final StringBuilder buf) {
        assertArgumentNotNull("text", text);
        assertArgumentNotNull("buf", buf);

        buf.append(encodeText(text.getData()));
    }

    /**
     * Appends the string representation of a {@link CDATASection}.
     *
     * @param cdataSection
     *            The CDATA section. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendCDATASection(final CDATASection cdataSection, final StringBuilder buf) {
        assertArgumentNotNull("cdataSection", cdataSection);
        assertArgumentNotNull("buf", buf);

        buf.append("<![CDATA[");
        buf.append(cdataSection.getData());
        buf.append("]]>");
    }

    /**
     * Appends the string representation of an {@link EntityReference}.
     *
     * @param entityReference
     *            The entity reference. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendEntityReference(final EntityReference entityReference, final StringBuilder buf) {
        assertArgumentNotNull("entityReference", entityReference);
        assertArgumentNotNull("buf", buf);

        buf.append('&');
        buf.append(entityReference.getNodeName());
        buf.append(';');
    }

    /**
     * Appends the string representation of a {@link Node}.
     *
     * @param node
     *            The node. Must not be {@literal null}.
     * @param buf
     *            The string buffer. Must not be {@literal null}.
     */
    public static void appendNode(final Node node, final StringBuilder buf) {
        assertArgumentNotNull("node", node);
        assertArgumentNotNull("buf", buf);

        switch (node.getNodeType()) {
        case Node.ELEMENT_NODE:
            appendElement((Element) node, buf);
            break;
        case Node.TEXT_NODE:
            appendText((Text) node, buf);
            break;
        case Node.CDATA_SECTION_NODE:
            appendCDATASection((CDATASection) node, buf);
            break;
        case Node.ENTITY_REFERENCE_NODE:
            appendEntityReference((EntityReference) node, buf);
            break;
        }
    }

}
