package cn.edu.cug.cs.gtl.data.styling;

import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;
import cn.edu.cug.cs.gtl.styling.Style;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.DataStore;
import cn.edu.cug.cs.gtl.styling.Style;

import java.io.IOException;
import java.nio.file.Path;

public interface StyleStore extends DataStore<DataSchema, Style> {

    /**
     * 打开风格库,如果该风格库不存在，则新建一个风格库并打开
     *
     * @param path
     * @return
     * @throws IOException
     */
    boolean open(String path) throws IOException;

    /**
     * 添加风格
     *
     * @param Style
     * @return
     * @throws IOException
     */
    Style append(Style Style) throws IOException;

    /**
     * 移除风格
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Style remove(Identifier identifier) throws IOException;

    /**
     * 查找风格，根据identifier指示器查找
     *
     * @param identifier
     * @return
     * @throws IOException
     */
    Style find(Identifier identifier) throws IOException;

    /**
     * 查找风格，根据风格名字查找 styleName
     */
    Style find(String styleName) throws IOException;

    /**
     * 关闭风格库
     */
    void close();

}
