package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.ipc.MasterDescriptor;
import cn.edu.cug.cs.gtl.ipc.SlaveDescriptor;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.geom.Timeline;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.ipc.MasterDescriptor;
import cn.edu.cug.cs.gtl.ipc.SlaveDescriptor;

import java.util.List;

public interface TTree {
    TriangleShape getRootTriangle();

    List<Pair<TriangleShape, SlaveDescriptor>> executePartition(MasterDescriptor md);

    Timeline executeSimilarityQuery(Timeline t);
}
