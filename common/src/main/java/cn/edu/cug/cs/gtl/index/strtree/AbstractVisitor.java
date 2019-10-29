package cn.edu.cug.cs.gtl.index.strtree;

import cn.edu.cug.cs.gtl.jts.index.ItemVisitor;
import cn.edu.cug.cs.gtl.jts.index.ItemVisitor;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.index.Entry;
import cn.edu.cug.cs.gtl.index.Node;
import cn.edu.cug.cs.gtl.index.Visitor;

import java.io.Serializable;

public abstract class AbstractVisitor implements Visitor, ItemVisitor, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public abstract void visitData(Entry in);

    public abstract void visitObject(Object in);

    @Override
    public void visitData(Entry[] ev) {
        for (Entry e : ev) {
            visitData(e);
        }
    }

    @Override
    public void visitItem(Object item) {
        if (item instanceof Entry)
            visitData((Entry) item);
        else if (item instanceof Geometry) {
            visitGeometry((Geometry) item);
        } else {
            visitObject(item);
        }
    }

    @Override
    public void visitNode(Node in) {

    }
}