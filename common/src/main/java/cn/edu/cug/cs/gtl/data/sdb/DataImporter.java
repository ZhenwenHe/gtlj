package cn.edu.cug.cs.gtl.data.sdb;

import cn.edu.cug.cs.gtl.io.storage.FileSystem;
import cn.edu.cug.cs.gtl.data.DataSolution;
import cn.edu.cug.cs.gtl.io.storage.FileSystem;

import java.io.IOException;

public interface DataImporter {

    /**
     * 将Path所指的数据导入到DataSolution中
     *
     * @param dataSolution
     * @param path
     * @return 如果成功，true;否则返回false
     * @throws IOException
     */
    boolean importSolution(DataSolution dataSolution, String path, FileSystem fileSystem) throws IOException;
}
