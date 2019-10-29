package cn.edu.cug.cs.gtl.ipc;

import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.IOException;

public class ResultDescriptor<T extends Storable> extends DataDescriptor<T> {
    private static final long serialVersionUID = 1L;

    public ResultDescriptor(Variant v) {
        super((T) v);
    }

    public ResultDescriptor(T v) {
        super(v);
    }

    public ResultDescriptor() {
        super();
    }

    public static ResultDescriptor read(DataInput in) throws IOException {
        ResultDescriptor pd = new ResultDescriptor();
        pd.readFields(in);
        return pd;
    }

    @Override
    public Object clone() {
        try {
            return new ResultDescriptor((Storable) (getData().clone()));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getResult() {
        return (Object) this.data;
    }

    public void setResult(Object i) {
        this.data = (T) i;
    }
}
