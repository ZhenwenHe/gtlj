package cn.edu.cug.cs.gtl.series.distances;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;

public class SaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int alphabet;
    protected int wordSize;

    public SaxDistanceMetrics(int w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof TimeSeries && b instanceof TimeSeries) {
            return DistanceUtils.sax((TimeSeries) a, (TimeSeries) b, this.wordSize, this.alphabet);
        } else if (a instanceof Series && b instanceof Series) {
            return DistanceUtils.sax((Series) a, (Series) b, this.wordSize, this.alphabet);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
