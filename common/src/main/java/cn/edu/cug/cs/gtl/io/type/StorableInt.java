package cn.edu.cug.cs.gtl.io.type;

import cn.edu.cug.cs.gtl.io.StorableComparable;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class StorableInt implements StorableComparable<StorableInt> {

    private static final long serialVersionUID = -3911801081295818379L;

    int value;

    public StorableInt(int value) {
        this.value = value;
    }

    public StorableInt() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Object clone() {
        return new StorableInt(this.value);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.value = in.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.value);
        return true;
    }

    @Override
    public int compareTo(@NotNull StorableInt o) {
        return 0;
    }

    public static byte[] encode(int v) {
        byte[] bs = new byte[4];
        bs[0] = (byte) ((v >>> 24) & 0xFF);
        bs[1] = (byte) ((v >>> 16) & 0xFF);
        bs[2] = (byte) ((v >>> 8) & 0xFF);
        bs[3] = (byte) ((v >>> 0) & 0xFF);
        return bs;
    }

    public static int decode(byte[] bs) {
        int ch1 = bs[0];
        int ch2 = bs[1];
        int ch3 = bs[2];
        int ch4 = bs[3];
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }
}
