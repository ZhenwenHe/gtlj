package cn.edu.cug.cs.gtl.ipc;

import cn.edu.cug.cs.gtl.io.Storable;
import org.apache.hadoop.io.Writable;

import java.io.*;

public class DataDescriptor<T extends Storable>
        implements Storable, Writable {
    private static final long serialVersionUID = 1L;

    T data;

    public DataDescriptor(T data) {
        this.data = data;
    }

    public DataDescriptor() {
        this.data = null;
    }

    public Storable getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public Object clone() {
        return new DataDescriptor((Storable) this.data.clone());
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof DataDescriptor) {
            this.data = (T) ((DataDescriptor) i).data.clone();
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            int len = in.readInt();
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            this.data = (T) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.data);
            byte[] bs = baos.toByteArray();
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.data);
            return baos.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.data.getByteArraySize();
    }


    public static DataDescriptor read(DataInput in) throws IOException {
        DataDescriptor pd = new DataDescriptor();
        pd.readFields(in);
        return pd;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        store(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        load(in);
    }
}
