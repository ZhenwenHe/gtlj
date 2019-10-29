package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.common.Property;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.common.Variant;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhenwenHe on 2016/12/27.
 */
class PropertySetImpl extends HashMap<String, Variant> implements PropertySet {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public PropertySetImpl() {
    }

    /**
     * @param data
     */
    public PropertySetImpl(byte[] data) {
        try {
            loadFromByteArray(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param p
     */
    @Override
    public void put(Property p) {
        super.put(p.getName(), (Variant) p);
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof PropertySet) {
            super.clear();
            for (Map.Entry<String, Variant> es : ((PropertySet) i).entrySet()) {
                super.put(es.getKey(), es.getValue());
            }
        }
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        super.clear();
        int count = dis.readInt();
        String key = null;
        Variant value = null;
        for (int i = 0; i < count; ++i) {
            //read key string
            key = Variant.readString(dis);
            value = new Variant();
            value.load(dis);
            super.put(key, value);
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        int c = this.size();
        dos.writeInt(c);
        for (Map.Entry<String, Variant> es : entrySet()) {
            Variant.writeString(dos, (String) (es.getKey()));
            ((Variant) es.getValue()).store(dos);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        int c = 4;// entry count;
        String s = null;
        for (Map.Entry<String, Variant> es : entrySet()) {
            s = (String) (es.getKey());
            c += 4;//key string length
            c += s.length() * 2;
            c += ((Variant) es.getValue()).getByteArraySize();
        }


        return c;
    }

    @Override
    public Variant getProperty(String key) {
        return super.get((Object) key);
    }
}
