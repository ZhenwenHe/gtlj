package cn.edu.cug.cs.gtl.index.itree;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.geom.*;
import cn.edu.cug.cs.gtl.geom.Vector;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.jts.geom.Geom2DSuits;
import cn.edu.cug.cs.gtl.index.shape.LineSegmentShape;
import cn.edu.cug.cs.gtl.index.shape.PointShape;
import cn.edu.cug.cs.gtl.index.shape.RegionShape;
import cn.edu.cug.cs.gtl.index.shape.TriangleShape;
import cn.edu.cug.cs.gtl.io.storage.StorageManager;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TD-Tree implements the TI-Tree's methods in our work
 * when the data range is given firstly
 * and it will change never after that.
 * That is the data range does not vary.
 *
 * @param <T>
 */
public class TDTree<T extends Interval> extends BaseTriangleTree<T> {

    private static final long serialVersionUID = 1L;

    TriangleEncoder triangleEncoder;
    StorageManager storageManager;
    Map<String, Identifier> leafInfos;
    ArrayList<String> emptyNodes;
    //spark variables
    transient JavaSparkContext sparkContext;
    transient JavaRDD<Pair<String, Identifier>> sparkRDD;
    AtomicBoolean constructedRDD; //whether the RDD is constructed, threadsafe

    public TDTree(TriangleShape root, int leafCapacity, StorageManager sm, JavaSparkContext sparkContext) {
        super(root, leafCapacity);
        triangleEncoder = new TriangleEncoder((TriangleShape) this.baseTriangle);
        storageManager = sm;
        leafInfos = new HashMap<>();

        emptyNodes = new ArrayList<String>();
        emptyNodes.add("1");
        sparkRDD = null;
        constructedRDD = new AtomicBoolean(false);
        this.sparkContext = sparkContext;
    }

    public JavaRDD<Pair<String, Identifier>> constructRDD() {
        if (constructedRDD.get() == false) {
            List<Pair<String, Identifier>> ls = new ArrayList<>(leafInfos.entrySet().size());
            for (Map.Entry<String, Identifier> me : leafInfos.entrySet())
                ls.add(new Pair<String, Identifier>(me.getKey(), me.getValue()));
            sparkRDD = sparkContext.parallelize(ls);
            constructedRDD.set(true);
            return sparkRDD;
        } else
            return sparkRDD;

    }

    @Override
    public List<T> pointQuery(final PointShape ps) {
        constructRDD();
        final TriangleEncoder te = triangleEncoder;
        JavaRDD<T> result = sparkRDD
                .filter(r -> {
                    //if(r.getValue().longValue()!=-1L) return false;
                    TriangleShape t = te.parse(r.getKey());
                    if (test(t, ps) != 0)
                        return true;
                    else
                        return false;
                })
                .flatMap(r -> {
                    LeafNode ln = readNode(r.getValue());
                    List<Interval> li = new ArrayList<>();
                    for (int i = 0; i < ln.size; ++i) {
                        if (ps.getX() == ln.intervals[i].getLowerBound() &&
                                ps.getY() == ln.intervals[i].getUpperBound())
                            li.add(ln.intervals[i]);
                    }
                    return li.iterator();
                })
                .map(r -> (T) r);
        return result.collect();
    }

    @Override
    public List<T> lineQuery(final LineSegmentShape lsp) {
        constructRDD();
        final TriangleEncoder te = triangleEncoder;
        final LineSegment ls = (LineSegment) lsp;
        JavaRDD<T> result = sparkRDD
                .filter(r -> {
                    //if(r.getValue().longValue()!=-1L) return false;
                    Triangle t = te.parse(r.getKey());
                    return Geom2DSuits.intersects(t, ls);
                })
                .flatMap(r -> {
                    LeafNode ln = readNode(r.getValue());
                    List<Interval> li = new ArrayList<>();
                    // point on line test
                    for (int i = 0; i < ln.size; ++i) {
                        if (Geom2DSuits.pointInLineSegment(new Vector2D(
                                ln.intervals[i].getLowerBound(),
                                ln.intervals[i].getUpperBound()), ls))
                            li.add(ln.intervals[i]);
                    }
                    return li.iterator();
                })
                .map(r -> (T) r);
        return result.collect();
    }

    @Override
    public List<T> regionQuery(final RegionShape rs) {
        constructRDD();
        final TriangleEncoder te = triangleEncoder;
        final Envelope e2d = rs.getMBR().flap();
        JavaRDD<T> result = sparkRDD
                .filter(r -> {
                    //if(r.getValue().longValue()!=-1L) return false;
                    Triangle t = te.parse(r.getKey());
                    return Geom2DSuits.intersects(e2d, t);
                })
                .flatMap(r -> {
                    LeafNode ln = readNode(r.getValue());
                    List<Interval> li = new ArrayList<>();
                    Triangle t = te.parse(r.getKey());
                    if (Geom2DSuits.contains(e2d, t)) {
                        List<Interval> l = new ArrayList<>();
                        for (int i = 0; i < ln.size; ++i)
                            l.add(ln.intervals[i]);
                        li.addAll(l);
                    } else {
                        List<Interval> l = new ArrayList<>();
                        for (int i = 0; i < ln.size; ++i)
                            if (e2d.contains(Vector.create(
                                    ln.intervals[i].getLowerBound(),
                                    ln.intervals[i].getUpperBound())))
                                l.add(ln.intervals[i]);
                        li.addAll(l);
                    }
                    return li.iterator();
                })
                .map(r -> (T) r);
        return result.collect();
    }

    @Override
    public boolean insert(T i) {
        Pair<String, LeafNode> ln = chooseNode(i);
        if (ln == null) return false;
        if (ln.first() == null || ln.second() == null)
            return false;

        int r = ln.getSecond().insert((Interval) i);
        if (r == 1) {//成功插入
            Identifier page = null;
            if (ln.second().size == 1)
                page = Identifier.create(-1);
            else
                page = leafInfos.get(ln.first());

            //rewrite the node to page
            page = writeNode(ln.second(), page);
            leafInfos.put(ln.first(), page);
            constructedRDD.set(false);
            return true;
        } else if (r == 0)//不在该节点范围内
            return false;
        else {//分裂节点并插入
            boolean b = splitAndInsert(i, ln);
            constructedRDD.set(false);
            return b;
//            Identifier page = leafInfos.get(ln.first());// get the old page of the node
//            List<LeafNode> lns=null;
//            Pair<String,LeafNode> leftNode= new Pair<String,LeafNode>(null,null);
//            Pair<String,LeafNode> rightNode= new Pair<String,LeafNode>(null,null);
//            boolean repeatFlag =false;
//            int splitTimes=0;
//            do{
//                lns = ln.second().split();
//                leftNode.setValue(lns.get(0));
//                leftNode.setKey(ln.getKey()+"0");
//                rightNode.setValue(lns.get(1));
//                rightNode.setKey(ln.getKey()+"1");
//                splitTimes++;
//                int s = leftNode.second().size;
//                if(s>0 && s< leafNodeCapacity){//分裂成功
//                    if(r==-1)//should be added to the left node
//                        leftNode.second().insert(i);
//                    else//should be added to the right node
//                        rightNode.second().insert(i);
//                    //1. write the left node to the page
//                    writeNode(leftNode.second(),page);
//                    //2. remove the ln from the scene, and add the leftNode info to scene
//                    leafInfos.remove(ln.first());
//                    leafInfos.put(leftNode.first(),page);
//                    //3. write the right node to a new page,and add the rightNode info to scene
//                    page.reset(-1);
//                    page = writeNode(rightNode.second(),page);
//                    leafInfos.put(rightNode.first(),page);
//                    constructedRDD.set(false);
//                    return true;
//                }
//                else{//还需要继续分裂
//                    //1. remove the ln from the scene, and add the leftNode info to scene
//                    leafInfos.remove(ln.first());
//
//                    if(s==0) {// all in rightNode
//                        ln = rightNode;
//                        writeNode(rightNode.second(),page);
//                        //记录空节点，不用写入外存
//                        leafInfos.put(leftNode.first(),Identifier.create(-1L));
//                        leafInfos.put(rightNode.first(),page);
//                    }
//                    else{//all in leftNode
//                        ln=leftNode;
//                        writeNode(leftNode.second(),page);
//                        leafInfos.put(leftNode.first(),page);
//                        //记录空节点，不用写入外存
//                        leafInfos.put(rightNode.first(),Identifier.create(-1L));
//                    }
//                    repeatFlag=true;
//                    if(splitTimes==100)
//                        break;
//                }
//            }while (repeatFlag);
        }
    }

    /**
     * t located in the triangle of ln,
     * split the node and insert t
     *
     * @param t
     * @param ln
     * @return
     */
    boolean splitAndInsert(T t, Pair<String, LeafNode> ln) {

        int splitTimes = 0;

        Interval[] intervals = ln.second().getIntervals();
        assert intervals.length == leafNodeCapacity;
        Interval[] leftIntervals = new Interval[leafNodeCapacity];
        Interval[] rightIntervals = new Interval[leafNodeCapacity];
        Interval[] tempIntervals = null;

        int leftIntervalNumber = 0;
        int rightIntervalNumber = 0;
        TriangleShape lnTriangle = ln.second().triangle;
        String lnString = ln.first();
        TriangleShape leftTriangle = null;
        TriangleShape rightTriangle = null;
        String leftString = null;
        String rightString = null;

        while (leftIntervalNumber == 0 || rightIntervalNumber == 0) {

            if (splitTimes == 10000) {
                System.out.println("splitTimes=" + splitTimes);
                System.out.println("you should increase the parameter leafNodeCapaity value!!!");
                assert false;
            } else
                splitTimes++;

            leftTriangle = lnTriangle.leftTriangle();
            leftIntervalNumber = 0;
            rightIntervalNumber = 0;

            for (Interval i : intervals) {
                if (Geom2DSuits.contains(leftTriangle, i.getLowerBound(), i.getUpperBound())) {
                    leftIntervals[leftIntervalNumber] = i;
                    leftIntervalNumber++;
                } else {
                    rightIntervals[rightIntervalNumber] = i;
                    rightIntervalNumber++;
                }
            }

            if (leftIntervalNumber == 0) {//all located at left side
                rightTriangle = lnTriangle.rightTriangle();
                rightString = lnString + "1";
                leftString = lnString + "0";
                emptyNodes.add(leftString);
                lnTriangle = rightTriangle;
                lnString = rightString;

                tempIntervals = intervals;
                intervals = rightIntervals;
                rightIntervals = tempIntervals;
            } else if (rightIntervalNumber == 0) {//all located at right side
                rightString = lnString + "1";
                leftString = lnString + "0";
                emptyNodes.add(rightString);
                lnTriangle = leftTriangle;
                lnString = leftString;

                tempIntervals = intervals;
                intervals = leftIntervals;
                leftIntervals = tempIntervals;
            } else { //spit successfully

                leftString = lnString + "0";
                LeafNode leftNode = new LeafNode();
                leftNode.intervals = leftIntervals;
                leftNode.size = leftIntervalNumber;
                leftNode.triangle = leftTriangle;

                rightString = lnString + "1";
                LeafNode rightNode = new LeafNode();
                rightTriangle = lnTriangle.rightTriangle();
                rightNode.triangle = rightTriangle;
                rightNode.size = rightIntervalNumber;
                rightNode.intervals = rightIntervals;

                if (leftNode.insert(t) != 1) {
                    rightNode.insert(t);
                }

                Identifier lnPage = leafInfos.remove(ln.first());
                writeNode(leftNode, lnPage);
                Identifier rightPage = Identifier.create(-1L);
                writeNode(rightNode, rightPage);

                leafInfos.put(leftString, lnPage);
                leafInfos.put(rightString, rightPage);
                return true;
            }
        }
        return false;
    }

    Pair<String, LeafNode> chooseNode(T i) {
        //1. scan the leafInfos
        Set<Map.Entry<String, Identifier>> s = leafInfos.entrySet();
        Identifier page = null;
        LeafNode ln = null;
        for (Map.Entry<String, Identifier> p : s) {
            TriangleShape t = triangleEncoder.parse(p.getKey());
            if (Geom2DSuits.contains(t, i.getLowerBound(), i.getUpperBound())) {
                ln = readNode(p.getValue());
                return new Pair<String, LeafNode>(p.getKey(), ln);
            }

//            int r = test(t,(Interval)i);
//            if(r==0)
//                continue;
//            else{
//                 page = p.getValue();
//                 if(page.longValue()!=-1)
//                     ln = readNode(page);
//                 else{
//                     ln = new LeafNode();
//                 }
//                 return  new Pair<String,LeafNode>(p.getKey(),ln);
//            }
        }

        //2. there is no leaf node that contains i, scan the emptyNodes only when the insertion is running
        int k = 0;
        for (String str : emptyNodes) {
            TriangleShape t = triangleEncoder.parse(str);
            if (Geom2DSuits.contains(t, i.getLowerBound(), i.getUpperBound())) {
                String sss = emptyNodes.remove(k);
                ln = new LeafNode();
                ln.triangle = t;
                return new Pair<String, LeafNode>(sss, ln);
            }
            ++k;
        }

        //3. error
        assert false;
        return new Pair<String, LeafNode>(null, null);
    }

    /**
     * @param leafNode
     * @return
     */
    Identifier writeNode(LeafNode leafNode, Identifier page) {
        try {
            byte[] bs = leafNode.storeToByteArray();
            this.storageManager.storeByteArray(page, bs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    /**
     * @param page
     * @return
     */
    LeafNode readNode(Identifier page) {
        try {
            byte[] bs = this.storageManager.loadByteArray(page);
            LeafNode ln = new LeafNode();
            ln.loadFromByteArray(bs);
            return ln;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param i
     * @return
     */
    public boolean delete(T i) {
        Pair<String, LeafNode> ln = chooseNode(i);
        if (ln == null) return false;
        if (ln.first() == null || ln.second() == null)
            return false;

        int r = ln.getSecond().delete((Interval) i);
        if (r == 1) {//成功删除
            if (ln.second().size == 0) {//如果叶子节点为空
                //回收该节点和页面
                emptyNodes.add(ln.first());
                Identifier page = leafInfos.get(ln.first());
                try {
                    if (page.longValue() != -1L)
                        storageManager.deleteByteArray(page);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {//重写该节点
                Identifier page = null;
                if (ln.second().size == 1)
                    page = Identifier.create(-1);
                else
                    page = leafInfos.get(ln.first());

                //rewrite the node to page
                page = writeNode(ln.second(), page);
                leafInfos.put(ln.first(), page);
            }

            constructedRDD.set(false);
            return true;
        }
        return false;
    }

    /**
     * 叶子节点类
     */
    class LeafNode implements Serializable {

        TriangleShape triangle;
        Interval[] intervals;
        int size;

        public LeafNode() {
            intervals = new Interval[leafNodeCapacity];
            triangle = (TriangleShape) baseTriangle.clone();
            size = 0;
        }

        public LeafNode(String identifier, Interval[] intervals, int size) {
            triangle = (TriangleShape) triangleEncoder.parse(identifier);
            this.intervals = new Interval[leafNodeCapacity];
            this.size = size > leafNodeCapacity ? leafNodeCapacity : size;
            for (int i = 0; i < this.size; ++i)
                this.intervals[i] = intervals[i];
        }

        public LeafNode(TriangleShape t, Interval[] intervals, int size) {
            triangle = (TriangleShape) t.clone();
            this.intervals = new Interval[leafNodeCapacity];
            this.size = size > leafNodeCapacity ? leafNodeCapacity : size;
            for (int i = 0; i < this.size; ++i)
                this.intervals[i] = intervals[i];
        }

        public Interval[] getIntervals() {
            Interval[] ints = new Interval[size];
            for (int i = 0; i < size; ++i)
                ints[i] = this.intervals[i];
            return ints;
        }

        public List<LeafNode> split() {
            LeafNode ln = new LeafNode();
            LeafNode rn = new LeafNode();

            TriangleShape lnTriangle = triangle.leftTriangle();
            int t = 0;
            for (int i = 0; i < size; ++i) {
                t = test(lnTriangle, intervals[i]);
                if (t == 0) {//add the interval to right node
                    rn.intervals[rn.size] = (Interval) intervals[i].clone();
                    rn.size++;
                } else {
                    ln.intervals[ln.size] = (Interval) intervals[i].clone();
                    ln.size++;
                }
            }
            List<LeafNode> leafNodes = new ArrayList<>();
            leafNodes.add(ln);
            leafNodes.add(rn);
            return leafNodes;
        }

        /**
         * @param i
         * @return 如果返回1，表示插入成功
         * 如果返回0，表示在三角形的外面；插入失败
         * 如果返回-1，表示在基准三角形的左子三角形里面或边上，但是节点需要分裂，插入失败
         * 如果返回-2，表示在基准三角形的右子三角形里面或边上；但是节点需要分裂，插入失败
         */
        public int insert(Interval i) {
            int r = test(triangle, i);
            /*
             * 如果返回0，表示在三角形的外面；
             * 如果返回1，表示在基准三角形的左子三角形里面或边上
             * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
             */
            if (r == 0)
                return 0;
            if (size < leafNodeCapacity) {
                intervals[size] = i;
                size++;
                return 1;
            } else {//need to split
                return -r;
            }
        }

        /**
         * @param i
         * @return *如果返回1，表示删除成功
         * 如果返回0，表示在三角形的外面；删除失败
         * 如果返回-1，表示在三角形里面，但是没有找到相等的Interval
         */
        public int delete(Interval i) {
            if (size == 0) return -1;
            int r = test(triangle, i);
            /*
             * 如果返回0，表示在三角形的外面；
             * 如果返回1，表示在基准三角形的左子三角形里面或边上
             * 如果返回2，则表示在基准三角形的右子三角形里面或边上；
             */
            if (r == 0)
                return 0;
            for (int it = 0; it < size; ++it) {
                if (i.equals(intervals[it])) {
                    for (int k = it; k < size - 1; ++k)
                        intervals[k] = intervals[k + 1];
                    size -= 1;
                    return 1;
                }
            }
            return -1;
        }

        @Override
        public Object clone() {
            LeafNode ln = new LeafNode(this.triangle, this.intervals, this.size);
            for (int i = 0; i < this.size; ++i)
                this.intervals[i] = (Interval) intervals[i].clone();
            return ln;
        }

        @Override
        public void copyFrom(Object leafNode) {
            LeafNode ln = (LeafNode) leafNode;
            this.triangle = (TriangleShape) ln.triangle.clone();
            this.size = ln.size;
            for (int i = 0; i < this.size; ++i)
                this.intervals[i] = (Interval) ln.intervals[i].clone();
        }

        @Override
        public boolean load(DataInput in) throws IOException {
            try {
                //由于不知道triangle的具体类型，将其反序列化成对象
                {
                    int len = in.readInt();
                    byte[] bs = new byte[len];
                    in.readFully(bs, 0, len);
                    ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    triangle = (TriangleShape) ois.readObject();
                    ois.close();
                }
                size = in.readInt();
                if (size == 0) return true;
                //由于不知道Interval的具体类型，将其反序列化成对象
                {
                    int len = in.readInt();
                    byte[] bs = new byte[len];
                    in.readFully(bs, 0, len);
                    ByteArrayInputStream bais = new ByteArrayInputStream(bs);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    for (int i = 0; i < size; ++i) {
                        intervals[i] = (Interval) ois.readObject();
                    }
                    ois.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean store(DataOutput out) throws IOException {
            try {

                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(triangle);
                    byte[] bs = baos.toByteArray();
                    out.writeInt(bs.length);
                    out.write(bs, 0, bs.length);
                    oos.close();
                }
                out.writeInt(size);
                if (size == 0) return true;
                //由于不知道Interval的具体类型，将其序列化成对象
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    for (int i = 0; i < size; ++i) {
                        oos.writeObject(intervals[i]);
                    }
                    byte[] bs = baos.toByteArray();
                    out.writeInt(bs.length);
                    out.write(bs, 0, bs.length);
                    oos.close();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public long getByteArraySize() {
            long s = triangle.getByteArraySize() + 4;
            for (int i = 0; i < size; ++i)
                s += intervals[i].getByteArraySize();
            return s;
        }
    }
}
