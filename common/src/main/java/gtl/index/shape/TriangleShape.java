package gtl.index.shape;

import gtl.geom.*;

/**
 * Created by ZhenwenHe on 2017/3/27.
 */
public class TriangleShape extends IsoscelesRightTriangle implements Shape {
    private static final long serialVersionUID = 1L;

    public TriangleShape(Vector v0, Vector v1, Vector v2) {
        super(v0, v1, v2);
    }

    public TriangleShape() {
        super();
    }

    public TriangleShape(Triangle t) {
        this(t.getVertices());
    }

    public TriangleShape(Vector[] vertices) {
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
        return new TriangleShape(getVertices());
    }


    public TriangleShape leftTriangle() {
        // (V1+V2)/2.0
        Vector m = vertices[1].add(vertices[2]).divide(2.0);
        return new TriangleShape(m, vertices[0], vertices[1]);
    }

    public TriangleShape rightTriangle() {
        // m=(V1+V2)/2.0
        Vector m = vertices[1].add(vertices[2]).divide(2.0);
        return new TriangleShape(m, vertices[2], vertices[0]);
    }
}
