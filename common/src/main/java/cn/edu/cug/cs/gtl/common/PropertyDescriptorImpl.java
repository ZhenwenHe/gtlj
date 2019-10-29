package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.geotools.resources.Classes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PropertyDescriptorImpl implements PropertyDescriptor {

    private static final long serialVersionUID = 1L;

    protected PropertyType type;
    protected String name;
    protected int minOccurs;
    protected int maxOccurs;
    protected boolean isNillable;

    /*
         类型                                             长度
         public final static int UNKNOWN = 0;                0
         public final static int BOOLEAN = 1;                0
         public final static int CHARACTER = 2;              0
         public final static int BYTE = 3;                   0
         public final static int SHORT = 4;                  0
         public final static int INTEGER = 5;                0
         public final static int LONG = 6;                   0
         public final static int FLOAT = 7;                  用户设定
         public final static int DOUBLE = 8;                 用户设定
         public final static int STRING = 9;                 0
         public final static int BYTES = 10;                 0
         public final static int CHARACTERS = 11;            0
         public final static int BOOLEANS = 12;              0
         public final static int SHORTS = 13;                0
         public final static int INTEGERS = 14;              0
         public final static int LONGS = 15;                 0
         public final static int FLOATS = 16;                0
         public final static int DOUBLES = 17;               0
         public final static int STRINGS = 18;               0
         public final static int OBJECT = 19;                0
         */
    protected int decimal;
    /*
    类型                                             长度
    public final static int UNKNOWN = 0;                0
    public final static int BOOLEAN = 1;                1
    public final static int CHARACTER = 2;              2
    public final static int BYTE = 3;                   1
    public final static int SHORT = 4;                  2
    public final static int INTEGER = 5;                4
    public final static int LONG = 6;                   8
    public final static int FLOAT = 7;                  4
    public final static int DOUBLE = 8;                 8
    public final static int STRING = 9;                 最大字符个数
    public final static int BYTES = 10;                 最大元素个数
    public final static int CHARACTERS = 11;            最大元素个数
    public final static int BOOLEANS = 12;              最大元素个数
    public final static int SHORTS = 13;                最大元素个数
    public final static int INTEGERS = 14;              最大元素个数
    public final static int LONGS = 15;                 最大元素个数
    public final static int FLOATS = 16;                最大元素个数
    public final static int DOUBLES = 17;               最大元素个数
    public final static int STRINGS = 18;               最大元素个数
    public final static int OBJECT = 19;                类序列化后的字节数组最大长度
     */
    protected int length;//类型长度

    @Deprecated
    public PropertyDescriptorImpl(PropertyType type, String name, int min, int max, boolean isNillable) {
        this(name, type, 0, 0, min, max, isNillable);
    }

    public PropertyDescriptorImpl(String name, PropertyType type, int length, int decimal, boolean isNillable) {
        this(name, type, length, decimal, 1, 1, isNillable);
    }

    public PropertyDescriptorImpl(String name, PropertyType type, int length, int decimal, int minOccurs, int maxOccurs, boolean isNillable) {
        this.type = type;
        this.name = name;
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
        this.isNillable = isNillable;
        this.decimal = decimal;
        this.length = length;
        if (type == null) {
            throw new NullPointerException("type");
        }

        if (name == null) {
            throw new NullPointerException("name");
        }

        if (this.maxOccurs > 0 && (this.maxOccurs < this.minOccurs)) {
            throw new IllegalArgumentException("max must be -1, or >= min");
        }
    }

    public PropertyDescriptorImpl() {
    }

    @Override
    public Object clone() {
        return new PropertyDescriptorImpl(name,
                (PropertyType) type.clone(),
                length, decimal, minOccurs, maxOccurs, isNillable);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.type = (PropertyType) ObjectUtils.load(in);
        this.name = StringUtils.load(in);
        this.decimal = in.readInt();
        this.length = in.readInt();
        this.minOccurs = in.readInt();
        this.maxOccurs = in.readInt();
        this.isNillable = in.readBoolean();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        ObjectUtils.store(this.type, out);
        StringUtils.store(this.name, out);
        out.writeInt(this.decimal);
        out.writeInt(this.length);
        out.writeInt(this.minOccurs);
        out.writeInt(this.maxOccurs);
        out.writeBoolean(this.isNillable);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return ObjectUtils.getByteArraySize(this.type) + StringUtils.getByteArraySize(this.name) + 4 + 4 + 1 + 4 + 4;
    }

    public PropertyType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getMinOccurs() {
        return minOccurs;
    }

    public int getMaxOccurs() {
        return maxOccurs;
    }

    public int getDecimal() {
        return decimal;
    }

    public int getLength() {
        return length;
    }

    public boolean isNullable() {
        return isNillable;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyDescriptorImpl)) {
            return false;
        }

        PropertyDescriptorImpl other = (PropertyDescriptorImpl) obj;
        return type.equals(other.type) &&
                name.equals(other.name) &&
                (minOccurs == other.minOccurs) && (maxOccurs == other.maxOccurs) &&
                (isNillable == other.isNillable);
    }

    public int hashCode() {
        return (37 * minOccurs + 37 * maxOccurs) ^ type.hashCode() ^ name.hashCode();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(Classes.getShortClassName(this));
        sb.append(" ");
        sb.append(getName());
        if (type != null) {
            sb.append(" <");
            sb.append(type.getName());
            sb.append(":");
            sb.append(Classes.getShortName(type.getBinding()));
            sb.append(">");
        }
        if (isNillable) {
            sb.append(" nillable");
        }
        if (minOccurs == 1 && maxOccurs == 1) {
            // ignore the 1:1
        } else {
            sb.append(" ");
            sb.append(minOccurs);
            sb.append(":");
            sb.append(maxOccurs);
        }

        return sb.toString();
    }
}
