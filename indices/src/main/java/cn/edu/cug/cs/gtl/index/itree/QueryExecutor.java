package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.geom.Interval;
import cn.edu.cug.cs.gtl.index.shape.LineSegmentShape;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;

import java.util.List;
import java.util.function.Function;

/**
 * Created by ZhenwenHe on 2017/4/3.
 * 查询执行器，传入构建好的三角形树
 * this is (Is,Ie)
 * q is the input parameter Inteval （Qs,Qe）
 * <p>
 * Equals Query: Is = Qs and Ie = Qe.
 * Starts Query: Is = Qs and Qs < Ie < Qe; as shown in Fig. 3a.
 * StartedBy Query: Is = Qs and Ie > Qe; as shown in Fig. 3b.
 * Meets Query: Is < Ie = Qs < Qe; as shown in Fig. 3c.
 * MetBy Query: Qs < Qe = Is < Ie; as shown in Fig. 3d.
 * Finishes Query: Qs < Is < Qe and Ie = Qe; as shown in Fig. 3e.
 * FinishedBy Query: Is < Qs and Ie = Qe; as shown in Fig. 3f.
 * Before Query: Is < Ie < Qs < Qe; as shown in Fig. 3a.
 * After Query: Qs < Qe < Is < Ie; as shown in Fig. 4b.
 * Overlaps Query: Is < Qs and Qs < Ie < Qe; as shown in Fig. 4c.
 * OverlappedBy Query: Qs < Is < Qe and Ie > Qe; as shown in      Fig. 4d.
 * During Query: Qs < Is < Ie < Qe; as shown in Fig. 4e.
 * Contains Query: Is < Qs < Qe < Ie; as shown in Fig. 4f.
 */
public class QueryExecutor<T extends Interval, TREE extends BaseTriangleTree<T>> {
    private static final long serialVersionUID = 1L;

    TREE tree;

    public QueryExecutor(TREE tree) {
        this.tree = tree;
    }

    public int equals(T q, Function<T, Boolean> f) {
        PointShape s = (PointShape) tree.queryShapeGenerator.equals(q);
        List<T> list = tree.pointQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int starts(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.starts(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int startedBy(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.startedBy(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int meets(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.meets(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int metBy(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.metBy(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int finishes(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.finishes(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int finishedBy(T q, Function<T, Boolean> f) {
        LineSegmentShape s = (LineSegmentShape) tree.queryShapeGenerator.finishedBy(q);
        List<T> list = tree.lineQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int before(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.before(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int after(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.after(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int overlaps(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.overlaps(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int overlappedBy(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.overlappedBy(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int during(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.during(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int contains(T q, Function<T, Boolean> f) {
        RegionShape s = (RegionShape) tree.queryShapeGenerator.contains(q);
        List<T> list = tree.regionQuery(s);
        list.forEach(r -> f.apply(r));
        return list.size();
    }

    public int covers(T q, Function<T, Boolean> f) {
        return equals(q, f);
    }

    public int coveredBy(T q, Function<T, Boolean> f) {
        return equals(q, f);
    }
}
