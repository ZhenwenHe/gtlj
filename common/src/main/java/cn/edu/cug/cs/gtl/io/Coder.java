package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.io.type.StorableNull;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.exception.CoderException;
import cn.edu.cug.cs.gtl.io.type.StorableNull;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class Coder<T> implements Serializable {

    private Class<T> type;

    public static <T> Coder<T> of(Class<T> clazz) {
        return new Coder<>(clazz);
    }

    public Coder(Class<T> type) {
        this.type = type;
    }


    public void encode(T value, OutputStream outStream) throws IOException {
        if (this.type.equals(String.class)) {
            StringUtils.write((String) value, outStream);
        } else {
            Storable v = (Storable) value;
            v.write(new DataOutputStream(outStream));
        }
    }

    public T decode(InputStream inStream) throws IOException {
        try {
            if (type == StorableNull.class) {
                // StorableNull has no default constructor
                return (T) StorableNull.get();
            }
            if (this.type.equals(String.class)) {
                return (T) StringUtils.read(inStream);
            } else {
                //每个实现了Serializable接口的类，必须具有缺省构造函数
                T t = type.getDeclaredConstructor().newInstance();
                ((Storable) t).read(new DataInputStream(inStream));
                return t;
            }

        } catch (InstantiationException
                | IllegalAccessException
                | NoSuchMethodException
                | InvocationTargetException e) {
            throw new CoderException("unable to deserialize record", e);
        }
    }

    @Override
    public Object clone() {
        return new Coder<>(this.type);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        int s = in.readInt();
        if (s == 0) return false;
        byte[] b = new byte[s];
        in.readFully(b);
        this.type = (Class<T>) ObjectUtils.loadFromByteArray(b);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        byte[] t = ObjectUtils.storeToByteArray(this.type);
        if (t == null) return false;
        out.writeInt(t.length);
        out.write(t);
        return false;
    }
}
