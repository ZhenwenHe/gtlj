package gtl.series.distances;

import gtl.series.common.TimeSeries;
import gtl.ml.distances.DistanceMetrics;

public class HaxDistanceMetrics<T> implements DistanceMetrics<T> {
    protected int wordSize;

    public HaxDistanceMetrics(int wordSize) {
        this.wordSize = wordSize;
    }

    @Override
    public double distance(T a, T b) {
        if(a instanceof TimeSeries && b instanceof TimeSeries){
            return DistanceUtils.hax((TimeSeries) a,(TimeSeries)b,this.wordSize);
        }
        else{
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
