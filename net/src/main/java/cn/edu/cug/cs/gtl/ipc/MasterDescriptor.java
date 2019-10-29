package cn.edu.cug.cs.gtl.ipc;

import cn.edu.cug.cs.gtl.io.Storable;
import org.apache.hadoop.io.Writable;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class MasterDescriptor implements Storable, Writable {
    private static final long serialVersionUID = 1L;

    String ipAddress;
    int port;
    transient MasterProtocol master;//不作序列化，需要通过getProxy或waitForProxy获取
    List<SlaveDescriptor> slaves;

    public MasterDescriptor(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        master = null;
        slaves = new ArrayList<SlaveDescriptor>();
    }

    public MasterDescriptor() {
        ipAddress = "127.0.0.1";
        port = 8888;
        master = null;
        slaves = new ArrayList<SlaveDescriptor>();
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetSocketAddress getAddress() {
        return new InetSocketAddress(ipAddress, port);
    }

    public MasterProtocol getMaster() {
        return master;
    }

    public void setMaster(MasterProtocol master) {
        this.master = master;
    }

    public List<SlaveDescriptor> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<SlaveDescriptor> slaves) {
        this.slaves.clear();
        this.slaves.addAll(slaves);
    }

    public void addSlave(SlaveDescriptor sd) {
        this.slaves.add(sd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MasterDescriptor)) return false;

        MasterDescriptor that = (MasterDescriptor) o;

        if (getPort() != that.getPort()) return false;
        if (!ipAddress.equals(that.ipAddress)) return false;
        if (getMaster() != null ? !getMaster().equals(that.getMaster()) : that.getMaster() != null) return false;
        return getSlaves() != null ? getSlaves().equals(that.getSlaves()) : that.getSlaves() == null;
    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + getPort();
        result = 31 * result + (getMaster() != null ? getMaster().hashCode() : 0);
        result = 31 * result + (getSlaves() != null ? getSlaves().hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() {
        MasterDescriptor md = new MasterDescriptor(this.ipAddress, this.port);
        md.master = this.master;
        md.slaves.addAll(this.slaves);
        return md;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof MasterDescriptor) {
            MasterDescriptor md = (MasterDescriptor) (i);
            this.ipAddress = md.ipAddress;
            this.port = md.port;
            this.master = md.master;
            this.slaves.clear();
            this.slaves.addAll(md.slaves);
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            int len = in.readInt();
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            this.ipAddress = (String) ois.readObject();
            Integer s = (Integer) ois.readObject();
            this.port = s.intValue();
            /*
            Boolean b =(Boolean) ois.readObject();
            if(!b)
                this.master=(MasterProtocol)ois.readObject();
            else
                this.master=null;
             */
            ois.close();

            s = in.readInt();
            this.slaves.clear();
            for (int i = 0; i < s.intValue(); ++i) {
                this.slaves.add(SlaveDescriptor.read((DataInput) in));
            }
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.ipAddress);
            Integer s = new Integer(this.port);
            oos.writeObject(s);
            /*
            Boolean b = new Boolean(this.master==null);
            oos.writeObject(b);
            if(!b)
                oos.writeObject(b);
            */
            byte[] bs = baos.toByteArray();
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
            oos.close();

            out.writeInt(this.slaves.size());
            if (this.slaves.size() > 0) {
                for (SlaveDescriptor p : this.slaves) {
                    p.write(out);
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.ipAddress);
            Integer s = new Integer(this.port);
            oos.writeObject(s);
            /*
            Boolean b = new Boolean(this.master==null);
            oos.writeObject(b);
            if(!b)
                oos.writeObject(b);
            */
            long c = baos.size();
            oos.close();
            c += 4;

            for (SlaveDescriptor p : this.slaves) {
                c += p.getByteArraySize();
            }
            return c;
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static MasterDescriptor read(DataInput in) throws IOException {
        MasterDescriptor pd = new MasterDescriptor();
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
