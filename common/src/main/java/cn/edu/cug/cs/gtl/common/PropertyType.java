package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.opengis.feature.type.Name;

import java.io.*;
import java.util.Arrays;

/**
 * 本类实现了序列化接口，表示属性类型；
 * 包含一个整形变量type表示数据类型，对应Variant中定义的常量；
 * 一个泛型变量binding表示数据类型的相对路径+类名，例如Boolean.class， 当type不为用户自定义类型时，binding为null；
 */
public class PropertyType implements Serializable {
    private static final long serialVersionUID = 1L;

    protected int type;
    protected Class<?> binding;//if type != OBJECT , binding is null

    /**
     * 默认构造函数，type设为0，binding为null
     */
    public PropertyType() {
        this.type = Variant.UNKNOWN;
        this.binding = null;
    }

    /**
     * 构造函数，传入泛型变量binding，然后去判断数据类型并设置type；
     *
     * @param binding 变量的类路径与类名，例如例如Boolean.class；
     */
    public PropertyType(Class<?> binding) {
        this.binding = binding;
        reset(binding);
    }

    /**
     * 构造函数，传入变量类型type，为一个整数，对应Variant中定义的常量，代表各种数据类型；
     * 根据type设置binding，仅当type=Variant.OBJECT时，即数据类型为用户自定义类型时，binding为Object.class，否则为null；
     *
     * @param type 一个整形变量，代表数据类型；
     */
    public PropertyType(int type) {
        this.type = type;
        if (this.type == Variant.OBJECT)
            this.binding = Object.class;
        else
            this.binding = null;
    }

    /**
     * 构造函数，同时传入type和binding；
     *
     * @param type    一个整形变量，代表了数据类型；
     * @param binding 变量类型的类路径+类名，例如Boolean.class、Variant.class;
     */
    public PropertyType(int type, Class<?> binding) {
        this.type = type;
        if (this.type == Variant.OBJECT)
            this.binding = binding;
        else
            this.binding = null;
    }

    /**
     * 获得类型名称，type对应Variant中定义的常量，代表数据类型，根据type对应的常量，获得类型名称，如"BOOLEAN"  "OBJECT"
     *
     * @return 一个代表类型名称的字符串，
     */
    public String getName() {
        return PropertyType.getName(this.type);
    }

    /**
     * 返回对象的数据类型type
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * 获得对象类型的类相对路径+类名 ，如Boolean.class;
     *
     * @return
     */
    public Class<?> getBinding() {
        if (this.type == Variant.OBJECT)
            return this.binding;
        else
            return PropertyType.getBinding(this.type);
    }

    /**
     * 重设对象属性，传入对象类型对应的类的相对路径+类型名称,然后判断c的类型去设置变量类型type；
     *
     * @param c 例如 Boolean.class;
     */
    public void reset(Class<?> c) {
        this.binding = null;
        if (c == null)
            type = Variant.UNKNOWN;
        else if (c.equals(Boolean.class))
            type = Variant.BOOLEAN;
        else if (c.equals(Character.class))
            type = Variant.CHARACTER;
        else if (c.equals(Byte.class))
            type = Variant.BYTE;
        else if (c.equals(Short.class))
            type = Variant.SHORT;
        else if (c.equals(Integer.class))
            type = Variant.INTEGER;
        else if (c.equals(Long.class))
            type = Variant.LONG;
        else if (c.equals(Float.class))
            type = Variant.FLOAT;
        else if (c.equals(Double.class))
            type = Variant.DOUBLE;
        else if (c.equals(String.class))
            type = Variant.STRING;
        else {
            byte[] bv = new byte[2];
            char[] cv = new char[2];
            boolean[] blv = new boolean[2];
            short[] sv = new short[2];
            int[] iv = new int[2];
            long[] lv = new long[2];
            float[] fv = new float[2];
            double[] dv = new double[2];
            String[] strv = new String[2];
            if (c.equals(bv.getClass()))
                type = Variant.BYTES;
            else if (c.equals(cv.getClass()))
                type = Variant.CHARACTERS;
            else if (c.equals(blv.getClass()))
                type = Variant.BOOLEANS;
            else if (c.equals(sv.getClass()))
                type = Variant.SHORTS;
            else if (c.equals(iv.getClass()))
                type = Variant.INTEGERS;
            else if (c.equals(lv.getClass()))
                type = Variant.LONGS;
            else if (c.equals(fv.getClass()))
                type = Variant.FLOATS;
            else if (c.equals(dv.getClass()))
                type = Variant.DOUBLES;
            else if (c.equals(strv.getClass()))
                type = Variant.STRINGS;
            else {
                type = Variant.OBJECT;
                this.binding = c;
            }
        }
    }

    /**
     * 克隆函数，根据本对象的type和binding new一个新对象并返回；
     *
     * @return 新对象
     */
    @Override
    public Object clone() {
        return new PropertyType(this.type, this.binding);
    }

    /**
     * 读取函数，通过输入流，读取对象的属性值（type和binding）
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        this.type = in.readInt();
        if (this.type == Variant.OBJECT) {
            try {
                this.binding = (Class<?>) ObjectUtils.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            this.binding = null;
        return true;
    }

    /**
     * 存储函数，通过输出流，将对象写入输出流，存储；
     *
     * @param out，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.type);
        if (this.type == Variant.OBJECT) {
            ObjectUtils.store(this.binding, out);
        }
        return true;
    }

    /**
     * 获得对象写成字节数组后的字节数组的长度；
     *
     * @return 字节数组的长度；
     */
    @Override
    public long getByteArraySize() {
        if (this.type == Variant.OBJECT) {
            return ObjectUtils.getByteArraySize(this.binding) + 4;
        } else
            return 4;
    }

    /**
     * 根据对象的数据类型type获取对象的类型名称，如“BOOLEAN”
     *
     * @param type 一个整形变量，代表对象的数据类型；
     * @return 一个字符串，表示对象的数据类型名称；
     */
    public static String getName(int type) {
        switch (type) {
            case Variant.UNKNOWN: {
                return "UNKNOWN";
            }
            case Variant.BOOLEAN: {
                return "BOOLEAN";
            }
            case Variant.CHARACTER: {
                return "CHARACTER";
            }
            case Variant.BYTE: {
                return "BYTE";
            }
            case Variant.SHORT: {
                return "SHORT";
            }
            case Variant.INTEGER: {
                return "INTEGER";
            }
            case Variant.LONG: {
                return "LONG";
            }
            case Variant.FLOAT: {
                return "FLOAT";
            }
            case Variant.DOUBLE: {
                return "DOUBLE";
            }
            case Variant.STRING: {
                return "STRING";
            }
            case Variant.BYTES: {
                return "BYTES";
            }
            case Variant.CHARACTERS: {
                return "CHARACTERS";
            }
            case Variant.BOOLEANS: {
                return "BOOLEANS";
            }
            case Variant.SHORTS: {
                return "SHORTS";
            }
            case Variant.INTEGERS: {
                return "INTEGERS";
            }
            case Variant.LONGS: {
                return "LONGS";
            }
            case Variant.FLOATS: {
                return "FLOATS";
            }
            case Variant.DOUBLES: {
                return "DOUBLES";
            }
            case Variant.STRINGS: {
                return "STRINGS";
            }
            default:
                return "OBJECT";
        }
    }

    /**
     * 根据对象的数据类型type获取对象数据类型的相对路径+类名，如Boolean.class、Object.class；
     *
     * @param type 一个整形变量，代表对象数据类型；
     * @return binding 对象的数据类型的相对路径+类名，如Boolean.class、Object.class
     */
    public static Class<?> getBinding(int type) {
        switch (type) {
            case Variant.UNKNOWN: {
                return null;
            }
            case Variant.BOOLEAN: {
                return Boolean.class;
            }
            case Variant.CHARACTER: {
                return Character.class;
            }
            case Variant.BYTE: {
                return Byte.class;
            }
            case Variant.SHORT: {
                return Short.class;
            }
            case Variant.INTEGER: {
                return Integer.class;
            }
            case Variant.LONG: {
                return Long.class;
            }
            case Variant.FLOAT: {
                return Float.class;
            }
            case Variant.DOUBLE: {
                return Double.class;
            }
            case Variant.STRING: {
                return String.class;
            }
            case Variant.BYTES: {
                byte[] v = new byte[2];
                return v.getClass();
            }
            case Variant.CHARACTERS: {
                char[] v = new char[2];
                return v.getClass();
            }
            case Variant.BOOLEANS: {
                boolean[] v = new boolean[2];
                return v.getClass();
            }
            case Variant.SHORTS: {
                short[] v = new short[2];
                return v.getClass();
            }
            case Variant.INTEGERS: {
                int[] v = new int[2];
                return v.getClass();
            }
            case Variant.LONGS: {
                long[] v = new long[2];
                return v.getClass();
            }
            case Variant.FLOATS: {
                float[] v = new float[2];
                return v.getClass();
            }
            case Variant.DOUBLES: {
                double[] v = new double[2];
                return v.getClass();
            }
            case Variant.STRINGS: {
                String[] v = new String[2];
                return v.getClass();
            }
            default: {
                return Object.class;
            }
        }
    }

}
