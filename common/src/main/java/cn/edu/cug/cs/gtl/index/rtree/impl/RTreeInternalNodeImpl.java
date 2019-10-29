package cn.edu.cug.cs.gtl.index.rtree.impl;

import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.Entry;
import cn.edu.cug.cs.gtl.index.Node;
import cn.edu.cug.cs.gtl.index.EntryImpl;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.math.MathSuits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;

/**
 * Created by ZhenwenHe on 2017/2/13.
 */
public class RTreeInternalNodeImpl extends RTreeNodeImpl {
    private static final long serialVersionUID = 1L;


    public RTreeInternalNodeImpl(RTreeImpl tree, Identifier identifier, int level) {
        super(tree, identifier, level, tree.indexCapacity);
        setType(1);
    }

    public RTreeInternalNodeImpl(RTreeImpl tree) {
        super(tree);
        setType(1);
    }

    @Override
    public Object clone() {
        RTreeInternalNodeImpl n = new RTreeInternalNodeImpl(tree);
        n.copyFrom(this);
        return n;
    }

    @Override
    protected Node chooseSubtree(RegionShape mbr, int insertionLevel, Stack<Identifier> pathBuffer) {
        int level = getLevel();
        if (level == insertionLevel)
            return this;

        pathBuffer.push(getIdentifier());

        int child = 0;

        switch (tree.treeVariant) {
            case RV_LINEAR:
            case RV_QUADRATIC:
                child = findLeastEnlargement(mbr);
                break;
            case RV_RSTAR:
                if (level == 1) {
                    // if this node points to leaves...
                    child = findLeastOverlap(mbr);
                } else {
                    child = findLeastEnlargement(mbr);
                }
                break;
            default:
                assert false;
        }
        assert (child != Integer.MAX_VALUE);

        Node n = tree.readNode(getChildIdentifier(child));
        Node ret = ((RTreeNodeImpl) n).chooseSubtree(mbr, insertionLevel, pathBuffer);
        return ret;
    }

    @Override
    protected Node findLeaf(RegionShape mbr, Identifier id, Stack<Identifier> pathBuffer) {
        int children = getChildrenCount();
        RegionShape r = null;
        pathBuffer.push(getIdentifier());

        for (int cChild = 0; cChild < children; ++cChild) {
            r = (RegionShape) getChildShape(cChild);
            if (r.containsRegion(mbr)) {
                Node n = tree.readNode(getChildIdentifier(cChild));
                Node l = ((RTreeNodeImpl) n).findLeaf(mbr, id, pathBuffer);
                //if (n.get() == l.get()) n.relinquish();
                if (l != null) return l;
            }
        }

        pathBuffer.pop();

        return null;
    }

    @Override
    protected Node[] split(Entry e) {

        tree.stats.increaseSplitTimes();

        ArrayList<Integer> g1 = new ArrayList<>();
        ArrayList<Integer> g2 = new ArrayList<>();

        switch (tree.treeVariant) {
            case RV_LINEAR:
            case RV_QUADRATIC:
                rtreeSplit(e, g1, g2);
                break;
            case RV_RSTAR:
                rstarSplit(e, g1, g2);
                break;
            default:
                return null;
        }

        Node ptrLeft = new RTreeInternalNodeImpl(tree, getIdentifier(), getLevel());
        Node ptrRight = new RTreeInternalNodeImpl(tree, Identifier.create(-1L), getLevel());

        ptrLeft.setShape(tree.infiniteRegionShape);
        ptrRight.setShape(tree.infiniteRegionShape);

        int cIndex;

        Entry te = null;
        for (cIndex = 0; cIndex < g1.size(); ++cIndex) {
            te = new EntryImpl(getChildIdentifier(g1.get(cIndex)), getChildShape(g1.get(cIndex)), null);
            ptrLeft.insertEntry(te);
        }

        for (cIndex = 0; cIndex < g2.size(); ++cIndex) {
            te = new EntryImpl(getChildIdentifier(g2.get(cIndex)), getChildShape(g2.get(cIndex)), null);
            ptrRight.insertEntry(te);
        }

        Node[] retNodes = new Node[2];
        retNodes[0] = ptrLeft;
        retNodes[1] = ptrRight;
        return retNodes;
    }

    public void adjustTree(Node n, Stack<Identifier> pathBuffer) {
        tree.stats.increaseAdjustments();
        int children = getChildrenCount();
        // find entry pointing to old node;
        int child;
        for (child = 0; child < children; ++child) {
            if (n.getIdentifier().equals(getChildIdentifier(child))) break;
        }

        // MBR needs recalculation if either:
        //   1. the NEW child MBR is not contained.
        //   2. the OLD child MBR is touching.
        RegionShape r = (RegionShape) getShape();
        RegionShape nr = (RegionShape) n.getShape();
        RegionShape cr = (RegionShape) getChildShape(child);
        boolean bContained = r.containsRegion(nr);
        boolean bTouches = r.touchesRegion(cr);
        boolean bRecompute = (!bContained || (bTouches && tree.tightMBRs));

        //*(m_ptrMBR[child]) = n->m_nodeMBR;
        cr.copyFrom(nr);

        if (bRecompute) {
            recalculateShape();
        }

        tree.writeNode(this);

        if (bRecompute && (!pathBuffer.empty())) {
            Identifier cParent = pathBuffer.pop();
            Node ptrN = tree.readNode(cParent);
            RTreeInternalNodeImpl p = (RTreeInternalNodeImpl) (ptrN);
            p.adjustTree(this, pathBuffer);
        }

    }

    public void adjustTree(Node n1, Node n2, Stack<Identifier> pathBuffer, byte[] overflowTable) {
        tree.stats.increaseAdjustments();
        int children = getChildrenCount();
        // find entry pointing to old node;
        int child;
        for (child = 0; child < children; ++child) {
            if (n1.getIdentifier().equals(getChildIdentifier(child))) break;
        }
        RegionShape r = (RegionShape) getShape();
        RegionShape r1 = (RegionShape) n1.getShape();
        RegionShape r2 = (RegionShape) n2.getShape();
        RegionShape cr = (RegionShape) getChildShape(child);
        // MBR needs recalculation if either:
        //   1. the NEW child MBR is not contained.
        //   2. the OLD child MBR is touching.
        boolean bContained = r.containsRegion(r1);
        boolean bTouches = r.touchesRegion(cr);
        boolean bRecompute = (!bContained || (bTouches && tree.tightMBRs));

        //*(m_ptrMBR[child]) = n1->m_nodeMBR;
        cr.copyFrom(r1);

        if (bRecompute) {
            recalculateShape();
        }

        // No write necessary here. insertData will write the node if needed.
        //tree.writeNode(this);
        Entry e = new EntryImpl(n2.getIdentifier(), n2.getShape(), null);
        boolean bAdjusted = insertData(e, pathBuffer, overflowTable);

        // if n2 is contained in the node and there was no split or reinsert,
        // we need to adjust only if recalculation took place.
        // In all other cases insertData above took care of adjustment.
        if ((!bAdjusted) && bRecompute && (!pathBuffer.empty())) {
            Identifier cParent = pathBuffer.pop();
            Node ptrN = tree.readNode(cParent);
            RTreeInternalNodeImpl p = (RTreeInternalNodeImpl) ptrN;
            p.adjustTree(this, pathBuffer);
        }
    }

    protected int findLeastEnlargement(RegionShape r) {

        double area = Double.MAX_VALUE;
        int best = Integer.MAX_VALUE;
        int children = getChildrenCount();
        RegionShape t = null;
        RegionShape cr = null;
        for (int cChild = 0; cChild < children; ++cChild) {
            cr = (RegionShape) getChildShape(cChild);
            t = cr.getCombinedRegion(r);

            double a = cr.getArea();
            double enl = t.getArea() - a;

            if (enl < area) {
                area = enl;
                best = cChild;
            } else if (enl == area) {
                // this will rarely happen, so compute best area on the fly only when necessary.
                t = (RegionShape) getChildShape(best);
                if (a < t.getArea()) best = cChild;
            }
        }

        return best;
    }

    protected int findLeastOverlap(RegionShape r) {

        int children = getChildrenCount();

        double leastOverlap = Double.MAX_VALUE;
        double me = Double.MAX_VALUE;
        OverlapEntry best = null;
        OverlapEntry[] overlapEntries = new OverlapEntry[children];
        RegionShape cr = null;
        // find combined regionShape and enlargement of every entry and store it.
        for (int cChild = 0; cChild < children; ++cChild) {

            overlapEntries[cChild] = new OverlapEntry();
            cr = (RegionShape) getChildShape(cChild);
            overlapEntries[cChild].m_index = cChild;
            overlapEntries[cChild].m_original = (RegionShape) cr.clone();//m_ptrMBR[cChild];
            overlapEntries[cChild].m_combined = cr.getCombinedRegion(r);
            overlapEntries[cChild].m_oa = overlapEntries[cChild].m_original.getArea();
            overlapEntries[cChild].m_ca = overlapEntries[cChild].m_combined.getArea();
            overlapEntries[cChild].m_enlargement = overlapEntries[cChild].m_ca - overlapEntries[cChild].m_oa;

            if (overlapEntries[cChild].m_enlargement < me) {
                me = overlapEntries[cChild].m_enlargement;
                best = overlapEntries[cChild];
            } else if (overlapEntries[cChild].m_enlargement == me && overlapEntries[cChild].m_oa < best.m_oa) {
                best = overlapEntries[cChild];
            }
        }

        if (me < -MathSuits.EPSILON || me > MathSuits.EPSILON) {
            int cIterations;

            if (children > tree.nearMinimumOverlapFactor) {
                // sort overlapEntries in increasing order of enlargement.
                //::qsort(overlapEntries, children,
                //    sizeof(OverlapEntry*),
                //    OverlapEntry::compareEntries);
                Arrays.sort(overlapEntries);
                assert (overlapEntries[0].m_enlargement <= overlapEntries[children - 1].m_enlargement);

                cIterations = tree.nearMinimumOverlapFactor;
            } else {
                cIterations = children;
            }

            // calculate overlap of most important original overlapEntries (near minimum overlap cost).
            for (int cIndex = 0; cIndex < cIterations; ++cIndex) {
                double dif = 0.0;
                OverlapEntry e = overlapEntries[cIndex];

                for (int cChild = 0; cChild < children; ++cChild) {
                    if (e.m_index != cChild) {
                        cr = (RegionShape) getChildShape(cChild);
                        double f = e.m_combined.getIntersectingArea(cr);
                        if (f != 0.0) dif += f - e.m_original.getIntersectingArea(cr);
                    }
                } // for (cChild)

                if (dif < leastOverlap) {
                    leastOverlap = dif;
                    best = overlapEntries[cIndex];
                } else if (dif == leastOverlap) {
                    if (e.m_enlargement == best.m_enlargement) {
                        // keep the one with least area.
                        if (e.m_original.getArea() < best.m_original.getArea()) best = overlapEntries[cIndex];
                    } else {
                        // keep the one with least enlargement.
                        if (e.m_enlargement < best.m_enlargement) best = overlapEntries[cIndex];
                    }
                }
            } // for (cIndex)
        }

        int ret = best.m_index;
        return ret;
    }


    class OverlapEntry implements Comparator<OverlapEntry>, Serializable {
        private static final long serialVersionUID = 1L;

        public int m_index;
        public double m_enlargement;
        public RegionShape m_original;
        public RegionShape m_combined;
        public double m_oa;
        public double m_ca;

        @Override
        public int compare(OverlapEntry pe1, OverlapEntry pe2) {
            if (pe1.m_enlargement < pe2.m_enlargement) return -1;
            if (pe1.m_enlargement > pe2.m_enlargement) return 1;
            return 0;
        }
    }

    ; // OverlapEntry

}
