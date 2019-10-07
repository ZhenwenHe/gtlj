package gtl.index.shape;

import gtl.geom.*;

/**
 * Created by ZhenwenHe on 2017/3/27.
 */
public class IsoscelesRightTriangleShape extends IsoscelesRightTriangle implements Shape {

    public IsoscelesRightTriangleShape(Vector v0, Vector v1, Vector v2) {
        super(v0, v1, v2);
    }

    public IsoscelesRightTriangleShape() {
        super();
    }

    public IsoscelesRightTriangleShape(Triangle t) {
        this(t.getVertices());
    }

    public IsoscelesRightTriangleShape(Vector[] vertices) {
        super(vertices);
    }

    @Override
    public boolean intersectsShape(Shape in) {
        return false;
    }

    @Override
    public boolean containsShape(Shape in) {
        return false;
    }

    @Override
    public boolean touchesShape(Shape in) {
        return false;
    }

    @Override
    public Envelope getMBR() {
        return null;
    }

    @Override
    public double getMinimumDistance(Shape in) {
        return 0;
    }

    public boolean contains(Interval i) {
        return this.contains(new Vector2D(i.getLowerBound(), i.getUpperBound()));
    }

    @Override
    public Object clone() {
        return new IsoscelesRightTriangleShape(getVertices());
    }
}
