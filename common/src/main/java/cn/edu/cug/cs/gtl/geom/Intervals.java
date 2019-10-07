package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.util.Collection;

/**
 * Created by ZhenwenHe on 2017/3/12.
 */
public interface Intervals extends Collection<Interval>, Serializable {
    Identifier getIdentifier();

    void setIdentifier(Identifier i);

    Interval get(int i);

    int find(Interval i);
}
