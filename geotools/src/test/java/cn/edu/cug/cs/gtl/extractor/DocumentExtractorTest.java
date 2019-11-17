package cn.edu.cug.cs.gtl.extractor;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.protos.Document;
import cn.edu.cug.cs.gtl.protoswrapper.DocumentWrapper;
import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentExtractorTest {


    static String rawDir = Config.getTestInputDirectory()+ cn.edu.cug.cs.gtl.io.File.separator+"geotools"+ cn.edu.cug.cs.gtl.io.File.separator
            +"dat"+ cn.edu.cug.cs.gtl.io.File.separator+"raw";



    @Test
    public void parseToDocumentForPDF() throws Exception{
        Document doc = DocumentExtractor
                .parseToDocument(
                        rawDir+ File.separator+"office"+File.separator+"introduction.pdf");
        DocumentWrapper.print(doc);
    }


    @Test
    public void parseToDocumentForWORD() throws Exception{
        Document doc2 = DocumentExtractor
                .parseToDocument(
                        rawDir+ File.separator+"office"+File.separator+"新闻联播.docx");
        DocumentWrapper.print(doc2);
    }

    @Test
    public void parseToDocumentForSHP() throws Exception{
        Document doc2 = DocumentExtractor
                .parseToDocument(
                        rawDir+ File.separator+"shape"+File.separator+"边界.SHP");
        DocumentWrapper.print(doc2);
    }

    @Test
    public void parseToDocumentForJPEG() throws Exception{
        Document doc2 = DocumentExtractor
                .parseToDocument(
                        rawDir+ File.separator+"image"+File.separator+"word_origin.jpg");
        DocumentWrapper.print(doc2);
    }

    @Test
    public void parseToDocumentWithAttachments() throws Exception{
        Document doc2 = DocumentExtractor
                .parseToDocument(
                        rawDir+ File.separator+"text"+File.separator+"text.txt",
                        rawDir+ File.separator+"text"+File.separator+"beam.txt",
                        rawDir+ File.separator+"text"+File.separator+"lucene.txt");
        DocumentWrapper.print(doc2);
    }

}