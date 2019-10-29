package cn.edu.cug.cs.gtl.index.dtree;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * rectangle decomposition tree
 */
public class RDTree<T extends Serializable> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 每个叶子节点中最多能存放leafNodeCapacity数据对象
     */
    protected int leafNodeCapacity;
    protected StorageManager storageManager;
    protected Map<String, Identifier> leafNodes;
    protected ArrayList<String> emptyNodes;

    protected RegionShape baseRegionShape;
    //rectangle encoder
    protected RegionEncoder regionEncoder;

    //spark variables
    transient JavaSparkContext sparkContext;
    transient JavaRDD<Pair<String, Identifier>> sparkRDD;
    transient AtomicBoolean constructedRDD; //whether the RDD is constructed, threadsafe


}
