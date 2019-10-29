package cn.edu.cug.cs.gtl.config;

import cn.edu.cug.cs.gtl.io.File;
import org.apache.hadoop.conf.Configuration;

import java.util.UUID;

public final class Config {
    /**
     * 只是构建Feature对象的时候，是否需要进行有效性验证，包括：
     * 1、几何对象的有效性
     * 2、每个属性值类型是否与FeatureType中的对应的属性值类型一致
     */
    public static boolean featureValidating = true;

    /**
     * 默认维度为3
     */
    private static int dimension = 3;

    private static String DEFAULT_DATA_DIR = System.getProperty("user.home") + File.separator + "git" + File.separator + "data";
    private static String DEFAULT_HDFS_DIR = "hdfs://namenode1:9000" + File.separator + "gtl" + File.separator + "data";

    public static String getTestInputDirectory() {
        return getDataDirectory();
    }

    public static String getTestOutputDirectory() {
        return getTestInputDirectory() + File.separator + "output";
    }

    public static void setDataDirectory(String dataRootDirectory) {
        if (dataRootDirectory != null) {
            dataRootDirectory = dataRootDirectory.trim();
            if (dataRootDirectory != null) {
                if (dataRootDirectory.charAt(dataRootDirectory.length() - 1) == File.separatorChar)
                    dataRootDirectory = dataRootDirectory.substring(0, dataRootDirectory.length() - 1);
                DEFAULT_DATA_DIR = dataRootDirectory;
            }
        }


    }

    public static String getDataDirectory() {
        return DEFAULT_DATA_DIR;
    }

    public static String getTemporaryDirectory() {
        return DEFAULT_DATA_DIR + File.separator + "temp";
    }

    public static int getDimension() {
        return dimension;
    }

    /**
     * 返回当前用户的Home目录
     */
    public static String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    /**
     * 返回本地默认数据目录
     *
     * @return
     */
    public static String getLocalDataDirectory() {
        return DEFAULT_DATA_DIR + File.separator + "dat";
    }

    /**
     * 返回本地默认临时数据目录
     *
     * @return
     */
    public static String getLocalTemporaryDirectory() {
        return DEFAULT_DATA_DIR + File.separator + "temp";
    }

    /**
     * 返回本地默认数据交换目录
     *
     * @return
     */
    public static String getLocalSwapDirectory() {
        return DEFAULT_DATA_DIR + File.separator + "swap";
    }

    /**
     * 返回本地交换文件名称（全名）
     *
     * @return
     */
    public static String getLocalSwapFile() {
        return getLocalSwapDirectory() + File.separator + UUID.randomUUID().toString();
    }

    /**
     * 返回默认的hdfs配置
     *
     * @return configuration
     */
    public static Configuration getDefaultHDFSConf() {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://namenode1:9000");
        configuration.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        return configuration;
    }

    /**
     * 返回HDFS的默认数据目录
     *
     * @return
     */
    public static String getHdfsDataDirectory() {
        return DEFAULT_HDFS_DIR + File.separator + "dat";
    }

    /**
     * 返回HDFS的默认临时数据目录
     *
     * @return
     */
    public static String getHdfsTemporaryDirectory() {
        return DEFAULT_HDFS_DIR + File.separator + "temp";
    }

    /**
     * 返回HDFS的默认数据交换目录
     *
     * @return
     */
    public static String getHdfsSwapDirectory() {
        return DEFAULT_HDFS_DIR + File.separator + "swap";
    }

    /**
     * 返回HDFS的默认数据交换文件名称（全名）
     *
     * @return
     */
    public static String getHdfsSwapFile() {
        return getHdfsSwapDirectory() + File.separator + UUID.randomUUID().toString();
    }
}
