package cn.edu.cug.cs.gtl.lucene.filefilter;

import java.io.File;
import java.io.FileFilter;

public class AllFileFilter implements FileFilter {
    /**
     * Tests whether or not the specified abstract pathname should be
     * included in a pathname list.
     *
     * @param pathname The abstract pathname to be tested
     * @return <code>true</code> if and only if <code>pathname</code>
     * should be included
     */
    @Override
    public boolean accept(File pathname) {
        return true;
    }
}
