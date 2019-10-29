package cn.edu.cug.cs.gtl.io.wkb;

import cn.edu.cug.cs.gtl.geom.*;
import cn.edu.cug.cs.gtl.math.PrecisionModel;
import cn.edu.cug.cs.gtl.exception.ParseException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * from JTS WKTWriter
 */
class WKBReaderImpl implements WKBReader, Serializable {

    private static final long serialVersionUID = 1L;

    private static final String INVALID_GEOM_TYPE_MSG
            = "Invalid geometry type encountered in ";


    private PrecisionModel precisionModel;
    // default dimension - will be set on read
    private int inputDimension = 2;
    private boolean hasSRID = false;
    private int SRID = 0;
    /**
     * true if structurally invalid input should be reported rather than repaired.
     * At some point this could be made client-controllable.
     */
    private boolean isStrict = false;
    private ByteOrderDataInputStream byteOrderDataInputStream = null;
    private double[] ordinateValues = null;


    public WKBReaderImpl() {
        precisionModel = new PrecisionModel();
        byteOrderDataInputStream = new ByteOrderDataInputStream();
    }

    /**
     * Converts a hexadecimal string to a byte array.
     * The hexadecimal digit symbols are case-insensitive.
     *
     * @param hex a string containing hex digits
     * @return an array of bytes with the value of the hex string
     */
    public static byte[] hexToBytes(String hex) {
        int byteLen = hex.length() / 2;
        byte[] bytes = new byte[byteLen];

        for (int i = 0; i < hex.length() / 2; i++) {
            int i2 = 2 * i;
            if (i2 + 1 > hex.length())
                throw new IllegalArgumentException("Hex string has odd length");

            int nib1 = hexToInt(hex.charAt(i2));
            int nib0 = hexToInt(hex.charAt(i2 + 1));
            byte b = (byte) ((nib1 << 4) + (byte) nib0);
            bytes[i] = b;
        }
        return bytes;
    }

    private static int hexToInt(char hex) {
        int nib = Character.digit(hex, 16);
        if (nib < 0)
            throw new IllegalArgumentException("Invalid hex digit: '" + hex + "'");
        return nib;
    }

    /**
     * Reads a single {@link Geometry} in WKB format from a byte array.
     *
     * @param bytes the byte array to read from
     * @return the geometry read
     * @throws ParseException if the WKB is ill-formed
     */
    public Geometry read(byte[] bytes) throws ParseException, IOException {
        // possibly reuse the ByteArrayInputStream?
        // don't throw IOExceptions, since we are not doing any I/O
        try {
            return read(new ByteArrayInputStream(bytes));
        } catch (IOException | ParseException ex) {
            throw new RuntimeException("Unexpected IOException caught: " + ex.getMessage());
        }
    }

    /**
     * Reads a {@link Geometry} in binary WKB format from an {@link InputStream}.
     *
     * @param is the stream to read from
     * @return the Geometry read
     * @throws IOException    if the underlying stream creates an error
     * @throws ParseException if the WKB is ill-formed
     */
    public Geometry read(InputStream is) throws IOException, ParseException {
        byteOrderDataInputStream.setInputStream(is);
        Geometry g = readGeometry();
        return g;
    }

    private Geometry readGeometry() throws IOException, ParseException {

        // determine byte order
        byte byteOrderWKB = byteOrderDataInputStream.readByte();

        // always set byte order, since it may change from geometry to geometry
        if (byteOrderWKB == WKBConstants.wkbNDR) {
            byteOrderDataInputStream.setOrder(ByteOrderValues.LITTLE_ENDIAN);
        } else if (byteOrderWKB == WKBConstants.wkbXDR) {
            byteOrderDataInputStream.setOrder(ByteOrderValues.BIG_ENDIAN);
        } else if (isStrict) {
            throw new ParseException("Unknown geometry byte order (not NDR or XDR): " + byteOrderWKB);
        }
        //if not strict and not XDR or NDR, then we just use the byteOrderDataInputStream default set at the
        //start of the geometry (if a multi-geometry).  This  allows WBKReader to work
        //with Spatialite native BLOB WKB, as well as other WKB variants that might just
        //specify endian-ness at the start of the multigeometry.


        int typeInt = byteOrderDataInputStream.readInt();
        int geometryType = typeInt & 0xff;
        // determine if Z values are present
        boolean hasZ = (typeInt & 0x80000000) != 0;
        inputDimension = hasZ ? 3 : 2;
        // determine if SRIDs are present
        hasSRID = (typeInt & 0x20000000) != 0;

        int SRID = 0;
        if (hasSRID) {
            SRID = byteOrderDataInputStream.readInt();
        }

        // only allocate ordinateValues buffer if necessary
        if (ordinateValues == null || ordinateValues.length < inputDimension)
            ordinateValues = new double[inputDimension];

        Geometry geom = null;
        switch (geometryType) {
            case WKBConstants.wkbPoint:
                geom = readPoint();
                break;
            case WKBConstants.wkbLineString:
                geom = readLineString();
                break;
            case WKBConstants.wkbPolygon:
                geom = readPolygon();
                break;
            case WKBConstants.wkbMultiPoint:
                geom = readMultiPoint();
                break;
            case WKBConstants.wkbMultiLineString:
                geom = readMultiLineString();
                break;
            case WKBConstants.wkbMultiPolygon:
                geom = readMultiPolygon();
                break;
            case WKBConstants.wkbGeometryCollection:
                geom = readGeometryCollection();
                break;
            default:
                throw new ParseException("Unknown WKB type " + geometryType);
        }
        setSRID(geom, SRID);
        return geom;
    }

    /**
     * Sets the SRID, if it was specified in the WKB
     *
     * @param g the geometry to update
     * @return the geometry with an updated SRID value, if required
     */
    private Geometry setSRID(Geometry g, int SRID) {
        if (SRID != 0)
            g.setSRID(SRID);
        return g;
    }

    private Point readPoint() throws IOException {
        VectorSequence pts = readVectorSequence(1);
        Point p = new Point(pts);
        return p;
    }

    private LineString readLineString() throws IOException {
        int size = byteOrderDataInputStream.readInt();
        VectorSequence pts = readVectorSequenceLineString(size);
        return new LineString(pts);
    }

    private LinearRing readLinearRing() throws IOException {
        int size = byteOrderDataInputStream.readInt();
        VectorSequence pts = readVectorSequenceRing(size);
        return new LinearRing(pts);
    }

    private Polygon readPolygon() throws IOException {
        int numRings = byteOrderDataInputStream.readInt();
        LinearRing[] holes = null;
        if (numRings > 1)
            holes = new LinearRing[numRings - 1];

        LinearRing shell = readLinearRing();
        for (int i = 0; i < numRings - 1; i++) {
            holes[i] = readLinearRing();
        }
        return new Polygon(shell, holes);
    }

    private MultiPoint readMultiPoint() throws IOException, ParseException {
        int numGeom = byteOrderDataInputStream.readInt();
        Point[] geoms = new Point[numGeom];
        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof Point))
                throw new ParseException(INVALID_GEOM_TYPE_MSG + "MultiPoint");
            geoms[i] = (Point) g;
        }
        return new MultiPoint(geoms);
    }

    private MultiLineString readMultiLineString() throws IOException, ParseException {
        int numGeom = byteOrderDataInputStream.readInt();
        LineString[] geoms = new LineString[numGeom];
        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof LineString))
                throw new ParseException(INVALID_GEOM_TYPE_MSG + "MultiLineString");
            geoms[i] = (LineString) g;
        }
        return new MultiLineString(geoms);
    }

    private MultiPolygon readMultiPolygon() throws IOException, ParseException {
        int numGeom = byteOrderDataInputStream.readInt();
        Polygon[] geoms = new Polygon[numGeom];

        for (int i = 0; i < numGeom; i++) {
            Geometry g = readGeometry();
            if (!(g instanceof Polygon))
                throw new ParseException(INVALID_GEOM_TYPE_MSG + "MultiPolygon");
            geoms[i] = (Polygon) g;
        }
        return new MultiPolygon(geoms);
    }

    private GeometryCollection readGeometryCollection() throws IOException, ParseException {
        int numGeom = byteOrderDataInputStream.readInt();
        Geometry[] geoms = new Geometry[numGeom];
        for (int i = 0; i < numGeom; i++) {
            geoms[i] = readGeometry();
        }
        return new GeometryCollection(geoms);
    }

    private VectorSequence readVectorSequence(int size) throws IOException {
        VectorSequence seq = VectorSequence.create(size, inputDimension);
        int targetDim = seq.getDimension();
        if (targetDim > inputDimension)
            targetDim = inputDimension;
        for (int i = 0; i < size; i++) {
            readVector();
            for (int j = 0; j < targetDim; j++) {
                seq.setOrdinate(i, j, ordinateValues[j]);
            }
        }
        return seq;
    }

    private VectorSequence readVectorSequenceLineString(int size) throws IOException {
        VectorSequence seq = readVectorSequence(size);
        if (isStrict) return seq;
        if (seq.size() == 0 || seq.size() >= 2) return seq;
        return VectorSequence.extend(seq, 2);
    }

    private VectorSequence readVectorSequenceRing(int size) throws IOException {
        VectorSequence seq = readVectorSequence(size);
        if (isStrict) return seq;
        if (VectorSequence.isRing(seq)) return seq;
        return VectorSequence.ensureValidRing(seq);
    }

    /**
     * Reads a coordinate value with the specified dimensionality.
     * Makes the X and Y ordinates precise according to the precision model
     * in use.
     */
    private void readVector() throws IOException {
        for (int i = 0; i < inputDimension; i++) {
            if (i <= 1) {
                ordinateValues[i] = precisionModel.makePrecise(byteOrderDataInputStream.readDouble());
            } else {
                ordinateValues[i] = byteOrderDataInputStream.readDouble();
            }

        }
    }
}
