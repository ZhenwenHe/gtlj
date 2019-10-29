package cn.edu.cug.cs.gtl.util.store;

import cn.edu.cug.cs.gtl.annotation.ConstantSize;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CommonStoreUtil {
    private static Map<Class<?>, Long> constantSizeMap = new HashMap<>();
    public static final int BYTE_SIZE = 1;
    public static final int SHORT_SIZE = 2;
    public static final int INT_SIZE = 4;
    public static final int LONG_SIZE = 8;
    public static final int FLOAT_SIZE = 4;
    public static final int DOUBLE_SIZE = 8;
    public static final int CHAR_SIZE = 2;
    public static final int BOOL_SIZE = 2;


    public static boolean isPrimitiveType(Class<?> type) {
        if (type == null) {
            return false;
        }
        if (type.isPrimitive()) {
            return true;
        } else if (type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == Character.class || type == Boolean.class) {
            return true;
        }
        return false;
    }

    public static boolean isConstantSizeType(Class<?> type) {
        if (type == null) {
            return false;
        }
        if (constantSizeMap.containsKey(type)) {
            //如果constantSizeMap中含有type的记录
            if (constantSizeMap.get(type) != null) {
                //如果constantSizeMap中type的记录不等于null
                return true;
            } else {
                //如果constantSizeMap中type的记录等于null
                return false;
            }
        } else {
            //如果constantSizeMap中没有type的记录
            if (type.isAnnotationPresent(ConstantSize.class)) {
                //如果type通过ConstantSize标签声明过
                ConstantSize constantSizeAnnotation = type.getAnnotation(ConstantSize.class);
                constantSizeMap.put(type, constantSizeAnnotation.value());
                return true;
            } else {
                //如果type没有通过ConstantSize标签声明过,暂时在constantSizeMap中将type标记为null
                constantSizeMap.put(type, null);
                long constantSize = 0;
                //遍历type中的所有成员属性field
                for (Field field : type.getDeclaredFields()) {
                    if (isConstantSizeType(field.getType())) {
                        //如果field是固定大小的类型
                        constantSize += getConstantTypeSize(field.getType());
                    } else if (isPrimitiveType(field.getType())) {
                        //如果field是原始基本数据类型
                        constantSize += getPrimitiveTypeSize(field.getType());
                    } else {
                        //如果field不是固定大小的类型也不是是原始基本数据类型
                        return false;
                    }
                }
                constantSizeMap.put(type, constantSize);
                return true;
            }
        }

    }

    public static Long getConstantTypeSize(Class<?> type) {
        if (isConstantSizeType(type)) {
            return constantSizeMap.get(type);
        }
        return null;
    }

    public static Integer getPrimitiveTypeSize(Class<?> type) {
        //整型
        if (type == byte.class || type == Byte.class) {
            return BYTE_SIZE;
        }
        if (type == short.class || type == Short.class) {
            return SHORT_SIZE;
        }
        if (type == int.class || type == Integer.class) {
            return INT_SIZE;
        }
        if (type == long.class || type == Long.class) {
            return LONG_SIZE;
        }

        //浮点型
        if (type == float.class || type == Float.class) {
            return FLOAT_SIZE;
        }
        if (type == double.class || type == Double.class) {
            return DOUBLE_SIZE;
        }

        //字符型
        if (type == char.class || type == Character.class) {
            return CHAR_SIZE;
        }

        //布尔型
        if (type == boolean.class || type == Boolean.class) {
            return BOOL_SIZE;
        }
        return null;
    }

    /**
     * 将指定对象存入DataOutput
     *
     * @param out  DataOutput
     * @param obj  指定的对象
     * @param type 指定对象的类型
     */
    public static void store(DataOutput out, Object obj, Type type) throws IOException {
        if (obj != null) {
            type = obj.getClass();
        }
        if (isPrimitiveType((Class<?>) type)) {

        }
    }

    /**
     * 从DataInput加载出对象
     *
     * @param in   DataInput
     * @param obj  加载出的对象
     * @param type 加载对象的类型
     * @return 加载出的对象
     */

    public static <T> T load(DataInput in, Object obj, Type type) {
        return null;
    }

    /**
     * 获得指定对象序列化字节数组的大小
     *
     * @param obj 指定的对象
     * @return 指定对象序列化字节数组的大小
     */
    public static long getByteArraySize(Object obj) {
        return 0;
    }

}
