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
package org.codelibs.core.lang;

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.codelibs.core.exception.IllegalAccessRuntimeException;
import org.codelibs.core.exception.InstantiationRuntimeException;
import org.codelibs.core.exception.InvocationTargetRuntimeException;

/**
 * {@link Constructor}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class ConstructorUtil {

    /**
     * 指定された初期化パラメータで、コンストラクタの宣言クラスの新しいインスタンスを作成および初期化します。
     *
     * @param <T>
     *            コンストラクタの宣言クラス
     * @param constructor
     *            コンストラクタ。{@literal null}であってはいけません
     * @param args
     *            コンストラクタ呼び出しに引数として渡すオブジェクトの配列
     * @return コンストラクタを呼び出すことで作成される新規オブジェクト
     * @throws InstantiationRuntimeException
     *             基本となるコンストラクタを宣言するクラスが{@code abstract}クラスを表す場合
     * @throws IllegalAccessRuntimeException
     *             実パラメータ数と仮パラメータ数が異なる場合、 プリミティブ引数のラップ解除変換が失敗した場合、 またはラップ解除後、
     *             メソッド呼び出し変換によってパラメータ値を対応する仮パラメータ型に変換できない場合、
     *             このコンストラクタが列挙型に関連している場合
     * @see Constructor#newInstance(Object[])
     */
    public static <T> T newInstance(final Constructor<T> constructor, final Object... args)
            throws InstantiationRuntimeException, IllegalAccessRuntimeException {
        assertArgumentNotNull("constructor", constructor);

        try {
            return constructor.newInstance(args);
        } catch (final InstantiationException e) {
            throw new InstantiationRuntimeException(constructor.getDeclaringClass(), e);
        } catch (final IllegalAccessException e) {
            throw new IllegalAccessRuntimeException(constructor.getDeclaringClass(), e);
        } catch (final InvocationTargetException e) {
            throw new InvocationTargetRuntimeException(constructor.getDeclaringClass(), e);
        }
    }

    /**
     * <code>public</code>かどうかを返します。
     *
     * @param constructor
     *            コンストラクタ。{@literal null}や空文字列であってはいけません
     * @return <code>public</code>かどうか
     */
    public static boolean isPublic(final Constructor<?> constructor) {
        assertArgumentNotNull("constructor", constructor);

        return Modifier.isPublic(constructor.getModifiers());
    }

}
