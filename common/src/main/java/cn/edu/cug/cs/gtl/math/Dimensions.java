package cn.edu.cug.cs.gtl.math;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class Dimensions implements Storable {

    private static final long serialVersionUID = -7795713301279487458L;
    int[] data = null;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimensions dimensions = (Dimensions) o;
        return Arrays.equals(data, dimensions.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

    public Dimensions(int[] data) {
        this(data, false);
    }

    public Dimensions(int[] data, boolean bCopy) {
        if (bCopy) {
            this.data = Arrays.copyOf(data, data.length);
        } else {
            this.data = data;
        }
    }

    public Dimensions(int first) {
        data = new int[1];
        data[0] = first;
    }

    public Dimensions(int first, int second) {
        data = new int[2];
        data[0] = first;
        data[1] = second;
    }

    public Dimensions(int first, int second, int third) {
        data = new int[3];
        data[0] = first;
        data[1] = second;
        data[2] = third;
    }

    public Dimensions(int first, int second, int third, int fourth) {
        data = new int[4];
        data[0] = first;
        data[1] = second;
        data[2] = third;
        data[3] = fourth;
    }

    public int[] get() {
        return data;
    }

    public int get(int i) {
        return data[i];
    }

    public void set(int... d) {
        data = Arrays.copyOf(d, d.length);

    }

    public void set(int i, int dim) {
        if (data == null) {
            data = new int[i + 1];
        }
        if (data.length <= i) {
            int[] dd = new int[i + 1];
            for (int j = 0; j < data.length; ++j) {
                dd[j] = data[j];
            }
            data = dd;
        }
        data[i] = dim;
    }

    /**
     * 维度，例如1维则返回1，2维度，则返回2，三维则返回3，四维则返回4
     *
     * @return
     */
    public int numberDimensions() {
        return data == null ? 0 : data.length;
    }

    /**
     * 所有维度可以承载的总的元素个数，例如，维度为2,1D的长度为10,2D的长度为50;则可以承载的元素个数为20*50=1000
     *
     * @return
     */
    public int numberElements() {
        if (data == null) return 0;
        int s = 1;
        for (int i : data)
            s *= i;
        return s;
    }

    public void copy(Dimensions d) {
        this.data = Arrays.copyOf(d.data, d.data.length);
    }

    @Override
    public Object clone() {
        return new Dimensions(data, true);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        int s = in.readInt();
        if (s == 0) {
            data = null;
            return true;
        }

        data = new int[s];
        for (int i = 0; i < s; ++i) {
            data[i] = in.readInt();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        int s = data == null ? 0 : data.length;
        out.writeInt(s);
        for (int i : data) {
            out.writeInt(i);
        }
        return true;
    }
}
