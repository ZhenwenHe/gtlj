package gtl.io.wkt;

import gtl.geom.Geometry;
import java.io.Serializable;

import java.io.Reader;

public interface WKTReader extends Serializable{
    Geometry read(String wkt);
    Geometry read(Reader w);
    static WKTReader create(){
        return new WKTReaderImpl() ;
    }
}
