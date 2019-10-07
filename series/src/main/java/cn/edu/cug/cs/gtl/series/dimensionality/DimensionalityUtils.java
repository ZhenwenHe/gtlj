package cn.edu.cug.cs.gtl.series.dimensionality;

import cn.edu.cug.cs.gtl.series.common.paa.Utils;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;

public class DimensionalityUtils {



    /**
     *
     * @param a  the input time series.
     *           it should be a n x 2 array;
     *           n is the length of the time series
     *           a.getDataX() is the x-axis values
     *           a.getDataY() is the y-axis values
     * @param w  The desired length of approximated timeseries
     * @return  the output time series.
     *          An array of points with the reduced dimensionality
     *          it should be a n x 2 array;
     *          w is the length of the time series
     *          col(0) is the x-axis values
     *          col(1) is the y-axis values
     */
    public static TimeSeries paa(TimeSeries a, long w){
        return Utils.paa(a,w);
    }

    /**
     * Approximate the timeseries using PAA.
     * If the timeseries has some NaN's they are handled as follows:
     * 1) if all values of the piece are NaNs - the piece is approximated as NaN,
     * 2) if there are some (more or equal one) values happened to be in the piece
     * algorithm will handle it as usual - getting the mean.
     *
     * @param ts The timeseries to approximate.
     * @param paaSize The desired length of approximated timeseries.
     * @return PAA-approximated timeseries.
     * @throws Exception if error occurs.
     *
     */
    public static double[] paa(double[] ts, int paaSize) throws Exception {
        return Utils.paa(ts,paaSize);
    }

    /**
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static Series paa(Series s, int paaSize){
        return null;
    }

    /**
     *
     * @param s
     * @param paaSize
     * @param alphabet
     * @return
     */
    public static char[] sax(Series s, int paaSize, int alphabet){
        return null;
    }

    /**
     *
     * @param s
     * @param paaSize
     * @param alphabet
     * @return
     */
    public static char[] sax(TimeSeries s, int paaSize, int alphabet){
        return null;
    }


    /**
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static Vector[] pax(TimeSeries s, int paaSize){
        return null;
    }

    /**
     *
     * @param s
     * @param paaSize
     * @return
     */
    public static Vector[] hax(TimeSeries s, int paaSize){
        return null;
    }





}