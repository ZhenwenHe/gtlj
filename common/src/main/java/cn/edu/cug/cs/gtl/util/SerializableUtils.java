package cn.edu.cug.cs.gtl.util;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SerializableUtils {
    public static Serializable load(DataInput input, Class<?> cls)
            throws IOException, InstantiationException, IllegalAccessException {
        int b = input.readInt();
        if (b != 0) {
            Serializable s = (Serializable) cls.newInstance();
            return s;
        }
        return null;
    }

    public static void store(DataOutput output, Serializable s) throws IOException {
        if (s == null)
            output.writeInt(0);
        else {
            output.writeInt(1);
            s.store(output);
        }
    }
}
