package cn.edu.cug.cs.gtl.index.dtree;

import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.*;

class LeafNode implements Serializable {
    RegionShape regionShape;
    Serializable[] objects;
    int size;

    LeafNode(int leafNodeCapacity, int dim) {
        regionShape = new RegionShape(dim);
        size = 0;
        objects = new Serializable[leafNodeCapacity];
    }

    public LeafNode(Envelope regionShape, int leafNodeCapacity) {
        this.regionShape = (RegionShape) regionShape.clone();
        size = 0;
        objects = new Serializable[leafNodeCapacity];
    }

    public boolean insert(Serializable obj) {
        if (size >= objects.length)
            return false;
        objects[size] = (Serializable) obj;
        return true;
    }

    public Serializable remove(int i) {
        if (size == 0) return null;
        Serializable s = objects[i];
        for (int j = i; j < size - 1; ++j) {
            objects[j] = objects[j + 1];
        }
        size--;
        return s;
    }

    public boolean remove(Serializable s) {
        for (int i = 0; i < size; ++i) {
            if (objects[i].equals(s)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Object clone() {
        LeafNode ln = new LeafNode(objects.length, this.regionShape.getDimension());
        for (Serializable s : objects)
            ln.insert(s);
        ln.regionShape.copyFrom(this.regionShape);
        return ln;
    }

    @Override
    public void copyFrom(Object i) {
        LeafNode ln = (LeafNode) (i);
        this.size = 0;
        this.regionShape.copyFrom(ln.regionShape);
        for (Serializable s : ln.objects)
            this.insert(s);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            {
                int len = in.readInt();
                byte[] bs = new byte[len];
                in.readFully(bs, 0, len);
                ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                ObjectInputStream ois = new ObjectInputStream(bais);
                regionShape = (RegionShape) ois.readObject();
                ois.close();
            }
            size = in.readInt();
            if (size == 0) return true;

            {
                int len = in.readInt();
                byte[] bs = new byte[len];
                in.readFully(bs, 0, len);
                ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                ObjectInputStream ois = new ObjectInputStream(bais);
                for (int i = 0; i < size; ++i) {
                    objects[i] = (Serializable) ois.readObject();
                }
                ois.close();
            }
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(regionShape);
                byte[] bs = baos.toByteArray();
                out.writeInt(bs.length);
                out.write(bs, 0, bs.length);
                oos.close();
            }
            out.writeInt(size);
            if (size == 0) return true;

            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                for (int i = 0; i < size; ++i) {
                    oos.writeObject(objects[i]);
                }
                byte[] bs = baos.toByteArray();
                out.writeInt(bs.length);
                out.write(bs, 0, bs.length);
                oos.close();
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getByteArraySize() {
        try {
            long len = 0;
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(regionShape);
                len += 4;
                len += baos.size();
            }
            len += 4;
            if (size == 0) return len;

            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                for (int i = 0; i < size; ++i) {
                    oos.writeObject(objects[i]);
                }
                len += 4;
                len += baos.size();
            }

            return len;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
