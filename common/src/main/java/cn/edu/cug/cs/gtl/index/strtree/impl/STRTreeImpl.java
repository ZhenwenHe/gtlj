package cn.edu.cug.cs.gtl.index.strtree.impl;


import cn.edu.cug.cs.gtl.index.*;
import cn.edu.cug.cs.gtl.index.strtree.AbstractVisitor;
import cn.edu.cug.cs.gtl.index.strtree.STRTree;
import cn.edu.cug.cs.gtl.jts.JTSWrapper;
import cn.edu.cug.cs.gtl.jts.geom.Envelope;
import cn.edu.cug.cs.gtl.jts.index.ItemVisitor;
import cn.edu.cug.cs.gtl.jts.index.strtree.*;
import cn.edu.cug.cs.gtl.jts.util.PriorityQueue;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.exception.UnimplementedException;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.index.knn.NearestNeighborComparator;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class STRTreeImpl extends STRtree implements STRTree {
    private static final long serialVersionUID = 1L;


    transient ArrayList<Pair<Command, CommandType>> commands = null;

    public STRTreeImpl() {
    }

    public STRTreeImpl(int nodeCapacity) {
        super(nodeCapacity);
    }

    /**
     * Inserts an item having the given bounds into the tree.
     */
    public synchronized void insert(cn.edu.cug.cs.gtl.geom.Envelope itemEnv, Object item) {
        super.insert(JTSWrapper.toJTSEnvelope(itemEnv), item);
    }

    /**
     * Returns items whose bounds intersect the given envelope.
     * item type maybe
     * Entry  -- inserted by the function insert(byte[] , Shape shape , Identifier id)
     * Object  -- insert(Envelope itemEnv, Object item)
     * Geometry -- insert(Geometry)
     *
     * @param searchEnv
     * @return
     */

    public List<Object> query(cn.edu.cug.cs.gtl.geom.Envelope searchEnv) {
        return super.query(JTSWrapper.toJTSEnvelope(searchEnv));
    }

    public void query(cn.edu.cug.cs.gtl.geom.Envelope searchEnv, AbstractVisitor visitor) {
        super.query(JTSWrapper.toJTSEnvelope(searchEnv), (ItemVisitor) visitor);
    }

    public List<cn.edu.cug.cs.gtl.geom.Envelope> getLeafNodeEnvelopes() {
        List list = queryBoundary();
        if (list == null) return null;
        List<cn.edu.cug.cs.gtl.geom.Envelope> results = new ArrayList<>(list.size());
        for (Object o : list) {
            results.add(JTSWrapper.toGTLEnvelope((Envelope) o));
        }
        return results;
    }

    /**
     * @param pData
     * @param shape
     * @param shapeIdentifier
     */
    @Override
    public void insert(byte[] pData, Shape shape, Identifier shapeIdentifier) {
        this.insert(shape.getMBR(), Entry.create(shapeIdentifier, shape, pData));
    }

    /**
     * Removes a single item from the tree.
     *
     * @param shape
     * @param shapeIdentifier
     * @return
     */
    @Override
    public boolean delete(Shape shape, Identifier shapeIdentifier) {
        return this.remove(JTSWrapper.toJTSEnvelope(shape.getMBR()), Entry.create(shapeIdentifier, shape, null));
    }

    @Override
    public void contains(Shape query, Visitor v) {
        intersects(query, v);
    }

    @Override
    public void intersects(Shape query, Visitor v) {
        if (v instanceof AbstractVisitor) {
            AbstractVisitor dv = (AbstractVisitor) v;
            this.query(query.getMBR(), dv);
        } else {
            try {
                throw new IllegalArgumentException("intersects:Visitor should be a subclass of AbstractVisitor");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pointLocation(PointShape query, Visitor v) {
        if (v instanceof AbstractVisitor) {
            AbstractVisitor dv = (AbstractVisitor) v;
            this.query(query.getMBR(), dv);
        } else {
            try {
                throw new IllegalArgumentException("pointLocation:Visitor should be a subclass of AbstractVisitor");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void nearestNeighbor(int k, Shape query, Visitor v, NearestNeighborComparator nnc) {
        try {
            if (v instanceof AbstractVisitor) {
                AbstractVisitor dv = (AbstractVisitor) v;
                throw new UnimplementedException("STRTreeImpl:nearestNeighbor(int k, Shape query, Visitor v, NearestNeighborComparator nnc)");
            } else {
                throw new IllegalArgumentException("nearestNeighbor:Visitor should be a subclass of AbstractVisitor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nearestNeighbor(int k, Shape query, Visitor v) {
        try {
            if (v instanceof AbstractVisitor) {
                AbstractVisitor dv = (AbstractVisitor) v;
                throw new UnimplementedException("STRTreeImpl:nearestNeighbor(int k, Shape query, Visitor v)");
            } else {
                throw new IllegalArgumentException("nearestNeighbor:Visitor should be a subclass of AbstractVisitor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selfJoin(Shape s, Visitor v) {
        try {
            if (v instanceof AbstractVisitor) {
                AbstractVisitor dv = (AbstractVisitor) v;
                throw new UnimplementedException("selfJoin(Shape s, Visitor v)");
            } else {
                throw new IllegalArgumentException("nearestNeighbor:Visitor should be a subclass of AbstractVisitor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void queryStrategy(QueryStrategy qs) {
        try {
            throw new UnimplementedException("queryStrategy(QueryStrategy qs)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PropertySet getProperties() {
        PropertySet ps = PropertySet.create();//new PropertySetImpl();

        // dimension
        ps.put("Dimension", new Variant(2));

        return ps;
    }


    @Override
    public void addCommand(Command in, CommandType ct) {
        if (this.commands == null)
            this.commands = new ArrayList<>();
        this.commands.add(new Pair<Command, CommandType>(in, ct));
    }


    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public void insert(Geometry g) {
        insert(g.getEnvelope(), g);
    }

    @Override
    public Collection<Geometry> intersects(Shape query) {
        List<Object> ls = this.query(query.getMBR());
        if (ls.get(0) instanceof Geometry) {
            ArrayList<Geometry> al = new ArrayList<>(ls.size());
            for (Object o : ls)
                al.add((Geometry) o);

            return al;
        }
        return null;
    }


    @Override
    public Collection<Geometry> contains(Shape query) {
        return intersects(query);
    }

    /**
     * This function traverses the boundaries of all leaf nodes.
     * This function should be called after all insertions.
     *
     * @return The list of lea nodes boundaries
     */
    public List queryBoundary() {
        build();
        List boundaries = new ArrayList();
        if (isEmpty()) {
            //Assert.isTrue(root.getBounds() == null);
            //If the root is empty, we stop traversing. This should not happen.
            return boundaries;
        }

        queryBoundary(root, boundaries);

        return boundaries;
    }

    /**
     * This function is to traverse the children of the root.
     *
     * @param node
     * @param boundaries
     */
    private void queryBoundary(AbstractNode node, List boundaries) {
        List childBoundables = node.getChildBoundables();
        boolean flagLeafnode = true;
        for (int i = 0; i < childBoundables.size(); i++) {
            Boundable childBoundable = (Boundable) childBoundables.get(i);
            if (childBoundable instanceof AbstractNode) {
                //We find this is not a leaf node.
                flagLeafnode = false;
                break;

            }
        }
        if (flagLeafnode == true) {
            boundaries.add((Envelope) node.getBounds());
            return;
        } else {
            for (int i = 0; i < childBoundables.size(); i++) {
                Boundable childBoundable = (Boundable) childBoundables.get(i);
                if (childBoundable instanceof AbstractNode) {
                    queryBoundary((AbstractNode) childBoundable, boundaries);
                }

            }
        }
    }

    public AbstractNode createNode(int level) {
        return super.createNode(level);
    }

    /**
     * Finds the item in this tree which is nearest to the given {@link Object},
     * using {@link ItemDistance} as the distance metric.
     * A Branch-and-Bound tree traversal algorithm is used
     * to provide an efficient search.
     * <p>
     * The query <tt>object</tt> does <b>not</b> have to be
     * contained in the tree, but it does
     * have to be compatible with the <tt>itemDist</tt>
     * distance metric.
     *
     * @param env      the envelope of the query item
     * @param item     the item to find the nearest neighbour of
     * @param itemDist a distance metric applicable to the items in this tree and the query item
     * @param k        the K nearest items in KNN
     * @return the K nearest items in this tree
     */
    public Object[] nearestNeighbour(Envelope env, Object item, ItemDistance itemDist, int k) {
        Boundable bnd = new ItemBoundable(env, item);
        cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair bp = new cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair(this.getRoot(), bnd, itemDist);
        return nearestNeighbour(bp, k);
    }

    public Object[] nearestNeighbour(cn.edu.cug.cs.gtl.geom.Envelope env, Object item, ItemDistance itemDist, int k) {

        Boundable bnd = new ItemBoundable(JTSWrapper.toJTSEnvelope(env), item);
        cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair bp = new cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair(this.getRoot(), bnd, itemDist);
        return nearestNeighbour(bp, k);
    }

    protected Object[] nearestNeighbour(cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair initBndPair, int k) {
        return nearestNeighbour(initBndPair, Double.POSITIVE_INFINITY, k);
    }

    protected Object[] nearestNeighbour(cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair initBndPair, double maxDistance, int k) {
        /*
         * This method implements the KNN algorithm described in the following paper:
         * Roussopoulos, Nick, Stephen Kelley, and Frédéric Vincent. "Nearest neighbor queries." ACM sigmod record. Vol. 24. No. 2. ACM, 1995.
         * We only use the minDistance and ignore minmaxDistance.
         */

        double distanceLowerBound = maxDistance;

        // initialize internal structures
        PriorityQueue priQ = new PriorityQueue();

        // initialize queue
        priQ.add(initBndPair);

        java.util.PriorityQueue<cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair> kNearestNeighbors = new java.util.PriorityQueue<cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair>(k, new BoundablePairComparator(false));

        while (!priQ.isEmpty() && distanceLowerBound >= 0.0) {
            // pop head of queue and expand one side of pair
            cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair bndPair = (cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair) priQ.poll();
            double currentDistance = bndPair.getDistance();


            /**
             * If the distance for the first node in the queue
             * is >= the current maximum distance in the k queue , all other nodes
             * in the queue must also have a greater distance.
             * So the current minDistance must be the true minimum,
             * and we are done.
             */


            if (currentDistance >= distanceLowerBound) {
                break;
            }
            /**
             * If the pair members are leaves
             * then their distance is the exact lower bound.
             * Update the distanceLowerBound to reflect this
             * (which must be smaller, due to the test
             * immediately prior to this).
             */
            if (bndPair.isLeaves()) {
                // assert: currentDistance < minimumDistanceFound

                if (kNearestNeighbors.size() < k) {
                    kNearestNeighbors.add(bndPair);
                } else {

                    if (kNearestNeighbors.peek().getDistance() > currentDistance) {
                        kNearestNeighbors.poll();
                        kNearestNeighbors.add(bndPair);
                    }
                    /*
                     * minDistance should be the farthest point in the K nearest neighbor queue.
                     */
                    distanceLowerBound = kNearestNeighbors.peek().getDistance();

                }
            } else {
                // testing - does allowing a tolerance improve speed?
                // Ans: by only about 10% - not enough to matter
        /*
        double maxDist = bndPair.getMaximumDistance();
        if (maxDist * .99 < lastComputedDistance)
          return;
        //*/

                /**
                 * Otherwise, expand one side of the pair,
                 * (the choice of which side to expand is heuristically determined)
                 * and insert the new expanded pairs into the queue
                 */
                bndPair.expandToQueue(priQ, distanceLowerBound);
            }
        }
        // done - return items with min distance

        Object[] result = new Object[kNearestNeighbors.size()];
        Iterator<cn.edu.cug.cs.gtl.index.strtree.impl.BoundablePair> resultIterator = kNearestNeighbors.iterator();
        int count = 0;
        while (resultIterator.hasNext()) {
            result[count] = ((ItemBoundable) resultIterator.next().getBoundable(0)).getItem();
            count++;
        }
        return result;
    }


}
