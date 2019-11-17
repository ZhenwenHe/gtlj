package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.protos.Document;
import com.google.protobuf.TextFormat;

public class DocumentWrapper {
    public static void print(Document doc){
        /*
         string title = 1;
        repeated string keyword = 2;
        string version = 3;
        string type = 4;
        string url = 5;
        repeated string author = 6;
        repeated string affiliation = 7;
        string abstract = 8;
        string schema=9;
        repeated string content=10;
        bytes raw_data = 11;
        Attachments attachments=12;
        BoundingBox  bounding=13;//location
        */

        System.out.println(TextFormat.printer().escapingNonAscii(false).printToString(doc));
    }
}
