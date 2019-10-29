package cn.edu.cug.cs.gtl.offices.text;

import org.apache.lucene.document.*;

import java.util.ArrayList;
import java.util.List;

public class DocumentCreator {
    public enum DocumentLevel {
        DL_FILE, //为整个文件创建一个文档
        DL_PARAGRAPH, //为文件中的每个自然段创建一个文档
        DL_LINE   //为文件中的每行文本创建一个文档
    }

    ;


    /**
     * 为一个文件（WORD，PDF， Excel， PowerPoint等）创建文档；
     * 首选出改文件中提取所有文本，然后根据级别创建Lucene文档对象
     * 级别由level指定
     *
     * @param inputFile
     * @param level
     * @return
     */
    public static List<Document> createFromFile(String inputFile, DocumentLevel level) {
        switch (level) {
            case DL_FILE: {
                List<Document> docs = new ArrayList<>();
                docs.add(createDoc4EachFile(inputFile));
                return docs;
            }
            case DL_LINE:
            case DL_PARAGRAPH: {
                return createDoc4EachParagraph(inputFile);
            }
            default: {
                List<Document> docs = new ArrayList<>();
                Document doc = createDoc4EachFile(inputFile);
                docs.add(doc);
                return docs;
            }
        }
    }


    /**
     * 为一个文件中的每个自然段构建一个文档对象
     *
     * @param inputFile
     * @return
     */
    private static List<Document> createDoc4EachParagraph(String inputFile) {
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
            Field pathField = new StringField("path", inputFile, Field.Store.YES);
            doc.add(pathField);

            Field paragraphField = new LongPoint("paragraph", i);
            doc.add(pathField);

            // Add the contents of the file to a field named "contents".  Specify a Reader,
            // so that the text of the file is tokenized and indexed, but not stored.
            // Note that FileReader expects the file to be in UTF-8 encoding.
            // If that's not the case searching for special characters will fail.
            Field contentField = new TextField("contents", paragraphs[0], Field.Store.YES);
            doc.add(contentField);
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
    private static Document createDoc4EachFile(String inputFile) {
        String text = TextExtractor.parseToString(inputFile);

        // make a new, empty document
        Document doc = new Document();

        // Add the path of the file as a field named "path".  Use a
        // field that is indexed (i.e. searchable), but don't tokenize
        // the field into separate words and don't index term frequency
        // or positional information:
        Field pathField = new StringField("path", inputFile, Field.Store.YES);
        doc.add(pathField);

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

}
