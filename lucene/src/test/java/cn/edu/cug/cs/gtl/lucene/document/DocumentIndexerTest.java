package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.lucene.document.DocumentIndexer;
import cn.edu.cug.cs.gtl.lucene.filefilter.AllFileFilter;
import cn.edu.cug.cs.gtl.lucene.filefilter.TextsFileFilter;
import org.junit.Test;

public class DocumentIndexerTest {

    @Test
    public void indexDoc() {
        DocumentIndexer.of("dat/idx","dat/raw",new AllFileFilter(),DocumentMapper.paragraphMapper(),false).execute();
    }
}