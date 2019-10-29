package cn.edu.cug.cs.gtl.data.feature;

import cn.edu.cug.cs.gtl.feature.*;
import cn.edu.cug.cs.gtl.io.DataStore;


import java.io.IOException;
import java.nio.file.Path;

public interface FeatureStore extends DataStore<FeatureType, Feature> {
    /**
     * 打开要素库,如果该要素库不存在，则新建一个要素库并打开
     *
     * @param path
     * @return
     * @throws IOException
     */
    boolean open(String path) throws IOException;

    /**
     * 关闭要素库
     */
    void close();
    /////////////////////////////////////////////////////////////////////////////
    //FeatureType operations begin
    /////////////////////////////////////////////////////////////////////////////

    /**
     * 构建一个FeatureTypeBuilder，并返回
     * 通过FeatureTypeBuilder构建的FeatureType 必须添加到本Store中
     *
     * @return
     */
    default FeatureTypeBuilder getFeatureTypeBuilder() {
        return new FeatureTypeBuilder();
    }

    /**
     * 获取FeatureType的查找器，可以通过名称查找该要素类型对象
     *
     * @return
     */
    FeatureTypeFinder getFeatureTypeFinder();

    /**
     * 传入FeatureType，并将其加入到本Store中,
     * 这是FeatureType添加到FeatureStore中的唯一方法
     * 目的是保证FeatureType与FeatureSet的一一对应关系
     * 然后在本FeatureStore中创建一个FeatureSet
     *
     * @param featureType
     * @return
     * @throws IOException
     */
    FeatureSet createFeatureSet(FeatureType featureType) throws IOException;

    /**
     * 获取对应的FeatureSet
     *
     * @param featureTypeName
     * @return
     * @throws IOException
     */
    FeatureSet getFeatureSet(String featureTypeName) throws IOException;

    /**
     * 移除FeatureSet及其对应的FeatureType
     *
     * @param featureTypeName
     * @throws IOException
     */
    boolean removeFeatureSet(String featureTypeName) throws IOException;

    /////////////////////////////////////////////////////////////////////////////
    //Feature operations begin
    /////////////////////////////////////////////////////////////////////////////

    /**
     * 添加要素，首先查找所属FeatureSet,
     * 然后打开该FeatureSet，添加进去
     *
     * @param feature
     * @return
     * @throws IOException
     */
    Feature appendFeature(Feature feature) throws IOException;

    /**
     * 移除要素
     *
     * @param feature
     * @return
     * @throws IOException
     */
    Feature removeFeature(Feature feature) throws IOException;

    /**
     * 获取FeatureWriter对象
     *
     * @param featureTypeName
     * @return
     * @throws IOException
     */

    default FeatureWriter getFeatureWriter(String featureTypeName) throws IOException {
        return getFeatureSet(featureTypeName).getFeatureWriter();
    }


    /**
     * 获取FeatureReader对象
     *
     * @param featureTypeName
     * @return
     * @throws IOException
     */
    default FeatureReader getFeatureReader(String featureTypeName) throws IOException {
        return getFeatureSet(featureTypeName).getFeatureReader();
    }

    /**
     * 根据要素类型名称，构建FeatureBuilder
     * 传入FeatureBuilder的要素类型必须是本Store中已经存在的要素类型
     *
     * @param featureTypeName 本Store中已经存在的要素类型名称
     * @return
     */
    default FeatureBuilder getFeatureBuilder(String featureTypeName) {
        FeatureTypeFinder featureTypeFinder = getFeatureTypeFinder();
        if (featureTypeFinder == null) return null;
        FeatureType ft = featureTypeFinder.find(featureTypeName);
        if (ft == null) return null;
        return new FeatureBuilder(ft);
    }

}
