package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.filter.FileFilter;

import java.io.File;

class OfficesFileFilter implements FileFilter {
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
        return path.endsWith("doc")
                ||path.endsWith("docx")
                ||path.endsWith("pdf")
                ||path.endsWith("xsl")
                ||path.endsWith("ppt")
                ||path.endsWith("xslx")
                ||path.endsWith("pptx");
    }
}
