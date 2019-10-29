package cn.edu.cug.cs.gtl.index.quadtree.impl;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;

public class QuadTreeInternalNodeImpl extends QuadTreeNodeImpl {
    private static final long serialVersionUID = 1L;

    public QuadTreeInternalNodeImpl(Identifier identifier, QuadTreeImpl tree, Shape shape) {
        super(Identifier.create(identifier), 1, 4, tree, shape);
        this.type = 1;
    }

    public QuadTreeInternalNodeImpl(QuadTreeImpl tree, Shape shape) {
        super(Identifier.create(), 1, 4, tree, shape);
        this.type = 1;
    }

    public QuadTreeInternalNodeImpl() {
        super(Identifier.create(), 1, 4, null, null);
        this.type = 1;
    }

    @Override
    public Object clone() {
        return new QuadTreeInternalNodeImpl(identifier, tree, this.shape);
    }
}
