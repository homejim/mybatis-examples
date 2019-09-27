package com.homejim.mybatis.plugin;

import java.lang.reflect.Field;

/**
 * 反射工具类，直接修改对象值
 */
public class ReflectUtil {
    /**
     * 获取指定对象的指定属性
     *
     * @param obj       指定对象
     * @param fieldName 指定属性名称
     * @return 指定属性
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        Object result = null;
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            field.setAccessible(true);
            try {
                result = field.get(obj);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取指定对象里面的指定属性对象
     *
     * @param obj       目标对象
     * @param fieldName 指定属性名称
     * @return 属性对象
     */
    private static Field getField(Object obj, String fieldName) {
        Field field = null;
        for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
                // do nothing
            }
        }
        return field;
    }

    /**
     * 设置指定对象的指定属性值
     *
     * @param obj        指定对象
     * @param fieldName  指定属性
     * @param fieldValue 指定属性值
     */
    public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
        Field field = ReflectUtil.getField(obj, fieldName);
        if (field != null) {
            try {
                field.setAccessible(true);
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
