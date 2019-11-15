package cn.edu.cug.cs.gtl.filter;

import cn.edu.cug.cs.gtl.filter.FileFilter;

import java.io.File;

class ImagesFileFilter implements FileFilter {
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
        return path.endsWith("jpg")
                ||path.endsWith("jpeg")
                ||path.endsWith("bmp")
                ||path.endsWith("png")
                ||path.endsWith("tif")
                ||path.endsWith("tiff");
    }
}
