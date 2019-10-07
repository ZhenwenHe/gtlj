package gtl.common.impl;

import gtl.common.Identifier;
import gtl.common.PropertySet;
import gtl.common.Variant;

/**
 * Created by ZhenwenHe on 2017/3/15.
 */
public class CommonImpl {
    public static Variant createVariant() {
        return new Variant();
    }

    public static PropertySet createPropertySet() {
        return new PropertySetImpl();
    }

    public static Identifier createIdentifier(long v) {
        return new IdentifierImpl(v);
    }

    public static Identifier[] createIdentifierArray(Identifier[] c) {
        Identifier[] r = new Identifier[c.length];
        for (int i = 0; i < r.length; i++) {
            r[i] = (Identifier) c[i].clone();
        }
        return r;
    }

    public static byte[] createByteArray(byte[] ba) {
        byte[] r = new byte[ba.length];
        System.arraycopy(ba, 0, r, 0, r.length);
        return r;
    }

    public static byte[] createByteArray(int size, byte defaultValue) {
        byte[] r = new byte[size];
        for (int i = 0; i < size; i++)
            r[i] = defaultValue;
        return r;
    }

    public static byte[][] createByteArray(byte[][] baa) {
        byte[][] r = new byte[baa.length][];
        for (int i = 0; i < baa.length; i++) {
            r[i] = new byte[baa[i].length];
            System.arraycopy(baa[i], 0, r[i], 0, baa[i].length);
        }
        return r;
    }
}
