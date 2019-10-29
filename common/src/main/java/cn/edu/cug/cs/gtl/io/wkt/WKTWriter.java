package cn.edu.cug.cs.gtl.io.wkt;

import cn.edu.cug.cs.gtl.geom.Geometry;

import java.io.Writer;

public interface WKTWriter extends java.io.Serializable {
    String write(Geometry g);

    void write(Geometry g, Writer w);

    static WKTWriter create(int dim) {
        return new WKTWriterImpl(dim);
    }
}
