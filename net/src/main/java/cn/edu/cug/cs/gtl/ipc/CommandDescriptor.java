package cn.edu.cug.cs.gtl.ipc;


import cn.edu.cug.cs.gtl.io.Storable;
import org.apache.hadoop.io.Writable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CommandDescriptor implements Storable, Writable {
    private static final long serialVersionUID = 1L;

    String name;
    List<ParameterDescriptor> parameterDescriptors;

    public CommandDescriptor(String name, List<ParameterDescriptor> parameterDescriptors) {
        this.name = name;
        this.parameterDescriptors = new ArrayList<>();
        this.parameterDescriptors.addAll(parameterDescriptors);
    }

    public CommandDescriptor(String name) {
        this.name = name;
        this.parameterDescriptors = new ArrayList<>();
    }

    public CommandDescriptor() {
        this.name = "unknown";
        this.parameterDescriptors = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterDescriptor> getParameterDescriptors() {
        return parameterDescriptors;
    }

    public void setParameterDescriptors(List<ParameterDescriptor> parameterDescriptors) {
        this.parameterDescriptors.clear();
        this.parameterDescriptors.addAll(parameterDescriptors);
    }

    public void addParameterDescriptor(ParameterDescriptor pd) {
        this.parameterDescriptors.add(pd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandDescriptor)) return false;

        CommandDescriptor that = (CommandDescriptor) o;

        if (!getName().equals(that.getName())) return false;
        return getParameterDescriptors() != null ? getParameterDescriptors().equals(that.getParameterDescriptors()) : that.getParameterDescriptors() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getParameterDescriptors() != null ? getParameterDescriptors().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandDescriptor{" +
                "name='" + name + '\'' +
                ", parameterDescriptors=" + parameterDescriptors +
                '}';
    }

    @Override
    public Object clone() {
        CommandDescriptor cd = new CommandDescriptor(this.name);
        for (ParameterDescriptor p : this.parameterDescriptors)
            cd.addParameterDescriptor((ParameterDescriptor) p.clone());
        return cd;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof CommandDescriptor) {
            CommandDescriptor cd = (CommandDescriptor) (i);
            this.name = cd.name;
            this.parameterDescriptors.clear();
            try {
                for (ParameterDescriptor p : cd.parameterDescriptors)
                    this.parameterDescriptors.add((ParameterDescriptor) p.clone());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            int len = in.readInt();
            byte[] bs = new byte[len];
            in.readFully(bs, 0, len);
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bs));
            this.name = (String) ois.readObject();
            Integer s = (Integer) ois.readObject();
            this.parameterDescriptors.clear();
            for (int i = 0; i < s.intValue(); ++i) {
                this.parameterDescriptors.add((ParameterDescriptor) ois.readObject());
            }
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
            oos.writeObject(this.name);
            Integer s = new Integer(this.parameterDescriptors.size());
            oos.writeObject(s);
            if (s.intValue() > 0) {
                for (ParameterDescriptor p : this.parameterDescriptors) {
                    oos.writeObject(p);
                }
            }

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
            oos.writeObject(this.name);
            int s = this.parameterDescriptors.size();
            oos.writeInt(s);
            if (s <= 0) return baos.size();
            for (ParameterDescriptor p : this.parameterDescriptors) {
                oos.writeObject(p);
            }
            return baos.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static CommandDescriptor read(DataInput in) throws IOException {
        CommandDescriptor pd = new CommandDescriptor();
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
