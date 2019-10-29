package cn.edu.cug.cs.gtl.index.kdtree.impl;


import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.OrdinateComparator;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.index.kdtree.KDTree;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * A k-d tree (short for k-dimensional tree) is a space-partitioning data
 * structure for organizing points in a k-dimensional space. k-d trees are a
 * useful data structure for several applications, such as searches involving a
 * multidimensional search key (e.g. range searches and nearest neighbor
 * searches). k-d trees are a special case of binary space partitioning trees.
 */
public class KDTreeImpl<T extends Vector> implements KDTree<T>, Iterable<T>, Serializable {
    private static final long serialVersionUID = 1L;

    private int k = 2;
    private KDNode root = null;
    private Envelope totalExtent;

    /**
     * Default constructor.
     */
    private KDTreeImpl() {
        k = 2;
        root = null;
        totalExtent = null;
    }

    /**
     * Constructor for creating a more balanced tree. It uses the
     * "median of points" algorithm.
     *
     * @param list of Vector.
     */
    private KDTreeImpl(List<Vector> list) {
        super();
        if (list == null || list.isEmpty())
            root = null;
        else {
            this.k = list.get(0).getDimension();
            root = createNode(list, k, 0);
        }
        totalExtent = null;
    }

    /**
     * Constructor for creating a more balanced tree. It uses the
     * "median of points" algorithm.
     *
     * @param list of Vector.
     */
    public KDTreeImpl(Envelope envelope, List<Vector> list) {
        super();
        this.totalExtent = (Envelope) envelope.clone();
        if (list == null || list.isEmpty())
            root = null;
        else {
            this.k = list.get(0).getDimension();
            root = createNode(list, k, 0);
        }
    }

    /**
     * Constructor for creating a more balanced tree. It uses the
     * "median of points" algorithm.
     *
     * @param list of Vector.
     * @param k    of the tree.
     */
    public KDTreeImpl(List<Vector> list, int k) {
        super();

        if (list == null || list.isEmpty())
            root = null;
        else {
            this.k = list.get(0).getDimension();
            //assert k<=this.k;
            this.k = Math.min(k, this.k);
            root = createNode(list, this.k, 0);
        }
    }

    /**
     * Creates node from list of Vector.
     *
     * @param list  of Vector.
     * @param k     of the tree.
     * @param depth depth of the node.
     * @return node created.
     */
    private static KDNode createNode(List<Vector> list, int k, int depth) {
        if (list == null || list.size() == 0)
            return null;


        int axis = depth % k;
//        if (axis == OrdinateComparator.X_AXIS)
//            Collections.sort(list, OrdinateComparator.X_COMPARATOR);
//        else if (axis == OrdinateComparator.Y_AXIS)
//            Collections.sort(list, OrdinateComparator.Y_COMPARATOR);
//        else
//            Collections.sort(list, OrdinateComparator.Z_COMPARATOR);
        Collections.sort(list, new OrdinateComparator<Vector>(axis));

        KDNode node = null;
        List<Vector> less = new ArrayList<Vector>(list.size());
        List<Vector> more = new ArrayList<Vector>(list.size());
        if (list.size() > 0) {
            int medianIndex = list.size() / 2;
            node = new KDNode(list.get(medianIndex), k, depth);
            // Process list to see where each non-median point lies
            for (int i = 0; i < list.size(); i++) {
                if (i == medianIndex)
                    continue;
                Vector p = list.get(i);
                // Cannot assume points before the median are less since they could be equal
                if (KDNode.compareTo(depth, k, p, node.id) <= 0) {
                    less.add(p);
                } else {
                    more.add(p);
                }
            }

            if ((medianIndex - 1 >= 0) && less.size() > 0) {
                node.lesser = createNode(less, k, depth + 1);
                node.lesser.parent = node;
            }

            if ((medianIndex <= list.size() - 1) && more.size() > 0) {
                node.greater = createNode(more, k, depth + 1);
                node.greater.parent = node;
            }
        }

        return node;
    }

    /**
     * Adds value to the tree. Tree can contain multiple equal values.
     *
     * @param value T to add to the tree.
     * @return True if successfully added to tree.
     */
    public boolean add(T value) {
        if (value == null)
            return false;

        if (root == null) {
            root = new KDNode(value);
            return true;
        }

        KDNode node = root;
        while (true) {
            if (KDNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    KDNode newNode = new KDNode(value, k, node.depth + 1);
                    newNode.parent = node;
                    node.lesser = newNode;
                    break;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    KDNode newNode = new KDNode(value, k, node.depth + 1);
                    newNode.parent = node;
                    node.greater = newNode;
                    break;
                }
                node = node.greater;
            }
        }

        return true;
    }


    @Override
    public List<Envelope> getLeafNodeEnvelopes() {
        if (this.totalExtent == null)
            return null;
        else
            return (List<Envelope>) partitionEnvelope(this.totalExtent);
    }

    public Collection<Envelope> partitionEnvelope(Envelope envelope) {
        ArrayList<Envelope> envelopes = new ArrayList<>();
        partitionEnvelope(root, envelope, envelopes);
        return envelopes;
    }

    private void partitionEnvelope(KDNode node, Envelope e, Collection<Envelope> envelopes) {
        if (node != null) {
            Envelope[] results = e.split(node.id, node.depth % k);
            if (node.lesser == null && node.greater == null) {
                envelopes.add(results[0]);
                envelopes.add(results[1]);
            } else {
                partitionEnvelope(node.greater, results[1], envelopes);
                partitionEnvelope(node.lesser, results[0], envelopes);
            }
        }
    }

    @Override
    public boolean insert(T v) {
        return add((T) v);
    }

    /**
     * Does the tree contain the value.
     *
     * @param value T to locate in the tree.
     * @return True if tree contains value.
     */
    @Override
    public boolean contains(T value) {
        if (value == null || root == null)
            return false;

        KDNode node = getNode(this, (T) value);
        return (node != null);
    }

    /**
     * Locates T in the tree.
     *
     * @param tree  to search.
     * @param value to search for.
     * @return KDNode or NULL if not found
     */
    private static final <T extends Vector> KDNode getNode(KDTreeImpl<T> tree, T value) {
        if (tree == null || tree.root == null || value == null)
            return null;

        KDNode node = tree.root;
        while (true) {
            if (node.id.equals(value)) {
                return node;
            } else if (KDNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                if (node.lesser == null) {
                    return null;
                }
                node = node.lesser;
            } else {
                // Greater
                if (node.greater == null) {
                    return null;
                }
                node = node.greater;
            }
        }
    }

    /**
     * Removes first occurrence of value in the tree.
     *
     * @param value T to remove from the tree.
     * @return True if value was removed from the tree.
     */
    @Override
    public boolean remove(T value) {
        if (value == null || root == null)
            return false;

        KDNode node = getNode(this, (T) value);
        if (node == null)
            return false;

        KDNode parent = node.parent;
        if (parent != null) {
            if (parent.lesser != null && node.equals(parent.lesser)) {
                List<Vector> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.lesser = createNode(nodes, node.k, node.depth);
                    if (parent.lesser != null) {
                        parent.lesser.parent = parent;
                    }
                } else {
                    parent.lesser = null;
                }
            } else {
                List<Vector> nodes = getTree(node);
                if (nodes.size() > 0) {
                    parent.greater = createNode(nodes, node.k, node.depth);
                    if (parent.greater != null) {
                        parent.greater.parent = parent;
                    }
                } else {
                    parent.greater = null;
                }
            }
        } else {
            // root
            List<Vector> nodes = getTree(node);
            if (nodes.size() > 0)
                root = createNode(nodes, node.k, node.depth);
            else
                root = null;
        }

        return true;
    }

    /**
     * Gets the (sub) tree rooted at root.
     *
     * @param root of tree to get nodes for.
     * @return points in (sub) tree, not including root.
     */
    private static final List<Vector> getTree(KDNode root) {
        List<Vector> list = new ArrayList<Vector>();
        if (root == null)
            return list;

        if (root.lesser != null) {
            list.add(root.lesser.id);
            list.addAll(getTree(root.lesser));
        }
        if (root.greater != null) {
            list.add(root.greater.id);
            list.addAll(getTree(root.greater));
        }

        return list;
    }

    /**
     * Searches the K nearest neighbor.
     *
     * @param K     Number of neighbors to retrieve. Can return more than K, if
     *              last nodes are equal distances.
     * @param value to find neighbors of.
     * @return Collection of T neighbors.
     */
    @SuppressWarnings("unchecked")
    public Collection<T> nearestNeighbourSearch(int K, T value) {
        if (value == null || root == null)
            return Collections.EMPTY_LIST;

        // Map used for results
        TreeSet<KDNode> results = new TreeSet<KDNode>(new EuclideanComparator(value));

        // Find the closest leaf node
        KDNode prev = null;
        KDNode node = root;
        while (node != null) {
            if (KDNode.compareTo(node.depth, node.k, value, node.id) <= 0) {
                // Lesser
                prev = node;
                node = node.lesser;
            } else {
                // Greater
                prev = node;
                node = node.greater;
            }
        }
        KDNode leaf = prev;

        if (leaf != null) {
            // Used to not re-examine nodes
            Set<KDNode> examined = new HashSet<KDNode>();

            // Go up the tree, looking for better solutions
            node = leaf;
            while (node != null) {
                // Search node
                searchNode(value, node, K, results, examined);
                node = node.parent;
            }
        }

        // Load up the collection of the results
        Collection<T> collection = new ArrayList<T>(K);
        for (KDNode kdNode : results)
            collection.add((T) kdNode.id);
        return collection;
    }

    private static final <T extends Vector> void searchNode(T value, KDNode node, int K, TreeSet<KDNode> results, Set<KDNode> examined) {
        examined.add(node);

        // Search node
        KDNode lastNode = null;
        Double lastDistance = Double.MAX_VALUE;
        if (results.size() > 0) {
            lastNode = results.last();
            lastDistance = lastNode.id.euclideanDistance(value);
        }
        Double nodeDistance = node.id.euclideanDistance(value);
        if (nodeDistance.compareTo(lastDistance) < 0) {
            if (results.size() == K && lastNode != null)
                results.remove(lastNode);
            results.add(node);
        } else if (nodeDistance.equals(lastDistance)) {
            results.add(node);
        } else if (results.size() < K) {
            results.add(node);
        }
        lastNode = results.last();
        lastDistance = lastNode.id.euclideanDistance(value);

        int axis = node.depth % node.k;
        KDNode lesser = node.lesser;
        KDNode greater = node.greater;

        // Search children branches, if axis aligned distance is less than
        // current distance
        if (lesser != null && !examined.contains(lesser)) {
            examined.add(lesser);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
//            if (axis == OrdinateComparator.X_AXIS) {
//                nodePoint = node.id.x;
//                valuePlusDistance = value.x - lastDistance;
//            } else if (axis == OrdinateComparator.Y_AXIS) {
//                nodePoint = node.id.y;
//                valuePlusDistance = value.y - lastDistance;
//            } else {
//                nodePoint = node.id.z;
//                valuePlusDistance = value.z - lastDistance;
//            }
            nodePoint = node.id.getOrdinate(axis);
            valuePlusDistance = value.getOrdinate(axis) - lastDistance;
            boolean lineIntersectsCube = ((valuePlusDistance <= nodePoint) ? true : false);

            // Continue down lesser branch
            if (lineIntersectsCube)
                searchNode(value, lesser, K, results, examined);
        }
        if (greater != null && !examined.contains(greater)) {
            examined.add(greater);

            double nodePoint = Double.MIN_VALUE;
            double valuePlusDistance = Double.MIN_VALUE;
//            if (axis == OrdinateComparator.X_AXIS) {
//                nodePoint = node.id.x;
//                valuePlusDistance = value.x + lastDistance;
//            } else if (axis == OrdinateComparator.Y_AXIS) {
//                nodePoint = node.id.y;
//                valuePlusDistance = value.y + lastDistance;
//            } else {
//                nodePoint = node.id.z;
//                valuePlusDistance = value.z + lastDistance;
//            }
            nodePoint = node.id.getOrdinate(axis);
            valuePlusDistance = value.getOrdinate(axis) + lastDistance;
            boolean lineIntersectsCube = ((valuePlusDistance >= nodePoint) ? true : false);

            // Continue down greater branch
            if (lineIntersectsCube)
                searchNode(value, greater, K, results, examined);
        }
    }

    /**
     * Adds, in a specified queue, a given node and its related nodes (lesser, greater).
     *
     * @param node    Node to check. May be null.
     * @param results Queue containing all found entries. Must not be null.
     */
    @SuppressWarnings("unchecked")
    private static <T extends Vector> void search(final KDNode node, final Deque<T> results) {
        if (node != null) {
            results.add((T) node.id);
            search(node.greater, results);
            search(node.lesser, results);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return TreePrinter.getString(this);
    }

    protected static class EuclideanComparator implements Comparator<KDNode> {

        private final Vector point;

        public EuclideanComparator(Vector point) {
            this.point = point;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int compare(KDNode o1, KDNode o2) {
            Double d1 = point.euclideanDistance(o1.id);
            Double d2 = point.euclideanDistance(o2.id);
            if (d1.compareTo(d2) < 0)
                return -1;
            else if (d2.compareTo(d1) < 0)
                return 1;
            return o1.id.compareTo(o2.id);
        }
    }

    /**
     * Searches all entries from the first to the last entry.
     *
     * @return Iterator
     * allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.iterator();
    }

    /**
     * Searches all entries from the last to the first entry.
     *
     * @return Iterator
     * allowing to iterate through a collection containing all found entries.
     */
    public Iterator<T> reverse_iterator() {
        final Deque<T> results = new ArrayDeque<T>();
        search(root, results);
        return results.descendingIterator();
    }


    protected static class TreePrinter {

        public static <T extends Vector> String getString(KDTreeImpl<T> tree) {
            if (tree.root == null)
                return "Tree has no nodes.";
            return getString(tree.root, "", true);
        }

        private static String getString(KDNode node, String prefix, boolean isTail) {
            StringBuilder builder = new StringBuilder();

            if (node.parent != null) {
                String side = "left";
                if (node.parent.greater != null && node.id.equals(node.parent.greater.id))
                    side = "right";
                builder.append(prefix + (isTail ? "└── " : "├── ") + "[" + side + "] " + "depth=" + node.depth + " id="
                        + node.id + "\n");
            } else {
                builder.append(prefix + (isTail ? "└── " : "├── ") + "depth=" + node.depth + " id=" + node.id + "\n");
            }
            List<KDNode> children = null;
            if (node.lesser != null || node.greater != null) {
                children = new ArrayList<KDNode>(2);
                if (node.lesser != null)
                    children.add(node.lesser);
                if (node.greater != null)
                    children.add(node.greater);
            }
            if (children != null) {
                for (int i = 0; i < children.size() - 1; i++) {
                    builder.append(getString(children.get(i), prefix + (isTail ? "    " : "│   "), false));
                }
                if (children.size() >= 1) {
                    builder.append(getString(children.get(children.size() - 1), prefix + (isTail ? "    " : "│   "),
                            true));
                }
            }

            return builder.toString();
        }
    }
} 