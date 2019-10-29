package cn.edu.cug.cs.gtl.common;


import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.util.Map;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface PropertySet extends Map<String, Variant>, Serializable {
    void put(Property p);

    Variant getProperty(String key);

    static PropertySet create() {
        return new PropertySetImpl();
    }
}
