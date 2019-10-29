package cn.edu.cug.cs.gtl.index.kdtree.impl;

import cn.edu.cug.cs.gtl.geom.OrdinateComparator;
import cn.edu.cug.cs.gtl.geom.Vector;

import java.io.Serializable;

public class KDNode<T extends Vector> implements Comparable<KDNode>, Serializable {
    private static final long serialVersionUID = 1L;

    public final Vector id;
    public final int k;
    public final int depth;

    public KDNode parent = null;
    public KDNode lesser = null;
    public KDNode greater = null;

    public KDNode(T id) {
        this.id = id;
        this.k = id.getDimension();
        this.depth = 0;
    }


    public KDNode(T id, int k, int depth) {
        this.id = id;
        this.k = k;
        this.depth = depth;
    }

    public static int compareTo(int depth, int k, Vector o1, Vector o2) {
        int axis = depth % k;

//        if (axis == OrdinateComparator.X_AXIS)
//            return OrdinateComparator.X_COMPARATOR.compare(o1, o2);
//        if (axis == OrdinateComparator.Y_AXIS)
//            return OrdinateComparator.Y_COMPARATOR.compare(o1, o2);
//        return OrdinateComparator.Z_COMPARATOR.compare(o1, o2);
        return new OrdinateComparator<Vector>(axis).compare(o1, o2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return 31 * (this.k + this.depth + this.id.hashCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof KDNode))
            return false;

        KDNode kdNode = (KDNode) obj;
        if (this.compareTo(kdNode) == 0)
            return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(KDNode o) {
        return compareTo(depth, k, this.id, o.id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("k=").append(k);
        builder.append(" depth=").append(depth);
        builder.append(" id=").append(id.toString());
        return builder.toString();
    }
}
