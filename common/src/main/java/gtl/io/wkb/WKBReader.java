package gtl.io.wkb;

import gtl.exception.ParseException;
import gtl.geom.Geometry;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public interface WKBReader extends Serializable {
    Geometry read(byte[] bytes) throws IOException,ParseException;
    Geometry read(InputStream is) throws IOException,ParseException;

    static WKBReader create(){
        return new WKBReaderImpl();
    }
}
