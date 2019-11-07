package cn.edu.cug.cs.gtl.lucene.file;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public interface DocumentFileFilter extends FileFilter {


    static FileFilter officesFileFilter(){ return  new OfficesFileFilter();}
    static FileFilter textsFileFilter(){ return  new TextsFileFilter();}
    static FileFilter imagesFileFilter(){ return  new ImagesFileFilter();}
    static FileFilter shapesFileFilter(){ return  new ShapesFileFilter();}
    static FileFilter allFileFilter(){ return  new AllFileFilter();}

    static FileFilter or(FileFilter ... filters){
        return new CompoundFileFilter(true,filters);
    }

    static FileFilter and(FileFilter ... filters){
        return new CompoundFileFilter(false,filters);
    }

    static FileFilter or(String ... filters){
        return new UDFileFilter(true,filters);
    }

    static FileFilter and(String ... filters){
        return new UDFileFilter(false,filters);
    }
}
