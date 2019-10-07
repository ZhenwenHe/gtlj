package gtl.index.knn;

import gtl.index.Entry;
import gtl.index.shape.Shape;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface NearestNeighborComparator extends java.io.Serializable {
    public double getMinimumDistance(Shape query, Shape entry);

    public double getMinimumDistance(Shape query, Entry data);
}
