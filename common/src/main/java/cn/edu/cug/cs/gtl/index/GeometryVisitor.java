package cn.edu.cug.cs.gtl.index;

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.util.ObjectUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

public class GeometryVisitor implements Visitor {
    final ArrayList<Geometry> geometries;

    public GeometryVisitor() {
        geometries = new ArrayList<>();
    }

    public ArrayList<Geometry> getGeometries() {
        return geometries;
    }

    @Override
    public void visitNode(Node in) {

    }

    public void visitGeometry(Geometry g) {
        if (g != null) {
            geometries.add(g);
        }
    }

    @Override
    public void visitData(Entry in) {
        try {
            if (in == null) return;
            Geometry g = (Geometry) ObjectUtils.loadFromByteArray(in.getData());
            visitGeometry(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitData(Entry[] ev) {

    }

}
