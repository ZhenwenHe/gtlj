package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.filter.FileFilter;

import java.io.File;


class TextsFileFilter implements FileFilter {
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
        String path = pathname.getName().trim().toLowerCase();
        boolean b=  path.endsWith("txt")
                ||path.endsWith("json")
                ||path.endsWith("xml")
                ||path.endsWith("csv")
                ||path.endsWith("tsv")
                ||path.endsWith("html")
                ||path.endsWith("htm")
                ||path.endsWith("sql")
                ||path.endsWith("java")
                ||path.endsWith(".c")
                ||path.endsWith("cpp");
        return b;
    }
}
