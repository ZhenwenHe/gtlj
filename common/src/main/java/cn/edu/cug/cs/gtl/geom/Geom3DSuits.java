package cn.edu.cug.cs.gtl.geom;


import cn.edu.cug.cs.gtl.math.MathSuits;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.math.MathSuits;

/**
 * Created by ZhenwenHe on 2017/3/15.
 */
public class Geom3DSuits extends GeomSuits {


    /**
     * dot product (3D) which  allows vector operations in arguments
     * #define dot(u,v)   ((u).x * (v).x + (u).y * (v).y + (u).z * (v).z)
     * #define norm(v)    sqrt(dot(v,v))  // norm = length of  vector
     * #define d(P,Q)     norm(P-Q)        // distance = norm of difference
     *
     * @param P
     * @param Q
     * @return
     */
    public static double distance(Vector P, Vector Q) {
        Vector v = Q.subtract(P);
        return Math.sqrt(v.dotProduct(v));
        //return MathSuits.distance(a.getCoordinates(),b.getCoordinates());
    }

    /**
     * @param P
     * @param L
     * @return the shortest distance from P to L
     * ref:http://geomalgorithms.com/a02-_lines.html
     */
    public static double perpendicularDistance(Vector P, InfiniteLine L) {
        Vector P0 = L.startPoint;
        Vector P1 = L.endPoint;
        Vector v = P1.subtract(P0);//P1 - P0;
        Vector w = P.subtract(P0);//P - P0;

        double c1 = w.dotProduct(v);//dot(w,v);
        double c2 = v.dotProduct(v);//dot(v,v);
        Scalar b = new Scalar(c1 / c2);
        //double b = c1 / c2;
        //PointShape Pb = L.P0 + b * v;
        Vector Pb = P0.add(b.multiply(v));
        return distance(P, Pb);
    }

    /**
     * @param P
     * @param PL
     * @return return the foot of perpendicular to PL
     */

    public static Vector perpendicularFoot(Vector P, Plane PL) {
        double sn, sd;
        Vector V0 = PL.getVertices()[0];
        Vector N = PL.getNormal();
        //sn = -dot( PL.n, (P - PL.V0));
        sn = -N.dotProduct(P.subtract(V0));
        //sd = dot(PL.n, PL.n);
        sd = N.dotProduct(N);
        //*B = P + sb * PL.n;
        Scalar sb = new Scalar(sn / sd);
        return P.add(sb.multiply(N));
    }

    /**
     * @param P
     * @param S
     * @return the shortest distance from P to S
     * ref:http://geomalgorithms.com/a02-_lines.html
     */
    public static double shortestDistance(Vector P, LineSegment S) {
        Vector P0 = S.startPoint;
        Vector P1 = S.endPoint;

        Vector v = P1.subtract(P0);
        Vector w = P.subtract(P0);

        double c1 = w.dotProduct(v);
        if (c1 <= 0)
            return distance(P, P0);

        double c2 = v.dotProduct(v);
        if (c2 <= c1)
            return distance(P, P1);

        //double b = c1 / c2;
        Scalar b = new Scalar(c1 / c2);
        Vector Pb = P0.add(b.multiply(v));
        return distance(P, Pb);
    }

    /**
     * @param L1
     * @param L2
     * @return the shortest distance between L1 and L2
     * ref:http://geomalgorithms.com/a07-_distance.html
     */
    public static double shortestDistance(InfiniteLine L1, InfiniteLine L2) {
        Vector u = L1.endPoint.subtract(L1.startPoint);//L1.P1 - L1.P0;
        Vector v = L2.endPoint.subtract(L2.startPoint);//L2.P1 - L2.P0;
        Vector w = L1.startPoint.subtract(L2.startPoint);//L1.P0 - L2.P0;
        double a = dotProduct(u, u);         // always >= 0
        double b = dotProduct(u, v);
        double c = dotProduct(v, v);         // always >= 0
        double d = dotProduct(u, w);
        double e = dotProduct(v, w);
        double D = a * c - b * b;        // always >= 0
        double sc, tc;

        // compute the line parameters of the two closest points
        if (D < MathSuits.EPSILON) {          // the lines are almost parallel
            sc = 0.0;
            tc = (b > c ? d / b : e / c);    // use the largest denominator
        } else {
            sc = (b * e - c * d) / D;
            tc = (a * e - b * d) / D;
        }
        Scalar scScalar = new Scalar(sc);
        Scalar tcScalar = new Scalar(tc);
        // get the difference of the two closest points
        //Vector   dP = w + (sc * u) - (tc * v);  // =  L1(sc) - L2(tc)
        Vector dP = w.add(scScalar.multiply(u)).subtract(tcScalar.multiply(v));
        return normalize(dP);   // return the closest distance
    }

    /**
     * @param S1
     * @param S2
     * @return the shortest distance between S1 and S2
     * ref:http://geomalgorithms.com/a07-_distance.html
     */
    public static double shortestDistance(LineSegment S1, LineSegment S2) {
        Vector u = subtract(S1.endPoint, S1.startPoint);//S1.P1 - S1.P0;
        Vector v = subtract(S2.endPoint, S2.startPoint);//S2.P1 - S2.P0;
        Vector w = subtract(S1.startPoint, S2.startPoint);//S1.P0 - S2.P0;
        double a = dotProduct(u, u);         // always >= 0
        double b = dotProduct(u, v);
        double c = dotProduct(v, v);         // always >= 0
        double d = dotProduct(u, w);
        double e = dotProduct(v, w);
        double D = a * c - b * b;        // always >= 0
        double sc, sN, sD = D;       // sc = sN / sD, default sD = D >= 0
        double tc, tN, tD = D;       // tc = tN / tD, default tD = D >= 0

        // compute the line parameters of the two closest points
        if (D < MathSuits.EPSILON) { // the lines are almost parallel
            sN = 0.0;         // force using point P0 on segment S1
            sD = 1.0;         // to prevent possible division by 0.0 later
            tN = e;
            tD = c;
        } else {                 // get the closest points on the infinite lines
            sN = (b * e - c * d);
            tN = (a * e - b * d);
            if (sN < 0.0) {        // sc < 0 => the s=0 edge is visible
                sN = 0.0;
                tN = e;
                tD = c;
            } else if (sN > sD) {  // sc > 1  => the s=1 edge is visible
                sN = sD;
                tN = e + b;
                tD = c;
            }
        }

        if (tN < 0.0) {            // tc < 0 => the t=0 edge is visible
            tN = 0.0;
            // recompute sc for this edge
            if (-d < 0.0)
                sN = 0.0;
            else if (-d > a)
                sN = sD;
            else {
                sN = -d;
                sD = a;
            }
        } else if (tN > tD) {      // tc > 1  => the t=1 edge is visible
            tN = tD;
            // recompute sc for this edge
            if ((-d + b) < 0.0)
                sN = 0;
            else if ((-d + b) > a)
                sN = sD;
            else {
                sN = (-d + b);
                sD = a;
            }
        }
        // finally do the division to get sc and tc
        sc = (Math.abs(sN) < MathSuits.EPSILON ? 0.0 : sN / sD);
        tc = (Math.abs(tN) < MathSuits.EPSILON ? 0.0 : tN / tD);
        Scalar scScalar = new Scalar(sc);
        Scalar tcScalar = new Scalar(tc);
        // get the difference of the two closest points
        //Vector   dP = w + (sc * u) - (tc * v);  // =  S1(sc) - S2(tc)
        Vector dP = w.add(scScalar.multiply(u)).subtract(tcScalar.multiply(v));
        return normalize(dP);   // return the closest distance
    }

    /**
     * dist_Point_to_Plane(): get distance (and perp base) from a point to a plane
     * Input:  P  = a 3D point
     * PL = a  plane with point V0 and normal n
     * Output: B = base point on PL of perpendicular from P
     * Return: the distance from P to the plane PL
     *
     * @param P
     * @param PL
     * @param basePoint base point on PL of perpendicular from P
     * @return the distance from P to the plane PL
     */
    public static double perpendicularDistance(Vector P, Plane PL, Vector basePoint) {
        double sn, sd;
        Vector V0 = PL.getVertices()[0];
        Vector N = PL.getNormal();
        //sn = -dot( PL.n, (P - PL.V0));
        sn = -N.dotProduct(P.subtract(V0));
        //sd = dot(PL.n, PL.n);
        sd = N.dotProduct(N);
        //*B = P + sb * PL.n;
        Scalar sb = new Scalar(sn / sd);
        Vector B = P.add(sb.multiply(N));
        if (basePoint != null) B.copyTo(basePoint);
        return distance(P, B);
    }

    /**
     * 求 C 点在直线 AB 上的垂线距离
     */
    public static double perpendicularDistance(Vector vA, Vector vB, Vector vC) {
        int dims = java.lang.Math.min(java.lang.Math.min(vA.getDimension(), vB.getDimension()), vC.getDimension());
        double[] v1 = new double[dims];
        double[] v2 = new double[dims];
        double[] P = new double[dims];
        double[] A = vA.getCoordinates();
        double[] B = vB.getCoordinates();
        double[] C = vC.getCoordinates();
        for (int i = 0; i < dims; ++i) {
            v1[i] = A[i] - B[i];
            v2[i] = C[i] - B[i];
        }
        double t = MathSuits.dotProduct(A, B);
        if (Math.abs(t) < MathSuits.EPSILON) {
            t = 0;
        } else {
            t = MathSuits.dotProduct(v1, v2) / t;
        }
        for (int i = 0; i < dims; ++i) {
            P[i] = B[i] + v1[i] * t;
        }
        return MathSuits.distance(C, P);
    }

    /**
     * 求 C 点在直线 AB 上的垂足点P，并返回垂直距离，
     *
     * @param vA
     * @param vB
     * @param vC
     * @param vP
     * @param onAB 如果点P在线段AB上，返回真，否则返回假
     * @return
     */
    public static double perpendicularDistance(Vector vA, Vector vB, Vector vC, Vector vP, Variant onAB) {
        int dims = java.lang.Math.min(java.lang.Math.min(vA.getDimension(), vB.getDimension()), vC.getDimension());
        double[] v1 = new double[dims];
        double[] v2 = new double[dims];
        double[] P = new double[dims];
        double[] A = vA.getCoordinates();
        double[] B = vB.getCoordinates();
        double[] C = vC.getCoordinates();
        for (int i = 0; i < dims; ++i) {
            v1[i] = A[i] - B[i];
            v2[i] = C[i] - B[i];
        }
        double t = MathSuits.dotProduct(A, B);
        if (Math.abs(t) < MathSuits.EPSILON) {
            t = 0;
        } else {
            t = MathSuits.dotProduct(v1, v2) / t;
        }
        for (int i = 0; i < dims; ++i) {
            P[i] = B[i] + v1[i] * t;
        }

        if (vP != null) {
            vP.reset(P);
        }
        if (onAB != null) {
            if ((t < 0) || (t > 1)) {
                onAB.reset(false);
            } else {
                onAB.reset(true);
            }
        }
        return MathSuits.distance(C, P);
    }

    /**
     * // return the original vector length
     *
     * @return
     */
    public static double normalize(Vector v) {
        return v.normalize();
    }

    /**
     * @param v
     * @param v2
     * @return
     */
    public static double dotProduct(Vector v, Vector v2) {
        return v.dotProduct(v2);
    }

    /**
     * Computes the dot product of the 3D vectors AB and CD.
     *
     * @param A
     * @param B
     * @param C
     * @param D
     * @return the dot product
     */
    public static double dotProduct(Vector A, Vector B, Vector C, Vector D) {
        Vector v1 = B.subtract(A);
        Vector v2 = D.subtract(C);
        return v1.dotProduct(v2);
    }

    /**
     * // a X  b
     *
     * @param a
     * @param b
     * @return
     */
    public static Vector crossProduct(Vector a, Vector b) {
        return a.crossProduct(b);
    }

    /**
     * @param o
     * @param a
     * @param b
     * @return
     */
    public static double angle(Vector o, Vector a, Vector b) {
        return o.angle(a, b);
    }

    /**
     * 求向量a与b的夹角
     *
     * @param a 向量
     * @param b 向量
     * @return 返回弧度[-PI,+PI]
     */
    public static double angle(Vector a, Vector b) {
        double lfRgn = a.dotProduct(b);// AdotB(A,B);
        double lfLA = a.length();
        double lfLB = b.length();
        double cosA = lfRgn / (lfLA * lfLB); //[-1,1]
        return java.lang.Math.acos(cosA);
    }

    /**
     * 弧度转换成角度
     *
     * @param r 弧度
     * @return 角度
     */
    public static double radianToDegree(double r) {
        return r * 180 / Math.PI;
    }

    /**
     * 角度转换成弧度
     *
     * @param d 角度
     * @return 弧度
     */
    public static double degreeToRadian(double d) {
        return d * Math.PI / 180;
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static Vector subtract(Vector a, Vector b) {
        return a.subtract(b);
    }

    /**
     * @param a
     * @param b
     * @return
     */
    public static Vector add(Vector a, Vector b) {
        return a.add(b);
    }

    /**
     * Closest PointShape of Approach (CPA)
     * The "Closest PointShape of Approach" refers to the positions at which two dynamically moving objects reach their closest possible distance.
     * This is an important calculation for collision avoidance. In many cases of interest, the objects, referred to as "tracks",
     * are points moving in two fixed directions at fixed speeds. That means that the two points are moving along two lines in space.
     * However, their closest distance is not the same as the closest distance between the lines since the distance between the points must be computed at the same moment in time.
     * So, even in 2D with two lines that intersect, points moving along these lines may remain far apart. But if one of the tracks is stationary,
     * then the CPA of another moving track is at the base of the perpendicular from the first track to the second's line of motion.
     * cpa_time(): compute the time of CPA for two tracks
     * Input:  two tracks Tr1 and Tr2
     *
     * @param Tr1
     * @param Tr2
     * @return the time at which the two tracks are closest
     * ref:http://geomalgorithms.com/a07-_distance.html
     */
    public static double cpaTime(Track Tr1, Track Tr2) {
        Vector dv = subtract(Tr1.velocity, Tr2.velocity);

        double dv2 = dotProduct(dv, dv);
        if (dv2 < MathSuits.EPSILON)      // the  tracks are almost parallel
            return 0.0;             // any time is ok.  Use time 0.

        Vector w0 = subtract(Tr1.origin, Tr2.origin);
        double cpatime = -dotProduct(w0, dv) / dv2;

        return cpatime;             // time of CPA
    }

    /**
     * Closest PointShape of Approach (CPA)
     * The "Closest PointShape of Approach" refers to the positions at which two dynamically moving objects reach their closest possible distance.
     * This is an important calculation for collision avoidance. In many cases of interest, the objects, referred to as "tracks",
     * are points moving in two fixed directions at fixed speeds. That means that the two points are moving along two lines in space.
     * However, their closest distance is not the same as the closest distance between the lines since the distance between the points must be computed at the same moment in time.
     * So, even in 2D with two lines that intersect, points moving along these lines may remain far apart. But if one of the tracks is stationary,
     * then the CPA of another moving track is at the base of the perpendicular from the first track to the second's line of motion.
     * cpa_distance(): compute the distance at CPA for two tracks
     *
     * @param Tr1
     * @param Tr2
     * @return the distance for which the two tracks are closest
     * ref: http://geomalgorithms.com/a07-_distance.html
     */
    public static double cpaDistance(Track Tr1, Track Tr2) {
        double ctime = cpaTime(Tr1, Tr2);
        Vector P1 = add(Tr1.origin, Tr1.velocity.multiply(ctime));
        Vector P2 = add(Tr2.origin, Tr2.velocity.multiply(ctime));

        return distance(P1, P2);            // distance at CPA
    }

    /**
     * find the 3D intersection of a segment and a plane
     * S = a segment, and Pn = a plane = {PointShape V0;  Vector n;}
     *
     * @param S
     * @param Pn
     * @param outPoint the intersect point (when it exists)
     * @return 0 = disjoint (no intersection)
     * 1 =  intersection in the unique point *I0
     * 2 = the segment lies in the plane
     */
    public static int intersection(LineSegment S, Plane Pn, Vector outPoint) {
        Vector SP1 = S.endPoint;
        Vector SP0 = S.startPoint;
        Vector PnV0 = Pn.getVertices()[0];
        Vector Pnn = Pn.getNormal();
        //Vector u = S.P1 - S.P0;
        Vector u = SP1.subtract(SP0);
        //Vector w = S.P0 - Pn.V0;
        Vector w = SP0.subtract(PnV0);
        double D = dotProduct(Pnn, u);
        double N = -dotProduct(Pnn, w);

        if (Math.abs(D) < MathSuits.EPSILON) { // segment is parallel to plane
            if (N == 0) // segment lies in plane
                return 2;
            else
                return 0;// no intersection
        }
        // they are not parallel
        // compute intersect param
        double sI = N / D;
        if (sI < 0 || sI > 1)
            return 0; // no intersection

        //*I = S.P0 + sI * u; // compute segment intersect point
        outPoint.copyFrom(u.multiply(sI).add(SP0));
        return 1;
    }

    /**
     * find the 3D intersection of two planes
     *
     * @param Pn1
     * @param Pn2
     * @param outLine the intersection line (when it exists)
     * @return 0 = disjoint (no intersection)
     * 1 = the two  planes coincide
     * 2 =  intersection in the unique line *L
     */
    public static int intersection(Plane Pn1, Plane Pn2, InfiniteLine outLine) {
        Vector Pn1V0 = Pn1.getVertices()[0];
        Vector Pn2V0 = Pn2.getVertices()[0];
        Vector Pn1n = Pn1.getNormal();
        Vector Pn2n = Pn2.getNormal();
        Vector u = crossProduct(Pn1n, Pn2n); // cross product
        double ax = (u.getX() >= 0 ? u.getX() : -u.getX());
        double ay = (u.getY() >= 0 ? u.getY() : -u.getY());
        double az = (u.getZ() >= 0 ? u.getZ() : -u.getZ());

        // test if the two planes are parallel
        if ((ax + ay + az) < MathSuits.EPSILON) { // Pn1 and Pn2 are near parallel
            // test if disjoint or coincide
            Vector v = Pn2V0.subtract(Pn1V0);
            if (dotProduct(Pn1n, v) == 0) // Pn2V0 lies in Pn1
                return 1;  // Pn1 and Pn2 coincide
            else
                return 0; // Pn1 and Pn2 are disjoint
        }
        // Pn1 and Pn2 intersect in a line
        // first determine max abs coordinate of cross product
        int maxc = 0; // max coordinate
        if (ax > ay) {
            if (ax > az)
                maxc = 1;
            else
                maxc = 3;
        } else {
            if (ay > az)
                maxc = 2;
            else
                maxc = 3;
        }
        // next, to get a point on the intersect line
        // zero the max coord, and solve for the other two
        double x = 0, y = 0, z = 0;//Vector iP;  // intersect point
        double d1, d2; // the constants in the 2 plane equations
        d1 = -dotProduct(Pn1n, Pn1V0); // note: could be pre-stored  with plane
        d2 = -dotProduct(Pn2n, Pn2V0); // ditto
        switch (maxc) { // select max coordinate
            case 1:// intersect with x=0
            {
                x = 0;
                y = (d2 * Pn1n.getZ() - d1 * Pn2n.getZ()) / u.getX();
                z = (d1 * Pn2n.getY() - d2 * Pn1n.getY()) / u.getX();
                break;
            }
            case 2: // intersect with y=0
            {
                x = (d1 * Pn2n.getZ() - d2 * Pn1n.getZ()) / u.getY();
                y = 0;
                z = (d2 * Pn1n.getX() - d1 * Pn2n.getX()) / u.getY();
                break;
            }
            case 3:// intersect with z=0
            {
                x = (d2 * Pn1n.getY() - d1 * Pn2n.getY()) / u.getZ();
                y = (d1 * Pn2n.getX() - d2 * Pn1n.getX()) / u.getZ();
                z = 0;
                break;
            }
        }
        Vector iP = new VectorImpl(x, y, z);
        outLine.reset(iP, iP.add(u));
        return 2;
    }

    /**
     * find the 3D intersection of a ray with a triangle
     *
     * @param R
     * @param T
     * @param outPoint intersection point (when it exists)
     * @return -1 = triangle is degenerate (a segment or point)
     * 0 =  disjoint (no intersect)
     * 1 =  intersect in unique point outPoint
     * 2 =  are in the same plane
     */
    public static int intersection(Ray R, Triangle T, Vector outPoint) {
        Vector TV0 = T.getVertex(0);
        Vector TV1 = T.getVertex(1);
        Vector TV2 = T.getVertex(2);
        Vector RP0 = R.startPoint;
        Vector RP1 = R.endPoint;
        Vector u, v, n;// triangle vectors
        Vector dir, w0, w;// ray vectors
        double r, a, b;// params to calc ray-plane intersect

        // get triangle edge vectors and plane normal
        u = subtract(TV1, TV0);
        v = subtract(TV2, TV0);
        n = crossProduct(u, v); // cross product
        if (n.equals(new VertexImpl(0, 0, 0)) || n.length() == 0)  // triangle is degenerate
            return -1; // do not deal with this case

        dir = subtract(RP1, RP0);// ray direction vector
        w0 = subtract(RP0, TV0);
        a = -dotProduct(n, w0);
        b = dotProduct(n, dir);
        if (Math.abs(b) < MathSuits.EPSILON) {// ray is  parallel to triangle plane
            if (a == 0)// ray lies in triangle plane
                return 2;
            else
                return 0;// ray disjoint from plane
        }

        // get intersect point of ray with triangle plane
        r = a / b;
        if (r < 0.0)// ray goes away from triangle
            return 0; // => no intersect
        // for a segment, also test if (r > 1.0) => no intersect
        //*I = RP0 + r * dir;// intersect point of ray and plane
        outPoint.copyFrom(dir.multiply(r).add(RP0));

        // is I inside T?
        double uu, uv, vv, wu, wv, D;
        uu = dotProduct(u, u);
        uv = dotProduct(u, v);
        vv = dotProduct(v, v);
        //w = *I - TV0;
        w = outPoint.subtract(TV0);
        wu = dotProduct(w, u);
        wv = dotProduct(w, v);
        D = uv * uv - uu * vv;

        // get and test parametric coords
        double s, t;
        s = (uv * wv - vv * wu) / D;
        if (s < 0.0 || s > 1.0) // I is outside T
            return 0;
        t = (uv * wu - uu * wv) / D;
        if (t < 0.0 || (s + t) > 1.0) // I is outside T
            return 0;

        return 1;// I is in T
    }


    /**
     * point in triangle with Barycentric Technique
     * abc are the three points of a triangle ,
     * their direction is clockwise.
     * It will also work with clockwise and anticlockwise triangles.
     * It will not work with collinear triangles. So the collinear
     * test should  execute before this.
     *
     * @param p
     * @param a
     * @param b
     * @param c
     * @return 在三角形内或三角形的边上、顶点，都会返回true，否则返回false
     * ref : http://blackpawn.com/texts/pointinpoly/default.html
     * ref : http://www.cnblogs.com/graphics/archive/2010/08/05/1793393.html
     */
    public static boolean pointInTriangle(Vector p, Vector a, Vector b, Vector c) {

        // Compute vectors
        //v0 = C - A
        Vector v0 = subtract(c, a);
        //v1 = B - A
        Vector v1 = subtract(b, a);
        //v2 = P - A
        Vector v2 = subtract(p, a);

        // Compute dot products
        double dot00 = dotProduct(v0, v0);
        double dot01 = dotProduct(v0, v1);
        double dot02 = dotProduct(v0, v2);
        double dot11 = dotProduct(v1, v1);
        double dot12 = dotProduct(v1, v2);

        // Compute barycentric coordinates
        double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
        double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
        if (u < 0 || u > 1) { // if u out of range, return directly
            return false;
        }
        double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
        if (v < 0 || v > 1) { // if v out of range, return directly
            return false;
        }
        // Check if point is in triangle,
        // if the point is on one of the edges, u or v should be zero
        // point in segment ac, v=0, 0<u<1
        // point in segment ab, u=0, 0<v<1
        // point in segment bc, u+v=1
        // point is one of the vertices of triangle, u=0 or v=0 ;
        //原来的算法是 return (u >= 0) && (v >= 0) && (u + v < 1);
        //由于p位于bc上的时候，u+v=1,所以返回条件做了修改
        return (u >= 0) && (v >= 0) && (u + v <= 1);
    }

    /**
     * point in triangle with Same Side Technique
     * abc are the three points of a triangle ,
     * the vertices direction is clockwise.
     *
     * @param p
     * @param a
     * @param b
     * @param c
     * @return ref : http://blackpawn.com/texts/pointinpoly/default.html
     * function SameSide(p1,p2, a,b)
     * cp1 = CrossProduct(b-a, p1-a)
     * cp2 = CrossProduct(b-a, p2-a)
     * if DotProduct(cp1, cp2) >= 0 then return true
     * else return false
     * <p>
     * function PointInTriangle(p, a,b,c)
     * if SameSide(p,a, b,c) and SameSide(p,b, a,c)
     * and SameSide(p,c, a,b) then return true
     * else return false
     * ref : http://www.cnblogs.com/graphics/archive/2010/08/05/1793393.html
     * 假设点P位于三角形内，会有这样一个规律，
     * 当我们沿着ABCA的方向在三条边上行走时，
     * 你会发现点P始终位于边AB，BC和CA的右侧。
     * 我们就利用这一点，但是如何判断一个点在线段的左侧还是右侧呢？
     * 我们可以从另一个角度来思考，当选定线段AB时，
     * 点C位于AB的右侧，同理选定BC时，点A位于BC的右侧，
     * 最后选定CA时，点B位于CA的右侧，所以当选择某一条边时，
     * 我们只需验证点P与该边所对的点在同一侧即可。
     * 问题又来了，如何判断两个点在某条线段的同一侧呢？
     * 可以通过叉积来实现，连接PA，将PA和AB做叉积，
     * 再将CA和AB做叉积，如果两个叉积的结果方向一致，
     * 那么两个点在同一测。
     * 判断两个向量的是否同向可以用点积实现，
     * 如果点积大于0，则两向量夹角是锐角，否则是钝角。
     */
    public static boolean pointInTriangle2(Vector p, Vector a, Vector b, Vector c) {
        return sameSide(p, a, b, c) && sameSide(p, b, a, c)
                && sameSide(p, c, a, b);
    }

    private static boolean sameSide(Vector p1, Vector p2, Vector a, Vector b) {
        Vector cp1 = crossProduct(subtract(b, a), subtract(p1, a));
        Vector cp2 = crossProduct(subtract(b, a), subtract(p2, a));
        return dotProduct(cp1, cp2) >= 0;
    }

    /**
     * 判断点p是否在直线L上，也即是否共线
     *
     * @param p 点
     * @param L 直线，两点表示V0，V1
     * @return 如果共线返回true, 否则返回false
     */
    public static boolean collinear(Vector p, InfiniteLine L) {
        return collinear(p, L.getStartPoint(), L.getEndPoint());
    }

    /**
     * 判断点p,V1,V0是否共线
     * 判定的规则是，其中一个点p到直线的距离为0
     * \ (V1-V0) x (p-V0)\
     * d = ------------------- =0
     * \V1-V0\
     * 也就是 \ (V1-V0) x (p-V0)\ =0
     *
     * @param p 点
     * @param L 直线，两点表示V0，V1
     * @return 如果共线返回true, 否则返回false
     * ref:http://mathworld.wolfram.com/Collinear.html
     */
    public static boolean collinear(Vector p, Vector v0, Vector v1) {
        return v1.subtract(v0).crossProduct(p.subtract(v0)).length() < MathSuits.EPSILON;
    }

    /**
     * 判断两条直线是否平行
     * Two vectors  are parallel if their cross product is zero.
     * that is its length is zero.
     *
     * @param L1
     * @param L2
     * @return ref:http://mathworld.wolfram.com/ParallelVectors.html
     */
    public static boolean parallel(InfiniteLine L1, InfiniteLine L2) {
        Vector u = L1.getEndPoint().subtract(L1.getStartPoint());
        Vector v = L2.getEndPoint().subtract(L2.getStartPoint());
        return crossProduct(u, v).length() < MathSuits.EPSILON;
    }

    /**
     * 首先判断是否共线
     * 如果p在Seg上，则ps与pe的夹角应该是0，
     *
     * @param p
     * @param Seg
     * @return -1 = p与Seg不线
     * 0 = p 在 Seg上，并且位于[s,e]之间
     * 1 = p 在 Seg的s点的外端延长线上
     * 2 = p 在 Seg的e点的外端延长线上
     */
    public static int pointInLineSegment(Vector p, LineSegment Seg) {
        Vector s = Seg.getStartPoint();
        Vector e = Seg.getEndPoint();
        if (collinear(p, s, e) == false) return -1;
        /*夹角spe如果等于180,则p在 Seg上，并且位于[s,e]之间
          根据下列求夹角的计算方法
          double lfRgn =a.dotProduct(b);// AdotB(A,B);
          double lfLA = a.length();
          double lfLB = b.length();
          double cosA = lfRgn/(lfLA*lfLB); //[-1,1]
          return java.lang.Math.acos(cosA);
          可以推导出来，在三点共线的情况下，cosA =-1,由于lfLA*lfLB>0
          所以，只要判断lfRgn<0，就可以判定其夹角为180，因此，只需要计算
          dot((s-p),(e-p))<0
         */
        if (dotProduct(s.subtract(p), e.subtract(p)) < 0)
            return 0;
        else {
            //如果在延长线上，可以通过距离判断在哪一端
            if (distance(p, s) < distance(e, e))
                return 1;//在s段的延长线上
            else
                return 2;//在e段的延长线上
        }
    }

    /**
     * with the condition that the four points be coplanar,for
     * L1 (x1,x2)
     * L2(x3,x4)
     * a=x2-x1
     * b=x4-x3
     * c=x3-x1
     * (cxb).(axb)
     * x=x1+a ---------
     * |axb|的平方
     *
     * @param L1
     * @param L2
     * @return 0 =  disjoint (no intersect) non-coplanar
     * 1 =  intersect in unique point outPoint
     * 2 =  parallel line
     * 3 =   overlap,same line
     * ref : http://mathworld.wolfram.com/Line-LineIntersection.html
     */
    public static int intersection(InfiniteLine L1, InfiniteLine L2, Vector outPoint) {
        Vector x1 = L1.getStartPoint();
        Vector x2 = L1.getEndPoint();
        Vector x3 = L2.getStartPoint();
        Vector x4 = L2.getEndPoint();
        if (coplanar(x1, x2, x3, x4) == false) return 0;
        if (parallel(L1, L2) == true) {
            if (perpendicularDistance(x1, L2) < MathSuits.EPSILON)
                return 3;
            else
                return 2;
        }

        Vector a = subtract(x2, x1);
        Vector b = subtract(x4, x3);
        Vector c = subtract(x3, x1);
        Vector axb = crossProduct(a, b);
        double sd = dotProduct(crossProduct(c, b), axb);
        sd = sd / Math.pow(axb.length(), 2);
        Vector intersectionPoint = a.multiply(sd).add(L1.getStartPoint());
        if (outPoint != null)
            outPoint.copyFrom(intersectionPoint);
        return 1;
    }

    /**
     * 判断四个点是否共面,
     * Coplanarity is equivalent to the statement
     * that the pair of lines determined by the
     * four points are not skew, and can be
     * equivalently stated in vector form as
     * (x3-x1).[(x2-x1)x(x4-x3)]=0
     *
     * @param x1 点
     * @param x2 点
     * @param x3 点
     * @param x4 点
     * @return 共面返回true，否则返回false
     * ref :http://mathworld.wolfram.com/Coplanar.html
     */
    public static boolean coplanar(Vector x1, Vector x2, Vector x3, Vector x4) {
        Vector cross = crossProduct(subtract(x2, x1), subtract(x4, x3));
        double d = dotProduct(subtract(x3, x1), cross);
        return Math.abs(d) < MathSuits.EPSILON;
    }

    /**
     * with the condition that the four points be coplanar,for
     * S1 (x1,x2)
     * S2(x3,x4)
     * a=x2-x1
     * b=x4-x3
     * c=x3-x1
     * (cxb).(axb)
     * x=x1+a ---------
     * |axb|的平方
     *
     * @param S1
     * @param S2
     * @return 0 =  disjoint (no intersect) ,non-coplanar or no intersection
     * 1 =  intersect in unique point outPoint (when exist)
     * 2 =  parallel line
     * 3 =   overlap,same line
     * ref : http://mathworld.wolfram.com/Line-LineIntersection.html
     */
    public static int intersection(LineSegment S1, LineSegment S2, Vector outPoint) {
        Vector v = (Vector) S2.getEndPoint().clone();
        int r = intersection(S1.extend(), S2.extend(), v);
        if (r == 1) {
            if (pointInLineSegment(v, S1) == 0) {
                if (outPoint != null)
                    outPoint.copyFrom(v);
                return r;
            } else {
                return 0;
            }
        }
        return r;
    }

    /**
     * find the intersection (LineSegment & Triangle)
     *
     * @param S         LineSegment
     * @param T         Triangle
     * @param outPoint  intersect point (when it exists)
     * @param outPoint2 endpoint of intersect segment [I0,I1] (when it exists)
     * @return 0=disjoint (no intersect)
     * 1=intersect in unique point outPoint
     * at lease one point of S in triangle
     * 2=overlap in segment from outPoint to outPoint2
     * both points out triangle
     * 3=both points in triangle,
     * outPoint=S.startPoint,and
     * outPoint2=S.endPoint
     */
    public static int intersection(LineSegment S, Triangle T,
                                   Vector outPoint, Vector outPoint2) {
        boolean testInS = pointInTriangle(S.getStartPoint(),
                T.getVertex(0), T.getVertex(2), T.getVertex(1));
        boolean testInE = pointInTriangle(S.getEndPoint(),
                T.getVertex(0), T.getVertex(2), T.getVertex(1));

        if (testInE && testInS) {//both in triangle
            if (outPoint != null) outPoint.copyFrom(S.getStartPoint());
            if (outPoint2 != null) outPoint2.copyFrom(S.getEndPoint());
            return 3;
        } else if (testInE == true || testInS == true) {
            if (intersection(S, T.getEdge(0, 1), outPoint) != 1) {
                if (intersection(S, T.getEdge(1, 2), outPoint) != 1) {
                    assert intersection(S, T.getEdge(2, 0), outPoint) == 1;
                }
            }
            return 1;
        } else {
            int f01 = intersection(S, T.getEdge(0, 1), outPoint);
            if (f01 != 1) {
                if (intersection(S, T.getEdge(1, 2), outPoint) != 1) {
                    if (intersection(S, T.getEdge(2, 0), outPoint) != 1)
                        return 0;
                } else {
                    intersection(S, T.getEdge(2, 0), outPoint2);
                }
            } else {
                intersection(S, T.getEdge(1, 2), outPoint2);
                intersection(S, T.getEdge(2, 0), outPoint2);
            }
            return 2;
        }
    }

    /**
     * @param P
     * @param L
     * @return return the foot of perpendicular to L
     */
    public Vector perpendicularFoot(Vector P, InfiniteLine L) {
        Vector P0 = L.startPoint;
        Vector P1 = L.endPoint;
        Vector v = P1.subtract(P0);//P1 - P0;
        Vector w = P.subtract(P0);//P - P0;

        double c1 = w.dotProduct(v);//dot(w,v);
        double c2 = v.dotProduct(v);//dot(v,v);
        Scalar b = new Scalar(c1 / c2);
        //double b = c1 / c2;
        //PointShape Pb = L.P0 + b * v;
        Vector Pb = P0.add(b.multiply(v));
        return Pb;
    }

    /**
     * @param P
     * @param L
     * @return return the foot of perpendicular to S
     */
    public Vector perpendicularFoot(Vector P, LineSegment S) {
        Vector P0 = S.startPoint;
        Vector P1 = S.endPoint;
        Vector v = P1.subtract(P0);//P1 - P0;
        Vector w = P.subtract(P0);//P - P0;

        double c1 = w.dotProduct(v);//dot(w,v);
        double c2 = v.dotProduct(v);//dot(v,v);
        Scalar b = new Scalar(c1 / c2);
        //double b = c1 / c2;
        //PointShape Pb = S.P0 + b * v;
        Vector Pb = P0.add(b.multiply(v));

        /*
        如果S.startPoint p S.endPoint的夹角为180,则Pb位于S上，
        否则，在S的延长线上，在共线的情况下，只需要判断两个向量的
        点集是否为负数即可。
         */
        if (dotProduct(S.getStartPoint().subtract(Pb), S.getEndPoint().subtract(Pb)) < 0)
            return Pb;
        else//Pb在延长线上，返回空值
            return null;
    }


}
