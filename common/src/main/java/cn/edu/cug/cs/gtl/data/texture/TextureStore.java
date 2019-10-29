package cn.edu.cug.cs.gtl.data.texture;

import cn.edu.cug.cs.gtl.geom.Texture;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.Texture;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;

import java.io.IOException;
import java.nio.file.Path;

/**
 * 打开纹理库
 * 添加纹理
 * 删除纹理
 * 查找纹理
 * 关闭纹理库
 */
public interface TextureStore extends DataStore<DataSchema, Texture> {

    /**
     * 打开纹理库,如果该纹理库不存在，则新建一个纹理库并打开
     *
     * @param path
     * @return
     * @throws IOException
     */
    boolean open(String path) throws IOException;

    /**
     * 添加纹理
     *
     * @param texture
     * @return
     * @throws IOException
     */
    Texture append(Texture texture) throws IOException;

    /**
     * 移除纹理
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Texture remove(Identifier identifier) throws IOException;

    /**
     * 移除纹理
     *
     * @param textureName
     * @return
     * @throws IOException
     */
    Texture remove(String textureName) throws IOException;

    /**
     * 查找纹理
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Texture find(Identifier identifier) throws IOException;

    /**
     * 查找纹理
     */
    Texture find(String textureName) throws IOException;

    /**
     * 关闭纹理库
     */
    void close();
}
