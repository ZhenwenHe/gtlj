package cn.edu.cug.cs.gtl.index.itree;


import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Interval;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.geom.VectorImpl;
import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.index.shape.LineSegmentShape;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhenwenHe on 2017/3/27.
 */
public class TBTree<T extends Interval> extends BaseTriangleTree<T> {
    private static final long serialVersionUID = 1L;
    /**
     * 树的根节点，其所包含的三角形范围为baseTriangle
     */
    TreeNode rootNode;

    /**
     * @param baseTriangle
     * @param leafNodeCapacity
     */
    public TBTree(TriangleShape baseTriangle, int leafNodeCapacity) {
        super(baseTriangle, leafNodeCapacity);
        this.rootNode = new TreeNode();
        this.rootNode.intervals = new ArrayList<>();
        rootNode.triangle = this.baseTriangle;
    }

    /**
     * 算法描述：
     * 1）如果不在本范围内，则调用extend方法进行三角形范围扩展
     * 1）调用findTreeNode查找i 要插入的节点tn（必定是叶子节点）
     * 2）如果tn的间隔数据对象个数小于leafNodeCapacity，则直接加入该节点
     * 3）如果tn中的间隔数据对象等于leafNodeCapacity，
     * 则执行节点分裂算法splitTreeNode，并将i插入
     *
     * @param i
     * @return
     */
    public boolean insert(T i) {
        if (test(this.baseTriangle, i) == 0) {
            this.rootNode = extend(i);
            this.baseTriangle = this.rootNode.triangle;
        }

        TreeNode tn = findTreeNode(i);
        if (tn.intervals.size() < leafNodeCapacity) {
            tn.intervals.add(i);
        } else {
            return splitTreeNode(i, tn);
        }
        return true;
    }


    /**
     * tn 是一个子节点，其中包含的间隔数据对象个数达到leafNodeCapacity
     * 在该节点中药插入i，则需要进行节点分裂,算法步骤如下：
     * 1)生成一个新的内部节点p,设置p的父节点为tn的父节点
     *
     * @param i
     * @param tn
     * @return
     */
    boolean splitTreeNode(T i, TreeNode tn) {

        List<T> intervals = tn.intervals;
        List<T> leftIntervals = new ArrayList<T>(0);
        List<T> rightIntervals = new ArrayList<T>(0);
        TriangleShape leftTriangleShape, rightTriangleShape;
        tn.intervals = null;
        intervals.add(i);
        TreeNode p = tn;
        TreeNode left, right;
        boolean loopFlag = true;

        while (loopFlag) {
            left = new TreeNode();
            left.parent = p;
            p.left = left;
            leftTriangleShape = new TriangleShape(p.triangle.leftTriangle());
            left.triangle = leftTriangleShape;

            right = new TreeNode();
            right.parent = p;
            p.right = right;
            rightTriangleShape = new TriangleShape(p.triangle.rightTriangle());
            right.triangle = rightTriangleShape;

            for (T it : intervals) {
                if (leftTriangleShape.contains(it)) {//在三角形外
                    leftIntervals.add(it);
                } else
                    rightIntervals.add(it);
            }
            //全部插入到左边三角形了，需要分解后重插
            if (leftIntervals.size() == intervals.size()) {
                leftIntervals.clear();
                p = left;
            }
            //全部插入到右边三角形了，需要分解后重插
            else if (rightIntervals.size() == intervals.size()) {
                rightIntervals.clear();
                p = right;
            }
            //分解完毕
            else {
                left.intervals = leftIntervals;
                right.intervals = rightIntervals;
                loopFlag = false;
            }
        }

        return true;
    }

    /**
     * 查找待插入的节点，返回必定为叶子节点，
     * 如果为空则表示应该调用extend函数要进行基准三角形扩展
     * 算法描述：
     * 1）让p指向根节点
     * 2）测试p的三角形范围与间隔数据对象的位置关系
     * 3) 如果i在p的左三角形里面或边上，让p指向其左节点
     * 4）如果i在p的右三角形里面或边上，让p指向其右节点
     * 5）如果p是叶子节点，则返回p;否则跳转到2）
     *
     * @param i
     * @return
     */
    TreeNode findTreeNode(T i) {
        TreeNode p = rootNode;
        int testResult = 0;
        while (p != null) {
            testResult = test(p.triangle, i);
            if (testResult == 0)
                return null;
            else if (testResult == 1) {
                if (p.isLeafNode())
                    return p;
                else
                    p = p.left;
            } else {//=2
                if (p.isLeafNode())
                    return p;
                else
                    p = p.right;
            }
        }
        return null;
    }

    /**
     * 如果i不在baseTriangle里面或边上，则需要扩展baseTriangle
     *
     * @param i
     * @return the new root node
     */
    TreeNode extend(T i) {
        TreeNode newRoot = this.rootNode;
        TriangleShape newBaseTriangle = this.rootNode.triangle;
        Vector V0;
        while (test(newBaseTriangle, i) == 0) {
            V0 = newRoot.triangle.getVertex(0);
            if (i.getLowerBound() <= V0.getX())
                newRoot = leftExtension(newRoot);
            else
                newRoot = rightExtension(newRoot);
            newBaseTriangle = newRoot.triangle;
        }
        this.rootNode = newRoot;
        this.baseTriangle = newBaseTriangle;
        this.queryShapeGenerator.baseTriangle = this.baseTriangle;
        return newRoot;
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->left extension
     * newRoot
     * left              right
     * rootNode     right
     *
     * @param tn
     * @return
     */
    TreeNode leftExtension(TreeNode tn) {
        TriangleShape baseT = tn.triangle;

        Vector[] vertices = baseT.getClockwiseVertices();
        Vector V0 = new VectorImpl(vertices[0].getX() - (vertices[2].getX() - vertices[0].getX()),
                vertices[0].getY(), 0.0);
        Vector V2 = vertices[2];
        Vector V1 = new VectorImpl(V0.getX(),
                V0.getY() - 2 * (vertices[0].getY() - vertices[1].getY()), 0.0);
        baseT = new TriangleShape(V0, V1, V2);
        TreeNode newRootNode = new TreeNode();
        newRootNode.triangle = baseT;

        newRootNode.left = new TreeNode();
        newRootNode.left.parent = newRootNode;
        newRootNode.left.intervals = new ArrayList<>();
        newRootNode.left.triangle = new TriangleShape(baseT.leftTriangle().getVertices());

        newRootNode.right = new TreeNode();
        newRootNode.right.parent = newRootNode;
        newRootNode.right.triangle = new TriangleShape(baseT.rightTriangle().getVertices());

        TreeNode p = newRootNode.right;
        p.right = new TreeNode();
        p.right.parent = p;
        p.right.triangle = new TriangleShape(
                p.triangle.rightTriangle().getVertices());
        p.right.intervals = new ArrayList<>();

        p.left = tn;
        p.left.parent = p;

        return newRootNode;
    }

    /**
     * 以传入的节点为基准三角形，进行范围扩展， 并返回扩展后的父节点
     * 图形参考 spatio-temporal query.vsox->extension->right extension
     * newRoot
     * left              right(leaf)
     * left（leaf)  rootNode
     *
     * @param tn
     * @return
     */
    TreeNode rightExtension(TreeNode tn) {
        TriangleShape baseT = tn.triangle;
        Vector[] vertices = baseT.getVertices();
        Vector V0 = new VectorImpl(vertices[0].getX(),
                vertices[0].getY() + (vertices[0].getY() - vertices[1].getY()), 0.0);
        Vector V1 = vertices[1];
        Vector V2 = new VectorImpl(vertices[2].getX() + vertices[2].getX() - vertices[0].getX(),
                V0.getY(), 0.0);
        baseT = new TriangleShape(V0, V1, V2);
        TreeNode newRootNode = new TreeNode();
        newRootNode.triangle = baseT;

        newRootNode.right = new TreeNode();
        newRootNode.right.parent = newRootNode;
        newRootNode.right.intervals = new ArrayList<>();
        newRootNode.right.triangle = new TriangleShape(baseT.rightTriangle().getVertices());

        newRootNode.left = new TreeNode();
        newRootNode.left.parent = newRootNode;
        newRootNode.left.triangle = new TriangleShape(baseT.leftTriangle().getVertices());

        TreeNode p = newRootNode.left;
        p.left = new TreeNode();
        p.left.parent = p;
        p.left.triangle = new TriangleShape(
                p.triangle.leftTriangle().getVertices());
        p.left.intervals = new ArrayList<>();

        p.right = tn;
        p.right.parent = p;

        return newRootNode;
    }

    /**
     * 点查询，也即是间隔数据的相等查询
     *
     * @param ps 由QueryShapeGenerator生成的点状查询图形
     * @return 返回查询结果
     */
    public List<T> pointQuery(PointShape ps) {
        TreeNode p = this.rootNode;
        TriangleShape pTri;
        int retVal;
        List<T> s = new ArrayList<>();
        while (p != null) {
            pTri = p.triangle;
            retVal = test(pTri, ps);
            if (retVal == 0) {//out
                return s;
            } else {//in
                if (p.isLeafNode()) {
                    for (T v : p.intervals) {
                        if (v.getLowerBound() == ps.getX()
                                && v.getUpperBound() == ps.getY()) {
                            s.add(v);
                        }
                    }
                    return s;
                } else {
                    if (retVal == 1) {//left
                        p = p.left;
                    } else {//right
                        p = p.right;
                    }
                }
            }
        }
        return s;
    }

    /**
     * 线查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param lsp 由QueryShapeGenerator生成的线状查询图形
     * @return 返回查询结果的个数
     */
    public List<T> lineQuery(LineSegmentShape lsp) {
        Vector s = lsp.getStartPoint();
        Vector e = lsp.getEndPoint();
        TreeNode p = rootNode;
        List<T> c = new ArrayList<>();
        int testS, testE;

        testE = test(p.triangle, lsp.getEndPoint());
        testS = test(p.triangle, lsp.getStartPoint());

        //如果线段的两个点都不在三角形内，则直接返回
        //这里还有一种可能的情况是，线段穿越了三角形，
        //并且两个端点都在三角形的外面，但是由于该函数
        //传入的线段都是QueryShapeGenerator生成的,不可
        //能出现这种情况，所以，这里可以不考虑。
        if (testE == 0 && testS == 0) return c;

        if (p.isLeafNode()) {
            for (T tv : p.intervals) {
                //test point on segment
                c.add(tv);
            }
            return c;
        } else {
            //两个点都在左边三角形
            if (testE == 1 && testS == 1) {
                p = p.left;
            }
            //两个点都在右边三角形
            else if (testE == 2 && testS == 2) {
                p = p.right;
            }
            //一个点在左边三角形，一个在右边三角形
            else {
                return c;
            }
        }
        return c;
    }

    /**
     * 区域查询，由间隔数据查询转换成的所有的现状图形的查询，
     * 所有落在线上的点构成查询结果集合
     *
     * @param rs 由QueryShapeGenerator生成的三角形（由于e>s，
     *           所以三角形扩展成矩形也不会影响查询结果，
     *           这样更加便于相交计算）或矩形查询图形
     * @return 返回查询结果的个数
     */
    public List<T> regionQuery(RegionShape rs) {
        List<T> f = new ArrayList<>();
        regionQuery(rootNode, rs.getMBR(), f);
        return f;
    }

    /**
     * 递归执行区域查询
     *
     * @param treeNode
     * @param rs
     * @param f
     * @return
     */
    private int regionQuery(TreeNode treeNode, Envelope rs, List<T> f) {
        if (!Geom2DSuits.intersects(rs, treeNode.triangle))
            return 0;
        else {
            if (treeNode.isLeafNode()) {
                for (T i : treeNode.intervals)
                    f.add(i);

                return 1;
            }
            if (treeNode.left != null && Geom2DSuits.intersects(rs, treeNode.left.triangle)) {
                regionQuery(treeNode.left, rs, f);
            }
            if (treeNode.right != null && Geom2DSuits.intersects(rs, treeNode.right.triangle)) {
                regionQuery(treeNode.right, rs, f);
            }
            return 1;
        }
    }

    /**
     * 树节点类，如果intervals==null，则为内部节点，
     * 否则为外部节点或叶子节点；
     * 当为内部节点的时候，left指向左子树节点，
     * right指向右子树节点；
     * parent指向父节点，如果父节点为空，则为根节点；
     * triangle是节点覆盖的三角形范围。
     */
    class TreeNode {
        TriangleShape triangle;
        TreeNode parent;
        TreeNode left;
        TreeNode right;
        /**
         * if null, internal node
         * else external node , or leaf
         */
        List<T> intervals;

        public TreeNode() {
            this.triangle = null;
            this.parent = null;
            this.left = null;
            this.right = null;
            this.intervals = null;
        }

        boolean isLeafNode() {
            return intervals != null;
        }

    }

}
