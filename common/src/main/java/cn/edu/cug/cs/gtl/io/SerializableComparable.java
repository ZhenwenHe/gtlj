package cn.edu.cug.cs.gtl.io;

import cn.edu.cug.cs.gtl.io.Serializable;

/**
 * 请使用StorableComparable接口
 *
 * @param <T>
 */
@Deprecated
public interface SerializableComparable<T> extends Serializable, Comparable<T> {
}
