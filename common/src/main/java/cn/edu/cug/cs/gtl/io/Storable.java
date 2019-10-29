package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.Variant;

import java.io.*;

public interface Storable extends java.io.Serializable, Cloneable {
    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    Object clone();

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    boolean load(DataInput in) throws IOException;

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    boolean store(DataOutput out) throws IOException;

    /**
     * 对象序列化后的字节数，
     * 默认实现为将其写入一个字节数组中，然后返回该字节数
     * 这种实现方法消耗过大，如果一个对象的字节数是固定的，
     * 建议重载此方法
     *
     * @return 返回计算得到的字节数
     */
    default long getByteArraySize() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            store(dos);
            dos.flush();
            return baos.size();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 从其它同类对象拷贝内容填充本对象
     *
     * @param i 其它任何实现了Serializable接口的对象
     */
    default void copyFrom(Object i) {
        if (i instanceof Storable) {
            Storable s = (Storable) i;
            try {
                this.loadFromByteArray(s.storeToByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将本对象的信息复制到相同的对象i中，并返回i
     * 如果i为空，则调用this.clone()，并返回clone的对象
     * 如果对象类型不匹配，则返回null
     *
     * @param i 其它任何实现了Serializable接口的对象
     */
    default void copyTo(Object i) {
        if (i instanceof Storable) {
            ((Storable) i).copyFrom(this);
        }
    }

    /**
     * 从输入流中读取数据填充对象
     *
     * @param in 输入流
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    default boolean read(InputStream in) throws IOException {
        byte[] size = new byte[4];
        in.read(size);
        int len = Variant.byteArrayToInteger(size);
        if (len > 0) {
            byte[] data = new byte[len];
            in.read(data);
            return loadFromByteArray(data);
        } else
            return false;
    }

    /**
     * 向输出流中写出数据
     *
     * @param out 输出流
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    default boolean write(OutputStream out) throws IOException {
        byte[] data = storeToByteArray();
        byte[] size = Variant.integerToByteArray(data.length);
        out.write(size);
        out.write(data);
        return true;
    }

    /**
     * 将对象数据成员写成一个字节数组
     *
     * @return 返回字节数组，通过loadFromByteArray函数解析可以恢复对象数据成员
     * @throws IOException
     */
    default byte[] storeToByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        store(dos);
        dos.flush();

        byte[] bs = baos.toByteArray();
        dos.close();
        return bs;
    }

    /**
     * 从字节数组中读取对象数据成员信息，并填充对象
     *
     * @param bs 通过函数storeToByteArray生成的字节数组
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    default boolean loadFromByteArray(byte[] bs) throws IOException {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bs));
        boolean b = load(dis);
        dis.close();
        return b;
    }

    /**
     * 将对象转成以SEMICOLON分隔的字节字符串，一个byte转换成字符串最多占4个字符，所以该字符串的最大长度是字节数组长度的4倍
     *
     * @return
     * @throws IOException
     */
    default String storeToByteString() throws IOException {
        byte[] t = storeToByteArray();
        StringBuilder sb = new StringBuilder(t.length * 4);
        int i = 0;
        for (i = 0; i < t.length - 1; ++i) {
            sb.append(Byte.toString(t[i]));
            sb.append(FileDataSplitter.SEMICOLON.getDelimiter());
        }
        sb.append(Byte.toString(t[i]));
        sb.trimToSize();
        return sb.toString();
    }

    /**
     * 与toByteString成对使用，用于从字节字符串中解析出对象
     *
     * @param str
     * @return
     * @throws IOException
     */
    default boolean loadFromByteString(String str) throws IOException {
        String[] ss = str.split(FileDataSplitter.SEMICOLON.getDelimiter());
        byte[] t = new byte[ss.length];
        int i = 0;
        for (String s : ss) {
            t[i] = Byte.parseByte(s);
            ++i;
        }
        return loadFromByteArray(t);
    }
}
