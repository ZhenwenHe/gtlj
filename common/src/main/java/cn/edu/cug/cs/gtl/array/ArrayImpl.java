package cn.edu.cug.cs.gtl.array;

import cn.edu.cug.cs.gtl.math.Dimensions;
import cn.edu.cug.cs.gtl.math.Dimensions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

class ArrayImpl implements Array {

    private static final long serialVersionUID = 2245481510326757457L;

    Dimensions dimensions = null;//It is always 4D
    double[] data = null;


    /**
     * internal function only for this package,please use Array.create() public function
     */
    ArrayImpl() {
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param dim0
     */
    ArrayImpl(int dim0) {
        this(dim0, 1, 1, 1);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param dim0
     * @param dim1
     */
    ArrayImpl(int dim0, int dim1) {
        this(dim0, dim1, 1, 1);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param dim0
     * @param dim1
     * @param dim2
     */
    ArrayImpl(int dim0, int dim1, int dim2) {
        this(dim0, dim1, dim2, 1);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param dim0
     * @param dim1
     * @param dim2
     * @param dim3
     */
    ArrayImpl(int dim0, int dim1, int dim2, int dim3) {
        reset(dim0, dim1, dim2, dim3, new double[dim0 * dim1 * dim2 * dim3]);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param d4
     */
    ArrayImpl(Dimensions d4) {
        assert d4.numberDimensions() == 4;
        reset(d4, new double[d4.numberElements()]);
    }

    /**
     * just return the raw data, internal function only for this package
     *
     * @return
     */
    public double[] raw() {
        return data;
    }

    /**
     * internal function only for this package,please use Array.create() public function
     * Create a column vector on the device using a host/device pointer
     * // allocate data on the host
     * int h_buffer[] = {23, 34, 18, 99, 34};
     * <p>
     * ArrayImpl A(4, h_buffer);   // copy host data to device
     * //
     * // A = 23
     * //   = 34
     * //   = 18
     * //   = 99
     *
     * @param dim0 number of elements in the column vector
     * @param data pointer (points to a buffer on the host/device)
     */
    ArrayImpl(int dim0, double[] data) {
        this(dim0, 1, 1, 1, data);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     * Create a 2D ArrayImpl on the device using a host/device pointer
     * int h_buffer[] = {0, 1, 2, 3,    // host ArrayImpl with 16 elements
     * 4, 5, 6, 7,    // written in "row-major" format
     * 8, 9, 0, 1
     * 2, 3, 4, 5};
     * dim4 dims(4, 4);
     * ArrayImpl A(dims, h_buffer);
     * A  =  0  4  8  2
     * 1  5  9  3
     * 2  6  0  4
     * 3  7  1  5     //Note the "column-major" ordering used in ArrayImplFire
     *
     * @param dim0 number of rows
     * @param dim1 number of columns
     * @param data pointer (points to a buffer on the host/device)
     */
    ArrayImpl(int dim0, int dim1, double[] data) {
        this(dim0, dim1, 1, 1, data);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     * Create a 3D ArrayImpl on the device using a host/device pointer
     * int h_buffer[] = {0, 1, 2, 3, 4, 5, 6, 7, 8
     * 9, 0, 1, 2, 3, 4, 5, 6, 7};   // host ArrayImpl
     * <p>
     * ArrayImpl A(3, 3, 2,  h_buffer);   // copy host data to 3D device ArrayImpl
     * // A= 0  3  6
     * 1  4  7
     * 2  5  8
     * <p>
     * 9  2  5
     * 0  3  6
     * 1  4  7
     *
     * @param dim0 first dimension
     * @param dim1 second dimension
     * @param dim2 third dimension
     * @param data pointer (points to a buffer on the host/device)
     */
    ArrayImpl(int dim0, int dim1, int dim2, double[] data) {
        this(dim0, dim1, dim2, 1, data);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     * Create a 4D ArrayImpl on the device using a host/device pointer
     * int h_buffer[] = {0, 1, 2, 3,
     * 4, 5, 6, 7,
     * 8, 9, 0, 1,
     * 2, 3, 4, 5};   // host ArrayImpl with 16 elements
     * <p>
     * ArrayImpl A(2, 2, 2, 2, h_buffer);   // copy host data to 4D device ArrayImpl
     * //A= 0   2     8  0
     * 1   3     9  1
     * <p>
     * 4   6     2  4
     * 5   7     3  5
     * dim0  dim1 dim2 dim3
     * idx0  idx1  idx2  idx3
     * idx3*dim0*dim1*dim2+ idx2*dim0*dim1+ idx1*dim0 + idx0;
     *
     * @param dim0 first dimension
     * @param dim1 second dimension
     * @param dim2 third dimension
     * @param dim3 fourth dimension
     * @param data pointer (points to a buffer on the host/device)
     */
    ArrayImpl(int dim0, int dim1, int dim2, int dim3, double[] data) {
        reset(dim0, dim1, dim2, dim3, data);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param d4   must be a 4D
     * @param data
     */
    ArrayImpl(Dimensions d4, double[] data) {
        reset(d4, data);
    }

    /**
     * internal function only for this package,please use Array.create() public function
     *
     * @param d4   must be a 4D
     * @param data
     */
    ArrayImpl(int[] d4, double[] data) {
        Dimensions d = new Dimensions(d4, false);
        reset(d, data);
    }

    /**
     * @return
     */
    @Override
    public Array array() {
        return this;
    }

    /**
     * for 4D ArrayImpl
     * int h_buffer[] = {0, 1, 2, 3,
     * 4, 5, 6, 7,
     * 8, 9, 0, 1,
     * 2, 3, 4, 5};   // host ArrayImpl with 16 elements
     * <p>
     * ArrayImpl A(2, 2, 2, 2, h_buffer);   // copy host data to 4D device ArrayImpl
     * //A= 0   2     8  0
     * 1   3     9  1
     * <p>
     * 4   6     2  4
     * 5   7     3  5
     * dim0  dim1 dim2 dim3
     * idx0  idx1  idx2  idx3
     * idx=idx3*dim0*dim1*dim2+ idx2*dim0*dim1+ idx1*dim0 + idx0;
     *
     * @param indices
     * @return
     */
    public int index(int[] indices) {
        int[] dims = dimensions.get();
        int[] idxs = indices;
        int len = dims.length;
        if (idxs.length < len) {
            //extend idxs
            idxs = new int[len];
            int i = 0;
            for (i = 0; i < indices.length; ++i) {
                idxs[i] = indices[i];
            }
            while (i < len) {
                idxs[i] = 0;
                ++i;
            }
        }
        int idx = idxs[0];
        int k = 0, j = 0;
        for (k = 1; k < len; ++k) {
            int m = dims[0];
            for (j = 1; j < k; ++j)
                m *= dims[j];
            idx += m * idxs[k];
        }
        return idx;
    }

    @Override
    public void set(double v, int... idxs) {
        int i = index(idxs);
        data[i] = v;
    }

    /**
     * empty test
     *
     * @return
     */
    public boolean isEmpty() {
        return this.data == null || this.dimensions == null;
    }

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
    public double get(int... idxs) {
        int idx = index(idxs);
        return data[idx];
    }

    /**
     * Get the first element of the ArrayImpl as a scalar
     *
     * @return the first element of the ArrayImpl
     */
    public double scalar() {
        return data[0];
    }

    /**
     * return as a 1D ArrayImpl
     *
     * @return
     */
    public double[] host() {
        return Arrays.copyOf(data, data.length);
    }


    /**
     * @param i
     * @return a reference to a row defined by i
     */
    public Visitor row(int i) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(i);
        idxs[1] = new Index(0, dims(1));
        idxs[2] = new Index(0);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(1, dims(1), 1, 1);
        return new VisitorImpl(this, idxs, d4);
    }

    /**
     * @param first
     * @param last
     * @return
     */
    public Visitor rows(int first, int last) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(first, last);
        idxs[1] = new Index(0, dims(1));
        idxs[2] = new Index(0);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(last - first, dims(1), 1, 1);
        return new VisitorImpl(this, idxs, d4);
    }

    /**
     * @param i index is the index of the col to be returned
     * @return a reference to a col
     */
    public Visitor col(int i) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(0, dims(0));
        idxs[1] = new Index(i);
        idxs[2] = new Index(0);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(dims(0), 1, 1, 1);
        return new VisitorImpl(this, idxs, d4);
    }

    /**
     * @param first
     * @param last
     * @return
     */
    public Visitor cols(int first, int last) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(0, dims(0));
        idxs[1] = new Index(first, last);
        idxs[2] = new Index(0);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(dims(0), last - first, 1, 1);
        return new VisitorImpl(this, idxs, d4);
    }

    /**
     * @param index
     * @return
     */
    public Visitor slice(int index) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(0, dims(0));
        idxs[1] = new Index(0, dims(1));
        idxs[2] = new Index(index);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(dims(0), dims(1), 1, 1);
        return new VisitorImpl(this, idxs, d4);
    }

    /**
     * @param first
     * @param last
     * @return
     */
    public Visitor slices(int first, int last) {
        Index[] idxs = new Index[MAXDIMS];
        idxs[0] = new Index(0, dims(0));
        idxs[1] = new Index(0, dims(1));
        idxs[2] = new Index(first, last);
        idxs[3] = new Index(0);
        Dimensions d4 = new Dimensions(dims(0), dims(1), last - first, 1);
        return new VisitorImpl(this, idxs, d4);
    }


    /**
     * @return
     */
    public Dimensions dims() {
        return dimensions;
    }

    /**
     * @return
     */
    @Override
    public Object clone() {
        Dimensions s = (Dimensions) this.dimensions.clone();
        double[] d = Arrays.copyOf(this.data, this.data.length);
        return new ArrayImpl(s, d);
    }

    /**
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        int f = in.readInt();
        if (f > 0) {
            dimensions = new Dimensions(1, 1, 1, 1);
            dimensions.load(in);
        }
        f = in.readInt();
        if (f > 0) {
            data = new double[f];
            for (int i = 0; i < f; ++i)
                data[i] = in.readDouble();
        }
        return true;
    }

    /**
     * @param out，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        int f = this.dimensions == null ? 0 : 1;
        out.writeInt(f);
        if (f > 0) {
            this.dimensions.store(out);
        }
        f = this.data == null ? 0 : this.data.length;
        out.writeInt(f);
        if (f > 0) {
            for (double d : this.data)
                out.writeDouble(d);
        }
        return true;
    }

    @Override
    public Array reset(int dim0, double[] data) {
        return reset(dim0, 1, 1, 1, data);
    }

    @Override
    public Array reset(int dim0, int dim1, double[] data) {
        return reset(dim0, dim1, 1, 1, data);
    }

    @Override
    public Array reset(int dim0, int dim1, int dim2, double[] data) {
        return reset(dim0, dim1, dim2, 1, data);
    }

    @Override
    public Array reset(int dim0, int dim1, int dim2, int dim3, double[] data) {
        dimensions = new Dimensions(dim0, dim1, dim2, dim3);
        assert data.length == dimensions.numberElements();
        this.data = data;
        return this;
    }

    @Override
    public Array reset(Dimensions d4, double[] data) {
        assert d4.numberDimensions() == 4;
        dimensions = d4;
        assert data.length == dimensions.numberElements();
        this.data = data;
        return this;
    }
}