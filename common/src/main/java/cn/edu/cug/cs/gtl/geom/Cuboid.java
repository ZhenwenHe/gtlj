package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class Cuboid extends Geometry implements Serializable {
    private Vector[] vertices;
    private double[] properties;

    public Cuboid() {
        super();
    }

    public Cuboid(Vector[] vertices) {
        this.vertices = vertices;
    }

    public Cuboid(Vector v1, Vector v2) {
        this.vertices = generateVertices(v1, v2);
    }

    private Vector[] generateVertices(Vector v1, Vector v2) {
        Vector[] vertices = new Vector[8];
        vertices[0] = Vector.createVector3D(v1.getX(), v1.getY(), v1.getZ());
        vertices[1] = Vector.createVector3D(v2.getX(), v1.getY(), v1.getZ());
        vertices[2] = Vector.createVector3D(v1.getX(), v2.getY(), v1.getZ());
        vertices[3] = Vector.createVector3D(v2.getX(), v2.getY(), v1.getZ());
        vertices[4] = Vector.createVector3D(v1.getX(), v1.getY(), v2.getZ());
        vertices[5] = Vector.createVector3D(v2.getX(), v1.getY(), v2.getZ());
        vertices[6] = Vector.createVector3D(v1.getX(), v2.getY(), v2.getZ());
        vertices[7] = Vector.createVector3D(v2.getX(), v2.getY(), v2.getZ());
        return vertices;
    }

    public Vector[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector[] vertices) {
        this.vertices = vertices;
    }

    public Vector getVertex(int index) {
        return vertices[index];
    }

    @Override
    public Geometry clone() {
        Cuboid c = new Cuboid();
        c.copyFrom(this);
        return c;
    }

    @Override
    public boolean isEmpty() {
        return this.vertices == null || this.vertices.length == 0;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        Vector v1 = Vector.createVector3D(in.readDouble(), in.readDouble(), in.readDouble());
        Vector v2 = Vector.createVector3D(in.readDouble(), in.readDouble(), in.readDouble());
        this.vertices = generateVertices(v1, v2);
        int propertiesNum = in.readInt();
        properties = new double[in.readInt()];
        for (int i = 0; i < propertiesNum; i++) {
            properties[i] = in.readDouble();
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeDouble(vertices[0].getX());
        out.writeDouble(vertices[0].getY());
        out.writeDouble(vertices[0].getZ());
        out.writeDouble(vertices[7].getX());
        out.writeDouble(vertices[7].getY());
        out.writeDouble(vertices[7].getZ());
        out.writeInt(properties.length);
        for (int i = 0; i < properties.length; i++) {
            out.writeDouble(properties[i]);
        }
        return true;
    }

    @Override
    public void copyFrom(Object i) {
        if (i instanceof Cuboid) {
            super.copyFrom(i);
            this.vertices = Arrays.copyOf(((Cuboid) i).vertices, vertices.length);
            this.properties = Arrays.copyOf(((Cuboid) i).properties, properties.length);
        }
    }

    @Override
    public long getByteArraySize() {
        return 0;
    }
}
