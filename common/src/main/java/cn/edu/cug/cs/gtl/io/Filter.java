package cn.edu.cug.cs.gtl.io;

import java.util.function.Predicate;

public interface Filter<T> extends Predicate<T>, Storable {
}
