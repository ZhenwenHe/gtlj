package cn.edu.cug.cs.gtl.io.wkb;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Allows reading a stream of Java primitive datatypes from an underlying
 * with the representation being in either common byte ordering.
 */
class ByteOrderDataInputStream implements Serializable {
    private static final long serialVersionUID = 1L;

    private int byteOrder = ByteOrderValues.BIG_ENDIAN;
    private InputStream stream;
    // buffers to hold primitive datatypes
    private byte[] buf1 = new byte[1];
    private byte[] buf4 = new byte[4];
    private byte[] buf8 = new byte[8];

    public ByteOrderDataInputStream() {
        this.stream = null;
    }

    public ByteOrderDataInputStream(InputStream stream) {
        this.stream = stream;
    }

    /**
     * Allows a single ByteOrderDataInputStream to be reused
     * on multiple InputStreams.
     *
     * @param stream
     */
    public void setInputStream(InputStream stream) {
        this.stream = stream;
    }

    public void setOrder(int byteOrder) {
        this.byteOrder = byteOrder;
    }

    /**
     * Reads a byte value
     *
     * @return the byte read
     */
    public byte readByte()
            throws IOException {
        stream.read(buf1);
        return buf1[0];
    }

    public int readInt()
            throws IOException {
        stream.read(buf4);
        return ByteOrderValues.getInt(buf4, byteOrder);
    }

    public long readLong()
            throws IOException {
        stream.read(buf8);
        return ByteOrderValues.getLong(buf8, byteOrder);
    }

    public double readDouble()
            throws IOException {
        stream.read(buf8);
        return ByteOrderValues.getDouble(buf8, byteOrder);
    }

}
