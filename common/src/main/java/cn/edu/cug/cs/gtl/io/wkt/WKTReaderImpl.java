package cn.edu.cug.cs.gtl.io.wkt;

import cn.edu.cug.cs.gtl.geom.*;
import cn.edu.cug.cs.gtl.math.PrecisionModel;
import cn.edu.cug.cs.gtl.exception.ParseException;

import java.io.*;
import java.util.ArrayList;

public class WKTReaderImpl implements WKTReader, Serializable {
    private static final long serialVersionUID = 1L;

    private static final String EMPTY = "EMPTY";
    private static final String COMMA = ",";
    private static final String L_PAREN = "(";
    private static final String R_PAREN = ")";
    private static final String NAN_SYMBOL = "NaN";
    private static final String DOUBLE_QUOTE = "\"";
    private static final String SINGLE_QUOTE = "\'";

    private PrecisionModel precisionModel;
    private StreamTokenizer tokenizer;


    public WKTReaderImpl() {
        precisionModel = new PrecisionModel();
    }

    public Geometry read(String wellKnownText) {
        //remove the quotes in the string
        if (wellKnownText.substring(0, 1).equals(DOUBLE_QUOTE))
            wellKnownText = wellKnownText.replace(DOUBLE_QUOTE, "");
        if (wellKnownText.substring(0, 1).equals(SINGLE_QUOTE))
            wellKnownText = wellKnownText.replace(SINGLE_QUOTE, "");

        StringReader reader = new StringReader(wellKnownText);
        try {
            return read(reader);
        } finally {
            reader.close();
        }
    }

    public Geometry read(Reader reader) {
        tokenizer = new StreamTokenizer(reader);
        // set tokenizer to NOT parse numbers
        tokenizer.resetSyntax();
        tokenizer.wordChars('a', 'z');
        tokenizer.wordChars('A', 'Z');
        tokenizer.wordChars(128 + 32, 255);
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('-', '-');
        tokenizer.wordChars('+', '+');
        tokenizer.wordChars('.', '.');
        tokenizer.whitespaceChars(0, ' ');
        tokenizer.commentChar('#');

        try {
            return readGeometryTaggedText();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Vector[] getCoordinates() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new Vector[]{};
        }
        ArrayList coordinates = new ArrayList();
        coordinates.add(getPreciseCoordinate());
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            coordinates.add(getPreciseCoordinate());
            nextToken = getNextCloserOrComma();
        }
        Vector[] array = new Vector[coordinates.size()];
        return (Vector[]) coordinates.toArray(array);
    }

    private Vector[] getCoordinatesNoLeftParen() throws IOException, ParseException {
        String nextToken = null;
        ArrayList coordinates = new ArrayList();
        coordinates.add(getPreciseCoordinate());
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            coordinates.add(getPreciseCoordinate());
            nextToken = getNextCloserOrComma();
        }
        Vector[] array = new Vector[coordinates.size()];
        return (Vector[]) coordinates.toArray(array);
    }

    private Vector getPreciseCoordinate()
            throws IOException, ParseException {
        double x = getNextNumber();
        double y = getNextNumber();
        if (isNumberNext()) {
            double z = getNextNumber();
            Vector coord = Vector.create(x, y, z);
            precisionModel.makePrecise(coord);
            return coord;
        } else {
            Vector coord = Vector.create(x, y);
            precisionModel.makePrecise(coord);
            return coord;
        }

    }

    private boolean isNumberNext() throws IOException {
        int type = tokenizer.nextToken();
        tokenizer.pushBack();
        return type == StreamTokenizer.TT_WORD;
    }

    private double getNextNumber() throws IOException,
            ParseException {
        int type = tokenizer.nextToken();
        switch (type) {
            case StreamTokenizer.TT_WORD: {
                if (tokenizer.sval.equalsIgnoreCase(NAN_SYMBOL)) {
                    return Double.NaN;
                } else {
                    try {
                        return Double.parseDouble(tokenizer.sval);
                    } catch (NumberFormatException ex) {
                        parseErrorWithLine("Invalid number: " + tokenizer.sval);
                    }
                }
            }
        }
        parseErrorExpected("number");
        return 0.0;
    }

    private String getNextEmptyOrOpener() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equals(EMPTY) || nextWord.equals(L_PAREN)) {
            return nextWord;
        }
        parseErrorExpected(EMPTY + " or " + L_PAREN);
        return null;
    }


    private String getNextCloserOrComma() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equals(COMMA) || nextWord.equals(R_PAREN)) {
            return nextWord;
        }
        parseErrorExpected(COMMA + " or " + R_PAREN);
        return null;
    }


    private String getNextCloser() throws IOException, ParseException {
        String nextWord = getNextWord();
        if (nextWord.equals(R_PAREN)) {
            return nextWord;
        }
        parseErrorExpected(R_PAREN);
        return null;
    }

    private String getNextWord() throws IOException, ParseException {
        int type = tokenizer.nextToken();
        switch (type) {
            case StreamTokenizer.TT_WORD:

                String word = tokenizer.sval;
                if (word.equalsIgnoreCase(EMPTY))
                    return EMPTY;
                return word;

            case '(':
                return L_PAREN;
            case ')':
                return R_PAREN;
            case ',':
                return COMMA;
        }
        parseErrorExpected("word");
        return null;
    }

    private String lookaheadWord() throws IOException, ParseException {
        String nextWord = getNextWord();
        tokenizer.pushBack();
        return nextWord;
    }

    private void parseErrorExpected(String expected)
            throws ParseException {
        // throws Asserts for tokens that should never be seen
        if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
            assert false;//Assert.shouldNeverReachHere("Unexpected NUMBER token");
        if (tokenizer.ttype == StreamTokenizer.TT_EOL)
            assert false;//Assert.shouldNeverReachHere("Unexpected EOL token");

        String tokenStr = tokenString();
        parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
    }

    private void parseErrorWithLine(String msg)
            throws ParseException {
        throw new ParseException(msg + " (line " + tokenizer.lineno() + ")");
    }

    /**
     * Gets a description of the current token
     *
     * @return a description of the current token
     */
    private String tokenString() {
        switch (tokenizer.ttype) {
            case StreamTokenizer.TT_NUMBER:
                return "<NUMBER>";
            case StreamTokenizer.TT_EOL:
                return "End-of-Line";
            case StreamTokenizer.TT_EOF:
                return "End-of-Stream";
            case StreamTokenizer.TT_WORD:
                return "'" + tokenizer.sval + "'";
        }
        return "'" + (char) tokenizer.ttype + "'";
    }

    private Geometry readGeometryTaggedText() throws IOException, ParseException {
        String type = null;

        try {
            type = getNextWord();
        } catch (IOException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }

        if (type.equalsIgnoreCase("POINT")) {
            return readPointText();
        } else if (type.equalsIgnoreCase("LINESTRING")) {
            return readLineStringText();
        } else if (type.equalsIgnoreCase("LINEARRING")) {
            return readLinearRingText();
        } else if (type.equalsIgnoreCase("POLYGON")) {
            return readPolygonText();
        } else if (type.equalsIgnoreCase("MULTIPOINT")) {
            return readMultiPointText();
        } else if (type.equalsIgnoreCase("MULTILINESTRING")) {
            return readMultiLineStringText();
        } else if (type.equalsIgnoreCase("MULTIPOLYGON")) {
            return readMultiPolygonText();
        } else if (type.equalsIgnoreCase("GEOMETRYCOLLECTION")) {
            return readGeometryCollectionText();
        }
        parseErrorWithLine("Unknown geometry type: " + type);
        // should never reach here
        return null;
    }

    private Point readPointText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new Point(Vector.NULL_ORDINATE, Vector.NULL_ORDINATE);
        }
        Point point = new Point(getPreciseCoordinate());
        getNextCloser();
        return point;
    }

    private LineString readLineStringText() throws IOException, ParseException {
        return new LineString(getCoordinates());
    }


    private LinearRing readLinearRingText()
            throws IOException, ParseException {
        return new LinearRing(getCoordinates());
    }


    private static final boolean ALLOW_OLD_JTS_MULTIPOINT_SYNTAX = true;


    private MultiPoint readMultiPointText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new MultiPoint(new Point[0]);
        }

        // check for old-style JTS syntax and parse it if present
        // MD 2009-02-21 - this is only provided for backwards compatibility for a few versions
        if (ALLOW_OLD_JTS_MULTIPOINT_SYNTAX) {
            String nextWord = lookaheadWord();
            if (nextWord != L_PAREN) {
                return new MultiPoint(toPoints(getCoordinatesNoLeftParen()));
            }
        }

        ArrayList points = new ArrayList();
        Point point = readPointText();
        points.add(point);
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            point = readPointText();
            points.add(point);
            nextToken = getNextCloserOrComma();
        }
        Point[] array = new Point[points.size()];
        return new MultiPoint((Point[]) points.toArray(array));
    }

    private Point[] toPoints(Vector[] coordinates) {
        ArrayList points = new ArrayList();
        for (int i = 0; i < coordinates.length; i++) {
            points.add(new Point(coordinates[i]));
        }
        return (Point[]) points.toArray(new Point[]{});
    }

    private Polygon readPolygonText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new Polygon(new LinearRing(
                    new Vector[]{}), new LinearRing[]{});
        }
        ArrayList holes = new ArrayList();
        LinearRing shell = readLinearRingText();
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            LinearRing hole = readLinearRingText();
            holes.add(hole);
            nextToken = getNextCloserOrComma();
        }
        LinearRing[] array = new LinearRing[holes.size()];
        return new Polygon(shell, (LinearRing[]) holes.toArray(array));
    }

    private MultiLineString readMultiLineStringText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new MultiLineString(new LineString[]{});
        }
        ArrayList lineStrings = new ArrayList();
        LineString lineString = readLineStringText();
        lineStrings.add(lineString);
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            lineString = readLineStringText();
            lineStrings.add(lineString);
            nextToken = getNextCloserOrComma();
        }
        LineString[] array = new LineString[lineStrings.size()];
        return new MultiLineString((LineString[]) lineStrings.toArray(array));
    }

    private MultiPolygon readMultiPolygonText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new MultiPolygon(new Polygon[]{});
        }
        ArrayList polygons = new ArrayList();
        Polygon polygon = readPolygonText();
        polygons.add(polygon);
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            polygon = readPolygonText();
            polygons.add(polygon);
            nextToken = getNextCloserOrComma();
        }
        Polygon[] array = new Polygon[polygons.size()];
        return new MultiPolygon((Polygon[]) polygons.toArray(array));
    }

    private GeometryCollection readGeometryCollectionText() throws IOException, ParseException {
        String nextToken = getNextEmptyOrOpener();
        if (nextToken.equals(EMPTY)) {
            return new GeometryCollection(new Geometry[]{});
        }
        ArrayList geometries = new ArrayList();
        Geometry geometry = readGeometryTaggedText();
        geometries.add(geometry);
        nextToken = getNextCloserOrComma();
        while (nextToken.equals(COMMA)) {
            geometry = readGeometryTaggedText();
            geometries.add(geometry);
            nextToken = getNextCloserOrComma();
        }
        Geometry[] array = new Geometry[geometries.size()];
        return new GeometryCollection((Geometry[]) geometries.toArray(array));
    }

}

//class WKTReaderImpl implements WKTReader {
//    private static final long serialVersionUID = 1L;
//    private  static WKTReader2  wktReader2;
//
//    public WKTReaderImpl() {
//        wktReader2 = new WKTReader2();
//    }
//
//    public WKTReaderImpl(double tolerance) {
//        wktReader2 = new WKTReader2(tolerance);
//    }
//
//    public Geometry read(String wellKnownText) {
//        try {
//            Geometry g =  JTSWrapper.toGTLGeometry(wktReader2.read(wellKnownText));
//            return g;
//        }
//        catch (ParseException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public Geometry read(Reader reader) {
//        try {
//            Geometry g =  JTSWrapper.toGTLGeometry(wktReader2.read(reader));
//            return g;
//        }
//        catch (ParseException e){
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
