package cn.edu.cug.cs.gtl.common;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.util.UUID;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface Identifier extends Serializable, Comparable<Identifier> {
    byte byteValue();

    short shortValue();

    int intValue();

    /**
     * @return
     */
    default long longValue() {
        return lowValue();
    }

    long highValue();

    long lowValue();

    void reset(long v);

    void reset(long mostSigBits, long leastSigBits);

    void increase();

    int compare(Identifier i);

    static Identifier create() {
        return new IdentifierImpl(-1L);
    }

    static Identifier create(long l) {
        return new IdentifierImpl(l);
    }

    static Identifier create(Identifier i) {
        return new IdentifierImpl(i.highValue(), i.lowValue());
    }

    static Identifier randomIdentifier() {
        return new IdentifierImpl(UUID.randomUUID());
    }
}
