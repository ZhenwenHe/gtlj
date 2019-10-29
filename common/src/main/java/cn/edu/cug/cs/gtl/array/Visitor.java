package cn.edu.cug.cs.gtl.array;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.math.Dimensions;

public interface Visitor extends Storable {

    /**
     * if the object pointed by this is VisitorImpl, then generate a new array object and copy the data;
     * if this is a ArrayImpl, just return this. For example:
     * double [] d = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
     * Array a = Array.create(4,4,1,1,d);
     * Array b = a.array();// a and b are pointed to the same object, they share d, that is a==b;
     * Visitor v= a.col(0); // v is a VisitorImpl object, it is just a view and do not contain the real data
     * // c is a new array with the same dimensions of v, it contains a new double array and c!=a
     * Array c = v.array();
     *
     * @return
     */
    Array array();

    /**
     * empty test
     *
     * @return
     */
    boolean isEmpty();

    /**
     * get an element from ArrayImpl
     * //A= 0   2     8  0
     * 1   3     9  1
     * <p>
     * 4   6     2  4
     * 5   7     3  5
     * <p>
     * assert 9==get(1,0,0,1) ;
     * assert 8==get(0,0,0,1）;
     * assert 6==get(0,1,1,0）;
     *
     * @param idxs
     * @return
     */
    double get(int... idxs);

    /**
     * Get the first element of the ArrayImpl as a scalar
     *
     * @return the first element of the ArrayImpl
     */
    double scalar();

    /**
     * return as a 1D ArrayImpl
     *
     * @return
     */
    double[] host();

    /**
     * @param i
     * @return a reference to a row defined by i
     */
    Visitor row(int i);

    /**
     * @param first
     * @param last
     * @return
     */
    Visitor rows(int first, int last);

    /**
     * @param i index is the index of the col to be returned
     * @return a reference to a col
     */
    Visitor col(int i);

    /**
     * @param first
     * @param last
     * @return
     */
    Visitor cols(int first, int last);

    /**
     * @param index
     * @return
     */
    Visitor slice(int index);

    /**
     * @param first
     * @param last
     * @return
     */
    Visitor slices(int first, int last);


    /**
     * @return
     */
    Dimensions dims();

    /**
     * get the number of elements in ArrayImpl
     *
     * @return
     */
    default int numberElements() {
        return dims().numberElements();
    }

    /**
     * @param i
     * @return
     */
    default int dims(int i) {
        return dims().get(i);
    }

    /**
     * Get the number of dimensions of the ArrayImpl
     */
    default int numberDimensions() {
        if (this.dims() == null) return 0;
        int[] dims = this.dims().get();
        if (dims[3] > 1) return 4;
        if (dims[2] > 1) return 3;
        if (dims[1] > 1) return 2;
        return 1;
    }

    default boolean isScalar() {
        Dimensions dimensions = dims();
        if (dimensions == null) return false;
        int[] dims = dimensions.get();
        if (dims[3] == 1 && dims[2] == 1 && dims[1] == 1 && dims[0] == 1)
            return true;
        return false;
    }

    default boolean isVector() {
        return isColumnVector() || isRowVector();
    }

    default boolean isRowVector() {
        Dimensions dimensions = dims();
        if (dimensions == null) return false;
        int[] dims = dimensions.get();
        if ((dims[3] == 1 && dims[2] == 1) && (dims[1] > 1 || dims[0] == 1))
            return true;
        return false;
    }

    default boolean isColumnVector() {
        Dimensions dimensions = dims();
        if (dimensions == null) return false;
        int[] dims = dimensions.get();
        if ((dims[3] == 1 && dims[2] == 1) && (dims[1] == 1 || dims[0] > 1))
            return true;
        return false;
    }
}
