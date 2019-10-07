package cn.edu.cug.cs.gtl.index.knn;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.index.Entry;
import cn.edu.cug.cs.gtl.index.shape.Shape;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface NearestNeighborComparator extends java.io.Serializable {
    public double getMinimumDistance(Shape query, Shape entry);

    public double getMinimumDistance(Shape query, Entry data);
}
