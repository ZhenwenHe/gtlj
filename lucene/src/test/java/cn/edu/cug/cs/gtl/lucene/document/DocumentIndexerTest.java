package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.filter.FileFilter;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.junit.Test;

public class DocumentIndexerTest {

    @Test
    public void createIndex() {
        DocumentIndexer di = DocumentIndexer.of("dat/idx","dat/raw");
        di.setFileFilter(FileFilter.allFileFilter());
        di.setDocumentMapper(DocumentMapper.paragraphMapper());
        di.setAnalyzer(new SmartChineseAnalyzer());
        di.create();
    }
    @Test
    public void updateIndex() {
        DocumentIndexer di = DocumentIndexer.of("dat/idx","dat/raw");
        di.setFileFilter(FileFilter.allFileFilter());
        di.setDocumentMapper(DocumentMapper.paragraphMapper());
        di.setAnalyzer(new SmartChineseAnalyzer());
        di.update();
    }
}