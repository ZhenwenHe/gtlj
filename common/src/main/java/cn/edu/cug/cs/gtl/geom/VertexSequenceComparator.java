package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-20.
 */

import java.util.Comparator;

/**
 * Compares two {@link VertexSequence}s.
 * For sequences of the same dimension, the ordering is lexicographic.
 * Otherwise, lower dimensions are sorted before higher.
 * The dimensions compared can be limited; if this is done
 * ordinate dimensions above the limit will not be compared.
 * <p>
 * If different behaviour is required for comparing size, dimension, or
 * coordinate values, any or all methods can be overridden.
 */
class VertexSequenceComparator
        implements Comparator<VertexSequence> {
    /**
     * The number of dimensions to test
     */
    protected int dimensionLimit;

    /**
     * Creates a comparator which will test all dimensions.
     */
    public VertexSequenceComparator() {
        dimensionLimit = Integer.MAX_VALUE;
    }

    /**
     * Creates a comparator which will test only the specified number of dimensions.
     *
     * @param dimensionLimit the number of dimensions to test
     */
    public VertexSequenceComparator(int dimensionLimit) {
        this.dimensionLimit = dimensionLimit;
    }

    /**
     * Compare two <code>double</code>s, allowing for NaN values.
     * NaN is treated as being less than any valid number.
     *
     * @param a a <code>double</code>
     * @param b a <code>double</code>
     * @return -1, 0, or 1 depending on whether a is less than, equal to or greater than b
     */
    public static int compare(double a, double b) {
        if (a < b) return -1;
        if (a > b) return 1;

        if (Double.isNaN(a)) {
            if (Double.isNaN(b)) return 0;
            return -1;
        }

        if (Double.isNaN(b)) return 1;
        return 0;
    }

    /**
     * Compares two {@link VertexSequence}s for relative order.
     *
     * @param o1 a {@link VertexSequence}
     * @param o2 a {@link VertexSequence}
     * @return -1, 0, or 1 depending on whether o1 is less than, equal to, or greater than o2
     */
    public int compare(VertexSequence o1, VertexSequence o2) {
        VertexSequence s1 = (VertexSequence) o1;
        VertexSequence s2 = (VertexSequence) o2;

        int size1 = s1.size();
        int size2 = s2.size();

        int dim1 = s1.getDimension();
        int dim2 = s2.getDimension();

        int minDim = dim1;
        if (dim2 < minDim)
            minDim = dim2;
        boolean dimLimited = false;
        if (dimensionLimit <= minDim) {
            minDim = dimensionLimit;
            dimLimited = true;
        }

        // lower dimension is less than higher
        if (!dimLimited) {
            if (dim1 < dim2) return -1;
            if (dim1 > dim2) return 1;
        }

        // lexicographic ordering of point sequences
        int i = 0;
        while (i < size1 && i < size2) {
            int ptComp = compareCoordinate(s1, s2, i, minDim);
            if (ptComp != 0) return ptComp;
            i++;
        }
        if (i < size1) return 1;
        if (i < size2) return -1;

        return 0;
    }

    /**
     * Compares the same coordinate of two {@link VertexSequence}s
     * along the given number of dimensions.
     *
     * @param s1        a {@link VertexSequence}
     * @param s2        a {@link VertexSequence}
     * @param i         the index of the coordinate to test
     * @param dimension the number of dimensions to test
     * @return -1, 0, or 1 depending on whether s1[i] is less than, equal to, or greater than s2[i]
     */
    protected int compareCoordinate(VertexSequence s1, VertexSequence s2, int i, int dimension) {
        for (int d = 0; d < dimension; d++) {
            double ord1 = s1.getOrdinate(i, d);
            double ord2 = s2.getOrdinate(i, d);
            int comp = compare(ord1, ord2);
            if (comp != 0) return comp;
        }
        return 0;
    }
}
