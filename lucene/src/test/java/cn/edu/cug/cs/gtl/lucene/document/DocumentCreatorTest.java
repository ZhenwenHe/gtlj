package cn.edu.cug.cs.gtl.lucene.document;


import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.filter.FileFilter;
import cn.edu.cug.cs.gtl.protoswrapper.DocumentMapperWrapper;
import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DocumentCreatorTest {

    static String rawDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"lucene"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"raw";
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void of() {
    }

    @Test
    public void testOf() {
    }

    @Test
    public void execute() {
    }

    @Test
    public void createFromFileForDoc() throws Exception{
        File f = new File(rawDir+"/office/新闻联播.doc");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapperWrapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForDocx() throws Exception{
        File f = new File(rawDir+"/office/新闻联播.docx");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapperWrapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForPDF() throws Exception{
        File f = new File(rawDir+"/office/introduction.pdf");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapperWrapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForTXT()throws Exception {
        File f = new File(rawDir+"/text/beam.txt");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(),DocumentMapperWrapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForPARAGRAPH() throws Exception{
        DocumentCreator dc = DocumentCreator.of(rawDir, FileFilter.officesFileFilter(),DocumentMapperWrapper.paragraphMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForFILE() throws Exception{
        DocumentCreator dc = DocumentCreator.of(rawDir, FileFilter.officesFileFilter(),DocumentMapperWrapper.fileMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForRAW() throws Exception{
        DocumentCreator dc = DocumentCreator.of(rawDir, FileFilter.allFileFilter(),DocumentMapperWrapper.rawMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
}