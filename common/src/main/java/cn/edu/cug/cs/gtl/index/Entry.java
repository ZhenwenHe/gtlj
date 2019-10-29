package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.index.shape.Shape;
import cn.edu.cug.cs.gtl.io.Serializable;

/**
 * Created by ZhenwenHe on 2016/12/7.
 */
public interface Entry extends Serializable {
    Identifier getIdentifier();

    void setIdentifier(Identifier id);

    Shape getShape();

    void setShape(Shape s);

    void setIdentifier(long id);

    byte[] getData();

    void setData(byte[] data);

    long getDataLength();

    static Entry create(Identifier identifier, Shape shape, byte[] data) {
        return new EntryImpl(identifier, shape, data);
    }
}
