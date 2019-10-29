package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.dataset.TrainSet;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代表多值时序数据，包含一个时间轴X和多个（至少一个）Y轴序列，每个Y轴上的序列具有一个标签
 */
public class MultiSeries extends Series {
    private static final long serialVersionUID = 225842638554087888L;
    //double [] data =null;//X轴表示时间, use super field data
    List<double[]> dataY = null;
    List<String> labels = null;


    /**
     * @param yss
     * @return
     */
    public static MultiSeries of(double[][] yss) {
        double[] xs = new double[yss[0].length];
        for (int i = 0; i < xs.length; ++i)
            xs[i] = i;
        return new MultiSeries(xs, yss, false);
    }

    /**
     * @param xs
     * @param yss
     * @return
     */
    public static MultiSeries of(double[] xs, double[][] yss) {
        assert xs != null;
        return new MultiSeries(xs, yss, false);
    }

    /**
     * @param xs
     * @param yss
     * @param bCopy
     * @return
     */
    public static MultiSeries of(double[] xs, double[][] yss, boolean bCopy) {
        assert xs != null && yss != null;
        return new MultiSeries(xs, yss, bCopy);
    }

    /**
     * @param xs
     * @param yss
     * @return
     */
    public static MultiSeries of(double[] xs, List<double[]> yss) {
        return new MultiSeries(xs, yss, false);
    }

    /**
     * @param xs
     * @param yss
     * @param bCopy
     * @return
     */
    public static MultiSeries of(double[] xs, List<double[]> yss, boolean bCopy) {
        return new MultiSeries(xs, yss, bCopy);
    }

    /**
     * @param yss
     * @return
     */
    public static MultiSeries of(float[][] yss) {
        float[] xs = new float[yss[0].length];
        for (int i = 0; i < xs.length; ++i)
            xs[i] = i;
        return new MultiSeries(xs, yss);
    }

    /**
     * @param xs
     * @param yss
     * @return
     */
    public static MultiSeries of(float[] xs, float[][] yss) {
        return new MultiSeries(xs, yss);
    }

    /**
     * @param xs
     * @param yss
     * @return
     */
    public static MultiSeries of(float[] xs, List<float[]> yss) {
        return new MultiSeries(xs, yss);
    }

    /**
     * double []xs = {1,2,3,4,5,6,7,8,9};
     * double[][] ys={{1,1,1,1,1,1,1,1,1},
     * {2,2,2,2,2,2,2,2,2},
     * {3,3,3,3,3,3,3,3,3},
     * {4,4,4,4,4,4,4,4,4},
     * {5,5,5,5,5,5,5,5,5}};
     * MultiSeries ms = MultiSeries.of(xs,ys);
     * try {
     * byte [] bytes = ms.storeToByteArray();
     * MultiSeries ms2 = MultiSeries.of(bytes);
     * TimeSeries s2 = ms2.getSeries(0);
     * Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static MultiSeries of(byte[] bytes) throws IOException {
        MultiSeries ms = new MultiSeries();
        ms.loadFromByteArray(bytes);
        return ms;
    }

    /**
     * double []xs = {1,2,3,4,5,6,7,8,9};
     * double[][] ys={{1,1,1,1,1,1,1,1,1},
     * {2,2,2,2,2,2,2,2,2},
     * {3,3,3,3,3,3,3,3,3},
     * {4,4,4,4,4,4,4,4,4},
     * {5,5,5,5,5,5,5,5,5}};
     * MultiSeries ms = MultiSeries.of(xs,ys);
     * try {
     * FileOutputStream f = new FileOutputStream("test.series");
     * ms.write(f);
     * f.close();
     * FileInputStream f2= new FileInputStream("test.series");
     * MultiSeries ms2 = MultiSeries.of(f2);
     * TimeSeries s2 = ms2.getSeries(0);
     * Assert.assertArrayEquals(s2.getValues(),ys[0],0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static MultiSeries of(InputStream inputStream) throws IOException {
        MultiSeries ms = new MultiSeries();
        ms.read(inputStream);
        return ms;
    }

    /**
     *
     */
    MultiSeries() {

    }

    /**
     * @param xs  time axis, time interval
     * @param yss
     */
    MultiSeries(double[] xs, double[][] yss) {
        this(xs, yss, false);
    }

    /**
     * @param xs
     * @param yss
     * @param bCopy
     */
    MultiSeries(double[] xs, double[][] yss, boolean bCopy) {
        if (bCopy) {
            data = new double[xs.length];
            for (int k = 0; k < xs.length; ++k) {
                data[k] = xs[k];
            }
            dataY = new ArrayList<>(yss.length);
            for (int i = 0; i < yss.length; ++i) {
                double[] t = Arrays.copyOf(yss[i], yss[i].length);
                dataY.add(t);
            }
        } else {
            data = xs;
            dataY = new ArrayList<>(yss.length);
            for (int i = 0; i < yss.length; ++i) {
                dataY.add(yss[i]);
            }
        }
        labels = new ArrayList<>();
        for (int i = 0; i < xs.length; ++i)
            labels.add(Integer.valueOf(i).toString());
    }

    /**
     * @param xs
     * @param yss
     */
    public MultiSeries(float[] xs, float[][] yss) {
        data = new double[xs.length];
        for (int k = 0; k < xs.length; ++k) {
            data[k] = xs[k];
        }
        dataY = new ArrayList<>(yss.length);
        for (int i = 0; i < yss.length; ++i) {
            double[] t = new double[yss[i].length];
            for (int j = 0; j < yss[i].length; ++j) {
                t[j] = yss[i][j];
            }
            dataY.add(t);
        }
        labels = new ArrayList<>();
        for (int i = 0; i < xs.length; ++i)
            labels.add(Integer.valueOf(i).toString());
    }

    /**
     * @param xs
     * @param yss
     */
    MultiSeries(float[] xs, List<float[]> yss) {
        data = new double[xs.length];
        for (int k = 0; k < xs.length; ++k) {
            data[k] = xs[k];
        }
        dataY = new ArrayList<>(yss.size());
        for (int i = 0; i < yss.size(); ++i) {
            double[] t = new double[yss.get(i).length];
            for (int j = 0; j < yss.get(i).length; ++j) {
                t[j] = yss.get(i)[j];
            }
            dataY.add(t);
        }
        labels = new ArrayList<>();
        for (int i = 0; i < xs.length; ++i)
            labels.add(Integer.valueOf(i).toString());
    }

    /**
     * @param xs
     * @param yss
     */
    MultiSeries(double[] xs, List<double[]> yss) {
        this(xs, yss, false);
    }

    /**
     * @param xs
     * @param yss
     * @param bCopy
     */
    MultiSeries(double[] xs, List<double[]> yss, boolean bCopy) {
        if (bCopy) {
            data = new double[xs.length];
            for (int k = 0; k < xs.length; ++k) {
                data[k] = xs[k];
            }
            dataY = new ArrayList<>(yss.size());
            for (int i = 0; i < yss.size(); ++i) {
                double[] t = Arrays.copyOf(yss.get(i), yss.get(i).length);
                dataY.add(t);
            }
        } else {
            data = xs;
            dataY = yss;
        }
        labels = new ArrayList<>();
        for (int i = 0; i < xs.length; ++i)
            labels.add(Integer.valueOf(i).toString());
    }

    /**
     * @return
     */
    @Override
    public long length() {
        return this.data.length;
    }

    /**
     * @param i
     * @return
     */
    public TimeSeries getSeries(int i) {
        TimeSeries ts = TimeSeries.of(data, dataY.get(i));
        ts.setLabel(this.labels.get(i));
        return ts;
    }

    /**
     * @return the count of series in this object
     */
    @Override
    public long count() {
        return this.dataY.size();
    }

    /**
     * @param i
     * @return
     */
    public String getLabel(int i) {
        return this.labels.get(i);
    }

    /**
     * @return
     */
    public List<String> getLabels() {
        return this.labels;
    }

    /**
     * @param ls
     */
    public void setLabels(List<String> ls) {
        this.labels = ls;
    }

    /**
     * @param ls
     */
    public void setLabels(String[] ls) {
        this.labels = new ArrayList<>();
        for (String s : ls)
            this.labels.add(s);
    }

    /**
     * @return
     */
    @Override
    public Object clone() {
        MultiSeries ms = new MultiSeries(data, dataY, true);
        if (labels != null) {
            ms.labels = new ArrayList<>(labels.size());
            for (String s : labels) {
                ms.labels.add(s);
            }
        }
        return ms;
    }

    /**
     * @param dataInput
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput dataInput) throws IOException {
        int s = dataInput.readInt();
        if (s > 0) {
            data = new double[s];
            for (int i = 0; i < s; ++i) {
                data[i] = dataInput.readDouble();
            }
        }
        s = dataInput.readInt();
        if (s > 0) {
            dataY = new ArrayList<>();
            for (int k = 0; k < s; ++k) {
                int c = dataInput.readInt();
                double[] t = new double[c];
                for (int i = 0; i < c; ++i) {
                    t[i] = dataInput.readDouble();
                }
                dataY.add(t);
            }
        }
        s = dataInput.readInt();
        if (s > 0) {
            labels = new ArrayList<>();
            for (int i = 0; i < s; ++i)
                labels.add(StringUtils.load(dataInput));
        }
        return true;
    }

    /**
     * @param dataOutput
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        int s = data == null ? 0 : data.length;
        dataOutput.writeInt(s);
        if (s > 0) {
            for (double d : data)
                dataOutput.writeDouble(d);
        }
        s = dataY == null ? 0 : dataY.size();
        dataOutput.writeInt(s);
        if (s > 0) {
            for (double[] dd : dataY) {
                int c = dd.length;
                dataOutput.writeInt(c);
                for (double d : dd) {
                    dataOutput.writeDouble(d);
                }
            }
        }
        s = labels == null ? 0 : labels.size();
        dataOutput.writeInt(s);
        if (s > 0) {
            for (String str : labels) {
                StringUtils.store(str, dataOutput);
            }
        }
        return true;
    }

    /**
     * @return
     */
    public TrainSet<TimeSeries, String> toTrainSet() {
        long c = this.count();
        ArrayList<TimeSeries> ss = new ArrayList<>();
        ArrayList<String> ls = new ArrayList<>();
        for (int i = 0; i < c; ++i) {
            TimeSeries s = getSeries(i);
            ls.add(s.getLabel());
            ss.add(s);
        }
        return new TrainSet<>(ss, ls);
    }

    /**
     * @return
     */
    public TestSet<TimeSeries, String> toTestSet() {
        long c = this.count();
        ArrayList<TimeSeries> ss = new ArrayList<>((int) c);
        ArrayList<String> ls = new ArrayList<>((int) c);
        for (int i = 0; i < c; ++i) {
            TimeSeries s = getSeries(i);
            ls.add(s.getLabel());
            ss.add(s);
        }
        return new TestSet<>(ss, ls);
    }

    /**
     * calculate sub-series
     *
     * @param paaSize
     * @param paaIndex
     * @return
     */
    @Override
    public Series subseries(int paaSize, int paaIndex) {
        double[] tsx = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(this.data, paaSize, paaIndex);
        List<double[]> ls = new ArrayList<>();
        for (double[] da : this.dataY) {
            double[] tsy = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(da, paaSize, paaIndex);
        }
        MultiSeries ms = MultiSeries.of(tsx, ls, false);
        if (this.labels != null) {
            ms.setLabels(this.labels.subList(0, this.labels.size()));
        }
        return ms;
    }

    /**
     * @return
     */
    @Override
    public double max() {
        double r = -Double.MAX_VALUE;
        for (double[] a : this.dataY) {
            r = Math.max(r, Series.max(a));
        }
        return r;
    }

    /**
     * @return
     */
    @Override
    public double min() {
        double r = Double.MAX_VALUE;
        for (double[] a : this.dataY) {
            r = Math.min(r, Series.min(a));
        }
        return r;
    }

    /**
     * 获取所有序列的值
     *
     * @return
     */
    @Override
    public double[] getValues() {
        int s = 0;
        for (double[] d : this.dataY)
            s += d.length;
        double[] r = new double[s];
        int i = 0;
        for (double[] d : this.dataY) {
            System.arraycopy(d, 0, r, i, d.length);
            i += d.length;
        }
        return r;
    }

    /**
     * to time series list
     *
     * @return
     */
    public List<TimeSeries> toList() {
        int c = this.dataY.size();
        List<TimeSeries> list = new ArrayList<>(c);
        int i = 0;
        for (double[] d : this.dataY) {
            list.set(i, TimeSeries.of(this.data, d, true));
            ++i;
        }
        return list;
    }

    /**
     * to time series array
     *
     * @return
     */
    public TimeSeries[] toArray() {
        int c = this.dataY.size();
        TimeSeries[] tss = new TimeSeries[c];
        int i = 0;
        for (double[] d : this.dataY) {
            tss[i] = TimeSeries.of(this.data, d, true);
            ++i;
        }
        return tss;
    }
}
