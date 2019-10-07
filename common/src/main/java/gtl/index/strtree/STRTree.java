package gtl.index.strtree;

import gtl.geom.Envelope;
import gtl.index.Indexable;
import gtl.index.strtree.impl.STRTreeImpl;

import java.io.Serializable;
import java.util.List;

public interface STRTree extends Indexable,Serializable {

    long serialVersionUID = 1L;

    void insert(Envelope itemEnv, Object item);

    default List<Envelope> getPartitionEnvelopes(){
        return getLeafNodeEnvelopes();
    }

    List<Envelope> getLeafNodeEnvelopes() ;
    static STRTree create(int nodeCapacity){
        return new STRTreeImpl(nodeCapacity);
    }
}
