package cn.edu.cug.cs.gtl.series.common.saxtd;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;

public class Utils {
    /**
     * @param ts
     * @param w
     * @return
     */
    public static double[] saxtd(double[] ts, int w) {
        try {
            double[] mid = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(ts, w);
            double[] d = new double[w + 1];
            double perSegment = (double) ts.length / (double) w;
            double i = 0.0;
            for (int count = 0; count < w; count++) {
                d[count] = ts[(int) i] - mid[count];
                i = count * perSegment;
            }
            d[w] = ts[(int) i] - mid[w - 1];
            return d;
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * saxtd distance
     *
     * @param a
     * @param b
     * @param w
     * @return
     */
    private static double distance(double[] a, double[] b, int w) {
        double[] t1 = saxtd(a, w);
        double[] t2 = saxtd(b, w);
        double s = 0.0;
        for (int i = 0; i <= w; ++i) {
            double d = t2[i] - t1[i];
            d = d * d;
            s += d;
        }
        return s;
    }

    /**
     * 计算两个时序数据对象之间的SAXTD距离
     *
     * @param s1       时序数据对象
     * @param s2       时序数据对象
     * @param w        paa的段数
     * @param alphabet
     * @return 返回两个时序数据对象之间的SAXTD距离
     */
    public static double distance(Series s1, Series s2, int w, int alphabet) {
        double t1 = cn.edu.cug.cs.gtl.series.common.sax.Utils.distance(s1, s2, w, alphabet);
        double t2 = distance(s1.getValues(), s2.getValues(), w);
        double res = t1 * t1 + t2;
        return Math.sqrt(res);
    }

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param s1       m条时序数据的集合
     * @param s2       n条时序数据的集合
     * @param w        paa的段数
     * @param alphabet
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    public static Array distances(MultiSeries s1, MultiSeries s2, int w, int alphabet) {
        int m = (int) s1.count();
        int n = (int) s2.count();
        double[] dist = new double[m * n];
        int k = 0;
        for (int i = 0; i < m; ++i) {
            Series s11 = s1.getSeries(i);
            for (int j = 0; j < n; ++j) {
                Series s22 = s2.getSeries(j);
                dist[k] = distance(s11, s22, w, alphabet);
                ++k;
            }
        }
        return Array.of(n, m, dist);
    }
}
