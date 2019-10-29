package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.common.PropertyDescriptorImpl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 本类从PropertyDescriptorImpl继承，添加了一个Object对象 defaultValue；
 */
public class AttributeDescriptor extends PropertyDescriptorImpl {


    private static final long serialVersionUID = -7488935680139035716L;
    protected Object defaultValue;

    /**
     * 默认构造函数，仅将defaultValue设置为null，其他什么也不做；
     */
    public AttributeDescriptor() {
        this.defaultValue = null;
    }

    /**
     * 构造函数
     *
     * @param type         AttributeType类型对象；
     * @param name         string类型变量 名字；
     * @param min
     * @param max
     * @param isNillable   boolean 类型变量
     * @param defaultValue Object类型对象；
     */
    protected AttributeDescriptor(AttributeType type, String name, int min, int max, boolean isNillable, Object defaultValue) {
        super(type, name, min, max, isNillable);
        this.defaultValue = defaultValue;
    }

    public AttributeDescriptor(String name, AttributeType type, int length, int decimal, boolean isNillable, Object defaultValue) {
        super(name, type, length, decimal, isNillable);
        this.defaultValue = defaultValue;
    }

    /**
     * 构造函数
     *
     * @param type    整形变量，代表数据类型
     * @param binding 对象数据类型的相对路径+类名，如Boolean.class;
     * @param name    string类型姓名；
     */
    protected AttributeDescriptor(int type, Class<?> binding, String name) {
        super(new AttributeType(type, binding), name, 1, 1, true);
        this.defaultValue = null;
    }

    /**
     * 返回默认的Object对象；
     *
     * @return defaultValue
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * 获得 AttributeType
     *
     * @return 返回AttributeType 对象
     */
    public AttributeType getAttributeType() {
        return (AttributeType) super.getType();
    }

    /**
     * 克隆函数，根据本对象的属性新构造一个对象并返回；
     *
     * @return 新对象；
     */
    @Override
    public Object clone() {
        return new AttributeDescriptor((AttributeType) this.getType().clone(), this.name, this.minOccurs, this.maxOccurs, this.isNillable, this.defaultValue);
    }

    /**
     * 读取函数，通过输入流，加载对象的各个属性值；
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        this.defaultValue = ObjectUtils.load(in);
        return true;
    }

    /**
     * 存储函数，通过输出流，将对象写入输出流，存储；
     *
     * @param out 表示可以写入的存储对象，可能是内存、文件、管道等；
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        ObjectUtils.store(this.defaultValue, out);
        return true;
    }

    /**
     * 获取将对象写成字节数组后的字节数组的长度；
     *
     * @return 字节数组的长度；
     */
    @Override
    public long getByteArraySize() {
        return super.getByteArraySize() + ObjectUtils.getByteArraySize(defaultValue);
    }
}
