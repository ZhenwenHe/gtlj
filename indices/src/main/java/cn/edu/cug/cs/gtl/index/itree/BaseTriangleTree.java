package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.geom.Interval;
import cn.edu.cug.cs.gtl.geom.Triangle;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.geom.Vector2D;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.index.shape.LineSegmentShape;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;

import java.io.Serializable;
import java.util.List;

public abstract class BaseTriangleTree<T extends Interval> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 基准三角形，记录根节点的范围，其始终是等腰直角三角形
     * V0为直角顶点，节点按照逆时针方向排列，分别V1，V2；
     * V0V1为Y轴方向，V2V0为X轴方向，作为基准坐标系的时候，V1为原点；
     * 基准三角形的范围可以左扩展（leftExtension），
     * 也可以右扩展（rightExtension），可以无限扩大；
     * 扩展后的基准三角形与原来的基准三角形为相似三角形
     */
    protected TriangleShape baseTriangle;
    /**
     * 每个叶子节点中最多能存放leafNodeCapacity个间隔数据对象
     */
    protected int leafNodeCapacity;

    /**
     * 将间隔查询转成点、线、面三种类型的空间查询
     * 如果对基本三角形进行了扩展，生成器中的基本
     * 三角形要跟着变动
     */
    transient QueryShapeGenerator<T> queryShapeGenerator;

    public BaseTriangleTree(TriangleShape triangle, int leafNodeCapacity) {
        this.baseTriangle = new TriangleShape(triangle);
        this.leafNodeCapacity = leafNodeCapacity;
        this.queryShapeGenerator = new QueryShapeGenerator<T>(baseTriangle);
    }

    /**
     * 点查询，也即是间隔数据的相等查询
     *
     * @param ps 由QueryShapeGenerator生成的点状查询图形
     * @return 返回查询结果
     */
    public abstract List<T> pointQuery(final PointShape ps);

    /**
     * 线查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param lsp 由QueryShapeGenerator生成的线状查询图形
     * @return 返回查询结果的个数
     */
    public abstract List<T> lineQuery(final LineSegmentShape lsp);

    /**
     * 区域查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param rs 由QueryShapeGenerator生成的三角形（由于e>s，
     *           所以三角形扩展成矩形也不会影响查询结果，
     *           这样更加便于相交计算）或矩形查询图形
     * @return 返回查询结果
     */
    public abstract List<T> regionQuery(final RegionShape rs);

    /**
     * 插入一个Interval对象
     *
     * @param i
     * @return
     */
    public abstract boolean insert(T i);

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     *
     * @param triangle
     * @param i
     * @return 0- out of triangle
     * 1-left sub triangle
     * 2- right sub triangle
     */
    public static int test(TriangleShape triangle, Interval i) {
        Vector2D v = new Vector2D(i.getLowerBound(), i.getUpperBound());
        return test(triangle, v);
    }

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     *
     * @param triangle
     * @param i
     * @return 0- out of triangle
     * 1-left sub triangle
     * 2- right sub triangle
     */
    public static int test(TriangleShape triangle, PointShape i) {
        Vector2D v = new Vector2D(i.getX(), i.getY());
        return test(triangle, v);
    }

    /**
     * 测试间隔数据对象i是否在基准三角形baseTriangle(直角等腰三角形)里面
     * 如果返回0，表示在三角形的外面；
     * 如果返回1，表示在基准三角形的左子三角形里面或边上
     * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
     *
     * @param triangle
     * @param v
     * @return 0- out of triangle
     * 1-left sub triangle
     * 2- right sub triangle
     */
    public static int test(TriangleShape triangle, Vector v) {
        if (!triangle.contains(v)) {
            return 0;
        }
        Triangle left = triangle.leftTriangle();
        if (left.contains(v))
            return 1;
        else
            return 2;
    }


}
