package cn.edu.cug.cs.gtl.filter;


import cn.edu.cug.cs.gtl.io.File;

public interface FileFilter extends java.io.FileFilter{
    default boolean accept(File pathname){
        return  this.accept((java.io.File)pathname);
    }
    default boolean accept(String pathname){
        return this.accept(new File(pathname));
    }

    static FileFilter officesFileFilter(){ return  new OfficesFileFilter();}
    static FileFilter textsFileFilter(){ return  new TextsFileFilter();}
    static FileFilter imagesFileFilter(){ return  new ImagesFileFilter();}
    static FileFilter shapesFileFilter(){ return  new ShapesFileFilter();}
    static FileFilter allFileFilter(){ return  new AllFileFilter();}

    static FileFilter or(FileFilter... filters){
        return new CompoundFileFilter(true,filters);
    }

    static FileFilter and(FileFilter... filters){
        return new CompoundFileFilter(false,filters);
    }

    static FileFilter or(String ... filters){
        return new UDFileFilter(true,filters);
    }

    static FileFilter and(String ... filters){
        return new UDFileFilter(false,filters);
    }
}
