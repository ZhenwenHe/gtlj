package cn.edu.cug.cs.gtl.series.dimensionality;

import cn.edu.cug.cs.gtl.series.common.paa.Utils;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPoints;

public class DimensionalityUtils {


    /**
     * @param a the input time series.
     *          it should be a n x 2 array;
     *          n is the length of the time series
     *          a.getDataX() is the x-axis values
     *          a.getDataY() is the y-axis values
     * @param w The desired length of approximated time series
     * @return the output time series.
     * An array of points with the reduced dimensionality
     * it should be a n x 2 array;
     * w is the length of the time series
     * col(0) is the x-axis values
     * col(1) is the y-axis values
     */
    public static TimeSeries paa(TimeSeries a, long w) {
        return cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(a, w);
    }

    /**
     * Approximate the time series using PAA.
     * If the time series has some NaN's they are handled as follows:
     * 1) if all values of the piece are NaNs - the piece is approximated as NaN,
     * 2) if there are some (more or equal one) values happened to be in the piece
     * algorithm will handle it as usual - getting the mean.
     *
     * @param ts      The time series to approximate.
     * @param paaSize The desired length of approximated time series.
     * @return PAA-approximated time series.
     * @throws Exception if error occurs.
     */
    public static double[] paa(double[] ts, int paaSize) throws Exception {
        return cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(ts, paaSize);
    }

    /**
     * Approximate the timeseries using PAA.
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static Series paa(Series s, int paaSize) {
        return cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(s, paaSize);
    }

    /**
     * Approximate the time series using SAX.
     *
     * @param s
     * @param paaSize
     * @param alphabet
     * @return
     */
    public static char[] sax(Series s, int paaSize, int alphabet) {
        return cn.edu.cug.cs.gtl.series.common.sax.Utils.sax(s, paaSize, alphabet);
    }

    /**
     * Approximate the time series using SAX.
     *
     * @param s
     * @param paaSize
     * @param alphabet
     * @return
     */
    public static char[] sax(TimeSeries s, int paaSize, int alphabet) {
        return cn.edu.cug.cs.gtl.series.common.sax.Utils.sax(s, paaSize, alphabet);
    }


    /**
     * Approximate the time series using PAX.
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static TIOPoints pax(TimeSeries s, int paaSize) {
        return cn.edu.cug.cs.gtl.series.common.pax.Utils.pax(s, paaSize);
    }

    /**
     * Approximate the time series using HAX.
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static byte[] hax(TimeSeries s, int paaSize) {
        return cn.edu.cug.cs.gtl.series.common.hax.Utils.hax(s, paaSize);
    }


}