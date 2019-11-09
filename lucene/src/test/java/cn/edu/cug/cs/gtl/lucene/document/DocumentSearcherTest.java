package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.lucene.document.DocumentSearcher;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.junit.Test;

public class DocumentSearcherTest {

    @Test
    public void execute() {
        try {
            DocumentSearcher.of("dat/idx").execute("新华社");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void termQuery() {
        try {
            DocumentSearcher documentSearcher=DocumentSearcher.of("dat/idx");
            TopDocs results= documentSearcher.termQuery("contents", "社会主义");
            documentSearcher.output(results);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void wildcardQuery() {
        try {
            DocumentSearcher documentSearcher=DocumentSearcher.of("dat/idx");
            TopDocs results= documentSearcher.wildcardQuery("contents", "社会*");
            documentSearcher.output(results);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void fuzzyQuery() {
        try {
            DocumentSearcher documentSearcher=DocumentSearcher.of("dat/idx");
            TopDocs results= documentSearcher.fuzzyQuery("contents", "社会",2,1);
            documentSearcher.output(results);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void booleanQuery() {
        try {
            DocumentSearcher documentSearcher=DocumentSearcher.of("dat/idx");
            documentSearcher.setTopNumber(10);
            TopDocs results= documentSearcher.booleanQuery(
                    "contents", "社会主义","or",
                    "contents", "beam");
            documentSearcher.output(results);

            System.out.println("////////////////////////////////other query/////////////////////////////");

            results= documentSearcher.booleanQuery(
                    "contents", "社会主义","not",
                    "contents", "心灵");
            documentSearcher.output(results);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}