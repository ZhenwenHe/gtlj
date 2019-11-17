package cn.edu.cug.cs.gtl.solr.document;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.filter.FileFilter;
import cn.edu.cug.cs.gtl.protoswrapper.DocumentMapperWrapper;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DocumentCreatorTest {

    static String rawDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"raw";

    static String idxDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"inx";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void of() throws Exception {
        List<SolrInputDocument> docs = DocumentCreator
                .of(rawDir,
                        FileFilter.textsFileFilter(),
                        DocumentMapperWrapper.fileMapper())
                .execute();


    }

    @Test
    public void execute() {
    }
}