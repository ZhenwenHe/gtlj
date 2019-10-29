package cn.edu.cug.cs.gtl.io.wkb;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import cn.edu.cug.cs.gtl.geom.*;


/**
 * from JTS WKBWriter
 */
class WKBWriterImpl implements WKBWriter, Serializable {
    private static final long serialVersionUID = 1L;

    private int outputDimension = 2;
    private int byteOrder;
    private boolean includeSRID = false;
    private ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
    // holds output data values
    private byte[] buf = new byte[8];

    /**
     * Creates a writer that writes {@link Geometry}s with
     * output dimension = 2 and BIG_ENDIAN byte order
     */
    public WKBWriterImpl() {
        this(2, ByteOrderValues.BIG_ENDIAN);
    }

    /**
     * Creates a writer that writes {@link Geometry}s with
     * the given dimension (2 or 3) for output coordinates
     * and {@link ByteOrderValues#BIG_ENDIAN} byte order.
     * If the input geometry has a small coordinate dimension,
     * coordinates will be padded with {@link Vector#NULL_ORDINATE}.
     *
     * @param outputDimension the coordinate dimension to output (2 or 3)
     */
    public WKBWriterImpl(int outputDimension) {
        this(outputDimension, ByteOrderValues.BIG_ENDIAN);
    }

    /**
     * Creates a writer that writes {@link Geometry}s with
     * the given dimension (2 or 3) for output coordinates
     * and {@link ByteOrderValues#BIG_ENDIAN} byte order. This constructor also
     * takes a flag to control whether srid information will be
     * written.
     * If the input geometry has a smaller coordinate dimension,
     * coordinates will be padded with {@link Vector#NULL_ORDINATE}.
     *
     * @param outputDimension the coordinate dimension to output (2 or 3)
     * @param includeSRID     indicates whether SRID should be written
     */
    public WKBWriterImpl(int outputDimension, boolean includeSRID) {
        this(outputDimension, ByteOrderValues.BIG_ENDIAN, includeSRID);
    }

    /**
     * Creates a writer that writes {@link Geometry}s with
     * the given dimension (2 or 3) for output coordinates
     * and byte order
     * If the input geometry has a small coordinate dimension,
     * coordinates will be padded with {@link Vector#NULL_ORDINATE}.
     *
     * @param outputDimension the coordinate dimension to output (2 or 3)
     * @param byteOrder       the byte ordering to use
     */
    public WKBWriterImpl(int outputDimension, int byteOrder) {
        this(outputDimension, byteOrder, false);
    }

    /**
     * Creates a writer that writes {@link Geometry}s with
     * the given dimension (2 or 3) for output coordinates
     * and byte order. This constructor also takes a flag to
     * control whether srid information will be written.
     * If the input geometry has a small coordinate dimension,
     * coordinates will be padded with {@link Vector#NULL_ORDINATE}.
     *
     * @param outputDimension the coordinate dimension to output (2 or 3)
     * @param byteOrder       the byte ordering to use
     * @param includeSRID     indicates whether SRID should be written
     */
    public WKBWriterImpl(int outputDimension, int byteOrder, boolean includeSRID) {
        this.outputDimension = outputDimension;
        this.byteOrder = byteOrder;
        this.includeSRID = includeSRID;

        if (outputDimension < 2 || outputDimension > 3)
            throw new IllegalArgumentException("Output dimension must be 2 or 3");
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes
     * @return a string of hexadecimal digits
     * @deprecated
     */
    public static String bytesToHex(byte[] bytes) {
        return toHex(bytes);
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param bytes a byte array
     * @return a string of hexadecimal digits
     */
    public static String toHex(byte[] bytes) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            byte b = bytes[i];
            buf.append(toHexDigit((b >> 4) & 0x0F));
            buf.append(toHexDigit(b & 0x0F));
        }
        return buf.toString();
    }

    private static char toHexDigit(int n) {
        if (n < 0 || n > 15)
            throw new IllegalArgumentException("Nibble value out of range: " + n);
        if (n <= 9)
            return (char) ('0' + n);
        return (char) ('A' + (n - 10));
    }


    /**
     * Writes a {@link Geometry} into a byte array.
     *
     * @param geom the geometry to write
     * @return the byte array containing the WKB
     */
    public byte[] write(Geometry geom) {
        try {
            byteArrayOS.reset();
            write(geom, byteArrayOS);
        } catch (IOException ex) {
            throw new RuntimeException("Unexpected IO exception: " + ex.getMessage());
        }
        return byteArrayOS.toByteArray();
    }

    /**
     * Writes a {@link Geometry} to an {@link OutputStream}.
     *
     * @param geom the geometry to write
     * @param os   the out stream to write to
     * @throws IOException if an I/O error occurs
     */
    public void write(Geometry geom, OutputStream os) throws IOException {
        if (geom instanceof Point)
            writePoint((Point) geom, os);
            // LinearRings will be written as LineStrings
        else if (geom instanceof LineString)
            writeLineString((LineString) geom, os);
        else if (geom instanceof Polygon)
            writePolygon((Polygon) geom, os);
        else if (geom instanceof MultiPoint)
            writeGeometryCollection(WKBConstants.wkbMultiPoint,
                    (MultiPoint) geom, os);
        else if (geom instanceof MultiLineString)
            writeGeometryCollection(WKBConstants.wkbMultiLineString,
                    (MultiLineString) geom, os);
        else if (geom instanceof MultiPolygon)
            writeGeometryCollection(WKBConstants.wkbMultiPolygon,
                    (MultiPolygon) geom, os);
        else if (geom instanceof GeometryCollection)
            writeGeometryCollection(WKBConstants.wkbGeometryCollection,
                    (GeometryCollection) geom, os);
        else {
            throw new IOException("Unknown Geometry type");
        }
    }

    private void writePoint(Point pt, OutputStream os) throws IOException {
        if (pt.getVectorSequence().size() == 0)
            throw new IllegalArgumentException("Empty Points cannot be represented in WKB");
        writeByteOrder(os);
        writeGeometryType(WKBConstants.wkbPoint, pt, os);
        writeVectorSequence(pt.getVectorSequence(), false, os);
    }

    private void writeLineString(LineString line, OutputStream os) throws IOException {
        writeByteOrder(os);
        writeGeometryType(WKBConstants.wkbLineString, line, os);
        writeVectorSequence(line.getVectorSequence(), true, os);
    }

    private void writePolygon(Polygon poly, OutputStream os) throws IOException {
        writeByteOrder(os);
        writeGeometryType(WKBConstants.wkbPolygon, poly, os);
        writeInt(poly.getInteriorRings().length + 1, os);
        writeVectorSequence(poly.getExteriorRing().getVectorSequence(), true, os);
        for (int i = 0; i < poly.getInteriorRings().length; i++) {
            writeVectorSequence(poly.getInteriorRing(i).getVectorSequence(), true,
                    os);
        }
    }

    private void writeGeometryCollection(int geometryType, GeometryCollection gc,
                                         OutputStream os) throws IOException {
        writeByteOrder(os);
        writeGeometryType(geometryType, gc, os);
        writeInt(gc.size(), os);
        for (int i = 0; i < gc.size(); i++) {
            write(gc.getGeometry(i), os);
        }
    }

    private void writeByteOrder(OutputStream os) throws IOException {
        if (byteOrder == ByteOrderValues.LITTLE_ENDIAN)
            buf[0] = WKBConstants.wkbNDR;
        else
            buf[0] = WKBConstants.wkbXDR;
        os.write(buf, 0, 1);
    }

    private void writeGeometryType(int geometryType, Geometry g, OutputStream os) throws IOException {
        int flag3D = (outputDimension == 3) ? 0x80000000 : 0;
        int typeInt = geometryType | flag3D;
        typeInt |= includeSRID ? 0x20000000 : 0;
        writeInt(typeInt, os);
        if (includeSRID) {
            writeInt(g.getSRID(), os);
        }
    }

    private void writeInt(int intValue, OutputStream os) throws IOException {
        ByteOrderValues.putInt(intValue, buf, byteOrder);
        os.write(buf, 0, 4);
    }

    private void writeVectorSequence(VectorSequence seq, boolean writeSize, OutputStream os) throws IOException {
        if (writeSize)
            writeInt(seq.size(), os);

        for (int i = 0; i < seq.size(); i++) {
            writeVector(seq, i, os);
        }
    }

    private void writeVector(VectorSequence seq, int index, OutputStream os) throws IOException {
        ByteOrderValues.putDouble(seq.getX(index), buf, byteOrder);
        os.write(buf, 0, 8);
        ByteOrderValues.putDouble(seq.getY(index), buf, byteOrder);
        os.write(buf, 0, 8);

        // only write 3rd dim if caller has requested it for this writer
        if (outputDimension >= 3) {
            // if 3rd dim is requested, only write it if the VectorSequence provides it
            double ordVal = Vector.NULL_ORDINATE;
            if (seq.getDimension() >= 3)
                ordVal = seq.getOrdinate(index, 2);
            ByteOrderValues.putDouble(ordVal, buf, byteOrder);
            os.write(buf, 0, 8);
        }
    }
}
