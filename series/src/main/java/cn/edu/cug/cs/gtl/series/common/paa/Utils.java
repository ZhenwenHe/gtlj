package cn.edu.cug.cs.gtl.series.common.paa;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.sax.SAXException;

import java.util.Arrays;

public class Utils {
    /**
     * Approximate the timeseries using PAA. If the timeseries has some NaN's they are handled as
     * follows: 1) if all values of the piece are NaNs - the piece is approximated as NaN, 2) if there
     * are some (more or equal one) values happened to be in the piece - algorithm will handle it as
     * usual - getting the mean.
     *
     * @param ts The timeseries to approximate.
     * @param paaSize The desired length of approximated timeseries.
     * @return PAA-approximated timeseries.
     * @throws Exception if error occurs.
     *
     */
    public static double[] paa(double[] ts, int paaSize) throws Exception {
        // fix the length
        int len = ts.length;
        if (len < paaSize) {
            throw new Exception("PAA size can't be greater than the series size.");
        }
        // check for the trivial case
        if (len == paaSize) {
            return Arrays.copyOf(ts, ts.length);
        }
        else {
            double[] paa = new double[paaSize];
            double pointsPerSegment = (double) len / (double) paaSize;
            double[] breaks = new double[paaSize + 1];
            for (int i = 0; i < paaSize + 1; i++) {
                breaks[i] = i * pointsPerSegment;
            }

            for (int i = 0; i < paaSize; i++) {
                double segStart = breaks[i];
                double segEnd = breaks[i + 1];

                double fractionStart = Math.ceil(segStart) - segStart;
                double fractionEnd = segEnd - Math.floor(segEnd);

                int fullStart = Double.valueOf(Math.floor(segStart)).intValue();
                int fullEnd = Double.valueOf(Math.ceil(segEnd)).intValue();

                double[] segment = Arrays.copyOfRange(ts, fullStart, fullEnd);

                if (fractionStart > 0) {
                    segment[0] = segment[0] * fractionStart;
                }

                if (fractionEnd > 0) {
                    segment[segment.length - 1] = segment[segment.length - 1] * fractionEnd;
                }

                double elementsSum = 0.0;
                for (double e : segment) {
                    elementsSum = elementsSum + e;
                }

                paa[i] = elementsSum / pointsPerSegment;

            }
            return paa;
        }
    }

    /**
     * 对Series的X轴和Y轴分别采用paa算法
     * @param a
     * @param paaSize
     * @return
     */
    public static TimeSeries paa(TimeSeries a, long paaSize) {
        try {
            double[] d =   paa(a.getDataY(),(int)paaSize);
            double[] xs = paa(a.getDataX(),(int)paaSize);
            return TimeSeries.of(xs,d);
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Approximate the timeseries using PAA.
     * @param a
     * @param paaSise
     * @return
     */
    /**
     * @brief Piecewise Aggregate Approximation (PAA) approximates a time series of length n into vector
     * of length paaSise
     * @param a Array with the input time series.
     *		  it should be a n x m array;
     *		  n is the length of the time series
     *		  m is the number of timeSeries
     *		  col(0) is the x-axis values
     *		  col(1) is the first y-axis values
     *		  ...........................
     *		  col(m-1) is the m-2 y-axis values
     * @param paaSise the total number of divisions. 	 *
     * @return af::array An array of points with the reduced dimensionality
     *		  it should be a n x m array;
     *		  paaSise is the length of the time series
     *		  m is the number of timeSeries
     *		  col(0) is the x-axis values
     *		  col(1) is the first y-axis values
     *		  ...........................
     *		  col(m-1) is the m-1 y-axis values
     */
    public static Array paa(Array a, int paaSise){
        int n = a.dims(0);
        int m = a.dims(1);
        double [] dat = new double[m*paaSise];
        Array r = Array.of(paaSise,m,dat);
        try {
            int s=0;
            for(int i=0;i<m;++i){
                double [] t = paa(a.col(i).host(),paaSise);
                System.arraycopy(t,0,dat,s,t.length);
                s+=t.length;
            }
            return r;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
