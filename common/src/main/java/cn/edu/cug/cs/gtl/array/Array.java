package cn.edu.cug.cs.gtl.array;

import cn.edu.cug.cs.gtl.math.Dimensions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.function.DoublePredicate;

public interface Array extends Visitor {
    int MAXDIMS = 4;

    /**
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
    static Array create(int dim0, double[] data) {
        return new ArrayImpl(dim0, data);
    }

    static Array of(int dim0, double[] data) {
        return new ArrayImpl(dim0, data);
    }

    /**
     * Create a 2D ArrayImpl on the device using a host/device pointer
     * int h_buffer[] = {0, 1, 2, 3,    // host ArrayImpl with 16 elements
     * 4, 5, 6, 7,    // written in "row-major" format
     * 8, 9, 0, 1,
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
    static Array create(int dim0, int dim1, double[] data) {
        return new ArrayImpl(dim0, dim1, data);
    }

    static Array of(int dim0, int dim1, double[] data) {
        return new ArrayImpl(dim0, dim1, data);
    }

    /**
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
    static Array create(int dim0, int dim1, int dim2, double[] data) {
        return new ArrayImpl(dim0, dim1, dim2, data);
    }

    static Array of(int dim0, int dim1, int dim2, double[] data) {
        return new ArrayImpl(dim0, dim1, dim2, data);
    }

    /**
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
    static Array create(int dim0, int dim1, int dim2, int dim3, double[] data) {
        return new ArrayImpl(dim0, dim1, dim2, dim3, data);
    }

    static Array of(int dim0, int dim1, int dim2, int dim3, double[] data) {
        return new ArrayImpl(dim0, dim1, dim2, dim3, data);
    }

    /**
     * @param d4   must be a 4D
     * @param data
     */
    static Array create(Dimensions d4, double[] data) {
        return new ArrayImpl(d4, data);
    }

    static Array of(Dimensions d4, double[] data) {
        return new ArrayImpl(d4, data);
    }

    /**
     * @param dim4
     * @param data
     * @return
     */
    static Array create(int[] dim4, double[] data) {
        return new ArrayImpl(dim4, data);
    }

    static Array of(int[] dim4, double[] data) {
        return new ArrayImpl(dim4, data);
    }

    /**
     * create an array from bytes, these bytes should be from storeToByteArray() function
     * double [] d = {1,2,3,4,5,6,7,8,9,10};
     * Array a = Array.create(5,2,1,1,d);
     * Array.print(a);
     * try {
     * Array b = Array.of(a.storeToByteArray());
     * Assert.assertArrayEquals(d,b.raw(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param bytes
     * @return
     */
    static Array of(byte[] bytes) throws IOException {
        ArrayImpl a = new ArrayImpl();
        a.loadFromByteArray(bytes);
        return a;
    }

    /**
     * create an array from input stream
     * double [] d = {1,2,3,4,5,6,7,8,9,10};
     * Array a = Array.create(5,2,1,1,d);
     * Array.print(a);
     * try {
     * FileOutputStream f = new FileOutputStream(Config.getTestOutputDirectory()+ File.separator+"storeAndLoadForFile");
     * a.write(f);
     * f.close();
     * FileInputStream f2= new FileInputStream(Config.getTestOutputDirectory()+ File.separator+"storeAndLoadForFile");
     * Array b = Array.of(f2);
     * Assert.assertArrayEquals(d,b.raw(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    static Array of(InputStream inputStream) throws IOException {
        ArrayImpl a = new ArrayImpl();
        a.read(inputStream);
        return a;
    }

    /**
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
    Array reset(int dim0, double[] data);

    /**
     * Create a 2D ArrayImpl on the device using a host/device pointer
     * int h_buffer[] = {0, 1, 2, 3,    // host ArrayImpl with 16 elements
     * 4, 5, 6, 7,    // written in "row-major" format
     * 8, 9, 0, 1,
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
    Array reset(int dim0, int dim1, double[] data);

    /**
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
    Array reset(int dim0, int dim1, int dim2, double[] data);

    /**
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
    Array reset(int dim0, int dim1, int dim2, int dim3, double[] data);

    /**
     * @param d4   must be a 4D
     * @param data
     */
    Array reset(Dimensions d4, double[] data);


    /**
     * change multi-dimensional index to 1D index
     * for example , 4D Array
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
    int index(int[] indices);

    /**
     * set the value to array by idxs
     *
     * @param v
     * @param idxs
     */
    void set(double v, int... idxs);

    /**
     * get the raw data
     *
     * @return
     */
    double[] raw();

    /**
     * merge arrays into one new array
     *
     * @param arrays
     * @return
     */
    static int[] merge(int[]... arrays) {
        int newLen = 0;
        for (int i = 0; i < arrays.length; ++i) {
            newLen += arrays[i].length;
        }
        int[] newArray = new int[newLen];
        int destPos = 0;
        for (int i = 0; i < arrays.length; ++i) {
            System.arraycopy(arrays[i], 0, newArray, destPos, arrays[i].length);
            destPos += arrays[i].length;
        }
        return newArray;
    }

    /**
     * merge arrays into one new array
     *
     * @param arrays
     * @return
     */
    static double[] merge(double[]... arrays) {
        int lengthOfNewArray = 0;
        for (int i = 0; i < arrays.length; ++i) {
            lengthOfNewArray += arrays[i].length;
        }
        double[] newArray = new double[lengthOfNewArray];
        int destPos = 0;
        for (int i = 0; i < arrays.length; ++i) {
            System.arraycopy(arrays[i], 0, newArray, destPos, arrays[i].length);
            destPos += arrays[i].length;
        }
        return newArray;
    }

    /**
     * @param arrays
     * @param <T>
     * @return
     */
    static <T> T[] merge(T[]... arrays) {
        int lengthOfNewArray = 0;
        for (int i = 0; i < arrays.length; ++i) {
            lengthOfNewArray += arrays[i].length;
        }
        //使用(T[])Array.newInstance(...)可避免(T[])new Object[...]引发的ClassCastException
        T[] newArray = (T[]) java.lang.reflect.Array.newInstance(arrays[0].getClass().getComponentType(), lengthOfNewArray);
        int destPos = 0;
        for (int i = 0; i < arrays.length; ++i) {
            System.arraycopy(arrays[i], 0, newArray, destPos, arrays[i].length);
            destPos += arrays[i].length;
        }
        return newArray;
    }

    /**
     * @param a
     */
    static void print(Visitor a) {
        System.out.print("[ ");
        int[] dims = a.dims().get();
        int k = 0;
        for (int i : dims) {
            System.out.print(i);
            if (k == dims.length - 1)
                break;
            System.out.print(", ");
            ++k;
        }
        System.out.println("]");

        //for 4D
        for (int r2 = 0; r2 < dims[2]; ++r2) {
            for (int r1 = 0; r1 < dims[0]; ++r1) {
                for (int c2 = 0; c2 < dims[3]; ++c2) {
                    for (int c1 = 0; c1 < dims[1]; ++c1) {
                        System.out.print(a.get(r1, c1, r2, c2));
                        System.out.print(" ");
                    }
                    System.out.print(" ");
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }


    /**
     * change the dimensions of an array without changing the data,
     * the data are shallow copied.
     * that is to create a (shallow) copy of an array with different dimensions.
     *
     * @param a
     * @param dims4
     * @return
     */
    static Visitor modifyDimensions(Array a, int... dims4) {
        int s = 1;
        for (int i : dims4)
            s *= i;
        assert s == a.numberElements();
        Dimensions d4 = new Dimensions(dims4);
        Index[] idxs = new Index[Array.MAXDIMS];
        idxs[0] = new Index(0, d4.get(0));
        idxs[1] = new Index(0, d4.get(1));
        idxs[2] = new Index(0, d4.get(2));
        idxs[3] = new Index(0, d4.get(3));
        return new VisitorImpl(a, idxs, d4);
    }

    /**
     * flatten an array to one dimension
     * a [3 3 1 1]
     * 1.0000     4.0000     7.0000
     * 2.0000     5.0000     8.0000
     * 3.0000     6.0000     9.0000
     * flat(a) [9 1 1 1]
     * 1.0000
     * 2.0000
     * 3.0000
     * 4.0000
     * 5.0000
     * 6.0000
     * 7.0000
     * 8.0000
     * 9.0000
     *
     * @return create a (shallow) copy of an array with one dimension.
     */
    static Visitor flat(Array a) {
        return modifyDimensions(a, a.numberElements(), 1, 1, 1);
    }

    /**
     * The flip() function flips the contents of an array along a chosen dimension. In the example below, we show the 5x2 array flipped along the zeroth (i.e. within a column) and first (e.g. across rows) axes:
     * a [5 2 1 1]
     * 1.0000     6.0000
     * 2.0000     7.0000
     * 3.0000     8.0000
     * 4.0000     9.0000
     * 5.0000    10.0000
     * flip(a, 0) [5 2 1 1]
     * 5.0000    10.0000
     * 4.0000     9.0000
     * 3.0000     8.0000
     * 2.0000     7.0000
     * 1.0000     6.0000
     * flip(a, 1) [5 2 1 1]
     * 6.0000     1.0000
     * 7.0000     2.0000
     * 8.0000     3.0000
     * 9.0000     4.0000
     * 10.0000     5.0000
     *
     * @param a
     * @param dim dimension order, must be 0,1,2,3
     * @return create a (shallow) data copy of an array
     */
    static Visitor flip(Array a, int dim) {
        Dimensions d4 = (Dimensions) a.dims().clone();
        Index[] idxs = new Index[Array.MAXDIMS];
        switch (dim) {
            case 0: {
                idxs[0] = new Index(d4.get(0) - 1, -1, -1);
                idxs[1] = new Index(0, d4.get(1));
                idxs[2] = new Index(0, d4.get(2));
                idxs[3] = new Index(0, d4.get(3));
                break;
            }
            case 1: {
                idxs[0] = new Index(0, d4.get(0));
                idxs[1] = new Index(d4.get(1) - 1, -1, -1);
                idxs[2] = new Index(0, d4.get(2));
                idxs[3] = new Index(0, d4.get(3));
                break;
            }
            case 2: {
                idxs[0] = new Index(0, d4.get(0));
                idxs[1] = new Index(0, d4.get(1));
                idxs[2] = new Index(d4.get(2) - 1, -1, -1);
                idxs[3] = new Index(0, d4.get(3));
                break;
            }
            case 3: {
                idxs[0] = new Index(0, d4.get(0));
                idxs[1] = new Index(0, d4.get(1));
                idxs[2] = new Index(0, d4.get(2));
                idxs[3] = new Index(d4.get(3) - 1, -1, -1);
                break;
            }
        }
        return new VisitorImpl(a, idxs, d4);
    }


    /**
     * The join() function joins arrays along a specific dimension.
     * Requires that all dimensions except the join dimension must be the same for all arrays.
     *
     * @param dim
     * @param a
     * @param b
     * @return
     */
    static Array join(int dim, Visitor a, Visitor b) {
        int[] da = a.dims().get();
        int[] db = b.dims().get();
        int[] dc = new int[MAXDIMS];
        for (int i = 0; i < MAXDIMS; ++i) {
            if (i == dim)
                dc[i] = da[i] + db[i];
            else
                dc[i] = da[i];
        }
        Dimensions dimc = new Dimensions(dc, false);
        double[] dat = new double[dimc.numberElements()];
        int i = 0;
        Array aa = a.array();
        Array ab = b.array();
        Array ac = create(dimc, dat);
        //special conditions,just merge these two array
        if ((dim == 0 && dc[1] == 1 && dc[2] == 1 && dc[3] == 1)
                || (dim == 1 && dc[2] == 1 && dc[3] == 1)
                || (dim == 2 && dc[3] == 1)
                || (dim == 4)) {
            System.arraycopy(aa.raw(), 0, dat, 0, aa.raw().length);
            System.arraycopy(ab.raw(), 0, dat, aa.raw().length, ab.numberElements());
            return ac;
        }
        //general conditions
        for (int c2 = 0; c2 < da[3]; ++c2) {
            for (int r2 = 0; r2 < da[2]; ++r2) {
                for (int c1 = 0; c1 < da[1]; ++c1) {
                    for (int r1 = 0; r1 < da[0]; ++r1) {
                        ac.set(aa.get(r1, c1, r2, c2), r1, c1, r2, c2);
                    }
                }
            }
        }
        if (dim == 0) {
            for (int c2 = 0; c2 < dc[3]; ++c2) {
                for (int r2 = 0; r2 < dc[2]; ++r2) {
                    for (int c1 = 0; c1 < dc[1]; ++c1) {
                        for (int r1 = db[0]; r1 < dc[0]; ++r1) {
                            ac.set(ab.get(r1 - db[0], c1, r2, c2), r1, c1, r2, c2);
                        }
                    }
                }
            }
        }
        if (dim == 1) {
            for (int c2 = 0; c2 < dc[3]; ++c2) {
                for (int r2 = 0; r2 < dc[2]; ++r2) {
                    for (int c1 = db[1]; c1 < dc[1]; ++c1) {
                        for (int r1 = 0; r1 < dc[0]; ++r1) {
                            ac.set(ab.get(r1, c1 - db[1], r2, c2), r1, c1, r2, c2);
                        }
                    }
                }
            }
        }

        if (dim == 2) {
            for (int c2 = 0; c2 < dc[3]; ++c2) {
                for (int r2 = db[2]; r2 < dc[2]; ++r2) {
                    for (int c1 = 0; c1 < dc[1]; ++c1) {
                        for (int r1 = 0; r1 < dc[0]; ++r1) {
                            ac.set(ab.get(r1, c1, r2 - db[2], c2), r1, c1, r2, c2);
                        }
                    }
                }
            }
        }
        if (dim == 3) {
            for (int c2 = db[3]; c2 < dc[3]; ++c2) {
                for (int r2 = 0; r2 < dc[2]; ++r2) {
                    for (int c1 = 0; c1 < dc[1]; ++c1) {
                        for (int r1 = 0; r1 < dc[0]; ++r1) {
                            ac.set(ab.get(r1, c1, r2, c2 - db[3]), r1, c1, r2, c2);
                        }
                    }
                }
            }
        }

        return ac;
    }

    /**
     * changes the dimension order within the array
     * Reorder an array according to the specified dimensions.
     * Exchanges data of an array such that the requested change in dimension is satisfied.
     * The linear ordering of data within the array is preserved.
     * a [2 2 3 1]
     * 1.0000     3.0000
     * 2.0000     4.0000
     * 1.0000     3.0000
     * 2.0000     4.0000
     * 1.0000     3.0000
     * 2.0000     4.0000
     * reorder(a, 1, 0, 2) [2 2 3 1]  //equivalent to a transpose
     * 1.0000     2.0000
     * 3.0000     4.0000
     * 1.0000     2.0000
     * 3.0000     4.0000
     * 1.0000     2.0000
     * 3.0000     4.0000
     * reorder(a, 2, 0, 1) [3 2 2 1]
     * 1.0000     2.0000
     * 1.0000     2.0000
     * 1.0000     2.0000
     * 3.0000     4.0000
     * 3.0000     4.0000
     * 3.0000     4.0000
     *
     * @param a
     * @param x specifies which dimension should be first
     * @param y specifies which dimension should be second
     * @param z specifies which dimension should be third
     * @param w specifies which dimension should be fourth
     * @return
     */
    static Array reorder(Visitor a, int x, int y, int z, int w) {
        if (x == 0 && y == 1 && z == 2 && w == 3) {
            return copy(a);
        } else {
            int[] da4 = a.dims().get();
            int[] dr4 = Arrays.copyOf(da4, da4.length);
            dr4[0] = da4[x];
            dr4[1] = da4[y];
            dr4[2] = da4[z];
            dr4[3] = da4[w];
            double[] dr = new double[dr4[0] * dr4[1] * dr4[2] * dr4[3]];
            int[] idx = new int[MAXDIMS];
            Array r = create(dr4, dr);
            for (int c2 = 0; c2 < da4[3]; ++c2) {
                idx[3] = c2;
                for (int r2 = 0; r2 < da4[2]; ++r2) {
                    idx[2] = r2;
                    for (int c1 = 0; c1 < da4[1]; ++c1) {
                        idx[1] = c1;
                        for (int r1 = 0; r1 < da4[0]; ++r1) {
                            idx[0] = r1;
                            r.set(a.get(r1, c1, r2, c2), idx[x], idx[y], idx[z], idx[w]);
                        }
                    }
                }
            }
            return r;
        }
    }

    /**
     * performs a matrix transpose
     *
     * @param v
     * @return
     */
    static Array transpose(Visitor v) {
        return reorder(v, 1, 0, 2, 3);
    }

    /**
     * The tile() function repeats an array along the specified dimension.
     * For example below we show how to tile an array along the zeroth and first dimensions of an array:
     * a [3 1 1 1]
     * 1.0000
     * 2.0000
     * 3.0000
     * // Repeat array a twice in the zeroth dimension
     * tile(a, 2) [6 1 1 1]
     * 1.0000
     * 2.0000
     * 3.0000
     * 1.0000
     * 2.0000
     * 3.0000
     * // Repeat array a twice along both the zeroth and first dimensions
     * tile(a, 2, 2) [6 2 1 1]
     * 1.0000     1.0000
     * 2.0000     2.0000
     * 3.0000     3.0000
     * 1.0000     1.0000
     * 2.0000     2.0000
     * 3.0000     3.0000
     * // Repeat array a twice along the first and three times along the second
     * // dimension.
     * af::dim4 tile_dims(1, 2, 3);
     * tile(a, tile_dims) [3 2 3 1]
     * 1.0000     1.0000
     * 2.0000     2.0000
     * 3.0000     3.0000
     * 1.0000     1.0000
     * 2.0000     2.0000
     * 3.0000     3.0000
     * 1.0000     1.0000
     * 2.0000     2.0000
     * 3.0000     3.0000
     *
     * @param v
     * @param x
     * @param y
     * @param z
     * @param w
     * @return
     */
    static Array tile(Visitor v, int x, int y, int z, int w) {
        Array r = null;
        if (x <= 1 && y <= 1 && z <= 1 && w <= 1) {
            r = copy(v);
            return r;
        }
        if (x > 1) {
            r = join(0, v, v);
            for (int i = 2; i < x; ++i) {
                r = join(0, r, v);
            }
        }
        if (y > 1) {
            if (r == null) r = v.array();
            Array ry = join(1, r, r);
            for (int i = 2; i < y; ++i) {
                ry = join(1, ry, r);
            }
            r = ry;
        }
        if (z > 1) {
            if (r == null) r = v.array();
            Array rz = join(2, r, r);
            for (int i = 2; i < z; ++i) {
                rz = join(2, rz, r);
            }
            r = rz;
        }
        if (w > 1) {
            if (r == null) r = v.array();
            Array rw = join(3, r, r);
            for (int i = 2; i < w; ++i) {
                rw = join(3, rw, r);
            }
            r = rw;
        }
        return r;
    }

    /**
     * @param v
     * @return
     */
    static double sum(Visitor v) {
        Array r = v.array();
        double[] dat = r.raw();
        return Arrays.stream(dat).sum();
    }

    /**
     * @param v
     * @return
     */
    static double max(Visitor v) {
        Array r = v.array();
        double[] dat = r.raw();
        return Arrays.stream(dat).max().getAsDouble();
    }

    /**
     * @param v
     * @return
     */
    static double min(Visitor v) {
        Array r = v.array();
        double[] dat = r.raw();
        return Arrays.stream(dat).min().getAsDouble();
    }

    /**
     * 统计符合条件的元素个数,下面统计大于5的数的个数
     * double [] d = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
     * Array a = Array.create(4,4,1,1,d);
     * System.out.println(Array.count(a,(t)->t>5));
     *
     * @param v
     * @param dp
     * @return
     */
    static long count(Visitor v, DoublePredicate dp) {
        Array r = v.array();
        double[] dat = r.raw();
        return Arrays.stream(dat).filter(dp).count();
    }

    /**
     * copy the data and generate a new array
     *
     * @param v
     * @return
     */
    static Array copy(Visitor v) {
        if (v instanceof VisitorImpl)
            return v.array();
        else {
            Array a = v.array();
            return (Array) a.clone();
        }
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array plus(Visitor v, double d) {
        Array a = copy(v);
        double[] r = a.raw();
        for (int i = 0; i < r.length; ++i)
            r[i] += d;
        return a;
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array plus(Visitor v, Visitor d) {
        Array a = copy(v);
        double[] r = a.raw();
        double[] r2 = d.array().raw();
        for (int i = 0; i < r.length; ++i)
            r[i] += r2[i];
        return a;
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array minus(Visitor v, double d) {
        Array a = copy(v);
        double[] r = a.raw();
        for (int i = 0; i < r.length; ++i)
            r[i] -= d;
        return a;
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array minus(Visitor v, Visitor d) {
        Array a = copy(v);
        double[] r = a.raw();
        double[] r2 = d.array().raw();
        for (int i = 0; i < r.length; ++i)
            r[i] -= r2[i];
        return a;
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array multiply(Visitor v, double d) {
        Array a = copy(v);
        double[] r = a.raw();
        for (int i = 0; i < r.length; ++i)
            r[i] *= d;
        return a;
    }

    /**
     * @param v
     * @param d
     * @return
     */
    static Array divide(Visitor v, double d) {
        Array a = copy(v);
        double[] r = a.raw();
        for (int i = 0; i < r.length; ++i)
            r[i] /= d;
        return a;
    }

}
