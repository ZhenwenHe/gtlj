package cn.edu.cug.cs.gtl.io;


import cn.edu.cug.cs.gtl.common.PropertySet;

import java.io.IOException;
import java.util.List;

/**
 * 对DataStore中存储数据的操作,
 * 相当于一个数据表或SHP文件
 *
 * @param <T>
 * @param <F>
 */
public interface DataSet<T extends DataSchema, F extends DataContent> extends Serializable {
    /**
     * 获取属性集
     *
     * @return 属性集
     */
    PropertySet getProperties();

    /**
     * 设置属性集
     *
     * @param ps 属性集
     */
    void setProperties(PropertySet ps);

    /**
     * 获取属性集名称
     *
     * @return 数据集的名称
     */
    String getName();

    /**
     * 获取数据模式
     *
     * @return 数据集的结构
     * @throws IOException
     */
    T getSchema() throws IOException;

    /**
     * 根据过滤条件，返回符合条件的对象列表
     *
     * @param filter 过滤条件
     * @return 符合过滤条件的对象列表
     * @throws IOException
     */
    List<F> getData(Filter<F> filter) throws IOException;

    /**
     * 得到该数据集上的Writer对象
     *
     * @return 该数据集上的Writer对象
     * @throws IOException
     */
    DataWriter<F> getDataWriter() throws IOException;

    /**
     * 得到该数据集上的Reader
     *
     * @return 该数据集上的Reader
     * @throws IOException
     */
    default DataReader<F> getDataReader() throws IOException {
        return getDataReader(null);
    }

    /**
     * 得到过滤后数据集上的Reader
     *
     * @return 该数据集上的Reader
     * @throws IOException
     */
    DataReader<F> getDataReader(Filter<F> fFilter) throws IOException;
}
