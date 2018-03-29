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
package org.codelibs.core.lang;

import static org.codelibs.core.lang.ClassLoaderIterator.iterable;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;

import org.codelibs.core.collection.EnumerationIterator;
import org.codelibs.core.exception.ClIllegalStateException;
import org.codelibs.core.exception.ClassNotFoundRuntimeException;
import org.codelibs.core.exception.IORuntimeException;
import org.codelibs.core.message.MessageFormatter;

/**
 * {@link ClassLoader}を扱うためのユーティリティ・クラスです。
 *
 * @author koichik
 */
public abstract class ClassLoaderUtil {

    private static final Method findLoadedClassMethod = getFindLoadedClassMethod();

    private static final Method defineClassMethod = getDefineClassMethod();

    private static final Method definePackageMethod = getDefinePackageMethod();

    private static Method getFindLoadedClassMethod() {
        final Method method = ClassUtil.getDeclaredMethod(ClassLoader.class,
                "findLoadedClass", String.class);
        method.setAccessible(true);
        return method;
    }

    private static Method getDefineClassMethod() {
        final Method method = ClassUtil
                .getDeclaredMethod(ClassLoader.class, "defineClass",
                        String.class, byte[].class, int.class, int.class);
        method.setAccessible(true);
        return method;
    }

    private static Method getDefinePackageMethod() {
        final Method method = ClassUtil.getDeclaredMethod(ClassLoader.class,
                "definePackage", String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                URL.class);
        method.setAccessible(true);
        return method;
    }

    /**
     * クラスローダを返します。
     * <p>
     * クラスローダは以下の順で検索します。
     * </p>
     * <ol>
     * <li>呼び出されたスレッドにコンテキスト・クラスローダが設定されている場合はそのコンテキスト・クラスローダ</li>
     * <li>ターゲット・クラスをロードしたクラスローダを取得できればそのクラスローダ</li>
     * <li>このクラスをロードしたクラスローダを取得できればそのクラスローダ</li>
     * <li>システムクラスローダを取得できればそのクラスローダ</li>
     * </ol>
     * <p>
     * ただし、ターゲット・クラスをロードしたクラスローダとこのクラスをロードしたクラスローダの両方が取得できた場合で、
     * ターゲット・クラスをロードしたクラスローダがこのクラスをロードしたクラスローダの祖先であった場合は、
     * このクラスをロードしたクラスローダを返します。
     * </p>
     *
     * @param targetClass
     *            ターゲット・クラス。{@literal null}であってはいけません
     * @return クラスローダ
     * @throws IllegalStateException
     *             クラスローダを取得できなかった場合
     */
    public static ClassLoader getClassLoader(final Class<?> targetClass) {
        assertArgumentNotNull("targetClass", targetClass);

        final ClassLoader contextClassLoader = Thread.currentThread()
                .getContextClassLoader();
        if (contextClassLoader != null) {
            return contextClassLoader;
        }

        final ClassLoader targetClassLoader = targetClass.getClassLoader();
        final ClassLoader thisClassLoader = ClassLoaderUtil.class
                .getClassLoader();
        if (targetClassLoader != null && thisClassLoader != null) {
            if (isAncestor(thisClassLoader, targetClassLoader)) {
                return thisClassLoader;
            }
            return targetClassLoader;
        }
        if (targetClassLoader != null) {
            return targetClassLoader;
        }
        if (thisClassLoader != null) {
            return thisClassLoader;
        }

        final ClassLoader systemClassLoader = ClassLoader
                .getSystemClassLoader();
        if (systemClassLoader != null) {
            return systemClassLoader;
        }

        throw new ClIllegalStateException(MessageFormatter.getMessage(
                "ECL0001", "ClassLoader"));
    }

    /**
     * クラスローダ<code>other</code>がクラスローダ<code>cl</code>の祖先なら<code>true</code>
     * を返します。
     *
     * @param cl
     *            クラスローダ
     * @param other
     *            クラスローダ
     * @return クラスローダ<code>other</code>がクラスローダ<code>cl</code>の祖先なら
     *         <code>true</code>
     */
    protected static boolean isAncestor(final ClassLoader cl,
            final ClassLoader other) {
        for (final ClassLoader loader : iterable(cl)) {
            if (loader == other) {
                return true;
            }
        }
        return false;
    }

    /**
     * コンテキストクラスローダから指定された名前を持つすべてのリソースを探します。
     *
     * @param name
     *            リソース名。{@literal null}や空文字列であってはいけません
     * @return リソースに対する URL
     *         オブジェクトの列挙。リソースが見つからなかった場合、列挙は空になる。クラスローダがアクセスを持たないリソースは列挙に入らない
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final String name) {
        assertArgumentNotEmpty("name", name);

        return getResources(Thread.currentThread().getContextClassLoader(),
                name);
    }

    /**
     * {@link #getClassLoader(Class)}が返すクラスローダから指定された名前を持つすべてのリソースを探します。
     *
     * @param targetClass
     *            ターゲット・クラス。{@literal null}であってはいけません
     * @param name
     *            リソース名。{@literal null}や空文字列であってはいけません
     * @return リソースに対する URL
     *         オブジェクトの列挙。リソースが見つからなかった場合、列挙は空になる。クラスローダがアクセスを持たないリソースは列挙に入らない
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final Class<?> targetClass,
            final String name) {
        assertArgumentNotNull("targetClass", targetClass);
        assertArgumentNotNull("name", name);

        return getResources(getClassLoader(targetClass), name);
    }

    /**
     * 指定のクラスローダから指定された名前を持つすべてのリソースを探します。
     *
     * @param loader
     *            クラスローダ。{@literal null}であってはいけません
     * @param name
     *            リソース名。{@literal null}や空文字列であってはいけません
     * @return リソースに対する URL
     *         オブジェクトの列挙。リソースが見つからなかった場合、列挙は空になる。クラスローダがアクセスを持たないリソースは列挙に入らない
     * @see java.lang.ClassLoader#getResources(String)
     */
    public static Iterator<URL> getResources(final ClassLoader loader,
            final String name) {
        assertArgumentNotNull("loader", loader);
        assertArgumentNotEmpty("name", name);

        try {
            final Enumeration<URL> e = loader.getResources(name);
            return new EnumerationIterator<>(e);
        } catch (final IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 指定のクラスローダまたはその祖先のクラスローダが、 このバイナリ名を持つクラスの起動ローダとしてJava仮想マシンにより記録されていた場合は、
     * 指定されたバイナリ名を持つクラスを返します。 記録されていなかった場合は<code>null</code>を返します。
     *
     * @param classLoader
     *            クラスローダ。{@literal null}であってはいけません
     * @param className
     *            クラスのバイナリ名。{@literal null}や空文字列であってはいけません
     * @return <code>Class</code>オブジェクト。クラスがロードされていない場合は<code>null</code>
     * @see java.lang.ClassLoader#findLoadedClass(String)
     */
    public static Class<?> findLoadedClass(final ClassLoader classLoader,
            final String className) {
        assertArgumentNotNull("classLoader", classLoader);
        assertArgumentNotEmpty("className", className);

        for (final ClassLoader loader : iterable(classLoader)) {
            final Class<?> clazz = (Class<?>) MethodUtil.invoke(
                    findLoadedClassMethod, loader, className);
            if (clazz != null) {
                return clazz;
            }
        }
        return null;
    }

    /**
     * バイトの配列を<code>Class</code>クラスのインスタンスに変換します。
     *
     * @param classLoader
     *            バイナリデータから<code>Class</code>クラスのインスタンスに変換するクラスローダ。
     *            {@literal null}であってはいけません
     * @param className
     *            クラスのバイナリ名。{@literal null}や空文字列であってはいけません
     * @param bytes
     *            クラスデータを構成するバイト列。{@literal null}や空配列であってはいけません
     * @param offset
     *            クラスデータ<code>bytes</code>の開始オフセット
     * @param length
     *            クラスデータの長さ
     * @return 指定されたクラスデータから作成された<code>Class</code>オブジェクト
     * @see java.lang.ClassLoader#defineClass(String, byte[], int, int)
     */
    public static Class<?> defineClass(final ClassLoader classLoader,
            final String className, final byte[] bytes, final int offset,
            final int length) {
        assertArgumentNotNull("classLoader", classLoader);
        assertArgumentNotEmpty("className", className);
        assertArgumentNotEmpty("bytes", bytes);

        return (Class<?>) MethodUtil.invoke(defineClassMethod, classLoader,
                className, bytes, offset, length);
    }

    /**
     * 指定の<code>ClassLoader</code>で名前を使ってパッケージを定義します。
     *
     * @param classLoader
     *            パッケージを定義するクラスローダ。{@literal null}であってはいけません
     * @param name
     *            パッケージ名。{@literal null}や空文字列であってはいけません
     * @param specTitle
     *            仕様のタイトル
     * @param specVersion
     *            仕様のバージョン
     * @param specVendor
     *            仕様のベンダー
     * @param implTitle
     *            実装のタイトル
     * @param implVersion
     *            実装のバージョン
     * @param implVendor
     *            実装のベンダー
     * @param sealBase
     *            <code>null</code>でない場合、このパッケージは指定されたコードソース<code>URL</code>
     *            オブジェクトを考慮してシールされる。そうでない場合、パッケージはシールされない
     * @return 新しく定義された<code>Package</code>オブジェクト
     * @see java.lang.ClassLoader#definePackage(String, String, String, String,
     *      String, String, String, URL)
     */
    public static Package definePackage(final ClassLoader classLoader,
            final String name, final String specTitle,
            final String specVersion, final String specVendor,
            final String implTitle, final String implVersion,
            final String implVendor, final URL sealBase) {
        assertArgumentNotNull("classLoader", classLoader);
        assertArgumentNotEmpty("name", name);

        return (Package) MethodUtil.invoke(definePackageMethod, classLoader,
                name, specTitle, specVersion, specVendor, implTitle,
                implVersion, implVendor, sealBase);
    }

    /**
     * 指定されたバイナリ名を持つクラスをロードします。
     *
     * @param loader
     *            クラスローダ。{@literal null}であってはいけません
     * @param className
     *            クラスのバイナリ名。{@literal null}や空文字列であってはいけません
     * @return 結果の<code>Class</code>オブジェクト
     * @throws ClassNotFoundRuntimeException
     *             クラスが見つからなかった場合
     * @see java.lang.ClassLoader#loadClass(String)
     */
    public static Class<?> loadClass(final ClassLoader loader,
            final String className) {
        assertArgumentNotNull("loader", loader);
        assertArgumentNotEmpty("className", className);

        try {
            return loader.loadClass(className);
        } catch (final ClassNotFoundException e) {
            throw new ClassNotFoundRuntimeException(e);
        }
    }

}
