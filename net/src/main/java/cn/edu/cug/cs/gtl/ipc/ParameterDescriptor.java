package cn.edu.cug.cs.gtl.ipc;

import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.*;

public class ParameterDescriptor<T extends Storable> extends DataDescriptor<T> {
    private static final long serialVersionUID = 1L;


    public ParameterDescriptor(T parameter) {
        super(parameter);
    }

    public ParameterDescriptor() {
        super();
    }

    public Object getParameter() {
        return getData();
    }

    public void setParameter(T parameter) {
        setData(parameter);
    }

    public void setParameter(Variant parameter) {
        setData((T) parameter);
    }

    public static ParameterDescriptor read(DataInput in) throws IOException {
        ParameterDescriptor pd = new ParameterDescriptor();
        pd.readFields(in);
        return pd;
    }

    @Override
    public Object clone() {
        return new ParameterDescriptor((T) (getData().clone()));
    }
}
