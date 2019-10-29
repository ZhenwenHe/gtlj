package cn.edu.cug.cs.gtl.io.wkb;

import cn.edu.cug.cs.gtl.geom.Geometry;

import java.io.IOException;
import java.io.OutputStream;

public interface WKBWriter {
    byte[] write(Geometry geom);

    void write(Geometry geom, OutputStream os) throws IOException;

    static WKBWriter create(int outputDimension, int byteOrder, boolean includeSRID) {
        return new WKBWriterImpl(outputDimension, byteOrder, includeSRID);
    }

    static WKBWriter create(int outputDimension, int byteOrder) {
        return new WKBWriterImpl(outputDimension, byteOrder);
    }

    static WKBWriter create(int outputDimension) {
        return new WKBWriterImpl(outputDimension);
    }
}
