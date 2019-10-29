package cn.edu.cug.cs.gtl.series.common.saxtg;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;

public class Utils {
    /**
     * saxtg representation
     *
     * @param ts
     * @param w
     * @return
     */
    public static double[] saxtg(double[] ts, int w) {
        double[] t1 = ts;
        double[] t2 = new double[w];
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        int elem_row = ts.length / w;
        int i = 0;
        while (i < w) {
            for (int j = i * elem_row; j < (i + 1) * elem_row; j++) {
                if (t1[j] > max) {
                    max = t1[j];
                }
                if (t1[j] < min) {
                    min = t1[j];
                }
            }
            t2[i] = (max - min) / elem_row;
            ++i;
        }
        return t2;
    }

    /**
     * sax_TG_distance
     *
     * @param a
     * @param b
     * @param w
     * @return
     */
    private static double distance(Series a, Series b, int w) {
        int n = (int) a.length();
        assert (n == b.length());
        double[] t1 = saxtg(a.getValues(), w);
        double[] t2 = saxtg(b.getValues(), w);
        double s = 0.0;
        for (int i = 0; i < w; ++i) {
            double d = t2[i] - t1[i];
            d = d * d;
            s += d;
        }
        return s;
    }


    /**
     * 计算两个时序数据对象之间的SAXTG距离
     *
     * @param s1       时序数据对象
     * @param s2       时序数据对象
     * @param w        paa的段数
     * @param alphabet
     * @return 返回两个时序数据对象之间的SAXTG距离
     */
    public static double distance(Series s1, Series s2, int w, int alphabet) {
        assert (s1.length() == s2.length());
        double t1 = cn.edu.cug.cs.gtl.series.common.sax.Utils.distance(s1, s2, w, alphabet);
        double t2 = distance(s1, s2, w);
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
