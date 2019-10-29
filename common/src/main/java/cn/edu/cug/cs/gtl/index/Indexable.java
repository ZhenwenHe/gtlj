package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.index.knn.NearestNeighborComparator;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.feature.Feature;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.index.knn.NearestNeighborComparator;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.util.ObjectUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface Indexable extends Serializable {

    long serialVersionUID = 1L;

    default void merge(Indexable indexable) {

    }

    void insert(byte[] pData, Shape shape, Identifier shapeIdentifier);

    boolean delete(Shape shape, Identifier shapeIdentifier);

    void contains(Shape query, Visitor v);

    void intersects(Shape query, Visitor v);

    void pointLocation(PointShape query, Visitor v);

    void nearestNeighbor(int k, Shape query, Visitor v, NearestNeighborComparator nnc);

    void nearestNeighbor(int k, Shape query, Visitor v);

    void selfJoin(Shape s, Visitor v);

    void queryStrategy(QueryStrategy qs);

    PropertySet getProperties();

    void addCommand(Command in, CommandType ct);

    boolean isValid();

    Statistics getStatistics();

    int getDimension();

    default void insert(Geometry g) {
        Envelope e = g.getEnvelope();
        try {
            byte[] da = ObjectUtils.storeToByteArray(g);
            insert(da, new RegionShape(e), Identifier.create());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    default void insert(Feature g) {
        Envelope e = g.getEnvelope();
        try {
            byte[] da = g.storeToByteArray();
            insert(da, new RegionShape(e), Identifier.create());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    default Collection<Geometry> intersects(Shape query) {
        GeometryVisitor gv = new GeometryVisitor();
        intersects(query, gv);
        return gv.getGeometries();
    }

    default Collection<Geometry> contains(Shape query) {
        GeometryVisitor gv = new GeometryVisitor();
        contains(query, gv);
        return gv.getGeometries();
    }
}
