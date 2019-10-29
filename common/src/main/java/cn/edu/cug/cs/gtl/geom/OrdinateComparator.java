package cn.edu.cug.cs.gtl.geom;

import java.io.Serializable;
import java.util.Comparator;


/**
 * 坐标分量比较器
 */
public class OrdinateComparator<V extends Vector> implements Comparator<V>, Serializable {
    private static final long serialVersionUID = 1L;


    public static final Comparator<Vector> X_COMPARATOR = new OrdinateComparator<Vector>(0);

    public static final Comparator<Vector> Y_COMPARATOR = new OrdinateComparator<Vector>(1);

    public static final Comparator<Vector> Z_COMPARATOR = new OrdinateComparator<Vector>(2);

    public OrdinateComparator(int dimensionOrder) {
        this.dimensionOrder = dimensionOrder;
    }

    public OrdinateComparator() {
        this.dimensionOrder = 0;
    }

    public int getDimensionOrder() {
        return dimensionOrder;
    }

    public void setDimensionOrder(int dimensionOrder) {
        this.dimensionOrder = dimensionOrder;
    }

    int dimensionOrder;//0,1,2

    @Override
    public int compare(V o1, V o2) {
        if (o1.getOrdinate(dimensionOrder) < o2.getOrdinate(dimensionOrder))
            return -1;
        if (o1.getOrdinate(dimensionOrder) > o2.getOrdinate(dimensionOrder))
            return 1;
        return 0;
    }
}
