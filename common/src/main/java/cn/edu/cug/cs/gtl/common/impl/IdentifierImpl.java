package cn.edu.cug.cs.gtl.common.impl;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Variant;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by ZhenwenHe on 2016/12/9.
 */
class IdentifierImpl implements Identifier {


    private static final long serialVersionUID = 1135478489292922975L;
    /*
     * The most significant 64 bits of this UUID.
     *
     * @serial
     */
    private long mostSigBits;

    /*
     * The least significant 64 bits of this UUID.
     *
     * @serial
     */
    private long leastSigBits;


    public IdentifierImpl() {

        this.leastSigBits = 0;
        this.mostSigBits = 0;
    }

    public IdentifierImpl(long leastSigBits) {
        this.leastSigBits = leastSigBits;
        this.mostSigBits = 0;
    }

    /**
     * Constructs a new {@code IdentifierImpl} using the specified data.  {@code
     * mostSigBits} is used for the most significant 64 bits of the {@code
     * IdentifierImpl} and {@code leastSigBits} becomes the least significant 64 bits of
     * the {@code IdentifierImpl}.
     *
     * @param mostSigBits  The most significant bits of the {@code IdentifierImpl}
     * @param leastSigBits The least significant bits of the {@code IdentifierImpl}
     */
    public IdentifierImpl(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
    }

    @Override
    public long longValue() {
        return leastSigBits;
    }

    @Override
    public long highValue() {
        return this.mostSigBits;
    }

    @Override
    public long lowValue() {
        return this.leastSigBits;
    }

    @Override
    public int intValue() {
        return (int) leastSigBits;
    }

    @Override
    public Object clone() {
        return new IdentifierImpl(this.leastSigBits, this.mostSigBits);
    }


    @Override
    public boolean load(DataInput dis) throws IOException {
        this.leastSigBits = dis.readLong();
        this.mostSigBits = dis.readLong();
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        dos.writeLong(this.leastSigBits);
        dos.writeLong(this.mostSigBits);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 16;
    }

    @Override
    public String toString() {
        return new UUID(this.leastSigBits, this.mostSigBits).toString();
    }


    @Override
    public void reset(long leastSigBits) {
        this.leastSigBits = leastSigBits;
        this.mostSigBits = 0;
    }

    @Override
    public void reset(long mostSigBits, long leastSigBits) {
        this.mostSigBits = mostSigBits;
        this.leastSigBits = leastSigBits;
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
        if (this.leastSigBits == Long.MAX_VALUE) {
            ++this.mostSigBits;
        } else
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
        return compareTo(i);
    }


    @Override
    public int compareTo(@NotNull Identifier o) {
        if (this.highValue() > o.highValue())
            return 1;
        else {
            if (this.highValue() == o.highValue()) {
                if (this.lowValue() > o.lowValue())
                    return 1;
                else if (this.lowValue() == o.lowValue())
                    return 0;
                else
                    return -1;
            } else {
                return -1;
            }
        }
    }
}
