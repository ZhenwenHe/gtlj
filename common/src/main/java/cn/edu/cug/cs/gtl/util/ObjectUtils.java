package cn.edu.cug.cs.gtl.util;

import java.io.*;

public class ObjectUtils {

    public static Object load(DataInput in) throws IOException {
        try {
            int len = in.readInt();
            if (len == 0) return null;
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            Object oResult = ois.readObject();
            ois.close();
            return oResult;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int store(Object obj, DataOutput out) throws IOException {
        try {
            if (obj == null) {
                out.writeInt(0);
                return 4;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bs = baos.toByteArray();
            int len = bs.length;
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
            oos.close();
            return len + 4;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Object loadFromByteArray(byte[] data) throws IOException {
        try {
            if (data == null) return null;
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
            int i = ois.readInt();
            if (i == 0) return null;
            Object oResult = ois.readObject();
            ois.close();
            return oResult;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] storeToByteArray(Object obj) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            if (obj == null)
                oos.writeInt(0);
            else
                oos.writeInt(1);

            oos.writeObject(obj);
            byte[] bs = baos.toByteArray();
            oos.close();
            return bs;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getByteArraySize(Object obj) {
        try {
            if (obj == null) return 4;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            byte[] bs = baos.toByteArray();
            int len = bs.length + 4;
            oos.close();
            return len;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
