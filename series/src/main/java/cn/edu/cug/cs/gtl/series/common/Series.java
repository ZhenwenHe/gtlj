package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.series.common.sax.SAXException;
import cn.edu.cug.cs.gtl.io.File;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.Storable;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Series implements Storable {


    private static final long serialVersionUID = -1765975818686067145L;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected double[] data;

    /**
     * 从TSV文件中读取数据构建时序数据集合
     *
     * @param name 时序数据文件名
     * @return
     * @throws IOException
     */
    public static MultiSeries readTSV(String name) throws IOException {
        File f = new File(name);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = br.readLine();
        int length = 0;
        List<double[]> yss = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        while (line != null) {
            String[] columns = line.split(FileDataSplitter.TSV.getDelimiter());
            length = columns.length - 1;
            double[] ys = new double[length];
            String label = columns[0];
            for (int i = 1; i < columns.length; ++i) {
                ys[i - 1] = Double.parseDouble(columns[i]);
            }
            yss.add(ys);
            labels.add(label);
            line = br.readLine();
        }
        double[] xs = new double[length];
        for (int i = 0; i < length; ++i)
            xs[i] = i;

        MultiSeries ms = new MultiSeries(xs, yss);
        ms.labels = labels;
        return ms;
    }

    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param fileName  时序数据文件名
     * @param columnIdx 从columnIdx开始读，下标从0开始
     * @param sizeLimit 总共读取sizeLimit行，0=all
     * @return 数据序列
     * @throws IOException
     */
    public static Series readNSV(String fileName, int columnIdx, int sizeLimit)
            throws IOException, SAXException {
        Path path = Paths.get(fileName);
        if (!(Files.exists(path))) {
            throw new SAXException("unable to load data - data source not found.");
        }
        BufferedReader reader = Files.newBufferedReader(path, DEFAULT_CHARSET);
        double[] ds = readNSV(reader, columnIdx, sizeLimit);
        return of(ds);
    }

    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param fileName 时序数据文件名
     * @return 数据序列
     * @throws IOException
     */
    public static Series readNSV(String fileName)
            throws IOException, SAXException {
        return readNSV(fileName, 0, 0);
    }

    /**
     * 从NSV文件中读取数据构建时序数据集合，所谓的NSV文件是以换行符为分隔的文件
     * ，也就是只有一列的CSV文件。
     * NSV is that file has a single double value on every line.
     *
     * @param br        时序数据文件名
     * @param columnIdx 从columnIdx开始读，下标从0开始
     * @param sizeLimit 总共读取sizeLimit行
     * @return 数据序列的数组
     * @throws IOException
     */
    public static double[] readNSV(BufferedReader br, int columnIdx, int sizeLimit)
            throws IOException, SAXException, NumberFormatException {
        ArrayList<Double> preRes = new ArrayList<Double>();
        int lineCounter = 0;

        String line = null;
        while ((line = br.readLine()) != null) {
            String[] split = line.trim().split("\\s+");
            if (split.length < columnIdx) {
                String message = "Unable to read data from column " + columnIdx;
                br.close();
                throw new SAXException(message);
            }
            String str = split[columnIdx];
            double num = Double.NaN;
            try {
                num = Double.valueOf(str);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("error in  the row " + lineCounter + " with value \"" + str + "\"");
            }

            preRes.add(num);
            lineCounter++;
            if ((0 != sizeLimit) && (lineCounter >= sizeLimit)) {
                break;
            }
        }
        br.close();
        double[] res = new double[preRes.size()];
        for (int i = 0; i < preRes.size(); i++) {
            res[i] = preRes.get(i);
        }
        return res;
    }


    /**
     * Finds the maximal value in series.
     *
     * @param series The series.
     * @return The max value.
     */
    public static double max(double[] series) {
        double max = Double.MIN_VALUE;
        for (int i = 0; i < series.length; i++) {
            if (max < series[i]) {
                max = series[i];
            }
        }
        return max;
    }

    /**
     * Finds the minimal value in series.
     *
     * @param series The series.
     * @return The min value.
     */
    public static double min(double[] series) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < series.length; i++) {
            if (min > series[i]) {
                min = series[i];
            }
        }
        return min;
    }

    /**
     * Computes the mean value of series.
     *
     * @param series The series.
     * @return The mean value.
     */
    public static double mean(double[] series) {
        double res = 0D;
        int count = 0;
        for (double tp : series) {
            res += tp;
            count += 1;
        }
        if (count > 0) {
            return res / ((Integer) count).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * Computes the mean value of series.
     *
     * @param series The series.
     * @return The mean value.
     */
    public static double mean(int[] series) {
        double res = 0D;
        int count = 0;
        for (int tp : series) {
            res += (double) tp;
            count += 1;

        }
        if (count > 0) {
            return res / ((Integer) count).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * Computes the median value of series.
     *
     * @param series The series.
     * @return The median value.
     */
    public static double median(double[] series) {
        double[] clonedSeries = series.clone();
        Arrays.sort(clonedSeries);

        double median;
        if (clonedSeries.length % 2 == 0) {
            median = (clonedSeries[clonedSeries.length / 2]
                    + (double) clonedSeries[clonedSeries.length / 2 - 1]) / 2;
        } else {
            median = clonedSeries[clonedSeries.length / 2];
        }
        return median;
    }

    /**
     * Compute the variance (方差) of series.
     *
     * @param series The series.
     * @return The variance.
     */
    public static double variance(double[] series) {
        double res = 0D;
        double mean = mean(series);
        int count = 0;
        for (double tp : series) {
            res += (tp - mean) * (tp - mean);
            count += 1;
        }
        if (count > 0) {
            return res / ((Integer) (count - 1)).doubleValue();
        }
        return Double.NaN;
    }

    /**
     * 标准差（Standard Deviation）,又常称均方差，是离均差平方的算术平均数的平方根，用σ表示。
     * 在概率统计中最常使用作为统计分布程度上的测量。标准差是方差的算术平方根。
     * 标准差能反映一个数据集的离散程度。
     * 平均数相同的两组数据，标准差未必相同。
     *
     * @param series The series.
     * @return the standard deviation.
     */
    public static double standardDeviation(double[] series) {
        double num0 = 0D;
        double sum = 0D;
        int count = 0;
        for (double tp : series) {
            num0 = num0 + tp * tp;
            count += 1;
        }
        double len = ((Integer) count).doubleValue();
        return Math.sqrt((len * num0 - sum * sum) / (len * (len - 1)));
    }

    /**
     * 标准差（Standard Deviation）
     *
     * @param a
     * @param b
     * @return
     */
    public static double standardDeviation(double a, double[] b) {
        double s = 0.0;
        int n = b.length;
        for (int i = 0; i < n; i++) {
            double d = (b[i] - a) * (b[i] - a);
            s += d;
        }
        s = s / n;
        return Math.sqrt(s);
    }

    /**
     * Z-Normalize routine.
     *
     * @param series                 the input series.
     * @param normalizationThreshold the zNormalization threshold value.
     * @return Z-normalized series.
     */
    public static double[] zNormalize(double[] series, double normalizationThreshold) {
        double[] res = new double[series.length];
        double sd = standardDeviation(series);
        if (sd < normalizationThreshold) {
            // return array of zeros
            return res;
        }
        double mean = mean(series);
        for (int i = 0; i < res.length; i++) {
            res[i] = (series[i] - mean) / sd;
        }
        return res;
    }

    /**
     * Extract subseries out of series.
     *
     * @param series The series array.
     * @param start  the fragment start.
     * @param end    the fragment end.
     * @return The subseries.
     * @throws IndexOutOfBoundsException If error occurs.
     */
    public static double[] subseries(double[] series, int start, int end)
            throws IndexOutOfBoundsException {
        if ((start > end) || (start < 0) || (end > series.length)) {
            throw new IndexOutOfBoundsException("Unable to extract subseries, series length: "
                    + series.length + ", start: " + start + ", end: " + String.valueOf(end - start));
        }
        return Arrays.copyOfRange(series, start, end);
    }


    Series(double[] data) {
        assert data != null;
        this.data = data;
    }

    Series(float[] data) {
        assert data != null;
        this.data = new double[data.length];
        int i = 0;
        for (float d : data) {
            this.data[i] = d;
            ++i;
        }
    }


    Series(int[] data) {
        assert data != null;
        this.data = new double[data.length];
        int i = 0;
        for (int d : data) {
            this.data[i] = d;
            ++i;
        }
    }

    Series(long[] data) {
        assert data != null;
        this.data = new double[data.length];
        int i = 0;
        for (long d : data) {
            this.data[i] = d;
            ++i;
        }
    }

    Series() {
        this.data = null;
    }

    /**
     * create a new Series object
     *
     * @param data
     * @return
     */
    public static Series of(int[] data) {
        return new Series(data);
    }

    /**
     * create a new Series object
     *
     * @param data
     * @return
     */
    public static Series of(long[] data) {
        return new Series(data);
    }

    /**
     * create a new Series object
     *
     * @param data
     * @return
     */
    public static Series of(float[] data) {
        return new Series(data);
    }


    /**
     * create a new Series object
     *
     * @param data
     * @return
     */
    public static Series of(double[] data) {
        return new Series(data);
    }


    /**
     * create a new Series Object from a byte array,
     * this byte array must be generated by storeToByteArray() function
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static Series of(byte[] bytes) throws IOException {
        Series s = new Series();
        s.loadFromByteArray(bytes);
        return s;
    }

    /**
     * create a new Series Object from a input stream,
     * it must be generated by write() function
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static Series of(InputStream inputStream) throws IOException {
        Series s = new Series();
        s.read(inputStream);
        return s;
    }

    /**
     * @return
     */
    public double[] getValues() {
        return this.data;
    }

    /**
     * 对象深拷贝
     *
     * @return 返回新的对象
     */
    @Override
    public Object clone() {
        double[] dat = Arrays.copyOf(this.data, this.data.length);
        return new Series(dat);
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        int s = in.readInt();
        if (s > 0) {
            this.data = new double[s];
            for (int i = 0; i < s; ++i) {
                this.data[i] = in.readDouble();
            }
        }
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out ，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return 执行成功返回true，否则返回false
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        int s = this.data.length;
        out.writeInt(s);
        if (s > 0) {
            for (int i = 0; i < s; ++i)
                out.writeDouble(this.data[i]);
        }
        return true;
    }

    /**
     * @return the count of series in this object
     */
    public long count() {
        return 0;
    }


    /**
     * return the length of the series
     *
     * @return
     */
    public long length() {
        return data == null ? 0 : data.length;
    }


    /**
     * calculate sub-series
     *
     * @param paaSize
     * @param paaIndex
     * @return
     */
    public Series subseries(int paaSize, int paaIndex) {
        double[] ts = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(this.data, paaSize, paaIndex);
        return Series.of(ts);
    }

    /**
     * @return
     */
    public double max() {
        return Series.max(this.data);
    }

    /**
     * @return
     */
    public double min() {
        return Series.min(this.data);
    }

}
