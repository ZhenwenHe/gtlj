package gtl.data.sdb;

import gtl.data.DataSolution;
import gtl.io.storage.FileSystem;

import java.io.IOException;


public interface DataExporter {
    /**
     * 将DataSolution导出到Path所指的数据中
     * @param dataSolution
     * @param path
     * @return 如果成功，true;否则返回false
     * @throws IOException
     */
    boolean exportSolution(DataSolution dataSolution, String path , FileSystem fileSystem) throws IOException;
}
