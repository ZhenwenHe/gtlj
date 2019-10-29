package cn.edu.cug.cs.gtl.series.common.esax;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.sax.NormalAlphabet;

import static org.apache.commons.math3.stat.StatUtils.mean;

public class Utils {
    /**
     * esax representation
     *
     * @param ts       series
     * @param w        the total number of divisions.
     * @param alphabet is the size of alphabet
     * @return
     */
    public static int[] esax(double[] ts, long w, int alphabet) {
        int[] aux = new int[(int) w * 3];
        double[] reducedDataY = esax(ts, w);

        double mean_value = mean(reducedDataY);
        double std_value = Series.standardDeviation(mean_value, reducedDataY);


        if (std_value > 0) {
            double[] breakingpoints = cn.edu.cug.cs.gtl.series.common.sax.Utils.saxComputeBreakpoints(alphabet, mean_value, std_value);
            int[] alphabets = cn.edu.cug.cs.gtl.series.common.sax.Utils.saxGenerateAlphabet(alphabet);

            // Iterate across elements of reducedDataY
            for (int i = 0; i < (int) w * 3; i++) {
                int j = 0;
                int alpha = alphabets[0];

                while ((j < breakingpoints.length) && (reducedDataY[i] > breakingpoints[j])) {
                    j++;
                }
                alpha = alphabets[j];
                aux[i] = alpha;
            }
        } else {
            for (int i = 0; i < (int) w * 3; ++i)
                aux[i] = 0;
        }
        return aux;
    }

    /**
     * esax representation
     *
     * @param ts series
     * @param w  the total number of divisions.
     * @return
     */
    private static double[] esax(double[] ts, long w) {

        int n = ts.length;
        double[] column = ts;
        double[] reducedColumn = new double[(int) w * 3];

        //Find out the number of elements per bin
        double elemPerBin = Long.valueOf(n).doubleValue() / w;

        //仅计算ys以及其索引值，xs其实就是x轴下标，也就是ys的索引值
        double start = 0.0;
        double end = elemPerBin - 1;


        int reducedcount = 0;
        //For each segment
        for (int j = 0; j < w; j++) {
            double avg = 0.0;
            double max = column[0];
            double min = column[0];
            double idxmax = 0;
            double idxmin = 0;
            double idxmean = 0.0;
            double count = 0;

            //Compute avg for this segment
            for (int k = (int) start; k <= end; k++) {
                avg = avg + column[k];
                if (max < column[k]) {
                    max = column[k];
                    idxmax = Integer.valueOf(k).doubleValue();
                }
                if (min > column[k]) {
                    min = column[k];
                    idxmin = Integer.valueOf(k).doubleValue();
                }
                count++;
            }
            avg = avg / count;
            idxmean = (start + end) / 2;
            if (idxmax < idxmean) {
                if (idxmean < idxmin) {
                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                } else if (idxmax < idxmin) {
                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;
                } else {
                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;
                }
            } else {
                if (idxmax < idxmin) {
                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;
                } else if (idxmean < idxmin) {
                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;
                } else {
                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;
                }
            }
            //compute next segment
            start = Math.ceil(end);
            end = end + elemPerBin;
            end = (end > n) ? n : end;

        }
        return reducedColumn;
    }


    /**
     * 计算两个时序数据对象之间的SAX距离
     *
     * @param s1       时序数据对象
     * @param s2       时序数据对象
     * @param w        paa的段数
     * @param alphabet
     * @return 返回两个时序数据对象之间的SAX MINDIST
     */
    public static double distance(Series s1, Series s2, int w, int alphabet) {
        int[] ar = esax(s1.getValues(), w, alphabet);
        int[] br = esax(s1.getValues(), w, alphabet);
        int n = (int) Math.min(s1.length(), s2.length());
        double s = 0.0;
        try {
            NormalAlphabet tab = new NormalAlphabet();
            double[][] disTab = tab.getDistanceMatrix(alphabet);
            for (int i = 0; i < w * 3; ++i) {
                double d = disTab[ar[i]][br[i]];
                d = d * d;
                s += d;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        double res = Math.sqrt(n / ((int) w * 3)) * Math.sqrt(s);
        return res;
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
