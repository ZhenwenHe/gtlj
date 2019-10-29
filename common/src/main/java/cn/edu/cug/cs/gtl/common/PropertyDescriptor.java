package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

public interface PropertyDescriptor extends Serializable {
    /**
     * 获取对象类型数据类型 type；
     *
     * @return
     */
    PropertyType getType();

    /**
     * 获取对象数据类型名字，如：“BOOLEAN”；
     *
     * @return
     */
    String getName();

    /**
     * 获取MinOccurs；
     *
     * @return
     */
    int getMinOccurs();

    /**
     * 获取MaxOccurs；
     *
     * @return
     */
    int getMaxOccurs();

    /**
     * 判断对象是否为空；
     *
     * @return
     */
    boolean isNullable();
}
