package cn.edu.cug.cs.gtl.index.shape;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geom3DSuits;
import cn.edu.cug.cs.gtl.geom.Vector;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public class PointShape implements Shape {
    private static final long serialVersionUID = 1L;

    Vector data;

    public PointShape() {
        this.data = Geom3DSuits.createVector();
    }

    public PointShape(double x, double y) {
        this.data = Geom3DSuits.createVector(x, y);
    }

    public PointShape(double x, double y, double z) {
        this.data = (Vector) Geom3DSuits.createVector(x, y, z);
    }

    public PointShape(double[] coordinates) {
        this.data = Geom3DSuits.createVector(coordinates);
    }


    public double[] getCoordinates() {
        return this.data.getCoordinates();
    }


    public double getCoordinate(int i) {
        return this.data.getOrdinate(i);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof PointShape || i instanceof Vector) {
            this.data.copyFrom(i);
        }
    }


    public double getX() {
        return this.data.getX();
    }

    public void setX(double x) {
        this.data.setX(x);
    }

    public double getY() {
        return this.data.getY();
    }

    public void setY(double y) {
        this.data.setY(y);
    }

    public double getZ() {
        return this.data.getZ();
    }

    public void setZ(double z) {
        this.data.setX(z);
    }


    public void makeInfinite(int dimension) {
        this.data.makeInfinite(dimension);
    }


    public void makeDimension(int dimension) {
        this.makeInfinite(dimension);
    }


    public void reset(double[] coordinates) {
        this.data.reset(coordinates);
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        return this.data.load(in);
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return this.data.store(out);
    }

    @Override
    public Object clone() {
        return new PointShape(this.getCoordinates());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PointShape)) return false;

        PointShape point = (PointShape) o;

        return Arrays.equals(getCoordinates(), point.getCoordinates());

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getCoordinates());
    }

    @Override
    public String toString() {
        return "PointShape{" +
                "coordinates=" + Arrays.toString(this.getCoordinates()) +
                '}';
    }

    @Override
    public boolean intersectsShape(Shape in) {
        if (in instanceof RegionShape) {
            return in.containsShape(this);
        } else
            return false;
    }

    @Override
    public boolean containsShape(Shape in) {
        return false;
    }

    @Override
    public boolean touchesShape(Shape in) {
        if (in instanceof PointShape) {
            return this.equals(in);
        }
        if (in instanceof RegionShape) {
            return in.touchesShape(this);
        }

        return false;
    }

    @Override
    public Vector getCenter() {
        try {
            return (Vector) this.data.clone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getDimension() {
        return this.data.getDimension();
    }

    @Override
    public Envelope getMBR() {
        return Geom3DSuits.createEnvelope(this.getCoordinates(), this.getCoordinates());
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getMinimumDistance(Shape in) {
        if (in instanceof PointShape) {
            return getMinimumDistance((PointShape) (in));
        }
        if (in instanceof RegionShape) {
            in.getMinimumDistance(this);
        }
        return 0;
    }

    @Override
    public long getByteArraySize() {
        return getDimension() * 8;
    }

    public double getMinimumDistance(PointShape in) {
        double ret = 0;
        double[] coords = in.getCoordinates();
        for (int cDim = 0; cDim < this.getCoordinates().length; ++cDim) {
            ret += Math.pow(this.getCoordinates()[cDim] - coords[cDim], 2.0);
        }
        return Math.sqrt(ret);
    }
}

