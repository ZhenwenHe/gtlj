package cn.edu.cug.cs.gtl.geom;

import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ColorSequenceImpl extends ArrayList<Color> implements ColorSequence {
    private static final long serialVersionID = 1L;

    public ColorSequenceImpl(int initialCapacity) {
        super(initialCapacity);
    }

    public ColorSequenceImpl() {
    }

    public ColorSequenceImpl(@NotNull Collection<? extends Color> c) {
        super(c);
    }

    @Override
    public ColorSequenceImpl clone() {
        ArrayList<Color> list = new ArrayList<>(this.size());
        for (Color c : this)
            ((ArrayList<Color>) list).add(c.clone());
        return new ColorSequenceImpl(list);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        int s = in.readInt();
        if (s > 0) {
            this.ensureCapacity(s);
            for (int i = 0; i < s; ++i) {
                Color c = new Color();
                c.load(in);
                this.set(i, c);
            }
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        int s = size();
        out.writeInt(s);
        if (s > 0) {
            for (Color c : this)
                c.store(out);
        }
        return true;
    }
}
