package cn.edu.cug.cs.gtl.ipc;

import cn.edu.cug.cs.gtl.io.Storable;
import org.apache.hadoop.io.Writable;

import java.io.*;
import java.net.InetSocketAddress;

public class SlaveDescriptor implements Storable, Writable {
    private static final long serialVersionUID = 1L;

    String ipAddress;
    int port;
    transient SlaveProtocol slave;//不作序列化，需要通过getProxy或waitForProxy获取

    public SlaveDescriptor(String ip, int port) {
        this.ipAddress = ip;
        this.port = port;
        this.slave = null;
    }

    public SlaveDescriptor() {
        this.ipAddress = "127.0.0.1";
        this.port = 6666;
        this.slave = null;
    }

    public String getIPAddress() {
        return ipAddress;
    }

    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public InetSocketAddress getAddress() {
        return new InetSocketAddress(ipAddress, port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SlaveProtocol getSlave() {
        return slave;
    }

    public void setSlave(SlaveProtocol sp) {
        slave = sp;
    }

    @Override
    public String toString() {
        return "SlaveDescriptor{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port=" + port +
                ", slave=" + slave +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SlaveDescriptor)) return false;

        SlaveDescriptor that = (SlaveDescriptor) o;

        if (getPort() != that.getPort()) return false;
        if (!ipAddress.equals(that.ipAddress)) return false;
        return getSlave() != null ? getSlave().equals(that.getSlave()) : that.getSlave() == null;
    }

    @Override
    public int hashCode() {
        int result = ipAddress.hashCode();
        result = 31 * result + getPort();
        result = 31 * result + (getSlave() != null ? getSlave().hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() {
        SlaveDescriptor sd = new SlaveDescriptor(this.ipAddress, this.port);
        sd.slave = this.slave;
        return sd;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof SlaveDescriptor) {
            SlaveDescriptor sd = (SlaveDescriptor) (i);
            this.slave = sd.slave;
            this.port = sd.port;
            this.ipAddress = sd.ipAddress;
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
            this.port = ((Integer) ois.readObject()).intValue();
            /*
            Boolean b = (Boolean) ois.readObject();
            if(!b)
                this.slave = (SlaveProtocol)ois.readObject();
            else
                this.slave=null;
            */
            ois.close();
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
            Boolean b = new Boolean(this.slave==null);
            oos.writeObject(b);
            if(!b)
                oos.writeObject(this.slave);
            */
            byte[] bs = baos.toByteArray();
            out.writeInt(bs.length);
            out.write(bs, 0, bs.length);
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
            Boolean b = new Boolean(this.slave == null);
            oos.writeObject(b);
            if (!b)
                oos.writeObject(this.slave);
            return baos.size();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static SlaveDescriptor read(DataInput in) throws IOException {
        SlaveDescriptor pd = new SlaveDescriptor();
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
