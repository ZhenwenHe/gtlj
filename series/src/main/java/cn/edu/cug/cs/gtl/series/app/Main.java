package cn.edu.cug.cs.gtl.series.app;

import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.ml.classification.KNNClassifier;
import cn.edu.cug.cs.gtl.ml.classification.NNClassifier;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.series.classification.Classification;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import cn.edu.cug.cs.gtl.series.common.pax.TIOPlane;
import cn.edu.cug.cs.gtl.series.distances.*;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This file is written by students.
 */
@Deprecated
public class Main {
    public static int alphabet = 16;
    public static Logger logger = Logger.getLogger(Main.class);


    String trainFilePath = Config.getTestInputDirectory() + cn.edu.cug.cs.gtl.io.File.separator + "UCRArchive_2018" + cn.edu.cug.cs.gtl.io.File.separator + "BirdChicken" + File.separator + "BirdChicken_TRAIN.tsv";

    String testFilePath = Config.getTestInputDirectory() + cn.edu.cug.cs.gtl.io.File.separator + "UCRArchive_2018" + cn.edu.cug.cs.gtl.io.File.separator + "BirdChicken" + File.separator + "BirdChicken_TEST.tsv";


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
    public static ArrayList<Double> haxList = new ArrayList<>();
    public static ArrayList<Double> euList = new ArrayList<>();


    ArrayList<String> trainFiles = new ArrayList<>();
    ArrayList<String> testFiles = new ArrayList<>();


    public void timeSeriesClassifier_hax() {
        try {


            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            TIOPlane tioPlane = TIOPlane.of(Math.min(trainMultiSeries.min(), testMultiSeries.min()),
                    Math.max(trainMultiSeries.max(), testMultiSeries.max()));

            HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10, tioPlane);
            System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void timeSeriesClassifier_sax() throws Exception {
        BasicConfigurator.configure();
        Layout layout = new PatternLayout("%d %r [%t] %p %c %x - %m%n");
        Appender appender = new RollingFileAppender(layout, "example.log");
        logger.addAppender(appender);
        try {

            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            SaxDistanceMetrics<TimeSeries> disFunc = new SaxDistanceMetrics<>(10, 16);
            //System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            logger.info(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void timeSeriesClassifier_esax() throws Exception {
        try {
            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            ESaxDistanceMetrics<TimeSeries> disFunc = new ESaxDistanceMetrics<>(10, 16);
            System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            //logger.info(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void timeSeriesClassifier_euclidean() {
        try {

            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            EuclideanDistanceMetrics<TimeSeries> disFunc = new EuclideanDistanceMetrics<>();
            System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void timeSeriesClassifier_dtw() {
        try {

            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            DTWDistanceMetrics<TimeSeries> disFunc = new DTWDistanceMetrics<>();
            System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void timeSeries_i0_j1() {
        try {

            MultiSeries trainMultiSeries = MultiSeries.readTSV(trainFilePath);
            MultiSeries testMultiSeries = MultiSeries.readTSV(testFilePath);

            for (int i = 0; i < 100; ++i) {
                TimeSeries si = testMultiSeries.getSeries(i);
                // si.print();
                for (int j = 0; j < 100; ++j) {
                    TimeSeries sj = trainMultiSeries.getSeries(j);
                    //sj.print();
                    double s = DistanceUtils.sax(si, sj, 10, 8);
                    System.out.println(i);
                    System.out.println(j);
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * pathList和nameList只需要在一个度量方式跑一遍即可
     * 因为dtw运行的时间十分漫长，因此把这两个放在sax度量方式里
     */


    public void timeSeries_testDtw_all() throws Exception {
        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));


                TIOPlane tioPlane = TIOPlane.of(Math.min(trainMultiSeries.min(), testMultiSeries.min()),
                        Math.max(trainMultiSeries.max(), testMultiSeries.max()));

                HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10, tioPlane);
                dtwList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * pathList保存的是根目录下各个数据文件下的训练数据和测试数据的绝对路径
     * nameList保存的是根目录下各个数据文件的名称且只需要跑一遍
     *
     * @throws Exception
     */

    public void timeSeries_testSax_all() throws IOException {
        PrintStream ps = new PrintStream("F:/TimeSeries/Results/SaxTg.txt");
        System.setOut(ps);
        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");

        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                nameList.add(f.getName());
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            System.out.print(nameList.get(k) + "\t");
            for (int w = 5; w < 21; w++) {
                try {
                    MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                    MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                    SaxTGDistanceMetrics<TimeSeries> disFunc = new SaxTGDistanceMetrics<>(w, alphabet);
                    System.out.print(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc) + "\t");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println();
        }
        PrintStream out = System.out;

    }


    public void timeSeries_testSaxTD_all() throws Exception {
        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                nameList.add(f.getName());
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }
        int w = 20;
        int alphabet = 16;
        System.out.println("w = " + w + "        " + "alphabet = " + alphabet);
        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                SaxDistanceMetrics<TimeSeries> disFunc = new SaxDistanceMetrics<>(w, alphabet);
                SaxTDDistanceMetrics<TimeSeries> disFunc1 = new SaxTDDistanceMetrics<>(w, alphabet);
                SaxTGDistanceMetrics<TimeSeries> disFunc2 = new SaxTGDistanceMetrics<>(w, alphabet);
                ESaxDistanceMetrics<TimeSeries> disFunc3 = new ESaxDistanceMetrics<>(w, alphabet);
                System.out.print(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc) + "        ");
                System.out.print(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc1) + "        ");
                System.out.print(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc2) + "        ");
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void timeSeries_testSaxTG_all() throws Exception {
        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                nameList.add(f.getName());
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                SaxTGDistanceMetrics<TimeSeries> disFunc = new SaxTGDistanceMetrics<>(20, 16);
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void timeSeries_testHax_all() throws Exception {
        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                TIOPlane tioPlane = TIOPlane.of(Math.min(trainMultiSeries.min(), testMultiSeries.min()),
                        Math.max(trainMultiSeries.max(), testMultiSeries.max()));

                HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10, tioPlane);
                haxList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void timeSeries_testEu_all() throws Exception {


        File file = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();

        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                EuclideanDistanceMetrics<TimeSeries> disFunc = new EuclideanDistanceMetrics<>();
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void timeSeries_testEsax_all() throws Exception {
        BasicConfigurator.configure();
        Layout layout = new PatternLayout("%d %r [%t] %p %c %x - %m%n");
        Appender appender = new RollingFileAppender(layout, "esax_full.log");
        logger.addAppender(appender);


        File file = new File("/gtl/trunk/data/UCRArchive_2018");
        File[] subFile = file.listFiles();
        List<String> trainPathList = new ArrayList<>();
        List<String> testPathList = new ArrayList<>();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                nameList.add(f.getName());
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }

        for (int k = 0; k < pathSize; k++) {
            //System.out.print(nameList.get(k) + "\t");
            //logger.info(nameList.get(k));
            String log = "";
            for (int w = 5; w < 21; w++) {
                try {
                    MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                    MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                    ESaxDistanceMetrics<TimeSeries> disFunc = new ESaxDistanceMetrics<>(w, alphabet);
                    String res = String.valueOf(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                    log += res + " ";
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            logger.info(nameList.get(k) + " " + log);
            //System.out.println();
        }
        PrintStream out = System.out;


    }

    /**
     * title 得到数据的Excel表的标题
     * 因为跑一个就很慢了所以把四个度量方式的分开写了
     *
     * @throws Exception
     */


    public void writeExcel() throws Exception {
        String[] title = {"DataName", "Dtw(error)", "Sax(error)", "Hax(error)", "Eu(error)"};
        File file = new File("D:\\gtl\\trunk\\data\\result.xls");
        try {
            file.createNewFile();
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            Label label = null;
            for (int i = 0; i < title.length; i++) {
                label = new Label(i, 0, title[i]);
                sheet.addCell(label);
            }

            for (int j = 0; j < pathList.size(); j++) {
                label = new Label(0, j, nameList.get(j));
                sheet.addCell(label);
                label = new Label(1, j, String.valueOf(dtwList.get(j)));
                sheet.addCell(label);
                label = new Label(2, j, String.valueOf(saxList.get(j)));
                sheet.addCell(label);
                label = new Label(3, j, String.valueOf(haxList.get(j)));
                sheet.addCell(label);
                label = new Label(4, j, String.valueOf(euList.get(j)));
                sheet.addCell(label);
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static double oneNNClassifier(MultiSeries trainTimeSeries, MultiSeries testTimeSeries, DistanceMetrics<TimeSeries> disfunc) {
        NNClassifier<TimeSeries, String> classifier = new NNClassifier<>(trainTimeSeries.toTrainSet(), testTimeSeries.toTestSet(), disfunc);
        return classifier.score();
    }

    public static double kNNClassifier(MultiSeries trainTimeSeries, MultiSeries testTimeSeries, DistanceMetrics<TimeSeries> disfunc) {
        KNNClassifier<TimeSeries, String> classifier = new KNNClassifier<>(trainTimeSeries.toTrainSet(), testTimeSeries.toTestSet(), disfunc);
        return classifier.score();
    }


    /**
     * 将得到的绝对路径分开
     * 其中trainPathList为训练数据的绝对路径
     * testPathList为测试数据的绝对路径
     */
    public static List<String> trainPathList = new ArrayList<>();
    public static List<String> testPathList = new ArrayList<>();


    public void getDataPath(File file) {
        File[] subFile = file.listFiles();
        for (File f : subFile) {
            if (f.isDirectory()) {
                File[] subFile1 = f.listFiles();
                nameList.add(f.getName());
                for (File f1 : subFile1) {
                    if (f1.isFile() && f1.getName().endsWith(".tsv")) {
                        pathList.add(f1.getPath());
                    }
                }
            }
        }
        int pathSize = pathList.size() / 2;
        for (int j = 0; j < pathSize; j++) {
            trainPathList.add(pathList.get(2 * j + 1));
            testPathList.add(pathList.get(2 * j));
        }
    }

    public void Sax_all() throws Exception {
        int pathSize = pathList.size() / 2;
        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                SaxDistanceMetrics<TimeSeries> disFunc = new SaxDistanceMetrics<>(10, 9);
                saxList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                System.out.println(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void Hax_all() throws Exception {
        int pathSize = pathList.size() / 2;
        File file = new File("D:\\gtl\\trunk\\data\\result.xls");
        Label dis = null;
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        WritableSheet sheet = workbook.createSheet("sheet1", 0);
        for (int k = 0; k < pathSize; k++) {
            try {
                MultiSeries trainMultiSeries = MultiSeries.readTSV(trainPathList.get(k));
                MultiSeries testMultiSeries = MultiSeries.readTSV(testPathList.get(k));

                TIOPlane tioPlane = TIOPlane.of(Math.min(trainMultiSeries.min(), testMultiSeries.min()),
                        Math.max(trainMultiSeries.max(), testMultiSeries.max()));

                HaxDistanceMetrics<TimeSeries> disFunc = new HaxDistanceMetrics<>(10, tioPlane);
                haxList.add(Classification.timeSeriesClassifier(trainMultiSeries, testMultiSeries, disFunc));
                dis = new Label(1, k, String.valueOf(haxList.get(k)));
                sheet.addCell(dis);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        workbook.write();
        workbook.close();

    }

    public static void main(String[] args) throws Exception {
        File abs = new File("D:\\gtl\\trunk\\data\\UCRArchive_2018");
        Main set = new Main();
        set.getDataPath(abs);
        set.writeExcel();
        //set.Hax_all();

    }
}

