package cn.edu.cug.cs.gtl.jts.geom;

/**
 * Created by hadoop on 17-3-20.
 */

import cn.edu.cug.cs.gtl.math.PrecisionModel;
import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.geom.Vertex2D;
import cn.edu.cug.cs.gtl.math.PrecisionModel;

/**
 * A <code>LineIntersector2D</code> is an algorithm that can both test whether
 * two line segments intersect and compute the intersection point(s)
 * if they do.
 * <p>
 * There are three possible outcomes when determining whether two line segments intersect:
 * <ul>
 * <li>{@link #NO_INTERSECTION} - the segments do not intersect
 * <li>{@link #POINT_INTERSECTION} - the segments intersect in a single point
 * <li>{@link #COLLINEAR_INTERSECTION} - the segments are collinear and they intersect in a line segment
 * </ul>
 * For segments which intersect in a single point, the point may be either an endpoint
 * or in the interior of each segment.
 * If the point lies in the interior of both segments,
 * this is termed a <i>proper intersection</i>.
 * The method {@link #isProper()} test for this situation.
 * <p>
 * The intersection point(s) may be computed in a precise or non-precise manner.
 * Computing an intersection point precisely involves rounding it
 * via a supplied {@link cn.edu.cug.cs.gtl.math.PrecisionModel}.
 * <p>
 * LineIntersectors do not perform an initial envelope2D intersection test
 * to determine if the segments are disjoint.
 * This is because this class is likely to be used in a context where
 * envelope2D overlap is already known to occur (or be likely).
 *
 * @version 1.7
 */
public abstract class LineIntersector2D {
    /**
     * These are deprecated, due to ambiguous naming
     */
    public final static int DONT_INTERSECT = 0;
    public final static int DO_INTERSECT = 1;
    public final static int COLLINEAR = 2;

    /**
     * Indicates that line segments do not intersect
     */
    public final static int NO_INTERSECTION = 0;

    /**
     * Indicates that line segments intersect in a single point
     */
    public final static int POINT_INTERSECTION = 1;

    /**
     * Indicates that line segments intersect in a line segment
     */
    public final static int COLLINEAR_INTERSECTION = 2;
    protected int result;
    protected Vertex2D[][] inputLines = new Vertex2D[2][2];
    protected Vertex2D[] intPt = new Vertex2D[2];
    /**
     * The indexes of the endpoints of the intersection lines, in order along
     * the corresponding line
     */
    protected int[][] intLineIndex;
    protected boolean isProper;
    protected Vertex2D pa;
    protected Vertex2D pb;
    /**
     * If makePrecise is true, computed intersection coordinates will be made precise
     * using Vertex2D#makePrecise
     */
    protected cn.edu.cug.cs.gtl.math.PrecisionModel precisionModel = null;

    public LineIntersector2D() {
        intPt[0] = Geom2DSuits.createVertex2D();//new Vertex2D();
        intPt[1] = Geom2DSuits.createVertex2D();//new Vertex2D();
        // alias the intersection points for ease of reference
        pa = intPt[0];
        pb = intPt[1];
        result = 0;
    }

    /**
     * Computes the "edge distance2D" of an intersection point p along a segment.
     * The edge distance2D is a metric of the point along the edge.
     * The metric used is a robust and easy to compute metric function.
     * It is <b>not</b> equivalent to the usual Euclidean metric.
     * It relies on the fact that either the x or the y ordinates of the
     * points in the edge are unique, depending on whether the edge is longer in
     * the horizontal or vertical direction.
     * <p>
     * NOTE: This function may produce incorrect distances
     * for inputs where p is not precisely on p1-p2
     * (E.g. p = (139,9) p1 = (139,10), p2 = (280,1) produces distance2D 0.0, which is incorrect.
     * <p>
     * My hypothesis is that the function is safe to use for points which are the
     * result of <b>rounding</b> points which lie on the line,
     * but not safe to use for <b>truncated</b> points.
     */
    public static double computeEdgeDistance(
            Vertex2D p,
            Vertex2D p0,
            Vertex2D p1) {
        double dx = Math.abs(p1.x - p0.x);
        double dy = Math.abs(p1.y - p0.y);

        double dist = -1.0;   // sentinel value
        if (p.equals(p0)) {
            dist = 0.0;
        } else if (p.equals(p1)) {
            if (dx > dy)
                dist = dx;
            else
                dist = dy;
        } else {
            double pdx = Math.abs(p.x - p0.x);
            double pdy = Math.abs(p.y - p0.y);
            if (dx > dy)
                dist = pdx;
            else
                dist = pdy;
            // <FIX>
            // hack to ensure that non-endpoints always have a non-zero distance2D
            if (dist == 0.0 && !p.equals(p0)) {
                dist = Math.max(pdx, pdy);
            }
        }
        //Assert.isTrue(! (dist == 0.0 && ! p.equals(p0)), "Bad distance2D calculation");
        return dist;
    }
//public int numIntersects = 0;

    /**
     * This function is non-robust, since it may compute the square of large numbers.
     * Currently not sure how to improve this.
     */
    public static double nonRobustComputeEdgeDistance(
            Vertex2D p,
            Vertex2D p1,
            Vertex2D p2) {
        double dx = p.x - p1.x;
        double dy = p.y - p1.y;
        double dist = Math.sqrt(dx * dx + dy * dy);   // dummy value
        //Assert.isTrue(! (dist == 0.0 && ! p.equals(p1)), "Invalid distance2D calculation");
        return dist;
    }

    /**
     * Force computed intersection to be rounded to a given precision model
     *
     * @param precisionModel
     * @deprecated use <code>setPrecisionModel</code> instead
     */
    public void setMakePrecise(cn.edu.cug.cs.gtl.math.PrecisionModel precisionModel) {
        this.precisionModel = precisionModel;
    }

    /**
     * Force computed intersection to be rounded to a given precision model.
     * No getter is provided, because the precision model is not required to be specified.
     *
     * @param precisionModel
     */
    public void setPrecisionModel(PrecisionModel precisionModel) {
        this.precisionModel = precisionModel;
    }

    /**
     * Gets an endpoint of an input segment.
     *
     * @param segmentIndex the index of the input segment (0 or 1)
     * @param ptIndex      the index of the endpoint (0 or 1)
     * @return the specified endpoint
     */
    public Vertex2D getEndpoint(int segmentIndex, int ptIndex) {
        return inputLines[segmentIndex][ptIndex];
    }

    /**
     * Compute the intersection of a point p and the line p1-p2.
     * This function computes the boolean value of the hasIntersection test.
     * The actual value of the intersection (if there is one)
     * is equal to the value of <code>p</code>.
     */
    public abstract void computeIntersection(
            Vertex2D p,
            Vertex2D p1, Vertex2D p2);

    protected boolean isCollinear() {
        return result == COLLINEAR_INTERSECTION;
    }

    /**
     * Computes the intersection of the lines p1-p2 and p3-p4.
     * This function computes both the boolean value of the hasIntersection test
     * and the (approximate) value of the intersection point itself (if there is one).
     */
    public void computeIntersection(
            Vertex2D p1, Vertex2D p2,
            Vertex2D p3, Vertex2D p4) {
        inputLines[0][0] = p1;
        inputLines[0][1] = p2;
        inputLines[1][0] = p3;
        inputLines[1][1] = p4;
        result = computeIntersect(p1, p2, p3, p4);
//numIntersects++;
    }

    protected abstract int computeIntersect(
            Vertex2D p1, Vertex2D p2,
            Vertex2D q1, Vertex2D q2);


//    public String toString() {
//        return WKTWriterImpl.toLineString(inputLines[0][0], inputLines[0][1]) + " - "
//                + WKTWriterImpl.toLineString(inputLines[1][0], inputLines[1][1])
//                + getTopologySummary();
//    }

    private String getTopologySummary() {
        StringBuilder catBuilder = new StringBuilder();
        if (isEndPoint()) catBuilder.append(" endpoint");
        if (isProper) catBuilder.append(" proper");
        if (isCollinear()) catBuilder.append(" collinear");
        return catBuilder.toString();
    }

    protected boolean isEndPoint() {
        return hasIntersection() && !isProper;
    }

    /**
     * Tests whether the input geometries intersect.
     *
     * @return true if the input geometries intersect
     */
    public boolean hasIntersection() {
        return result != NO_INTERSECTION;
    }

    /**
     * Returns the number of intersection points found.  This will be either 0, 1 or 2.
     *
     * @return the number of intersection points found (0, 1, or 2)
     */
    public int getIntersectionNum() {
        return result;
    }

    /**
     * Returns the intIndex'th intersection point
     *
     * @param intIndex is 0 or 1
     * @return the intIndex'th intersection point
     */
    public Vertex2D getIntersection(int intIndex) {
        return intPt[intIndex];
    }

    protected void computeIntLineIndex() {
        if (intLineIndex == null) {
            intLineIndex = new int[2][2];
            computeIntLineIndex(0);
            computeIntLineIndex(1);
        }
    }

    /**
     * Test whether a point is a intersection point of two line segments.
     * Note that if the intersection is a line segment, this method only tests for
     * equality with the endpoints of the intersection segment.
     * It does <b>not</b> return true if
     * the input point is internal to the intersection segment.
     *
     * @return true if the input point is one of the intersection points.
     */
    public boolean isIntersection(Vertex2D pt) {
        for (int i = 0; i < result; i++) {
            if (intPt[i].equals(pt)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests whether either intersection point is an interior point of one of the input segments.
     *
     * @return <code>true</code> if either intersection point is in the interior of one of the input segments
     */
    public boolean isInteriorIntersection() {
        if (isInteriorIntersection(0)) return true;
        if (isInteriorIntersection(1)) return true;
        return false;
    }

    /**
     * Tests whether either intersection point is an interior point of the specified input segment.
     *
     * @return <code>true</code> if either intersection point is in the interior of the input segment
     */
    public boolean isInteriorIntersection(int inputLineIndex) {
        for (int i = 0; i < result; i++) {
            if (!(intPt[i].equals(inputLines[inputLineIndex][0])
                    || intPt[i].equals(inputLines[inputLineIndex][1]))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tests whether an intersection is proper.
     * <br>
     * The intersection between two line segments is considered proper if
     * they intersect in a single point in the interior of both segments
     * (e.g. the intersection is a single point and is not equal to any of the
     * endpoints).
     * <p>
     * The intersection between a point and a line segment is considered proper
     * if the point lies in the interior of the segment (e.g. is not equal to
     * either of the endpoints).
     *
     * @return true if the intersection is proper
     */
    public boolean isProper() {
        return hasIntersection() && isProper;
    }

    /**
     * Computes the intIndex'th intersection point in the direction of
     * a specified input line segment
     *
     * @param segmentIndex is 0 or 1
     * @param intIndex     is 0 or 1
     * @return the intIndex'th intersection point in the direction of the specified input line segment
     */
    public Vertex2D getIntersectionAlongSegment(int segmentIndex, int intIndex) {
        // lazily compute int line array
        computeIntLineIndex();
        return intPt[intLineIndex[segmentIndex][intIndex]];
    }

    /**
     * Computes the index (order) of the intIndex'th intersection point in the direction of
     * a specified input line segment
     *
     * @param segmentIndex is 0 or 1
     * @param intIndex     is 0 or 1
     * @return the index of the intersection point along the input segment (0 or 1)
     */
    public int getIndexAlongSegment(int segmentIndex, int intIndex) {
        computeIntLineIndex();
        return intLineIndex[segmentIndex][intIndex];
    }

    protected void computeIntLineIndex(int segmentIndex) {
        double dist0 = getEdgeDistance(segmentIndex, 0);
        double dist1 = getEdgeDistance(segmentIndex, 1);
        if (dist0 > dist1) {
            intLineIndex[segmentIndex][0] = 0;
            intLineIndex[segmentIndex][1] = 1;
        } else {
            intLineIndex[segmentIndex][0] = 1;
            intLineIndex[segmentIndex][1] = 0;
        }
    }

    /**
     * Computes the "edge distance2D" of an intersection point along the specified input line segment.
     *
     * @param segmentIndex is 0 or 1
     * @param intIndex     is 0 or 1
     * @return the edge distance2D of the intersection point
     */
    public double getEdgeDistance(int segmentIndex, int intIndex) {
        double dist = computeEdgeDistance(intPt[intIndex], inputLines[segmentIndex][0],
                inputLines[segmentIndex][1]);
        return dist;
    }
}