package gtl.series.distances;

import gtl.series.common.TimeSeries;
import gtl.ml.distances.DistanceMetrics;

public class SaxTGDistanceMetrics<T>  implements DistanceMetrics<T> {
    protected int alphabet;
    protected long wordSize;

    public SaxTGDistanceMetrics(long w, int alphabet) {
        this.alphabet = alphabet;
        this.wordSize = w;
    }

    @Override
    public double distance(T a, T b) {
        if (a instanceof TimeSeries && b instanceof TimeSeries) {
            return DistanceUtils.saxtg((TimeSeries) a, (TimeSeries) b, (int)this.wordSize, this.alphabet);
        } else {
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}