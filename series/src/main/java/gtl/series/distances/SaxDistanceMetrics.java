package gtl.series.distances;

import gtl.series.common.TimeSeries;
import gtl.ml.distances.DistanceMetrics;

public class SaxDistanceMetrics<T>  implements DistanceMetrics<T> {
    protected int alphabet;
    protected int wordSize;

    public SaxDistanceMetrics(int w, int alphabet) {
        this.alphabet=alphabet;
        this.wordSize=w;
    }

    @Override
    public double distance(T a, T b) {
        if(a instanceof TimeSeries && b instanceof TimeSeries){
            return DistanceUtils.sax((TimeSeries) a,(TimeSeries)b,this.wordSize,this.alphabet);
        }
        else{
            System.out.println("Error");
            return Double.MAX_VALUE;
        }
    }
}
