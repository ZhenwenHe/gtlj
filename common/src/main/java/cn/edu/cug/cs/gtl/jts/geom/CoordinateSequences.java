/*
 * The JTS Topology Suite is a collection of Java classes that
 * implement the fundamental operations required to validate a given
 * geo-spatial data set to a known topological specification.
 *
 * Copyright (C) 2001 Vivid Solutions
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * For more information, contact:
 *
 *     Vivid Solutions
 *     Suite #1A
 *     2328 Government Street
 *     Victoria BC  V8T 5G5
 *     Canada
 *
 *     (250)385-6040
 *     www.vividsolutions.com
 */
package cn.edu.cug.cs.gtl.jts.geom;

import cn.edu.cug.cs.gtl.jts.geom.impl.CoordinateArraySequence;
import cn.edu.cug.cs.gtl.jts.util.StringUtil;
import cn.edu.cug.cs.gtl.jts.algorithm.RobustDeterminant;
import cn.edu.cug.cs.gtl.jts.geom.impl.CoordinateArraySequence;
import cn.edu.cug.cs.gtl.jts.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility functions for manipulating {@link CoordinateSequence}s
 *
 * @version 1.7
 */
public class CoordinateSequences {

    /**
     * Reverses the coordinates in a sequence in-place.
     */
    public static void reverse(CoordinateSequence seq) {
        int last = seq.size() - 1;
        int mid = last / 2;
        for (int i = 0; i <= mid; i++) {
            swap(seq, i, last - i);
        }
    }

    /**
     * Swaps two coordinates in a sequence.
     *
     * @param seq the sequence to modify
     * @param i   the index of a coordinate to swap
     * @param j   the index of a coordinate to swap
     */
    public static void swap(CoordinateSequence seq, int i, int j) {
        if (i == j) return;
        for (int dim = 0; dim < seq.getDimension(); dim++) {
            double tmp = seq.getOrdinate(i, dim);
            seq.setOrdinate(i, dim, seq.getOrdinate(j, dim));
            seq.setOrdinate(j, dim, tmp);
        }
    }

    /**
     * Copies a section of a {@link CoordinateSequence} to another {@link CoordinateSequence}.
     * The sequences may have different dimensions;
     * in this case only the common dimensions are copied.
     *
     * @param src     the sequence to copy from
     * @param srcPos  the position in the source sequence to start copying at
     * @param dest    the sequence to copy to
     * @param destPos the position in the destination sequence to copy to
     * @param length  the number of coordinates to copy
     */
    public static void copy(CoordinateSequence src, int srcPos, CoordinateSequence dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            copyCoord(src, srcPos + i, dest, destPos + i);
        }
    }

    /**
     * Copies a coordinate of a {@link CoordinateSequence} to another {@link CoordinateSequence}.
     * The sequences may have different dimensions;
     * in this case only the common dimensions are copied.
     *
     * @param src     the sequence to copy from
     * @param srcPos  the source coordinate to copy
     * @param dest    the sequence to copy to
     * @param destPos the destination coordinate to copy to
     */
    public static void copyCoord(CoordinateSequence src, int srcPos, CoordinateSequence dest, int destPos) {
        int minDim = Math.min(src.getDimension(), dest.getDimension());
        for (int dim = 0; dim < minDim; dim++) {
            dest.setOrdinate(destPos, dim, src.getOrdinate(srcPos, dim));
        }
    }

    /**
     * Tests whether a {@link CoordinateSequence} forms a valid {@link LinearRing},
     * by checking the sequence length and closure
     * (whether the first and last points are identical in 2D).
     * Self-intersection is not checked.
     *
     * @param seq the sequence to test
     * @return true if the sequence is a ring
     * @see LinearRing
     */
    public static boolean isRing(CoordinateSequence seq) {
        int n = seq.size();
        if (n == 0) return true;
        // too few points
        if (n <= 3)
            return false;
        // test if closed
        return seq.getOrdinate(0, CoordinateSequence.X) == seq.getOrdinate(n - 1, CoordinateSequence.X)
                && seq.getOrdinate(0, CoordinateSequence.Y) == seq.getOrdinate(n - 1, CoordinateSequence.Y);
    }

    /**
     * Ensures that a CoordinateSequence forms a valid ring,
     * returning a new closed sequence of the correct length if required.
     * If the input sequence is already a valid ring, it is returned
     * without modification.
     * If the input sequence is too short or is not closed,
     * it is extended with one or more copies of the start point.
     *
     * @param fact the CoordinateSequenceFactory to use to create the new sequence
     * @param seq  the sequence to test
     * @return the original sequence, if it was a valid ring, or a new sequence which is valid.
     */
    public static CoordinateSequence ensureValidRing(CoordinateSequenceFactory fact, CoordinateSequence seq) {
        int n = seq.size();
        // empty sequence is valid
        if (n == 0) return seq;
        // too short - make a new one
        if (n <= 3)
            return createClosedRing(fact, seq, 4);

        boolean isClosed = seq.getOrdinate(0, CoordinateSequence.X) == seq.getOrdinate(n - 1, CoordinateSequence.X)
                && seq.getOrdinate(0, CoordinateSequence.Y) == seq.getOrdinate(n - 1, CoordinateSequence.Y);
        if (isClosed) return seq;
        // make a new closed ring
        return createClosedRing(fact, seq, n + 1);
    }

    private static CoordinateSequence createClosedRing(CoordinateSequenceFactory fact, CoordinateSequence seq, int size) {
        CoordinateSequence newseq = fact.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        // fill remaining coordinates with start point
        for (int i = n; i < size; i++)
            copy(seq, 0, newseq, i, 1);
        return newseq;
    }

    public static CoordinateSequence extend(CoordinateSequenceFactory fact, CoordinateSequence seq, int size) {
        CoordinateSequence newseq = fact.create(size, seq.getDimension());
        int n = seq.size();
        copy(seq, 0, newseq, 0, n);
        // fill remaining coordinates with end point, if it exists
        if (n > 0) {
            for (int i = n; i < size; i++)
                copy(seq, n - 1, newseq, i, 1);
        }
        return newseq;
    }

    /**
     * Tests whether two {@link CoordinateSequence}s are equal.
     * To be equal, the sequences must be the same length.
     * They do not need to be of the same dimension,
     * but the ordinate values for the smallest dimension of the two
     * must be equal.
     * Two <code>NaN</code> ordinates values are considered to be equal.
     *
     * @param cs1 a CoordinateSequence
     * @param cs2 a CoordinateSequence
     * @return true if the sequences are equal in the common dimensions
     */
    public static boolean isEqual(CoordinateSequence cs1, CoordinateSequence cs2) {
        int cs1Size = cs1.size();
        int cs2Size = cs2.size();
        if (cs1Size != cs2Size) return false;
        int dim = Math.min(cs1.getDimension(), cs2.getDimension());
        for (int i = 0; i < cs1Size; i++) {
            for (int d = 0; d < dim; d++) {
                double v1 = cs1.getOrdinate(i, d);
                double v2 = cs2.getOrdinate(i, d);
                if (cs1.getOrdinate(i, d) == cs2.getOrdinate(i, d))
                    continue;
                // special check for NaNs
                if (Double.isNaN(v1) && Double.isNaN(v2))
                    continue;
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a string representation of a {@link CoordinateSequence}.
     * The format is:
     * <pre>
     *   ( ord0,ord1.. ord0,ord1,...  ... )
     * </pre>
     *
     * @param cs the sequence to output
     * @return the string representation of the sequence
     */
    public static String toString(CoordinateSequence cs) {
        int size = cs.size();
        if (size == 0)
            return "()";
        int dim = cs.getDimension();
        StringBuffer buf = new StringBuffer();
        buf.append('(');
        for (int i = 0; i < size; i++) {
            if (i > 0) buf.append(" ");
            for (int d = 0; d < dim; d++) {
                if (d > 0) buf.append(",");
                buf.append(StringUtil.toString(cs.getOrdinate(i, d)));
            }
        }
        buf.append(')');
        return buf.toString();
    }

    /**
     * Computes whether a ring defined by an array of {@link Coordinate}s is
     * oriented counter-clockwise.
     * <ul>
     * <li>The list of points is assumed to have the first and last points equal.
     * <li>This will handle coordinate lists which contain repeated points.
     * </ul>
     * This algorithm is <b>only</b> guaranteed to work with valid rings.
     * If the ring is invalid (e.g. self-crosses or touches),
     * the computed result may not be correct.
     *
     * @param ring an array of Coordinates forming a ring
     * @return true if the ring is oriented counter-clockwise.
     */
    public static boolean isCCW(CoordinateSequence ring) {
        // # of points without closing endpoint
        int nPts = ring.size() - 1;

        // find highest point
        double hiy = ring.getOrdinate(0, 1);
        int hiIndex = 0;
        for (int i = 1; i <= nPts; i++) {
            if (ring.getOrdinate(i, 1) > hiy) {
                hiy = ring.getOrdinate(i, 1);
                hiIndex = i;
            }
        }

        // find distinct point before highest point
        int iPrev = hiIndex;
        do {
            iPrev = iPrev - 1;
            if (iPrev < 0) iPrev = nPts;
        } while (equals2D(ring, iPrev, hiIndex) && iPrev != hiIndex);

        // find distinct point after highest point
        int iNext = hiIndex;
        do {
            iNext = (iNext + 1) % nPts;
        } while (equals2D(ring, iNext, hiIndex) && iNext != hiIndex);

        /**
         * This check catches cases where the ring contains an A-B-A configuration of points.
         * This can happen if the ring does not contain 3 distinct points
         * (including the case where the input array has fewer than 4 elements),
         * or it contains coincident line segments.
         */
        if (equals2D(ring, iPrev, hiIndex) || equals2D(ring, iNext, hiIndex) || equals2D(ring, iPrev, iNext))
            return false;

        int disc = computeOrientation(ring, iPrev, hiIndex, iNext);

        /**
         *  If disc is exactly 0, lines are collinear.  There are two possible cases:
         *  (1) the lines lie along the x axis in opposite directions
         *  (2) the lines lie on top of one another
         *
         *  (1) is handled by checking if next is left of prev ==> CCW
         *  (2) will never happen if the ring is valid, so don't check for it
         *  (Might want to assert this)
         */
        boolean isCCW = false;
        if (disc == 0) {
            // poly is CCW if prev x is right of next x
            isCCW = (ring.getOrdinate(iPrev, 0) > ring.getOrdinate(iNext, 0));
        } else {
            // if area is positive, points are ordered CCW
            isCCW = (disc > 0);
        }
        return isCCW;
    }

    private static boolean equals2D(CoordinateSequence cs, int i, int j) {
        return cs.getOrdinate(i, 0) == cs.getOrdinate(j, 0) &&
                cs.getOrdinate(i, 1) == cs.getOrdinate(j, 1);
    }

    public static int computeOrientation(CoordinateSequence cs, int p1, int p2, int q) {
        // travelling along p1->p2, turn counter clockwise to get to q return 1,
        // travelling along p1->p2, turn clockwise to get to q return -1,
        // p1, p2 and q are colinear return 0.
        double p1x = cs.getOrdinate(p1, 0);
        double p1y = cs.getOrdinate(p1, 1);
        double p2x = cs.getOrdinate(p2, 0);
        double p2y = cs.getOrdinate(p2, 1);
        double qx = cs.getOrdinate(q, 0);
        double qy = cs.getOrdinate(q, 1);
        double dx1 = p2x - p1x;
        double dy1 = p2y - p1y;
        double dx2 = qx - p2x;
        double dy2 = qy - p2y;
        return RobustDeterminant.signOfDet2x2(dx1, dy1, dx2, dy2);
    }


    /**
     * Gets the dimension of the coordinates in a {@link Geometry},
     * by reading it from a component {@link CoordinateSequence}.
     * This will be usually either 2 or 3.
     *
     * @param g a Geometry
     * @return the dimension of the coordinates in the Geometry
     */
    public static int coordinateDimension(Geometry g) {

        if (g instanceof Point)
            return coordinateDimension(((Point) g).getCoordinateSequence());
        if (g instanceof LineString)
            return coordinateDimension(((LineString) g).getCoordinateSequence());
        if (g instanceof Polygon)
            return coordinateDimension(((Polygon) g).getExteriorRing()
                    .getCoordinateSequence());

        // dig down to find a CS
        CoordinateSequence cs = CoordinateSequences.CoordinateSequenceFinder.find(g);
        return coordinateDimension(cs);
    }

    /**
     * Gets the effective dimension of a CoordinateSequence.
     * This is a workaround for the issue that CoordinateArraySequence
     * does not keep an accurate dimension - it always
     * reports dim=3, even if there is no Z ordinate (ie they are NaN).
     * This method checks for that case and reports dim=2.
     * Only the first coordinate is checked.
     * <p>
     * There is one small hole: if a CoordinateArraySequence is empty,
     * the dimension will be reported as 3.
     *
     * @param seq a CoordinateSequence
     * @return the effective dimension of the coordinate sequence
     */
    public static int coordinateDimension(CoordinateSequence seq) {
        if (seq == null) return 3;

        int dim = seq.getDimension();
        if (dim != 3)
            return dim;

        // hack to handle issue that CoordinateArraySequence always reports
        // dimension = 3
        // check if a Z value is NaN - if so, assume dim is 2
        if (seq instanceof CoordinateArraySequence) {
            if (seq.size() > 0) {
                if (Double.isNaN(seq.getOrdinate(0, CoordinateSequence.Y)))
                    return 1;
                if (Double.isNaN(seq.getOrdinate(0, CoordinateSequence.Z)))
                    return 2;
            }
        }
        return 3;
    }

    /**
     * Returns true if the two geometries are equal in N dimensions (normal geometry equality is only 2D)
     *
     * @param g1
     * @param g2
     * @return
     */
    public static boolean equalsND(Geometry g1, Geometry g2) {
        // if not even in 2d, they are not equal
        if (!g1.equals(g2)) {
            return false;
        }
        int dim1 = coordinateDimension(g1);
        int dim2 = coordinateDimension(g2);
        if (dim1 != dim2) {
            return false;
        }
        if (dim1 == 2) {
            return true;
        }

        // ok, 2d equal, it means they have the same list of geometries and coordinate sequences, in the same order
        List<CoordinateSequence> sequences1 = CoordinateSequences.CoordinateSequenceCollector.find(g1);
        List<CoordinateSequence> sequences2 = CoordinateSequences.CoordinateSequenceCollector.find(g2);
        if (sequences1.size() != sequences2.size()) {
            return false;
        }
        CoordinateSequenceComparator comparator = new CoordinateSequenceComparator();
        for (int i = 0; i < sequences1.size(); i++) {
            CoordinateSequence cs1 = sequences1.get(i);
            CoordinateSequence cs2 = sequences2.get(i);
            if (comparator.compare(cs1, cs2) != 0) {
                return false;
            }
        }

        return true;
    }

    private static class CoordinateSequenceFinder implements CoordinateSequenceFilter {

        public static CoordinateSequence find(Geometry g) {
            CoordinateSequences.CoordinateSequenceFinder finder = new CoordinateSequences.CoordinateSequenceFinder();
            g.apply(finder);
            return finder.getSeq();
        }

        private CoordinateSequence firstSeqFound = null;

        /**
         * Gets the coordinate sequence found (if any).
         *
         * @return null if no sequence could be found
         */
        public CoordinateSequence getSeq() {
            return firstSeqFound;
        }

        public void filter(CoordinateSequence seq, int i) {
            if (firstSeqFound == null)
                firstSeqFound = seq;

        }

        public boolean isDone() {
            return firstSeqFound != null;
        }

        public boolean isGeometryChanged() {
            return false;
        }

    }

    private static class CoordinateSequenceCollector implements CoordinateSequenceFilter {

        public static List<CoordinateSequence> find(Geometry g) {
            CoordinateSequences.CoordinateSequenceCollector finder = new CoordinateSequences.CoordinateSequenceCollector();
            g.apply(finder);
            return finder.getSequences();
        }

        private List<CoordinateSequence> sequences = new ArrayList<>();

        public List<CoordinateSequence> getSequences() {
            return sequences;
        }

        public void filter(CoordinateSequence seq, int i) {
            sequences.add(seq);

        }

        public boolean isDone() {
            return false;
        }

        public boolean isGeometryChanged() {
            return false;
        }

    }
}