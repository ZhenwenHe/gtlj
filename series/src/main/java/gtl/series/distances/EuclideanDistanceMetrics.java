package gtl.series.distances;

import gtl.series.common.TimeSeries;
import gtl.ml.distances.DistanceMetrics;

public class EuclideanDistanceMetrics<T> implements DistanceMetrics<T> {
    public EuclideanDistanceMetrics() {
    }

    @Override
    public double distance(T a, T b) {
        if(a instanceof TimeSeries && b instanceof TimeSeries){
            return DistanceUtils.euclidean((TimeSeries) a,(TimeSeries)b);
        }
        else{
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
