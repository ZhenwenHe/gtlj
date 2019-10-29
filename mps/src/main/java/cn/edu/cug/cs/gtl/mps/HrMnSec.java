package cn.edu.cug.cs.gtl.mps;

import cn.edu.cug.cs.gtl.io.Storable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class HrMnSec implements Storable {
    int hour;
    int minute;
    int second;

    public HrMnSec() {
    }

    public HrMnSec(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HrMnSec)) return false;

        HrMnSec hrMnSec = (HrMnSec) o;

        if (getHour() != hrMnSec.getHour()) return false;
        if (getMinute() != hrMnSec.getMinute()) return false;
        return getSecond() == hrMnSec.getSecond();
    }

    @Override
    public int hashCode() {
        int result = getHour();
        result = 31 * result + getMinute();
        result = 31 * result + getSecond();
        return result;
    }

    @Override
    public Object clone() {
        return new HrMnSec(this.hour, this.minute, this.second);
    }

    @Override
    public boolean load(DataInput dataInput) throws IOException {
        this.hour = dataInput.readInt();
        this.minute = dataInput.readInt();
        this.second = dataInput.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.hour);
        out.writeInt(this.minute);
        out.writeInt(this.second);
        return true;
    }
}
