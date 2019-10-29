package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

/**
 * Created by ZhenwenHe on 2017/3/13.
 */
public interface Triangle extends Serializable {
    Vector getVertex(int i);

    Vector[] getVertices();

    VectorSequence getVectorSequence();

    Vector[] getClockwiseVertices();

    VectorSequence getClockwiseVectorSequence();

    double getAngle(int i);

    LineSegment getEdge(int s, int e);

    double getHeight(int v);//"height" is measured at right angles  from vertex(v) to its base edge

    double getPerimeter();

    double getArea();

    double getEdgeLength(int s, int e);

    int getDimension();

    Envelope getEnvelope();

    Vector getCenter();//call getCentroid()

    //The intersection of the altitudes is the orthocenter.
    Vector getOrthocenter();//垂心

    //The intersection of the medians is the centroid.
    Vector getCentroid();//重心

    //The circumcenter is the center of a circle
    //passing through the three vertices of the triangle.
    Vector getCircumcenter();//外心

    //The intersection of the angle bisectors is the center of the incircle.
    Vector getIncircleCenter();//内心

    boolean intersects(Envelope e);

    boolean intersects(Triangle e);

    boolean contains(Vector p);

    boolean contains(double x, double y);

    boolean contains(double x, double y, double z);

    boolean contains(LineSegment p);

    boolean contains(Triangle p);

    boolean contains(Envelope p);


    boolean isEquilateralTriangle();

    boolean isIsoscelesTriangle();

    boolean isRightAngledTriangle();

    boolean isIsoscelesRightTriangle();

    boolean isScaleneTriangle();

    Triangle flap();


}
