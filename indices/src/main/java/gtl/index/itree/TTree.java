package gtl.index.itree;

import gtl.common.Pair;
import gtl.geom.Timeline;
import gtl.index.shape.TriangleShape;
import gtl.ipc.MasterDescriptor;
import gtl.ipc.SlaveDescriptor;

import java.util.List;

public interface TTree {
    TriangleShape getRootTriangle();
    List<Pair<TriangleShape,SlaveDescriptor> > executePartition(MasterDescriptor md);
    Timeline executeSimilarityQuery(Timeline t);
}
