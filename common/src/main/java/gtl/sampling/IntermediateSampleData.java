package gtl.sampling;

/**
 * The data structure which is transferred between partitions and the coordinator for distributed
 * random sampling.
 *
 * @param <T> The type of sample data.
 */

public class IntermediateSampleData<T> implements Comparable<IntermediateSampleData<T>> {
    private double weight;
    private T element;

    public IntermediateSampleData(double weight, T element) {
        this.weight = weight;
        this.element = element;
    }

    public double getWeight() {
        return weight;
    }

    public T getElement() {
        return element;
    }

    @Override
    public int compareTo(IntermediateSampleData<T> other) {
        return this.weight >= other.getWeight() ? 1 : -1;
    }
}