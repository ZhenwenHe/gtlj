package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.series.classification.Classification;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics;
import cn.edu.cug.cs.gtl.series.distances.SaxDistanceMetrics;
import cn.edu.cug.cs.gtl.series.classification.Classification;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.distances.HaxDistanceMetrics;
import cn.edu.cug.cs.gtl.series.distances.SaxDistanceMetrics;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * pathList 对应训练数据测试数据的绝对路径
     * pathList 对应训练数据测试数据文件名称
     */

        public static List<String> nameList = new ArrayList<>();
        public static List<String> pathList = new ArrayList<>();

    /**
     * 四种度量方式得到的结果保存到list里
     */
        public static ArrayList<Double> dtwList = new ArrayList<>();
        public static ArrayList<Double> saxList = new ArrayList<>();
        public static  ArrayList<Double> haxList = new ArrayList<>();
        public static ArrayList<Double> euList = new ArrayList<>();

    /**
     * 将得到的绝对路径分开
     * 其中trainPathList为训练数据的绝对路径
     * testPathList为测试数据的绝对路径
     */
        public static List<String> trainPathList = new ArrayList<>();
        public static List<String> testPathList = new ArrayList<>();


        public void getDataPath(File file){
            File[] subFile = file.listFiles();
            for (File f : subFile) {
                if (f.isDirectory()) {
                    File[] subFile1 = f.listFiles();
                    nameList.add(f.getName());
                    for (File f1 : subFile1){
                        if (f1.isFile() && f1.getName().endsWith(".tsv")){
                            pathList.add(f1.getPath());
                        }
                    }
                }
            }
            int pathSize = pathList.size()/2;
            for (int j = 0; j < pathSize; j++){
                trainPathList.add(pathList.get(2*j+1));
                testPathList.add(pathList.get(2*j));
            }
        }

    public  void Sax_all() throws Exception {
        int pathSize = pathList.size()/2;
        for (int k = 0; k < pathSize; k++)
        {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                SaxDistanceMetrics<TimeSeries> disFunc = new SaxDistanceMetrics<>(10,9);
                saxList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  void Hax_all() throws Exception {
        int pathSize = pathList.size()/2;
        File file = new File("D:\\gtl\\trunk\\data\\result.xls");
        Label dis = null;
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("sheet1",0);
        for (int k = 0; k < pathSize; k++)
        {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10);
                haxList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                dis = new Label(1,k,String.valueOf(haxList.get(k)));
                sheet.addCell(dis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        workbook.write();
        workbook.close();

    }

    public void writeExcel() throws Exception{
        String[] title = {"DataName","Dtw(error)","Sax(error)","Hax(error)","Eu(error)"};
        File file = new File("D:\\gtl\\trunk\\data\\result.xls");
        try{
            file.createNewFile();
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("sheet1",0);
            Label label = null;
            for  (int i =0; i<title.length; i++){
                label = new Label(i,0,title[i]);
                sheet.addCell(label);
            }

            for (int j = 0; j<pathList.size()/2; j++){
                label = new Label(0,j,nameList.get(j));
                sheet.addCell(label);
            }
            workbook.write();
            workbook.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        File abs = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        Main set = new Main();
        set.getDataPath(abs);
        set.writeExcel();
        //set.Hax_all();

    }
}

