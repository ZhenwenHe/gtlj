package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;

import java.util.List;

public class HaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int wordSize;
    protected TIOPlane tioPlane;

    public HaxDistanceMetrics(int wordSize, TIOPlane tioPlane) {
        this.wordSize = wordSize;
        this.tioPlane = tioPlane;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof TimeSeries && b instanceof TimeSeries) {
            return DistanceUtils.hax((TimeSeries) a, (TimeSeries) b, this.wordSize, tioPlane);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
