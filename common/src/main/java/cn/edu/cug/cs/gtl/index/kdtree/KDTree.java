package cn.edu.cug.cs.gtl.index.kdtree;

import cn.edu.cug.cs.gtl.index.kdtree.impl.KDTreeImpl;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.index.kdtree.impl.KDTreeImpl;

import java.util.Collection;
import java.util.List;

public interface KDTree<T extends Vector> {


    boolean insert(T value);

    boolean contains(T value);

    boolean remove(T value);

    static KDTree create(Envelope envelope, List<Vector> points) {
        return new KDTreeImpl(envelope, points);
    }

    default List<Envelope> getPartitionEnvelopes() {
        return getLeafNodeEnvelopes();
    }

    List<Envelope> getLeafNodeEnvelopes();
}
