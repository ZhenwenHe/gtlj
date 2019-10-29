package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by hadoop on 17-2-19.
 */

/**
 * 此类从泛型变量类Variant继承，添加了一个string类型的name属性，表示属性；
 */
public class Property extends Variant implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name = null;

    public Property() {
        super();
        this.name = "Unknown";
    }

    public Property(String name, Variant v) {
        super(v);
        this.name = name;
    }

    public Property(String name, int type, Object value) {
        super(type, value);
        this.name = name;
    }

    public Property(String name, Object value) {
        super(value);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PropertyType getPropertyType() {
        return new PropertyType(super.getType(), super.value.getClass());
    }

    /**
     * 重载的判断是否相等的函数；
     *
     * @param o 另一个Variant对象；
     * @return
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && this.name.equals(((Property) o).name);
    }

    /**
     * 将对象转换成字符串；
     *
     * @return 转换后的字符串；
     */
    @Override
    public String toString() {
        return this.name + ":" + super.toString();
    }

    /**
     * 复制函数，从给定对象复制出一个新的对象；
     *
     * @param i 其它任何实现了Serializable接口的对象
     */
    @Override
    public void copyFrom(Object i) {
        super.copyFrom(i);
        if (i instanceof Property) {
            this.name = ((Property) i).name;
        }
    }

    /**
     * 通过输入流，读取对象的属性；
     *
     * @param dis 表示可以读取的存储对象，可能是内存、文件、管道等；
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput dis) throws IOException {
        this.name = readString(dis);
        boolean b = super.load(dis);
        return b;
    }

    /**
     * 通过输出流，将对象存储；
     *
     * @param dos 表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput dos) throws IOException {
        writeString(dos, this.name);
        boolean b = super.store(dos);
        return b;
    }

    /**
     * 获得对象写成字节数组后的长度；
     *
     * @return 字节数组长度；
     */
    @Override
    public long getByteArraySize() {
        return super.getByteArraySize() + StringUtils.getByteArraySize(this.name);
    }


}
