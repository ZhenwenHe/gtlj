package cn.edu.cug.cs.gtl.sampling;

import java.util.Iterator;

/**
 * A simple abstract iterator which implements the remove method as unsupported operation.
 *
 * @param <T> The type of iterator data.
 */
abstract class SampledIterator<T> implements Iterator<T> {
    @Override
    public void remove() {
        throw new UnsupportedOperationException("Do not support this operation.");
    }

}