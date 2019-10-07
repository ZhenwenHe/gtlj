package cn.edu.cug.cs.gtl.series.common.saxtg;

public class Utils {
    /**
     *
     * @param ts
     * @param w
     * @param n
     * @return
     */
    public static double[] saxtg(double[] ts, int w, int n){
        double[] t1 = ts;
        double[] t2 = new double[w];
        double max = -Double.MAX_VALUE;
        double min = Double.MAX_VALUE;
        int elem_row = n / w;
        int i = 0;
        while (i < w) {
            for (int j = i * elem_row; j < (i + 1)*elem_row; j++) {
                if (t1[j] > max) {
                    max = t1[j];
                }
                if (t1[j] < min) {
                    min = t1[j];
                }
            }
            t2[i] = (max - min) / elem_row;
            ++i;
        }
        return t2;
    }
}
