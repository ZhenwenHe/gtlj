package cn.edu.cug.cs.gtl.series.common.sax;

public abstract class Alphabet {

    /**
     * get the max size of the alphabet.
     *
     * @return maximum size of the alphabet.
     */
    public abstract Integer getMaxSize();

    /**
     * Get cut intervals corresponding to the alphabet size.
     *
     * @param size The alphabet size.
     * @return cut intervals for the alphabet.
     * @throws Exception if error occurs.
     */
    public abstract double[] getCuts(Integer size) throws Exception;

    /**
     * Get the distance matrix for the alphabet size.
     *
     * @param size The alphabet size.
     * @return The distance matrix.
     * @throws Exception if error occurs.
     */
    public abstract double[][] getDistanceMatrix(Integer size) throws Exception;

}

