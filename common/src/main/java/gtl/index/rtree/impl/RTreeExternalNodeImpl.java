package gtl.index.rtree.impl;

import gtl.common.Identifier;
import gtl.index.Entry;
import gtl.index.Node;
import gtl.index.shape.RegionShape;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by ZhenwenHe on 2017/2/13.
 */
public class RTreeExternalNodeImpl extends RTreeNodeImpl {
    public RTreeExternalNodeImpl(RTreeImpl tree, Identifier identifier) {
        super(tree, identifier, 0, tree.leafCapacity);
        setType(0);
    }

    public RTreeExternalNodeImpl(RTreeImpl tree) {
        super(tree);
        setType(0);
    }

    @Override
    public Object clone() {
        RTreeExternalNodeImpl r = new RTreeExternalNodeImpl(tree);
        r.copyFrom(this);
        return r;
    }

    @Override
    protected Node chooseSubtree(RegionShape mbr, int level, Stack<Identifier> pathBuffer) {
        return this;
    }

    @Override
    protected Node findLeaf(RegionShape mbr, Identifier id, Stack<Identifier> pathBuffer) {
        int childCount = getChildrenCount();
        for (int cChild = 0; cChild < childCount; ++cChild) {
            if (getChildIdentifier(cChild).equals(id) && getChildShape(cChild).equals(mbr))
                return this;
        }
        return null;
    }

    @Override
    protected Node[] split(Entry e) {
        tree.stats.setSplitTimes(tree.stats.getSplitTimes() + 1);
        ArrayList<Integer> g1 = new ArrayList<Integer>();
        ArrayList<Integer> g2 = new ArrayList<Integer>();
        switch (tree.treeVariant) {
            case RV_LINEAR:
            case RV_QUADRATIC:
                rtreeSplit(e, g1, g2);
                break;
            case RV_RSTAR:
                rstarSplit(e, g1, g2);
                break;
            default:
                assert false;
        }

        Node[] nodes = new Node[2];
        nodes[0] = (Node) new RTreeExternalNodeImpl(this.tree, Identifier.create(-1L));
        nodes[1] = (Node) new RTreeExternalNodeImpl(this.tree, Identifier.create(-1L));
        RTreeExternalNodeImpl pLeft = (RTreeExternalNodeImpl) (nodes[0]);
        RTreeExternalNodeImpl pRight = (RTreeExternalNodeImpl) (nodes[1]);

        pLeft.getShape().copyFrom(tree.infiniteRegionShape);
        pRight.getShape().copyFrom(tree.infiniteRegionShape);

        Entry[] entries = getChildEntries();

        int cIndex;
        int tIndex;
        for (cIndex = 0; cIndex < g1.size(); ++cIndex) {
            tIndex = g1.get(cIndex);
            pLeft.insertEntry(entries[tIndex]);
        }

        for (cIndex = 0; cIndex < g2.size(); ++cIndex) {
            tIndex = g2.get(cIndex);
            pRight.insertEntry(entries[tIndex]);
        }
        return nodes;
    }

    protected void deleteData(Identifier id, Stack<Identifier> pathBuffer) {
        int child;
        int children = getChildrenCount();
        for (child = 0; child < children; ++child) {
            if (id.equals(getChildIdentifier(child))) break;
        }
        deleteEntry(child);
        tree.writeNode(this);

        Stack<Node> toReinsert = new Stack<>();
        condenseTree(toReinsert, pathBuffer, this);


        // re-insert eliminated nodes.
        while (!toReinsert.empty()) {
            Node n = toReinsert.pop();
            tree.deleteNode(n);
            // warning : the value has been changed
            children = n.getChildrenCount();
            for (int cChild = 0; cChild < children; ++cChild) {
                // keep this in the for loop. The tree height might change after insertions.
                byte[] overflowTable = new byte[(int) tree.stats.getTreeHeight()];
                for (int i = 0; i < overflowTable.length; i++)
                    overflowTable[i] = 0;
                tree.insertData_impl(n.getChildData(cChild),
                        (RegionShape) n.getChildShape(cChild),
                        n.getChildIdentifier(cChild),
                        n.getLevel(), overflowTable);
                n.setChildData(cChild, null);
            }
        }
    }


}
