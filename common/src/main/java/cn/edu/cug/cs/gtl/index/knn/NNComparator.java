package cn.edu.cug.cs.gtl.index.knn;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.index.Entry;
import cn.edu.cug.cs.gtl.index.shape.Shape;

public class NNComparator implements NearestNeighborComparator, java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public double getMinimumDistance(Shape query, Shape entry) {
        return query.getMinimumDistance(entry);
    }

    @Override
    public double getMinimumDistance(Shape query, Entry data) {
        Shape pS = data.getShape();
        double ret = query.getMinimumDistance(pS);
        return ret;
    }
}