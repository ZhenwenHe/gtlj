package cn.edu.cug.gtl.decompose;

import java.util.ArrayList;

public class LeafNode {

    private long identifer;

    private ArrayList<Vertex> entryArray;

    LeafNode() {
        identifer = 0;
        entryArray = new ArrayList<>(0);
    }

    LeafNode(long identifer) {
        this.identifer = identifer;
        entryArray = new ArrayList<>(0);
    }

    void push(Vertex v) {
        entryArray.add(v);
    }

    int entrySize() {
        return entryArray.size();
    }

    public void setIdentifer(long identifer) {
        this.identifer = identifer;
    }

    public long getIdentifer() {
        return identifer;
    }

    public ArrayList<Vertex> getEntryArray() {
        return entryArray;
    }

    public void setEntryArray(ArrayList<Vertex> entryArray) {
        this.entryArray = entryArray;
    }

}
