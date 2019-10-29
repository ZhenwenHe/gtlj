package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

class Vec3i1f extends Vec3i implements Storable {
    /**
     * @brief value
     */
    float _v;

    /**
     * @brief Constructors
     * <p>
     * Default value is (0, 0, 0, NaN)
     */
    public Vec3i1f() {
        super();
        this._v = Float.NaN;
    }

    /**
     * @param x x value
     * @param y y value
     * @param z z value
     * @param v v value default is NaN
     * @brief Constructors from x, y, z and value
     */
    public Vec3i1f(int x, int y, int z, float v) {
        super(x, y, z);
        this._v = v;
    }

    /**
     * @param x x value
     * @param y y value
     * @param z z value
     * @param v v value default is NaN
     * @brief Constructors from x, y, z and value
     */
    public Vec3i1f(int x, int y, int z) {
        this(x, y, z, Float.NaN);
    }


    /**
     * @param coord3D a coord3D
     * @param v       v value default is NaN
     * @brief Constructors from a coords3D and a value
     */
    public Vec3i1f(final Vec3i coord3D, final float v) {
        this(coord3D._x, coord3D._y, coord3D._z, v);
    }

    /**
     * @return x value
     * @brief Getter v Value
     */
    public float getValue() {
        return _v;
    }

    /**
     * @param v v value
     * @brief Setter v Value
     */
    public void setValue(final float v) {
        _v = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec3i1f)) return false;
        if (!super.equals(o)) return false;

        Vec3i1f vec3i1f = (Vec3i1f) o;

        return Float.compare(vec3i1f._v, _v) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_v != +0.0f ? Float.floatToIntBits(_v) : 0);
        return result;
    }

    @Override
    public Object clone() {
        return new Vec3i1f(this._x, this._y, this._z, this._v);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        super.load(dataInput);
        this._v = dataInput.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        out.writeFloat(this._v);
        return true;
    }
}