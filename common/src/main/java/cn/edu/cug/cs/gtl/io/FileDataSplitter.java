package cn.edu.cug.cs.gtl.io;

import java.io.Serializable;

public enum FileDataSplitter implements Serializable {
    WKT("\t"),
    WKB("\t"),
    CSV(","),
    SSV(" "),
    TSV("\t"),
    GEOJSON(""),
    COMMA(","),
    TAB("\t"),
    QUESTIONMARK("?"),
    SINGLEQUOTE("'"),
    QUOTE("\""),
    UNDERSCORE("_"),
    DASH("-"),
    PERCENT("%"),
    TILDE("~"),
    PIPE("|"),
    SEMICOLON(";"),
    SPACE(" ");

    private String splitter;

    public static FileDataSplitter getFileDataSplitter(String str) {
        FileDataSplitter[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            FileDataSplitter me = var1[var3];
            if (me.getDelimiter().equalsIgnoreCase(str) || me.name().equalsIgnoreCase(str)) {
                return me;
            }
        }

        throw new IllegalArgumentException("[" + FileDataSplitter.class + "] Unsupported FileDataSplitter:" + str);
    }

    private FileDataSplitter(String splitter) {
        this.splitter = splitter;
    }

    public String getDelimiter() {
        return this.splitter;
    }


}
