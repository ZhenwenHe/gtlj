package gtl.geom;

import gtl.io.Serializable;

import java.util.Collection;

public interface ColorSequence extends Collection<Color>, gtl.io.Serializable {

    static ColorSequence create(){
        return new ColorSequenceImpl();
    }
}
