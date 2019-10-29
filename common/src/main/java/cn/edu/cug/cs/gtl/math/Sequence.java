package cn.edu.cug.cs.gtl.math;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;

/**
 * af::seq
 */
public class Sequence implements Storable {
    private static final long serialVersionUID = -8096118532586286120L;
    /// Start position of the sequence
    double dBegin;
    /// End position of the sequence (inclusive)
    double dEnd;
    /// Step size between sequence values
    double dStep;

    public double begin() {
        return this.dBegin;
    }

    public void begin(double dBegin) {
        this.dBegin = dBegin;
    }

    public double end() {
        return dEnd;
    }

    public void end(double dEnd) {
        this.dEnd = dEnd;
    }

    public double step() {
        return dStep;
    }

    public void step(double dStep) {
        this.dStep = dStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sequence sequence = (Sequence) o;
        return Double.compare(sequence.dBegin, dBegin) == 0 &&
                Double.compare(sequence.dEnd, dEnd) == 0 &&
                Double.compare(sequence.dStep, dStep) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dBegin, dEnd, dStep);
    }

    public Sequence(double dBegin, double end, double dStep) {
        this.dBegin = dBegin;
        this.dEnd = end;
        this.dStep = dStep;
    }

    public Sequence(double dBegin, double end) {
        this(dBegin, end, 1);
    }

    public int size() {
        return (int) ((dEnd - dBegin) / dStep);
    }

    @Override
    public Object clone() {
        return new Sequence(this.dBegin, this.dEnd, this.dStep);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.dBegin = in.readDouble();
        this.dEnd = in.readDouble();
        this.dStep = in.readDouble();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(this.dBegin);
        out.writeDouble(this.dEnd);
        out.writeDouble(this.dStep);
        return true;
    }

    public static Sequence plus(Sequence s, double x) {
        return new Sequence(s.dBegin + x, s.dEnd + x, s.dStep);
    }

    public static Sequence minus(Sequence s, double x) {
        return new Sequence(s.dBegin - x, s.dEnd - x, s.dStep);
    }

    public static Sequence multiply(Sequence s, double x) {
        return new Sequence(s.dBegin * x, s.dEnd * x, s.dStep * x);
    }

    public static Sequence divide(Sequence s, double x) {
        return new Sequence(s.dBegin / x, s.dEnd / x, s.dStep / x);
    }


}
