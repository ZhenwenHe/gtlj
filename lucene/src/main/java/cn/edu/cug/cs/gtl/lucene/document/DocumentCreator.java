package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.lucene.filefilter.AllFileFilter;
import cn.edu.cug.cs.gtl.lucene.filefilter.DocumentFileVisitor;
import cn.edu.cug.cs.gtl.lucene.text.TextExtractor;
import org.apache.lucene.document.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class DocumentCreator {

    private String docsPath;
    private FileFilter fileFilter;
    private DocumentMapper documentMapper;


    /**
     *
     * @param docsPath
     * @param fileFilter
     * @param documentMapper
     * @return
     */
    public static DocumentCreator of(String docsPath, FileFilter fileFilter,DocumentMapper documentMapper){
        if(fileFilter==null)
            fileFilter = new AllFileFilter();
        return new DocumentCreator(docsPath,fileFilter, documentMapper);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public List<Document> execute() throws IOException{
        Path p = new File(docsPath).toPath();
        if (Files.isDirectory(p)) {
            CreatorFileVisitor v = new CreatorFileVisitor(fileFilter,documentMapper);
            Files.walkFileTree(p, v);
            return v.getDocuments();
        } else {
            if(fileFilter.accept(p.toFile())) {
                return createFromFile(p.toFile().getCanonicalPath(),documentMapper,p.toFile().lastModified());
            }
        }
        return null;
    }


    /**
     * 为一个文件（WORD，PDF， Excel， PowerPoint等）创建文档；
     * 首选出改文件中提取所有文本，然后根据级别创建Lucene文档对象
     * 级别由level指定
     *
     * @param inputFile
     * @param level
     * @param lastModifiedTime
     * @return
     */
    public static List<Document> createFromFile(String inputFile, DocumentMapper level,long lastModifiedTime) {
        switch (level.getMappingType()) {
            case DocumentMapper.DM_DOC_PRE_TEXT_FILE: {
                List<Document> docs = new ArrayList<>();
                docs.add(createDoc4EachFile(inputFile,lastModifiedTime));
                return docs;
            }
            case DocumentMapper.DM_DOC_PRE_TEXT_LINE:{
                return createDoc4EachParagraph(inputFile,lastModifiedTime);
            }
            case DocumentMapper.DM_DOC_PRE_TEXT_PARAGRAPH: {
                return createDoc4EachParagraph(inputFile,lastModifiedTime);
            }
            case DocumentMapper.DM_DOC_PRE_RAW_FILE:{
                List<Document> docs = new ArrayList<>();
                Document doc = createDoc4EachRaw(inputFile,lastModifiedTime);
                docs.add(doc);
                return docs;
            }
            default: {
                List<Document> docs = new ArrayList<>();
                Document doc = createDoc4EachFile(inputFile,lastModifiedTime);
                docs.add(doc);
                return docs;
            }
        }
    }



    /**
     *
     * @param docsPath
     * @param fileFilter
     * @param documentMapper
     */
    DocumentCreator(String docsPath, FileFilter fileFilter, DocumentMapper documentMapper) {
        this.docsPath = docsPath;
        this.fileFilter = fileFilter;
        this.documentMapper=documentMapper;
    }

    /**
     * 为一个文件中的每个自然段构建一个文档对象
     *
     * @param inputFile
     * @return
     */
    private static List<Document> createDoc4EachParagraph(String inputFile, long lastModifiedTime) {
        String[] paragraphs = TextExtractor.parseToParagraphs(inputFile);
        List<Document> docs = new ArrayList<>();
        long i = 0;
        for (String p : paragraphs) {
            // make a new, empty document
            Document doc = new Document();

            // Add the path of the file as a field named "path".  Use a
            // field that is indexed (i.e. searchable), but don't tokenize
            // the field into separate words and don't index term frequency
            // or positional information:
            doc.add(new StringField("path", inputFile, Field.Store.YES));

            doc.add(new LongPoint("paragraph", i));



            // Add the last modified date of the file a field named "modified".
            // Use a LongPoint that is indexed (i.e. efficiently filterable with
            // PointRangeQuery).  This indexes to milli-second resolution, which
            // is often too fine.  You could instead create a number based on
            // year/month/day/hour/minutes/seconds, down the resolution you require.
            // For example the long value 2011021714 would mean
            // February 17, 2011, 2-3 PM.
            doc.add(new LongPoint("modified", lastModifiedTime));

            // Add the contents of the file to a field named "contents".  Specify a Reader,
            // so that the text of the file is tokenized and indexed, but not stored.
            // Note that FileReader expects the file to be in UTF-8 encoding.
            // If that's not the case searching for special characters will fail.
            Field contentField = new TextField("contents", paragraphs[(int)i], Field.Store.YES);
            doc.add(contentField);

            doc.add(new StringField("text", Long.valueOf(i).toString()+" "+ paragraphs[(int)i].trim(), Field.Store.YES));


            docs.add(doc);
            ++i;
        }
        return docs;
    }

    /**
     * 为一个文件创建一个文档，自然段默认为-1
     *
     * @param inputFile
     * @return
     */
    private static Document createDoc4EachFile(String inputFile,long lastModifiedTime) {
        String text = TextExtractor.parseToString(inputFile);

        // make a new, empty document
        Document doc = new Document();

        // Add the path of the file as a field named "path".  Use a
        // field that is indexed (i.e. searchable), but don't tokenize
        // the field into separate words and don't index term frequency
        // or positional information:
        Field pathField = new StringField("path", inputFile, Field.Store.YES);
        doc.add(pathField);

        // Add the last modified date of the file a field named "modified".
        // Use a LongPoint that is indexed (i.e. efficiently filterable with
        // PointRangeQuery).  This indexes to milli-second resolution, which
        // is often too fine.  You could instead create a number based on
        // year/month/day/hour/minutes/seconds, down the resolution you require.
        // For example the long value 2011021714 would mean
        // February 17, 2011, 2-3 PM.
        doc.add(new LongPoint("modified", lastModifiedTime));


        Field paragraphField = new LongPoint("paragraph", -1L);
        doc.add(pathField);

        // Add the contents of the file to a field named "contents".  Specify a Reader,
        // so that the text of the file is tokenized and indexed, but not stored.
        // Note that FileReader expects the file to be in UTF-8 encoding.
        // If that's not the case searching for special characters will fail.
        Field contentField = new TextField("contents", text, Field.Store.YES);
        doc.add(contentField);
        return doc;
    }

    /**
     *
     * @param inputFile
     * @param lastModifiedTime
     * @return
     */
    private static Document createDoc4EachRaw(String inputFile,long lastModifiedTime) {

        // make a new, empty document
        Document doc = new Document();

        // Add the path of the file as a field named "path".  Use a
        // field that is indexed (i.e. searchable), but don't tokenize
        // the field into separate words and don't index term frequency
        // or positional information:
        Field pathField = new StringField("path", inputFile, Field.Store.YES);
        doc.add(pathField);

        // Add the last modified date of the file a field named "modified".
        // Use a LongPoint that is indexed (i.e. efficiently filterable with
        // PointRangeQuery).  This indexes to milli-second resolution, which
        // is often too fine.  You could instead create a number based on
        // year/month/day/hour/minutes/seconds, down the resolution you require.
        // For example the long value 2011021714 would mean
        // February 17, 2011, 2-3 PM.
        doc.add(new LongPoint("modified", lastModifiedTime));

        // Add the contents of the file to a field named "contents".  Specify a Reader,
        // so that the text of the file is tokenized and indexed, but not stored.
        // Note that FileReader expects the file to be in UTF-8 encoding.
        // If that's not the case searching for special characters will fail.
        ByteBuffer bb = cn.edu.cug.cs.gtl.io.File.readRawFile(inputFile);
        doc.add(new StoredField( "contents",  bb.array()));
        return doc;

    }
    /**
     *
     */
    private static class CreatorFileVisitor extends DocumentFileVisitor<Path>{
        List<Document> docs=null;


        public CreatorFileVisitor(FileFilter fileFilter, DocumentMapper documentMapper) {
            super(fileFilter, documentMapper);
        }

        public CreatorFileVisitor(FileFilter fileFilter, int documentMapper) {
            super(fileFilter, documentMapper);
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                if(fileFilter.accept(file.toFile())){
                    List<Document> ls = createFromFile(file.toFile().getCanonicalPath(),documentMapper,attrs.lastModifiedTime().toMillis());
                    if(ls!=null && ls.isEmpty()==false){
                        if(docs==null){
                            docs=new ArrayList<>();
                        }
                        docs.addAll(ls);
                    }
                }
            } catch (IOException ignore) {
                // don't index files that can't be read.
            }
            return FileVisitResult.CONTINUE;
        }

        public List<Document> getDocuments(){
            return docs;
        }
    }

}
