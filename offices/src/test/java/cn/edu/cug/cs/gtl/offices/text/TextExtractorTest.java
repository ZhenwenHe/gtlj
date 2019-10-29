package cn.edu.cug.cs.gtl.offices.text;

import org.junit.Test;

public class TextExtractorTest {

    @Test
    public void parseToParagraphsPDF() {
        String inputFile = "dat/word.pdf";
        String[] paragraphs = TextExtractor.parseToParagraphs(inputFile);
        for (String s : paragraphs) {
            System.out.println(s);
        }
    }

    @Test
    public void parseToParagraphsDOC() {
        String inputFile = "dat/word.doc";
        String[] paragraphs = TextExtractor.parseToParagraphs(inputFile);
        for (String s : paragraphs) {
            System.out.println(s);
        }
    }

    @Test
    public void parseToParagraphsDOCX() {
        String inputFile = "dat/word.docx";
        String[] paragraphs = TextExtractor.parseToParagraphs(inputFile);
        for (String s : paragraphs) {
            System.out.println(s);
        }
    }

}