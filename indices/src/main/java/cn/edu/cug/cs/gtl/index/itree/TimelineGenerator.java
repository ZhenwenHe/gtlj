package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.ComplexInterval;
import cn.edu.cug.cs.gtl.geom.GeomSuits;
import cn.edu.cug.cs.gtl.geom.Timeline;
import cn.edu.cug.cs.gtl.util.ArrayUtils;
import org.apache.spark.mllib.random.UniformGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TimelineGenerator {
    static double timeMaxValue = 10000;//最大时间值
    static double timeMinValue = 0;//最小时间值
    static double timelineMinLength = 50;//一条时间线的最小长度
    static double timelineMaxLength = 100;//一条时间线的最大长度
    static double intervalMinLength = 2;//间隔最小持续时间
    static double intervalMaxLength = 10;//间隔最大持续时间
    static int labelTypes = 5;//标识种类
    static final long numberTimelines = 10000;// the number of the generated timelines
    static String outputFileName = "d://devs//data//timeline//timelines.txt";
    static long numberIntervals = 0;
    static long intervalMinAttachment = 1024;//bytes
    static long intervalMaxAttachment = 2048;//bytes
    static byte defaultByteInData = 0;

    public static void main(String[] args) {
        //StandardNormalGenerator sng = new StandardNormalGenerator();
        UniformGenerator ug = new UniformGenerator();
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFileName));
            for (long i = 0; i < numberTimelines; ++i) {
                Timeline tl = generate();
                numberIntervals += tl.getLabeledIntervals().size();
                tl.getIdentifier().reset(i);
                bw.write(tl.toString());
                if (i < numberTimelines - 1)
                    bw.newLine();

                //create an attachment file
                String attachFileName = "d://devs//data//timeline//" + Long.valueOf(i).toString() + ".dat";
                DataOutputStream bos = new DataOutputStream(new FileOutputStream(attachFileName));
                for (ComplexInterval ci : tl.getLabeledIntervals()) {
                    int len = ci.getData().length;
                    bos.writeInt(len);
                    bos.write(ci.getData());
                }
                bos.close();
            }
            bw.close();

            System.out.println(numberTimelines);
            System.out.println(numberIntervals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * generate a timeline
     *
     * @return
     */
    public static Timeline generate() {
        //StandardNormalGenerator sng = new StandardNormalGenerator();
        UniformGenerator ug = new UniformGenerator();

        while (true) {
            double timelineLength = ug.nextValue() * (timelineMaxLength - timelineMinLength) + timelineMinLength;
            double timelineStartValue = ug.nextValue() * (timeMaxValue - timeMinValue) + timeMinValue;
            double timelineEndValue = timelineStartValue + timelineLength;
            double intervalStartValue = timelineStartValue;
            double intervalEndValue = intervalStartValue;
            double intervalLength = ug.nextValue() * (intervalMaxLength - intervalMinLength);
            long intervalData = (long) (ug.nextValue() * (intervalMaxAttachment - intervalMinAttachment));
            int label = 0;
            List<ComplexInterval> labeledIntervals = new ArrayList<>();
            do {
                intervalLength = ug.nextValue() * (intervalMaxLength - intervalMinLength);
                intervalEndValue = intervalStartValue + intervalLength;
                while (label == 0)
                    label = (int) (ug.nextValue() * labelTypes);
                ComplexInterval li = ComplexInterval.create(intervalStartValue,
                        intervalEndValue, Integer.valueOf(label).toString(), -1L, -1L);
                intervalData = (long) (ug.nextValue() * (intervalMaxAttachment - intervalMinAttachment));
                while (intervalData == 0)
                    intervalData = (long) (ug.nextValue() * (intervalMaxAttachment - intervalMinAttachment));
                li.setData(ArrayUtils.createByteArray((int) intervalData, defaultByteInData));
                labeledIntervals.add(li);
                intervalStartValue = intervalEndValue;
                label = 0;
            } while (intervalEndValue < timelineEndValue);

            if (labeledIntervals.size() > 0) {//generate a timeline
                Timeline tl = GeomSuits.createTimeline(
                        Identifier.create(-1L),
                        labeledIntervals
                );
                return tl;
            }
        }
    }
}
