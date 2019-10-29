package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Status;

/**
 * 数据内容接口，可以数据库中数据表的一条记录，
 * 或者是SHP文件的一个Feature记录
 * 一个DataSchema对应0个或多个DataContent
 * 一个DataContent可以理解为该数据模式下的一条数据记录
 */
public interface DataContent extends Storable {
    /**
     * 获取数据内容的ID
     *
     * @return 数据内容的ID
     */
    Identifier getIdentifier();

    /**
     * 获取数据内容的名称
     *
     * @return 数据内容的名称
     */
    String getName();

    /**
     * 获取数据状态
     *
     * @return 数据状态
     */
    Status getStatus();
}
