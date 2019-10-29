package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.geom.Triangle;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;

public class TriangleEncoder implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    TriangleShape rootTriangle;

    public TriangleEncoder(TriangleShape triangle) {
        rootTriangle = (TriangleShape) triangle.clone();
    }

    public void reset(TriangleShape triangle) {
        rootTriangle = (TriangleShape) triangle.clone();
    }

    /**
     * parse the triangle code,0 represents the left sub-triangle,
     * 1 represents the right sub-triangle,
     * the first char of the identifier is always 1 except identifier is empty
     *
     * @param identifier code of the triangle
     * @return the corresponding triangle
     */
    public TriangleShape parse(String identifier) {
        TriangleShape r = null;
        if (identifier.isEmpty()) return r;
        int s = identifier.length();
        if (s == 1) return rootTriangle;
        TriangleShape p = rootTriangle;
        for (int i = 1; i < s; ++i) {
            if (identifier.charAt(i) == '0')
                r = p.leftTriangle();
            else
                r = p.rightTriangle();
            p = r;
        }
        return r;
    }

    /**
     * whether the rootTriangle contains sub
     *
     * @param sub triangle
     * @return true-contains, false - does not contain
     */
    public boolean contains(Triangle sub) {
        return Geom2DSuits.contains(rootTriangle, sub);
    }

    /**
     * calculate the minimum triangle which contains the sub-triangle,
     * and return its code string
     *
     * @param subtriangle
     * @return calculate the minimum triangle which contains the sub-triangle,
     * and return its code string, if the string returned is empty,
     * it means that rootTriangle does not contain subtriangle
     */
    public String encode(TriangleShape subtriangle) {
        TriangleShape p = (TriangleShape) rootTriangle;
        StringBuilder sb = new StringBuilder();
        if (contains(subtriangle)) {
            sb.append('1');
            TriangleShape left = p.leftTriangle();
            TriangleShape right = p.rightTriangle();
            if (!Geom2DSuits.contains(left, subtriangle)) {
                if (!Geom2DSuits.contains(right, subtriangle))
                    return sb.toString();
                else
                    p = (TriangleShape) right;
            } else {
                p = (TriangleShape) left;
            }
        } else
            return sb.toString();

        do {
            Triangle left = p.leftTriangle();
            if (Geom2DSuits.contains(left, subtriangle)) {
                sb.append('0');
                p = (TriangleShape) left;
            } else {
                Triangle right = p.rightTriangle();
                if (Geom2DSuits.contains(right, subtriangle)) {
                    sb.append('1');
                    p = (TriangleShape) right;
                } else
                    return sb.toString();
            }
        } while (p != null);
        return sb.toString();
    }
}
