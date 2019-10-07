package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.util.ArrayUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.util.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by ZhenwenHe on 2017/2/12.
 */
public class EntryImpl implements Entry {
    private static final long serialVersionUID = 1L;

    Identifier identifier;
    Shape shape;
    byte[] data;//可以为空

    public EntryImpl(Identifier identifier, Shape shape, byte[] data) {
        this.identifier = (Identifier) identifier.clone();
        this.shape = (Shape) shape.clone();
        if (data != null) {
            this.data = ArrayUtils.createByteArray(data);
        } else
            this.data = null;
    }

    public EntryImpl(Identifier identifier, Shape shape) {
        this.identifier = (Identifier) identifier.clone();
        this.shape = (Shape) shape.clone();
        this.data = null;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setIdentifier(Identifier id) {
        if (id != null)
            this.identifier.reset(id.longValue());
    }

    @Override
    public Object clone() {
        return (Object) new EntryImpl(this.identifier, this.shape, this.data);
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
    public void copyFrom(Object i) {
        if (i instanceof Entry) {
            Entry e = (Entry) i;
            this.identifier = (Identifier) e.getIdentifier().clone();
            this.shape = (Shape) e.getShape().clone();
            byte[] d = e.getData();
            if (d == null)
                this.data = null;
            else
                this.data = ArrayUtils.createByteArray(d);
        }
    }

    @Override
    public void setIdentifier(long id) {
        this.identifier.reset(id);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public void setData(byte[] data) {
        if (data != null) {
            this.data = ArrayUtils.createByteArray(data);
        } else
            this.data = null;
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        this.identifier.load(dis);
        this.shape.load(dis);
        int len = dis.readInt();
        if (len > 0) {
            this.data = new byte[len];
            dis.readFully(this.data);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        this.identifier.store(dos);
        this.shape.store(dos);
        if (this.data == null)
            dos.writeInt(0);
        else {
            dos.writeInt(this.data.length);
            dos.write(this.data);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long sum = this.identifier.getByteArraySize();
        sum += this.shape.getByteArraySize();
        sum += 4;//data length
        if (this.data != null)
            sum += this.data.length;
        return sum;
    }

    @Override
    public long getDataLength() {
        return this.data == null ? 0 : this.data.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryImpl)) return false;

        EntryImpl entry = (EntryImpl) o;

        if (!getIdentifier().equals(entry.getIdentifier())) return false;
        if (!getShape().equals(entry.getShape())) return false;
        return Arrays.equals(getData(), entry.getData());
    }

    @Override
    public int hashCode() {
        int result = getIdentifier().hashCode();
        result = 31 * result + getShape().hashCode();
        result = 31 * result + Arrays.hashCode(getData());
        return result;
    }

    @Override
    public String toString() {
        return "EntryImpl{" +
                "identifier=" + identifier +
                ", shape=" + shape +
                ", data=" + Arrays.toString(data) +
                '}';
    }


}
