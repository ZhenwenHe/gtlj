package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.exception.IllegalArgumentException;
import cn.edu.cug.cs.gtl.index.shape.Shape;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ZhenwenHe on 2017/2/12.
 */
public abstract class NodeImpl implements Node {

    private static final long serialVersionUID = 1L;

    protected Identifier identifier;
    protected int type;
    protected Shape shape;
    // The level of the node in the tree.
    // Leaves are always at level 0.
    protected int level;
    // The numeric of children pointed by this node.
    protected int children;
    // child entry array
    protected Entry[] entries;
    // Specifies the node capacity.
    protected int capacity;
    // total data length
    protected int totalDataLength;


    public NodeImpl(Identifier identifier, int level, int capacity) {
        this.identifier = Identifier.create(identifier.longValue());
        //this.shape = newShape();
        this.shape = null;
        this.level = level;
        this.children = 0;
        this.totalDataLength = 0;
        this.capacity = capacity;
        this.entries = new Entry[this.capacity + 1];
        this.type = -1;
    }

    public NodeImpl() {
        this.identifier = Identifier.create(-1L);
        //this.shape = newShape();
        this.shape = null;
        this.capacity = 64;
        this.level = 0;
        this.children = 0;
        this.totalDataLength = 0;
        this.entries = new Entry[this.capacity + 1];
        this.type = -1;
    }

    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public void setType(int nodeType) {
        this.type = nodeType;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setIdentifier(Identifier id) {
        this.identifier.reset(id.longValue());
    }

    @Override
    public Shape getShape() {
        return this.shape;
    }

    @Override
    public void setShape(Shape s) {
        this.shape = (Shape) s.clone();
    }

    @Override
    public void setIdentifier(long id) {
        this.identifier.reset(id);
    }

    @Override
    public byte[] getData() {
        return null;
    }

    @Override
    public void setData(byte[] data) {
        // do nothing
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public long getDataLength() {
        return this.totalDataLength;
    }

    //重新计算节点的数据长度
    @Override
    public long recalculateDataLength() {
        this.totalDataLength = 0;
        for (Entry e : this.entries) {
            this.totalDataLength += e.getDataLength();
        }
        return this.totalDataLength;
    }

    @Override
    public int getChildrenCount() {
        return this.children;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean isInternalNode() {
        return (this.level != 0);
    }

    @Override
    public boolean isExternalNode() {
        return (this.level == 0);
    }

    @Override
    public Entry getChildEntry(int index) {
        return this.entries[index];
    }

    @Override
    public void setChildEntry(int index, Entry e) {
        this.entries[index] = e;
    }

    @Override
    public Entry[] getChildEntries() {
        return this.entries;
    }

    @Override
    public void setChildEntries(Entry[] es) {
        this.totalDataLength = 0;
        this.entries = es;
        for (Entry e : es) {
            if (e != null)
                this.totalDataLength += e.getDataLength();
        }
    }

    @Override
    public long getByteArraySize() {
        long sum = 4;//node type
        sum += 4 * 4;
        sum += this.identifier.getByteArraySize();
        sum += this.shape.getByteArraySize();
        // child entry array
        for (int iChild = 0; iChild < this.children; ++iChild) {
            sum += this.entries[iChild].getByteArraySize();
        }
        return sum;
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        this.type = dis.readInt();
        this.identifier.load(dis);
        this.shape.load(dis);
        // Leaves are always at level 0.
        this.level = dis.readInt();
        // The numeric of children pointed by this node.
        this.children = dis.readInt();
        // Specifies the node capacity.
        this.capacity = dis.readInt();
        // total data length
        this.totalDataLength = dis.readInt();
        // child entry array
        for (int iChild = 0; iChild < this.children; ++iChild) {
            EntryImpl e = new EntryImpl(Identifier.create(-1L), newShape(), null);
            e.load(dis);
            this.entries[iChild] = e;
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        dos.writeInt(this.type);
        this.identifier.store(dos);
        this.shape.store(dos);
        // Leaves are always at level 0.
        dos.writeInt(this.level);
        // The numeric of children pointed by this node.
        dos.writeInt(this.children);
        // Specifies the node capacity.
        dos.writeInt(this.capacity);
        // total data length
        dos.writeInt(this.totalDataLength);
        // child entry array
        for (int iChild = 0; iChild < this.children; ++iChild) {
            this.entries[iChild].store(dos);
        }
        return true;
    }

    @Override
    public void copyFrom(Object i) {
        if (i == null) return;
        if (i instanceof NodeImpl) {
            NodeImpl ni = ((NodeImpl) i);
            this.identifier.reset(ni.identifier.longValue());
            this.shape = (Shape) ni.shape.clone();
            this.level = ni.level;
            this.children = ni.children;
            this.capacity = ni.capacity;
            this.totalDataLength = ni.totalDataLength;

            for (int iChild = 0; iChild < this.children; ++iChild) {
                this.entries[iChild] = (Entry) ni.entries[iChild].clone();
            }

        }
    }

    @Override
    public void insertEntry(Entry e) {
        try {
            if (e instanceof Entry) {
                //assert (this.children < this.capacity);
                this.entries[this.children] = e;
                this.totalDataLength += e.getDataLength();
                ++this.children;
            } else {
                throw new IllegalArgumentException("NodeImpl.insertEntry(Entry e): wrong parameter");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Entry removeEntry(int index) {
        assert (index >= 0 && index < this.children);
        Entry e = this.entries[index];
        this.totalDataLength -= e.getDataLength();
        if (this.children > 1 && index != this.children - 1) {
            this.entries[index] = this.entries[this.children - 1];
            this.entries[this.children - 1] = null;
        }
        --this.children;
        return e;
    }

    @Override
    public abstract Object clone();

    @Override
    public abstract Shape recalculateShape();

    @Override
    public abstract Shape newShape();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NodeImpl)) return false;

        NodeImpl node = (NodeImpl) o;

        if (getType() != node.getType()) return false;
        if (getLevel() != node.getLevel()) return false;
        if (children != node.children) return false;
        if (getCapacity() != node.getCapacity()) return false;
        if (totalDataLength != node.totalDataLength) return false;
        if (!getIdentifier().equals(node.getIdentifier())) return false;
        if (!getShape().equals(node.getShape())) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(entries, node.entries);
    }

    @Override
    public int hashCode() {
        int result = getIdentifier().hashCode();
        result = 31 * result + getType();
        result = 31 * result + getShape().hashCode();
        result = 31 * result + getLevel();
        result = 31 * result + children;
        result = 31 * result + Arrays.hashCode(entries);
        result = 31 * result + getCapacity();
        result = 31 * result + totalDataLength;
        return result;
    }
}
