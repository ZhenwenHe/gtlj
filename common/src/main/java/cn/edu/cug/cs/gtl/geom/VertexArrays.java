package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-20.
 */

import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.util.Collection;
import java.util.Comparator;

/**
 * Useful utility fn for handling Vertex arrays
 */
class VertexArrays {

    private final static Vertex[] coordArrayType = new Vertex[0];

    public VertexArrays() {
    }

    /**
     * Tests whether an array of {@link Vertex}s forms a ring,
     * by checking length and closure.
     * Self-intersection is not checked.
     *
     * @param pts an array of Coordinates
     * @return true if the coordinate form a ring.
     */
    public static boolean isRing(Vertex[] pts) {
        if (pts.length < 4) return false;
        if (!pts[0].equals2D(pts[pts.length - 1])) return false;
        return true;
    }

    /**
     * Finds a point in a list of points which is not contained in another list of points
     *
     * @param testPts the {@link Vertex}s to test
     * @param pts     an array of {@link Vertex}s to test the input points against
     * @return a {@link Vertex} from <code>testPts</code> which is not in <code>pts</code>, '
     * or <code>null</code>
     */
    public static Vertex ptNotInList(Vertex[] testPts, Vertex[] pts) {
        for (int i = 0; i < testPts.length; i++) {
            Vertex testPt = testPts[i];
            if (VertexArrays.indexOf(testPt, pts) < 0)
                return testPt;
        }
        return null;
    }

    /**
     * Compares two {@link Vertex} arrays
     * in the forward direction of their coordinates,
     * using lexicographic ordering.
     *
     * @param pts1
     * @param pts2
     * @return an integer indicating the order
     */
    public static int compare(Vertex[] pts1, Vertex[] pts2) {
        int i = 0;
        while (i < pts1.length && i < pts2.length) {
            int compare = pts1[i].compareTo(pts2[i]);
            if (compare != 0)
                return compare;
            i++;
        }
        // handle situation when arrays are of different length
        if (i < pts2.length) return -1;
        if (i < pts1.length) return 1;

        return 0;
    }

    /**
     * Determines which orientation of the {@link Vertex} array
     * is (overall) increasing.
     * In other words, determines which end of the array is "smaller"
     * (using the standard ordering on {@link Vertex}).
     * Returns an integer indicating the increasing direction.
     * If the sequence is a palindrome, it is defined to be
     * oriented in a positive direction.
     *
     * @param pts the array of Coordinates to test
     * @return <code>1</code> if the array is smaller at the start
     * or is a palindrome,
     * <code>-1</code> if smaller at the end
     */
    public static int increasingDirection(Vertex[] pts) {
        for (int i = 0; i < pts.length / 2; i++) {
            int j = pts.length - 1 - i;
            // skip equal points on both ends
            int comp = pts[i].compareTo(pts[j]);
            if (comp != 0)
                return comp;
        }
        // array must be a palindrome - defined to be in positive direction
        return 1;
    }

    /**
     * Determines whether two {@link Vertex} arrays of equal length
     * are equal in opposite directions.
     *
     * @param pts1
     * @param pts2
     * @return <code>true</code> if the two arrays are equal in opposite directions.
     */
    private static boolean isEqualReversed(Vertex[] pts1, Vertex[] pts2) {
        for (int i = 0; i < pts1.length; i++) {
            Vertex p1 = pts1[i];
            Vertex p2 = pts2[pts1.length - i - 1];
            if (p1.compareTo(p2) != 0)
                return false;
        }
        return true;
    }

    /**
     * Creates a deep copy of the argument {@link Vertex} array.
     *
     * @param vertices an array of Coordinates
     * @return a deep copy of the input
     */
    public static Vertex[] copyDeep(Vertex[] vertices) {
        Vertex[] copy = new Vertex[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            copy[i] = new VertexImpl((VertexImpl) vertices[i]);
        }
        return copy;
    }

    /**
     * Creates a deep copy of a given section of a source {@link Vertex} array
     * into a destination Vertex array.
     * The destination array must be an appropriate size to receive
     * the copied coordinates.
     *
     * @param src       an array of Coordinates
     * @param srcStart  the index to start copying from
     * @param dest      the
     * @param destStart the destination index to start copying to
     * @param length    the number of items to copy
     */
    public static void copyDeep(Vertex[] src, int srcStart, Vertex[] dest, int destStart, int length) {
        for (int i = 0; i < length; i++) {
            dest[destStart + i] = new VertexImpl((VertexImpl) src[srcStart + i]);
        }
    }

    /**
     * Converts the given Collection of Coordinates into a Vertex array.
     */
    public static Vertex[] toCoordinateArray(Collection<Vertex> coordList) {
        return (Vertex[]) coordList.toArray(coordArrayType);
    }

    /**
     * Returns whether #equals returns true for any two consecutive Coordinates
     * in the given array.
     */
    public static boolean hasRepeatedPoints(Vertex[] coord) {
        for (int i = 1; i < coord.length; i++) {
            if (coord[i - 1].equals(coord[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns either the given coordinate array if its length is greater than the
     * given amount, or an empty coordinate array.
     */
    public static Vertex[] atLeastNCoordinatesOrNothing(int n, Vertex[] c) {
        return c.length >= n ? c : new Vertex[]{};
    }

    /**
     * If the coordinate array argument has repeated points,
     * constructs a new array containing no repeated points.
     * Otherwise, returns the argument.
     *
     * @see #hasRepeatedPoints(Vertex[])
     */
    public static Vertex[] removeRepeatedPoints(Vertex[] coord) {
        if (!hasRepeatedPoints(coord)) return coord;
        VertexList coordList = new VertexList(coord, false);
        return coordList.toCoordinateArray();
    }

    /**
     * Collapses a coordinate array to remove all null elements.
     *
     * @param coord the coordinate array to collapse
     * @return an array containing only non-null elements
     */
    public static Vertex[] removeNull(Vertex[] coord) {
        int nonNull = 0;
        for (int i = 0; i < coord.length; i++) {
            if (coord[i] != null) nonNull++;
        }
        Vertex[] newCoord = new Vertex[nonNull];
        // empty case
        if (nonNull == 0) return newCoord;

        int j = 0;
        for (int i = 0; i < coord.length; i++) {
            if (coord[i] != null) newCoord[j++] = coord[i];
        }
        return newCoord;
    }

    /**
     * Reverses the coordinates in an array in-place.
     */
    public static void reverse(Vertex[] coord) {
        int last = coord.length - 1;
        int mid = last / 2;
        for (int i = 0; i <= mid; i++) {
            Vertex tmp = coord[i];
            coord[i] = coord[last - i];
            coord[last - i] = tmp;
        }
    }

    /**
     * Returns true if the two arrays are identical, both null, or pointwise
     * equal (as compared using Vertex#equals)
     *
     * @see Vertex#equals(Object)
     */
    public static boolean equals(
            Vertex[] coord1,
            Vertex[] coord2) {
        if (coord1 == coord2) return true;
        if (coord1 == null || coord2 == null) return false;
        if (coord1.length != coord2.length) return false;
        for (int i = 0; i < coord1.length; i++) {
            if (!coord1[i].equals(coord2[i])) return false;
        }
        return true;
    }

    /**
     * Returns true if the two arrays are identical, both null, or pointwise
     * equal, using a user-defined {@link Comparator} for {@link Vertex} s
     *
     * @param coord1               an array of Coordinates
     * @param coord2               an array of Coordinates
     * @param coordinateComparator a Comparator for Coordinates
     */
    public static boolean equals(
            Vertex[] coord1,
            Vertex[] coord2,
            Comparator coordinateComparator) {
        if (coord1 == coord2) return true;
        if (coord1 == null || coord2 == null) return false;
        if (coord1.length != coord2.length) return false;
        for (int i = 0; i < coord1.length; i++) {
            if (coordinateComparator.compare(coord1[i], coord2[i]) != 0)
                return false;
        }
        return true;
    }

    /**
     * Returns the minimum coordinate, using the usual lexicographic comparison.
     *
     * @param vertices the array to search
     * @return the minimum coordinate in the array, found using <code>compareTo</code>
     * @see Vertex#compareTo(Object)
     */
    public static Vertex minCoordinate(Vertex[] vertices) {
        Vertex minCoord = null;
        for (int i = 0; i < vertices.length; i++) {
            if (minCoord == null || minCoord.compareTo(vertices[i]) > 0) {
                minCoord = vertices[i];
            }
        }
        return minCoord;
    }

    /**
     * Shifts the positions of the vertices until <code>firstVertex</code>
     * is first.
     *
     * @param vertices    the array to rearrange
     * @param firstVertex the coordinate to make first
     */
    public static void scroll(Vertex[] vertices, Vertex firstVertex) {
        int i = indexOf(firstVertex, vertices);
        if (i < 0) return;
        Vertex[] newVertices = new Vertex[vertices.length];
        System.arraycopy(vertices, i, newVertices, 0, vertices.length - i);
        System.arraycopy(vertices, 0, newVertices, vertices.length - i, i);
        System.arraycopy(newVertices, 0, vertices, 0, vertices.length);
    }

    /**
     * Returns the index of <code>vertex</code> in <code>vertices</code>.
     * The first position is 0; the second, 1; etc.
     *
     * @param vertex   the <code>Vertex</code> to search for
     * @param vertices the array to search
     * @return the position of <code>vertex</code>, or -1 if it is
     * not found
     */
    public static int indexOf(Vertex vertex, Vertex[] vertices) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertex.equals(vertices[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Extracts a subsequence of the input {@link Vertex} array
     * from indices <code>start</code> to
     * <code>end</code> (inclusive).
     * The input indices are clamped to the array size;
     * If the end index is less than the start index,
     * the extracted array will be empty.
     *
     * @param pts   the input array
     * @param start the index of the start of the subsequence to extract
     * @param end   the index of the end of the subsequence to extract
     * @return a subsequence of the input array
     */
    public static Vertex[] extract(Vertex[] pts, int start, int end) {
        start = MathSuits.clamp(start, 0, pts.length);
        end = MathSuits.clamp(end, -1, pts.length);

        int npts = end - start + 1;
        if (end < 0) npts = 0;
        if (start >= pts.length) npts = 0;
        if (end < start) npts = 0;

        Vertex[] extractPts = new Vertex[npts];
        if (npts == 0) return extractPts;

        int iPts = 0;
        for (int i = start; i <= end; i++) {
            extractPts[iPts++] = pts[i];
        }
        return extractPts;
    }

    /**
     * Computes the envelope2D of the vertices.
     *
     * @param vertices the vertices to scan
     * @return the envelope2D of the vertices
     */
    public static Envelope envelope(Vertex2D[] vertices) {
        Envelope env = new Envelope();
        for (int i = 0; i < vertices.length; i++) {
            //env.expandToInclude(vertices[i]);
            env.combine(Vector.create(vertices[i].x, vertices[i].y));
        }
        return env;
    }

    /**
     * Extracts the vertices which intersect an {@link Envelope}.
     *
     * @param vertices the vertices to scan
     * @param env      the envelope2D to intersect with
     * @return an array of the vertices which intersect the envelope2D
     */
    public static Vertex[] intersection(Vertex2D[] vertices, Envelope env) {
        VertexList coordList = new VertexList();
        for (int i = 0; i < vertices.length; i++) {
            if (env.contains(Vector.create(vertices[i].x, vertices[i].y)))
                coordList.add(vertices[i], true);
        }
        return coordList.toCoordinateArray();
    }

    /**
     * A {@link Comparator} for {@link Vertex} arrays
     * in the forward direction of their coordinates,
     * using lexicographic ordering.
     */
    public static class ForwardComparator
            implements Comparator<Vertex[]> {
        public int compare(Vertex[] o1, Vertex[] o2) {
            return VertexArrays.compare(o1, o2);
        }
    }

    /**
     * A {@link Comparator} for {@link Vertex} arrays
     * modulo their directionality.
     * E.g. if two coordinate arrays are identical but reversed
     * they will compare as equal under this ordering.
     * If the arrays are not equal, the ordering returned
     * is the ordering in the forward direction.
     */
    public static class BidirectionalComparator
            implements Comparator<Vertex[]> {
        public int compare(Vertex[] o1, Vertex[] o2) {
            Vertex[] pts1 = (Vertex[]) o1;
            Vertex[] pts2 = (Vertex[]) o2;

            if (pts1.length < pts2.length) return -1;
            if (pts1.length > pts2.length) return 1;

            if (pts1.length == 0) return 0;

            int forwardComp = VertexArrays.compare(pts1, pts2);
            boolean isEqualRev = isEqualReversed(pts1, pts2);
            if (isEqualRev)
                return 0;
            return forwardComp;
        }

        public int OLDcompare(Object o1, Object o2) {
            Vertex[] pts1 = (Vertex[]) o1;
            Vertex[] pts2 = (Vertex[]) o2;

            if (pts1.length < pts2.length) return -1;
            if (pts1.length > pts2.length) return 1;

            if (pts1.length == 0) return 0;

            int dir1 = increasingDirection(pts1);
            int dir2 = increasingDirection(pts2);

            int i1 = dir1 > 0 ? 0 : pts1.length - 1;
            int i2 = dir2 > 0 ? 0 : pts1.length - 1;

            for (int i = 0; i < pts1.length; i++) {
                int comparePt = pts1[i1].compareTo(pts2[i2]);
                if (comparePt != 0)
                    return comparePt;
                i1 += dir1;
                i2 += dir2;
            }
            return 0;
        }

    }
}
