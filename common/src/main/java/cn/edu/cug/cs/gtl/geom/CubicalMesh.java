package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Geo3dML
 */
public class CubicalMesh extends HexahedralMesh {

    private static final long serialVersionUID = -2390373016302672615L;

    private double xStep;
    private double yStep;
    private double zStep;
    private int xCellNumber;
    private int yCellNumber;
    private int zCellNumber;


    public CubicalMesh() {
        this.geometryType = CUBICALMESH;
    }

    public CubicalMesh(int dim) {
        super(dim);
        this.geometryType = CUBICALMESH;
    }

    public CubicalMesh(Vector origin, double xStep, double yStep, double zStep, int xCellNumber, int yCellNumber, int zCellNumber) {
        this.xStep = xStep;
        this.yStep = yStep;
        this.zStep = zStep;
        this.xCellNumber = xCellNumber;
        this.yCellNumber = yCellNumber;
        this.zCellNumber = zCellNumber;
        this.geometryType = CUBICALMESH;
        setOrigin(origin);
    }

    @Override
    public int getGeometryType() {
        return CUBICALMESH;
    }

    @Override
    public int getVertexNumberPerCell() {
        return 8;
    }

    /**
     * 获取单体的个数
     *
     * @return
     */
    public int getCellNumber() {
        return xCellNumber * yCellNumber * zCellNumber;
    }

    public Vector getOrigin() {
        if (this.coordinates == null)
            return null;
        return this.coordinates.getVector(0);
    }

    public void setOrigin(Vector v) {
        if (this.coordinates == null) {
            this.coordinates = VectorSequence.create(1, v.getDimension());
            this.coordinates.add(v);
        } else {
            this.coordinates.getVector(0).reset(v.getCoordinates());
        }
    }


    public double getXStep() {
        return xStep;
    }

    public void setXStep(double xStep) {
        this.xStep = xStep;
    }

    public double getYStep() {
        return yStep;
    }

    public void setYStep(double yStep) {
        this.yStep = yStep;
    }

    public double getZStep() {
        return zStep;
    }

    public void setZStep(double zStep) {
        this.zStep = zStep;
    }

    public int getXCellNumber() {
        return xCellNumber;
    }

    public void setXCellNumber(int xCellNumber) {
        this.xCellNumber = xCellNumber;
    }

    public int getYCellNumber() {
        return yCellNumber;
    }

    public void setYCellNumber(int yCellNumber) {
        this.yCellNumber = yCellNumber;
    }

    public int getZCellNumber() {
        return zCellNumber;
    }

    public void setZCellNumber(int zCellNumber) {
        this.zCellNumber = zCellNumber;
    }

    public double[] getCellProperties(int x, int y, int z) {
        return super.getCellProperties(getCellOrder(x, y, z));
    }

    public int getCellOrder(int x, int y, int z) {
        return z * xCellNumber * yCellNumber + y * xCellNumber + x;
    }

    public Cuboid getCell(int x, int y, int z) {
        return null;
    }

    @Override
    public CubicalMesh clone() {
        CubicalMesh p = (CubicalMesh) super.clone();
        p.xCellNumber = this.xCellNumber;
        p.yCellNumber = this.yCellNumber;
        p.zCellNumber = this.zCellNumber;
        p.xStep = this.xStep;
        p.yStep = this.yStep;
        p.zStep = this.zStep;
        return p;
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        xStep = in.readDouble();
        yStep = in.readDouble();
        zStep = in.readDouble();
        xCellNumber = in.readInt();
        yCellNumber = in.readInt();
        zCellNumber = in.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        out.writeDouble(xStep);
        out.writeDouble(yStep);
        out.writeDouble(zStep);
        out.writeInt(xCellNumber);
        out.writeInt(yCellNumber);
        out.writeInt(zCellNumber);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = super.getByteArraySize() + 8 * 3 + 4 * 3;
        return len;
    }


}
