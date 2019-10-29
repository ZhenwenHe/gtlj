package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class TimelineImpl implements Timeline {
    private static final long serialVersionUID = 1L;

    Identifier identifier;
    List<ComplexInterval> labeledIntervals;

    private static final Pattern COMMA_SPLITTER = Pattern.compile(",");

    public TimelineImpl(Identifier identifier, List<ComplexInterval> labeledIntervals) {
        this.identifier = identifier;
        this.labeledIntervals = new ArrayList<ComplexInterval>();
        this.labeledIntervals.addAll(labeledIntervals);
        long i = 0;
        for (ComplexInterval li : this.labeledIntervals) {
            li.setParentID(identifier.longValue());
            li.setOrder(i);
            i++;
        }
    }

    public TimelineImpl() {
        this.identifier = Identifier.create(0);// Identifier.create(0);
        this.labeledIntervals = new ArrayList<ComplexInterval>();
    }

    public TimelineImpl(Identifier identifier, List<Interval> li, List<String> ls) {
        this.identifier = identifier;
        this.labeledIntervals = new ArrayList<ComplexInterval>();
        int c = li.size() > ls.size() ? ls.size() : li.size();
        for (int i = 0; i < c; ++i)
            this.labeledIntervals.add(ComplexInterval.create(li.get(i), ls.get(i), identifier.longValue(), (long) i));
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public List<Interval> getIntervals() {
        List<Interval> li = new ArrayList<Interval>();
        li.addAll(this.labeledIntervals);
        return li;
    }

    @Override
    public List<String> getLabels() {
        List<String> ls = new ArrayList<String>();
        for (ComplexInterval li : labeledIntervals)
            ls.add(li.getName());
        return ls;
    }

    @Override
    public List<ComplexInterval> getLabeledIntervals() {
        return labeledIntervals;
    }

    @Override
    public ComplexInterval[] getLabelIntervalArray() {
        ComplexInterval[] la = new ComplexInterval[labeledIntervals.size()];
        for (int i = 0; i < la.length; ++i)
            la[i] = ComplexInterval.create();
        return labeledIntervals.toArray(la);
    }

    @Override
    public void addLabelInterval(ComplexInterval lb) {
        lb.setParentID(this.identifier.longValue());
        lb.setOrder(labeledIntervals.size());
        labeledIntervals.add(lb);
    }

    @Override
    public Object clone() {
        TimelineImpl ti = new TimelineImpl();
        ti.identifier = (Identifier) this.identifier.clone();
        for (ComplexInterval li : labeledIntervals)
            ti.labeledIntervals.add((ComplexIntervalImpl) li.clone());
        return ti;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Timeline) {
            Timeline ti = (Timeline) (i);
            this.identifier = ti.getIdentifier();
            List<ComplexInterval> olabeledIntervals = ti.getLabeledIntervals();
            this.labeledIntervals.clear();
            for (ComplexInterval li : olabeledIntervals)
                this.labeledIntervals.add((ComplexIntervalImpl) li.clone());
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        try {
            this.identifier.load(in);
            int c = in.readInt();
            this.labeledIntervals.clear();
            for (int i = 0; i < c; ++c) {
                ComplexIntervalImpl li = new ComplexIntervalImpl();
                li.load(in);
                this.labeledIntervals.add(li);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        try {
            this.identifier.store(out);
            int c = this.labeledIntervals.size();
            out.writeInt(c);
            for (ComplexInterval i : this.labeledIntervals) {
                i.store(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        for (ComplexInterval li : labeledIntervals)
            len += li.getByteArraySize();
        len += identifier.getByteArraySize();
        return len;
    }

    //转化成字符串
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Long.valueOf(getIdentifier().longValue()).toString());
        sb.append(',');
        for (ComplexInterval li : labeledIntervals)
            sb.append(li.getName())
                    .append(',')
                    .append(Integer.valueOf(li.getType().ordinal()).toString())
                    .append(',')
                    .append(Double.valueOf(li.getLowerBound()).toString())
                    .append(',')
                    .append(Double.valueOf(li.getUpperBound()).toString())
                    .append(',')
                    .append(Long.valueOf(li.getParentID().longValue()).toString())
                    .append(',')
                    .append(Long.valueOf(li.getOrder().longValue()).toString())
                    .append(',');
        sb.deleteCharAt(sb.length() - 1);//删除最后多余的逗号
        return sb.toString();
    }

    //解析字符串，并填充Timeline
    public Timeline parse(String s) {
        String[] ss = COMMA_SPLITTER.split(s);
        if (ss.length < 5) return this;
        int i = 0;
        identifier.reset(Long.valueOf(ss[i]).longValue());
        labeledIntervals.clear();
        ++i;
        while (i < ss.length) {
            String label = ss[i];
            ++i;
            int type = Integer.valueOf(ss[i]).intValue();
            ++i;
            double low = Double.valueOf(ss[i]).doubleValue();
            ++i;
            double high = Double.valueOf(ss[i]).doubleValue();
            ++i;
            long pid = Long.valueOf(ss[i]).longValue();
            ++i;
            long order = Long.valueOf(ss[i]).longValue();
            ++i;
            labeledIntervals.add(GeomSuits.createLabeledInterval(IntervalType.values()[type], low, high, label, pid, order));
        }
        return (Timeline) this;
    }
}
