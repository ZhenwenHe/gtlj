package cn.edu.cug.cs.gtl.io.wkt;

import cn.edu.cug.cs.gtl.geom.Geometry;

import java.io.Serializable;

import java.io.Reader;

public interface WKTReader extends Serializable {
    Geometry read(String wkt);

    Geometry read(Reader w);

    static WKTReader create() {
        return new WKTReaderImpl();
    }
}
