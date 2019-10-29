package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Models a collection of {@link Geometry}s of
 * arbitrary type and dimension.
 */
public class GeometryCollection extends Geometry implements Collection<Geometry> {
    private static final long serialVersionUID = 1L;

    /**
     * Internal representation of this <code>GeometryCollection</code>.
     */
    protected ArrayList<Geometry> geometries;

    public ArrayList<Geometry> getGeometries() {
        return geometries;
    }

    public GeometryCollection(@NotNull ArrayList<Geometry> geometries) {
        super.makeDimension(geometries.get(0).getDimension());
        this.geometries = new ArrayList<Geometry>(geometries.size());
        for (Geometry g : geometries) {
            this.geometries.add(g);
            this.envelope.combine(g.getEnvelope());
        }
        this.geometryType = GEOMETRYCOLLECTION;
    }

    public GeometryCollection(@NotNull Geometry[] geometries) {
        super.makeDimension(geometries[0].getDimension());
        this.geometries = new ArrayList<Geometry>(geometries.length);

        for (Geometry g : geometries) {
            this.geometries.add(g);
            this.envelope.combine(g.getEnvelope());
        }
        this.geometryType = GEOMETRYCOLLECTION;
    }

    public GeometryCollection() {
        this.geometries = new ArrayList<Geometry>();
        this.geometryType = GEOMETRYCOLLECTION;
    }

    public GeometryCollection(int dim) {
        super.makeDimension(dim);
        this.geometries = new ArrayList<Geometry>();
        this.geometryType = GEOMETRYCOLLECTION;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < geometries.size(); i++) {
            if (!geometries.get(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void makeDimension(int dim) {
        super.makeDimension(dim);
        for (Geometry g : geometries)
            g.makeDimension(dim);
    }

    public Geometry getGeometry(int n) {
        return this.geometries.get(n);
    }

    @Override
    public int size() {
        return this.geometries.size();
    }

    @Override
    public boolean contains(Object o) {
        return this.geometries.contains(o);
    }

    @Override
    public Iterator<Geometry> iterator() {
        return this.geometries.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.geometries.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.geometries.toArray(a);
    }

    @Override
    public boolean add(Geometry geometry) {
        return this.geometries.add(geometry);
    }

    @Override
    public boolean remove(Object o) {
        return this.geometries.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.geometries.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Geometry> c) {
        return this.geometries.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.geometries.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.geometries.retainAll(c);
    }

    @Override
    public void clear() {
        this.geometries.clear();
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        int c = in.readInt();
        if (c > 0) {
            this.geometries.clear();
            this.geometries.ensureCapacity(c);
            for (int i = 0; i < c; ++i) {
                this.geometries.add((Geometry) ObjectUtils.load(in));
            }
        } else {
            this.geometries.clear();
        }

        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        out.writeInt(this.geometries.size());
        for (Geometry g : this.geometries) {
            ObjectUtils.store(g, out);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = super.getByteArraySize();
        len += 4;
        for (Geometry g : this.geometries) {
            len += ObjectUtils.getByteArraySize(g);
        }
        return len;
    }

    @Override
    public GeometryCollection clone() {
        GeometryCollection gc = new GeometryCollection();
        gc.geometries.ensureCapacity(this.size());
        for (Geometry g : geometries) {
            gc.add((Geometry) g.clone());
        }
        return gc;
    }


}

