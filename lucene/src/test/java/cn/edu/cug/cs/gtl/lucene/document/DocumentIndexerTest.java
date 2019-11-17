package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.filter.FileFilter;
import cn.edu.cug.cs.gtl.protoswrapper.DocumentMapperWrapper;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.junit.Test;

public class DocumentIndexerTest {

    static String rawDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"raw";

    static String idxDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"inx";

    @Test
    public void createIndex() {
        DocumentIndexer di = DocumentIndexer.of(idxDir,rawDir);
        di.setFileFilter(FileFilter.allFileFilter());
        di.setDocumentMapper(DocumentMapperWrapper.paragraphMapper());
        di.setAnalyzer(new SmartChineseAnalyzer());
        di.create();
    }
    @Test
    public void updateIndex() {
        DocumentIndexer di = DocumentIndexer.of(idxDir,rawDir);
        di.setFileFilter(FileFilter.allFileFilter());
        di.setDocumentMapper(DocumentMapperWrapper.paragraphMapper());
        di.setAnalyzer(new SmartChineseAnalyzer());
        di.update();
    }
}