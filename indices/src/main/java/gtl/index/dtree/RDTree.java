package gtl.index.dtree;

import gtl.common.Identifier;
import gtl.common.Pair;
import gtl.index.shape.RegionShape;
import gtl.io.storage.StorageManager;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * rectangle decomposition tree
 *
 */
public class RDTree <T extends gtl.io.Serializable> implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 每个叶子节点中最多能存放leafNodeCapacity数据对象
     */
    protected int leafNodeCapacity;
    protected StorageManager storageManager;
    protected Map<String,Identifier> leafNodes;
    protected ArrayList<String> emptyNodes;

    protected RegionShape baseRegionShape;
    //rectangle encoder
    protected RegionEncoder regionEncoder;

    //spark variables
    transient JavaSparkContext sparkContext;
    transient JavaRDD<Pair<String,Identifier>> sparkRDD;
    transient AtomicBoolean constructedRDD; //whether the RDD is constructed, threadsafe



}
