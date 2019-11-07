package cn.edu.cug.cs.gtl.lucene.file;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class CompoundFileFilter implements DocumentFileFilter {
    List<FileFilter> filters;
    boolean orOperation;

    public CompoundFileFilter(List<FileFilter> filters, boolean orOperation) {
        this.filters = filters;
        this.orOperation=orOperation;
    }

    public CompoundFileFilter(boolean orOperation, FileFilter ... filters) {
        this.filters = new ArrayList<>();
        for(FileFilter f: filters)
            this.filters.add(f);
        this.orOperation=orOperation;
    }
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
        if(orOperation){
            for(FileFilter f: this.filters){
                if(f.accept(pathname))
                    return true;
            }
            return false;
        }
        else {
            for(FileFilter f: this.filters){
                if(!f.accept(pathname))
                    return false;
            }
            return true;
        }
    }
}
