package cn.edu.cug.cs.gtl.io.wkt;

import cn.edu.cug.cs.gtl.geom.*;
import cn.edu.cug.cs.gtl.math.PrecisionModel;


import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class WKTWriterImpl implements WKTWriter, Serializable {
    private static final long serialVersionUID = 1L;


    public static String toPoint(Vector p0) {
        return "POINT ( " + p0.getX() + " " + p0.getY() + " )";
    }

    public static String toLineString(VectorSequence seq) {
        StringBuffer buf = new StringBuffer();
        buf.append("LINESTRING ");
        if (seq.size() == 0)
            buf.append(" EMPTY");
        else {
            buf.append("(");
            for (int i = 0; i < seq.size(); i++) {
                if (i > 0)
                    buf.append(", ");
                buf.append(seq.getX(i) + " " + seq.getY(i));
            }
            buf.append(")");
        }
        return buf.toString();
    }

    public static String toLineString(Vector[] coord) {
        StringBuffer buf = new StringBuffer();
        buf.append("LINESTRING ");
        if (coord.length == 0)
            buf.append(" EMPTY");
        else {
            buf.append("(");
            for (int i = 0; i < coord.length; i++) {
                if (i > 0)
                    buf.append(", ");
                buf.append(coord[i].getX() + " " + coord[i].getY());
            }
            buf.append(")");
        }
        return buf.toString();
    }

    public static String toLineString(Vector p0, Vector p1) {
        return "LINESTRING ( " + p0.getX() + " " + p0.getY() + ", " + p1.getX() + " " + p1.getY() + " )";
    }

    private static final int INDENT = 2;

    /**
     * Creates the <code>DecimalFormat</code> used to write <code>double</code>s
     * with a sufficient number of decimal places.
     *
     * @param precisionModel the <code>PrecisionModel</code> used to determine
     *                       the number of decimal places to write.
     * @return a <code>DecimalFormat</code> that write <code>double</code>
     * s without scientific notation.
     */
    private static DecimalFormat createFormatter(PrecisionModel precisionModel) {
        // the default number of decimal places is 16, which is sufficient
        // to accomodate the maximum precision of a double.
        int decimalPlaces = precisionModel.getMaximumSignificantDigits();
        // specify decimal separator explicitly to avoid problems in other locales
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        String fmtString = "0" + (decimalPlaces > 0 ? "." : "")
                + stringOfChar('#', decimalPlaces);
        return new DecimalFormat(fmtString, symbols);
    }

    /**
     * Returns a <code>String</code> of repeated characters.
     *
     * @param ch    the character to repeat
     * @param count the number of times to repeat the character
     * @return a <code>String</code> of characters
     */
    public static String stringOfChar(char ch, int count) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < count; i++) {
            buf.append(ch);
        }
        return buf.toString();
    }

    private int outputDimension = 2;
    private DecimalFormat formatter;
    private boolean isFormatted = false;
    private boolean useFormatting = false;
    private int level = 0;
    private int coordsPerLine = -1;
    private String indentTabStr = "  ";

    /**
     * Creates a new WKTWriter with default settings
     */
    public WKTWriterImpl() {
        this.outputDimension = 2;
    }

    /**
     * Creates a writer that writes {@link Geometry}s with
     * the given output dimension (2 or 3).
     * If the specified output dimension is 3, the Z value
     * of coordinates will be written if it is present
     * (i.e. if it is not <code>Double.NaN</code>).
     *
     * @param outputDimension the coordinate dimension to output (2 or 3)
     */
    public WKTWriterImpl(int outputDimension) {
        this.outputDimension = outputDimension;

        if (outputDimension < 2 || outputDimension > 3)
            throw new IllegalArgumentException("Invalid output dimension (must be 2 or 3)");
    }


    /**
     * Sets whether the output will be formatted.
     *
     * @param isFormatted true if the output is to be formatted
     */
    public void setFormatted(boolean isFormatted) {
        this.isFormatted = isFormatted;
    }

    /**
     * Sets the maximum number of coordinates per line
     * written in formatted output.
     * If the provided coordinate number is <= 0,
     * coordinates will be written all on one line.
     *
     * @param coordsPerLine the number of coordinates per line to output.
     */
    public void setMaxCoordinatesPerLine(int coordsPerLine) {
        this.coordsPerLine = coordsPerLine;
    }

    /**
     * Sets the tab size to use for indenting.
     *
     * @param size the number of spaces to use as the tab string
     * @throws IllegalArgumentException if the size is non-positive
     */
    public void setTab(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Tab count must be positive");
        this.indentTabStr = stringOfChar(' ', size);
    }

    /**
     * Converts a <code>Geometry</code> to its Well-known Text representation.
     *
     * @param geometry a <code>Geometry</code> to process
     * @return a <Geometry Tagged Text> string (see the OpenGIS Simple
     * Features Specification)
     */
    public String write(Geometry geometry) {
        Writer sw = new StringWriter();
        try {
            writeFormatted(geometry, isFormatted, sw);
        } catch (IOException ex) {
            //Assert.shouldNeverReachHere();
            ex.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * Converts a <code>Geometry</code> to its Well-known Text representation.
     *
     * @param geometry a <code>Geometry</code> to process
     */
    public void write(Geometry geometry, Writer writer) {
        try {
            writeFormatted(geometry, false, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Same as <code>write</code>, but with newlines and spaces to make the
     * well-known text more readable.
     *
     * @param geometry a <code>Geometry</code> to process
     * @return a <Geometry Tagged Text> string (see the OpenGIS Simple
     * Features Specification), with newlines and spaces
     */
    public String writeFormatted(Geometry geometry) {
        Writer sw = new StringWriter();
        try {
            writeFormatted(geometry, true, sw);
        } catch (IOException ex) {
            //Assert.shouldNeverReachHere();
            ex.printStackTrace();
        }
        return sw.toString();
    }

    /**
     * Same as <code>write</code>, but with newlines and spaces to make the
     * well-known text more readable.
     *
     * @param geometry a <code>Geometry</code> to process
     */
    public void writeFormatted(Geometry geometry, Writer writer)
            throws IOException {
        writeFormatted(geometry, true, writer);
    }

    /**
     * Converts a <code>Geometry</code> to its Well-known Text representation.
     *
     * @param geometry a <code>Geometry</code> to process
     */
    private void writeFormatted(Geometry geometry, boolean useFormatting, Writer writer)
            throws IOException {
        this.useFormatting = useFormatting;
        formatter = createFormatter(geometry.getPrecisionModel());
        appendGeometryTaggedText(geometry, 0, writer);
    }


    /**
     * Converts a <code>Geometry</code> to &lt;Geometry Tagged Text&gt; format,
     * then appends it to the writer.
     *
     * @param geometry the <code>Geometry</code> to process
     * @param writer   the output writer to append to
     */
    private void appendGeometryTaggedText(Geometry geometry, int level, Writer writer)
            throws IOException {
        indent(level, writer);

        if (geometry instanceof Point) {
            Point point = (Point) geometry;
            appendPointTaggedText(point.getVectorSequence().getVector(0), level, writer, point.getPrecisionModel());
        } else if (geometry instanceof LinearRing) {
            appendLinearRingTaggedText((LinearRing) geometry, level, writer);
        } else if (geometry instanceof LineString) {
            appendLineStringTaggedText((LineString) geometry, level, writer);
        } else if (geometry instanceof Polygon) {
            appendPolygonTaggedText((Polygon) geometry, level, writer);
        } else if (geometry instanceof MultiPoint) {
            appendMultiPointTaggedText((MultiPoint) geometry, level, writer);
        } else if (geometry instanceof MultiLineString) {
            appendMultiLineStringTaggedText((MultiLineString) geometry, level, writer);
        } else if (geometry instanceof MultiPolygon) {
            appendMultiPolygonTaggedText((MultiPolygon) geometry, level, writer);
        } else if (geometry instanceof GeometryCollection) {
            appendGeometryCollectionTaggedText((GeometryCollection) geometry, level, writer);
        } else {
            //Assert.shouldNeverReachHere("Unsupported Geometry implementation:" + geometry.getClass());
            assert false;
        }
    }

    /**
     * Converts a <code>Vector</code> to &lt;Point Tagged Text&gt; format,
     * then appends it to the writer.
     *
     * @param coordinate     the <code>Vector</code> to process
     * @param writer         the output writer to append to
     * @param precisionModel the <code>PrecisionModel</code> to use to convert
     *                       from a precise coordinate to an external coordinate
     */
    private void appendPointTaggedText(Vector coordinate, int level, Writer writer,
                                       PrecisionModel precisionModel)
            throws IOException {
        writer.write("POINT ");
        appendPointText(coordinate, level, writer, precisionModel);
    }

    /**
     * Converts a <code>LineString</code> to &lt;LineString Tagged Text&gt;
     * format, then appends it to the writer.
     *
     * @param lineString the <code>LineString</code> to process
     * @param writer     the output writer to append to
     */
    private void appendLineStringTaggedText(LineString lineString, int level, Writer writer)
            throws IOException {
        writer.write("LINESTRING ");
        appendLineStringText(lineString, level, false, writer);
    }

    /**
     * Converts a <code>LinearRing</code> to &lt;LinearRing Tagged Text&gt;
     * format, then appends it to the writer.
     *
     * @param linearRing the <code>LinearRing</code> to process
     * @param writer     the output writer to append to
     */
    private void appendLinearRingTaggedText(LinearRing linearRing, int level, Writer writer)
            throws IOException {
        writer.write("LINEARRING ");
        appendLineStringText(linearRing, level, false, writer);
    }

    /**
     * Converts a <code>Polygon</code> to &lt;Polygon Tagged Text&gt; format,
     * then appends it to the writer.
     *
     * @param polygon the <code>Polygon</code> to process
     * @param writer  the output writer to append to
     */
    private void appendPolygonTaggedText(Polygon polygon, int level, Writer writer)
            throws IOException {
        writer.write("POLYGON ");
        appendPolygonText(polygon, level, false, writer);
    }

    /**
     * Converts a <code>MultiPoint</code> to &lt;MultiPoint Tagged Text&gt;
     * format, then appends it to the writer.
     *
     * @param multipoint the <code>MultiPoint</code> to process
     * @param writer     the output writer to append to
     */
    private void appendMultiPointTaggedText(MultiPoint multipoint, int level, Writer writer)
            throws IOException {
        writer.write("MULTIPOINT ");
        appendMultiPointText(multipoint, level, writer);
    }

    /**
     * Converts a <code>MultiLineString</code> to &lt;MultiLineString Tagged
     * Text&gt; format, then appends it to the writer.
     *
     * @param multiLineString the <code>MultiLineString</code> to process
     * @param writer          the output writer to append to
     */
    private void appendMultiLineStringTaggedText(MultiLineString multiLineString, int level,
                                                 Writer writer) throws IOException {
        writer.write("MULTILINESTRING ");
        appendMultiLineStringText(multiLineString, level, false, writer);
    }

    /**
     * Converts a <code>MultiPolygon</code> to &lt;MultiPolygon Tagged Text&gt;
     * format, then appends it to the writer.
     *
     * @param multiPolygon the <code>MultiPolygon</code> to process
     * @param writer       the output writer to append to
     */
    private void appendMultiPolygonTaggedText(MultiPolygon multiPolygon, int level, Writer writer)
            throws IOException {
        writer.write("MULTIPOLYGON ");
        appendMultiPolygonText(multiPolygon, level, writer);
    }

    /**
     * Converts a <code>GeometryCollection</code> to &lt;GeometryCollection
     * Tagged Text&gt; format, then appends it to the writer.
     *
     * @param geometryCollection the <code>GeometryCollection</code> to process
     * @param writer             the output writer to append to
     */
    private void appendGeometryCollectionTaggedText(GeometryCollection geometryCollection, int level,
                                                    Writer writer)
            throws IOException {
        writer.write("GEOMETRYCOLLECTION ");
        appendGeometryCollectionText(geometryCollection, level, writer);
    }

    /**
     * Converts a <code>Vector</code> to &lt;Point Text&gt; format, then
     * appends it to the writer.
     *
     * @param coordinate     the <code>Vector</code> to process
     * @param writer         the output writer to append to
     * @param precisionModel the <code>PrecisionModel</code> to use to convert
     *                       from a precise coordinate to an external coordinate
     */
    private void appendPointText(Vector coordinate, int level, Writer writer,
                                 PrecisionModel precisionModel)
            throws IOException {
        if (coordinate == null) {
            writer.write("EMPTY");
        } else {
            writer.write("(");
            appendCoordinate(coordinate, writer);
            writer.write(")");
        }
    }

    /**
     * Appends the i'th coordinate from the sequence to the writer
     *
     * @param seq    the <code>VectorSequence</code> to process
     * @param i      the index of the coordinate to write
     * @param writer the output writer to append to
     */
    private void appendCoordinate(VectorSequence seq, int i, Writer writer)
            throws IOException {
        writer.write(writeNumber(seq.getX(i)) + " " + writeNumber(seq.getY(i)));
        if (outputDimension >= 3 && seq.getDimension() >= 3) {
            double z = seq.getOrdinate(i, 3);
            if (!Double.isNaN(z)) {
                writer.write(" ");
                writer.write(writeNumber(z));
            }
        }
    }

    /**
     * Converts a <code>Vector</code> to <code>&lt;Point&gt;</code> format,
     * then appends it to the writer.
     *
     * @param coordinate the <code>Vector</code> to process
     * @param writer     the output writer to append to
     */
    private void appendCoordinate(Vector coordinate, Writer writer)
            throws IOException {
        writer.write(writeNumber(coordinate.getX()) + " " + writeNumber(coordinate.getY()));
        if (outputDimension >= 3 && !Double.isNaN(coordinate.getZ())) {
            writer.write(" ");
            writer.write(writeNumber(coordinate.getZ()));
        }
    }

    /**
     * Converts a <code>double</code> to a <code>String</code>, not in scientific
     * notation.
     *
     * @param d the <code>double</code> to convert
     * @return the <code>double</code> as a <code>String</code>, not in
     * scientific notation
     */
    private String writeNumber(double d) {
        return formatter.format(d);
    }

    private void appendSequenceText(VectorSequence seq, int level, boolean doIndent, Writer writer)
            throws IOException {
        if (seq.size() == 0) {
            writer.write("EMPTY");
        } else {
            if (doIndent) indent(level, writer);
            writer.write("(");
            for (int i = 0; i < seq.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    if (coordsPerLine > 0
                            && i % coordsPerLine == 0) {
                        indent(level + 1, writer);
                    }
                }
                appendCoordinate(seq, i, writer);
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>LineString</code> to &lt;LineString Text&gt; format, then
     * appends it to the writer.
     *
     * @param lineString the <code>LineString</code> to process
     * @param writer     the output writer to append to
     */
    private void appendLineStringText(LineString lineString, int level, boolean doIndent, Writer writer)
            throws IOException {
        if (lineString.isEmpty()) {
            writer.write("EMPTY");
        } else {
            if (doIndent) indent(level, writer);
            writer.write("(");
            VectorSequence vs = lineString.getVectorSequence();
            for (int i = 0; i < vs.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    if (coordsPerLine > 0
                            && i % coordsPerLine == 0) {
                        indent(level + 1, writer);
                    }
                }
                appendCoordinate(vs.getVector(i), writer);
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>Polygon</code> to &lt;Polygon Text&gt; format, then
     * appends it to the writer.
     *
     * @param polygon the <code>Polygon</code> to process
     * @param writer  the output writer to append to
     */
    private void appendPolygonText(Polygon polygon, int level, boolean indentFirst, Writer writer)
            throws IOException {
        if (polygon.isEmpty()) {
            writer.write("EMPTY");
        } else {
            if (indentFirst) indent(level, writer);
            writer.write("(");
            appendLineStringText(polygon.getExteriorRing(), level, false, writer);
            LinearRing[] holes = polygon.getInteriorRings();
            int s = holes == null ? 0 : holes.length;
            for (int i = 0; i < s; i++) {
                writer.write(", ");
                appendLineStringText(polygon.getInteriorRing(i), level + 1, true, writer);
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>MultiPoint</code> to &lt;MultiPoint Text&gt; format, then
     * appends it to the writer.
     *
     * @param multiPoint the <code>MultiPoint</code> to process
     * @param writer     the output writer to append to
     */
    private void appendMultiPointText(MultiPoint multiPoint, int level, Writer writer)
            throws IOException {
        if (multiPoint.isEmpty()) {
            writer.write("EMPTY");
        } else {
            writer.write("(");
            Vector v = null;
            for (int i = 0; i < multiPoint.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    indentCoords(i, level + 1, writer);
                }
                writer.write("(");
                v = ((Point) multiPoint.getGeometry(i)).getVectorSequence().getVector(0);
                appendCoordinate(v, writer);
                writer.write(")");
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>MultiLineString</code> to &lt;MultiLineString Text&gt;
     * format, then appends it to the writer.
     *
     * @param multiLineString the <code>MultiLineString</code> to process
     * @param writer          the output writer to append to
     */
    private void appendMultiLineStringText(MultiLineString multiLineString, int level, boolean indentFirst,
                                           Writer writer)
            throws IOException {
        if (multiLineString.isEmpty()) {
            writer.write("EMPTY");
        } else {
            int level2 = level;
            boolean doIndent = indentFirst;
            writer.write("(");
            for (int i = 0; i < multiLineString.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    level2 = level + 1;
                    doIndent = true;
                }
                appendLineStringText((LineString) multiLineString.getGeometry(i), level2, doIndent, writer);
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>MultiPolygon</code> to &lt;MultiPolygon Text&gt; format,
     * then appends it to the writer.
     *
     * @param multiPolygon the <code>MultiPolygon</code> to process
     * @param writer       the output writer to append to
     */
    private void appendMultiPolygonText(MultiPolygon multiPolygon, int level, Writer writer)
            throws IOException {
        if (multiPolygon.isEmpty()) {
            writer.write("EMPTY");
        } else {
            int level2 = level;
            boolean doIndent = false;
            writer.write("(");
            for (int i = 0; i < multiPolygon.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    level2 = level + 1;
                    doIndent = true;
                }
                appendPolygonText((Polygon) multiPolygon.getGeometry(i), level2, doIndent, writer);
            }
            writer.write(")");
        }
    }

    /**
     * Converts a <code>GeometryCollection</code> to &lt;GeometryCollectionText&gt;
     * format, then appends it to the writer.
     *
     * @param geometryCollection the <code>GeometryCollection</code> to process
     * @param writer             the output writer to append to
     */
    private void appendGeometryCollectionText(GeometryCollection geometryCollection, int level,
                                              Writer writer) throws IOException {
        if (geometryCollection.isEmpty()) {
            writer.write("EMPTY");
        } else {
            int level2 = level;
            writer.write("(");
            for (int i = 0; i < geometryCollection.size(); i++) {
                if (i > 0) {
                    writer.write(", ");
                    level2 = level + 1;
                }
                appendGeometryTaggedText(geometryCollection.getGeometry(i), level2, writer);
            }
            writer.write(")");
        }
    }

    private void indentCoords(int coordIndex, int level, Writer writer)
            throws IOException {
        if (coordsPerLine <= 0
                || coordIndex % coordsPerLine != 0)
            return;
        indent(level, writer);
    }

    private void indent(int level, Writer writer)
            throws IOException {
        if (!useFormatting || level <= 0)
            return;
        writer.write("\n");
        for (int i = 0; i < level; i++) {
            writer.write(indentTabStr);
        }
    }


}
//class WKTWriterImpl  implements WKTWriter,  Serializable {
//    private static final long serialVersionUID = 1L;
//
//    private static WKTWriter2 wktWriter2=null;
//    public WKTWriterImpl() {
//        wktWriter2=new WKTWriter2();
//    }
//
//    public WKTWriterImpl(int outputDimension) {
//        wktWriter2=new WKTWriter2(outputDimension);
//    }
//
//    @Override
//    public String write(Geometry g) {
//        return wktWriter2.write(JTSWrapper.toJTSGeometry(g));
//    }
//
//    @Override
//    public void write(Geometry g, Writer w) {
//        try {
//            wktWriter2.write(JTSWrapper.toJTSGeometry(g),w);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
//
//}
