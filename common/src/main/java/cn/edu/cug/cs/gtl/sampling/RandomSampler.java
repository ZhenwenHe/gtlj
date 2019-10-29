package cn.edu.cug.cs.gtl.sampling;

import cn.edu.cug.cs.gtl.io.File;

import java.util.Iterator;
import java.util.List;

/**
 * A data sample is a set of data selected from a statistical population by a defined procedure.
 * RandomSampler helps to create data sample randomly.
 *
 * @param <T> The type of sampler data.
 */

public abstract class RandomSampler<T> {

    protected static final double EPSILON = 1e-5;

    protected final Iterator<T> emptyIterable = new SampledIterator<T>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    };


    /**
     * Randomly sample the elements from input in sequence, and return the result iterator.
     *
     * @param input Source data
     * @return The sample result.
     */
    public abstract Iterator<T> sample(Iterator<T> input);


    /**
     * Returns the number of samples to take to partition the PCollection into specified number of partitions.
     * <p>
     * Number of partitions cannot exceed half the number of records in the PCollection.
     * <p>
     * Returns total number of records if it is < 1000. Otherwise, returns 1% of the total number
     * of records or twice the number of partitions whichever is larger. Never returns a
     * number > Integer.MAX_VALUE.
     * <p>
     * If desired number of samples is not -1, returns that number.
     *
     * @param numPartitions        the num partitions
     * @param totalNumberOfRecords the total number of records
     * @param givenSampleNumbers   the given sample numbers
     * @return the sample numbers
     * @throws IllegalArgumentException if requested number of samples exceeds total number of records
     *                                  or if requested number of partitions exceeds half of total number of records
     */
    public static int getSampleNumbers(int numPartitions, long totalNumberOfRecords, int givenSampleNumbers) {
        if (givenSampleNumbers > 0) {
            if (givenSampleNumbers > totalNumberOfRecords) {
                throw new IllegalArgumentException("[GeoSpark] Number of samples " + givenSampleNumbers + " cannot be larger than total records num " + totalNumberOfRecords);
            }
            return givenSampleNumbers;
        }

        // Make sure that number of records >= 2 * number of partitions
        if (totalNumberOfRecords < 2 * numPartitions) {
            throw new IllegalArgumentException("[GeoSpark] Number of partitions " + numPartitions + " cannot be larger than half of total records num " + totalNumberOfRecords);
        }

        if (totalNumberOfRecords < 1000) {
            return (int) totalNumberOfRecords;
        }

        final int minSampleCnt = numPartitions * 2;
        return (int) Math.max(minSampleCnt, Math.min(totalNumberOfRecords / 100, Integer.MAX_VALUE));
    }

    /**
     * Returns a sampling rate that guarantees a sample of size greater than or equal to
     * sampleSizeLowerBound 99.99% of the time.
     * <p>
     * How the sampling rate is determined:
     * <p>
     * Let p = num / total, where num is the sample size and total is the total number of
     * datapoints in the RDD. We're trying to compute q {@literal >} p such that
     * - when sampling with replacement, we're drawing each datapoint with prob_i ~ Pois(q),
     * where we want to guarantee
     * Pr[s {@literal <} num] {@literal <} 0.0001 for s = sum(prob_i for i from 0 to total),
     * i.e. the failure rate of not having a sufficiently large sample {@literal <} 0.0001.
     * Setting q = p + 5 * sqrt(p/total) is sufficient to guarantee 0.9999 success rate for
     * num {@literal >} 12, but we need a slightly larger q (9 empirically determined).
     * - when sampling without replacement, we're drawing each datapoint with prob_i
     * ~ Binomial(total, fraction) and our choice of q guarantees 1-delta, or 0.9999 success
     * rate, where success rate is defined the same as in sampling with replacement.
     * <p>
     * The smallest sampling rate supported is 1e-10 (in order to avoid running into the limit of the
     * RNG's resolution).
     *
     * @param sampleSizeLowerBound sample size
     * @param total                size of RDD
     * @param withReplacement      whether sampling with replacement
     * @return a sampling rate that guarantees sufficient sample size with 99.99% success rate
     */
    public static double computeFractionForSampleSize(int sampleSizeLowerBound,
                                                      long total,
                                                      boolean withReplacement) {
        if (withReplacement) {
            return PoissonBounds.getUpperBound(sampleSizeLowerBound) / total;
        } else {
            double fraction = sampleSizeLowerBound / total;
            return BinomialBounds.getUpperBound(1e-4, total, fraction);
        }
    }

    /**
     * Utility fn that help us determine bounds on adjusted sampling rate to guarantee exact
     * sample sizes with high confidence when sampling with replacement.
     */
    public static class PoissonBounds {

        /**
         * Returns a lambda such that Pr[X {@literal >} s] is very small, where X ~ Pois(lambda).
         */
        public static double getLowerBound(double s) {
            return Math.max(s - numStd(s) * Math.sqrt(s), 1e-15);
        }

        /**
         * Returns a lambda such that Pr[X {@literal <} s] is very small, where X ~ Pois(lambda).
         *
         * @param s sample size
         */
        public static double getUpperBound(double s) {
            return Math.max(s + numStd(s) * Math.sqrt(s), 1e-10);
        }

        private static double numStd(double s) {
            // TODO: Make it tighter.
            if (s < 6.0) {
                return 12.0;
            } else if (s < 16.0) {
                return 9.0;
            } else {
                return 6.0;
            }
        }
    }


    /**
     * Utility fn that help us determine bounds on adjusted sampling rate to guarantee exact
     * sample size with high confidence when sampling without replacement.
     */
    public static class BinomialBounds {

        static final double minSamplingRate = 1e-10;

        /**
         * Returns a threshold `p` such that if we conduct n Bernoulli trials with success rate = `p`,
         * it is very unlikely to have more than `fraction * n` successes.
         */
        public static double getLowerBound(double delta, long n, double fraction) {
            double gamma = -Math.log(delta) / n * (2.0 / 3.0);
            return fraction + gamma - Math.sqrt(gamma * gamma + 3 * gamma * fraction);
        }

        /**
         * Returns a threshold `p` such that if we conduct n Bernoulli trials with success rate = `p`,
         * it is very unlikely to have less than `fraction * n` successes.
         */
        public static double getUpperBound(double delta, long n, double fraction) {
            double gamma = -Math.log(delta) / n;
            return Math.min(1,
                    Math.max(minSamplingRate, fraction + gamma + Math.sqrt(gamma * gamma + 2 * gamma * fraction)));
        }
    }


    public static List<String> loadSamples(String path) {
        return File.readTextLines(path);
    }

}

