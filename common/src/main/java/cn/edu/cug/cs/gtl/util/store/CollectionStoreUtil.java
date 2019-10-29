package cn.edu.cug.cs.gtl.util.store;

import cn.edu.cug.cs.gtl.exception.StoreException;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CollectionStoreUtil extends ParameterizedTypeUtil {
    /**
     * 将指定Collection存入DataOutput
     *
     * @param out        DataOutput
     * @param collection 指定的Collection
     */
    public static <T> void store(DataOutput out, Collection<T> collection) throws IOException {
        //如果collection是空输出-1
        if (collection == null) {
            out.writeInt(-1);
            return;
        } else {
            Type type = getParameterizedType(collection.getClass(), 0);
            //输出collection的长度
            out.writeInt(collection.size());
            for (Object element : collection) {
                CommonStoreUtil.store(out, element, type);
            }
        }
    }

    /**
     * 从DataInput加载出一个Collection
     *
     * @param in         DataInput
     * @param collection 加载出的Collection
     * @return 加载出的Collection
     */
    public static <T> Collection<T> load(DataInput in, Collection<T> collection) throws IOException {
        int size = in.readInt();
        if (size == -1) {
            return null;
        } else {
            Type type = getParameterizedType(collection.getClass(), 0);
            collection.clear();
            for (int i = 0; i < size; i++) {
                collection.add(CommonStoreUtil.load(in, null, type));
            }
            return collection;
        }

    }

    public static Collection load(DataInput in, Class<?> collectionType) throws InstantiationException, IllegalAccessException, IOException, StoreException {
        return load(in, (Collection) getInstance(collectionType));
    }

    public static <T> Collection<T> load(DataInput in, Collection<T> collection, Class<?> collectionType) throws IOException, IllegalAccessException, InstantiationException, StoreException {
        if (collection != null) {
            return load(in, collection);
        } else {
            return load(in, collectionType);
        }
    }

    public static <T> T getInstance(Class<T> collectionType) throws IllegalAccessException, InstantiationException, StoreException {
        if (Collection.class.isAssignableFrom(collectionType) && isImplementedClass(collectionType)) {
            return collectionType.newInstance();
        } else if (collectionType == List.class || collectionType == Collection.class) {
            return (T) new ArrayList<>();
        } else if (collectionType == Set.class) {
            return (T) new HashSet<>();
        } else {
            throw new StoreException(StoreException.CollectionInstance);
        }

    }

    /**
     * 获得指定Collection序列化字节数组的大小
     *
     * @param collection 指定的Collection
     * @return 指定Collection序列化字节数组的大小
     */
    public static long getByteArraySize(Collection collection) {
        if (collection == null) {
            return CommonStoreUtil.INT_SIZE;
        }
        Type type = getParameterizedType(collection.getClass(), 0);
        if (CommonStoreUtil.isConstantSizeType((Class<?>) type)) {
            return CommonStoreUtil.INT_SIZE + CommonStoreUtil.getConstantTypeSize((Class<?>) type) * collection.size();
        } else if (CommonStoreUtil.isPrimitiveType((Class<?>) type)) {
            return CommonStoreUtil.INT_SIZE + CommonStoreUtil.getPrimitiveTypeSize((Class<?>) type) * collection.size();
        } else {
            long size = CommonStoreUtil.INT_SIZE;
            for (Object element : collection) {
                size += CommonStoreUtil.getByteArraySize(element);
            }
            return size;
        }
    }
}
