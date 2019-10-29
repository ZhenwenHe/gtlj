package cn.edu.cug.cs.gtl.series.common.sax;

import cn.edu.cug.cs.gtl.array.Array;
import cn.edu.cug.cs.gtl.series.common.MultiSeries;
import cn.edu.cug.cs.gtl.series.common.Series;
import cn.edu.cug.cs.gtl.series.common.TimeSeries;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.text.NumberFormat;
import java.util.*;

import static cn.edu.cug.cs.gtl.series.common.paa.Utils.paa;
import static org.apache.commons.math3.stat.StatUtils.mean;

public class Utils {

    /**
     * Convert the series into SAX string representation.
     *
     * @param ts       the series.
     * @param paaSize  the PAA size.
     * @param alphabet
     * @return
     */
    public static char[] sax(Series ts, int paaSize, int alphabet) {
        try {
            NormalAlphabet normalAlphabet = new NormalAlphabet();
            return seriesToString(ts.getValues(), paaSize, normalAlphabet.getCuts(alphabet), Double.MIN_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Convert the series into SAX string representation.
     *
     * @param ts         the series.
     * @param paaSize    the PAA size.
     * @param cuts       the alphabet cuts.
     * @param nThreshold the normalization thresholds.
     * @return The SAX representation for series.
     */
    public static char[] sax(double[] ts, int paaSize, double[] cuts, double nThreshold) {
        try {
            return seriesToString(ts, paaSize, cuts, nThreshold);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Converts the input time series into a SAX data structure via chunking and Z normalization.
     *
     * @param ts         the input data.
     * @param paaSize    the PAA size.
     * @param cuts       the Alphabet cuts.
     * @param nThreshold the normalization threshold value.
     * @return SAX representation of the time series.
     * @throws SAXException if error occurs.
     */
    public SAXRecords saxByChunking(double[] ts, int paaSize, double[] cuts, double nThreshold)
            throws Exception, SAXException {

        SAXRecords saxFrequencyData = new SAXRecords();

        // Z normalize it
        double[] normalizedTS = Series.zNormalize(ts, nThreshold);

        // perform PAA conversion if needed
        double[] paa = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(normalizedTS, paaSize);

        // Convert the PAA to a string.
        char[] currentString = seriesToString(paa, cuts);

        // create the data structure
        for (int i = 0; i < currentString.length; i++) {
            char c = currentString[i];
            int pos = (int) Math.floor(i * ts.length / currentString.length);
            saxFrequencyData.add(String.valueOf(c).toCharArray(), pos);
        }

        return saxFrequencyData;

    }

    /**
     * Converts the input time series into a SAX data structure via sliding window and Z
     * normalization.
     *
     * @param ts         the input data.
     * @param windowSize the sliding window size.
     * @param paaSize    the PAA size.
     * @param cuts       the Alphabet cuts.
     * @param nThreshold the normalization threshold value.
     * @param strategy   the NR strategy.
     * @return SAX representation of the time series.
     * @throws SAXException if error occurs.
     */
    public SAXRecords saxByWindow(double[] ts, int windowSize, int paaSize, double[] cuts,
                                  NumerosityReductionStrategy strategy, double nThreshold)
            throws Exception, SAXException {

        if (windowSize > ts.length) {
            throw new SAXException(
                    "Unable to saxify via window, window size is greater than the timeseries length...");
        }

        // the resulting data structure init
        //
        SAXRecords saxFrequencyData = new SAXRecords();

        // scan across the time series extract sub sequences, and convert them to strings
        char[] previousString = null;

        for (int i = 0; i <= ts.length - windowSize; i++) {

            // fix the current subsection
            double[] subSection = Arrays.copyOfRange(ts, i, i + windowSize);

            // Z normalize it
            subSection = Series.zNormalize(subSection, nThreshold);

            // perform PAA conversion if needed
            double[] paa = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(subSection, paaSize);

            // Convert the PAA to a string.
            char[] currentString = seriesToString(paa, cuts);

            if (null != previousString) {
                if (NumerosityReductionStrategy.EXACT.equals(strategy)
                        && Arrays.equals(previousString, currentString)) {
                    // NumerosityReduction
                    continue;
                } else if (NumerosityReductionStrategy.MINDIST.equals(strategy)
                        && checkMinDistIsZero(previousString, currentString)) {
                    continue;
                }

            }

            previousString = currentString;

            saxFrequencyData.add(currentString, i);
        }

        // ArrayList<Integer> keys = saxFrequencyData.getAllIndices();
        // for (int i : keys) {
        // System.out.println(i + "," + String.valueOf(saxFrequencyData.getByIndex(i).getPayload()));
        // }

        return saxFrequencyData;

    }

    /**
     * Converts the input time series into a SAX data structure via sliding window and Z
     * normalization. The difference between this function and ts2saxViaWindow is that in this
     * function, Z normalization occurs on entire range, rather than the sliding window.
     *
     * @param ts         the input data.
     * @param windowSize the sliding window size.
     * @param paaSize    the PAA size.
     * @param cuts       the Alphabet cuts.
     * @param nThreshold the normalization threshold value.
     * @param strategy   the NR strategy.
     * @return SAX representation of the time series.
     * @throws SAXException if error occurs.
     */
    public SAXRecords saxByWindowGlobalZNorm(double[] ts, int windowSize, int paaSize,
                                             double[] cuts, NumerosityReductionStrategy strategy,
                                             double nThreshold) throws Exception, SAXException {

        // the resulting data structure init
        //
        SAXRecords saxFrequencyData = new SAXRecords();

        // scan across the time series extract sub sequences, and convert them to strings
        char[] previousString = null;

        // normalize the entire range
        double[] normalizedData = Series.zNormalize(ts, nThreshold);

        for (int i = 0; i <= ts.length - windowSize; i++) {

            // get the current subsection
            double[] subSection = Arrays.copyOfRange(normalizedData, i, i + windowSize);

            // perform PAA conversion if needed
            double[] paa = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(subSection, paaSize);

            // Convert the PAA to a string.
            char[] currentString = seriesToString(paa, cuts);

            if (null != previousString) {

                if (NumerosityReductionStrategy.EXACT.equals(strategy)
                        && Arrays.equals(previousString, currentString)) {
                    // NumerosityReduction
                    continue;
                } else if (NumerosityReductionStrategy.MINDIST.equals(strategy)
                        && checkMinDistIsZero(previousString, currentString)) {
                    continue;
                }

            }

            previousString = currentString;

            saxFrequencyData.add(currentString, i);
        }

        return saxFrequencyData;

    }

    /**
     * Converts the input time series into a SAX data structure via sliding window and Z
     * normalization.
     *
     * @param ts         the input data.
     * @param windowSize the sliding window size.
     * @param paaSize    the PAA size.
     * @param cuts       the Alphabet cuts.
     * @param nThreshold the normalization threshold value.
     * @param strategy   the NR strategy.
     * @param skips      The list of points which shall be skipped during conversion; this feature is
     *                   particularly important when building a concatenated from pieces time series and junction shall
     *                   not make it into the grammar.
     * @return SAX representation of the time series.
     * @throws SAXException if error occurs.
     */
    public SAXRecords saxByWindowSkipping(double[] ts, int windowSize, int paaSize, double[] cuts,
                                          NumerosityReductionStrategy strategy, double nThreshold,
                                          ArrayList<Integer> skips)
            throws SAXException, Exception {

        // the resulting data structure init
        //
        SAXRecords saxFrequencyData = new SAXRecords();

        Collections.sort(skips);
        int cSkipIdx = 0;

        // scan across the time series extract sub sequences, and convert them to strings
        char[] previousString = null;
        boolean skipped = false;

        for (int i = 0; i < ts.length - (windowSize - 1); i++) {

            // skip what need to be skipped
            if (cSkipIdx < skips.size() && i == skips.get(cSkipIdx)) {
                cSkipIdx = cSkipIdx + 1;
                skipped = true;
                continue;
            }

            // fix the current subsection
            double[] subSection = Arrays.copyOfRange(ts, i, i + windowSize);

            // Z normalize it
            subSection = Series.zNormalize(subSection, nThreshold);

            // perform PAA conversion if needed
            double[] paa = cn.edu.cug.cs.gtl.series.common.paa.Utils.paa(subSection, paaSize);

            // Convert the PAA to a string.
            char[] currentString = seriesToString(paa, cuts);

            if (!(skipped) && null != previousString) {

                if (NumerosityReductionStrategy.EXACT.equals(strategy)
                        && Arrays.equals(previousString, currentString)) {
                    // NumerosityReduction
                    continue;
                } else if (NumerosityReductionStrategy.MINDIST.equals(strategy)
                        && checkMinDistIsZero(previousString, currentString)) {
                    continue;
                }

            }

            previousString = currentString;
            if (skipped) {
                skipped = false;
            }

            saxFrequencyData.add(currentString, i);
        }

        return saxFrequencyData;
    }


    /**
     * Convert the series into SAX string representation.
     *
     * @param ts         the series.
     * @param paaSize    the PAA size.
     * @param cuts       the alphabet cuts.
     * @param nThreshold the normalization thresholds.
     * @return The SAX representation for series.
     * @throws SAXException if error occurs.
     */
    public static char[] seriesToString(double[] ts, int paaSize, double[] cuts, double nThreshold)
            throws Exception, SAXException {
        if (paaSize == ts.length) {
            return seriesToString(Series.zNormalize(ts, nThreshold), cuts);
        } else {
            // perform PAA conversion
            double[] paa = paa(Series.zNormalize(ts, nThreshold), paaSize);
            return seriesToString(paa, cuts);
        }
    }

    /**
     * Converts the series into string using given cuts intervals. Useful for not-normal
     * distribution cuts.
     *
     * @param vals The series.
     * @param cuts The cut intervals.
     * @return The series SAX representation.
     */
    public static char[] seriesToString(double[] vals, double[] cuts) {
        char[] res = new char[vals.length];
        for (int i = 0; i < vals.length; i++) {
            res[i] = numberToCharacter(vals[i], cuts);
        }
        return res;
    }

    /**
     * Convert the series into the index using SAX cuts.
     *
     * @param series The series to convert.
     * @param cuts   The alphabet cuts.
     * @return SAX cuts indices.
     * @throws Exception if error occurs.
     */
    public static int[] seriesToIndex(double[] series, double[] cuts) throws Exception {
        int[] res = new int[series.length];
        for (int i = 0; i < series.length; i++) {
            res[i] = numberToIndex(series[i], cuts);
        }
        return res;
    }

    /**
     * Compute the distance between the two chars based on the ASCII symbol codes.
     *
     * @param a The first char.
     * @param b The second char.
     * @return The distance.
     */
    public static int characterDistance(char a, char b) {
        return Math.abs(Character.getNumericValue(a) - Character.getNumericValue(b));
    }

    /**
     * Compute the distance between the two strings, this function use the numbers associated with
     * ASCII codes, i.e. distance between a and b would be 1.
     *
     * @param a The first string.
     * @param b The second string.
     * @return The pairwise distance.
     * @throws SAXException if length are differ.
     */
    public static int stringDistance(char[] a, char[] b) throws SAXException {
        if (a.length == b.length) {
            int distance = 0;
            for (int i = 0; i < a.length; i++) {
                int tDist = Math.abs(Character.getNumericValue(a[i]) - Character.getNumericValue(b[i]));
                distance += tDist;
            }
            return distance;
        } else {
            throw new SAXException("Unable to compute SAX distance, string lengths are not equal");
        }
    }

    /**
     * This function implements SAX MINDIST function which uses alphabet based distance matrix.
     *
     * @param a              The SAX string.
     * @param b              The SAX string.
     * @param distanceMatrix The distance matrix to use.
     * @param n              the time series length (sliding window length).
     * @param w              the number of PAA segments.
     * @return distance between strings.
     * @throws SAXException If error occurs.
     */
    public static double saxMinDist(char[] a, char[] b, double[][] distanceMatrix, int n, int w)
            throws SAXException {
        if (a.length == b.length) {
            double dist = 0.0D;
            for (int i = 0; i < a.length; i++) {
                if (Character.isLetter(a[i]) && Character.isLetter(b[i])) {
                    // ... forms have numeric values from 10 through 35
                    int numA = Character.getNumericValue(a[i]) - 10;
                    int numB = Character.getNumericValue(b[i]) - 10;
                    int maxIdx = distanceMatrix[0].length;
                    if (numA > (maxIdx - 1) || numA < 0 || numB > (maxIdx - 1) || numB < 0) {
                        throw new SAXException(
                                "The character index greater than " + maxIdx + " or less than 0!");
                    }
                    double localDist = distanceMatrix[numA][numB];
                    dist = dist + localDist * localDist;
                } else {
                    throw new SAXException("Non-literal character found!");
                }
            }
            return Math.sqrt((double) n / (double) w) * Math.sqrt(dist);
        } else {
            throw new SAXException("Data arrays lengths are not equal!");
        }
    }

    /**
     * Check for trivial mindist case.
     *
     * @param a first string.
     * @param b second string.
     * @return true if mindist between strings is zero.
     */
    public static boolean checkMinDistIsZero(char[] a, char[] b) {
        for (int i = 0; i < a.length; i++) {
            if (characterDistance(a[i], b[i]) > 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * Get mapping of a number to char.
     *
     * @param value the value to map.
     * @param cuts  the array of intervals.
     * @return character corresponding to numeric value.
     */
    public static char numberToCharacter(double value, double[] cuts) {
        int idx = 0;
        if (value >= 0) {
            idx = cuts.length;
            while ((idx > 0) && (cuts[idx - 1] > value)) {
                idx--;
            }
        } else {
            while ((idx < cuts.length) && (cuts[idx] <= value)) {
                idx++;
            }
        }
        return NormalAlphabet.ALPHABET[idx];
    }

    /**
     * Converts index into char.
     *
     * @param idx The index value.
     * @return The char by index.
     */
    public static char numberToCharacter(int idx) {
        return NormalAlphabet.ALPHABET[idx];
    }

    /**
     * Get mapping of number to cut index.
     *
     * @param value the value to map.
     * @param cuts  the array of intervals.
     * @return character corresponding to numeric value.
     */
    public static int numberToIndex(double value, double[] cuts) {
        int count = 0;
        while ((count < cuts.length) && (cuts[count] <= value)) {
            count++;
        }
        return count;
    }

    /**
     * Prettyfies the series for screen output.
     *
     * @param series the data.
     * @param df     the number format to use.
     * @return The series formatted for screen output.
     */
    public String seriesToString(double[] series, NumberFormat df) {
        StringBuffer sb = new StringBuffer();
        sb.append('[');
        for (double d : series) {
            sb.append(df.format(d)).append(',');
        }
        sb.delete(sb.length() - 2, sb.length() - 1).append("]");
        return sb.toString();
    }

    /**
     * Normalizes data in interval 0-1.
     *
     * @param data the dataset.
     * @return normalized dataset.
     */
    public double[] normalize(double[] data) {
        double[] res = new double[data.length];
        double max = Series.max(data);
        for (int i = 0; i < data.length; i++) {
            res[i] = data[i] / max;
        }
        return res;
    }

    /**
     * generate sax alphabet set
     *
     * @param alphabet_size
     * @return
     */
    public static int[] saxGenerateAlphabet(int alphabet_size) {

        int[] res = new int[alphabet_size];
        for (int i = 0; i < alphabet_size; i++) {
            res[i] = i;
        }
        return res;
    }

    /**
     * compute the breakpoints
     *
     * @param alphabetSize
     * @param meanValue
     * @param stdValue
     * @return
     */
    public static double[] saxComputeBreakpoints(int alphabetSize, double meanValue, double stdValue) {
        double[] res = new double[alphabetSize - 1];
        NormalDistribution normalDistribution = new NormalDistribution(meanValue, stdValue);

        for (int i = 1; i < alphabetSize; ++i) {
            //compute the breakingpoints
            double value = normalDistribution.inverseCumulativeProbability((float) i * (1 / (float) alphabetSize));
            res[i - 1] = value;
        }
        return res;
    }

    /**
     * @param ts
     * @param w        the total number of divisions.
     * @param alphabet is the size of alphabet
     * @return result An array of string index [a,z].
     * @brief Symbolic Aggregate approXimation (SAX). It transforms a numeric time series into a time series of string with
     * the reduced size w. The algorithm was proposed by Zhenwen He et al.) and extends the PAA-based approach inheriting the original
     * algorithm simplicity and low computational complexity while providing satisfactory sensitivity and selectivity in
     * range query processing.
     */
    public static int[] seriesToIndex(double[] ts, int w, int alphabet) {
        //paa 降维
        double[] reducedDataY = new double[(int) w];
        try {
            reducedDataY = paa(ts, (int) w);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sax符号化
        double mean_value = mean(reducedDataY);
        double std_value = Series.standardDeviation(mean_value, reducedDataY);
        int[] aux = new int[(int) w];

        if (std_value > 0) {
            double[] breakingpoints = saxComputeBreakpoints(alphabet, mean_value, std_value);
            int[] alphabets = saxGenerateAlphabet(alphabet);

            // Iterate across elements of reducedDataY
            for (int i = 0; i < (int) w; i++) {
                int j = 0;
                int alpha = alphabets[0];

                while ((j < breakingpoints.length) && (reducedDataY[i] > breakingpoints[j])) {
                    j++;
                }
                alpha = alphabets[j];
                aux[i] = alpha;
            }
        } else {
            for (int i = 0; i < (int) w; ++i)
                aux[i] = 0;
        }
        return aux;
    }


    /**
     * 计算两个时序数据对象之间的SAX距离
     *
     * @param s1       时序数据对象
     * @param s2       时序数据对象
     * @param w        paa的段数
     * @param alphabet
     * @return 返回两个时序数据对象之间的SAX MINDIST
     */
    public static double distance(Series s1, Series s2, int w, int alphabet) {
        NormalAlphabet normalAlphabet = new NormalAlphabet();
        long n = Math.min(s1.length(), s2.length());
        try {
            char[] a = seriesToString(s1.getValues(), w, normalAlphabet.getCuts(alphabet), Double.MIN_NORMAL);
            char[] b = seriesToString(s2.getValues(), w, normalAlphabet.getCuts(alphabet), Double.MIN_NORMAL);
            return saxMinDist(a, b, normalAlphabet.getDistanceMatrix(alphabet), (int) n, w);
        } catch (Exception e) {
            e.printStackTrace();
            return Double.MAX_VALUE;
        }
    }

    /**
     * 计算两个数据集合中每条时序数据对象之间的距离
     *
     * @param s1       m条时序数据的集合
     * @param s2       n条时序数据的集合
     * @param w        paa的段数
     * @param alphabet
     * @return 返回n行m列的2D数组 a
     * 也即，s1中的第0条与s2中的n条时序数据的距离存储在第0列；
     * s1中的第i条与s2中的第j条时序数据之间的距离为 a.get(j,i);
     * 获取s1中第i条与s2中所有时序数据对象的距离为一个n元列向量，也即 a.col(i)
     */
    public static Array distances(MultiSeries s1, MultiSeries s2, int w, int alphabet) {
        try {
            int m = (int) s1.count();
            int n = (int) s2.count();
            double[] dist = new double[m * n];
            int k = 0;
            for (int i = 0; i < m; ++i) {
                Series s11 = s1.getSeries(i);
                for (int j = 0; j < n; ++j) {
                    Series s22 = s2.getSeries(j);
                    dist[k] = distance(s11, s22, w, alphabet);
                    ++k;
                }
            }
            return Array.of(n, m, dist);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
