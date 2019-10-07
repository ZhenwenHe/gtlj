package cn.edu.cug.cs.gtl.series.common.saxtd;

public class Utils {
    /**
     *
     * @param ts
     * @param w
     * @param n
     * @return
     */
    public static double[] saxtd(double [] ts, int w, int n){
        try{
            double[] mid = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(ts, w);
            double[] d = new double[w+1];
            double paaSize = (double)n / (double)w;
            double i = 0.0;
            for (int count = 0; count < w ; count++) {
                d[count] = ts[(int)i] - mid[count];
                i = count * paaSize;
            }
            d[w] = ts[(int)i] - mid[w-1];
            return d;
        }catch (Exception e){
            return null;
        }
    }
}
