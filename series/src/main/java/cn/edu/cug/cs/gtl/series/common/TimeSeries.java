package cn.edu.cug.cs.gtl.series.common;

import cn.edu.cug.cs.gtl.io.Storable;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.*;

/**
 * 表示一对序列数据，包含X轴和Y轴，对于时序数据而言，
 * X轴表示时间，Y轴表示序列值，
 * 当X轴的数据为空的时候，则表示X是一个由0开始，步长为1的一个递增序列，器长度与Y轴一致
 */
public class TimeSeries extends Series {

    private static final long serialVersionUID = -7930383387862030536L;
    //protected double [] data =null;//X轴表示时间, use super field data
    protected double[] dataY = null;//Y轴表示序列值
    protected String label = new String();//序列标识

    /**
     * 序列构造函数
     * float []xs = {0,1,2,3,4,5,6,7,8,9};
     * float []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = new TimeSeries(xs,ys);
     * TimeSeries s2 = new TimeSeries(null,ys);
     * s1和s2是等价的。
     *
     * @param xs
     * @param ys
     */
    TimeSeries(float[] xs, float[] ys) {
        assert ys != null;
        dataY = new double[ys.length];
        for (int j = 0; j < ys.length; ++j) {
            dataY[j] = ys[j];
        }
        if (xs == null) {
            data = new double[ys.length];
            // copy the data
            for (int i = 0; i < ys.length; ++i) {
                data[i] = i;
            }
        } else {
            data = new double[xs.length];
            // copy the data
            for (int i = 0; i < xs.length; ++i) {
                data[i] = xs[i];
            }
        }
    }

    TimeSeries() {
    }

    /**
     * 序列构造函数
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = new TimeSeries(xs,ys);
     * TimeSeries s2 = new TimeSeries(null,ys);
     * s1和s2是等价的。
     *
     * @param xs
     * @param ys
     */
    TimeSeries(double[] xs, double[] ys) {
        this(xs, ys, false);
    }

    /**
     * 序列构造函数
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = new TimeSeries(xs,ys);
     * TimeSeries s2 = new TimeSeries(null,ys);
     * s1和s2是等价的。
     * 如果bCopy为真，则Series将会复制xs和ys数组
     *
     * @param xs
     * @param ys
     * @param bCopy
     */
    TimeSeries(double[] xs, double[] ys, boolean bCopy) {
        assert ys != null;
        if (xs == null) {
            data = new double[ys.length];
            // copy the data
            for (int i = 0; i < ys.length; ++i) {
                data[i] = i;
            }
            if (bCopy) {
                dataY = new double[ys.length];
                for (int j = 0; j < ys.length; ++j) {
                    dataY[j] = ys[j];
                }
            } else {
                dataY = ys;
            }
        } else {
            if (bCopy) {
                dataY = new double[ys.length];
                data = new double[xs.length];
                // copy the data
                for (int i = 0; i < xs.length; ++i) {
                    data[i] = xs[i];
                }
                for (int j = 0; j < ys.length; ++j) {
                    dataY[j] = ys[j];
                }
            } else {
                dataY = ys;
                data = xs;
            }
        }
    }

    /**
     * 构建一个Series对象
     *
     * @param vs 序列值的数组,由于Series内部采用的是double数组，
     *           因此，传入float数组并不能带来更加高效的性能
     * @return
     */
    public static TimeSeries of(float[] vs) {
        return of(null, vs);
    }

    /**
     * 构建一个Series对象
     *
     * @param xs
     * @param ys
     * @return
     */
    public static TimeSeries of(float[] xs, float[] ys) {
        return new TimeSeries(xs, ys);
    }

    /**
     * 构建一个Series对象
     *
     * @param vs
     * @return
     */
    public static TimeSeries of(double[] vs) {
        return of(null, vs);
    }

    /**
     * 构建一个Series对象
     *
     * @param xs
     * @param ys
     * @return
     */
    public static TimeSeries of(double[] xs, double[] ys) {
        return of(xs, ys, false);
    }

    /**
     * 构建一个Series对象
     *
     * @param xs
     * @param ys
     * @param bCopy
     * @return
     */
    public static TimeSeries of(double[] xs, double[] ys, boolean bCopy) {
        return new TimeSeries(xs, ys, bCopy);
    }

    /**
     * 从数据流中读取信息构建一个新的Series对象，例如：
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = TimeSeries.of(xs,ys);
     * TimeSeries s2 = TimeSeries.of(ys);
     * try{
     * ByteArrayOutputStream baos=new ByteArrayOutputStream();
     * s1.write(baos);
     * byte[] bytes = baos.toByteArray();
     * baos.close();
     * ByteArrayInputStream bais=new ByteArrayInputStream(bytes);
     * TimeSeries s3 = TimeSeries.of(bais);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     * <p>
     * 或者从文件中读取信息构建Series
     * <p>
     * try{
     * FileOutputStream f = new FileOutputStream("test.series");
     * s1.write(f);
     * f.close();
     * FileInputStream f2= new FileInputStream("test.series");
     * TimeSeries s3 = TimeSeries.of(f2);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param inputStream
     * @return
     */
    public static TimeSeries of(InputStream inputStream) throws IOException {
        TimeSeries s = new TimeSeries();
        s.read(inputStream);
        return s;
    }

    /**
     * 从字节数组中读取信息，构造Series对象；
     * 该数组应该是调用Series的storeToByteArray()得到的字节数组,例如：
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = TimeSeries.of(xs,ys);
     * TimeSeries s2 = TimeSeries.of(ys);
     * try{
     * byte[] bytes = s1.storeToByteArray();
     * TimeSeries s3 = TimeSeries.of(bytes);
     * Assert.assertArrayEquals(ys,s3.getValues(),0.001);
     * Assert.assertArrayEquals(ys,s2.getValues(),0.001);
     * }
     * catch (IOException e){
     * e.printStackTrace();
     * }
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static TimeSeries of(byte[] bytes) throws IOException {
        TimeSeries s = new TimeSeries();
        s.loadFromByteArray(bytes);
        return s;
    }

    /**
     * 复制s中的数据，填充本对象
     *
     * @param s
     */
    public void copy(TimeSeries s) {
        this.label = s.label;
        dataY = new double[s.dataY.length];
        data = new double[s.data.length];
        // copy the data
        for (int i = 0; i < data.length; ++i) {
            data[i] = s.data[i];
        }
        for (int j = 0; j < dataY.length; ++j) {
            dataY[j] = s.dataY[j];
        }
    }

    /**
     * 获取时间序列长度，以Y轴数据长度为准
     *
     * @return
     */
    @Override
    public long length() {
        return this.dataY.length;
    }

    /**
     * 获取X轴的数值数组，在有些应用中，
     * X轴为一个从0开始，步长为1的递增数列，可以忽略；
     * 例如，在paa中，对于Series对象，则只会处理Y轴的数值
     *
     * @return
     */
    public double[] getDataX() {
        return this.data;
    }

    /**
     * 获取Y轴的数值数组
     *
     * @return
     */
    public double[] getDataY() {
        return this.dataY;
    }

    /**
     * 获取序列的值
     *
     * @return
     */
    @Override
    public double[] getValues() {
        return getDataY();
    }

    /**
     * 获取序列的标识字符串
     *
     * @return
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * 设置序列的标识字符串
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 打印输出序列，主要用于调试
     */
    public void print() {
        System.out.println(this.label);
        for (double it : data) {
            System.out.print(it);
            System.out.print(" ");
        }
        System.out.println(" ");
        for (double it : dataY) {
            System.out.print(it);
            System.out.print(" ");
        }
    }

    /**
     * 克隆一个新的Series对象
     *
     * @return
     */
    @Override
    public Object clone() {
        TimeSeries s = new TimeSeries();
        s.copy(this);
        return s;
    }

    /**
     * 从数据流中加载信息，填充Series对象
     * double []xs = {0,1,2,3,4,5,6,7,8,9};
     * double []ys= {50,10,20,30,40,70,90,10,30,40};
     * TimeSeries s1 = new TimeSeries(xs,ys);
     *
     * @param dataInput
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput dataInput) throws IOException {
        int s = dataInput.readInt();
        if (s > 0) {
            this.data = new double[s];
            for (int i = 0; i < s; ++i)
                this.data[i] = dataInput.readDouble();
        }
        s = dataInput.readInt();
        if (s > 0) {
            dataY = new double[s];
            for (int i = 0; i < s; ++i)
                dataY[i] = dataInput.readDouble();
        }
        label = StringUtils.loadString(dataInput);
        return true;
    }

    /**
     * 将Series的信息写入数据流
     *
     * @param dataOutput
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        int s = (data == null) ? 0 : data.length;
        dataOutput.writeInt(s);
        if (s > 0) {
            for (double si : this.data)
                dataOutput.writeDouble(si);
        }
        s = (dataY == null) ? 0 : dataY.length;
        dataOutput.writeInt(s);
        if (s > 0) {
            for (double si : dataY)
                dataOutput.writeDouble(si);
        }
        StringUtils.storeString(label, dataOutput);
        return true;
    }

    /**
     * @return the count of series in this object
     */
    @Override
    public long count() {
        return 1;
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
        double[] tsy = cn.edu.cug.cs.gtl.series.common.paa.Utils.subseries(this.dataY, paaSize, paaIndex);
        TimeSeries ts = TimeSeries.of(tsx, tsy);
        ts.setLabel(this.label);
        return ts;
    }

    /**
     * @return
     */
    @Override
    public double max() {
        return Series.max(this.dataY);
    }

    /**
     * @return
     */
    @Override
    public double min() {
        return Series.min(this.dataY);
    }

}
