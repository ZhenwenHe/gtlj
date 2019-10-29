package cn.edu.cug.gtl.decompose;

import java.util.*;

class DecompositionTree {
    private int numDims;        //几维
    private int powDimensions;  //2的n次方（n为几维）
    private Envelope total;     //最大矩形范围
    //the depth of the tree
    private int depth = 1;
    private int limitSize = 2;
    double currentTime;
    HashMap<Long, LeafNode> directory = new HashMap<>();

    DecompositionTree(int numDims, Envelope total) {
        this.numDims = numDims;
        this.powDimensions = (int) Math.pow(2.0, numDims);
        this.total = total;
//        this.limitSize =
    }

    //算法1
    //判断a是否是b的前缀,这里相同的不算前缀
    //>>1表示除以2，>>2表示除以4，>>3表示除以8，>>n即除以2的n次方
    //若传进来的就是两个一样的数，这里默认使用最后的返回false，后期提升算法需要处理这里
    boolean isPrefix(long a, long b) {
        if (a > b) return false;
        b = b >> numDims;
        while (b != 0) {
            if (b < a) return false;
            if (b == a) return true;
            b = b >> numDims;
        }
        return false;
    }

    //该编码的前缀，n代表深度为几的前缀，例如n=1，则为根的编码
    long getPrefix(final long code, int n) {
        return code >> (numDims * (calculateDepthByIdentifer(code) - n));
    }

    //在哈希表中查找是否有以该前缀为前缀的节点，
    boolean hasPrefix(final long prefix) {
        for (Map.Entry<Long, LeafNode> entry : directory.entrySet()) {
            if (isPrefix(prefix, entry.getKey()))
                return true;
        }
        return false;
    }

    //算法2
    //分解矩形
    //&按位与
    Envelope[] decompose(final Envelope p) {
        Envelope[] envelopes = new Envelope[powDimensions];
        //4个维度分解为16个
        for (int i = 0; i < powDimensions; i++) {
            envelopes[i] = decompose(p, i);
        }
        return envelopes;
    }

    Envelope decompose(final Envelope p, int index) {
        long mark = 1;
        //获取每个矩形的各个维度的范围
        Envelope e = new Envelope(numDims);
        for (int j = 0; j < numDims; j++) {
            if ((index & mark) != 0) {
                //envelopes[i].minData[j]第i个矩形的第j个维度的最小值
                e.getMinData()[j] = (p.getMinData()[j] + p.getMaxData()[j]) / 2;
                e.getMaxData()[j] = p.getMaxData()[j];
            } else {
                e.getMinData()[j] = p.getMinData()[j];
                e.getMaxData()[j] = (p.getMinData()[j] + p.getMaxData()[j]) / 2;
            }
            mark = mark << 1;
        }
        return e;
    }

    //计算一个整数标识符所处的深度
    int calculateDepthByIdentifer(long code) {
        int i = 0;
        while (code != 0) {
            code = code >> numDims;
            i++;
        }
        return i;
    }

    //算法3
    //根据给定的整数编码计算得出最小矩形
    Envelope calculateEnvelopeByIndentifer(long code) {
        Envelope envelope = null;
        if (code == 1) return total;
        Envelope to = total;
        Stack<Long> a = new Stack<>();
        //code & 0xf等同于code - (code >> numDims) << numDims，例如0X1089,则依次取出9，8，0，（1）存入栈中，但是这里不一定是Oxf.如果维度是3，则为7
        //这里code != 1 ,以为着最上层默认为1，实则应该为0；   ,存在隐患,不易扩展，有需要的话需修改
        while (code != 1) {
            a.push(code & (powDimensions - 1));
            code = code >> numDims;
        }
        while (!a.empty()) {
            envelope = decompose(to, a.peek().intValue());
            to = envelope;
            a.pop();
        }
        return envelope;
    }

    //算法4
    //计算一个矩形或点在树的最大深度处所属的节点的整数代码
    //该算法可以处理两种类型的输入数据，包含矩形和点。
    //想法：太慢了，反向查找
    long calculateIdentiferByEnvelopeOrVertex(final GeoObject r, int depth) {
        if (!total.encloses(r)) {
            return -1;  //或者返回0
        }
        Envelope to = total;
        Envelope e2d;
        int i = 1;
        long s = 1;
        while (i < depth) {
            for (int j = 0; j < powDimensions; j++) {
                //分解树，若分解树的范围包含目标范围，则添加上对应的编号，右移并继续分解
                e2d = decompose(to, j);
                if (e2d.encloses(r)) {
                    to = e2d;
                    s = s << numDims;
                    s += j;
                    break;
                }
            }
            i++;
        }
        return s;
    }

    //算法5
    boolean insertPoint(GeoObject pi) {
//        struct Statistics {
//            uint64_t splitTimes;
//            uint64_t nodeNumber;
//            uint64_t objectNumber;
//            Statistics(){
//                splitTimes = 0;
//                nodeNumber = 0;
//                objectNumber = 0;
//            }
//        };

        //时间未处理
//        if (currentTime < pigetLastTime()) {
//            _currentTime = pi->getLastTime();
//            _currentEntries[pi->getIdentifier()] = pi;
//        }

        long code = calculateIdentiferByEnvelopeOrVertex(pi, depth);
        long oldCode = code;

        //哈希表中依次向上查找该编码，如果存在的话，
        while (code != 0) {
            if (directory.containsKey(code)) {
                //判断该节点的点数是否超过限制，如果没有超过限制的话，直接添加进去，超过的话添加进去并调用算法6拆分节点
                if (directory.get(code).entrySize() < limitSize) {
                    directory.get(code).push((Vertex) pi);
//                    _statistics.objectNumber++;
                    return true;
                } else {
//                    LeafNode leafNode = new LeafNode(code);
//                    leafNode.setEntryArray(directory.get(code).getEntryArray());
                    LeafNode leafNode1 = directory.get(code);
                    directory.remove(code);
                    //分解后添加进去
                    splitNode(leafNode1, (Vertex) pi);
//                    split_for_point(split_node, pi);
                    return true;
                }
            } else code = code >> numDims;
        }

        //???????????
        //在hash表中找不到包含其前缀的节点的话
        int i = 1;
        code = oldCode;
        int dep = calculateDepthByIdentifer(code);  //深度
        long prefix;
        do {
            prefix = getPrefix(code, i);
            if (hasPrefix(prefix)) {
                i++;
            } else
                break;
        } while (i <= dep);
//        if (i != 1) {
//            code = code >> ((i - 1) * numDims);
//        }
        LeafNode li = new LeafNode(prefix);
        li.push((Vertex) pi);
        directory.put(prefix, li);
//        _statistics.objectNumber++;
        return false;
    }

    private void insertEnvelope(Envelope pi) {
//        if (_currentTime<pi->getLastTime())
//        {
//            _currentTime = pi->getLastTime();
//            _currentEntries[pi->getIdentifier()] = pi;
//        }


//#ifdef _GTL_DEBUG_
//        envelope e = pi->getEnvelope();
//        //assert(e.isValid());
//#endif //_GTL_DEBUG_

//        entry_ptr_array * pat = 0;
//        long code = calculateEnvelopeIdentifer(pi, _depth);
//        uint64_t old_code = code;
//        map_iterator it;
//        leaf_node_type split_node;
//        //1.?ì2écodeμ??ùóD?°×o?°??±?éí?ú_directory?Dê?·?′??ú￡?è?1?′??ú?ò2?è?
//        while (code != 0){
//            it = _directory.find(code);
//            if (it != _directory.end())
//            {
//                if (it->second.size()<_blockingFactor)
//                {
//                    it->second.push_back(pi);
//                    return true;
//                }
//                else
//                {
//                    split_node.identifier = it->first;
//                    split_node.objects = it->second.objects;
//                    it->second.objects = 0;
//                    _directory.erase(it);
//                    split_for_envelope(split_node, pi);
//                    return true;
//                }
//            }
//            else
//                code = code >> NUMDIMS;
//        }

//        //2.?D??ê?·?′??úò?code?a?°×oμ??úμ?′??ú￡?è?1?óD￡??òμ??ùóDò?code?a?°×oμ??úμ?￡?
//        //??è?óú2?è???D???μt·??§×?′óμ??úμ?2?è?￡?è?1?Dèòa·??a?ò·??a
//        code = old_code;
//        envelope env_it;
//        for (map_iterator it = _directory.begin(); it != _directory.end(); it++)
//        {
//            if (isPrefix(code, it->first))
//            {
//                calculateEnvelope(it->first, env_it);
//                if (test(env_it, pi) != 0)
//                {
//                    if (it->second.size()<_blockingFactor)
//                    {
//                        it->second.push_back(pi);
//                        return true;
//                    }
//                    else
//                    {
//                        split_node.identifier = it->first;
//                        split_node.objects = it->second.objects;
//                        it->second.objects = 0;
//                        _directory.erase(it);
//                        split_for_envelope(split_node, pi);
//                        return true;
//                    }
//                }
//            }
//        }
//
//        //3.Dèòaìí?óD?μ?ò?×ó?úμ?
//        int i = 1;
//        code = old_code;
//        int dep = calculateIdentiferDepth(code);
//        uint64_t  prefix;
//        do{
//            prefix = getPrefix(code, i);
//            if (hasPrefix(prefix))
//            {
//                i++;
//            }
//            else
//                break;
//        } while (i <= dep);
//        _directory[prefix].push_back(pi);
//        return true;
    }

    //    算法6
    //输入：叶子节点_node
    //_mbr为_node在树的最深处所表示的最小矩形
    //_code为叶子节点的唯一编码
    //使用算法2分解该矩形为2^n个子矩形
    //i = [0，2^n]
//    for(int i = 0; i < 2^n; i ++)
    //遍历该节点的所有空间对象，判断是否被访问过，如果访问过则移除，如果某个对象包含在某个矩形内，则创建一个节点对象，将该节点唯一编码设置为_code<<n + i;将空间对象添加到其中，将节点加入hash表中
    //如果所有空间对象被添加到同一个矩形叶子节点中，则继续调用该算法
    void splitNode(LeafNode li, Vertex pi) {
//        _statistics.splitTimes++;
        long code = li.getIdentifer();
        ArrayList<Vertex> vertices = li.getEntryArray();
        if (pi != null) vertices.add(pi);
        Envelope to = calculateEnvelopeByIndentifer(code);
        Envelope[] splitEnvelope = decompose(to);
        LeafNode[] leafNodes = new LeafNode[powDimensions];
        for (Vertex vertex : vertices) {
            for (int k = 0; k < powDimensions; k++) {
                if (splitEnvelope[k].encloses(vertex)) {
                    if (leafNodes[k] == null) {
                        leafNodes[k] = new LeafNode((code << numDims) + k);
                    }
                    leafNodes[k].push(vertex);
                    break;
                }
            }
        }

        boolean index = true;   //用来判断后面是否要进行递归，如果递归我们不计算深度，在后面递归中进行计算
        for (LeafNode s : leafNodes) {
            if (s != null) {
                if (s.entrySize() > limitSize) {
                    splitNode(s, null);
                    index = false;
                } else {
                    directory.put(s.getIdentifer(), s);
                }
            }
        }
//        _statistics.objectNumber++;
        if (index) {
            depth = Math.max(calculateDepthByIdentifer(code << numDims), depth);
        }
    }

//    splitNode

//    void search

//    算法7,窗口(范围)查询
//    输入:_queryRectangle要查询的矩形  _result
//    输出:移动点按他们的轨迹(TRJID)和时间排序
//    使用算法4计算要查询的矩形在D树最大深度的编码,让_code为该编码,添加hash表中编码是_code的前缀的叶节点到数列_list中
//    当以整数编码_code为编码的叶节点在hash表中存在的话,添加到list中,这里应该是以_code为前缀的编码
//    并行遍历_list中的所有叶节点,假设当前的叶节点为_node  for(Node _node:list){
//      让_notecode为_node的整数编码
//      如果_nodecode是等于_code或者是_code的前缀,遍历该node里面里的所有点,...
//          将在_queryRectangle范围内的所有移动点添加到_result中,将_result按轨迹(TRJID)和时间进行分类,返回结果;
//      如果_code是_nodecode的前缀,调用算法3,让_rect为_node所代表的矩形,...
//          如果_queryRectangle包含_rect,添加_node的所有对象到_result中
//          否则的话,如果_queryRectangle和_rect重叠相交,遍历该节点中的所有对象,如果对象和_queryRectangle重叠,则将对象添加到_resul
//    将_result按轨迹(TRJID)和时间进行分类


    //    算法8:删除操作
    boolean remove(Vertex p) {
        long code = calculateIdentiferByEnvelopeOrVertex(p, depth);
        while (code != 0) {
            if (directory.containsKey(code)) {
                LeafNode leafNode = directory.get(code);
                for (Vertex a : leafNode.getEntryArray()) {
                    if (a.equals(p)) {
                        leafNode.getEntryArray().remove(a);
                        if (leafNode.getEntryArray().size() == 0) directory.remove(leafNode.getIdentifer(), leafNode);
                        return true;
                    }
                }
            } else code = code >> numDims;
        }
        return false;
    }

    boolean extend() {
//        	void insertExternalObject(entry_ptr p)
        return false;
    }
}
