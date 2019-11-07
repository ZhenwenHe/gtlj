package cn.edu.cug.cs.gtl.lucene.document;


import cn.edu.cug.cs.gtl.lucene.file.DocumentFileFilter;
import cn.edu.cug.cs.gtl.lucene.file.DocumentFileVisitor;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentIndexer {
    private FileFilter fileFilter;
    private DocumentMapper documentMapper;
    private String indexPath;
    private String docsPath;
    private boolean bUpdate;
    private Analyzer analyzer;


    /**
     *
     * @param indexPath
     * @param docsPath
     * @return
     */
    public static DocumentIndexer of(String indexPath, String docsPath){
        return new DocumentIndexer(indexPath,docsPath, DocumentFileFilter.allFileFilter(),DocumentMapper.rawMapper(),false);
    }

    /**
     *
     */
    public void create(){
        this.bUpdate=false;
        execute();
    }

    /**
     *
     */
    public void update(){
        this.bUpdate=true;
        execute();
    }
    /**
     *
     * @return
     */
    public DocumentMapper getDocumentMapper() {
        return documentMapper;
    }

    /**
     *
     * @param documentMapper
     */
    public void setDocumentMapper(DocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    /**
     *
     * @return
     */
    public FileFilter getFileFilter() {
        return fileFilter;
    }

    /**
     *
     * @param fileFilter
     */
    public void setFileFilter(FileFilter fileFilter) {
        this.fileFilter = fileFilter;
    }

    /**
     *
     * @return
     */
    public Analyzer getAnalyzer() {
        return analyzer;
    }

    /**
     *
     * @param analyzer
     */
    public void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    /**
     *
     * @param fileFilter
     * @param indexPath
     * @param docsPath
     * @param bUpdate
     */
    DocumentIndexer(String indexPath, String docsPath, FileFilter fileFilter, DocumentMapper dm, boolean bUpdate) {
        this.fileFilter = fileFilter;
        this.indexPath = indexPath;
        this.docsPath = docsPath;
        this.bUpdate = bUpdate;
        this.analyzer = new StandardAnalyzer();
        if(dm==null)
            this.documentMapper= DocumentMapper.fileMapper();
        else
            this.documentMapper=dm;
    }

    /**
     * Index all text files under a directory.
     */
    private void execute() {
        String usage = "java IndexFiles"
                + " [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\n"
                + "This indexes the documents in DOCS_PATH, creating a Lucene index"
                + "in INDEX_PATH that can be searched with SearchFiles";

        boolean create = !bUpdate;

        if (docsPath == null) {
            System.err.println("Usage: " + usage);
            System.exit(1);
        }

        final Path docDir = Paths.get(docsPath);
        if (!Files.isReadable(docDir)) {
            System.out.println("Document directory '" + docDir.toAbsolutePath() + "' does not exist or is not readable, please check the path");
            System.exit(1);
        }

        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexPath + "'...");

            Directory dir = FSDirectory.open(Paths.get(indexPath));
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            if (create) {
                // Create a new index in the directory, removing any
                // previously indexed documents:
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            } else {
                // Add new documents to an existing index:
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            }

            // Optional: for better indexing performance, if you
            // are indexing many documents, increase the RAM
            // buffer.  But if you do this, increase the max heap
            // size to the JVM (eg add -Xmx512m or -Xmx1g):
            //
            // iwc.setRAMBufferSizeMB(256.0);

            IndexWriter writer = new IndexWriter(dir, iwc);
            indexDocs(writer, docDir);

            // NOTE: if you want to maximize search performance,
            // you can optionally call forceMerge here.  This can be
            // a terribly costly operation, so generally it's only
            // worth it when your index is relatively static (ie
            // you're done adding documents to it):
            //
            // writer.forceMerge(1);

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    /**
     * Indexes the given file using the given writer, or if a directory is given,
     * recurses over files and directories found under the given directory.
     * <p>
     * NOTE: This method indexes one document per input file.  This is slow.  For good
     * throughput, put multiple documents into your input file(s).  An example of this is
     * in the benchmark module, which can create "line doc" files, one document per line,
     * using the
     * <a href="../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
     * >WriteLineDocTask</a>.
     *
     * @param writer Writer to the index where the given file/dir info will be stored
     * @param path   The file to index, or the directory to recurse into to find files to index
     * @throws IOException If there is a low-level I/O error
     */
    private void indexDocs(final IndexWriter writer, Path path) throws IOException {
        if (Files.isDirectory(path)) {
            IndexerFileVisitor indexerFileVisitor = new IndexerFileVisitor(this.fileFilter,this.documentMapper,writer);
            Files.walkFileTree(path, indexerFileVisitor);
        } else {
            if(this.fileFilter.accept(path.toFile())) {
                indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis(),this.documentMapper);
            }
        }
    }

    /**
     * Indexes a single file
     */
    static void indexDoc(IndexWriter writer, Path file, long lastModified, DocumentMapper dm) throws IOException {
        File f = file.toFile();
        List<Document> docs = new ArrayList<>();
        //如果是RAW，任何类型的文件都可以处理
        if(dm.getMappingType()==DocumentMapper.DM_DOC_PRE_RAW_FILE){
            List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(),dm,f.lastModified());
            if(ls!=null && ls.isEmpty()==false){
                docs.addAll(ls);
            }
        }
        else{ //文本处理，必须是可以提取文本的文件类型，所以只处理Text类和Office类的文件

            FileFilter ff = DocumentFileFilter.or(DocumentFileFilter.textsFileFilter(),DocumentFileFilter.officesFileFilter());
            if(ff.accept(f)){
                List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(),dm,f.lastModified());
                if(ls!=null && ls.isEmpty()==false){
                    docs.addAll(ls);
                }
            }
            else {//属于不能提取文本的RAW文件，强行以RAW方式建立文档
                List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(),DocumentMapper.rawMapper(),f.lastModified());
                if(ls!=null && ls.isEmpty()==false){
                    docs.addAll(ls);
                }
            }
        }

       for(Document doc: docs) {
           if (writer.getConfig().getOpenMode() == IndexWriterConfig.OpenMode.CREATE) {
               // New index, so we just add the document (no old document can be there):
               System.out.println("adding " + file);
               writer.addDocument(doc);
           } else {
               // Existing index (an old copy of this document may have been indexed) so
               // we use updateDocument instead to replace the old one matching the exact
               // path, if present:
               System.out.println("updating " + file);
               writer.updateDocument(new Term("path", file.toString()), doc);
           }
       }
    }

    /**
     *
     */
    private static class IndexerFileVisitor extends DocumentFileVisitor<Path>{

        private IndexWriter writer;

        public IndexerFileVisitor(FileFilter fileFilter, DocumentMapper documentMapper,IndexWriter w) {
            super(fileFilter, documentMapper);
            this.writer=w;
        }

        public IndexerFileVisitor(FileFilter fileFilter, int documentMapper,IndexWriter w) {
            super(fileFilter, documentMapper);
            this.writer=w;
        }
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            try {
                if(getFileFilter().accept(file.toFile())){
                    indexDoc(writer, file, attrs.lastModifiedTime().toMillis(),getDocumentMapper());
                }
            } catch (IOException ignore) {
                // don't index files that can't be read.
            }
            return FileVisitResult.CONTINUE;
        }
    }
}
