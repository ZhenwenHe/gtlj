package cn.edu.cug.cs.gtl.util.store;

import cn.edu.cug.cs.gtl.exception.StoreException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class MapStoreUtil extends ParameterizedTypeUtil {
    /**
     * 将指定对Map象存入DataOutput
     *
     * @param out DataOutput
     * @param map 指定的Map对象
     */
    public void store(DataOutput out, Map map) throws IOException {
        if (map == null) {
            out.writeInt(-1);
            return;
        } else {
            out.writeInt(map.size());
            Set keys = map.keySet();
            Type keyType = getParameterizedType(map.getClass(), 0);
            Type valueType = getParameterizedType(map.getClass(), 1);
            for (Object key : keys) {
                CommonStoreUtil.store(out, key, keyType);
                CommonStoreUtil.store(out, map.get(key), valueType);
            }
        }
    }

    /**
     * 从DataInput加载出一个Map对象
     *
     * @param in  DataInput
     * @param map 待加载的Map对象
     * @return 加载出的Map对象
     */
    public static Map load(DataInput in, Map map) throws IOException {
        int size = in.readInt();
        if (size == -1) {
            return null;
        } else {
            Type keyType = getParameterizedType(map.getClass(), 0);
            Type valueType = getParameterizedType(map.getClass(), 1);
            map.clear();
            for (int i = 0; i < size; i++) {
                map.put(CommonStoreUtil.load(in, null, keyType), CommonStoreUtil.load(in, null, valueType));
            }
        }
        return map;
    }

    public static Map load(DataInput in, Class<?> mapType) throws InstantiationException, IllegalAccessException, IOException, StoreException {
        return load(in, (Map) getInstance(mapType));
    }

    /**
     * 从DataInput加载出一个Map对象
     *
     * @param in      DataInput
     * @param map     待加载的Map对象
     * @param mapType map的类型
     * @return 加载出的Map对象
     */
    public Map load(DataInput in, Map map, Class<?> mapType) throws IOException, IllegalAccessException, InstantiationException, StoreException {
        if (map != null) {
            return load(in, map);
        } else {
            return load(in, mapType);
        }
    }

    public static <T> T getInstance(Class<T> mapType) throws IllegalAccessException, InstantiationException, StoreException {
        if (Map.class.isAssignableFrom(mapType) && isImplementedClass(mapType)) {
            return mapType.newInstance();
        } else if (mapType == Map.class) {
            return (T) new HashMap<>();
        } else {
            throw new StoreException(StoreException.MapInstance);
        }

    }

    /**
     * 获得指定Map对象序列化字节数组的大小
     *
     * @param map 指定的Map对象
     * @return 指定Map对象序列化字节数组的大小
     */
    public long getByteArraySize(Map map) {
        if (map == null) {
            return CommonStoreUtil.INT_SIZE;
        }
        Type keyType = getParameterizedType(map.getClass(), 0);
        Type valueType = getParameterizedType(map.getClass(), 1);

        long keySize = 0;
        long valueSize = 0;
        if (CommonStoreUtil.isConstantSizeType((Class<?>) keyType)) {
            keySize = CommonStoreUtil.getConstantTypeSize((Class<?>) keyType) * map.size();
        } else if (CommonStoreUtil.isPrimitiveType((Class<?>) keyType)) {
            keySize = CommonStoreUtil.getPrimitiveTypeSize((Class<?>) keyType) * map.size();
        } else {
            for (Object key : map.keySet()) {
                keySize += CommonStoreUtil.getByteArraySize(key);
            }
        }
        if (CommonStoreUtil.isConstantSizeType((Class<?>) valueType)) {
            valueSize = CommonStoreUtil.getConstantTypeSize((Class<?>) valueType) * map.size();
        } else if (CommonStoreUtil.isPrimitiveType((Class<?>) keyType)) {
            valueSize = CommonStoreUtil.getPrimitiveTypeSize((Class<?>) valueType) * map.size();
        } else {
            for (Object value : map.values()) {
                valueSize += CommonStoreUtil.getByteArraySize(value);
            }
        }
        return CommonStoreUtil.INT_SIZE + keySize + valueSize;
    }
}
