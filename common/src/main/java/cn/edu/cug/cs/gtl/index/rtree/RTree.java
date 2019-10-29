package cn.edu.cug.cs.gtl.index.rtree;

import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.index.Indexable;
import cn.edu.cug.cs.gtl.index.rtree.impl.RTreeImpl;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;

import java.util.List;

/**
 * Created by ZhenwenHe on 2016/12/22.
 */
public interface RTree extends Indexable {


    List<Envelope> getPartitionEnvelopes();

    // String                   Value     Description
    // ----------------------------------------------
    // IndexIdentifier         Identifier   If specified an existing index will be openened from the supplied
    //                          storage manager with the given index id. Behaviour is unspecified
    //                          if the index id or the storage manager are incorrect.
    // Dimension                Integer  Dimensionality of the data that will be inserted.
    // IndexCapacity            Integer  The index node capacity. Default is 100.
    // LeafCapacity             Integer  The leaf node capacity. Default is 100.
    // FillFactor               Double The fill factor. Default is 70%
    // TreeVariant              Integer   Can be one of Linear, Quadratic or Rstar. Default is Rstar
    // NearMinimumOverlapFactor Integer  Default is 32.
    // SplitDistributionFactor  Double Default is 0.4
    // ReinsertFactor           Double Default is 0.3
    // EnsureTightMBRs          Boolean   Default is true
    void reset(StorageManager storageManager, PropertySet propSet);

    void reset(StorageManager storageManager,
               Identifier indexIdentifier,
               int dimension,
               int indexCapacity, int leafCapacity,
               double fillFactor,
               RTreeVariant treeVariant,
               int nearMinimumOverlapFactor,
               double splitDistributionFactor,
               double reinsertFactor,
               boolean ensureTightMBRs);


    static RTree create(StorageManager storageManager, PropertySet propSet) {
        RTree r = new RTreeImpl();
        r.reset(storageManager, propSet);
        return r;
    }

    static RTree create(StorageManager storageManager,
                        Identifier indexIdentifier,
                        int dimension,
                        int indexCapacity, int leafCapacity,
                        double fillFactor,
                        RTreeVariant treeVariant,
                        int nearMinimumOverlapFactor,
                        double splitDistributionFactor,
                        double reinsertFactor,
                        boolean ensureTightMBRs) {
        RTree r = new RTreeImpl();
        r.reset(storageManager,
                indexIdentifier,
                dimension,
                indexCapacity, leafCapacity,
                fillFactor,
                treeVariant,
                nearMinimumOverlapFactor,
                splitDistributionFactor,
                reinsertFactor,
                ensureTightMBRs);
        return r;
    }

    static RTree create(int dimension, int indexCapacity, int leafCapacity) {
        RTree r = new RTreeImpl();
        r.reset(StorageManager.createMemoryStorageManager(),
                null,
                dimension,
                indexCapacity, leafCapacity,
                0,
                RTreeVariant.RV_RSTAR,
                0,
                0,
                0,
                true);
        return r;
    }
}
