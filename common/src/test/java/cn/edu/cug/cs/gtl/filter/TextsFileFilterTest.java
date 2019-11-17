package cn.edu.cug.cs.gtl.filter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TextsFileFilterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void accept() {
        FileFilter textsFileFilter= FileFilter.textsFileFilter();
        boolean b= textsFileFilter.accept(new File("dat/raw/word.doc"));
        Assert.assertTrue(!b);
    }
}