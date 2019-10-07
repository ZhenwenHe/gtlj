package gtl.index.kdtree;

import gtl.geom.Envelope;
import gtl.geom.Vector;
import gtl.index.kdtree.impl.KDTreeImpl;

import java.util.Collection;
import java.util.List;

public interface KDTree<T extends Vector> {



    boolean insert(T value);

    boolean contains(T value);

    boolean remove(T value);

    static KDTree create( Envelope envelope , List<Vector> points){
        return new KDTreeImpl(envelope,points);
    }

    default List<Envelope> getPartitionEnvelopes(){
        return getLeafNodeEnvelopes();
    }

    List<Envelope> getLeafNodeEnvelopes();
}
