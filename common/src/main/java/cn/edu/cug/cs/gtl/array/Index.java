package cn.edu.cug.cs.gtl.array;

import cn.edu.cug.cs.gtl.math.Sequence;
import cn.edu.cug.cs.gtl.math.Sequence;

public class Index extends Sequence {
    private static final long serialVersionUID = -1798002402592301876L;

    public Index(int dBegin, int end, int dStep) {
        super(dBegin * 1.0, end * 1.0, dStep * 1.0);
    }

    public Index(int dBegin, int end) {
        super(dBegin, end, 1.0);
    }

    public Index(int dBegin) {
        super(dBegin, dBegin + 1.0, 1.0);
    }

    public int ibegin() {
        return (int) super.begin();
    }

    public int iend() {
        return (int) super.end();
    }

    @Override
    public Object clone() {
        return new Index((int) begin(), (int) end(), (int) step());
    }


}
