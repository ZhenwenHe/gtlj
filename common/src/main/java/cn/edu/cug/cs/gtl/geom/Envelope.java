package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;


/**
 * Created by ZhenwenHe on 2016/12/8.
 */
public class Envelope implements Storable, Dimensional {

    private static final long serialVersionUID = 1L;

    double[] low;
    double[] high;

    public Envelope() {
        this(2);
    }

    public Envelope(int dim) {
        this.low = new double[dim];
        this.high = new double[dim];
        for (int cIndex = 0; cIndex < dim; ++cIndex) {
            this.low[cIndex] = Double.MAX_VALUE;
            this.high[cIndex] = -Double.MAX_VALUE;
        }
    }

    public Envelope(double[] low, double[] high) {
        reset(low, high, Math.min(low.length, high.length));
    }

    public Envelope(double x1, double x2, double y1, double y2) {
        this.low = new double[2];
        this.high = new double[2];

        this.low[0] = x1;
        this.low[1] = y1;
        this.high[0] = x2;
        this.high[1] = y2;
    }

    public Envelope(double x1, double x2, double y1, double y2, double z1, double z2) {
        this.low = new double[3];
        this.high = new double[3];

        this.low[0] = x1;
        this.low[1] = y1;
        this.low[2] = z1;
        this.high[0] = x2;
        this.high[1] = y2;
        this.high[2] = z2;
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        int i = 0;
        int dims = dis.readInt();
        this.makeDimension(dims);
        for (i = 0; i < dims; i++) {
            this.low[i] = dis.readDouble();
        }
        for (i = 0; i < dims; i++) {
            this.high[i] = dis.readDouble();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        int dims = this.getDimension();
        assert dims <= 4;
        dos.writeInt(dims);
        for (double d : this.low)
            dos.writeDouble(d);
        for (double d : this.high)
            dos.writeDouble(d);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return getDimension() * 8 * 2 + 4;
    }


    public Envelope flap() {
        return new Envelope(this.low[0], this.high[0], this.low[1], this.high[1]);
    }


    public void makeInfinite(int dimension) {
        makeDimension(dimension);
        for (int cIndex = 0; cIndex < dimension; ++cIndex) {
            this.low[cIndex] = Double.MAX_VALUE;
            this.high[cIndex] = -Double.MAX_VALUE;
        }
    }


    public void makeInfinite() {
        int dimension = this.getDimension();
        for (int cIndex = 0; cIndex < dimension; ++cIndex) {
            this.low[cIndex] = Double.MAX_VALUE;
            this.high[cIndex] = -Double.MAX_VALUE;
        }
    }


    public void makeDimension(int dimension) {
        if (getDimension() != dimension) {
            double[] newdataLow = new double[dimension];
            double[] newdataHigh = new double[dimension];

            int minDims = Math.min(dimension, this.low.length);
            for (int i = 0; i < minDims; i++) {
                newdataLow[i] = this.low[i];
            }
            this.low = newdataLow;

            minDims = Math.min(dimension, this.high.length);
            for (int i = 0; i < minDims; i++) {
                newdataHigh[i] = this.high[i];
            }
            this.high = newdataHigh;
        }
    }


    public int getDimension() {
        if (this.low == null || this.high == null)
            return 0;
        else
            return Math.min(this.low.length, this.high.length);
    }


    public double[] getLowCoordinates() {
        return this.low;
    }


    public double[] getHighCoordinates() {
        return this.high;
    }


    public Vector getLowVector() {
        return new VectorImpl(this.low);
    }


    public Vector getHighVector() {
        return new VectorImpl(this.high);
    }


    public double getLowOrdinate(int i) {
        return this.low[i];
    }


    public double getHighOrdinate(int i) {
        return this.high[i];
    }


    public void setLowOrdinate(int i, double d) {
        this.low[i] = d;
    }


    public void setHighOrdinate(int i, double d) {
        this.high[i] = d;
    }

    public double getMinX() {
        return this.low[0];
    }

    public double getMinY() {
        return this.low[1];
    }

    public double getMinZ() {
        return this.low[2];
    }

    public double getMaxX() {
        return this.high[0];
    }

    public double getMaxY() {
        return this.high[1];
    }

    public double getMaxZ() {
        return this.high[2];
    }

    public Vector getCenter() {
        int dim = getDimension();
        Vector v = Vector.create(dim);
        for (int i = 0; i < dim; ++i) {
            v.setOrdinate(i, (low[i] + high[i]) / 2);
        }
        return v;
    }

    public void reset(double[] low, double[] high, int dimension) {
        dimension = Math.min(Math.min(low.length, high.length), dimension);
        this.low = new double[dimension];
        this.high = new double[dimension];
        System.arraycopy(low, 0, this.low, 0, dimension);
        System.arraycopy(high, 0, this.high, 0, dimension);
    }

    @Override
    public Envelope clone() {
        return new Envelope(this.low, this.high);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Envelope)) return false;

        Envelope envelope = (Envelope) o;

        if (!Arrays.equals(this.low, envelope.low)) return false;
        return Arrays.equals(this.high, envelope.high);
    }

    @Override
    public String toString() {
        return "Envelope{" +
                "low=" + Arrays.toString(this.low) +
                ", high=" + Arrays.toString(this.high) +
                '}';
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(low);
        result = 31 * result + Arrays.hashCode(high);
        return result;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Envelope) {
            Envelope e = (Envelope) i;
            this.reset(e.getLowCoordinates(), e.getHighCoordinates(), e.getDimension());
        } else {
            assert false;
        }
    }


    public void reset(double[] low, double[] high) {
        int dimension = Math.min(low.length, high.length);
        if (getDimension() != dimension) {
            this.low = new double[dimension];
            this.high = new double[dimension];
        }
        System.arraycopy(low, 0, this.low, 0, dimension);
        System.arraycopy(high, 0, this.high, 0, dimension);
    }

    public boolean intersects(Envelope e) {
        if (e == null) return false;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return false;

        for (int i = 0; i < dims; ++i) {
            if (this.low[i] > e.getHighOrdinate(i)
                    || this.high[i] < e.getLowOrdinate(i))
                return false;
        }
        return true;
    }

    public boolean contains(Envelope e) {
        if (e == null) return false;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return false;

        for (int i = 0; i < dims; ++i) {
            if (this.low[i] > e.getLowOrdinate(i)
                    || this.high[i] < e.getHighOrdinate(i))
                return false;
        }
        return true;
    }

    /**
     * @param e
     * @return
     */
    public boolean touches(Envelope e) {
        if (e == null) return false;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return false;

        for (int i = 0; i < dims; ++i) {
            if (
                    (
                            this.low[i] >= e.getLowOrdinate(i) + MathSuits.EPSILON
                                    &&
                                    this.low[i] <= e.getLowOrdinate(i) - MathSuits.EPSILON
                    )
                            ||
                            (
                                    this.high[i] >= e.getHighOrdinate(i) + MathSuits.EPSILON
                                            &&
                                            this.high[i] <= e.getHighOrdinate(i) - MathSuits.EPSILON
                            )
            )
                return false;
        }
        return true;
    }

    public boolean contains(Vector p) {
        if (p == null) return false;
        int dims = this.getDimension();
        if (dims != p.getDimension()) return false;

        for (int i = 0; i < dims; ++i) {
            if (this.low[i] > p.getOrdinate(i) || this.high[i] < p.getOrdinate(i))
                return false;
        }
        return true;
    }

    public boolean touches(Vector p) {
        if (p == null) return false;
        int dims = this.getDimension();
        if (dims != p.getDimension()) return false;

        for (int i = 0; i < dims; ++i) {
            if (
                    (this.low[i] >= p.getOrdinate(i) - MathSuits.EPSILON &&
                            this.low[i] <= p.getOrdinate(i) + MathSuits.EPSILON) ||
                            (this.high[i] >= p.getOrdinate(i) - MathSuits.EPSILON &&
                                    this.high[i] <= p.getOrdinate(i) + MathSuits.EPSILON))
                return true;
        }
        return false;
    }


    public Envelope getIntersectingEnvelope(Envelope e) {
        if (e == null) return null;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return null;

        Envelope ret = new Envelope(dims);
        ret.makeInfinite(dims);

        // check for intersection.
        for (int cDim = 0; cDim < dims; ++cDim) {
            if (this.low[cDim] > e.getHighOrdinate(cDim) || this.high[cDim] < e.getLowOrdinate(cDim))
                return ret;
        }

        for (int cDim = 0; cDim < dims; ++cDim) {
            ret.low[cDim] = Math.max(this.low[cDim], e.getLowOrdinate(cDim));
            ret.high[cDim] = Math.min(this.high[cDim], e.getHighOrdinate(cDim));
        }

        return ret;
    }


    public double getIntersectingArea(Envelope e) {
        if (e == null) return 0.0;
        int dims = this.getDimension();
        if (dims != e.getDimension()) return 0.0;

        double ret = 1.0;
        double f1, f2;

        for (int cDim = 0; cDim < dims; ++cDim) {
            if (this.low[cDim] > e.getHighOrdinate(cDim) || this.high[cDim] < e.getLowOrdinate(cDim)) return 0.0;

            f1 = Math.max(this.low[cDim], e.getLowOrdinate(cDim));
            f2 = Math.min(this.high[cDim], e.getHighOrdinate(cDim));
            ret *= f2 - f1;
        }

        return ret;
    }

    /*
     * Returns the margin of a region. It is calculated as the sum of  2^(d-1) * width, in each dimension.
     * It is actually the sum of all edges, no matter what the dimensionality is.
     */

    public double getMargin() {
        int dims = this.getDimension();
        double mul = Math.pow(2.0, dims - 1.0);
        double margin = 0.0;

        for (int i = 0; i < dims; ++i) {
            margin += (this.high[i] - this.low[i]) * mul;
        }

        return margin;
    }


    public void combine(Envelope e) {
        int dims = this.getDimension();
        if (e.getDimension() != dims)
            return;

        for (int cDim = 0; cDim < dims; ++cDim) {
            this.low[cDim] = Math.min(this.low[cDim], e.getLowOrdinate(cDim));
            this.high[cDim] = Math.max(this.high[cDim], e.getHighOrdinate(cDim));
        }
    }


    public void combine(Vector v) {
        int dims = this.getDimension();
        if (v.getDimension() != dims)
            return;

        for (int cDim = 0; cDim < dims; ++cDim) {
            this.low[cDim] = Math.min(this.low[cDim], v.getOrdinate(cDim));
            this.high[cDim] = Math.max(this.high[cDim], v.getOrdinate(cDim));
        }
    }


    public Envelope getCombinedEnvelope(Envelope e) {
        Envelope r = (Envelope) this.clone();
        r.combine(e);
        return r;
    }

    /**
     * 以2D为例，dimensionOrder为0或1，x=ordinate或 y=ordinate的垂直或水平的直线将矩形划分成2份
     * 3D,4D以此类推
     *
     * @param dimensionOrder
     * @param ordinate
     * @return
     */
    public Envelope[] split(double ordinate, int dimensionOrder) {
        int dim = getDimension();
        dimensionOrder = dimensionOrder % dim;
        Envelope[] envelopes = new Envelope[2];
        double[] lowCoordinate = new double[dim];
        double[] highCoordinate = new double[dim];
        for (int i = 0; i < dim; ++i) {
            lowCoordinate[i] = this.low[i];
            highCoordinate[i] = this.high[i];
        }
        highCoordinate[dimensionOrder] = ordinate;
        envelopes[0] = new Envelope(this.low, highCoordinate);
        lowCoordinate[dimensionOrder] = ordinate;
        envelopes[1] = new Envelope(lowCoordinate, this.high);
        return envelopes;
    }

    public Envelope[] split(Vector coordinate, int dimensionOrder) {
        return split(coordinate.getOrdinate(dimensionOrder % getDimension()), dimensionOrder);
    }


    public static Envelope create(int dim) {
        return new Envelope(dim);
    }

    public static Envelope create(double[] low, double[] high) {
        return new Envelope(low, high);
    }

    public static Envelope create(Vector v, double r) {
        Envelope e = new Envelope(v.getDimension());
        if (v.getDimension() != e.getDimension())
            e.makeInfinite(v.getDimension());
        for (int i = 0; i < e.getDimension(); ++i) {
            e.setLowOrdinate(i, v.getOrdinate(i) - MathSuits.EPSILON);
            e.setHighOrdinate(i, v.getOrdinate(i) + MathSuits.EPSILON);
        }
        return e;
    }

    public static Envelope create(Vertex v, double r) {
        Envelope e = new Envelope(2);
        if (e.getDimension() != 2)
            e.makeInfinite(2);
        for (int i = 0; i < e.getDimension(); ++i) {
            e.setLowOrdinate(i, v.getOrdinate(i) - MathSuits.EPSILON);
            e.setHighOrdinate(i, v.getOrdinate(i) + MathSuits.EPSILON);
        }
        return e;
    }


    public static Envelope create(double x1, double y1, double x2, double y2) {
        return new Envelope(x1, x2, y1, y2);
    }

    public static Envelope create(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Envelope(x1, x2, y1, y2, z1, z2);
    }

    /**
     * 生成随机矩形r，其长宽比率为ratio,例如，如果X方向长度为d,则生成的矩形X方向长度为d*ratio;
     * 并且r完全在envelope内
     *
     * @param envelope
     * @param ratio
     * @return
     */
    public static Envelope randomEnvelope(final Envelope envelope, double ratio) {
        Envelope e = new Envelope(envelope.getDimension());
        double[] low = envelope.getLowCoordinates();
        double[] high = envelope.getHighCoordinates();
        double[] d = new double[low.length];
        for (int i = 0; i < d.length; ++i) {
            d[i] = (high[i] - low[i]);
        }
        //计算随机矩形的中心点
        double[] center = new double[d.length];
        for (int i = 0; i < d.length; ++i) {
            center[i] = d[i] * (1 - ratio) * Math.random() + low[i] + d[i] * ratio / 2;
            e.low[i] = center[i] - d[i] * ratio / 2;
            e.high[i] = center[i] + d[i] * ratio / 2;
        }
        return e;
    }

    /**
     * @param g
     * @return
     */
    public static String toTSVString(final Envelope g) {
        StringBuilder sb = new StringBuilder();
        int dim = g.getDimension();
        sb.append(dim);

        for (int i = 0; i < dim; ++i) {
            sb.append(FileDataSplitter.TSV.getDelimiter());
            sb.append(g.getLowCoordinates()[i]);
        }

        for (int i = 0; i < dim; ++i) {
            sb.append(FileDataSplitter.TSV.getDelimiter());
            sb.append(g.getHighCoordinates()[i]);
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * @param s
     * @return
     */
    public static Envelope fromTSVString(final String s) {
        String[] columns = s.split(FileDataSplitter.TSV.getDelimiter());
        int dim = Integer.parseInt(columns[0]);
        int i = 1;
        Envelope e = new Envelope(dim);
        double[] low = e.getLowCoordinates();
        double[] high = e.getHighCoordinates();
        for (; i <= dim; ++i) {
            low[i - 1] = Double.parseDouble(columns[i]);
        }
        for (; i <= dim * 2; ++i) {
            high[i - dim - 1] = Double.parseDouble(columns[i]);
        }
        return e;
    }

    /**
     * @param str the string generated by toString() function
     * @return
     */
    public static Envelope fromString(String str) {
        StringTokenizer st = new StringTokenizer(str, "[,]", false);
        ArrayList<Double> al = new ArrayList<>(4);
        while (st.hasMoreTokens()) {
            String s = st.nextToken().trim();
            Character c = s.charAt(0);
            if (Character.isDigit(s.charAt(0)) || c.equals('-'))
                al.add(Double.valueOf(s));
        }
        int dim = al.size() / 2;
        double[] lows = new double[dim];
        double[] highs = new double[dim];
        for (int i = 0; i < dim; ++i)
            lows[i] = al.get(i);
        for (int i = dim; i < al.size(); ++i)
            highs[i - dim] = al.get(i);
        return Envelope.create(lows, highs);
    }

    /**
     * 将Envelope转为字符串，内部直接调用成员函数toString()
     * 与fromString成对使用
     *
     * @param e
     * @return
     */
    public static String toString(final Envelope e) {
        return e.toString();
    }
}
