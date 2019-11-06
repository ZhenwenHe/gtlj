package cn.edu.cug.cs.gtl.lucene.document;

import cn.edu.cug.cs.gtl.lucene.document.DocumentSearcher;
import org.junit.Test;

public class DocumentSearcherTest {

    @Test
    public void execute() {
        try {
            DocumentSearcher.of("dat/idx","新华社").execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}