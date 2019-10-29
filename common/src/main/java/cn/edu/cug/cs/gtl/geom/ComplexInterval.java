package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.common.Identifier;

public interface ComplexInterval extends Interval {
    String getName();

    Identifier getOrder();

    void setOrder(Identifier i);

    void setOrder(long i);

    Identifier getParentID();

    void setParentID(Identifier v);

    void setParentID(long v);

    void setData(byte[] data);

    byte[] getData();

    static ComplexInterval create(IntervalType type, double low, double high, String label, long pid, long order) {
        return new ComplexIntervalImpl(type, low, high, label, pid, order);
    }

    static ComplexInterval create(IntervalType type, double low, double high, String label, Identifier pid, Identifier order) {
        return new ComplexIntervalImpl(type, low, high, label, pid, order);
    }

    static ComplexInterval create(double low, double high, String label, long pid, long order) {
        return new ComplexIntervalImpl(low, high, label, pid, order);
    }

    static ComplexInterval create(Interval i, String label, long pid, long order) {
        return new ComplexIntervalImpl(i, label, pid, order);
    }

    static ComplexInterval create() {
        return new ComplexIntervalImpl();
    }
}
