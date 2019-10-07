package cn.edu.cug.cs.gtl.index.knn;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.Entry;

public class NNEntry implements Comparable<NNEntry>, java.io.Serializable {
    private static final long serialVersionUID = 1L;

    public Identifier identifier;
    public Entry entry;
    public double minDistance;

    public NNEntry(Identifier id, Entry e, double f) {

        this.identifier = Identifier.create(id.longValue());
        this.entry = e;
        this.minDistance = f;
    }

    public NNEntry() {
    }

    @Override
    public int compareTo(NNEntry o) {
        if (this.minDistance > o.minDistance)
            return 1;
        else if (this.minDistance == o.minDistance)
            return 0;
        else
            return -1;
    }
}
