package cn.edu.cug.cs.gtl.solr.document;

import cn.edu.cug.cs.gtl.extractor.DocumentExtractor;
import cn.edu.cug.cs.gtl.filter.FileFilter;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.protos.*;
import cn.edu.cug.cs.gtl.protoswrapper.BoundingBoxWrapper;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    public static DocumentCreator of(String docsPath, FileFilter fileFilter, DocumentMapper documentMapper){
        if(fileFilter==null)
            fileFilter = FileFilter.allFileFilter();
        return new DocumentCreator(docsPath,fileFilter, documentMapper);
    }

    public List<SolrInputDocument> execute() throws Exception{
        Path p = new File(docsPath).toPath();
        List<Document> ls = new ArrayList<>();
        if (Files.isDirectory(p)) {
            CreatorFileVisitor v = new CreatorFileVisitor(fileFilter,documentMapper,ls);
            Files.walkFileTree(p, v);
        } else {
            if(fileFilter.accept(p.toFile())) {
                Document doc = DocumentExtractor.parseToDocument(p.toFile());
                if(doc!=null)
                    ls.add(doc);
            }
        }
        List<SolrInputDocument> solrInputDocuments = new ArrayList<>();
        for(Document d: ls){
            List<SolrInputDocument> solrInputDocument = documentToSolrInputDocument(d);
            if(solrInputDocument!=null)
                solrInputDocuments.addAll(solrInputDocument);
        }
        return solrInputDocuments;
    }
    /**
     *
     * @param docsPath
     * @param fileFilter
     * @param documentMapper
     */
    private DocumentCreator(String docsPath, FileFilter fileFilter, DocumentMapper documentMapper) {
        this.docsPath = docsPath;
        this.fileFilter = fileFilter;
        this.documentMapper = documentMapper;
    }

    /**
     *
     * @param d
     * @return
     */
    private SolrInputDocument documentToSolrInputDocumentForCommonFields(Document d){
        SolrInputDocument doc = new SolrInputDocument();
        /*
         string title = 1;
        repeated string keyword = 2;
        string version = 3;
        string type = 4;
        string url = 5;
        repeated string author = 6;
        repeated string affiliation = 7;
        string abstract = 8;
        string schema=9;
        repeated string content=10;
        bytes raw_data = 11;
        Attachments attachments=12;
        BoundingBox  bounding=13;//location
        */
        doc.addField("id", UUID.randomUUID().toString());

        String s = d.getTitle();
        if(!s.isEmpty())
            doc.addField("title",s);

        StringBuilder keywords = new StringBuilder();
        for(String k : d.getKeywordList()){
            if(!k.isEmpty()){
                keywords.append(k);
                keywords.append(FileDataSplitter.TAB);
            }
        }
        s = keywords.toString().trim();
        if(!s.isEmpty())
            doc.addField("keywords",s);

        s = d.getVersion();
        if(!s.isEmpty())
            doc.addField("version", s);

        s=d.getType();
        if(!s.isEmpty())
            doc.addField("type", s);

        StringBuilder authors = new StringBuilder();
        for(String a : d.getAuthorList()){
            if(!a.isEmpty()){
                keywords.append(a);
                keywords.append(FileDataSplitter.TAB);
            }
        }
        s = authors.toString().trim();
        if(!s.isEmpty())
            doc.addField("authors",s );

        s = d.getUrl();
        if(!s.isEmpty())
            doc.addField("url",d.getUrl());

        StringBuilder affiliations = new StringBuilder();
        for(String a : d.getAffiliationList()){
            if(!a.isEmpty()){
                affiliations.append(a);
                affiliations.append(FileDataSplitter.TAB);
            }
        }
        s = affiliations.toString().trim();
        if(!s.isEmpty())
            doc.addField("affiliations",s);

        s=d.getAbstract();
        if(!s.isEmpty())
            doc.addField("abstract",s);

        s= d.getSchema();
        if(!s.isEmpty())
            doc.addField("schema",s);


        BoundingBox bb = d.getBounding();
        if(bb!=null){
            String bounding = BoundingBoxWrapper.toWKT(bb);
            doc.addField("bounding",bounding);
        }


        return doc;
    }


    /**
     *
     * @param d
     * @return
     */
    private List<SolrInputDocument> documentToSolrInputDocument(Document d){
        List<SolrInputDocument> ls = new ArrayList<>();
        if(documentMapper.getMappingType()==DocumentMapper.MappingType.DOC_PRE_RAW_FILE_VALUE ||
                documentMapper.getMappingType()==DocumentMapper.MappingType.DOC_PRE_RAW_FILE_VALUE){
            SolrInputDocument doc = documentToSolrInputDocumentForCommonFields(d);

            StringBuilder sb = new StringBuilder();
            for(String s: d.getContentList()){
                sb.append(s);
                sb.append("\n");
            }
            String s = sb.toString();
            if(!s.isEmpty())
                doc.addField("contents",s);

            byte [] raw_data = d.getRawData().asReadOnlyByteBuffer().array();
            if(raw_data!=null && raw_data.length>0)
                doc.addField("raw_data",raw_data);

            byte[] attachments = d.getAttachments().toByteArray();
            if(attachments!=null&&attachments.length>0)
                doc.addField("attachments",attachments);

            ls.add(doc);
        }
        if(documentMapper.getMappingType()==DocumentMapper.MappingType.DOC_PRE_TEXT_PARAGRAPH_VALUE ||
                documentMapper.getMappingType()==DocumentMapper.MappingType.DOC_PRE_TEXT_LINE_VALUE) {

            int i=0;
            for(String str: d.getContentList()){
                SolrInputDocument doc = documentToSolrInputDocumentForCommonFields(d);

                String s = str;
                if(!s.isEmpty())
                    doc.addField("contents",s);

                if(i==0){
                    byte [] raw_data = d.getRawData().asReadOnlyByteBuffer().array();
                    if(raw_data!=null && raw_data.length>0)
                        doc.addField("raw_data",raw_data);

                    byte[] attachments = d.getAttachments().toByteArray();
                    if(attachments!=null&&attachments.length>0)
                        doc.addField("attachments",attachments);

                    doc.addField("order",i);
                    i++;
                }
                else{
                    doc.addField("order",i);
                    i++;
                }


                ls.add(doc);
            }

        }
        return ls;
    }

    /**
     *
     * @param t
     * @return
     */
    private SolrInputDocument tableToSolrInputDocument(Table t){
        return null;
    }


    /**
     *
     */
    class CreatorFileVisitor extends SimpleFileVisitor<Path>{
        protected FileFilter fileFilter;
        protected DocumentMapper documentMapper;
        List<Document> documentList;

        CreatorFileVisitor(FileFilter fileFilter, DocumentMapper documentMapper, List<Document> documentList) {
            this.fileFilter = fileFilter;
            this.documentMapper = documentMapper;
            this.documentList = documentList;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                throws IOException
        {
            Objects.requireNonNull(file);
            Objects.requireNonNull(attrs);

            if(fileFilter.accept(file.toFile())){
                try {
                    Document doc = DocumentExtractor.parseToDocument(file.toFile());
                    if(doc!=null){
                        documentList.add(doc);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            return FileVisitResult.CONTINUE;
        }

    }
}
