package cn.edu.cug.cs.gtl.series.distances;


import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;

import static java.lang.Math.sqrt;

/**
 *
 */
public class DistanceUtils {

    /**
     * 计算两个序列之间的欧式距离和
     * @param x1 序列1的X坐标数组
     * @param y1 序列1的Y坐标数组
     * @param x2 序列2的X坐标数组
     * @param y2 序列2的Y坐标数组
     * @param c  序列中参与计算的坐标个数
     * @return  两个序列之间的欧式距离和
     */
    static double distance( double   [] x1, double    [] y1,  double   [] x2, double   [] y2, int c){
        double dis = 0;
        for (int i = 0; i < c; ++i) {
            dis+= sqrt((y2[i] -y1[i])*(y2[i] - y1[i]) + (x2[i] - x1[i])*(x2[i] - x1[i]));
        }
        return  dis;
    }

    /**
     * euclidean distance
     * @param a time series
     * @param b time series
     * @return
     */
    public static double euclidean(Series a, Series b){
        int n = (int)a.length();
        assert (n == b.length());
        double[] t1 = a.getValues();
        double[] t2 = b.getValues();
        double s = 0.0;
        for (int i = 0; i < n; ++i){
            double d = t2[i] - t1[i];
            d = d * d;
            s += d;
        }
        return sqrt(s);
    }

    /**
     * euclidean distance
     * @param a time series
     * @param b time series
     * @return
     */
    public static double euclidean(TimeSeries a, TimeSeries b){
        int n = (int)a.length();
        assert (n == b.length());
        double[] t1 = a.getDataY();
        double[] t2 = b.getDataY();
        double s = 0.0;
        for (int i = 0; i < n; ++i){
            double d = t2[i] - t1[i];
            d = d * d;
            s += d;
        }
        return sqrt(s);
    }


    /**
     * @calculate the sax distance between two time series  a and b
     * @param a
     * @param b
     * @param w the total number of divisions.
     * @param alphabet is the size of alphabet
     * @return distance between a and b.
     */
    public static double sax (Series a, Series b, int w, int  alphabet) {
        return cn.edu.cug.cs.gtl.series.common.sax.Utils.distance(a,b,w,alphabet);
    }

    /**
     * @calculate the sax distance between two time series  a and b
     * @param a
     * @param b
     * @param w the total number of divisions.
     * @param alphabet is the size of alphabet
     * @return distance between a and b.
     */
    public static double sax (TimeSeries a, TimeSeries b, int w, int  alphabet) {
        return cn.edu.cug.cs.gtl.series.common.sax.Utils.distance(a,b,w,alphabet);
    }

    /**
     * the sax distance between two multi time series  a and b
     * @param train
     * @param test
     * @param w
     * @param alphabet is the size of alphabet
     * @return
     */
    public static Array sax(MultiSeries train, MultiSeries test, int w, int  alphabet) {
        return cn.edu.cug.cs.gtl.series.common.sax.Utils.distances(train,test,w,alphabet);
    }


    /**
     * pax distance
     * @param a
     * @param b
     * @param w
     * @return
     */
    public static double pax(TimeSeries a, TimeSeries b, int w){
        return cn.edu.cug.cs.gtl.series.common.pax.Utils.distance(a,b,w);
    }

    /**
     * pax distances
     * @param train
     * @param test
     * @param w
     * @return
     */
    public static Array pax(MultiSeries train, MultiSeries test, int w) {
        return cn.edu.cug.cs.gtl.series.common.pax.Utils.distances(train,test,w);
    }





    /**
     * @brief calculate the hax distance between two time series a and b
     * @param a Array with the input time series.
     * @param b Array with the input time series.
     * @param w the total number of divisions.
     * @return distance of hax .
     */
    public static double hax(TimeSeries a, TimeSeries b, int w) {
        return cn.edu.cug.cs.gtl.series.common.hax.Utils.distance(a,b,w);
    }

    /**
     *calculate the hax distance between two multi time series a and b
     * @param train
     * @param test
     * @param w
     * @return
     */
    public static Array hax(MultiSeries train, MultiSeries test, int w) {
        return cn.edu.cug.cs.gtl.series.common.hax.Utils.distances(train,test,w);
    }


    /**
     *
     * @param a
     * @param b
     * @param w
     * @param alphabet
     * @return
     */
    public static double esax (TimeSeries a, TimeSeries b, long w, int alphabet){
        assert (a.length() ==b.length());
//        int [] ar = DimensionalityUtils.esax(a,w,alphabet);
//        int [] br = DimensionalityUtils.esax(b,w,alphabet);
//        int n = (int) a.length();
//        double s = 0.0;
//        try {
//            NormalAlphabet tab = new NormalAlphabet();
//            double[][] disTab = tab.getDistanceMatrix(alphabet);
//            for (int i = 0; i < w*3; ++i) {
//                double d = disTab[ar[i]][br[i]];
//                d = d * d;
//                s += d;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        double res = sqrt(n / ((int) w*3)) * sqrt(s);
//        return res;
        return 0.0;
    }

    /**
     *
     * @param a
     * @param b
     * @param w
     * @return
     */
    public static double saxtd(TimeSeries a, TimeSeries b, int w){
//        int n = (int)a.length();
//        assert (n == b.length());
//        double[] t1 = DimensionalityUtils.sax_TD(a, w, n);
//        double[] t2 = DimensionalityUtils.sax_TD(b, w, n);
//        double s = 0.0;
//        for (int i = 0; i <= w; ++i) {
//            double d = t2[i] - t1[i];
//            d = d * d;
//            s += d;
//        }
//        return s;
        return 0.0;
    }

    /**
     *
     * @param a
     * @param b
     * @param w
     * @param alphabet
     * @return
     */
    public static double saxtd(TimeSeries a, TimeSeries b, int w, int alphabet){
//        assert (a.length() == b.length());
//        double t1 = sax(a, b, w, alphabet);
//        double t2 = sax_TD_distance(a, b, w);
//        double res = t1 * t1 + t2;
//        return sqrt(res);
        return 0.0;
    }

    /**
     *
     * @param a
     * @param b
     * @param w
     * @return
     */
    public static double saxtg(TimeSeries a, TimeSeries b, int w){
//    int n = (int)a.length();
//    assert (n == b.length());
//    double[] t1 = DimensionalityUtils.sax_TG(a, w, n);
//    double[] t2 = DimensionalityUtils.sax_TG(b, w, n);
//    double s = 0.0;
//    for (int i = 0; i < w; ++i) {
//    double d = t2[i] - t1[i];
//    d = d * d;
//    s += d;
//    }
//    return s;
        return 0.0;
    }

    /**
     *
     * @param a
     * @param b
     * @param w
     * @param alphabet
     * @return
     */
    public static double saxtg(TimeSeries a, TimeSeries b, int w, int alphabet){
//    assert (a.length() == b.length());
//    double t1 = sax(a, b, w, alphabet);
//    double t2 = sax_TG_distance(a, b, w);
//    double res = t1 * t1 + t2;
//    return sqrt(res);
        return 0.0;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double dtw(TimeSeries a, TimeSeries b){

        return -Double.MAX_VALUE;
    }


    /**
     *
     * @param x
     * @param y
     * @return
     */
    public static int hamming(int x, int y){
        int dif = x ^ y;
        int res = 0;
        while (dif != 0){
            dif = dif & (dif - 1);
            res++;
        }
        return res;
    }

    /**
     *
     * @param a
     * @param b
     * @param w
     * @param alphabet
     * @return
     */
    public static int hamming(TimeSeries a, TimeSeries b, long w, int alphabet){
//        int n = (int)a.length();
//        assert (n == b.length());
//        int[] ar = DimensionalityUtils.sax(a, w, alphabet);
//        int[] br = DimensionalityUtils.sax(b, w, alphabet);
//        int s = 0;
//        for (int i = 0; i < n; ++i){
//            int d = hamming(ar[i], br[i]);
//            s += d;
//        }
//        return s;
        return 0;
    }

    /**
     *
     * @param train
     * @param test
     * @return
     */
    public static double[][] hamming(MultiSeries train, MultiSeries test){

        return null;
    }


    /**
     *
     * @param a
     * @param b
     * @return
     */
    public static double manhattan(TimeSeries a, TimeSeries b){
        int n = (int)a.length();
        assert (n == b.length());
        double[] t1 = a.getDataY();
        double[] t2 = b.getDataY();
        double s = 0.0;
        for (int i = 0; i < n; ++i){
            double d = Math.abs(t2[i] - t1[i]);
            s += d;
        }
        return s;
    }

    /**
     *
     * @param train
     * @param test
     * @return
     */
    public static double[][] manhattan(MultiSeries train, MultiSeries test){
         return null;
    }
}
