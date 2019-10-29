package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Represents a polygon with linear edges, which may include holes.
 * The outer boundary (shell)
 * and inner boundaries (holes) of the polygon are represented by {@link LinearRing}s.
 * The boundary rings of the polygon may have any orientation.
 * Polygons are closed, simple geometries by definition.
 * <p>
 * The polygon model conforms to the assertions specified in the
 * <A HREF="http://www.opengis.org/techno/specs.htm">OpenGIS Simple Features
 * Specification for SQL</A>.
 * <p>
 * A <code>Polygon</code> is topologically valid if and only if:
 * <ul>
 * <li>the coordinates which define it are valid coordinates
 * <li>the linear rings for the shell and holes are valid
 * (i.e. are closed and do not self-intersect)
 * <li>holes touch the shell or another hole at at most one point
 * (which implies that the rings of the shell and holes must not cross)
 * <li>the interior of the polygon is connected,
 * or equivalently no sequence of touching holes
 * makes the interior of the polygon disconnected
 * (i.e. effectively split the polygon into two pieces).
 * </ul>
 */
public class Polygon extends Geometry implements Polygonal {
    private static final long serialVersionUID = 1L;

    /**
     * The exterior boundary,
     * or <code>null</code> if this <code>Polygon</code>
     * is empty.
     */
    protected LinearRing shell = null;

    /**
     * The interior boundaries, if any.
     * This instance var is never null.
     * If there are no holes, the array is of zero length.
     */
    protected LinearRing[] holes = null;

    /**
     * 材质ID，主要用于三维对象，默认为-1,表示不适用材质
     */
    private long materialID;

    public Polygon(@NotNull LinearRing shell, LinearRing[] holes) {
        super.makeDimension(shell.getDimension());
        this.shell = shell;
        this.holes = holes;
        this.geometryType = POLYGON;
        this.envelope.combine(this.shell.getEnvelope());
        if (holes != null) {
            for (LinearRing r : holes)
                this.envelope.combine(r.getEnvelope());
        }
        materialID = -1;
    }

    public Polygon(@NotNull LinearRing shell) {
        super.makeDimension(shell.getDimension());
        this.shell = shell;
        this.geometryType = POLYGON;
        this.envelope.combine(this.shell.getEnvelope());
        if (holes != null) {
            for (LinearRing r : holes)
                this.envelope.combine(r.getEnvelope());
        }
        materialID = -1;
    }

    public Polygon() {
        this.geometryType = POLYGON;
        this.shell = new LinearRing(2);
        materialID = -1;
    }

    public Polygon(int dim) {
        super.makeDimension(dim);
        this.geometryType = POLYGON;
        materialID = -1;
    }

    @Override
    public boolean isEmpty() {
        return shell.isEmpty();
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Polygon) {
            Polygon g = (Polygon) i;
            super.copyFrom(i);
            this.shell = (LinearRing) g.shell.clone();
            if (g.holes != null) {
                this.holes = new LinearRing[g.holes.length];
                int k = 0;
                for (LinearRing r : g.holes) {
                    this.holes[k] = (LinearRing) r.clone();
                    k++;
                }
            }
            this.materialID = g.materialID;
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        int s = in.readInt();
        if (s > 0) {
            if (this.shell == null)
                this.shell = (LinearRing) create(LINEARRING, 2);
            this.shell.load(in);
        } else {
            this.shell = null;
        }

        int len = in.readInt();
        if (len > 0) {
            this.holes = new LinearRing[len];
            for (int i = 0; i < len; ++i) {
                this.holes[i] = new LinearRing();
            }
        }

        this.materialID = in.readLong();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        if (this.shell != null) {
            out.writeInt(1);
            this.shell.store(out);
        } else {
            out.writeInt(0);
        }

        int len = 0;
        if (this.holes != null) len = this.holes.length;
        out.writeInt(len);
        if (len > 0) {
            for (LinearRing r : this.holes)
                r.store(out);
        }
        out.writeLong(this.materialID);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = super.getByteArraySize() + 4 + this.shell.getByteArraySize();

        len += 4;//holes number
        if (this.holes != null) {
            for (LinearRing r : this.holes)
                len += ObjectUtils.getByteArraySize(r);
        }

        len += 8;

        return len;
    }

    @Override
    public Polygon clone() {
        Polygon p = new Polygon();
        p.copyFrom(this);
        return p;
    }

    public LinearRing getExteriorRing() {
        return this.shell;
    }

    public void setExteriorRing(LinearRing lr) {
        this.shell = lr;
        this.envelope = (Envelope) lr.getEnvelope().clone();
    }

    public LinearRing[] getInteriorRings() {
        return this.holes;
    }

    public LinearRing getInteriorRing(int i) {
        if (this.holes == null) return null;
        return this.holes[i];
    }

    public void setInteriorRings(LinearRing[] lrs) {
        this.holes = lrs;
    }

    public long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(long materialID) {
        this.materialID = materialID;
    }


    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Polygon)) return false;
        if (!super.equals(o)) return false;

        Polygon polygon = (Polygon) o;

        if (getMaterialID() != polygon.getMaterialID()) return false;
        if (shell != null ? !shell.equals(polygon.shell) : polygon.shell != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(holes, polygon.holes);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (shell != null ? shell.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(holes);
        result = 31 * result + (int) (getMaterialID() ^ (getMaterialID() >>> 32));
        return result;
    }
}


