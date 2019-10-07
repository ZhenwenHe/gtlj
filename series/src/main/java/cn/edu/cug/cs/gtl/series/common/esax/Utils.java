package cn.edu.cug.cs.gtl.series.common.esax;

import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;

import static org.apache.commons.math3.stat.StatUtils.mean;

public class Utils {
    /**
     * esax representation
     * @param ts series
     * @param w the total number of divisions.
     * @param alphabet is the size of alphabet
     * @return
     */
    public static int [] esax(double[] ts, long w, int alphabet){
        int [] aux = new int[ (int) w*3];
        double[] reducedDataY = esax(ts,w);

        double mean_value = mean(reducedDataY);
        double std_value = Series.standardDeviation(mean_value, reducedDataY);


        if(std_value > 0 ){
            double[] breakingpoints = cn.edu.cug.cs.gtl.series.common.sax.Utils.saxComputeBreakpoints(alphabet,mean_value,std_value);
            int[] alphabets =  cn.edu.cug.cs.gtl.series.common.sax.Utils.saxGenerateAlphabet(alphabet);

            // Iterate across elements of reducedDataY
            for(int i =0 ;i< (int)w*3;i++){
                int j = 0;
                int alpha = alphabets[0];

                while((j < breakingpoints.length) && (reducedDataY[i] > breakingpoints[j])){
                    j++;
                }
                alpha = alphabets[j];
                aux[i] = alpha;
            }
        }
        else {
            for(int i = 0;i< (int)w*3;++i)
                aux[i] = 0;
        }
        return aux;
    }

    /**
     * esax representation
     * @param ts series
     * @param w the total number of divisions.
     * @return
     */
    static double [] esax(double[] ts, long w) {

        int n = ts.length;
        double [] column = ts;
        double [] reducedColumn = new double[(int)w*3];

        //Find out the number of elements per bin
        double elemPerBin = Long.valueOf(n).doubleValue()/w;

        //仅计算ys以及其索引值，xs其实就是x轴下标，也就是ys的索引值
        double start = 0.0;
        double end = elemPerBin - 1;


        int reducedcount = 0;
        //For each segment
        for(int j=0;j<w;j++){
            double avg = 0.0;
            double max = column[0];
            double min = column[0];
            double idxmax = 0;
            double idxmin = 0;
            double idxmean = 0.0;
            double count= 0;

            //Compute avg for this segment
            for(int k =(int) start; k<= end; k++){
                avg = avg+column[k];
                if (max < column[k]) {
                    max = column[k];
                    idxmax = Integer.valueOf(k).doubleValue();
                }
                if(min >column[k]){
                    min = column[k];
                    idxmin = Integer.valueOf(k).doubleValue();
                }
                count++;
            }
            avg = avg /count;
            idxmean = (start+end)/2;
            if(idxmax < idxmean){
                if(idxmean <idxmin){
                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                }else if(idxmax < idxmin){
                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;
                }else{
                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;
                }
            }else{
                if(idxmax < idxmin){
                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;
                }else if(idxmean < idxmin){
                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;
                }else {
                    reducedColumn[reducedcount] = min;
                    reducedcount++;

                    reducedColumn[reducedcount] = avg;
                    reducedcount++;

                    reducedColumn[reducedcount] = max;
                    reducedcount++;
                }
            }
            //compute next segment
            start = Math.ceil(end);
            end = end +elemPerBin;
            end = (end > n) ? n:end;

        }
        return reducedColumn;
    }


}
