package gtl.index;

import gtl.common.Identifier;
import gtl.common.PropertySet;
import gtl.common.Variant;
import gtl.index.rtree.RTree;
import gtl.index.rtree.RTreeVariant;
import gtl.index.rtree.impl.RTreeImpl;
import gtl.io.storage.StorageManager;

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
