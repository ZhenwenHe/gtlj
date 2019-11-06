package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.lucene.filefilter.AllFileFilter;
import cn.edu.cug.cs.gtl.lucene.filefilter.OfficesFileFilter;
import org.apache.lucene.document.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class DocumentCreatorTest {

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
    public void createFromFileForDoc() {
        File f = new File("dat/raw/word.doc");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForDocx() {
        File f = new File("dat/raw/word.docx");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForPDF() {
        File f = new File("dat/raw/word.pdf");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(), DocumentMapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }

    @Test
    public void createFromFileForTXT() {
        File f = new File("dat/raw/introduction.txt");
        List<Document> ls = DocumentCreator.createFromFile(f.getAbsolutePath(),DocumentMapper.paragraphMapper(),f.lastModified());
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForPARAGRAPH() throws Exception{
        DocumentCreator dc = DocumentCreator.of("dat/raw",new OfficesFileFilter(),DocumentMapper.paragraphMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForFILE() throws Exception{
        DocumentCreator dc = DocumentCreator.of("dat/raw",new OfficesFileFilter(),DocumentMapper.fileMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
    @Test
    public void createFromPathForRAW() throws Exception{
        DocumentCreator dc = DocumentCreator.of("dat/raw",new AllFileFilter(),DocumentMapper.rawMapper());
        List<Document> ls = dc.execute();
        for(Document d: ls){
            System.out.println(d.toString());
        }
    }
}