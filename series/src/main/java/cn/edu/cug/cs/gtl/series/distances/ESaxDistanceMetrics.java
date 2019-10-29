package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

public class ESaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int alphabet;
    protected long wordSize;

    public ESaxDistanceMetrics(long w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof TimeSeries && b instanceof TimeSeries) {
            //return DistanceUtils.esax((TimeSeries) a,(TimeSeries)b,this.wordSize);
            return 0;
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }

    }
}
