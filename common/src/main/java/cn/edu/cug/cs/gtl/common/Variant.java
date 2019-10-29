package cn.edu.cug.cs.gtl.common;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by hadoop on 17-2-18.
 */

/**
 * 自定义的泛型变量类；可以是任何类型变量；
 * 包括一个object对象value来存放变量内容，一个int类型 type表示变量类型；
 */
public class Variant implements Serializable, Comparable<Variant> {
    private static final long serialVersionUID = 1L;

    public final static int UNKNOWN = 0;//empty value
    public final static int BOOLEAN = 1;
    public final static int CHARACTER = 2;
    public final static int BYTE = 3;
    public final static int SHORT = 4;
    public final static int INTEGER = 5;
    public final static int LONG = 6;
    public final static int FLOAT = 7;
    public final static int DOUBLE = 8;
    public final static int STRING = 9;
    public final static int BYTES = 10;
    public final static int CHARACTERS = 11;
    public final static int BOOLEANS = 12;
    public final static int SHORTS = 13;
    public final static int INTEGERS = 14;
    public final static int LONGS = 15;
    public final static int FLOATS = 16;
    public final static int DOUBLES = 17;
    public final static int STRINGS = 18;
    public final static int OBJECT = 19;//user define type

    protected int type;
    protected Object value;

    public Variant() {
        this.type = UNKNOWN;
        this.value = null;
    }

    /**
     * 构造函数，有个重载函数，根据传入的任意变量值，来初始化type 和value；
     *
     * @param v 可以是任意类型变量；
     */
    public Variant(boolean v) {
        reset(v);
    }

    public Variant(char v) {
        reset(v);
    }

    public Variant(byte v) {
        reset(v);
    }

    public Variant(short v) {
        reset(v);
    }

    public Variant(int v) {
        reset(v);
    }

    public Variant(long v) {
        reset(v);
    }

    public Variant(float v) {
        reset(v);
    }

    public Variant(double v) {
        reset(v);
    }

    public Variant(String v) {
        reset(v);
    }

    public Variant(byte[] v) {
        reset(v);
    }

    public Variant(boolean[] v) {
        reset(v);
    }

    public Variant(char[] v) {
        reset(v);
    }

    public Variant(short[] v) {
        reset(v);
    }

    public Variant(int[] v) {
        reset(v);
    }

    public Variant(long[] v) {
        reset(v);
    }

    public Variant(float[] v) {
        reset(v);
    }

    public Variant(double[] v) {
        reset(v);
    }

    public Variant(String[] v) {
        reset(v);
    }


    public Variant(Variant v) {
        reset(v.type, v.value);
    }

    /**
     * 传入为用户自定义类型变量的构造函数
     *
     * @param v
     */
    public Variant(Object v) {
        reset(v);
    }

    /**
     * 构造函数，同时传入type和value；
     *
     * @param type  数据类型
     * @param value 数据值
     */
    public Variant(int type, Object value) {
        reset(type, value);
    }

    /**
     * 从输入流读取数据，有多个类似函数，可以满足任意类型数据；
     *
     * @param dataInput 为输入流
     * @return 为读取的字节数组
     */
    public static byte[] readBytes(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                byte[] r = new byte[c];
                dataInput.readFully(r, 0, c);
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入输出流；有多个类似函数，能满足多种类型数据的写入
     *
     * @param dataOutput 为输出流
     * @param data       为要写入输出流的字节数组
     */
    public static void writeBytes(DataOutput dataOutput, byte[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                dataOutput.write(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean[] readBooleans(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                boolean[] r = new boolean[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readBoolean();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeBooleans(DataOutput dataOutput, boolean[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeBoolean(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static char[] readChars(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                char[] r = new char[c];
                for (int i = 0; i < c; ++i)
                    r[i] = dataInput.readChar();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeChars(DataOutput dataOutput, char[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            for (int i = 0; i < c; ++i)
                dataOutput.writeChar(data[i]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readString(DataInput dataInput) throws IOException {

        return StringUtils.load(dataInput);
    }

    public static void writeString(DataOutput dataOutput, String s) throws IOException {
        StringUtils.store(s, dataOutput);
    }

    public static short[] readShorts(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                short[] r = new short[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readShort();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeShorts(DataOutput dataOutput, short[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeShort(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] readIntegers(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                int[] r = new int[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readInt();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeIntegers(DataOutput dataOutput, int[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeInt(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static long[] readLongs(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                long[] r = new long[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readLong();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeLongs(DataOutput dataOutput, long[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeLong(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float[] readFloats(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                float[] r = new float[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readFloat();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeFloats(DataOutput dataOutput, float[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeFloat(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[] readDoubles(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                double[] r = new double[c];
                for (int i = 0; i < c; ++i) r[i] = dataInput.readDouble();
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeDoubles(DataOutput dataOutput, double[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) dataOutput.writeDouble(data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] readStrings(DataInput dataInput) {
        try {
            int c = dataInput.readInt();
            if (c > 0) {
                String[] r = new String[c];
                for (int i = 0; i < c; ++i) r[i] = readString(dataInput);
                return r;
            } else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeStrings(DataOutput dataOutput, String[] data) {
        try {
            int c = data.length;
            dataOutput.writeInt(c);
            if (c > 0) {
                for (int i = 0; i < c; ++i) writeString(dataOutput, data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(DataInput dataInput) {
        try {
            return ObjectUtils.load(dataInput);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeObject(DataOutput dataOutput, Object data) {
        try {
            ObjectUtils.store(data, dataOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将任意类型数据写成字节数组，有多个类似函数，满足各种不同数据类型；
     *
     * @param v 输入的数据；
     * @return 返回一个字节数组；
     */
    public static byte[] integerToByteArray(int v) {
        return ByteBuffer.allocate(4).putInt(v).array();
    }

    /**
     * 将字节数组中的数据还原，有多个类似函数，能满足各种不同数据类型
     *
     * @param bytes
     * @return 原数据
     */
    public static int byteArrayToInteger(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getInt();
    }

    public static byte[] longToByteArray(long v) {
        return ByteBuffer.allocate(8).putLong(v).array();
    }

    public static long byteArrayToLong(byte[] s) {
        return ByteBuffer.wrap(s).getLong();
    }

    public static float byteArrayToFloat(byte[] s) {
        return Float.intBitsToFloat(byteArrayToInteger(s));
    }

    public static byte[] floatToByteArray(float v) {
        return integerToByteArray(Float.floatToIntBits(v));
    }

    public static double byteArrayToDouble(byte[] s) {
        return Double.longBitsToDouble(byteArrayToLong(s));
    }

    public static byte[] doubleToByteArray(double v) {
        return longToByteArray(Double.doubleToLongBits(v));
    }

    /**
     * 判断是否为数值类型数据；
     *
     * @return
     */
    public boolean isNumber() {
        return this.type >= BYTE && this.type <= DOUBLE;
    }

    /**
     * 判断是否为数组类型数据；
     *
     * @return
     */
    public boolean isArray() {
        return this.type >= BYTES && this.type <= STRINGS;
    }

    /**
     * 判空函数；
     *
     * @return
     */
    public boolean isEmpty() {
        return this.value == null;
    }

    /**
     * 重写的判断是否相等的函数；先判断类型是否相同，若不同返回false，若相同进一步判断value是否相等；
     *
     * @param o 另一个Variant对象；
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variant)) return false;

        Variant variant = (Variant) o;

        if (this.type != variant.type) return false;

        switch (type) {
            case UNKNOWN: {
                return true;
            }
            case BOOLEAN:
            case CHARACTER:
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG:
            case FLOAT:
            case DOUBLE:
            case STRING: {
                return this.value.equals(variant.value);
            }
            case BYTES: {
                return Arrays.equals((byte[]) this.value, (byte[]) variant.value);
            }
            case CHARACTERS: {
                return Arrays.equals((char[]) this.value, (char[]) variant.value);
            }
            case BOOLEANS: {
                return Arrays.equals((boolean[]) this.value, (boolean[]) variant.value);
            }
            case SHORTS: {
                return Arrays.equals((short[]) this.value, (short[]) variant.value);
            }
            case INTEGERS: {
                return Arrays.equals((int[]) this.value, (int[]) variant.value);
            }
            case LONGS: {
                return Arrays.equals((long[]) this.value, (long[]) variant.value);
            }
            case FLOATS: {
                return Arrays.equals((float[]) this.value, (float[]) variant.value);
            }
            case DOUBLES: {
                return Arrays.equals((double[]) this.value, (double[]) variant.value);
            }
            case STRINGS: {
                String[] ss1 = (String[]) this.value;
                String[] ss2 = (String[]) variant.value;
                if (ss1.length != ss2.length) return false;
                for (int i = 0; i < ss1.length; i++) {
                    if (ss1[i].equals(ss2[i]) == false)
                        return false;
                }
                return true;
            }
            default:
                return this.value.equals(variant.value);
        }

    }

    /**
     * 返回当前对象的哈希编码；
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = type;
        result = 31 * result + value.hashCode();
        return result;
    }

    /**
     * 返回当前对象的克隆；
     *
     * @return
     */
    @Override
    public Object clone() {
        return new Variant(this);
    }

    /**
     * 将对象转换为string；
     *
     * @return
     */
    @Override
    public String toString() {
        if (this.type == UNKNOWN)
            return "UNKNOWN";
        else
            return strValue();
    }

    /**
     * 获得本对象的数据类型 type；
     *
     * @return type；
     */
    public int getType() {
        return this.type;
    }


    /**
     * 返回本对象的值value，有多个类似函数，能满足不同数据类型；
     *
     * @return value；
     */
    public Object getValue() {
        return this.value;
    }


    public long longValue() {
        long b = Long.MAX_VALUE;
        switch (type) {
            case UNKNOWN: {
                assert false;
                break;
            }
            case BOOLEAN: {
                Boolean v = (Boolean) this.value;
                if (v)
                    b = 1;
                else
                    b = 0;
                return b;
            }
            case CHARACTER: {
                Character v = (Character) this.value;
                b = (long) v.charValue();
                return b;
            }
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG: {
                java.lang.Number v = (java.lang.Number) this.value;
                return v.longValue();
            }
            case FLOAT:
            case DOUBLE: {
                java.lang.Number v = (java.lang.Number) this.value;
                b = (long) v.doubleValue();
                return b;
            }
            case STRING: {
                String s = (String) this.value;
                b = Long.parseLong(s);
                return b;
            }
            case BYTES:
            case CHARACTERS:
            case BOOLEANS:
            case SHORTS:
            case INTEGERS:
            case LONGS:
            case FLOATS:
            case DOUBLES:
            case STRINGS:
            case OBJECT: {
                assert false;
            }
        }
        return b;
    }

    public boolean booleanValue() {
        switch (type) {
            case UNKNOWN: {
                assert false;
                return false;
            }
            case BOOLEAN: {
                return (Boolean) this.value;
            }
            case CHARACTER: {
                Character v = (Character) this.value;
                if (v.charValue() == 't' || v.charValue() == 'T')
                    return true;
                if (v.charValue() == 'f' || v.charValue() == 'F')
                    return false;
                assert false;
                return false;
            }
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG: {
                java.lang.Number v = (java.lang.Number) this.value;
                return v.longValue() != 0;
            }
            case FLOAT:
            case DOUBLE: {
                java.lang.Number v = (java.lang.Number) this.value;
                return v.doubleValue() != 0;
            }
            case STRING: {
                String s = (String) this.value;
                if (s.toLowerCase().equals("true"))
                    return true;
                if (s.toLowerCase().equals("false"))
                    return false;

                assert false;
                return false;
            }
            case BYTES:
            case CHARACTERS:
            case BOOLEANS:
            case SHORTS:
            case INTEGERS:
            case LONGS:
            case FLOATS:
            case DOUBLES:
            case STRINGS:
            case OBJECT: {
                assert false;
                return false;
            }
        }
        return false;
    }

    public double doubleValue() {
        double b = Double.MAX_VALUE;
        switch (type) {
            case UNKNOWN: {
                assert false;
                return b;
            }
            case BOOLEAN: {
                Boolean v = (Boolean) this.value;
                if (v)
                    b = 1;
                else
                    b = 0;
                return b;
            }
            case CHARACTER: {
                Character v = (Character) this.value;
                b = (int) v.charValue();
                return b;
            }
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG: {
                java.lang.Number v = (java.lang.Number) this.value;
                return (double) v.longValue();
            }
            case FLOAT:
            case DOUBLE: {
                java.lang.Number v = (java.lang.Number) this.value;
                b = v.doubleValue();
                return b;
            }
            case STRING: {
                String s = (String) this.value;
                b = Double.parseDouble(s);
                return b;
            }
            case BYTES:
            case CHARACTERS:
            case BOOLEANS:
            case SHORTS:
            case INTEGERS:
            case LONGS:
            case FLOATS:
            case DOUBLES:
            case STRINGS:
            case OBJECT: {
                assert false;
                return Double.MAX_VALUE;
            }
        }
        return b;
    }

    public char charValue() {
        char b = Character.MAX_VALUE;
        switch (type) {
            case UNKNOWN: {
                assert false;
                return b;
            }
            case BOOLEAN: {
                Boolean v = (Boolean) this.value;
                if (v)
                    b = 't';
                else
                    b = 'f';
                return b;
            }
            case CHARACTER: {
                Character v = (Character) this.value;
                return v.charValue();
            }
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG: {
                java.lang.Number v = (java.lang.Number) this.value;
                b = (char) v.longValue();
                return b;
            }
            case FLOAT:
            case DOUBLE: {
                java.lang.Number v = (java.lang.Number) this.value;
                b = (char) v.doubleValue();
                return b;
            }
            case STRING: {
                String s = (String) this.value;
                return s.charAt(0);
            }
            case BYTES:
            case CHARACTERS:
            case BOOLEANS:
            case SHORTS:
            case INTEGERS:
            case LONGS:
            case FLOATS:
            case DOUBLES:
            case STRINGS:
            case OBJECT: {
                assert false;
                return b;
            }
        }
        return b;
    }

    /**
     * UNKNOWN return null
     * BOOLEAN-CHARACTERS ： the implementation called String function valueOf
     * BOOLEANS-STRINGS: call StringBuilder fn: append and toString
     */

    public String strValue() {
        switch (type) {
            case UNKNOWN: {
                return null;
            }
            case BOOLEAN: {
                return String.valueOf(((Boolean) this.value).booleanValue());
            }
            case CHARACTER: {
                return String.valueOf(((Character) this.value).charValue());
            }
            case BYTE:
            case SHORT:
            case INTEGER:
            case LONG: {
                return String.valueOf(((Number) this.value).longValue());
            }
            case FLOAT:
            case DOUBLE: {
                return String.valueOf(((Number) this.value).doubleValue());
            }
            case STRING: {
                return (String) this.value;
            }
            case BYTES: {
                return String.valueOf((byte[]) this.value);
            }
            case CHARACTERS: {
                return String.valueOf((char[]) this.value);
            }
            case BOOLEANS: {
                StringBuilder sb = new StringBuilder(1024);
                boolean[] vv = (boolean[]) this.value;
                for (boolean b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case SHORTS: {
                StringBuilder sb = new StringBuilder(1024);
                short[] vv = (short[]) this.value;
                for (short b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case INTEGERS: {
                StringBuilder sb = new StringBuilder(1024);
                int[] vv = (int[]) this.value;
                for (int b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case LONGS: {
                StringBuilder sb = new StringBuilder(1024);
                long[] vv = (long[]) this.value;
                for (long b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case FLOATS: {
                StringBuilder sb = new StringBuilder(1024);
                float[] vv = (float[]) this.value;
                for (float b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case DOUBLES: {
                StringBuilder sb = new StringBuilder(1024);
                double[] vv = (double[]) this.value;
                for (double b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case STRINGS: {
                StringBuilder sb = new StringBuilder(1024);
                String[] vv = (String[]) this.value;
                for (String b : vv)
                    sb.append(b);
                return sb.toString();
            }
            case OBJECT: {
                return this.value.toString();
            }
        }
        return null;
    }

    public short shortValue() {
        return (short) longValue();
    }

    public int intValue() {
        return (int) longValue();
    }

    public float floatValue() {
        return (float) doubleValue();
    }

    public byte byteValue() {
        return (byte) longValue();
    }

    /**
     * 重设对象值函数，有多个重载，可以传入多种不同类型变量；
     *
     * @param v
     */
    public void reset(boolean v) {
        this.value = new Boolean(v);
        this.type = BOOLEAN;
    }

    public void reset(char v) {
        this.value = new Character(v);
        this.type = CHARACTER;
    }

    public void reset(byte v) {
        this.value = new Byte(v);
        this.type = BYTE;
    }

    public void reset(short v) {
        this.value = new Short(v);
        this.type = SHORT;
    }

    public void reset(int v) {
        this.value = new Integer(v);
        this.type = INTEGER;
    }

    public void reset(long v) {
        this.value = new Long(v);
        this.type = LONG;
    }

    public void reset(float v) {
        this.value = new Float(v);
        this.type = FLOAT;
    }

    public void reset(double v) {
        this.value = new Double(v);
        this.type = DOUBLE;
    }

    public void reset(String v) {
        this.value = new String(v);
        this.type = STRING;
    }

    public void reset(byte[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = BYTES;
    }

    public void reset(boolean[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = BOOLEANS;
    }

    public void reset(char[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = CHARACTERS;
    }

    public void reset(short[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = SHORTS;
    }

    public void reset(int[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = INTEGERS;
    }

    public void reset(long[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = LONGS;
    }

    public void reset(float[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = FLOATS;
    }

    public void reset(double[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = DOUBLES;
    }

    public void reset(String[] v) {
        this.value = Arrays.copyOf(v, v.length);
        this.type = STRINGS;
    }

    /**
     * 重设对象值函数，通过给定的type判断value类型；
     *
     * @param type  数据类型
     * @param value 数据值
     */
    public void reset(int type, Object value) {
        switch (type) {
            case UNKNOWN: {
                this.type = UNKNOWN;
                this.value = null;
                break;
            }
            case BOOLEAN: {
                reset(((Boolean) value).booleanValue());
                break;
            }
            case CHARACTER: {
                reset(((Character) value).charValue());
                break;
            }
            case BYTE: {
                reset(((Byte) value).byteValue());
                break;
            }
            case SHORT: {
                reset(((Short) value).shortValue());
                break;
            }
            case INTEGER: {
                reset(((Integer) value).intValue());
                break;
            }
            case LONG: {
                reset(((Long) value).longValue());
                break;
            }
            case FLOAT: {
                reset(((Float) value).floatValue());
                break;
            }
            case DOUBLE: {
                reset(((Double) value).doubleValue());
                break;
            }
            case STRING: {
                reset((String) value);
                break;
            }
            case BYTES: {
                reset((byte[]) value);
                break;
            }
            case CHARACTERS: {
                reset((char[]) value);
                break;
            }
            case BOOLEANS: {
                reset((boolean[]) value);
                break;
            }
            case SHORTS: {
                reset((short[]) value);
                break;
            }
            case INTEGERS: {
                reset((int[]) value);
                break;
            }
            case LONGS: {
                reset((long[]) value);
                break;
            }
            case FLOATS: {
                reset((float[]) value);
                break;
            }
            case DOUBLES: {
                reset((double[]) value);
                break;
            }
            case STRINGS: {
                reset((String[]) value);
                break;
            }
            default: {
                this.value = value;
                this.type = type;
            }
        }
    }

    /**
     * 重设对象值函数，只给出数据值，自己判断数据类型，并给type赋值；
     *
     * @param value
     */
    public void reset(Object value) {
        if (value instanceof Boolean) {
            reset(((Boolean) value).booleanValue());
        } else if (value instanceof Byte) {
            reset(((Byte) value).byteValue());
        } else if (value instanceof Character) {
            reset(((Character) value).charValue());
        } else if (value instanceof Short) {
            reset(((Short) value).shortValue());
        } else if (value instanceof Integer) {
            reset(((Integer) value).intValue());
        } else if (value instanceof Long) {
            reset(((Long) value).longValue());
        } else if (value instanceof Float) {
            reset(((Float) value).floatValue());
        } else if (value instanceof Double) {
            reset(((Double) value).doubleValue());
        } else if (value instanceof String) {
            reset((String) value);
        } else if (value instanceof byte[]) {
            reset((byte[]) value);
        } else if (value instanceof boolean[]) {
            reset((boolean[]) value);
        } else if (value instanceof char[]) {
            reset((char[]) value);
        } else if (value instanceof short[]) {
            reset((short[]) value);
        } else if (value instanceof int[]) {
            reset((int[]) value);
        } else if (value instanceof long[]) {
            reset((long[]) value);
        } else if (value instanceof float[]) {
            reset((float[]) value);
        } else if (value instanceof double[]) {
            reset((double[]) value);
        } else if (value instanceof String[]) {
            reset((String[]) value);
        } else if (value instanceof Variant) {
            reset((Variant) value);
        } else {
            reset(OBJECT, value);
        }
    }

    /**
     * 两个对象作比较，分两种情况，
     * 两个对象都是数值型对象，转换为double进行比较；
     * 否则均转换为string进行比较，调用String的比较函数；
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Variant o) {
        if (o.isNumber() && this.isNumber()) {
            double d1 = this.doubleValue();
            double d2 = o.doubleValue();
            if (d1 > d2) return 1;
            else if (d1 < d2) return -1;
            else return 0;
        }
        String s1 = this.toString();
        String s2 = o.toString();
        return s1.compareTo(s2);
    }

    /**
     * 复制函数（深复制），从给定对象复制出一个新对象；
     *
     * @param i 其它任何实现了Serializable接口的对象
     */
    @Override
    public void copyFrom(Object i) {
        if (i instanceof Variant) {
            reset(((Variant) i).type, ((Variant) i).value);
        }
    }

    /**
     * 通过输入流，读入对象的值，type和value；
     *
     * @param dis 表示可以读取的存储对象，可能是内存、文件、管道等；
     * @return true or false；
     * @throws IOException
     */
    @Override
    public boolean load(DataInput dis) throws IOException {
        this.type = dis.readInt();
        switch (this.type) {
            case UNKNOWN: {
                this.value = null;
                return true;
            }
            case BOOLEAN: {
                reset(dis.readBoolean());
                break;
            }
            case CHARACTER: {
                reset(dis.readChar());
                break;
            }
            case BYTE: {
                reset(dis.readByte());
                break;
            }
            case SHORT: {
                reset(dis.readShort());
                break;
            }
            case INTEGER: {
                reset(dis.readInt());
                break;
            }
            case LONG: {
                reset(dis.readLong());
                break;
            }
            case FLOAT: {
                reset(dis.readFloat());
                break;
            }
            case DOUBLE: {
                reset(dis.readDouble());
                break;
            }
            case STRING: {
                this.value = (Object) readString(dis);
                break;
            }
            case BYTES: {
                this.value = (Object) readBytes(dis);
                break;
            }
            case CHARACTERS: {
                this.value = (Object) readChars(dis);
                break;
            }
            case BOOLEANS: {
                this.value = (Object) readBooleans(dis);
                break;
            }
            case SHORTS: {
                this.value = (Object) readShorts(dis);
                break;
            }
            case INTEGERS: {
                this.value = (Object) readIntegers(dis);
                break;
            }
            case LONGS: {
                this.value = (Object) readLongs(dis);
                break;
            }
            case FLOATS: {
                this.value = (Object) readFloats(dis);
                break;
            }
            case DOUBLES: {
                this.value = (Object) readDoubles(dis);
                break;
            }
            case STRINGS: {
                this.value = (Object) readStrings(dis);
                break;
            }
            default: {
                this.value = ObjectUtils.load(dis);
                break;
            }
        }
        return true;
    }

    /**
     * 将对象写入到输出流，储存起来
     *
     * @param dos 表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput dos) throws IOException {
        dos.writeInt(this.type);
        switch (this.type) {
            case UNKNOWN: {
                return true;
            }
            case BOOLEAN: {
                dos.writeBoolean(((Boolean) this.value).booleanValue());
                break;
            }
            case CHARACTER: {
                dos.writeChar(((Character) value).charValue());
                break;
            }
            case BYTE: {
                dos.writeByte(((Byte) value).byteValue());
                break;
            }
            case SHORT: {
                dos.writeShort(((Short) value).shortValue());
                break;
            }
            case INTEGER: {
                dos.writeInt(((Integer) value).intValue());
                break;
            }
            case LONG: {
                dos.writeLong(((Long) value).longValue());
                break;
            }
            case FLOAT: {
                dos.writeFloat(((Float) value).floatValue());
                break;
            }
            case DOUBLE: {
                dos.writeDouble(((Double) value).doubleValue());
                break;
            }
            case STRING: {
                writeString(dos, (String) value);
                break;
            }
            case BYTES: {
                writeBytes(dos, (byte[]) value);
                break;
            }
            case CHARACTERS: {
                writeChars(dos, (char[]) value);
                break;
            }
            case BOOLEANS: {
                writeBooleans(dos, (boolean[]) value);
                break;
            }
            case SHORTS: {
                writeShorts(dos, (short[]) value);
                break;
            }
            case INTEGERS: {
                writeIntegers(dos, (int[]) value);
                break;
            }
            case LONGS: {
                writeLongs(dos, (long[]) value);
                break;
            }
            case FLOATS: {
                writeFloats(dos, (float[]) value);
                break;
            }
            case DOUBLES: {
                writeDoubles(dos, (double[]) value);
                break;
            }
            case STRINGS: {
                writeStrings(dos, (String[]) value);
                break;
            }
            default: {
                ObjectUtils.store(this.value, dos);
                break;
            }
        }
        return true;
    }

    /**
     * 获取写成的字节数组长度
     *
     * @return 字节数组长度
     */
    @Override
    public long getByteArraySize() {
        long c = 4;
        switch (this.type) {
            case UNKNOWN: {
                break;
            }
            case BOOLEAN: {
                c += 1;
                break;
            }
            case CHARACTER: {
                c += 2;
                break;
            }
            case BYTE: {
                c += 1;
                break;
            }
            case SHORT: {
                c += 2;
                break;
            }
            case INTEGER: {
                c += 4;
                break;
            }
            case LONG: {
                c += 8;
                break;
            }
            case FLOAT: {
                c += 4;
                break;
            }
            case DOUBLE: {
                c += 8;
                break;
            }
            case STRING: {
                c += StringUtils.getByteArraySize((String) this.value);
                break;
            }
            case BYTES: {
                c += 4;
                c += ((byte[]) this.value).length;
                break;
            }
            case CHARACTERS: {
                c += 4;
                c += ((char[]) this.value).length * 2;
                break;
            }
            case BOOLEANS: {
                c += 4;
                c += ((boolean[]) this.value).length;
                break;
            }
            case SHORTS: {
                c += 4;
                c += ((short[]) this.value).length * 2;
                break;
            }
            case INTEGERS: {
                c += 4;
                c += ((int[]) this.value).length * 4;
                break;
            }
            case LONGS: {
                c += 4;
                c += ((long[]) this.value).length * 8;
                break;
            }
            case FLOATS: {
                c += 4;
                c += ((float[]) this.value).length * 4;
                break;
            }
            case DOUBLES: {
                c += 4;
                c += ((double[]) this.value).length * 8;
                break;
            }
            case STRINGS: {
                c += 4;
                String[] ss = (String[]) this.value;
                for (int i = 0; i < ss.length; ++i) {
                    c += StringUtils.getByteArraySize((String) ss[i]);
                    ;
                }
                break;
            }
            default: {
                return 4 + ObjectUtils.getByteArraySize(this.value);
            }
        }
        return c;
    }
}
