package cn.edu.cug.cs.gtl.geom;

/**
 * Created by hadoop on 17-3-21.
 */

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.PrecisionModel;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.math.PrecisionModel;
import cn.edu.cug.cs.gtl.util.ObjectUtils;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class Geometry implements Boundable, GeometryOperators, Dimensional {

    private static final long serialVersionUID = 1L;


    public static final int POINT = 0;
    public static final int MULTIPOINT = 1;
    public static final int ANNOTATION = 2;

    public static final int LINESTRING = 11;
    public static final int LINEARRING = 12;
    public static final int MULTILINESTRING = 13;
    public static final int INDEXEDPOLYLINE = 14;

    public static final int POLYGON = 21;
    public static final int MULTIPOLYGON = 22;
    public static final int TRIANGLEMESH = 23;
    public static final int INDEXEDPOLYGON = 24;
    public static final int DEMGRID = 25;
    public static final int QUADMESH = 26;

    public static final int SOLID = 31;
    public static final int MULTISOLID = 32;
    public static final int INDEXEDSOLID = 33;
    public static final int CUBICALMESH = 34;
    public static final int HEXAHEDRALMESH = 35;
    public static final int TETRAHEDRALMESH = 36;
    public static final int CORNERPOINTGRID = 37;

    public static final int GEOMETRYCOLLECTION = 100;
    /**
     * The bounding box of this <code>Geometry</code>.
     */
    protected Envelope envelope;
    /**
     * The ID of the Spatial Reference System used by this <code>Geometry</code>
     */
    protected int SRID;
    /**
     * An object reference which can be used to carry ancillary data defined
     * by the client.
     * If this object needs to be storage, please implement Serializable
     * otherwise it will not be storage with this geometry.
     */
    protected Object userData;
    /**
     * if userDataSize =0 , the userData does not need to storage
     * else the userDataSize id the bytearray length of userData
     * in this case, the userData must implement the interface gtl.o.Serializable
     */
    protected int userDataSize;
    protected int geometryType;
    private Color defaultColor = null;


    public Geometry() {
        this.envelope = Envelope.create(2);
        this.SRID = 8037;
        this.userData = null;
        this.userDataSize = 0;
    }

    public static Geometry create(Class<?> binding, int dim) {
        try {
            Constructor c = binding.getConstructor(new Class[]{int.class});
            return (Geometry) c.newInstance(new Object[]{dim});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Geometry create(int gtype, int dim) {
        switch (gtype) {
            case POINT: {
                return (Geometry) new Point(dim);
            }
            case MULTIPOINT: {
                return (Geometry) new MultiPoint(dim);
            }
            case ANNOTATION: {
                return (Geometry) new Annotation(dim);
            }
            case LINESTRING: {
                return (Geometry) new LineString(dim);
            }
            case LINEARRING: {
                return (Geometry) new LinearRing(dim);
            }
            case MULTILINESTRING: {
                return (Geometry) new MultiLineString(dim);
            }
            case INDEXEDPOLYLINE: {
                return (Geometry) new IndexedPolyline(dim);
            }
            case POLYGON: {
                return (Geometry) new Polygon(dim);
            }
            case MULTIPOLYGON: {
                return (Geometry) new MultiPolygon(dim);
            }
            case TRIANGLEMESH: {
                return (Geometry) new TriangleMesh(dim);
            }
            case DEMGRID: {
                return (Geometry) new DEMGrid(dim);
            }
            case INDEXEDPOLYGON: {
                return (Geometry) new IndexedPolygon(dim);
            }
            case QUADMESH: {
                return (Geometry) new QuadMesh(dim);
            }
            case SOLID: {
                return (Geometry) new Solid(dim);
            }
            case INDEXEDSOLID: {
                return (Geometry) new IndexedSolid(dim);
            }
            case CUBICALMESH: {
                return (Geometry) new CubicalMesh(dim);
            }
            case CORNERPOINTGRID: {
                return new CornerPointGrid();
            }
            case GEOMETRYCOLLECTION: {
                return (Geometry) new GeometryCollection(dim);
            }
        }
        return null;
    }

    public int getType() {
        return this.geometryType;
    }


    public static String getTypeName(int geomType) {
        return getTypeBinding(geomType).getName();
    }

    public static String getSimpleTypeName(int geomType) {
        return getTypeBinding(geomType).getSimpleName();
    }

    public static int getType(String simpleTypeName) {
        if (simpleTypeName.equalsIgnoreCase("POINT")) {
            return Geometry.POINT;
        } else if (simpleTypeName.equalsIgnoreCase("MULTIPOINT")) {
            return Geometry.MULTIPOINT;
        } else if (simpleTypeName.equalsIgnoreCase("ANNOTATION")) {
            return Geometry.ANNOTATION;
        } else if (simpleTypeName.equalsIgnoreCase("LINESTRING")) {
            return Geometry.LINESTRING;
        } else if (simpleTypeName.equalsIgnoreCase("LINEARRING")) {
            return Geometry.LINEARRING;
        } else if (simpleTypeName.equalsIgnoreCase("MULTILINESTRING")) {
            return Geometry.MULTILINESTRING;
        } else if (simpleTypeName.equalsIgnoreCase("INDEXEDPOLYLINE")) {
            return Geometry.INDEXEDPOLYLINE;
        } else if (simpleTypeName.equalsIgnoreCase("POLYGON")) {
            return Geometry.POLYGON;
        } else if (simpleTypeName.equalsIgnoreCase("MULTIPOLYGON")) {
            return Geometry.MULTIPOLYGON;
        } else if (simpleTypeName.equalsIgnoreCase("TRIANGLEMESH")) {
            return Geometry.TRIANGLEMESH;
        } else if (simpleTypeName.equalsIgnoreCase("INDEXEDPOLYGON")) {
            return Geometry.INDEXEDPOLYGON;
        } else if (simpleTypeName.equalsIgnoreCase("DEMGRID")) {
            return Geometry.DEMGRID;
        } else if (simpleTypeName.equalsIgnoreCase("QUADMESH")) {
            return Geometry.QUADMESH;
        } else if (simpleTypeName.equalsIgnoreCase("SOLID")) {
            return Geometry.SOLID;
        } else if (simpleTypeName.equalsIgnoreCase("MULTISOLID")) {
            return Geometry.MULTISOLID;
        } else if (simpleTypeName.equalsIgnoreCase("INDEXEDSOLID")) {
            return Geometry.INDEXEDSOLID;
        } else if (simpleTypeName.equalsIgnoreCase("CUBICALMESH")) {
            return Geometry.CUBICALMESH;
        } else if (simpleTypeName.equalsIgnoreCase("CORNERPOINTGRID")) {
            return Geometry.CORNERPOINTGRID;
        } else if (simpleTypeName.equalsIgnoreCase("HEXAHEDRALMESH")) {
            return Geometry.HEXAHEDRALMESH;
        } else if (simpleTypeName.equalsIgnoreCase("GEOMETRYCOLLECTION")) {
            return Geometry.GEOMETRYCOLLECTION;
        } else
            return -1;
    }


    public static Class<?> getTypeBinding(int geomType) {
        switch (geomType) {
            case Geometry.POINT: {
                return Point.class;//new String("POINT");
            }
            case Geometry.MULTIPOINT: {
                return MultiPoint.class;//new String("MULTIPOINT");
            }
            case ANNOTATION: {
                return Annotation.class;
            }
            case Geometry.LINESTRING: {
                return LineString.class;//new String("LINESTRING");
            }
            case Geometry.LINEARRING: {
                return LinearRing.class;//new String("LINEARRING");
            }
            case Geometry.MULTILINESTRING: {
                return MultiLineString.class;//new String("MULTILINESTRING");
            }
            case Geometry.INDEXEDPOLYLINE: {
                return IndexedPolyline.class;//new String("INDEXEDPOLYLINE");
            }
            case Geometry.POLYGON: {
                return Polygon.class;//String("POLYGON");
            }
            case Geometry.MULTIPOLYGON: {
                return MultiPolygon.class;//new String("MULTIPOLYGON");
            }
            case Geometry.TRIANGLEMESH: {
                return TriangleMesh.class;//new String("TRIANGLEMESH");
            }
            case Geometry.INDEXEDPOLYGON: {
                return IndexedPolygon.class;//new String("INDEXEDPOLYGON");
            }
            case Geometry.DEMGRID: {
                return DEMGrid.class;//new String("DEMGRID");
            }
            case Geometry.QUADMESH: {
                return QuadMesh.class;
            }
            case Geometry.SOLID: {
                return Solid.class;//new String("SOLID");
            }
            case Geometry.MULTISOLID: {
                return MultiSolid.class;//new String("MULTISOLID");
            }
            case Geometry.INDEXEDSOLID: {
                return IndexedSolid.class;//new String("INDEXEDSOLID");
            }
            case Geometry.CUBICALMESH: {
                return CubicalMesh.class;
            }
            case Geometry.CORNERPOINTGRID: {
                return CornerPointGrid.class;
            }
            case Geometry.HEXAHEDRALMESH: {
                return HexahedralMesh.class;
            }
            case Geometry.GEOMETRYCOLLECTION: {
                return GeometryCollection.class;//new String("GEOMETRYCOLLECTION");
            }
            default: {
                return null;
            }
        }
    }

    public PrecisionModel getPrecisionModel() {
        return new PrecisionModel();
    }

    public abstract boolean isEmpty();

    public Envelope getEnvelope() {
        return this.envelope;
    }

    public void setEnvelope(Envelope envelope) {
        if (envelope != null)
            this.envelope.copyFrom(envelope);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int getSRID() {
        return SRID;
    }

    public void setSRID(int SRID) {
        this.SRID = SRID;
    }

    public Object getUserData() {
        return userData;
    }

    public void setUserData(Object userData) {
        this.userData = userData;
        this.userDataSize = 0;
    }

    public void makeDimension(int dim) {
        this.envelope.makeDimension(dim);
    }

    public int getDimension() {
        return this.envelope.getDimension();
    }

    /**
     * @param userData
     * @param storageFlag if the value is true, the userData will be stored with Geometry
     *                    in this case ,
     */
    public void setUserData(cn.edu.cug.cs.gtl.io.Serializable userData, boolean storageFlag) {
        if (storageFlag && userData != null) {
            this.userData = userData;
            this.userDataSize = ObjectUtils.getByteArraySize(userData);
        } else {
            this.userDataSize = 0;
            this.userData = userData;
        }
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Geometry) {
            Geometry g = (Geometry) i;
            g.geometryType = this.geometryType;
            this.SRID = g.SRID;
            this.envelope.copyFrom(g.envelope);
            if (g.defaultColor != null)
                this.defaultColor = (Color) g.defaultColor.clone();

            this.userDataSize = g.userDataSize;
            if (this.userDataSize != 0) {
                if (g.userData != null) {
                    this.userData = ((cn.edu.cug.cs.gtl.io.Serializable) g.userData).clone();
                }
            } else
                this.userData = this.userData;
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.geometryType = in.readInt();
        this.SRID = in.readInt();
        this.envelope.load(in);
        int f = in.readInt();
        if (f == 0)
            this.defaultColor = null;
        else {
            this.defaultColor = new Color();
            this.defaultColor.load(in);
        }
        this.userDataSize = in.readInt();
        if (this.userDataSize > 0) { //this.userData!=null
            byte[] tmp = new byte[this.userDataSize];
            in.readFully(tmp);
            ObjectUtils.loadFromByteArray(tmp);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.geometryType);
        out.writeInt(this.SRID);
        this.envelope.store(out);
        if (this.defaultColor == null)
            out.writeInt(0);
        else {
            out.writeInt(1);
            this.defaultColor.store(out);
        }
        out.writeInt(this.userDataSize);
        if (this.userDataSize > 0) {
            byte[] tmp = ObjectUtils.storeToByteArray(this.userData);
            out.write(tmp);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 4 * 4 + this.envelope.getByteArraySize();
        if (this.defaultColor != null)
            len += this.defaultColor.getByteArraySize();
        if (this.userData != null && this.userData instanceof cn.edu.cug.cs.gtl.io.Serializable) {
            cn.edu.cug.cs.gtl.io.Serializable s = (cn.edu.cug.cs.gtl.io.Serializable) this.userData;
            len += s.getByteArraySize();
        }
        return len;
    }

    @Override
    public Geometry clone() {
        try {
            Geometry g = (Geometry) super.clone();
            g.geometryType = this.geometryType;
            g.SRID = this.SRID;
            g.envelope.copyFrom(this.envelope);
            if (this.defaultColor != null)
                g.defaultColor = (Color) this.defaultColor.clone();

            g.userDataSize = this.userDataSize;
            if (g.userDataSize != 0) {
                if (this.userData != null) {
                    if (g.defaultColor instanceof cn.edu.cug.cs.gtl.io.Serializable)
                        g.userData = ((Serializable) this.userData).clone();
                }
            } else
                g.userData = this.userData;

            return g;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(baos);
//            oos.writeObject(this);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(bais);
//            return (ois.readObject());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    //operators
    @Override
    public boolean coveredBy(Geometry g) {
        return g.covers(this);
    }

    @Override
    public boolean within(Geometry g) {
        return g.contains(this);
    }

    @Override
    public boolean disjoint(Geometry g) {
        return !intersects(g);
    }

    @Override
    public double distance(Geometry g) {
        return GeometryOperators.create(this).distance(g);
    }

    @Override
    public boolean touches(Geometry g) {
        return GeometryOperators.create(this).touches(g);
    }

    @Override
    public boolean intersects(Geometry g) {
        return GeometryOperators.create(this).intersects(g);
    }

    @Override
    public boolean crosses(Geometry g) {
        return GeometryOperators.create(this).crosses(g);
    }

    @Override
    public boolean contains(Geometry g) {
        return GeometryOperators.create(this).contains(g);
    }

    @Override
    public boolean overlaps(Geometry g) {
        return GeometryOperators.create(this).overlaps(g);
    }

    @Override
    public boolean covers(Geometry g) {
        return GeometryOperators.create(this).covers(g);
    }

    @Override
    public Geometry buffer(double distance) {
        return GeometryOperators.create(this).buffer(distance);
    }

    @Override
    public Geometry convexHull() {
        return GeometryOperators.create(this).convexHull();
    }

    @Override
    public Geometry intersection(Geometry other) {
        return GeometryOperators.create(this).intersection(other);
    }

    @Override
    public Geometry union(Geometry other) {
        return GeometryOperators.create(this).union(other);
    }

    @Override
    public Geometry difference(Geometry other) {
        return GeometryOperators.create(this).difference(other);
    }

    @Override
    public Geometry symDifference(Geometry other) {
        return GeometryOperators.create(this).symDifference(other);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Geometry)) return false;

        Geometry geometry = (Geometry) o;

        if (SRID != geometry.SRID) return false;
        if (userDataSize != geometry.userDataSize) return false;
        if (geometryType != geometry.geometryType) return false;
        if (envelope != null ? !envelope.equals(geometry.envelope) : geometry.envelope != null) return false;
        if (userData != null ? !userData.equals(geometry.userData) : geometry.userData != null) return false;
        return defaultColor != null ? defaultColor.equals(geometry.defaultColor) : geometry.defaultColor == null;
    }

    @Override
    public int hashCode() {
        int result = envelope != null ? envelope.hashCode() : 0;
        result = 31 * result + SRID;
        result = 31 * result + (userData != null ? userData.hashCode() : 0);
        result = 31 * result + userDataSize;
        result = 31 * result + geometryType;
        result = 31 * result + (defaultColor != null ? defaultColor.hashCode() : 0);
        return result;
    }
}
