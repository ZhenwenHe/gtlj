package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.common.PropertySet;

import java.io.IOException;
import java.util.List;

/**
 * 存储数据的仓库，相当于数据库
 * 每个Schema相当于一个SHP文件结构或一个数据表的结构
 * 一个DataSet相当于一个数据表或一个SHP文件
 *
 * @param <T>                                                           数据模型，例如，FeatureType是Feature的模式，也可以是数据表的结构和数据表的记录集之间的关系
 * @param <F>数据内容，例如，Feature是FeatureType对应的数据内容，也可以是数据表的结构和数据表的记录集之间的关系
 */
public interface DataStore<T extends DataSchema, F extends DataContent> extends Serializable {
    /**
     * 获取属性集
     *
     * @return
     */
    PropertySet getProperties();

    /**
     * 设置属性集
     *
     * @param ps 传入的属性集对象
     */
    void setProperties(PropertySet ps);
//    /**
//     * 获取数据库相关服务信息
//     * @return 数据库相关服务信息
//     */
//    ServiceInfo getInfo();

    /**
     * 获取数据库的名称
     *
     * @return 数据库的名称
     */
    String getName();

    /**
     * 在数据库中创建一个数据模式
     *
     * @param featureType 数据模型
     * @throws IOException
     */
    void createSchema(T featureType) throws IOException;

//    /**
//     * 更新一个指定名称的数据模式
//     * @param typeName 数据模型名称
//     * @param featureType 数据模型类型
//     * @throws IOException
//     */
//    void updateSchema(String typeName, T featureType) throws IOException;

    /**
     * 删除指定的数据模式，模式对应的数据集也要随之删除
     *
     * @param typeName 数据模式名称
     * @throws IOException
     */
    void removeSchema(String typeName) throws IOException;

    /**
     * 获取所有的数据模式名称
     *
     * @return 所有数据模式名称的string数组
     * @throws IOException
     */
    String[] getSchemaNames() throws IOException;

    /**
     * 返回数据库中所有的数据模式
     *
     * @return 所有数据模式的集合
     * @throws IOException
     */
    List<T> getSchemas() throws IOException;

    /**
     * 获取指定模式的数据集
     *
     * @param typeName 数据集名称
     * @return 指定模式的数据集
     * @throws IOException
     */
    DataSet<T, F> getDataSet(String typeName) throws IOException;
}
