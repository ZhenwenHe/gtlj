package cn.edu.cug.cs.gtl.io.type;

import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.io.StorableComparable;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StorableString implements StorableComparable<StorableString> {
    private static final long serialVersionUID = 4419823521768644244L;

    private String value = null;

    public StorableString() {
    }

    public StorableString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public Object clone() {
        return new StorableString(this.value);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.value = StringUtils.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        StringUtils.store(this.value, out);
        return true;
    }

    @Override
    public int compareTo(@NotNull StorableString o) {
        return this.value.compareTo(o.value);
    }
}
