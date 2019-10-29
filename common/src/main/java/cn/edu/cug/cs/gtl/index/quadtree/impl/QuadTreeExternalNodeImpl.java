package cn.edu.cug.cs.gtl.index.quadtree.impl;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.index.shape.Shape;

public class QuadTreeExternalNodeImpl extends QuadTreeNodeImpl {
    private static final long serialVersionUID = 1L;

    public QuadTreeExternalNodeImpl(Identifier identifier, int capacity, QuadTreeImpl tree, Shape shape) {
        super(identifier, 0, capacity, tree, shape);
        this.type = 0;
    }

    public QuadTreeExternalNodeImpl(int capacity, QuadTreeImpl tree, Shape shape) {
        super(Identifier.create(), 0, capacity, tree, shape);
        this.type = 0;
    }

    public QuadTreeExternalNodeImpl() {
        super(Identifier.create(), 0, 64, null, null);
        this.type = 0;
    }

    @Override
    public Object clone() {
        return new QuadTreeExternalNodeImpl(this.identifier, this.capacity, this.getTree(), (Shape) this.shape.clone());
    }

}
