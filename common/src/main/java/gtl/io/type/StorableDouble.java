package gtl.io.type;

import gtl.io.StorableComparable;
import gtl.io.StorableComparable;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StorableDouble implements StorableComparable<StorableDouble> {
    @Override
    public Object clone() {
        return null;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return false;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return false;
    }

    @Override
    public int compareTo(@NotNull StorableDouble o) {
        return 0;
    }
}
