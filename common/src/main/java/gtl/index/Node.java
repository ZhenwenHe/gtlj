package gtl.index;

import gtl.common.Identifier;
import gtl.exception.IllegalArgumentException;
import gtl.index.shape.Shape;

/**
 * Created by ZhenwenHe on 2016/12/7.
 */
public interface Node extends Entry {
    int getChildrenCount();

    int getCapacity();

    int getType();

    void setType(int nodeType);

    default Identifier getChildIdentifier(int index) {
        if (index <= this.getChildrenCount())
            return getChildEntry(index).getIdentifier();
        else
            return null;
    }

    default void setChildIdentifier(int index, Identifier id) {
        if (id == null) return;
        if (index <= this.getChildrenCount()) {
            Entry e = this.getChildEntry(index);
            assert e != null;
            e.setIdentifier(id);
        }
    }

    default byte[] getChildData(int index) {
        if (index <= this.getChildrenCount())
            return getChildEntry(index).getData();
        else
            return null;
    }

    default void setChildData(int index, byte[] data) {
        if (index <= this.getChildrenCount()) {
            Entry e = this.getChildEntry(index);
            assert e != null;
            e.setData(data);
        }
    }

    default Shape getChildShape(int index) {
        try {
            if (index <= this.getChildrenCount()) {
                Entry e = this.getChildEntry(index);
                assert e != null;
                return e.getShape();
            } else {
                throw new IllegalArgumentException("Node.getChildShape(int index):" + String.valueOf(index));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    int getLevel();

    void setLevel(int l);

    default boolean isIndex() {
        return isInternalNode();
    }

    default boolean isLeaf() {
        return isExternalNode();
    }

    boolean isInternalNode();

    boolean isExternalNode();

    Entry getChildEntry(int index);

    void setChildEntry(int index, Entry e);

    Entry[] getChildEntries();

    void setChildEntries(Entry[] es);

    void insertEntry(Entry e);

    Entry removeEntry(int index);

    default void insertEntry(Identifier i, Shape s, byte[] data) {
        insertEntry(new EntryImpl(i, s, data));
    }

    //重新计算节点的包围矩形
    Shape recalculateShape();

    //重新计算节点的数据长度
    long recalculateDataLength();

    /**
     * 对于不同的节点实现，需要重载这个函数生成对应的边界矩形，
     * 例如，如果是RTree的Node，则生产的是Region ;
     */
    Shape newShape();
}
