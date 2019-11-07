package cn.edu.cug.cs.gtl.lucene.file;

import cn.edu.cug.cs.gtl.lucene.document.DocumentMapper;

import java.io.FileFilter;
import java.nio.file.SimpleFileVisitor;

public class DocumentFileVisitor<T> extends SimpleFileVisitor<T> {
    protected FileFilter fileFilter;
    protected DocumentMapper documentMapper;

    public FileFilter getFileFilter() {
        return fileFilter;
    }

    public DocumentMapper getDocumentMapper(){return this.documentMapper;}

    public DocumentFileVisitor(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
        this.documentMapper =  DocumentMapper.fileMapper();
    }

    public DocumentFileVisitor(FileFilter fileFilter, DocumentMapper documentMapper) {
        this.fileFilter = fileFilter;
        if(documentMapper==null)
            this.documentMapper = DocumentMapper.fileMapper();
        else
            this.documentMapper=documentMapper;
    }

    public DocumentFileVisitor(FileFilter fileFilter, final int  documentMapper) {
        this.fileFilter = fileFilter;
        this.documentMapper = DocumentMapper.of(documentMapper);
    }
}