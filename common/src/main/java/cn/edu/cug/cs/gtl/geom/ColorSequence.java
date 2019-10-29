package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.util.Collection;

public interface ColorSequence extends Collection<Color>, Serializable {

    static ColorSequence create() {
        return new ColorSequenceImpl();
    }
}
