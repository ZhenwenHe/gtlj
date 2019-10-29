package cn.edu.cug.cs.gtl.index.quadtree;

import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.index.Indexable;
import cn.edu.cug.cs.gtl.index.quadtree.impl.QuadTreeImpl;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;

import java.util.List;

public interface QuadTree extends Indexable {

    /**
     * clear the tree , and reset the tree
     * if the headerIdentifier is valid, then load the tree context from the storage,
     * else create a new tree by these parameters
     *
     * @param storageManager
     * @param headerIdentifier
     * @param leafCapacity
     * @param dimension
     * @param totalEnvelope
     */
    void reset(
            StorageManager storageManager,
            Identifier headerIdentifier,
            int leafCapacity,
            int dimension,
            Envelope totalEnvelope);

    /**
     * reset the tree to be a memory -based quad-tree
     *
     * @param leafCapacity
     * @param dimension
     * @param totalEnvelope
     */
    void reset(
            int leafCapacity,
            int dimension,
            Envelope totalEnvelope);

    /**
     * create a new memory-based tree
     *
     * @param leafCapacity
     * @param dimension
     * @param totalEnvelope
     * @return
     */
    static QuadTree create(
            int leafCapacity,
            int dimension,
            Envelope totalEnvelope) {
        QuadTreeImpl tree = new QuadTreeImpl();
        tree.reset(leafCapacity, dimension, totalEnvelope);
        return tree;
    }

    /**
     * create a tree,
     * if the headerIdentifier is valid, then load the tree context from the storage,
     * else create a new tree by these parameters
     *
     * @param storageManager
     * @param headerIdentifier
     * @param leafCapacity
     * @param dimension
     * @param totalEnvelope
     * @return
     */
    static QuadTree create(
            StorageManager storageManager,
            Identifier headerIdentifier,
            int leafCapacity,
            int dimension,
            Envelope totalEnvelope) {
        QuadTreeImpl tree = new QuadTreeImpl();
        tree.reset(storageManager, headerIdentifier, leafCapacity, dimension, totalEnvelope);
        return tree;
    }

    default List<Envelope> getPartitionEnvelopes() {
        return getLeafNodeEnvelopes();
    }

    List<Envelope> getLeafNodeEnvelopes();
}
