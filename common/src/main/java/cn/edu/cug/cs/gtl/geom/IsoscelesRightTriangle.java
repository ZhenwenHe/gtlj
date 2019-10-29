package cn.edu.cug.cs.gtl.geom;

/**
 * Created by ZhenwenHe on 2017/3/13.
 */
public class IsoscelesRightTriangle extends IsoscelesTriangle {
    private static final long serialVersionUID = 1L;

    public IsoscelesRightTriangle(Vector v0, Vector v1, Vector v2) {
        super(v0, v1, v2);
    }

    public IsoscelesRightTriangle() {
    }

    public IsoscelesRightTriangle(Vector[] vertices) {
        super(vertices);
    }

    @Override
    public Object clone() {
        return new IsoscelesRightTriangle(vertices);
    }


}
