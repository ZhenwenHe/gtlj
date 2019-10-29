package cn.edu.cug.cs.gtl.util.store;

import cn.edu.cug.cs.gtl.annotation.StoreFiled;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;

public class StorableObjectStoreUtil {
    public static Storable load(DataInput in, Storable obj) throws IllegalAccessException, IOException {
        //如果当且对象是空
        if (in.readBoolean()) {
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(StoreFiled.class)) {
                field.set(obj, CommonStoreUtil.load(in, field.get(obj), field.getType()));
            }
        }
        return null;
    }

    public static void store(DataOutput out, Storable obj) throws IllegalAccessException, IOException {
        //如果当前对象是空
        if (obj == null) {
            out.writeBoolean(true);
        } else {
            out.writeBoolean(false);
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(StoreFiled.class)) {
                CommonStoreUtil.store(out, field.get(obj), field.getType());
            }

        }
    }

    public static long getByteArraySize(Storable obj) {
        if (CommonStoreUtil.isConstantSizeType(obj.getClass())) {
            return CommonStoreUtil.getConstantTypeSize(obj.getClass());
        } else {
            //TODO 继续完成
        }
        return -1;
    }
}
