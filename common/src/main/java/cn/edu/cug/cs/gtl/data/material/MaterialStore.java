package cn.edu.cug.cs.gtl.data.material;

import cn.edu.cug.cs.gtl.geom.Material;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.Material;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;

import java.io.IOException;
import java.nio.file.Path;

public interface MaterialStore extends DataStore<DataSchema, Material> {
    /**
     * 打开材质库,如果该材质库不存在，则新建一个材质库并打开
     *
     * @param path
     * @return
     * @throws IOException
     */
    boolean open(String path) throws IOException;

    /**
     * 添加材质
     *
     * @param Material
     * @return
     * @throws IOException
     */
    Material append(Material Material) throws IOException;

    /**
     * 移除材质
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Material remove(Identifier identifier) throws IOException;

    /**
     * 查找材质 根据identifier指示器查找；
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Material find(Identifier identifier) throws IOException;


    /**
     * 查找材质，根据材质名称查找；
     *
     * @param materialName
     * @return
     * @throws IOException
     */
    Material find(String materialName) throws IOException;

    /**
     * 关闭材质库
     */
    void close();
}
