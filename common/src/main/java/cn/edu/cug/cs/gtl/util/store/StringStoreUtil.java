package cn.edu.cug.cs.gtl.util.store;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringStoreUtil {

    public static void store(DataOutput out, String str) throws IOException {
        if (str == null) {
            out.writeInt(-1);
        }
        if (str.equals("")) {
            out.writeInt(0);
        }
        byte[] bs = str.getBytes();
        out.writeInt(bs.length);
        if (bs.length > 0) {
            out.write(bs);
        }
    }


    public static String load(DataInput in) throws IOException {
        int length = in.readInt();
        if (length == 0) return new String("");
        if (length == -1) return null;
        byte[] bs = new byte[length];
        in.readFully(bs, 0, length);
        return new String(bs, 0, length);
    }

    public static long getByteArraySize(String str) {
        return str.getBytes().length + CommonStoreUtil.INT_SIZE;
    }
}
