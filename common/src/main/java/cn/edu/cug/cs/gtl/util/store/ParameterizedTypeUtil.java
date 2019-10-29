package cn.edu.cug.cs.gtl.util.store;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeUtil {
    public static Type getParameterizedType(Class<?> cls, int index) {
        return ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public static boolean isImplementedClass(Class<?> cls) {
        return !(cls.isEnum() || cls.isInterface() || Modifier.isAbstract(cls.getModifiers()));
    }
}
