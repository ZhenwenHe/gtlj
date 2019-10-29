package cn.edu.cug.cs.gtl.util.store;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;

public class ArrayStoreUtil {
    /**
     * 将指定数组存入DataOutput
     *
     * @param out   DataOutput
     * @param array 指定的数组
     */
    public void store(DataOutput out, Object array) throws IOException {
        if (array == null) {
            out.writeInt(-1);
            return;
        }
        int length = Array.getLength(array);
        out.write(length);
        for (int i = 0; i < length; i++) {
            CommonStoreUtil.store(out, Array.get(array, i), array.getClass().getComponentType());
        }
    }

    /**
     * 从DataInput加载出数组
     *
     * @param in            DataInput
     * @param componentType 数组元素的类型
     * @return 加载出的数组
     */
    public static Object load(DataInput in, Class<?> componentType) throws IOException, IllegalAccessException, InstantiationException {
        int length = in.readInt();
        if (length == -1) {
            return null;
        }
        Object array = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(array, i, CommonStoreUtil.load(in, null, componentType));
        }
        return array;
    }

    /**
     * 获得指定数组序列化字节数组的大小
     *
     * @param array 指定的数组
     * @return 指定数组序列化字节数组的大小
     */
    public long getByteArraySize(Object array) {
        if (array.getClass().isArray()) {
            int length = Array.getLength(array);
            Class<?> componentType = array.getClass().getComponentType();
            long typeSize = -1;
            //如果是基本数据类型
            if (CommonStoreUtil.isPrimitiveType(componentType)) {
                typeSize = CommonStoreUtil.getPrimitiveTypeSize(componentType);
                //如果是实现了Storable接口
            } else if (Storable.class.isAssignableFrom(componentType) || componentType == String.class) {
                //如果该类是固定大小的类
                if (CommonStoreUtil.isConstantSizeType(componentType)) {
                    typeSize = CommonStoreUtil.getConstantTypeSize(componentType);
                } else if (componentType == String.class) {
                    typeSize = 0;
                    for (int i = 0; i < length; i++) {
                        String element = (String) Array.get(array, i);
                        typeSize += StringUtils.getByteArraySize(element);
                    }
                } else if (componentType == Storable.class) {
                    typeSize = 0;
                    for (int i = 0; i < length; i++) {
                        Storable element = (Storable) Array.get(array, i);
                        typeSize += element.getByteArraySize();
                    }
                }
            }
            if (typeSize >= 0 && length >= 0) {
                return length * typeSize;
            }
        }
        return -1;
    }
}
