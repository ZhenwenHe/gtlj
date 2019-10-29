package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.util.ArrayUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.util.ArrayUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

class ComplexIntervalImpl extends IntervalImpl implements ComplexInterval {
    private static final long serialVersionUID = 1L;
    Identifier pid;//parent ID
    Identifier order;// order in the parent object
    String label;//label string
    byte[] data;

    public ComplexIntervalImpl(IntervalType type, double low, double high, String label, long pid, long order) {
        super(type, low, high);
        this.label = label;
        this.pid = Identifier.create(pid);
        this.order = Identifier.create(order);
        this.data = null;
    }

    public ComplexIntervalImpl(IntervalType type, double low, double high, String label, Identifier pid, Identifier order) {
        super(type, low, high);
        this.label = label;
        this.pid = Identifier.create(pid.longValue());
        this.order = Identifier.create(order.longValue());
        this.data = null;
    }

    public ComplexIntervalImpl(double low, double high, String label, long pid, long order) {
        super(low, high);
        this.label = label;
        this.pid = Identifier.create(pid);
        this.order = Identifier.create(order);
        this.data = null;
    }

    public ComplexIntervalImpl(Interval i, String label, long pid, long order) {
        super(i.getType(), i.getLowerBound(), i.getUpperBound());
        this.label = label;
        this.pid = Identifier.create(pid);
        this.order = Identifier.create(order);
        this.data = null;
    }

    public ComplexIntervalImpl() {
        super();
        this.label = new String();
        this.pid = Identifier.create(-1L);
        this.order = Identifier.create(-1L);
        this.data = null;
    }

    public String getName() {
        return label;
    }

    public Identifier getOrder() {
        return order;
    }

    public void setOrder(Identifier i) {
        order.reset(i.longValue());
    }

    public void setOrder(long i) {
        order.reset(i);
    }

    public Identifier getParentID() {
        return pid;
    }

    public void setParentID(Identifier v) {
        pid.reset(v.longValue());
    }

    public void setParentID(long v) {
        pid.reset(v);
    }

    @Override
    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplexIntervalImpl)) return false;
        if (!super.equals(o)) return false;

        ComplexIntervalImpl that = (ComplexIntervalImpl) o;

        if (!pid.equals(that.pid)) return false;
        if (!order.equals(that.order)) return false;
        if (getName().equals(that.getName())) {
            return ArrayUtils.compare(this.data, that.getData()) == 0;
        } else
            return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + pid.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ComplexIntervalImpl{" +
                "pid=" + pid +
                ", order=" + order +
                ", label='" + label + '\'' + super.toString() +
                '}';
    }

    @Override
    public Object clone() {
        ComplexIntervalImpl li = new ComplexIntervalImpl(getType(), getLowerBound(), getUpperBound(), label, pid, order);
        li.data = ArrayUtils.createByteArray(this.data);
        return (Object) li;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof ComplexIntervalImpl) {
            reset(((Interval) i).getType(), ((Interval) i).getLowerBound(), ((Interval) i).getUpperBound());
            this.label = ((ComplexIntervalImpl) i).label;
            this.pid = ((ComplexIntervalImpl) i).pid;
            this.order = ((ComplexIntervalImpl) i).order;
            this.data = ArrayUtils.createByteArray(((ComplexIntervalImpl) i).data);
        }
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        super.load(dis);
        int len = dis.readInt();
        byte[] cc = new byte[len];
        dis.readFully(cc);
        this.label = new String(cc, 0, cc.length);
        this.pid.load(dis);
        this.order.load(dis);
        len = 0;
        len = dis.readInt();
        if (len > 0) {
            this.data = new byte[len];
            dis.readFully(this.data);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        super.store(dos);
        byte[] bs = label.getBytes();
        dos.writeInt(bs.length);
        dos.write(bs);
        //dos.writeLong(this.pid.longValue());
        //dos.writeLong(this.order.longValue());
        this.pid.store(dos);
        this.order.store(dos);
        int s = this.data == null ? 0 : this.data.length;
        dos.writeInt(s);
        if (s > 0)
            dos.write(this.data);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long s = 8 * 2 + 4 + label.getBytes().length + 4 + pid.getByteArraySize() + order.getByteArraySize();
        if (data != null)
            s += data.length;
        return s;
    }

}
