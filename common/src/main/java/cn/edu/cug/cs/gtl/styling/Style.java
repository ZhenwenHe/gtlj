package cn.edu.cug.cs.gtl.styling;

import cn.edu.cug.cs.gtl.io.DataContent;

public interface Style extends DataContent {
    int getType();

    static Style create(int type) {
        return null;
    }
}
