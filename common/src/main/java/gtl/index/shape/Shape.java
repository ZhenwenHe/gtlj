package gtl.index.shape;


import gtl.geom.Envelope;
import gtl.geom.Vector;
import gtl.io.Serializable;

/**
 * Created by ZhenwenHe on 2016/12/6.
 */
public interface Shape extends Serializable {
    boolean intersectsShape(Shape in);

    boolean containsShape(Shape in);

    boolean touchesShape(Shape in);

    Vector getCenter();

    int getDimension();

    Envelope getMBR();

    double getArea();

    double getMinimumDistance(Shape in);

    Object clone();
}
