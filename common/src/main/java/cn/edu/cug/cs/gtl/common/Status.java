package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.*;

public class Status implements Serializable {
    int status;

    public Status() {
        this.status = 0;
    }

    protected Status(int s) {
        this.status = s;
    }

    /**
     * 是否可见
     *
     * @return
     */
    public boolean isVisible() {
        return true;
    }

    /**
     * 设置是否可见
     *
     * @param flag
     */
    public void setVisible(boolean flag) {

    }

    /**
     * 是否删除
     *
     * @return
     */
    public boolean isDeleted() {
        return false;
    }

    /**
     * 设置删除标志
     *
     * @param flag
     */
    public void setDeleted(boolean flag) {
        setSelected(false);
        setVisible(false);
    }

    /**
     * 是否选择
     *
     * @return
     */
    public boolean isSelected() {
        return false;
    }

    /**
     * 设置选中标志
     *
     * @param flag
     */
    public void setSelected(boolean flag) {

    }

    @Override
    public Status clone() {
        return new Status(this.status);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.status = in.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.status);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 4;
    }
}
