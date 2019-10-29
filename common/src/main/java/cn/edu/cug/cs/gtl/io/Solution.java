package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.PropertySet;

import java.io.IOException;
import java.net.URI;

public interface Solution<T extends DataSchema, F extends DataContent> extends Storable {
    /**
     * 获取属性集
     *
     * @return
     */
    PropertySet getProperties();

    /**
     * 设置属性集
     *
     * @param ps 属性集
     */
    void setProperties(PropertySet ps);

    /**
     * 创建一个数据库
     *
     * @param storeName 数据库名称
     * @return 返回创建的数据库对象
     * @throws IOException
     */
    DataStore<T, F> createDataStore(String storeName) throws IOException;

    /**
     * 获取方案中存在的数据库
     *
     * @param storeName 数据库名称
     * @return 返回方案中的数据库
     * @throws IOException
     */
    DataStore<T, F> getDataStore(String storeName) throws IOException;

    /**
     * 获取唯一资源标识URI，一般是一个目录
     *
     * @return 返回唯一标识URI
     */
    URI getURI() throws IOException;
}
