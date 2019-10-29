package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.geom.Interval;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.geom.VectorImpl;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.HashMap;
import java.util.Map;

public class TITree<T extends Interval> extends TDTree<T> {

    private static final long serialVersionUID = 1L;


    public TITree(TriangleShape root, int leafCapacity, StorageManager sm, JavaSparkContext jsc) {
        super(root, leafCapacity, sm, jsc);
    }

    @Override
    public boolean insert(T i) {
        int r = test(this.baseTriangle, i);
        if (r == 0)
            extend(i);
        return super.insert(i);
    }

    /**
     * 如果i不在baseTriangle里面或边上，则需要扩展baseTriangle
     *
     * @param i
     * @return
     */
    void extend(T i) {
        TriangleShape newBaseTriangle = this.baseTriangle;
        Vector V0;
        while (test(this.baseTriangle, i) == 0) {
            V0 = this.baseTriangle.getVertex(0);
            if (i.getLowerBound() <= V0.getX())
                leftExtension();
            else
                rightExtension();
        }
        constructedRDD.set(false);
        constructRDD();
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->left extension
     * newRoot
     * left              right
     * rootNode     right
     *
     * @return
     */
    void leftExtension() {
        //triangle ABC
        TriangleShape ABC = this.baseTriangle;

        Vector[] vertices = this.baseTriangle.getClockwiseVertices();
        Vector V0 = new VectorImpl(vertices[0].getX() - (vertices[2].getX() - vertices[0].getX()),
                vertices[0].getY(), 0.0);
        Vector V2 = vertices[2];
        Vector V1 = new VectorImpl(V0.getX(),
                V0.getY() - 2 * (vertices[0].getY() - vertices[1].getY()), 0.0);

        //triangle GCK
        TriangleShape GCK = new TriangleShape(V0, V1, V2);
        //triangle BKG
        TriangleShape BKG = GCK.leftTriangle();
        //triangle BGC
        TriangleShape BGC = GCK.rightTriangle();
        //triangle ABG
        TriangleShape ABG = BGC.rightTriangle();

        //1.reset the base triangle
        this.baseTriangle = new TriangleShape(GCK.getVertices());
        //2.reset QueryShapeGenerator
        this.queryShapeGenerator.reset(baseTriangle);
        //3.reset TriangleEncoder
        this.triangleEncoder.reset(baseTriangle);
        //4. generate new leafInfos
        Map<String, Identifier> newMap = new HashMap<>();
        for (Map.Entry<String, Identifier> e : leafInfos.entrySet()) {
            newMap.put("110" + e.getKey().substring(1), e.getValue());
        }
        //5.insert empty leaf node ABG
        newMap.put("111", Identifier.create(-1L));
        //6.insert empty leaf node BKG
        newMap.put("10", Identifier.create(-1L));
        //7. reset the leafInfos
        leafInfos = newMap;
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->right extension
     * newRoot
     * left              right(leaf)
     * left（leaf)  rootNode
     *
     * @return
     */
    void rightExtension() {
        //triangle ABC
        TriangleShape ABC = this.baseTriangle;

        Vector[] vertices = ABC.getVertices();
        Vector V0 = new VectorImpl(vertices[0].getX(),
                vertices[0].getY() + (vertices[0].getY() - vertices[1].getY()), 0.0);
        Vector V1 = vertices[1];
        Vector V2 = new VectorImpl(vertices[2].getX() + vertices[2].getX() - vertices[0].getX(),
                V0.getY(), 0.0);

        //triangle DEB
        TriangleShape DEB = new TriangleShape(V0, V1, V2);
        //triangle CBD
        TriangleShape CBD = DEB.leftTriangle();
        //triangle CDE
        TriangleShape CDE = DEB.rightTriangle();
        //triangle ADC
        TriangleShape ADC = CBD.leftTriangle();

        //1.reset the base triangle
        this.baseTriangle = new TriangleShape(DEB.getVertices());
        //2.reset QueryShapeGenerator
        this.queryShapeGenerator.reset(baseTriangle);
        //3.reset TriangleEncoder
        this.triangleEncoder.reset(baseTriangle);
        //4. generate new leafInfos
        Map<String, Identifier> newMap = new HashMap<>();
        for (Map.Entry<String, Identifier> e : leafInfos.entrySet()) {
            newMap.put("101" + e.getKey().substring(1), e.getValue());
        }
        //5.insert empty leaf node ADC
        newMap.put("100", Identifier.create(-1L));
        //6.insert empty leaf node CDE
        newMap.put("11", Identifier.create(-1L));
        //7. reset the leafInfos
        leafInfos = newMap;
    }

}
