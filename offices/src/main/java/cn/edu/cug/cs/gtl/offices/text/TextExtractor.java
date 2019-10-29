package cn.edu.cug.cs.gtl.offices.text;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.tika.Tika;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TextExtractor {
    /**
     * extract text from a file, that file may be a word (doc,docx),
     * pdf, excel, powerpoint, access, autoCAD(dwg,dxf).
     *
     * @param inputFile the file name
     * @return text from the file
     */
    public static String parseToString(String inputFile) {
        try {
            String extFile = inputFile.substring(inputFile.lastIndexOf('.') + 1);
            if (extFile.toLowerCase().equals("docx")) {
                POIXMLDocument.openPackage(inputFile);
                OPCPackage opcPackage = POIXMLDocument.openPackage(inputFile);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                String text = extractor.getText();
                return text;
            } else {
                Tika tika = new Tika();
                String text = tika.parseToString(new File(inputFile));
                return text;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * extract text from a file, that file may be a word (doc,docx),
     * pdf, excel, powerpoint, access, autoCAD(dwg,dxf).
     *
     * @param inputFile the file name
     * @return paragraphs text from the file
     */
    public static String[] parseToParagraphs(String inputFile) {
        try {
            String extFile = inputFile.substring(inputFile.lastIndexOf('.') + 1);
            if (extFile.toLowerCase().equals("docx")) {
                POIXMLDocument.openPackage(inputFile);
                OPCPackage opcPackage = POIXMLDocument.openPackage(inputFile);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                String text = extractor.getText();

                String[] paragraphs = StringUtils.split(text, StringUtils.LF);
                return paragraphs;
            } else if (extFile.toLowerCase().equals("pdf")) {
                Tika tika = new Tika();
                String text = tika.parseToString(new File(inputFile));
                String[] lines = StringUtils.split(text, StringUtils.LF);
                text = null;
                //lines to Paragraph; all paragraph begin with '\t' or space
                StringBuilder sb = new StringBuilder();
                List<String> paragraphs = new ArrayList<>();
                for (String s : lines) {
                    if (s.isEmpty()) continue;
                    if (s.charAt(0) == ' ' || s.charAt(0) == '\t') {
                        if (sb != null) {
                            String str = sb.toString().trim();
                            if (!str.isEmpty())
                                paragraphs.add(str);
                        }

                        sb = new StringBuilder();
                        sb.append(s);
                    } else {
                        sb.append(s);
                    }
                }

                if (sb != null) {
                    String s = sb.toString().trim();
                    if (!s.isEmpty())
                        paragraphs.add(s);
                }
                String[] paras = new String[paragraphs.size()];
                paragraphs.toArray(paras);
                return paras;
            } else {
                Tika tika = new Tika();
                String text = tika.parseToString(new File(inputFile));
                String[] paragraphs = StringUtils.split(text, StringUtils.LF);
                return paragraphs;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
