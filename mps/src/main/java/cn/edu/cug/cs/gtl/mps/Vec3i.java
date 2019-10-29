package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @brief A 3D coordinates used for indexing in a 3D grid
 */
public class Vec3i implements Storable {
    /**
     * @brief x index
     */
    int _x;

    /**
     * @brief y index
     */
    int _y;

    /**
     * @brief z index
     */
    int _z;

    public int getX() {
        return _x;
    }

    public void setX(int _x) {
        this._x = _x;
    }

    public int getY() {
        return _y;
    }

    public void setY(int _y) {
        this._y = _y;
    }

    public int getZ() {
        return _z;
    }

    public void setZ(int _z) {
        this._z = _z;
    }

    /**
     * @brief Constructors
     * <p>
     * Default value is (0, 0, 0)
     */

    public Vec3i() {
        _x = 0;
        _y = 0;
        _z = 0;
    }

    /**
     * @param x x value
     * @param y y value
     * @param z z value
     * @brief Constructors from x, y, z
     */
    public Vec3i(final int x, final int y, final int z) {
        _x = x;
        _y = y;
        _z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vec3i)) return false;

        Vec3i vec3i = (Vec3i) o;

        if (_x != vec3i._x) return false;
        if (_y != vec3i._y) return false;
        return _z == vec3i._z;
    }

    @Override
    public int hashCode() {
        int result = _x;
        result = 31 * result + _y;
        result = 31 * result + _z;
        return result;
    }

    @Override
    public Object clone() {
        return new Vec3i(this._x, this._y, this._z);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        this._x = dataInput.readInt();
        this._y = dataInput.readInt();
        this._z = dataInput.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this._x);
        out.writeInt(this._y);
        out.writeInt(this._z);
        return true;
    }
}

