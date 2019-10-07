package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by ZhenwenHe on 2017/3/12.
 */
class IntervalsImpl implements Intervals {
    private static final long serialVersionUID = 1L;
    Identifier identifier;
    Interval[] intervals;

    public IntervalsImpl(Identifier identifier, Interval[] intervals) {
        this.identifier = Identifier.create(identifier.longValue());
        this.intervals = new Interval[intervals.length];
        System.arraycopy(intervals, 0, this.intervals, 0, intervals.length);
    }

    public IntervalsImpl(Identifier identifier) {
        this.identifier = Identifier.create(identifier.longValue());
        this.intervals = null;
    }

    public IntervalsImpl() {
        this.identifier = Identifier.create(-1L);
        this.intervals = null;
    }

    @Override
    public Object clone() {
        Intervals its = new IntervalsImpl();
        its.copyFrom(this);
        return its;
    }

    @Override
    public void copyFrom(Object i) {
        if (i == null) return;
        if (i instanceof Intervals) {
            Intervals its = (Intervals) i;
            this.identifier.reset(its.getIdentifier().longValue());
            int s = its.size();
            this.intervals = new Interval[s];
            for (int k = 0; k < s; ++k) {
                this.intervals[k] = (Interval) its.get(k).clone();
            }
        }
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.identifier.load(in);
        int s = in.readInt();
        if (s != this.intervals.length) {
            this.intervals = new Interval[s];
        }
        for (int k = 0; k < s; ++k) {
            Interval i = new IntervalImpl();
            i.load(in);
            this.intervals[k] = i;
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.identifier.store(out);
        out.writeInt(this.size());
        for (Interval i : this.intervals)
            i.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = this.identifier.getByteArraySize();
        for (Interval v : this.intervals) {
            len += v.getByteArraySize();
        }
        return len;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public void setIdentifier(Identifier i) {
        if (i != null)
            this.identifier.reset(i.longValue());
    }

    @Override
    public int size() {
        return this.intervals == null ? 0 : this.intervals.length;
    }

    @Override
    public boolean isEmpty() {
        return this.intervals == null;
    }

    @Override
    public Interval get(int i) {
        return this.intervals[i];
    }

    @Override
    public int find(Interval i) {
        if (this.intervals == null || i == null)
            return -1;
        for (int j = 0; j < this.intervals.length; ++j)
            if (this.intervals[j].equals((i)))
                return j;
        return -1;
    }

    @Override
    public boolean contains(Object o) {
        if (this.intervals == null)
            return false;
        if (o instanceof Interval) {
            return this.find((Interval) o) != -1;
        }
        return false;
    }

    @Override
    public Iterator<Interval> iterator() {
        return new Itr();
    }

    @Override
    public Object[] toArray() {
        int c = this.size();
        if (c > 0) {
            Object[] returnArray = new Object[c];
            for (int i = 0; i < c; i++)
                returnArray[i] = this.intervals[i];
            return returnArray;
        } else
            return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int c = this.size();
        if (c > 0) {
            for (int i = 0; i < c; i++)
                a[i] = (T) this.intervals[i];
            return a;
        } else
            return null;
    }

    @Override
    public boolean add(Interval interval) {
        if (interval == null) return false;
        int c = this.size() + 1;
        this.intervals = new Interval[c];
        System.arraycopy(intervals, 0, this.intervals, 0, c - 1);
        this.intervals[c - 1] = interval;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (this.intervals == null || o == null)
            return false;
        if (o instanceof Interval) {
            int i = this.find((Interval) o);
            int c = this.intervals.length;
            if (c == 1) {
                this.intervals[0] = null;
                return true;
            }
            Interval[] its = new Interval[c - 1];
            if (i == 0) {
                System.arraycopy(this.intervals, 1, its, 0, c - 1);
            } else if (i == c - 1) {
                System.arraycopy(this.intervals, 0, its, 0, c - 1);
            } else {
                System.arraycopy(this.intervals, 0, its, 0, i);
                System.arraycopy(this.intervals, i + 1, its, i, c - 1 - i);
            }
            this.intervals = its;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            if (this.contains(it.next()) == false)
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Interval> c) {
        int s = this.size() + c.size();
        Interval[] iv = new Interval[s];
        Iterator<?> it = c.iterator();
        Interval i = null;
        while (it.hasNext()) {
            i = (Interval) it.next();
            if (this.contains(i) == false) {
                this.add(i);
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Iterator<?> it = c.iterator();
        while (it.hasNext()) {
            int i = this.find((Interval) it.next());
            if (i != -1) this.intervals[i] = null;
        }
        LinkedList<Interval> lls = new LinkedList<>();
        for (int i = 0; i < this.intervals.length; ++i)
            if (this.intervals[i] != null)
                lls.add(this.intervals[i]);
        this.intervals = new Interval[lls.size()];
        lls.toArray(this.intervals);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        int s = c.size();
        if (s != this.size()) {
            this.intervals = new Interval[s];
        }
        c.toArray(this.intervals);
        return true;
    }

    @Override
    public void clear() {
        this.intervals = null;
    }

    /**
     * An optimized version of AbstractList.Itr
     */
    private class Itr implements Iterator<Interval> {
        int cursor = 0;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        public boolean hasNext() {
            return cursor != IntervalsImpl.this.size();
        }

        @SuppressWarnings("unchecked")
        public Interval next() {
            int i = cursor;
            if (i >= IntervalsImpl.this.size())
                throw new NoSuchElementException();
            Interval[] elementData = IntervalsImpl.this.intervals;
            if (i >= elementData.length)
                throw new ConcurrentModificationException();
            cursor = i + 1;
            return elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();
            try {
                IntervalsImpl.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void forEachRemaining(Consumer<? super Interval> consumer) {
            Objects.requireNonNull(consumer);
            final int size = IntervalsImpl.this.size();
            int i = cursor;
            if (i >= size) {
                return;
            }
            final Interval[] elementData = IntervalsImpl.this.intervals;
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            while (i != size) {
                consumer.accept(elementData[i++]);
            }
            // update once at end of iteration to reduce heap write traffic
            cursor = i;
            lastRet = i - 1;
        }
    }
}
