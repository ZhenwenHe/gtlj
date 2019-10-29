package cn.edu.cug.cs.gtl.io.storage;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.Closeable;
import java.io.IOException;

/**
 * 分页存储管理器的接口
 * 其子接口为BufferedStorageManager
 * 其子类有：
 * DiskStorageManager
 * MemoryStorageManager
 * Created by ZhenwenHe on 2016/12/8.
 */
public interface StorageManager extends Closeable, AutoCloseable, java.io.Serializable {

    long EMPTY_PAGE = -0x1;
    long NEW_PAGE = -0x1;

    /**
     * 读取指定页面的内容,推荐使用本函数替换loadByteArray
     *
     * @param page 读取页面的id
     * @return 通过调用loadByteArray()函数来读取指定页面的数据，返回byte数组
     * @throws IOException
     */
    default byte[] read(long page) throws IOException {
        if (page == NEW_PAGE) return null;
        return loadByteArray(Identifier.create(page));
    }

    /**
     * 将数据写入page页面，推荐使用本函数替换storeByteArray
     *
     * @param page 如果是新插入的页面，传入的page必须是-1；
     *             如果是复写，则应该是一个有效的page
     * @param data 待写入的数据
     * @return i 返回数据写入的页面id
     * @throws IOException
     */
    default Identifier write(long page, byte[] data) throws IOException {
        Identifier i = Identifier.create(page);
        storeByteArray(i, data);
        return i;
    }

    /**
     * 删除指定页面，推荐使用本函数替换deleteByteArray
     *
     * @param page 要删除的页面id
     * @throws IOException
     */
    default void delete(long page) throws IOException {
        deleteByteArray(Identifier.create(page));
    }

    /**
     * 读取页面ID为page的字节信息，存放到一个字节数组中
     *
     * @param page 页面唯一ID
     * @return 页面中的内容，存放到字节数组中返回
     * @throws IOException
     */
    byte[] loadByteArray(Identifier page) throws IOException;

    /**
     * 在页面page中写入字节数组；如果页面ID为-1，则选择一个新的页面写入，并返回新页面ID。
     * 如果一个页面写不完需要写入的数据，则会自动增加私有页面或扩展页面；在具体实现本方法
     * 的时候，如果是高速存储介质，例如内存，推荐采用扩展页面大小的方式；如果是低速介质，
     * 如磁盘，HDFS等，推荐采用私有页面方式。所谓的私有页面，就是跟在一个公有页面（也称
     * 为目录上的页码，本接口中的所有页面都是公有页面）。
     *
     * @param page 页面ID，如果为-1，则选择一个新的页面写入，并修改传入page并返回
     *             如果为有效页面ID，则写入该页面。
     * @param data 需要写入的字节数组
     * @throws IOException
     */
    void storeByteArray(Identifier page, byte[] data) throws IOException;

    /**
     * 删除指定页面
     *
     * @param page 页面ID
     * @throws IOException
     */
    void deleteByteArray(Identifier page) throws IOException;

    /**
     * 强制写入存储介质
     *
     * @throws IOException
     */
    void flush() throws IOException;

    /**
     * 创建基于内存的分页存储器
     *
     * @return 返回创建的MemoryStorageManager对象
     */
    static StorageManager createMemoryStorageManager() {
        try {
            return StorageSuits.createMemoryStorageManager();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建缓存分页存储管理器
     *
     * @param storageManager 存储管理器类型，包括内存管理器、
     *                       磁盘管理以及HDFS存储管理
     * @param capacity       buffer中存放的数据项的个数最多为capacity
     *                       如果超过了，则必须调用removeEntry删除一项
     *                       并将删除的项存储到storageManager指向的存储设备中
     * @param writeThrough   写入缓存的时候是否同步将内容也写入到存储设备中
     *                       如果为true,则在写入缓存的同时也会向存储设备也写入
     *                       如果为false,则不会同时写入到存储设备
     * @return 返回创建的缓存分页存储管理器
     */
    static BufferedStorageManager createBufferedStorageManager(
            StorageManager storageManager,
            int capacity,
            boolean writeThrough) {
        return StorageSuits.createBufferedStorageManager(storageManager, capacity, writeThrough);
    }

    /**
     * 创建基于本磁盘的分页存储管理器
     *
     * @param baseName  文件名，不包含文件后缀
     * @param pageSize  页面的大小
     * @param overWrite 是否覆盖源文件
     * @return 返回创建的本磁盘的分页存储管理器
     * @throws IOException
     */
    static StorageManager createDiskStorageManager(
            String baseName,
            int pageSize,
            boolean overWrite) throws IOException {
        return StorageSuits.createDiskStorageManager(baseName, pageSize, overWrite);
    }


}
