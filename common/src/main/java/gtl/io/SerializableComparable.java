package gtl.io;

import gtl.io.Serializable;

/**
 * 请使用StorableComparable接口
 * @param <T>
 */
@Deprecated
public interface SerializableComparable <T> extends Serializable, Comparable<T> {
}
