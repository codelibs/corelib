/*
 * Copyright 2012-2019 CodeLibs Project and the Others.
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

import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import org.codelibs.core.exception.IllegalAccessRuntimeException;
import org.codelibs.core.exception.InvocationTargetRuntimeException;

/**
 * {@link Method}用のユーティリティクラスです。
 *
 * @author higa
 */
public abstract class MethodUtil {

    /**
     * {@link Method}オブジェクトによって表される基本となるメソッドを、指定したオブジェクトに対して指定したパラメータで呼び出します。
     *
     * @param <T>
     *            メソッドの戻り値の型
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @param target
     *            基本となるメソッドの呼び出し元のオブジェクト。{@literal static}メソッドの場合は{@literal null}
     * @param args
     *            メソッド呼び出しに使用される引数
     * @return このオブジェクトが表すメソッドを、パラメータ{@code args}を使用して{@code obj}にディスパッチした結果
     * @throws IllegalAccessRuntimeException
     *             この{@link Method}オブジェクトがJava言語アクセス制御を実施し、 基本となるメソッドにアクセスできない場合
     * @throws InvocationTargetRuntimeException
     *             基本となるメソッドが例外をスローする場合
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(final Method method, final Object target, final Object... args) throws InvocationTargetRuntimeException,
            IllegalAccessRuntimeException {
        assertArgumentNotNull("method", method);

        try {
            return (T) method.invoke(target, args);
        } catch (final InvocationTargetException ex) {
            final Throwable t = ex.getCause();
            if (t instanceof RuntimeException) {
                throw (RuntimeException) t;
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw new InvocationTargetRuntimeException(method.getDeclaringClass(), ex);
        } catch (final IllegalAccessException ex) {
            throw new IllegalAccessRuntimeException(method.getDeclaringClass(), ex);
        }
    }

    /**
     * {@link Method}オブジェクトによって表される基本となる{@code static}メソッドを、指定したパラメータで呼び出します。
     *
     * @param <T>
     *            メソッドの戻り値の型
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @param args
     *            メソッド呼び出しに使用される引数
     * @return このオブジェクトが表す{@code static}メソッドを、パラメータ{@code args}を使用してディスパッチした結果
     * @throws IllegalAccessRuntimeException
     *             この{@link Method}オブジェクトがJava言語アクセス制御を実施し、 基本となるメソッドにアクセスできない場合
     * @throws InvocationTargetRuntimeException
     *             基本となるメソッドが例外をスローする場合
     * @see Method#invoke(Object, Object[])
     */
    @SuppressWarnings("unchecked")
    public static <T> T invokeStatic(final Method method, final Object... args) throws InvocationTargetRuntimeException,
            IllegalAccessRuntimeException {
        assertArgumentNotNull("method", method);

        return (T) invoke(method, null, args);
    }

    /**
     * <code>abstract</code>かどうかを返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return <code>abstract</code>かどうか
     */
    public static boolean isAbstract(final Method method) {
        return Modifier.isAbstract(method.getModifiers());
    }

    /**
     * <code>public</code>かどうかを返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return <code>public</code>なら{@literal true}
     */
    public static boolean isPublic(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * <code>static</code>かどうかを返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return <code>static</code>かどうか
     */
    public static boolean isStatic(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * <code>final</code>かどうかを返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return <code>final </code>かどうか
     */
    public static boolean isFinal(final Method method) {
        assertArgumentNotNull("method", method);

        return Modifier.isFinal(method.getModifiers());
    }

    /**
     * シグニチャの文字列表現を返します。
     *
     * @param methodName
     *            メソッド名。{@literal null}や空文字列であってはいけません
     * @param argTypes
     *            引数型のな並び
     * @return シグニチャの文字列表現
     */
    public static String getSignature(final String methodName, final Class<?>... argTypes) {
        assertArgumentNotEmpty("methodName", methodName);

        final StringBuilder buf = new StringBuilder(100);
        buf.append(methodName).append("(");
        if (argTypes != null && argTypes.length > 0) {
            for (final Class<?> argType : argTypes) {
                buf.append(argType.getName()).append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");
        return new String(buf);
    }

    /**
     * シグニチャの文字列表現を返します。
     *
     * @param methodName
     *            メソッド名。{@literal null}や空文字列であってはいけません
     * @param methodArgs
     *            引数の並び
     * @return シグニチャの文字列表現
     */
    public static String getSignature(final String methodName, final Object... methodArgs) {
        assertArgumentNotEmpty("methodName", methodName);

        final StringBuilder buf = new StringBuilder(100);
        buf.append(methodName).append("(");
        if (methodArgs != null && methodArgs.length > 0) {
            for (final Object arg : methodArgs) {
                buf.append(arg == null ? null : arg.getClass().getName()).append(", ");
            }
            buf.setLength(buf.length() - 2);
        }
        buf.append(")");
        return buf.toString();
    }

    /**
     * {@literal equals(Object)}メソッドかどうかを返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return {@literal equals(Object)}メソッドなら{@literal true}
     */
    public static boolean isEqualsMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("equals") && method.getReturnType() == boolean.class
                && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Object.class;
    }

    /**
     * {@literal hashCode()}メソッドかどうか返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return {@literal hashCode()}メソッドなら{@literal true}
     */
    public static boolean isHashCodeMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("hashCode") && method.getReturnType() == int.class
                && method.getParameterTypes().length == 0;
    }

    /**
     * {@literal toString()}メソッドかどうか返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return {@literal toString()}メソッドなら{@literal true}
     */
    public static boolean isToStringMethod(final Method method) {
        assertArgumentNotNull("method", method);

        return method != null && method.getName().equals("toString") && method.getReturnType() == String.class
                && method.getParameterTypes().length == 0;
    }

    /**
     * メソッドの引数型 (パラメタ化されたコレクション) の要素型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @param position
     *            パラメタ化されたコレクションが宣言されているメソッド引数の位置
     * @return 指定されたメソッドの引数型として宣言されているパラメタ化されたコレクションの要素型
     */
    public static Class<?> getElementTypeOfCollectionFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(parameterTypes[position]));
    }

    /**
     * 指定されたメソッドの戻り値型として宣言されているパラメタ化されたコレクションの要素型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return 指定されたメソッドの戻り値型として宣言されているパラメタ化されたコレクションの要素型
     */
    public static Class<?> getElementTypeOfCollectionFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getElementTypeOfCollection(returnType));
    }

    /**
     * メソッドの引数型 (パラメタ化されたマップ) のキー型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @param position
     *            パラメタ化されたマップが宣言されているメソッド引数の位置
     * @return 指定されたメソッドの引数型として宣言されているパラメタ化されたマップのキー型
     */
    public static Class<?> getKeyTypeOfMapFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(parameterTypes[position]));
    }

    /**
     * 指定されたメソッドの戻り値型として宣言されているパラメタ化されたマップのキー型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return 指定されたメソッドの戻り値型として宣言されているパラメタ化されたマップのキー型
     */
    public static Class<?> getKeyTypeOfMapFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getKeyTypeOfMap(returnType));
    }

    /**
     * メソッドの引数型 (パラメタ化されたマップ) の値型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @param position
     *            パラメタ化されたマップが宣言されているメソッド引数の位置
     * @return 指定されたメソッドの引数型として宣言されているパラメタ化されたマップの値型
     */
    public static Class<?> getValueTypeOfMapFromParameterType(final Method method, final int position) {
        assertArgumentNotNull("method", method);

        final Type[] parameterTypes = method.getGenericParameterTypes();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(parameterTypes[position]));
    }

    /**
     * 指定されたメソッドの戻り値型として宣言されているパラメタ化されたマップの値型を返します。
     *
     * @param method
     *            メソッド。{@literal null}であってはいけません
     * @return 指定されたメソッドの戻り値型として宣言されているパラメタ化されたマップの値型
     */
    public static Class<?> getValueTypeOfMapFromReturnType(final Method method) {
        assertArgumentNotNull("method", method);

        final Type returnType = method.getGenericReturnType();
        return GenericsUtil.getRawClass(GenericsUtil.getValueTypeOfMap(returnType));
    }

}
