package cn.edu.cug.cs.gtl.extractor;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.tika.Tika;
import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.geojson.feature.FeatureJSON;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextExtractor {
    /**
     * 利用geotools，打开shp文件，将每个Feature转成一个GeoJSON，
     * 然后返回拼接的每个字符串，返回一个总的字符串
     *
     * @param inputFileName 输入的shp文件名
     * @return 将该文件内容解析成JSON字符串, 每个字符串为一个Feature的GeoJSON值
     */
    private static List<String> parseShapeFileToStrings(String inputFileName) throws IOException {
        FileDataStore store = FileDataStoreFinder.getDataStore(new File(inputFileName));
        List<String> ss = new ArrayList<>();
        FeatureJSON featureJSON = new FeatureJSON();
        if(store.getSchema()!=null) {
            featureJSON.setFeatureType(store.getSchema());
        }
        var r = store.getFeatureReader();
        while (r.hasNext()) {
            var feature = r.next();
            ss.add(featureJSON.toString(feature));
        }
        return ss;
    }

    /**
     * extract text from a file, that file may be a word (doc,docx),
     * pdf, excel, powerpoint, access, autoCAD(dwg,dxf).
     *
     * @param inputFile the file name
     * @return text from the file
     */
    public static String parseToString(String inputFile) throws Exception {
        String extFile = inputFile.substring(inputFile.lastIndexOf('.') + 1).toLowerCase();
        if(extFile.equals("shp")){
            List<String> ss= parseShapeFileToStrings(inputFile);
            StringBuilder sb = new StringBuilder();
            for(String s: ss)
                sb.append(s);
            return sb.toString();
        }
        else if (extFile.equals("docx")) {
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
    }

    /**
     * extract text from a file, that file may be a word (doc,docx),
     * pdf, excel, powerpoint, access, autoCAD(dwg,dxf).
     *
     * @param inputFile the file name
     * @return paragraphs text from the file
     */
    public static String[] parseToStrings(String inputFile) throws Exception {
        String extFile = inputFile.substring(inputFile.lastIndexOf('.') + 1).toLowerCase();
        if(extFile.equals("shp")){
            String ss[] = new String[1];
            return parseShapeFileToStrings(inputFile).toArray(ss);
        }
        else if (extFile.equals("docx")) {
            POIXMLDocument.openPackage(inputFile);
            OPCPackage opcPackage = POIXMLDocument.openPackage(inputFile);
            POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
            String text = extractor.getText();

            String[] paragraphs = StringUtils.split(text, StringUtils.LF);
            return paragraphs;
        } else if (extFile.equals("pdf")) {
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
    }
}
