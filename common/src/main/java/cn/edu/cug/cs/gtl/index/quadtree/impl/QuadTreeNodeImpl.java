package cn.edu.cug.cs.gtl.index.quadtree.impl;

import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.index.shape.ShapeSuits;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.NodeImpl;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.index.shape.ShapeSuits;

public abstract class QuadTreeNodeImpl extends NodeImpl {
    QuadTreeImpl tree;

    public QuadTreeNodeImpl(Identifier identifier, int level, int capacity, QuadTreeImpl tree, Shape shape) {
        super(identifier, level, capacity);
        this.tree = tree;
        this.shape = shape;
    }

    public QuadTreeNodeImpl() {
        this.tree = null;
    }

    public QuadTreeImpl getTree() {
        return tree;
    }

    public void setTree(QuadTreeImpl tree) {
        this.tree = tree;
    }

    @Override
    public abstract Object clone();

    @Override
    public Shape recalculateShape() {
        RegionShape er = null;
        RegionShape r = (RegionShape) getShape();
        int children = getChildrenCount();
        //重新计算节点的矩形区域
        for (int cDim = 0; cDim < r.getDimension(); ++cDim) {
            r.setLowOrdinate(cDim, Double.MAX_VALUE);
            r.setHighOrdinate(cDim, -Double.MAX_VALUE);
            for (int u32Child = 0; u32Child < children; ++u32Child) {
                er = (RegionShape) getChildShape(u32Child);
                r.setLowOrdinate(cDim, Math.min(r.getLowOrdinate(cDim), er.getLowOrdinate(cDim)));
                r.setHighOrdinate(cDim, Math.max(r.getHighOrdinate(cDim), er.getHighOrdinate(cDim)));
            }
        }
        return r;
    }

    @Override
    public Shape newShape() {
        RegionShape r = ShapeSuits.createRegion(this.tree.dimension);
        r.makeInfinite(this.tree.dimension);
        return r;
    }
}
