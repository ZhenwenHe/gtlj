package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Identifier;

public interface FeatureTypeFinder {
    /**
     * 通过FeatureType的名称获取该对象
     *
     * @param name
     * @return
     */
    FeatureType find(String name);

    /**
     * 通过FeatureType的ID获取该对象
     *
     * @param identifier
     * @return
     */
    FeatureType find(Identifier identifier);

    /**
     * 查找Feature对应的FeatureType
     *
     * @param f
     * @return
     */
    default FeatureType find(Feature f) {
        if (f == null) return null;
        return find(f.getFeatureTypeName());
    }
}
