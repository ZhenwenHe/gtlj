package cn.edu.cug.cs.gtl.index.quadtree.impl;

import cn.edu.cug.cs.gtl.index.*;
import cn.edu.cug.cs.gtl.index.quadtree.QuadTree;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.index.shape.ShapeSuits;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.exception.IllegalArgumentException;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.index.knn.NNComparator;
import cn.edu.cug.cs.gtl.index.knn.NNEntry;
import cn.edu.cug.cs.gtl.index.knn.NearestNeighborComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

public class QuadTreeImpl implements QuadTree, Serializable {
    private static final long serialVersionUID = 1L;

    StorageManager storageManager;
    Identifier rootIdentifier;//存储根节点信息的页面ID
    Identifier headerIdentifier;//存储索引头信息的页面ID
    int dimension;
    int leafCapacity;
    Envelope envelope;
    transient ArrayList<Pair<Command, CommandType>> commands = null;

    public QuadTreeImpl(StorageManager storageManager, int leafCapacity, int dimension, Envelope totalEnvelope) {
        this.storageManager = storageManager;
        this.dimension = dimension;
        this.leafCapacity = leafCapacity;
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
    }

    public QuadTreeImpl(StorageManager storageManager, int leafCapacity, Envelope totalEnvelope) {
        this.storageManager = storageManager;
        this.dimension = 2;
        this.leafCapacity = leafCapacity;
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.envelope = totalEnvelope;
    }

    public QuadTreeImpl(StorageManager storageManager, Envelope totalEnvelope) {
        this.storageManager = storageManager;
        this.dimension = 2;
        this.leafCapacity = 64;
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.envelope = totalEnvelope;
    }

    public QuadTreeImpl() {
        this.storageManager = StorageManager.createMemoryStorageManager();
        this.dimension = 2;
        this.leafCapacity = 64;
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        Vector v = Vector.create(this.dimension);
        this.envelope = Envelope.create(v, 1.0);

    }

    @Override
    public void reset(StorageManager storageManager, Identifier headerIdentifier, int leafCapacity, int dimension, Envelope totalEnvelope) {
        this.storageManager = storageManager;
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.leafCapacity = leafCapacity;
        this.dimension = dimension;
        this.envelope = totalEnvelope;

        if (headerIdentifier == null) {//new
            QuadTreeExternalNodeImpl root = new QuadTreeExternalNodeImpl(this.rootIdentifier, leafCapacity, this, new RegionShape(this.envelope));

            this.rootIdentifier = writeNode(root);
            storeHeader();
        } else {//old
            this.headerIdentifier.reset(headerIdentifier.longValue());
            loadHeader();
        }
    }

    @Override
    public void reset(
            int leafCapacity,
            int dimension,
            Envelope totalEnvelope) {
        this.storageManager = StorageManager.createMemoryStorageManager();
        this.rootIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.headerIdentifier = Identifier.create(StorageManager.NEW_PAGE);
        this.leafCapacity = leafCapacity;
        this.dimension = dimension;
        this.envelope = totalEnvelope;


        QuadTreeExternalNodeImpl root = new QuadTreeExternalNodeImpl(this.rootIdentifier, leafCapacity, this, new RegionShape(this.envelope));
        this.rootIdentifier = writeNode(root);
        storeHeader();

    }

    protected void storeHeader() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            DataOutputStream dos = new DataOutputStream(bos);
            this.rootIdentifier.store(dos);
            dos.writeInt(this.leafCapacity);
            dos.writeInt(this.dimension);
            this.envelope.store(dos);
            dos.flush();
            byte[] data = bos.toByteArray();
            dos.close();
            this.storageManager.storeByteArray(this.headerIdentifier, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadHeader() {
        try {
            byte[] data = this.storageManager.loadByteArray(this.headerIdentifier);
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
            this.rootIdentifier.load(dis);
            this.leafCapacity = dis.readInt();
            this.dimension = dis.readInt();
            this.envelope.load(dis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCapacity() {
        return leafCapacity;
    }

    public void setCapacity(int leafCapacity) {
        this.leafCapacity = leafCapacity;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    @Override
    public void insert(byte[] pData, Shape shape, Identifier shapeIdentifier) {
        if (this.envelope.intersects(shape.getMBR()) == false)
            return;

//        if(this.rootIdentifier==null
//                || this.rootIdentifier.longValue()==StorageManager.NEW_PAGE){
//            QuadTreeExternalNodeImpl root =new QuadTreeExternalNodeImpl(leafCapacity,this,new RegionShape(this.envelope));
//            root.insertEntry(shapeIdentifier,shape,pData);
//            this.rootIdentifier=writeNode(root);
//            return;
//        }

        Node node = readNode(this.rootIdentifier);
        Identifier nodePageIdentifier = null;
        Shape nodeShape = (RegionShape) node.newShape();
        nodeShape.copyFrom(this.envelope);
        while (nodeShape.intersectsShape(shape)) {
            if (node instanceof QuadTreeExternalNodeImpl) {//if is leaf node
                if (node.getChildrenCount() < node.getCapacity()) {// insert
                    node.insertEntry(shapeIdentifier, shape, pData);
                    writeNode(node);
                    return;
                } else {//split node
                    splitAndInsert(node, pData, shape, shapeIdentifier);
                    return;
                }
            } else {//if it non-leaf node
                assert node.getChildrenCount() == 4;
                for (int i = 0; i < node.getChildrenCount(); ++i) {
                    nodeShape = node.getChildShape(i);//QuadTreeInternalNodeImpl默认填充四个四边形，没有数据的ID为null
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodeShape.intersectsShape(shape)) {
                        if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE) {//该象限没有子节点，需要生成一个新的子节点
                            nodePageIdentifier = Identifier.create();
                            QuadTreeExternalNodeImpl externalNode = new QuadTreeExternalNodeImpl(nodePageIdentifier, leafCapacity, this, (Shape) nodeShape.clone());
                            externalNode.insertEntry(shapeIdentifier, shape, pData);
                            nodePageIdentifier = writeNode(externalNode);
                            node.setChildIdentifier(i, nodePageIdentifier);//update the parent node information
                            writeNode(node);//PageID does not be changed
                            return;
                        } else {//该象限有子节点，则读取该节点赋值给node
                            node = readNode(node.getChildIdentifier(i));
                            nodeShape.copyFrom(node.getShape());
                            break;
                        }
                    }
                }
            }
        }
    }

    protected void splitAndInsert(Node node, byte[] pData, Shape shape, Identifier shapeIdentifier) {
        QuadTreeInternalNodeImpl internalNode = new QuadTreeInternalNodeImpl(node.getIdentifier(), this, (Shape) node.getShape().clone());
        RegionShape rp = (RegionShape) internalNode.getShape();
        QuadTreeExternalNodeImpl[] subnodes = new QuadTreeExternalNodeImpl[4];
        RegionShape subregion = null;
        int splitAgain = -1;
        for (int i = 0; i < 4; ++i) {
            subregion = rp.subregion(i);
            internalNode.insertEntry(Identifier.create(StorageManager.EMPTY_PAGE), subregion, null);
            subnodes[i] = new QuadTreeExternalNodeImpl(leafCapacity, this, subregion);
            for (int j = 0; j < node.getChildrenCount(); ++j) {
                if (subregion.intersectsShape(node.getChildShape(j))) {
                    subnodes[i].insertEntry(node.getChildEntry(j));
                }
            }
            if (subnodes[i].getChildrenCount() == node.getChildrenCount()) {
                if (subregion.intersectsShape(shape))
                    splitAgain = i;
            } else {
                if (subregion.intersectsShape(shape))
                    subnodes[i].insertEntry(shapeIdentifier, shape, pData);
            }
        }

        Identifier identifier = null;
        for (int i = 0; i < 4; ++i) {
            if (subnodes[i].getChildrenCount() > 0) {
                identifier = writeNode(subnodes[i]);
                internalNode.setChildIdentifier(i, identifier);
            }
        }
        identifier = writeNode(internalNode);

        if (splitAgain > 0) {
            splitAndInsert(subnodes[splitAgain], pData, shape, shapeIdentifier);
        }
    }

    /**
     * delete all the object contained by shape and the id is equal to shapeIdentifier
     *
     * @param shape
     * @param shapeIdentifier
     * @return
     */
    @Override
    public boolean delete(Shape shape, Identifier shapeIdentifier) {
        if (this.envelope.intersects(shape.getMBR()) == false)
            return false;
        if (this.rootIdentifier == null || this.rootIdentifier.longValue() == StorageManager.NEW_PAGE)
            return false;
        Identifier nodePageIdentifier = Identifier.create(this.rootIdentifier);
        Stack<Pair<Identifier, Identifier>> stack = new Stack<>();
        stack.push(new Pair<>(nodePageIdentifier, null));
        nodePageIdentifier = null;

        while (stack.empty() == false) {
            Pair<Identifier, Identifier> nodePageIdentifiers = stack.pop();
            nodePageIdentifier = nodePageIdentifiers.first();
            Node node = readNode(nodePageIdentifier);
            if (node instanceof QuadTreeExternalNodeImpl) {
                for (int i = 0; i < node.getChildrenCount(); ++i) {
                    if (shape.containsShape(node.getChildShape(i))) {
                        if (node.getChildEntry(i).getIdentifier().equals(shapeIdentifier)) {
                            node.removeEntry(i);
                        }
                    }
                }
                //if the node is empty
                if (node.getChildrenCount() == 0) {
                    try {
                        storageManager.deleteByteArray(nodePageIdentifier);
                        if (nodePageIdentifiers.second() == null || nodePageIdentifiers.second().longValue() == StorageManager.NEW_PAGE) {
                            assert nodePageIdentifier.longValue() == this.rootIdentifier.longValue();
                            return true;
                        } else {
                            Node parentNode = readNode(nodePageIdentifiers.second());
                            for (int i = 0; i < 4; ++i) {
                                if (parentNode.getChildIdentifier(i) != null) {
                                    if (parentNode.getChildIdentifier(i).longValue() == nodePageIdentifier.longValue()) {
                                        parentNode.setChildIdentifier(i, Identifier.create(StorageManager.NEW_PAGE));
                                        writeNode(parentNode);
                                        return true;
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return false;
                    }
                } else {
                    writeNode(node);
                    return true;
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE)
                        continue;
                    if (shape.intersectsShape(node.getChildShape(i)))
                        stack.push(new Pair(nodePageIdentifier, node.getIdentifier()));
                }
            }
        }
        return false;
    }

    @Override
    public void contains(Shape query, Visitor v) {
        if (this.envelope.intersects(query.getMBR()) == false)
            return;
        if (this.rootIdentifier == null || this.rootIdentifier.longValue() == StorageManager.NEW_PAGE)
            return;
        Identifier nodePageIdentifier = Identifier.create(this.rootIdentifier);
        Stack<Identifier> stack = new Stack<>();
        stack.push(nodePageIdentifier);
        nodePageIdentifier = null;

        while (stack.empty() == false) {
            nodePageIdentifier = stack.pop();
            Node node = readNode(nodePageIdentifier);
            if (node instanceof QuadTreeExternalNodeImpl) {
                for (int i = 0; i < node.getChildrenCount(); ++i) {
                    if (query.containsShape(node.getChildShape(i))) {
                        v.visitData(node.getChildEntry(i));
                    }
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE)
                        continue;
                    if (query.intersectsShape(node.getChildShape(i)))
                        stack.push(nodePageIdentifier);
                }
            }
        }
    }

    @Override
    public void intersects(Shape query, Visitor v) {
        if (this.envelope.intersects(query.getMBR()) == false)
            return;
        if (this.rootIdentifier == null || this.rootIdentifier.longValue() == StorageManager.NEW_PAGE)
            return;
        Identifier nodePageIdentifier = Identifier.create(this.rootIdentifier);
        Stack<Identifier> stack = new Stack<>();
        stack.push(nodePageIdentifier);
        nodePageIdentifier = null;

        while (stack.empty() == false) {
            nodePageIdentifier = stack.pop();
            Node node = readNode(nodePageIdentifier);
            if (node instanceof QuadTreeExternalNodeImpl) {
                for (int i = 0; i < node.getChildrenCount(); ++i) {
                    if (query.intersectsShape(node.getChildShape(i))) {
                        v.visitData(node.getChildEntry(i));
                    }
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE)
                        continue;
                    if (query.intersectsShape(node.getChildShape(i)))
                        stack.push(nodePageIdentifier);
                }
            }
        }
    }

    /**
     * @param query
     * @param v
     */
    @Override
    public void pointLocation(PointShape query, Visitor v) {
        if (this.envelope.contains(query.getCenter()) == false)
            return;
        if (this.rootIdentifier == null || this.rootIdentifier.longValue() == StorageManager.NEW_PAGE)
            return;
        Identifier nodePageIdentifier = Identifier.create(this.rootIdentifier);
        Stack<Identifier> stack = new Stack<>();
        stack.push(nodePageIdentifier);
        nodePageIdentifier = null;

        while (stack.empty() == false) {
            nodePageIdentifier = stack.pop();
            Node node = readNode(nodePageIdentifier);
            if (node instanceof QuadTreeExternalNodeImpl) {
                for (int i = 0; i < node.getChildrenCount(); ++i) {
                    if (node.getChildShape(i).containsShape(query)) {
                        v.visitData(node.getChildEntry(i));
                    }
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE)
                        continue;
                    if (node.getChildShape(i).containsShape(query))
                        stack.push(nodePageIdentifier);
                }
            }
        }
    }

    @Override
    public void nearestNeighbor(int k, Shape query, Visitor v, NearestNeighborComparator nnc) {
        try {
            if (query.getDimension() != this.getDimension())
                throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong numeric of dimensions.");

            PriorityQueue<NNEntry> queue = new PriorityQueue<NNEntry>();

            queue.add(new NNEntry(this.rootIdentifier, null, 0.0));

            int count = 0;
            double knearest = 0.0;

            while (!queue.isEmpty()) {
                NNEntry pFirst = queue.peek();
                // report all nearest neighbors with equal greatest distances.
                // (neighbors can be more than k, if many happen to have the same greatest distance2D).
                if (count >= k && pFirst.minDistance > knearest) break;

                queue.poll();

                if (pFirst.entry == null) {
                    // n is a leaf or an index.
                    Node n = readNode(pFirst.identifier);
                    v.visitNode(n);

                    for (int cChild = 0; cChild < n.getChildrenCount(); ++cChild) {
                        if (n.getLevel() == 0) {
                            Entry e = new EntryImpl(n.getChildIdentifier(cChild), n.getChildShape(cChild), n.getChildData(cChild));
                            // we need to compare the query with the actual data entry here, so we call the
                            // appropriate getMinimumDistance method of NearestNeighborComparator.
                            queue.add(new NNEntry(n.getChildIdentifier(cChild), e, nnc.getMinimumDistance(query, e)));
                        } else {
                            queue.add(new NNEntry(n.getChildIdentifier(cChild), null, nnc.getMinimumDistance(query, n.getChildShape(cChild))));
                        }
                    }
                } else {
                    v.visitData(pFirst.entry);
                    ++count;
                    knearest = pFirst.minDistance;
                }
            }
            queue.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nearestNeighbor(int k, Shape query, Visitor v) {
        try {
            if (query.getDimension() != this.dimension)
                throw new IllegalArgumentException("nearestNeighborQuery: Shape has the wrong numeric of dimensions.");
            nearestNeighbor(k, query, v, new NNComparator());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selfJoin(Shape query, Visitor v) {
        try {
            if (query.getDimension() != this.dimension)
                throw new IllegalArgumentException("selfJoinQuery: Shape has the wrong numeric of dimensions.");

            RegionShape mbr = ShapeSuits.createRegion(query.getMBR());
            selfJoin(this.rootIdentifier, this.rootIdentifier, mbr, v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void selfJoin(Identifier id1, Identifier id2, RegionShape r, Visitor vis) {
        Node n1 = readNode(id1);
        Node n2 = readNode(id2);
        vis.visitNode(n1);
        vis.visitNode(n2);
        RegionShape tr1 = null;
        RegionShape tr2 = null;
        Entry[] ev = new Entry[2];
        Identifier ti1 = null;
        Identifier ti2 = null;
        for (int cChild1 = 0; cChild1 < n1.getChildrenCount(); ++cChild1) {
            tr1 = (RegionShape) n1.getChildShape(cChild1);
            ti1 = n1.getChildIdentifier(cChild1);
            if (r.intersectsRegion(tr1)) {
                for (int cChild2 = 0; cChild2 < n2.getChildrenCount(); ++cChild2) {
                    tr2 = (RegionShape) n2.getChildShape(cChild2);
                    ti2 = n2.getChildIdentifier(cChild2);
                    if (r.intersectsRegion(tr2) && tr1.intersectsRegion(tr2)) {
                        if (n1.getLevel() == 0) {
                            if (!ti1.equals(ti2)) {
                                assert (n2.getLevel() == 0);

                                ev[0] = n1.getChildEntry(cChild1);
                                ev[1] = n2.getChildEntry(cChild2);
                                vis.visitData(ev);
                            }
                        } else {
                            RegionShape rr = r.getIntersectingRegion(tr1.getIntersectingRegion(tr2));
                            selfJoin(ti1, ti2, rr, vis);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void queryStrategy(QueryStrategy qs) {
        Identifier next = null;
        try {
            next = (Identifier) this.rootIdentifier.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Boolean hasNext = true;

        while (hasNext) {
            Node n = readNode(next);
            qs.getNextEntry(n, next, hasNext);
        }
    }

    @Override
    public PropertySet getProperties() {
        PropertySet ps = PropertySet.create();//new PropertySetImpl();

        // dimension
        ps.put("Dimension", new Variant(this.dimension));


        // leaf capacity
        ps.put("LeafCapacity", new Variant(this.leafCapacity));


        ps.put("Envelope", new Variant(this.envelope));

        return ps;
    }

    @Override
    public void addCommand(Command in, CommandType ct) {
        if (this.commands == null) {
            this.commands = new ArrayList<>();
        }
        this.commands.add(new Pair(in, ct));
    }

    /**
     * it is always valid
     *
     * @return
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * have not the statistics information,
     * return null
     *
     * @return
     */
    @Override
    public Statistics getStatistics() {
        return null;
    }

    @Override
    public int getDimension() {
        return dimension;
    }

    /**
     * write the node to storageManger
     *
     * @param n
     * @return
     */
    Identifier writeNode(Node n) {
        try {
            byte[] buffer = n.storeToByteArray();
            Identifier page = Identifier.create(-1L);
            if (n.getIdentifier().longValue() < 0)
                page.reset(StorageManager.NEW_PAGE);
            else
                page = (Identifier) n.getIdentifier().clone();

            this.storageManager.storeByteArray(page, buffer);

            if (n.getIdentifier().longValue() < 0) {
                n.setIdentifier(page);
            }
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * read the node from storageManger
     *
     * @param page is the page identifier in the storageManager
     * @return
     */
    Node readNode(Identifier page) {
        try {
            byte[] buffer = this.storageManager.loadByteArray(page);
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buffer));
            int nodeType = dis.readInt();
            QuadTreeNodeImpl n = null;
            if (nodeType == 1)
                n = new QuadTreeInternalNodeImpl(Identifier.create(-1L), this, new RegionShape(this.getDimension()));
            else
                n = new QuadTreeExternalNodeImpl(Identifier.create(-1L), getCapacity(), this, new RegionShape(this.getDimension()));
            n.loadFromByteArray(buffer);
            n.setIdentifier(page);//change the identifier
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get all leaf node boundaries
     *
     * @return
     */
    @Override
    public List<Envelope> getLeafNodeEnvelopes() {
        if (this.rootIdentifier == null || this.rootIdentifier.longValue() == StorageManager.NEW_PAGE)
            return null;
        Identifier nodePageIdentifier = Identifier.create(this.rootIdentifier);
        Stack<Identifier> stack = new Stack<>();
        stack.push(nodePageIdentifier);
        nodePageIdentifier = null;

        ArrayList<Envelope> results = new ArrayList<>();

        while (stack.empty() == false) {
            nodePageIdentifier = stack.pop();
            Node node = readNode(nodePageIdentifier);
            if (node instanceof QuadTreeExternalNodeImpl) {
                results.add(node.getShape().getMBR());
            } else {
                for (int i = 0; i < 4; ++i) {
                    nodePageIdentifier = node.getChildIdentifier(i);
                    if (nodePageIdentifier == null || nodePageIdentifier.longValue() == StorageManager.NEW_PAGE)
                        continue;
                    stack.push(nodePageIdentifier);
                }
            }
        }
        return results;
    }
}
