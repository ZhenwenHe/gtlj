package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Property;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.util.ObjectUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 本类从Property继承，添加了一个Identifier类型指示器；表示属性；
 */

public class Attribute extends Property {


    private static final long serialVersionUID = -4740500805017078875L;
    protected Identifier identifier = null;

    public Attribute() {

    }

    public Attribute(Identifier identifier) {
        this.identifier = identifier;
    }

    public Attribute(String name, Variant v, Identifier identifier) {
        super(name, v);
        this.identifier = identifier;
    }

    public Attribute(String name, int type, Object value, Identifier identifier) {
        super(name, type, value);
        this.identifier = identifier;
    }

    public Attribute(String name, Object value, Identifier identifier) {
        super(name, value);
        this.identifier = identifier;
    }

    /**
     * 复制函数，从给定对象复制其属性值；
     *
     * @param i 其它任何实现了Serializable接口的对象
     */
    @Override
    public void copyFrom(Object i) {
        super.copyFrom(i);
        this.identifier = (Identifier) ((Attribute) i).identifier.clone();
    }

    /**
     * 读取函数，通过输入流，读取对象的属性值；
     *
     * @param dis 表示可以读取的存储对象，可能是内存、文件、管道等；
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput dis) throws IOException {
        boolean b = super.load(dis);
        this.identifier = (Identifier) ObjectUtils.load(dis);
        return b;
    }

    /**
     * 存储函数，通过输出流，将对象写入输出流，存储起来；
     *
     * @param dos 表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput dos) throws IOException {
        boolean b = super.store(dos);
        ObjectUtils.store(this.identifier, dos);
        return b;
    }

    /**
     * 获取对象写成字节数组后的字节数组的长度；
     *
     * @return 字节数组的长度；
     */
    @Override
    public long getByteArraySize() {
        return super.getByteArraySize() + ObjectUtils.getByteArraySize(this.identifier);
    }


}
