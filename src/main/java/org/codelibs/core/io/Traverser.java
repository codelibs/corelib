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
package org.codelibs.core.io;

/**
 * クラスやリソースの集まりを表すオブジェクトです。
 *
 * @author koichik
 * @see TraversalUtil
 */
public interface Traverser {

    /**
     * 指定されたクラス名に対応するクラスファイルがこのインスタンスが扱うリソースの中に存在すれば<code>true</code>を返します。
     * <p>
     * インスタンス構築時にルートパッケージが指定されている場合、 指定されたクラス名はルートパッケージからの相対名として解釈されます。
     * </p>
     *
     * @param className
     *            クラス名
     * @return 指定されたクラス名に対応するクラスファイルがこのインスタンスが扱うリソースの中に存在すれば <code>true</code>
     */
    boolean isExistClass(final String className);

    /**
     * このインスタンスが扱うクラスを探して {@link ClassHandler#processClass(String, String) ハンドラ}
     * をコールバックします。
     * <p>
     * インスタンス構築時にルートパッケージが指定されている場合は、 ルートパッケージ以下のクラスのみが対象となります。
     * </p>
     *
     * @param handler
     *            クラスを処理するハンドラ
     */
    void forEach(ClassHandler handler);

    /**
     * このインスタンスが扱うリソースを探して
     * {@link ResourceHandler#processResource(String, java.io.InputStream) ハンドラ}
     * をコールバックします。
     * <p>
     * インスタンス構築時にルートディレクトリが指定されている場合は、 ルートディレクトリ以下のリソースのみが対象となります。
     * </p>
     *
     * @param handler
     *            リソースを処理するハンドラ
     */
    void forEach(ResourceHandler handler);

    /**
     * リソースの後処理を行います。
     */
    void close();

}
