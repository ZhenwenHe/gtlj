package cn.edu.cug.cs.gtl.ml.distances;

public interface DistanceMetrics<T> {
    double distance(T a, T b);
}
