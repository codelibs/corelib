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
package org.codelibs.core.lang;

import static org.codelibs.core.collection.CollectionsUtil.newArrayList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Utility class for string operations.
 *
 * @author higa
 * @author shinsuke
 */
public abstract class StringUtil {

    /**
     * Do not instantiate.
     */
    protected StringUtil() {
    }

    /**
     * A system line separator.
     */
    public static final String RETURN_STRING = System.getProperty("line.separator");

    /**
     * An empty string <code>""</code>.
     */
    public static final String EMPTY = "";

    /**
     * An empty array of strings.
     */
    public static final String[] EMPTY_STRINGS = new String[0];

    static Object javaLangAccess = null;

    static Method newStringUnsafeMethod = null;

    static {
        try {
            final Class<?> sharedSecretsClass = Class.forName("sun.misc.SharedSecrets");
            javaLangAccess = sharedSecretsClass.getDeclaredMethod("getJavaLangAccess").invoke(null);
            final Class<?> javaLangAccessClass = Class.forName("sun.misc.JavaLangAccess");
            newStringUnsafeMethod = javaLangAccessClass.getMethod("newStringUnsafe", char[].class);
        } catch (final Throwable t) {
            // ignore
            // t.printStackTrace();
        }
    }

    /**
     * Checks if the string is empty or null.
     *
     * @param text the string to check
     * @return true if empty or null, false otherwise
     */
    public static final boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

    /**
     * Checks if the string is not empty.
     *
     * @param text the string to check
     * @return true if not empty, false otherwise
     */
    public static final boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }

    /**
     * Replaces all occurrences of a substring within a string with another string.
     *
     * @param text
     *            The original string.
     * @param fromText
     *            The substring to be replaced.
     * @param toText
     *            The replacement substring.
     * @return The resulting string after replacements.
     */
    public static final String replace(final String text, final String fromText, final String toText) {
        if (text == null || fromText == null || toText == null) {
            return null;
        }
        final StringBuilder buf = new StringBuilder(100);
        int pos = 0;
        int pos2 = 0;
        while (true) {
            pos = text.indexOf(fromText, pos2);
            if (pos == 0) {
                buf.append(toText);
                pos2 = fromText.length();
            } else if (pos > 0) {
                buf.append(text.substring(pos2, pos));
                buf.append(toText);
                pos2 = pos + fromText.length();
            } else {
                buf.append(text.substring(pos2));
                break;
            }
        }
        return new String(buf);
    }

    /**
     * Splits the string by the specified delimiter.
     *
     * @param str
     *            the string to split
     * @param delim
     *            the delimiter to use for splitting
     * @return an array of split strings
     */
    public static String[] split(final String str, final String delim) {
        if (isEmpty(str)) {
            return EMPTY_STRINGS;
        }
        final List<String> list = newArrayList();
        final StringTokenizer st = new StringTokenizer(str, delim);
        while (st.hasMoreElements()) {
            list.add(st.nextElement().toString());
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * Removes whitespace from the left side of the string.
     *
     * @param text
     *            The text to trim
     * @return The resulting string
     */
    public static final String ltrim(final String text) {
        return ltrim(text, null);
    }

    /**
     * Removes the specified characters from the left side of the string.
     *
     * @param text
     *            The text to trim
     * @param trimText
     *            The characters to remove
     * @return The resulting string
     */
    public static final String ltrim(final String text, String trimText) {
        if (text == null) {
            return null;
        }
        if (trimText == null) {
            trimText = " ";
        }
        int pos = 0;
        for (; pos < text.length(); pos++) {
            if (trimText.indexOf(text.charAt(pos)) < 0) {
                break;
            }
        }
        return text.substring(pos);
    }

    /**
     * Removes whitespace from the right side of the string.
     *
     * @param text
     *            The text to trim
     * @return The resulting string
     */
    public static final String rtrim(final String text) {
        return rtrim(text, null);
    }

    /**
     * Removes the specified characters from the right side of the string.
     *
     * @param text
     *            The text to trim
     * @param trimText
     *            The characters to remove
     * @return The resulting string
     */
    public static final String rtrim(final String text, String trimText) {
        if (text == null) {
            return null;
        }
        if (trimText == null) {
            trimText = " ";
        }
        int pos = text.length() - 1;
        for (; pos >= 0; pos--) {
            if (trimText.indexOf(text.charAt(pos)) < 0) {
                break;
            }
        }
        return text.substring(0, pos + 1);
    }

    /**
     * Removes the suffix from the string if it is present.
     *
     * @param text
     *            The text
     * @param suffix
     *            The suffix to remove
     * @return The resulting string
     */
    public static final String trimSuffix(final String text, final String suffix) {
        if (text == null) {
            return null;
        }
        if (suffix == null) {
            return text;
        }
        if (text.endsWith(suffix)) {
            return text.substring(0, text.length() - suffix.length());
        }
        return text;
    }

    /**
     * Removes the prefix from the string if it is present.
     *
     * @param text
     *            The text
     * @param prefix
     *            The prefix to remove
     * @return The resulting string
     */
    public static final String trimPrefix(final String text, final String prefix) {
        if (text == null) {
            return null;
        }
        if (prefix == null) {
            return text;
        }
        if (text.startsWith(prefix)) {
            return text.substring(prefix.length());
        }
        return text;
    }

    /**
     * Decapitalizes a string according to JavaBeans conventions.
     * Note: If the first two characters are uppercase, the string will not be decapitalized.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.decapitalize("UserId")  = "userId"
     * StringUtil.decapitalize("ABC")     = "ABC"
     * </pre>
     *
     * @param name
     *            the string to decapitalize
     * @return the decapitalized string
     */
    public static String decapitalize(final String name) {
        if (isEmpty(name)) {
            return name;
        }
        final char[] chars = name.toCharArray();
        if (chars.length >= 2 && Character.isUpperCase(chars[0]) && Character.isUpperCase(chars[1])) {
            return name;
        }
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * Capitalizes a string according to JavaBeans conventions.
     * Note: If the first two characters are uppercase, the string will not be capitalized.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.capitalize("userId")  = "UserId"
     * StringUtil.capitalize("ABC")     = "ABC"
     * </pre>
     *
     * @param name
     *            the string to capitalize
     * @return the capitalized string
     */
    public static String capitalize(final String name) {
        if (isEmpty(name)) {
            return name;
        }
        final char[] chars = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * Returns true if the string is blank.
     *
     * @param str
     *            the string to check
     * @return {@literal true} if the string is blank
     */
    public static boolean isBlank(final String str) {
        if (str == null || str.length() == 0) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the string is not blank.
     *
     * @param str
     *            the string to check
     * @return {@literal true} if the string is not blank
     * @see #isBlank(String)
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * Returns true if the string contains the specified character.
     *
     * @param str
     *            the string to check
     * @param ch
     *            the character to find
     * @return true if the character is contained in the string, false otherwise
     */
    public static boolean contains(final String str, final char ch) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(ch) >= 0;
    }

    /**
     * Returns true if the string contains the specified substring.
     *
     * @param s1
     *            the string to check
     * @param s2
     *            the substring to find
     * @return true if the string contains the substring, false otherwise
     */
    public static boolean contains(final String s1, final String s2) {
        if (isEmpty(s1)) {
            return false;
        }
        return s1.indexOf(s2) >= 0;
    }

    /**
     * Returns whether two strings are equal. If both are null, returns <code>true</code>.
     *
     * @param target1
     *            the first string
     * @param target2
     *            the second string
     * @return true if the strings are equal, false otherwise
     */
    public static boolean equals(final String target1, final String target2) {
        return target1 == null ? target2 == null : target1.equals(target2);
    }

    /**
     * Returns whether two strings are equal ignoring case. If both are null, returns <code>true</code>.
     *
     * @param target1
     *            the first string
     * @param target2
     *            the second string
     * @return {@literal true} if the strings are equal ignoring case
     */
    public static boolean equalsIgnoreCase(final String target1, final String target2) {
        return target1 == null ? target2 == null : target1.equalsIgnoreCase(target2);
    }

    /**
     * Returns true if the string ends with the specified substring, ignoring case.
     *
     * @param target1
     *            the text to check
     * @param target2
     *            the substring to compare
     * @return {@literal true} if the string ends with the specified substring, ignoring case
     */
    public static boolean endsWithIgnoreCase(final String target1, final String target2) {
        if (target1 == null || target2 == null) {
            return false;
        }
        final int length1 = target1.length();
        final int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        final String s1 = target1.substring(length1 - length2);
        return s1.equalsIgnoreCase(target2);
    }

    /**
     * Returns true if the string starts with the specified substring, ignoring case.
     *
     * @param target1
     *            the text to check
     * @param target2
     *            the substring to compare
     * @return {@literal true} if the string starts with the specified substring, ignoring case
     */
    public static boolean startsWithIgnoreCase(final String target1, final String target2) {
        if (target1 == null || target2 == null) {
            return false;
        }
        final int length1 = target1.length();
        final int length2 = target2.length();
        if (length1 < length2) {
            return false;
        }
        final String s1 = target1.substring(0, target2.length());
        return s1.equalsIgnoreCase(target2);
    }

    /**
     * Returns the part of the string before the last occurrence of the specified separator.
     *
     * @param str
     *            the string
     * @param separator
     *            the separator
     * @return the resulting string
     */
    public static String substringFromLast(final String str, final String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * Returns the part of the string after the last occurrence of the specified separator.
     *
     * @param str
     *            the string
     * @param separator
     *            the separator
     * @return the resulting string
     */
    public static String substringToLast(final String str, final String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(pos + 1, str.length());
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes
     *            the byte array
     * @return the hexadecimal string
     */
    public static String toHex(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (final byte b : bytes) {
            appendHex(sb, b);
        }
        return new String(sb);
    }

    /**
     * Converts an {@literal int} value to a hexadecimal string.
     *
     * @param i
     *            the {@literal int} value
     * @return the hexadecimal string
     */
    public static String toHex(final int i) {
        final StringBuilder buf = new StringBuilder();
        appendHex(buf, i);
        return new String(buf);
    }

    /**
     * Appends the hexadecimal string representation of a number to the given StringBuilder.
     *
     * @param buf
     *            the StringBuilder to append to
     * @param i
     *            the number to convert
     */
    public static void appendHex(final StringBuilder buf, final byte i) {
        buf.append(Character.forDigit((i & 0xf0) >> 4, 16));
        buf.append(Character.forDigit(i & 0x0f, 16));
    }

    /**
     * Appends the hexadecimal string representation of a number to the given StringBuilder.
     *
     * @param buf
     *            the StringBuilder to append to
     * @param i
     *            the number to convert
     */
    public static void appendHex(final StringBuilder buf, final int i) {
        buf.append(Integer.toHexString(i >> 24 & 0xff));
        buf.append(Integer.toHexString(i >> 16 & 0xff));
        buf.append(Integer.toHexString(i >> 8 & 0xff));
        buf.append(Integer.toHexString(i & 0xff));
    }

    /**
     * Converts an underscore-separated string to camel case.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.camelize("USER_ID")  = "UserId"
     * </pre>
     *
     * @param s
     *            the text
     * @return the resulting string
     */
    public static String camelize(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        final String[] array = StringUtil.split(s, "_");
        if (array.length == 1) {
            return StringUtil.capitalize(s);
        }
        final StringBuilder buf = new StringBuilder(40);
        for (final String element : array) {
            buf.append(StringUtil.capitalize(element));
        }
        return buf.toString();
    }

    /**
     * Converts a camel case string to an underscore-separated string.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.decamelize("UserId")  = "USER_ID"
     * </pre>
     *
     * @param s
     *            the text
     * @return the resulting string
     */
    public static String decamelize(final String s) {
        if (s == null) {
            return null;
        }
        if (s.length() == 1) {
            return s.toUpperCase();
        }
        final StringBuilder buf = new StringBuilder(40);
        int pos = 0;
        for (int i = 1; i < s.length(); ++i) {
            if (Character.isUpperCase(s.charAt(i))) {
                if (buf.length() != 0) {
                    buf.append('_');
                }
                buf.append(s.substring(pos, i).toUpperCase());
                pos = i;
            }
        }
        if (buf.length() != 0) {
            buf.append('_');
        }
        buf.append(s.substring(pos, s.length()).toUpperCase());
        return buf.toString();
    }

    /**
     * Returns true if the string consists only of numeric characters.
     *
     * @param s
     *            the string to check
     * @return <code>true</code> if the string consists only of numeric characters
     */
    public static boolean isNumber(final String s) {
        if (isEmpty(s)) {
            return false;
        }

        final int size = s.length();
        for (int i = 0; i < size; i++) {
            final char chr = s.charAt(i);
            if (chr < '0' || '9' < chr) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the given string, or an empty string if the argument is <code>null</code>.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.defaultString(null)  = ""
     * StringUtil.defaultString("")    = ""
     * StringUtil.defaultString("aaa") = "aaa"
     * </pre>
     *
     * @param str
     *            the string (can be <code>null</code>)
     * @return the given string, or an empty string if the argument is <code>null</code>
     */
    public static String defaultString(final String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * Returns the given string, or the specified default string if the argument is <code>null</code>.
     * <p>
     * Usage example:
     * </p>
     *
     * <pre>
     * StringUtil.defaultString(null, "NULL")  = "NULL"
     * StringUtil.defaultString("", "NULL")    = ""
     * StringUtil.defaultString("aaa", "NULL") = "aaa"
     * StringUtil.defaultString("aaa", null)   = "aaa"
     * StringUtil.defaultString(null, null)    = null
     * </pre>
     *
     * @param str
     *            the string (can be <code>null</code>)
     * @param defaultStr
     *            the string to return if the argument is <code>null</code> (can be <code>null</code>)
     * @return the given string, or the specified default string if the argument is <code>null</code>
     */
    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * <p>Checks if the CharSequence contains only ASCII printable characters.</p>
     *
     * <p>{@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code true}.</p>
     *
     * <pre>
     * StringUtils.isAsciiPrintable(null)     = false
     * StringUtils.isAsciiPrintable("")       = true
     * StringUtils.isAsciiPrintable(" ")      = true
     * StringUtils.isAsciiPrintable("Ceki")   = true
     * StringUtils.isAsciiPrintable("ab2c")   = true
     * StringUtils.isAsciiPrintable("!ab-c~") = true
     * StringUtils.isAsciiPrintable("\u0020") = true
     * StringUtils.isAsciiPrintable("\u0021") = true
     * StringUtils.isAsciiPrintable("\u007e") = true
     * StringUtils.isAsciiPrintable("\u007f") = false
     * StringUtils.isAsciiPrintable("Ceki G\u00fclc\u00fc") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if every character is in the range 32 thru 126
     */
    public static boolean isAsciiPrintable(final CharSequence cs) {
        if (cs == null) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (isAsciiPrintable(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    private static boolean isAsciiPrintable(final char ch) {
        return ch >= 32 && ch < 127;
    }

    /**
     * Creates a new String from a char array without copying the array.
     * <p>
     * This method uses internal JDK APIs and may not be available in all Java versions.
     * If the internal API is not available, it falls back to the standard String constructor.
     * </p>
     *
     * @param chars
     *            the char array
     * @return a new String
     */
    public static String newStringUnsafe(final char[] chars) {
        if (chars == null) {
            return null;
        }
        if (newStringUnsafeMethod != null) {
            try {
                return (String) newStringUnsafeMethod.invoke(javaLangAccess, chars);
            } catch (final Throwable t) {
                // ignore
            }
        }
        return new String(chars);
    }
}
