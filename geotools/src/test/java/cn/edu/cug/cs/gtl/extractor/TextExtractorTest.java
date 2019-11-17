package cn.edu.cug.cs.gtl.extractor;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.io.File;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextExtractorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void parseToString() {
    }

    @Test
    public void parseToStrings() {
    }

    @Test
    public void parseToParagraphsDBF() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"shape"+File.separator+"边界.DBF";
            String s = TextExtractor.parseToString(inputFile);
            System.out.println(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void parseToParagraphsSHP() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"shape"+File.separator+"边界.SHP";
            String s = TextExtractor.parseToString(inputFile);
            System.out.println(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void parseToParagraphsDWG() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"autocad"+File.separator+"test2000.dwg";
            String s = TextExtractor.parseToString(inputFile);

            System.out.println(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    @Test
    public void parseToParagraphsPDF() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"office"+File.separator+"introduction.pdf";
            String[] paragraphs = TextExtractor.parseToStrings(inputFile);
            for (String s : paragraphs) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void parseToParagraphsDOC() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"office"+File.separator+"新闻联播.doc";
            String[] paragraphs = TextExtractor.parseToStrings(inputFile);
            for (String s : paragraphs) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void parseToParagraphsDOCX() {
        try {
            String inputFile = Config.getTestInputDirectory()+ File.separator+"geotools"+File.separator
                    +"dat"+File.separator+"raw"+File.separator+"office"+File.separator+"新闻联播.docx";
            String[] paragraphs = TextExtractor.parseToStrings(inputFile);
            for (String s : paragraphs) {
                System.out.println(s);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}