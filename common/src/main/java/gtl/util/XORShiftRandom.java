package gtl.util;

import java.util.Random;

/**
 * Implement a random number generator based on the XORShift algorithm discovered by George Marsaglia.
 * This RNG is observed 4.5 times faster than {@link java.util.Random} in benchmark, with the cost
 * that abandon thread-safety. So it's recommended to create a new {@link XORShiftRandom} for each
 * thread.
 *
 * @see <a href="http://www.jstatsoft.org/v08/i14/paper">XORShift Algorithm Paper</a>
 */

public class XORShiftRandom extends Random {

    private static final long serialVersionUID = -825722456120842841L;
    private long seed;

    public XORShiftRandom() {
        this(System.nanoTime());
    }

    public XORShiftRandom(long input) {
        super(input);
        this.seed = MathUtils.murmurHash((int) input) ^ MathUtils.murmurHash((int) (input >>> 32));
    }

    /**
     * All other methods like nextInt()/nextDouble()... depends on this, so we just need to overwrite
     * this.
     *
     * @param bits Random bits
     * @return The next pseudorandom value from this random number
     * generator's sequence
     */
    @Override
    public int next(int bits) {
        long nextSeed = seed ^ (seed << 21);
        nextSeed ^= (nextSeed >>> 35);
        nextSeed ^= (nextSeed << 4);
        seed = nextSeed;
        return (int) (nextSeed & ((1L << bits) - 1));
    }
}