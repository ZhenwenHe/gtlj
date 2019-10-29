package cn.edu.cug.cs.gtl.ml.distances;

import cn.edu.cug.cs.gtl.array.Array;

import java.util.List;

public interface DistanceMetrics<T> {
    double distance(T a, T b);

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param a m条时序数据的集合
     * @param b n条时序数据的集合
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    default Array distances(List<T> a, List<T> b) {
        int m = a.size();
        int n = b.size();
        double[] dist = new double[m * n];
        int k = 0;
        for (int i = 0; i < m; ++i) {
            T s11 = a.get(i);
            for (int j = 0; j < n; ++j) {
                T s22 = b.get(j);
                dist[k] = distance(s11, s22);
                ++k;
            }
        }
        return Array.of(n, m, dist);
    }

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param a m条时序数据的集合
     * @param b n条时序数据的集合
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    default Array distances(T[] a, T[] b) {
        int m = a.length;
        int n = b.length;
        double[] dist = new double[m * n];
        int k = 0;
        for (int i = 0; i < m; ++i) {
            T s11 = a[i];
            for (int j = 0; j < n; ++j) {
                T s22 = b[j];
                dist[k] = distance(s11, s22);
                ++k;
            }
        }
        return Array.of(n, m, dist);
    }
}
