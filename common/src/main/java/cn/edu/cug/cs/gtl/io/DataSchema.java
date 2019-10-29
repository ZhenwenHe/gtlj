package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.Identifier;

/**
 * 数据模式接口，可以数据库中数据表的结构，
 * 或者是SHP文件的属性结构，也即FeatureType
 * 一个DataSchema对应0个或多个DataContent
 */
public interface DataSchema extends Serializable {
    /**
     * 获取数据模式的ID
     *
     * @return 数据模式的ID
     */
    Identifier getIdentifier();

    /**
     * 获取数据模式的名称
     *
     * @return 数据模式的名称
     */
    String getName();
}
