package cn.edu.cug.cs.gtl.data;

import cn.edu.cug.cs.gtl.data.feature.FeatureStore;
import cn.edu.cug.cs.gtl.data.material.MaterialStore;
import cn.edu.cug.cs.gtl.data.styling.StyleStore;
import cn.edu.cug.cs.gtl.data.texture.TextureStore;
import cn.edu.cug.cs.gtl.feature.Feature;
import cn.edu.cug.cs.gtl.feature.FeatureType;
import cn.edu.cug.cs.gtl.io.Solution;
import cn.edu.cug.cs.gtl.data.feature.FeatureStore;
import cn.edu.cug.cs.gtl.data.material.MaterialStore;
import cn.edu.cug.cs.gtl.data.styling.StyleStore;
import cn.edu.cug.cs.gtl.data.texture.TextureStore;
import cn.edu.cug.cs.gtl.feature.Feature;
import cn.edu.cug.cs.gtl.feature.FeatureType;
import cn.edu.cug.cs.gtl.io.Solution;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;

public interface DataSolution extends Solution<FeatureType, Feature> {

    /**
     * 如果已经存在，则直接打开
     * 在指定位置建立方案,并初始化;
     * 初始化包括：1）创建方案目录
     * 2）创建要素库目录
     * 3）创建纹理库目录
     * 4）创建材质库目录
     * 5）创建风格库目录
     *
     * @param path
     * @return
     */
    boolean open(String path) throws IOException;

    /**
     * 关闭方案
     */
    void close() throws IOException;

    /**
     * 创建一个要素库
     *
     * @param storeName
     * @return
     * @throws IOException
     */
    FeatureStore createFeatureStore(String storeName) throws IOException;

    /**
     * 获取方案中存在的数据库
     *
     * @param storeName
     * @return
     * @throws IOException
     */
    FeatureStore getFeatureStore(String storeName) throws IOException;

    /**
     * 获取方案中的所有要素库
     *
     * @return
     * @throws IOException
     */
    List<FeatureStore> getFeatureStores() throws IOException;

    /**
     * 获取材质库，在一个方案中，材质库是唯一的
     *
     * @return
     * @throws IOException
     */
    MaterialStore getMaterialStore() throws IOException;

    /**
     * 获取纹理库，在一个方案中，纹理库是唯一的
     *
     * @return
     * @throws IOException
     */
    TextureStore getTextureStore() throws IOException;

    /**
     * 获取风格库，在一个方案中，风格库是唯一的
     *
     * @return
     * @throws IOException
     */
    StyleStore getStyleStore() throws IOException;

}
