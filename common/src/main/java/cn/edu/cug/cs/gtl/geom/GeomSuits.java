package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.*;
import java.util.List;
import java.util.Random;

/**
 * Created by hadoop on 17-3-21.
 */
public class GeomSuits {
    /**
     * A value that indicates an orientation of clockwise, or a right turn.
     */
    public static final int CLOCKWISE = -1;

    /**
     * A value that indicates an orientation of clockwise, or a right turn.
     */
    public static final int RIGHT = CLOCKWISE;

    /**
     * A value that indicates an orientation of counterclockwise, or a left turn.
     */
    public static final int COUNTERCLOCKWISE = 1;

    /**
     * A value that indicates an orientation of counterclockwise, or a left turn.
     */
    public static final int LEFT = COUNTERCLOCKWISE;

    /**
     * A value that indicates an orientation of collinear, or no turn (straight).
     */
    public static final int COLLINEAR = 0;

    /**
     * A value that indicates an orientation of collinear, or no turn (straight).
     */
    public static final int STRAIGHT = COLLINEAR;


    /**
     * A value which is safely greater than the
     * relative round-off error in double-precision numbers
     */
    protected static final double DP_SAFE_EPSILON = 1e-15;

    @Deprecated
    public static Interval createInterval(IntervalType t, double low, double high) {
        return new IntervalImpl(t, low, high);
    }

    public static ComplexInterval createLabeledInterval(
            IntervalType t, double low, double high, String label,
            long pid, long order) {
        return new ComplexIntervalImpl(t, low, high, label, pid, order);
    }

    public static ComplexInterval createLabeledInterval(
            int t, double low, double high, String label,
            long pid, long order) {
        return new ComplexIntervalImpl(IntervalType.values()[t], low, high, label, pid, order);
    }

    public static Timeline createTimeline(Identifier identifier, List<Interval> li, List<String> ls) {
        return new TimelineImpl(identifier, li, ls);
    }

    public static Timeline createTimeline(Identifier identifier, List<ComplexInterval> li) {
        return new TimelineImpl(identifier, li);
    }

    public static Timeline createTimeline(String s) {
        return new TimelineImpl().parse(s);
    }

    @Deprecated
    public static Envelope createEnvelope(int dim) {
        return new Envelope(dim);
    }

    @Deprecated
    public static Envelope createEnvelope(double[] low, double[] high) {
        return new Envelope(low, high);
    }

    public static Vector createVector() {
        return new VectorImpl();
    }

    public static Vector createVector(double[] v) {
        return new VectorImpl(v);
    }

    public static Vector2D createVector(double x, double y) {
        return new Vector2D(x, y);
    }

    public static Vector3D createVector(double x, double y, double z) {
        return new Vector3D(x, y, z);
    }

    public static Vector4D createVector(double x, double y, double z, double w) {
        return new Vector4D(x, y, z, w);
    }

    public static VectorSequence createVectorSequence(double[] coordinates, int dim) {
        return new PackedVectorSequence(coordinates, dim);
    }

    public static VectorSequence createVectorSequence(int dim) {
        return new PackedVectorSequence(dim);
    }

    public static Vertex3D createVertex3D(double x, double y, double z) {
        return new Vertex3D(x, y, z);
    }

    public static Vertex2D createVertex2D(double x, double y) {
        return new Vertex2D(x, y);
    }

    public static Vertex2D createVertex2D(Vertex2D v) {
        return new Vertex2D(v.x, v.y);
    }

    public static Vertex3D createVertex3D() {
        return new Vertex3D(0.0, 0.0, 0.0);
    }

    public static Vertex2D createVertex2D() {
        return new Vertex2D(0.0, 0.0);
    }

    public static Vertex createVertex(Vertex c) {
        return new VertexImpl(c.x, c.y, c.z);
    }

    public static VertexSequence createVertexSequence(double[] coordinates, int dim) {
        return new PackedVertexSequence(coordinates, dim);
    }

    public static VertexSequence createVertexSequence(Vertex[] vertices) {
        return new PackedVertexSequence(vertices);
    }


    /**
     * each interval's range is [0,1.0)
     *
     * @param numb numeric of the generated intervals
     * @return
     */
    public static Interval[] generateRandomIntervals(int numb) {
        double v1, v2, v;
        Interval[] ivs = new Interval[numb];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < numb; i++) {
            v1 = r.nextDouble();
            v2 = r.nextDouble();
            while (v1 == v2) {
                v2 = r.nextDouble();
            }
            if (v1 > v2)
                ivs[i] = createInterval(IntervalType.IT_CLOSED, v2, v1);
            else
                ivs[i] = createInterval(IntervalType.IT_CLOSED, v1, v2);
        }
        return ivs;
    }

    /**
     * all vertices generated by this function are in the range [0,1.0)
     *
     * @param numb vertex numeric
     * @param dim  vertex dimension
     * @return
     */
    public static Vector[] generateRandomVertices(int numb, int dim) {
        double v1, v2, v;
        Vector[] ivs = new Vector[numb];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < numb; i++) {
            ivs[i] = createVector();
            ivs[i].makeDimension(dim);
            for (int k = 0; k < dim; k++) {
                ivs[i].getCoordinates()[k] = r.nextDouble();
            }
        }
        return ivs;
    }

    /**
     * all envelopes generated by this function are in the range [0,1.0)
     *
     * @param numb
     * @param dim
     * @param minEdgeLength
     * @param maxEdgeLength
     * @return
     */
    public static Envelope[] generateRandomEnvelopes(int numb, int dim, double minEdgeLength, double maxEdgeLength) {
        double v1, v2, v;
        Envelope[] ivs = new Envelope[numb];
        double[] originalVertex = new double[dim];
        double[] highVertex = new double[dim];
        double[] delta = new double[dim];//[minEdgeLength,maxEdgeLength]
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < numb; i++) {
            for (int k = 0; k < dim; k++) {
                originalVertex[k] = r.nextDouble();
                delta[k] = r.nextDouble();
                while (delta[k] > maxEdgeLength || delta[k] < minEdgeLength)
                    delta[k] = r.nextDouble();
                highVertex[k] = originalVertex[k] + delta[k];
            }
            ivs[i] = createEnvelope(originalVertex, highVertex);
        }
        return ivs;
    }

    public static boolean writeEnvelopeFile(Envelope[] envelopes, String envelopeFileName) {
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(envelopeFileName));
            dos.writeInt(envelopes.length);
            for (Envelope e : envelopes)
                e.store(dos);
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static Envelope[] readEnvelopeFile(String envelopeFileName) {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(envelopeFileName));
            int len = dis.readInt();
            if (len == 0) return null;
            Envelope[] envelopes = new Envelope[len];
            for (int i = 0; i < len; ++i) {
                envelopes[i] = createEnvelope(3);
                envelopes[i].load(dis);
            }
            return envelopes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
