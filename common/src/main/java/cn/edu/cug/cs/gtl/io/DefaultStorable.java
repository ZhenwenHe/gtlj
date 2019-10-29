package cn.edu.cug.cs.gtl.io;


import cn.edu.cug.cs.gtl.util.store.StorableObjectStoreUtil;
import cn.edu.cug.cs.gtl.util.store.StorableObjectStoreUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public interface DefaultStorable extends Storable {

    @Override
    default Object clone() {
        Storable storable = null;
        try {
            storable = this.getClass().newInstance();
            storable.loadFromByteArray(this.storeToByteArray());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storable;
    }

    @Override
    default boolean load(DataInput in) throws IOException {
        try {
            StorableObjectStoreUtil.load(in, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    default boolean store(DataOutput out) throws IOException {
        try {
            StorableObjectStoreUtil.store(out, this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    default long getByteArraySize() {
        return StorableObjectStoreUtil.getByteArraySize(this);
    }
}
