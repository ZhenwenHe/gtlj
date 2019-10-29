package cn.edu.cug.cs.gtl.io.storage;

import java.io.IOException;

/**
 * 带有缓存的存储管理器
 * Created by ZhenwenHe on 2016/12/8.
 */
public interface BufferedStorageManager extends StorageManager {
    /**
     * 得到命中次数
     *
     * @return 返回hits
     */
    long getHits();

    /**
     * 将缓存写回到存储设备，强制写入存储介质
     *
     * @throws IOException 文件IO异常
     */
    void clear() throws IOException;
}
