package cn.edu.cug.cs.gtl.series.common.pax;


import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * xs is time axis values, and ys is the series axis values
     * convert a time-series into a set of points
     *
     * @param s
     * @param paaSize
     * @return a set of points
     */
    public static TIOPoints pax(Series s, int paaSize) {
        TIOPoints vl = new TIOPoints();
        for (int i = 0; i < paaSize; ++i) {
            vl.add(subseriesToTIOPoint(s.subseries(paaSize, i)));
        }
        return vl;
    }

    /**
     * 将一个子序列转换成一个TIO点
     *
     * @param s
     * @return
     */
    private static TIOPoint subseriesToTIOPoint(Series s) {
        double[] xs = null;
        double[] ys = null;
        if (s instanceof TimeSeries) {
            TimeSeries ts = (TimeSeries) s;
            xs = ts.getDataX();
            ys = ts.getDataY();
        } else if (s instanceof MultiSeries) {
            MultiSeries ms = (MultiSeries) s;
            TimeSeries ts = ms.getSeries(0);
            xs = ts.getDataX();
            ys = ts.getDataY();
        } else {
            ys = s.getValues();
            xs = new double[ys.length];
            for (int i = 0; i < xs.length; ++i)
                xs[i] = i;
        }
        assert xs != null;
        assert ys != null;
        return new TIOPoint(calculateAngle(xs, ys), Series.mean(ys));
    }

    /**
     * 计算拟合线段的角度
     *
     * @param xs 时间轴上的值
     * @param ys V轴上的值
     * @return 拟合线段的方向角度[-90，90]
     */
    private static double calculateAngle(double[] xs, double[] ys) {
        PolynomialCurveFitter p = PolynomialCurveFitter.create(1);
        List<WeightedObservedPoint> points = new ArrayList<>();
        for (int i = 0; i < ys.length; ++i) {
            points.add(new WeightedObservedPoint(1.0, xs[i], ys[i]));
        }
        double[] f = p.fit(points);

        return Math.toDegrees(Math.atan(f[1]));
    }


    /**
     * @param t1
     * @param t2
     * @return
     */
    private static double distance(TIOPoint t1, TIOPoint t2, TIOPlane plane) {
        TIOPoint t11 = plane.normalize(t1);
        TIOPoint t22 = plane.normalize(t2);
        double v = t11.getValue() - t22.getValue();
        double a = t11.getAngle() - t22.getAngle();
        return Math.sqrt(v * v + a * a);
    }

    /**
     * @param ts1
     * @param ts2
     * @return
     */
    private static double distance(TIOPoints ts1, TIOPoints ts2, TIOPlane plane) {
        int s = Math.min(ts1.size(), ts2.size());
        double sum = 0;
        for (int i = 0; i < s; ++i) {
            sum += distance(ts1.get(i), ts2.get(i), plane);
        }
        return sum;
    }

    /**
     * 计算两个时序数据对象之间的距离
     *
     * @param tss1
     * @param tss2
     * @param w
     * @return
     */
    public static double distance(Series tss1, Series tss2, int w) {
        TIOPlane plane = TIOPlane.of(tss1, tss2);
        return distance(pax(tss1, w), pax(tss2, w), plane);
    }

    /**
     * 计算两个时序数据对象之间的距离
     *
     * @param tss1
     * @param tss2
     * @param w
     * @param tioPlane
     * @return
     */
    public static double distance(Series tss1, Series tss2, int w, TIOPlane tioPlane) {
        return distance(pax(tss1, w), pax(tss2, w), tioPlane);
    }

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param s1 m条时序数据的集合
     * @param s2 n条时序数据的集合
     * @param w  paa的段数
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    public static Array distances(MultiSeries s1, MultiSeries s2, int w) {
        try {
            int m = (int) s1.count();
            int n = (int) s2.count();
            double[] dist = new double[m * n];
            int k = 0;
            for (int i = 0; i < m; ++i) {
                Series s11 = s1.getSeries(i);
                for (int j = 0; j < n; ++j) {
                    Series s22 = s2.getSeries(j);
                    dist[k] = distance(s11, s22, w);
                    ++k;
                }
            }
            return Array.of(n, m, dist);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


