package cn.edu.cug.cs.gtl.common;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by ZhenwenHe on 2016/12/9.
 */
class IdentifierImpl implements Identifier {
    private static final long serialVersionUID = 1L;

    private long leastSigBits;
    private long mostSigBits;

    public IdentifierImpl() {
        this(0, 0);
    }

    public IdentifierImpl(long data) {
        this(0, data);
    }

    public IdentifierImpl(long mostSigBits, long leastSigBits) {
        this.leastSigBits = leastSigBits;
        this.mostSigBits = mostSigBits;
    }

    public IdentifierImpl(UUID uuid) {
        this(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    @Override
    public long lowValue() {
        return leastSigBits;
    }

    @Override
    public long highValue() {
        return this.mostSigBits;
    }

    @Override
    public int intValue() {
        return (int) leastSigBits;
    }

    @Override
    public Object clone() {
        return new IdentifierImpl(this.mostSigBits, this.leastSigBits);
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        this.mostSigBits = in.readLong();
        this.leastSigBits = in.readLong();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeLong(this.mostSigBits);
        out.writeLong(this.leastSigBits);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 16;
    }

    @Override
    public String toString() {
        return new UUID(this.mostSigBits, this.leastSigBits).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Identifier)) return false;
        Identifier that = (Identifier) o;
        return this.highValue() == that.highValue() &&
                this.lowValue() == that.lowValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(leastSigBits, mostSigBits);
    }

    @Override
    public void reset(long v) {
        this.leastSigBits = v;
    }

    @Override
    public void reset(long highValue, long lowValue) {
        this.mostSigBits = highValue;
        this.leastSigBits = lowValue;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Identifier) {
            this.leastSigBits = ((Identifier) i).lowValue();
            this.mostSigBits = ((Identifier) i).highValue();
        }
    }

    @Override
    public void increase() {
        ++this.leastSigBits;
    }

    @Override
    public byte byteValue() {
        return (byte) this.leastSigBits;
    }

    @Override
    public short shortValue() {
        return (short) this.leastSigBits;
    }

    @Override
    public int compare(Identifier i) {
        if (this.leastSigBits > i.longValue())
            return 1;
        else if (this.leastSigBits == i.longValue())
            return 0;
        else
            return -1;
    }

    @Override
    public int compareTo(@NotNull Identifier o) {
        return compare(o);
    }
}
