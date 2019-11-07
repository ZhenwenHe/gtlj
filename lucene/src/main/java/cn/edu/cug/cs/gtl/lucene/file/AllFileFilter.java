package cn.edu.cug.cs.gtl.lucene.file;

import java.io.File;

class AllFileFilter implements DocumentFileFilter {
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
