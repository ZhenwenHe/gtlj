package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.index.rtree.RTree;
import cn.edu.cug.cs.gtl.index.rtree.RTreeVariant;
import cn.edu.cug.cs.gtl.index.rtree.impl.RTreeImpl;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.common.Variant;

import java.io.File;

/**
 * Created by ZhenwenHe on 2016/12/10.
 */
public class IndexSuits {
    public static final String DATA_DIR = "." + File.separator + "data" + File.separator;

    public static RTree createRTree(StorageManager sm, PropertySet ps) {
        return new RTreeImpl(sm, ps);
    }

    public static RTree createRTree(StorageManager sm,
                                    int dimension,
                                    int indexCapacity,
                                    int leafCapacity,
                                    RTreeVariant treeVariant) {
        return new RTreeImpl(sm, dimension, indexCapacity, leafCapacity, treeVariant);
    }

    public static RTree loadRTree(StorageManager sm, Identifier indexIdentifier) {
        Variant var = new Variant(indexIdentifier.longValue());
        PropertySet ps = PropertySet.create();
        ps.put("IndexIdentifier", var);
        return createRTree(sm, ps);
    }

}
