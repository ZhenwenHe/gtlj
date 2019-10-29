package cn.edu.cug.cs.gtl.index.rtree.impl;

import cn.edu.cug.cs.gtl.index.Statistics;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ZhenwenHe on 2016/12/16.
 */
public class StatisticsImpl implements Statistics {
    long readTimes;
    long writeTimes;
    long splitTimes;
    long hits;
    long misses;
    long nodeNumber;
    long adjustments;
    long queryResults;
    long dataNumber;
    long treeHeight;
    ArrayList<Long> nodeNumberInLevelArray;

    public StatisticsImpl() {
        reset();
    }

    public long getSplitTimes() {
        return splitTimes;
    }

    public void setSplitTimes(long splitTimes) {
        this.splitTimes = splitTimes;
    }

    public void increaseSplitTimes() {
        this.splitTimes++;
    }

    public long getHits() {
        return hits;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public void increaseHits() {
        this.hits++;
    }

    public long getMisses() {
        return misses;
    }

    public void setMisses(long misses) {
        this.misses = misses;
    }

    public void increaseNodeNumber() {
        ++this.nodeNumber;
    }

    public void decreaseNodeNumber() {
        --this.nodeNumber;
    }

    public long getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(long adjustments) {
        this.adjustments = adjustments;
    }

    public void increaseAdjustments() {
        ++this.adjustments;
    }

    public long getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(long queryResults) {
        this.queryResults = queryResults;
    }

    public void increaseQueryResults() {
        this.queryResults++;
    }

    public void increaseDataNumber() {
        ++this.dataNumber;
    }

    public void decreaseDataNumber() {
        --this.dataNumber;
    }

    public long getTreeHeight() {
        return this.treeHeight;
    }

    public void setTreeHeight(long treeHeight) {
        this.treeHeight = treeHeight;
    }

    public void increaseTreeHeight() {
        ++this.treeHeight;
    }

    public ArrayList<Long> getNodeNumberInLevelArray() {
        return nodeNumberInLevelArray;
    }

    public void setNodeNumberInLevelArray(ArrayList<Long> nodeNumberInLevelArray) {
        this.nodeNumberInLevelArray = nodeNumberInLevelArray;
    }

    public long getNodeNumberInLevel(int level) {
        return nodeNumberInLevelArray.get(level);
    }

    public void setNodeNumberInLevel(int level, long numb) {
        nodeNumberInLevelArray.set(level, numb);
    }

    @Override
    public long getReadTimes() {
        return this.readTimes;
    }

    public void setReadTimes(long readTimes) {
        this.readTimes = readTimes;
    }

    public void increaseReadTimes() {
        ++this.readTimes;
    }

    @Override
    public long getWriteTimes() {
        return this.writeTimes;
    }

    public void setWriteTimes(long writeTimes) {
        this.writeTimes = writeTimes;
    }

    public void increaseWriteTimes() {
        ++this.writeTimes;
    }

    @Override
    public Object clone() {
        try {
            StatisticsImpl s = new StatisticsImpl();
            copyTo(s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long getNodeNumber() {
        return this.nodeNumber;
    }

    public void setNodeNumber(long nodeNumber) {
        this.nodeNumber = nodeNumber;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof StatisticsImpl) {
            StatisticsImpl s = (StatisticsImpl) (i);
            this.readTimes = s.readTimes;
            this.writeTimes = s.writeTimes;
            this.splitTimes = s.splitTimes;
            this.hits = s.hits;
            this.misses = s.misses;
            this.nodeNumber = s.nodeNumber;
            this.adjustments = s.adjustments;
            this.queryResults = s.queryResults;
            this.dataNumber = s.dataNumber;
            this.treeHeight = s.treeHeight;
            this.nodeNumberInLevelArray = new ArrayList<Long>(s.nodeNumberInLevelArray);
        }
    }

    @Override
    public long getDataNumber() {
        return this.dataNumber;
    }

    public void setDataNumber(long dataNumber) {
        this.dataNumber = dataNumber;
    }

    @Override
    public boolean load(DataInput dis) throws IOException {
        this.readTimes = dis.readLong();
        this.writeTimes = dis.readLong();
        this.splitTimes = dis.readLong();
        this.hits = dis.readLong();
        this.misses = dis.readLong();
        this.nodeNumber = dis.readLong();
        this.adjustments = dis.readLong();
        this.queryResults = dis.readLong();
        this.dataNumber = dis.readLong();
        this.treeHeight = dis.readLong();
        int s = dis.readInt();
        for (int i = 0; i < s; i++) {
            this.nodeNumberInLevelArray.add(Long.valueOf(dis.readLong()));
        }
        return true;
    }

    @Override
    public boolean store(DataOutput dos) throws IOException {
        dos.writeLong(this.readTimes);
        dos.writeLong(this.writeTimes);
        dos.writeLong(this.splitTimes);
        dos.writeLong(this.hits);
        dos.writeLong(this.misses);
        dos.writeLong(this.nodeNumber);
        dos.writeLong(this.adjustments);
        dos.writeLong(this.queryResults);
        dos.writeLong(this.dataNumber);
        dos.writeLong(this.treeHeight);
        int s = this.nodeNumberInLevelArray.size();
        dos.writeInt(s);
        for (Long i : this.nodeNumberInLevelArray) {
            dos.writeLong(i);
        }
        return true;
    }

    @Override
    public long getByteArraySize() {
        return (10 + this.nodeNumberInLevelArray.size()) * 8 + 4;
    }

    @Override
    public void reset() {
        readTimes = 0;
        writeTimes = 0;
        splitTimes = 0;
        hits = 0;
        misses = 0;
        nodeNumber = 0;
        adjustments = 0;
        queryResults = 0;
        dataNumber = 0;
        treeHeight = 0;
        nodeNumberInLevelArray = new ArrayList<Long>();
    }
}
