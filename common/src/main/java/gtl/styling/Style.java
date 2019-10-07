package gtl.styling;

import gtl.io.DataContent;

public interface Style extends DataContent {
    int getType();
    static Style create(int type){
        return null;
    }
}
