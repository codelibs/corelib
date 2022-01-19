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
package org.codelibs.core.lang;

import static org.codelibs.core.collection.CollectionsUtil.newArrayList;

import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

/**
 * {@link String}用のユーティリティクラスです。
 *
 * @author higa
 * @author shinsuke
 */
public abstract class StringUtil {

    /**
     * A system line separator.
     */
    public static final String RETURN_STRING = System.getProperty("line.separator");

    /**
     * 空文字<code>""</code>です。
     */
    public static final String EMPTY = "";

    /**
     * 文字列型の空の配列です。
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
     * 文字列が<code>null</code>または空文字列なら<code>true</code>を返します。
     *
     * @param text
     *            文字列
     * @return 文字列が<code>null</code>または空文字列なら<code>true</code>
     */
    public static final boolean isEmpty(final String text) {
        return text == null || text.length() == 0;
    }

    /**
     * 文字列が<code>null</code>でも空文字列でもなければ<code>true</code>を返します。
     *
     * @param text
     *            文字列
     * @return 文字列が<code>null</code>でも空文字列でもなければ<code>true</code>
     */
    public static final boolean isNotEmpty(final String text) {
        return !isEmpty(text);
    }

    /**
     * 文字列を置き換えます。
     *
     * @param text
     *            テキスト
     * @param fromText
     *            置き換え対象のテキスト
     * @param toText
     *            置き換えるテキスト
     * @return 結果
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
     * 文字列を分割します。
     *
     * @param str
     *            文字列
     * @param delim
     *            分割するためのデリミタ
     * @return 分割された文字列の配列
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
     * 左側の空白を削ります。
     *
     * @param text
     *            テキスト
     * @return 結果の文字列
     */
    public static final String ltrim(final String text) {
        return ltrim(text, null);
    }

    /**
     * 左側の指定した文字列を削ります。
     *
     * @param text
     *            テキスト
     * @param trimText
     *            削るテキスト
     * @return 結果の文字列
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
     * 右側の空白を削ります。
     *
     * @param text
     *            テキスト
     * @return 結果の文字列
     */
    public static final String rtrim(final String text) {
        return rtrim(text, null);
    }

    /**
     * 右側の指定した文字列を削ります。
     *
     * @param text
     *            テキスト
     * @param trimText
     *            削る文字列
     * @return 結果の文字列
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
     * サフィックスを削ります。
     *
     * @param text
     *            テキスト
     * @param suffix
     *            サフィックス
     * @return 結果の文字列
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
     * プレフィックスを削ります。
     *
     * @param text
     *            テキスト
     * @param prefix
     *            プレフィックス
     * @return 結果の文字列
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
     * JavaBeansの仕様にしたがってデキャピタライズを行ないます。大文字が2つ以上続く場合は、小文字にならないので注意してください。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.capitalize("UserId")  = "userId"
     * StringUtil.capitalize("ABC")  = "ABC"
     * </pre>
     *
     * @param name
     *            名前
     * @return 結果の文字列
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
     * JavaBeansの仕様にしたがってキャピタライズを行ないます。大文字が2つ以上続く場合は、小文字にならないので注意してください。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.capitalize("userId")  = "UserId"
     * StringUtil.capitalize("ABC")  = "ABC"
     * </pre>
     *
     * @param name
     *            名前
     * @return 結果の文字列
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
     * ブランクかどうか返します。
     *
     * @param str
     *            文字列
     * @return ブランクなら{@literal true}
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
     * ブランクではないかどうか返します。
     *
     * @param str
     *            文字列
     * @return ブランクではなければ{@literal true}
     * @see #isBlank(String)
     */
    public static boolean isNotBlank(final String str) {
        return !isBlank(str);
    }

    /**
     * charを含んでいるかどうか返します。
     *
     * @param str
     *            文字列
     * @param ch
     *            char
     * @return charを含んでいるかどうか
     */
    public static boolean contains(final String str, final char ch) {
        if (isEmpty(str)) {
            return false;
        }
        return str.indexOf(ch) >= 0;
    }

    /**
     * 文字列を含んでいるかどうか返します。
     *
     * @param s1
     *            文字列
     * @param s2
     *            比較する対象となる文字列
     * @return 文字列を含んでいるかどうか
     */
    public static boolean contains(final String s1, final String s2) {
        if (isEmpty(s1)) {
            return false;
        }
        return s1.indexOf(s2) >= 0;
    }

    /**
     * 文字列同士が等しいかどうか返します。どちらもnullの場合は、<code>true</code>を返します。
     *
     * @param target1
     *            文字列1
     * @param target2
     *            文字列2
     * @return 文字列同士が等しいかどうか
     */
    public static boolean equals(final String target1, final String target2) {
        return target1 == null ? target2 == null : target1.equals(target2);
    }

    /**
     * 大文字小文字を無視して文字列同士が等しいかどうか返します。どちらもnullの場合は、<code>true</code>を返します。
     *
     * @param target1
     *            文字列1
     * @param target2
     *            文字列2
     * @return 大文字小文字を無視して文字列同士が等しければ{@literal true}
     */
    public static boolean equalsIgnoreCase(final String target1, final String target2) {
        return target1 == null ? target2 == null : target1.equalsIgnoreCase(target2);
    }

    /**
     * 大文字小文字を無視して特定の文字で終わっているのかどうかを返します。
     *
     * @param target1
     *            テキスト
     * @param target2
     *            比較する文字列
     * @return 大文字小文字を無視して特定の文字で終わっていれば{@literal true}
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
     * 大文字小文字を無視して特定の文字で始まっているのかどうかを返します。
     *
     * @param target1
     *            テキスト
     * @param target2
     *            比較する文字列
     * @return 大文字小文字を無視して特定の文字で始まっていれば{@literal true}
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
     * 文字列の最後から指定した文字列で始まっている部分より手前を返します。
     *
     * @param str
     *            文字列
     * @param separator
     *            セパレータ
     * @return 結果の文字列
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
     * 文字列の最後から指定した文字列で始まっている部分より後ろを返します。
     *
     * @param str
     *            文字列
     * @param separator
     *            セパレータ
     * @return 結果の文字列
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
     * 16進数の文字列に変換します。
     *
     * @param bytes
     *            バイトの配列
     * @return 16進数の文字列
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
     * {@literal int}の値を16進数の文字列に変換します。
     *
     * @param i
     *            {@literal int}の値
     * @return 16進数の文字列
     */
    public static String toHex(final int i) {
        final StringBuilder buf = new StringBuilder();
        appendHex(buf, i);
        return new String(buf);
    }

    /**
     * 文字列に、数値を16進数に変換した文字列を追加します。
     *
     * @param buf
     *            追加先の文字列
     * @param i
     *            数値
     */
    public static void appendHex(final StringBuilder buf, final byte i) {
        buf.append(Character.forDigit((i & 0xf0) >> 4, 16));
        buf.append(Character.forDigit(i & 0x0f, 16));
    }

    /**
     * 文字列に、数値を16進数に変換した文字列を追加します。
     *
     * @param buf
     *            追加先の文字列
     * @param i
     *            数値
     */
    public static void appendHex(final StringBuilder buf, final int i) {
        buf.append(Integer.toHexString(i >> 24 & 0xff));
        buf.append(Integer.toHexString(i >> 16 & 0xff));
        buf.append(Integer.toHexString(i >> 8 & 0xff));
        buf.append(Integer.toHexString(i & 0xff));
    }

    /**
     * _記法をキャメル記法に変換します。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.camelize("USER_ID")  = "UserId"
     * </pre>
     *
     * @param s
     *            テキスト
     * @return 結果の文字列
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
     * キャメル記法を_記法に変換します。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.decamelize("UserId")  = "USER_ID"
     * </pre>
     *
     * @param s
     *            テキスト
     * @return 結果の文字列
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
     * 文字列が数値のみで構成されているかどうかを返します。
     *
     * @param s
     *            文字列
     * @return 数値のみで構成されている場合、<code>true</code>
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
     * 引数の文字列を返します。引数の文字列が<code>null</code>だったら空文字を返します。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.defaultString(null)  = ""
     * StringUtil.defaultString("")    = ""
     * StringUtil.defaultString("aaa") = "aaa"
     * </pre>
     *
     * @param str
     *            文字列(<code>null</code>可)
     * @return 引数の文字列を返します。引数の文字列が<code>null</code>だったら空文字を返します。
     */
    public static String defaultString(final String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * 引数の文字列を返します。引数の文字列が<code>null</code>だったら<code>defaultStr</code>を返します。
     * <p>
     * 次のように使います．
     * </p>
     *
     * <pre>
     * StringUtil.defaultString(null, "NULL")  = "NULL"
     * StringUtil.defaultString("", "NULL")    = ""
     * StringUtil.defaultString("aaa", "NULL") = "aaa"
     * StringUtil.defaultString("aaa", null) = "aaa"
     * StringUtil.defaultString(null, null) = null
     * </pre>
     *
     * @param str
     *            文字列(<code>null</code>可)
     * @param defaultStr
     *            引数の文字列が<code>null</code>だったら返す文字列(<code>null</code>可)
     * @return 引数の文字列を返します。引数の文字列が<code>null</code>だったら<code>defaultStr</code>
     *         を返します。
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
