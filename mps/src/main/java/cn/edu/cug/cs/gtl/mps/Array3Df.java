package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Random;

public class Array3Df implements Storable {
    int dimensionX;
    int dimensionY;
    int dimensionZ;
    float[] data;


    public Array3Df(int dimensionX, int dimensionY, int dimensionZ, float[] data) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.dimensionZ = dimensionZ;
        this.data = java.util.Arrays.copyOf(data, dimensionX * dimensionY * dimensionZ);
    }

    public Array3Df(int dimensionX, int dimensionY, int dimensionZ) {
        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.dimensionZ = dimensionZ;
        int s = dimensionX * dimensionY * dimensionZ;
        this.data = new float[s];
        for (int i = 0; i < s; ++i)
            this.data[i] = Float.NaN;
    }

    public Array3Df() {
        this.dimensionX = 0;
        this.dimensionY = 0;
        this.dimensionZ = 0;
        this.data = null;
    }

    public Array3Df(float[][][] a) {
        this.dimensionX = a[0][0].length;
        this.dimensionY = a[0].length;
        this.dimensionZ = a.length;
        this.data = new float[dimensionX * dimensionY * dimensionZ];

        for (int z = 0; z < dimensionZ; ++z) {
            for (int y = 0; y < dimensionY; ++y) {
                for (int x = 0; x < dimensionX; ++x) {
                    setElement(x, y, z, a[z][y][x]);
                }
            }
        }
    }

    public boolean isEmpty() {
        if (this.data == null || this.dimensionX <= 0 || this.dimensionY <= 0 || this.dimensionZ <= 0)
            return true;
        else return false;
    }

    public float[][][] toArray() {
        float[][][] a = new float[this.dimensionZ][this.dimensionY][this.dimensionX];
        for (int z = 0; z < dimensionZ; ++z) {
            for (int y = 0; y < dimensionY; ++y) {
                for (int x = 0; x < dimensionX; ++x) {
                    a[z][y][x] = getElement(x, y, z);
                }
            }
        }
        return a;
    }

    public int getDimensionX() {
        return dimensionX;
    }


    public int getDimensionY() {
        return dimensionY;
    }


    public int getDimensionZ() {
        return dimensionZ;
    }

    public final float[] getData() {
        return data;
    }

    public float getElement(int idxX, int idxY, int idxZ) {
        int i = Utility.threeDto1D(idxX, idxY, idxZ, dimensionX, dimensionY);
        return data[i];
    }

    public float getElement(final Vec3i idx) {
        int i = Utility.threeDto1D(idx.getX(), idx.getY(), idx.getZ(), dimensionX, dimensionY);
        return data[i];
    }

    public float getElement(int idx) {
        return data[idx];
    }

    public void setElement(int idxX, int idxY, int idxZ, float v) {
        int i = Utility.threeDto1D(idxX, idxY, idxZ, dimensionX, dimensionY);
        data[i] = v;
    }

    public void setElement(final Vec3i idx, float v) {
        int i = Utility.threeDto1D(idx.getX(), idx.getY(), idx.getZ(), dimensionX, dimensionY);
        data[i] = v;
    }

    public void setElement(int idx, float v) {
        data[idx] = v;
    }

    /**
     * 随机获取数组中的元素
     *
     * @return
     */
    public float randomElement() {
        Random r = new java.util.Random();
        int tiRandomX, tiRandomY, tiRandomZ;
        tiRandomX = (r.nextInt() % (int) (this.dimensionX));
        tiRandomY = (r.nextInt() % (int) (this.dimensionY));
        tiRandomZ = (r.nextInt() % (int) (this.dimensionZ));
        return getElement(tiRandomX, tiRandomY, tiRandomZ);
    }

    public int size() {
        if (data == null) return 0;
        else return this.data.length;
    }

    public void resize(int dimensionX, int dimensionY, int dimensionZ) {
        resize(dimensionX, dimensionY, dimensionZ, Float.NaN);
    }

    public void resize(int dimensionX, int dimensionY, int dimensionZ, boolean copyData) {
        if (copyData) {
            Array3Df a = new Array3Df(dimensionX, dimensionY, dimensionZ);
            int dimX = Math.min(dimensionX, this.dimensionX);
            int dimY = Math.min(dimensionY, this.dimensionY);
            int dimZ = Math.min(dimensionZ, this.dimensionZ);
            for (int z = 0; z < dimZ; ++z) {
                for (int y = 0; y < dimY; ++y) {
                    for (int x = 0; x < dimX; ++x) {
                        a.setElement(x, y, z, this.getElement(x, y, z));
                    }
                }
            }
            this.dimensionX = dimensionX;
            this.dimensionY = dimensionY;
            this.dimensionZ = dimensionZ;
            this.data = a.data;
        } else {
            resize(dimensionX, dimensionY, dimensionZ, Float.NaN);
        }
    }

    public void resize(int dimensionX, int dimensionY, int dimensionZ, float v) {
        if (size() != dimensionX * dimensionY * dimensionZ) {
            this.dimensionX = dimensionX;
            this.dimensionY = dimensionY;
            this.dimensionZ = dimensionZ;
            this.data = new float[dimensionX * dimensionY * dimensionZ];
            int s = dimensionX * dimensionY * dimensionZ;
            this.data = new float[s];
            for (int i = 0; i < s; ++i)
                this.data[i] = v;
        }
    }

    @Override
    public Object clone() {
        return new Array3Df(this.dimensionX, this.dimensionY, this.dimensionZ, this.data);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        this.dimensionX = dataInput.readInt();
        this.dimensionY = dataInput.readInt();
        this.dimensionZ = dataInput.readInt();
        int s = size();
        if (this.data == null)
            this.data = new float[s];
        if (this.data.length != s)
            this.data = new float[s];
        for (int i = 0; i < s; ++i) {
            this.data[i] = dataInput.readFloat();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.dimensionX);
        out.writeInt(this.dimensionY);
        out.writeInt(this.dimensionZ);
        for (float a : this.data)
            out.writeFloat(a);
        return true;
    }
}
