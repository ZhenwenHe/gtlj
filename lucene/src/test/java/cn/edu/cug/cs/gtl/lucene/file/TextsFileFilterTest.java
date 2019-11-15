package cn.edu.cug.cs.gtl.lucene.file;

import cn.edu.cug.cs.gtl.filter.FileFilter;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TextsFileFilterTest {

    @Test
    public void accept() {
        FileFilter textsFileFilter= FileFilter.textsFileFilter();
        boolean b= textsFileFilter.accept(new File("dat/raw/word.doc"));
        Assert.assertTrue(!b);
    }
}