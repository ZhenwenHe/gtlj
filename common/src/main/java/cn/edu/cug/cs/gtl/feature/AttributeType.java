package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.PropertyType;
import cn.edu.cug.cs.gtl.common.Variant;

/**
 * 本类从PropertyType继承，没有添加新的属性；
 */
public class AttributeType extends PropertyType {


    private static final long serialVersionUID = -1689794475994575668L;

    /**
     * 克隆函数，根据本对象的type和binding ，构造一个新对象并且返回；
     *
     * @return 新对象
     */
    @Override
    public Object clone() {
        return new AttributeType(
                this.type, this.binding);
    }

    /**
     * 构造函数，传入对象类型的相对路径+类型，如Boolean.class，然后根据binding判断type；
     *
     * @param binding 对象类型的相对路径+类型，如Boolean.class；
     */
    public AttributeType(Class<?> binding) {
        super(binding);
    }

    /**
     * 构造函数，传入对象类型type，调用父类构造函数，根据type给binding复制；
     * 仅当type为Variant.OBJECT时，binding为Object.class，其余情况为null；
     *
     * @param type 整形变量，代表数据类型；
     */
    public AttributeType(int type) {
        super(type);
    }

    /**
     * 构造函数，传入type和binding，调用父类构造函数；
     *
     * @param type    一个整形变量，代表数据类型；
     * @param binding 数据类型的相对路径+类型名称 ，例如 Boolean.class
     */
    public AttributeType(int type, Class<?> binding) {
        super(type, binding);
    }

    /**
     * 默认构造函数，将type设为0，binding设为null；
     */
    public AttributeType() {
        super(Variant.UNKNOWN);
    }
}
