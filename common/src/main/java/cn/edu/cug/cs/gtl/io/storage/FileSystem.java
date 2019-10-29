package cn.edu.cug.cs.gtl.io.storage;


import cn.edu.cug.cs.gtl.io.Storable;

import java.io.*;
import java.net.URI;
import java.util.List;

/**
 * FileSystemManager接口表示一个抽象文件系统管理器
 * 通过该接口可以操作管理一个抽象文件系统
 *
 * @author Yang Li
 */
public interface FileSystem extends Storable {

    public static final int DEFAULT_BUFFER_SIZE = 1024 * 128;

    /**
     * 通过指定路径生成对应的URI
     *
     * @param path 指定的路径
     * @return 生成的URI
     */
    URI toURI(String path);

    /**
     * 打开当前文件系统
     *
     * @throws IOException
     */
    void open() throws IOException;

    /**
     * 关闭当前文件系统
     *
     * @throws IOException
     */
    void close() throws IOException;

    /**
     * 获取指定路径文件大小
     *
     * @param path 指定的路径
     * @return 指定路径文件大小
     * @throws IOException
     */
    long getLength(String path) throws IOException;

    /**
     * 获得指定父路径下子路径的路径字符串
     *
     * @param parent 父路径
     * @param child  子路径
     * @return 指定父路径下子路径的路径字符串
     */
    String getPath(String parent, String child);


    String getPath(String parent, String... children);

    /**
     * 获得指定路径的父路径
     *
     * @param path 指定的路径
     * @return 指定路径的父路径
     */
    String getParentPath(String path);

    /**
     * 判断指定路径在该文件系统中是否存在。
     *
     * @param path 指定的路径
     * @return 是否存在
     * @throws IOException
     */
    boolean exists(String path) throws IOException;

    /**
     * 返回指定路径表示的文件或目录的名称。
     *
     * @param path 指定的路径
     * @return 指定路径表示的文件或目录的名称
     */
    String getName(String path);

    /**
     * 创建指定的路径对应的目录，包括所有必需但不存在的父目录。
     *
     * @param path 指定的路径
     * @return 当且仅当指定目录以及所有必需的父目录创建成功时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean mkdirs(String path) throws IOException;

    /**
     * 创建指定路径所对应的文件。
     *
     * @param path 指定的路径
     * @return 如果指定的文件不存在并成功地创建，则返回 true；如果指定的文件已经存在，则返回 false
     */
    boolean createNewFile(String path) throws IOException;

    /**
     * 返回指定路径表示的目录中的所有的文件及目录的路径。
     *
     * @param path 指定路径
     * @return 指定路径表示的目录中的所有的文件及目录的路径
     * @throws IOException
     */
    List<String> getFilePaths(String path) throws IOException;

    /**
     * 判断指定路径在该文件系统中是否对应一个目录。
     *
     * @param path 指定路径
     * @return 指定路径在该文件系统中对应一个目录，则返回true；否则返回false；
     * @throws IOException
     */
    boolean isDirectory(String path) throws IOException;

    /**
     * 判断指定路径在该文件系统中是否对应一个标准文件。
     *
     * @param path 指定路径
     * @return 指定路径在该文件系统中对应一个标准文件，则返回true；否则返回false；
     * @throws IOException
     */
    boolean isFile(String path) throws IOException;

    /**
     * 返回指定路径对应的DataOutput。
     *
     * @param path   指定路径
     * @param append 是否追加
     * @return 指定路径对应的DataOutput
     * @throws IOException
     */
    DataOutputStream getOutput(String path, boolean append) throws IOException;

    /**
     * 返回指定路径对应的DataOutput。
     *
     * @param path       指定路径
     * @param bufferSize 缓存大小
     * @param append     是否追加
     * @return 指定路径对应的DataOutput
     * @throws IOException
     */
    default DataOutputStream getBufferedOutput(String path, int bufferSize, boolean append) throws IOException {
        return new DataOutputStream(new BufferedOutputStream(getOutput(path, append), bufferSize));
    }

    /**
     * 返回指定路径对应的DataInput。
     *
     * @param path 指定路径
     * @return 指定路径对应的DataInput
     * @throws IOException
     */
    DataInputStream getInput(String path) throws IOException;

    /**
     * 返回指定路径对应的DataInput。
     *
     * @param path         指定路径
     * @param bufferedSize 缓存大小
     * @return 指定路径对应的DataInput
     * @throws IOException
     */
    default DataInputStream getBufferedInput(String path, int bufferedSize) throws IOException {
        return new DataInputStream(new BufferedInputStream(getInput(path), bufferedSize));
    }

    /**
     * 删除指定路径对应的目录或文件。
     *
     * @param path 指定路径
     * @return 当且仅当成功删除文件或目录时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean delete(String path) throws IOException;

    /**
     * 当前文件系统管理器关闭时，尝试删除指定路径对应的目录或文件。
     *
     * @param path 指定路径
     * @throws IOException
     */
    void deleteOnExit(String path) throws IOException;

    /**
     * 重命名当前的路径表示的文件。
     *
     * @param srcPath  原始路径
     * @param destPath 目标路径
     * @return 当且仅当重命名成功时，返回 true；否则返回 false
     * @throws IOException
     */
    boolean renameTo(String srcPath, String destPath) throws IOException;

    /**
     * 强制将指定目录下的所有用户写操作持久化到存储系统中。
     *
     * @param path 指定的目录
     * @throws IOException
     */
    void force(String path) throws IOException;

    /**
     * 将指定对象写出到指定的路径对应的文件中。
     *
     * @param obj    指定对象
     * @param path   指定路径
     * @param append 是否采用追加模式
     * @return 如果成功写出，则返回true；否在返回false
     * @throws IOException
     */
    default boolean write(Storable obj, String path, boolean append) throws IOException {
        DataOutputStream out = getOutput(path, append);
        try {
            out.write(obj.storeToByteArray());
            return true;
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }

    }

    /**
     * 从指定路径对应的文件中读入指定对象的加载信息。
     *
     * @param obj  指定对象
     * @param path 指定路径
     * @return 如果成功读入，则返回true；否在返回false
     * @throws IOException
     */
    default boolean read(Storable obj, String path) throws IOException {
        DataInputStream in = getInput(path);
        try {
            return obj.load(in);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }


}
