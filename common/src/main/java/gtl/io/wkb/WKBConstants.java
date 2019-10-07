package gtl.io.wkb;

/**
 * Constant values used by the WKB format
 */
public interface WKBConstants {
    int wkbXDR = 0;
    int wkbNDR = 1;

    int wkbPoint = 1;
    int wkbLineString = 2;
    int wkbPolygon = 3;
    int wkbMultiPoint = 4;
    int wkbMultiLineString = 5;
    int wkbMultiPolygon = 6;
    int wkbGeometryCollection = 7;
}
