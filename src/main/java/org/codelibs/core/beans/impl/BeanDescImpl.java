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
package org.codelibs.core.beans.impl;

import static java.util.Collections.unmodifiableCollection;
import static org.codelibs.core.collection.CollectionsUtil.newArrayList;
import static org.codelibs.core.collection.CollectionsUtil.newHashMap;
import static org.codelibs.core.collection.CollectionsUtil.newHashSet;
import static org.codelibs.core.lang.GenericsUtil.getActualClass;
import static org.codelibs.core.lang.GenericsUtil.getGenericParameters;
import static org.codelibs.core.lang.GenericsUtil.getTypeVariableMap;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentArrayIndex;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotEmpty;
import static org.codelibs.core.misc.AssertionUtil.assertArgumentNotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codelibs.core.beans.BeanDesc;
import org.codelibs.core.beans.ConstructorDesc;
import org.codelibs.core.beans.FieldDesc;
import org.codelibs.core.beans.MethodDesc;
import org.codelibs.core.beans.ParameterizedClassDesc;
import org.codelibs.core.beans.PropertyDesc;
import org.codelibs.core.collection.ArrayMap;
import org.codelibs.core.collection.CaseInsensitiveMap;
import org.codelibs.core.convert.ByteConversionUtil;
import org.codelibs.core.convert.DoubleConversionUtil;
import org.codelibs.core.convert.FloatConversionUtil;
import org.codelibs.core.convert.IntegerConversionUtil;
import org.codelibs.core.convert.LongConversionUtil;
import org.codelibs.core.convert.ShortConversionUtil;
import org.codelibs.core.exception.BeanFieldSetAccessibleFailureException;
import org.codelibs.core.exception.ConstructorNotFoundRuntimeException;
import org.codelibs.core.exception.FieldNotFoundRuntimeException;
import org.codelibs.core.exception.MethodNotFoundRuntimeException;
import org.codelibs.core.exception.PropertyNotFoundRuntimeException;
import org.codelibs.core.lang.ClassUtil;
import org.codelibs.core.lang.FieldUtil;
import org.codelibs.core.lang.StringUtil;

/**
 * {@link BeanDesc}の実装クラスです。
 *
 * @author higa
 */
public class BeanDescImpl implements BeanDesc {

    /** 空のオブジェクト配列 */
    protected static final Object[] EMPTY_ARGS = new Object[0];

    /** 空のクラス配列 */
    protected static final Class<?>[] EMPTY_PARAM_TYPES = new Class<?>[0];

    /** Beanのクラス */
    protected final Class<?> beanClass;

    /** 型引数と型変数のマップ */
    protected final Map<TypeVariable<?>, Type> typeVariables;

    /** プロパティ名から{@link PropertyDesc}へのマップ */
    protected final CaseInsensitiveMap<PropertyDesc> propertyDescCache = new CaseInsensitiveMap<>();

    /** フィールド名から{@link FieldDescImpl}へのマップ */
    protected final ArrayMap<String, FieldDesc> fieldDescCache = new ArrayMap<>();

    /** {@link ConstructorDesc}の配列 */
    protected final List<ConstructorDesc> constructorDescs = newArrayList();

    /** メソッド名から{@link MethodDesc}配列へのマップ */
    protected final Map<String, MethodDesc[]> methodDescsCache = newHashMap();

    /** 不正なプロパティ名のセット */
    protected final Set<String> invalidPropertyNames = newHashSet();

    /**
     * {@link BeanDescImpl}を作成します。
     *
     * @param beanClass
     *            ビーンのクラス。{@literal null}であってはいけません
     */
    public BeanDescImpl(final Class<?> beanClass) {
        assertArgumentNotNull("beanClass", beanClass);

        this.beanClass = beanClass;
        typeVariables = getTypeVariableMap(beanClass);
        setupConstructorDescs();
        setupPropertyDescs();
        setupMethodDescs();
        setupFieldDescs();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Class<T> getBeanClass() {
        return (Class<T>) beanClass;
    }

    @Override
    public Map<TypeVariable<?>, Type> getTypeVariables() {
        return typeVariables;
    }

    @Override
    public boolean hasPropertyDesc(final String propertyName) {
        assertArgumentNotEmpty("propertyName", propertyName);

        return propertyDescCache.get(propertyName) != null;
    }

    @Override
    public PropertyDesc getPropertyDesc(final String propertyName) {
        assertArgumentNotEmpty("propertyName", propertyName);

        final PropertyDesc pd = propertyDescCache.get(propertyName);
        if (pd == null) {
            throw new PropertyNotFoundRuntimeException(beanClass, propertyName);
        }
        return pd;
    }

    @Override
    public PropertyDesc getPropertyDesc(final int index) {
        assertArgumentArrayIndex("index", index, getPropertyDescSize());

        return propertyDescCache.getAt(index);
    }

    @Override
    public int getPropertyDescSize() {
        return propertyDescCache.size();
    }

    @Override
    public Iterable<PropertyDesc> getPropertyDescs() {
        return unmodifiableCollection(propertyDescCache.values());
    }

    @Override
    public boolean hasFieldDesc(final String fieldName) {
        assertArgumentNotEmpty("fieldName", fieldName);

        return fieldDescCache.containsKey(fieldName);
    }

    @Override
    public FieldDesc getFieldDesc(final String fieldName) {
        assertArgumentNotEmpty("fieldName", fieldName);

        final FieldDesc fieldDesc = fieldDescCache.get(fieldName);
        if (fieldDesc == null) {
            throw new FieldNotFoundRuntimeException(beanClass, fieldName);
        }
        return fieldDesc;
    }

    @Override
    public FieldDesc getFieldDesc(final int index) {
        assertArgumentArrayIndex("index", index, getFieldDescSize());

        return fieldDescCache.getAt(index);
    }

    @Override
    public int getFieldDescSize() {
        return fieldDescCache.size();
    }

    @Override
    public Iterable<FieldDesc> getFieldDescs() {
        return unmodifiableCollection(fieldDescCache.values());
    }

    @Override
    public <T> T newInstance(final Object... args) {
        final ConstructorDesc constructorDesc = getSuitableConstructorDesc(args);
        return constructorDesc.newInstance(args);
    }

    @Override
    public ConstructorDesc getConstructorDesc(final Class<?>... paramTypes) {
        for (final ConstructorDesc constructorDesc : constructorDescs) {
            if (Arrays.equals(paramTypes, constructorDesc.getParameterTypes())) {
                return constructorDesc;
            }
        }
        throw new ConstructorNotFoundRuntimeException(beanClass, paramTypes);
    }

    @Override
    public ConstructorDesc getSuitableConstructorDesc(final Object... args) {
        ConstructorDesc constructorDesc = findSuitableConstructorDesc(args);
        if (constructorDesc != null) {
            return constructorDesc;
        }
        constructorDesc = findSuitableConstructorDescAdjustNumber(args);
        if (constructorDesc != null) {
            return constructorDesc;
        }
        throw new ConstructorNotFoundRuntimeException(beanClass, args);
    }

    @Override
    public ConstructorDesc getConstructorDesc(final int index) {
        return constructorDescs.get(index);
    }

    @Override
    public int getConstructorDescSize() {
        return constructorDescs.size();
    }

    @Override
    public Iterable<ConstructorDesc> getConstructorDescs() {
        return unmodifiableCollection(constructorDescs);
    }

    @Override
    public MethodDesc getMethodDesc(final String methodName, final Class<?>... paramTypes) {
        assertArgumentNotEmpty("methodName", methodName);

        final MethodDesc methodDesc = getMethodDescNoException(methodName, paramTypes);
        if (methodDesc != null) {
            return methodDesc;
        }
        throw new MethodNotFoundRuntimeException(beanClass, methodName, paramTypes);
    }

    @Override
    public MethodDesc getMethodDescNoException(final String methodName, final Class<?>... paramTypes) {
        assertArgumentNotEmpty("methodName", methodName);

        final MethodDesc[] methodDescs = methodDescsCache.get(methodName);
        if (methodDescs == null) {
            return null;
        }
        for (final MethodDesc methodDesc : methodDescs) {
            if (Arrays.equals(paramTypes, methodDesc.getParameterTypes())) {
                return methodDesc;
            }
        }
        return null;
    }

    @Override
    public MethodDesc getSuitableMethodDesc(final String methodName, final Object... args) {
        assertArgumentNotEmpty("methodName", methodName);

        final MethodDesc[] methodDescs = getMethodDescs(methodName);
        MethodDesc methodDesc = findSuitableMethod(methodDescs, args);
        if (methodDesc != null) {
            return methodDesc;
        }
        methodDesc = findSuitableMethodDescAdjustNumber(methodDescs, args);
        if (methodDesc != null) {
            return methodDesc;
        }
        throw new MethodNotFoundRuntimeException(beanClass, methodName, args);
    }

    @Override
    public MethodDesc[] getMethodDescs(final String methodName) {
        assertArgumentNotEmpty("methodName", methodName);

        final MethodDesc[] methodDescs = methodDescsCache.get(methodName);
        if (methodDescs == null) {
            throw new MethodNotFoundRuntimeException(beanClass, methodName, null);
        }
        return methodDescs;
    }

    @Override
    public boolean hasMethodDesc(final String methodName) {
        assertArgumentNotEmpty("methodName", methodName);

        return methodDescsCache.containsKey(methodName);
    }

    @Override
    public String[] getMethodNames() {
        return methodDescsCache.keySet().toArray(new String[methodDescsCache.size()]);
    }

    /**
     * {@link PropertyDesc}を返します。
     *
     * @param propertyName
     *            プロパティ名
     * @return {@link PropertyDesc}。プロパティが存在しない場合は{@literal null}
     */
    protected PropertyDesc getPropertyDescNoException(final String propertyName) {
        return propertyDescCache.get(propertyName);
    }

    /**
     * 引数に適合する{@link ConstructorDesc}を返します。
     *
     * @param args
     *            コンストラクタ引数の並び
     * @return 引数に適合する{@link ConstructorDesc}。存在しない場合は{@literal null}
     */
    protected ConstructorDesc findSuitableConstructorDesc(final Object... args) {
        for (final ConstructorDesc constructorDesc : constructorDescs) {
            if (isSuitable(constructorDesc.getParameterTypes(), args, false)) {
                return constructorDesc;
            }
        }
        return null;
    }

    /**
     * 引数に適合する{@link ConstructorDesc}を返します。
     * <p>
     * 引数の型が数値の場合、引数を数値に変換することが出来れば適合するとみなします。
     * </p>
     *
     * @param args
     *            コンストラクタ引数の並び
     * @return 引数に適合する{@link ConstructorDesc}。存在しない場合は{@literal null}
     */
    protected ConstructorDesc findSuitableConstructorDescAdjustNumber(final Object... args) {
        for (final ConstructorDesc constructorDesc : constructorDescs) {
            if (isSuitable(constructorDesc.getParameterTypes(), args, true)) {
                return constructorDesc;
            }
        }
        return null;
    }

    /**
     * 引数に適合する{@link MethodDesc}を返します。
     *
     * @param methodDescs
     *            メソッドの配列
     * @param args
     *            メソッド引数の並び
     * @return 引数に適合する{@link MethodDesc}。存在しない場合は{@literal null}
     */
    protected MethodDesc findSuitableMethod(final MethodDesc[] methodDescs, final Object[] args) {
        for (final MethodDesc methodDesc : methodDescs) {
            if (isSuitable(methodDesc.getParameterTypes(), args, false)) {
                return methodDesc;
            }
        }
        return null;
    }

    /**
     * 引数に適合する{@link MethodDesc}を返します。
     * <p>
     * 引数の型が数値の場合、引数を数値に変換することが出来れば適合するとみなします。
     * </p>
     *
     * @param methodDescs
     *            メソッドの配列
     * @param args
     *            メソッド引数の並び
     * @return 引数に適合する{@link MethodDesc}。存在しない場合は{@literal null}
     */
    protected MethodDesc findSuitableMethodDescAdjustNumber(final MethodDesc[] methodDescs, final Object[] args) {
        for (final MethodDesc methodDesc : methodDescs) {
            if (isSuitable(methodDesc.getParameterTypes(), args, true)) {
                return methodDesc;
            }
        }
        return null;
    }

    /**
     * 引数が引数型に適合するかチェックします。
     *
     * @param paramTypes
     *            引数型の並び
     * @param args
     *            引数の並び
     * @param adjustNumber
     *            引数型が数値型の場合に引数を適合するように変換する場合は{@literal true}
     * @return 引数が引数型に適合する場合は{@literal true}
     */
    protected boolean isSuitable(final Class<?>[] paramTypes, final Object[] args, final boolean adjustNumber) {
        if (args == null) {
            return paramTypes.length == 0;
        }
        if (paramTypes.length != args.length) {
            return false;
        }
        for (int i = 0; i < args.length; ++i) {
            if (args[i] == null) {
                continue;
            }
            if (ClassUtil.isAssignableFrom(paramTypes[i], args[i].getClass())) {
                continue;
            }
            if (adjustNumber && adjustNumber(paramTypes, args, i)) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 指定された位置の引数型が数値の場合、引数を適合するように変換します。
     *
     * @param paramTypes
     *            引数型の並び
     * @param args
     *            引数の並び
     * @param index
     *            操作対象となる引数のインデックス
     * @return 引数を適合するように変換した場合は{@literal true}
     */
    protected static boolean adjustNumber(final Class<?>[] paramTypes, final Object[] args, final int index) {
        if (paramTypes[index].isPrimitive()) {
            if (paramTypes[index] == byte.class) {
                args[index] = ByteConversionUtil.toByte(args[index]);
                return true;
            } else if (paramTypes[index] == short.class) {
                args[index] = ShortConversionUtil.toShort(args[index]);
                return true;
            } else if (paramTypes[index] == int.class) {
                args[index] = IntegerConversionUtil.toInteger(args[index]);
                return true;
            } else if (paramTypes[index] == long.class) {
                args[index] = LongConversionUtil.toLong(args[index]);
                return true;
            } else if (paramTypes[index] == float.class) {
                args[index] = FloatConversionUtil.toFloat(args[index]);
                return true;
            } else if (paramTypes[index] == double.class) {
                args[index] = DoubleConversionUtil.toDouble(args[index]);
                return true;
            }
        } else {
            if (paramTypes[index] == Byte.class) {
                args[index] = ByteConversionUtil.toByte(args[index]);
                return true;
            } else if (paramTypes[index] == Short.class) {
                args[index] = ShortConversionUtil.toShort(args[index]);
                return true;
            } else if (paramTypes[index] == Integer.class) {
                args[index] = IntegerConversionUtil.toInteger(args[index]);
                return true;
            } else if (paramTypes[index] == Long.class) {
                args[index] = LongConversionUtil.toLong(args[index]);
                return true;
            } else if (paramTypes[index] == Float.class) {
                args[index] = FloatConversionUtil.toFloat(args[index]);
                return true;
            } else if (paramTypes[index] == Double.class) {
                args[index] = DoubleConversionUtil.toDouble(args[index]);
                return true;
            }
        }
        return false;
    }

    /**
     * {@link PropertyDesc}を準備します。
     */
    protected void setupPropertyDescs() {
        for (final Method m : beanClass.getMethods()) {
            if (m.isBridge() || m.isSynthetic()) {
                continue;
            }
            final String methodName = m.getName();
            if (methodName.startsWith("get")) {
                if (m.getParameterTypes().length != 0 || methodName.equals("getClass") || m.getReturnType() == void.class) {
                    continue;
                }
                final String propertyName = StringUtil.decapitalize(methodName.substring(3));
                setupReadMethod(m, propertyName);
            } else if (methodName.startsWith("is")) {
                if (m.getParameterTypes().length != 0
                        || !m.getReturnType().equals(Boolean.TYPE) && !m.getReturnType().equals(Boolean.class)) {
                    continue;
                }
                final String propertyName = StringUtil.decapitalize(methodName.substring(2));
                setupReadMethod(m, propertyName);
            } else if (methodName.startsWith("set")) {
                if (m.getParameterTypes().length != 1 || methodName.equals("setClass") || m.getReturnType() != void.class) {
                    continue;
                }
                final String propertyName = StringUtil.decapitalize(methodName.substring(3));
                setupWriteMethod(m, propertyName);
            }
        }
        for (final String name : invalidPropertyNames) {
            propertyDescCache.remove(name);
        }
        invalidPropertyNames.clear();
    }

    /**
     * getterメソッドを準備します。
     *
     * @param readMethod
     *            getterメソッド
     * @param propertyName
     *            プロパティ名
     */
    protected void setupReadMethod(final Method readMethod, final String propertyName) {
        final Class<?> propertyType = readMethod.getReturnType();
        PropertyDescImpl propDesc = (PropertyDescImpl) propertyDescCache.get(propertyName);
        if (propDesc == null) {
            propDesc = new PropertyDescImpl(propertyName, propertyType, readMethod, null, null, this);
            addPropertyDesc(propDesc);
        } else if (propDesc.getPropertyType() != propertyType) {
            invalidPropertyNames.add(propertyName);
        } else {
            propDesc.setReadMethod(readMethod);
        }
    }

    /**
     * setterメソッドを準備します。
     *
     * @param writeMethod
     *            setterメソッド
     * @param propertyName
     *            プロパティ名
     */
    protected void setupWriteMethod(final Method writeMethod, final String propertyName) {
        final Class<?> propertyType = writeMethod.getParameterTypes()[0];
        PropertyDescImpl propDesc = (PropertyDescImpl) propertyDescCache.get(propertyName);
        if (propDesc == null) {
            propDesc = new PropertyDescImpl(propertyName, propertyType, null, writeMethod, null, this);
            addPropertyDesc(propDesc);
        } else if (propDesc.getPropertyType() != propertyType) {
            invalidPropertyNames.add(propertyName);
        } else {
            propDesc.setWriteMethod(writeMethod);
        }
    }

    /**
     * {@link PropertyDesc}を追加します．
     *
     * @param propertyDesc
     *            {@link PropertyDesc}
     */
    protected void addPropertyDesc(final PropertyDescImpl propertyDesc) {
        assertArgumentNotNull("propertyDesc", propertyDesc);
        propertyDescCache.put(propertyDesc.getPropertyName(), propertyDesc);
    }

    /**
     * コンストラクタを準備します。
     */
    protected void setupConstructorDescs() {
        for (final Constructor<?> constructor : beanClass.getConstructors()) {
            constructorDescs.add(new ConstructorDescImpl(this, constructor));
        }
    }

    /**
     * メソッドを準備します。
     */
    protected void setupMethodDescs() {
        final ArrayMap<String, List<MethodDesc>> methodDescListMap = new ArrayMap<>();
        for (final Method method : beanClass.getMethods()) {
            if (method.isBridge() || method.isSynthetic()) {
                continue;
            }
            final String methodName = method.getName();
            List<MethodDesc> list = methodDescListMap.get(methodName);
            if (list == null) {
                list = newArrayList();
                methodDescListMap.put(methodName, list);
            }
            list.add(new MethodDescImpl(this, method));
        }
        for (int i = 0; i < methodDescListMap.size(); ++i) {
            final List<MethodDesc> methodDescList = methodDescListMap.getAt(i);
            methodDescsCache.put(methodDescListMap.getKeyAt(i), methodDescList.toArray(new MethodDesc[methodDescList.size()]));
        }
    }

    /**
     * フィールドを準備します。
     */
    protected void setupFieldDescs() {
        if (beanClass.isInterface()) {
            setupFieldDescsByInterface(beanClass);
        } else {
            setupFieldDescsByClass(beanClass);
        }
    }

    /**
     * インターフェースに定義されたフィールドを準備します。
     *
     * @param interfaceClass
     *            対象のインターフェース
     */
    protected void setupFieldDescsByInterface(final Class<?> interfaceClass) {
        addFieldDescs(interfaceClass);
        final Class<?>[] interfaces = interfaceClass.getInterfaces();
        for (final Class<?> intf : interfaces) {
            setupFieldDescsByInterface(intf);
        }
    }

    /**
     * クラスに定義されたフィールドを準備します。
     *
     * @param targetClass
     *            対象のクラス
     */
    private void setupFieldDescsByClass(final Class<?> targetClass) {
        addFieldDescs(targetClass);
        for (final Class<?> intf : targetClass.getInterfaces()) {
            setupFieldDescsByInterface(intf);
        }
        final Class<?> superClass = targetClass.getSuperclass();
        if (superClass != Object.class && superClass != null) {
            setupFieldDescsByClass(superClass);
        }
    }

    /**
     * クラスまたはインターフェースに定義されたフィールドを追加します。
     *
     * @param clazz
     *            対象のクラスまたはインターフェース
     */
    protected void addFieldDescs(final Class<?> clazz) {
        for (final Field field : clazz.getDeclaredFields()) {
            final String fname = field.getName();
            if (fieldDescCache.containsKey(fname)) {
                continue;
            }
            setFieldAccessible(field);
            final FieldDescImpl fieldDesc = new FieldDescImpl(this, field);
            fieldDescCache.put(fname, fieldDesc);
            if (!FieldUtil.isInstanceField(field)) {
                continue;
            }
            if (hasPropertyDesc(fname)) {
                final PropertyDescImpl pd = (PropertyDescImpl) propertyDescCache.get(field.getName());
                pd.setField(field);
                continue;
            }
            if (FieldUtil.isPublicField(field)) {
                final PropertyDescImpl pd = new PropertyDescImpl(field.getName(), field.getType(), null, null, field, this);
                propertyDescCache.put(fname, pd);
            }
        }
    }

    private void setFieldAccessible(Field field) {
        if (isExceptPrivateAccessible(field)) {
            return;
        }
        try {
            field.setAccessible(true);
        } catch (RuntimeException e) {
            throw new BeanFieldSetAccessibleFailureException(beanClass, field, e);
        }
    }

    private boolean isExceptPrivateAccessible(Field field) {
        // to avoid warning of JDK-internal access at Java11
        // Lasta Di does not need private access to the classes
        final String fqcn = field.getDeclaringClass().getName();
        return fqcn.startsWith("java.") || fqcn.startsWith("jdk.") || fqcn.startsWith("com.sun.") || fqcn.startsWith("sun.");
    }

    /**
     * {@link Type}を表現する{@link ParameterizedClassDesc}を作成して返します。
     *
     * @param type
     *            型
     * @param map
     *            パラメータ化された型が持つ型変数をキー、型引数を値とする{@link Map}
     * @return 型を表現する{@link ParameterizedClassDesc}
     */
    protected static ParameterizedClassDesc createParameterizedClassDesc(final Type type, final Map<TypeVariable<?>, Type> map) {
        final Class<?> rowClass = getActualClass(type, map);
        if (rowClass == null) {
            return null;
        }
        final ParameterizedClassDescImpl desc = new ParameterizedClassDescImpl(rowClass);
        final Type[] parameterTypes = getGenericParameters(type);
        if (parameterTypes == null) {
            return desc;
        }
        final ParameterizedClassDesc[] parameterDescs = new ParameterizedClassDesc[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; ++i) {
            parameterDescs[i] = createParameterizedClassDesc(parameterTypes[i], map);
        }
        desc.setArguments(parameterDescs);
        return desc;
    }

}
