package cn.edu.cug.cs.gtl.ml.classification;

import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;
import cn.edu.cug.cs.gtl.ml.dataset.DataSet;
import cn.edu.cug.cs.gtl.ml.dataset.TestSet;
import cn.edu.cug.cs.gtl.ml.distances.DistanceMetrics;


import java.util.*;

public class KNNClassifier<S, L> extends DefaultClassifier<S, L> {

    private int m_kNN;

    protected KNNClassifier() {
    }

    public KNNClassifier(DataSet<S, L> trainSet, DataSet<S, L> testSet, DistanceMetrics<S> distanceMetrics) {
        super(trainSet, testSet, distanceMetrics);
    }

    /**
     * 设计一个类里包含一个链表，用来存储一个测试数据的K个邻居
     */
    private class NeighborNode {

        private double m_Distance;
        private L m_Label;
        private NeighborNode m_Next;

        public NeighborNode(double distance, L label, NeighborNode next) {
            m_Distance = distance;
            m_Label = label;
            m_Next = next;
        }

        public NeighborNode(double distance, L label) {
            this(distance, label, null);
        }

    }

    /**
     * m_First;m_Last;k个邻居中m_First是最近的，m_Last是距离最远的
     */
    private class NeighborList {

        private NeighborNode m_First;
        private NeighborNode m_Last;
        private int m_Length = 1;

        public NeighborList(int length) {
            m_Length = length;
        }

        public boolean isEmpty() {
            return (m_First == null);
        }

        public int currentLength() {
            int i = 0;
            NeighborNode current = m_First;
            while (current != null) {
                i++;
                current = current.m_Next;
            }
            return i;
        }

        /**
         * 把距离和标签一同插入进去，通过距离之间的比较，最后留下距离最小的K个值以及标签形成链表
         */

        public void insertSorted(double distance, L label) {
            if (isEmpty()) {
                m_First = m_Last = new NeighborNode(distance, label);
            } else {
                NeighborNode current = m_First;
                if (distance < m_First.m_Distance) {
                    m_First = new NeighborNode(distance, label, m_First);
                } else {
                    for (; (current.m_Next != null) &&
                            (current.m_Next.m_Distance < distance);
                         current = current.m_Next)
                        ;
                    current.m_Next = new NeighborNode(distance, label, current.m_Next);
                    if (current.equals(m_Last)) {
                        m_Last = current.m_Next;
                    }
                }
                //找到K个邻居
                int valcount = 0;
                for (current = m_First; current.m_Next != null; current = current.m_Next) {
                    valcount++;
                    if ((valcount >= m_Length) && (current.m_Distance != current.m_Next.m_Distance)) {
                        m_Last = current;
                        current.m_Next = null;
                        break;
                    }
                }

            }

        }

        //如果在第K个位置，距离有多个相同的邻居则将这些都保存下来
        public void pruneToK(int k) {
            if ((isEmpty())) {
                return;
            }
            if (k < 1) {
                k = 1;
            }
            int currentK = 0;
            double currentDist = m_First.m_Distance;
            NeighborNode current = m_First;
            for (; current.m_Next != null; current = current.m_Next) {
                currentK++;
                currentDist = current.m_Distance;
                if ((currentK >= k) && (currentDist != current.m_Next.m_Distance)) {
                    m_Last = current;
                    current.m_Next = null;
                    break;
                }
            }
        }

    }

    public L computeDistribution(NeighborList neighborList) {
        HashMap<L, Integer> hashMap = new HashMap<L, Integer>();
        Set<L> labels = hashMap.keySet();
        int max_value = 0;
        L proLabel = null;
        NeighborNode current = neighborList.m_First;
        for (; current.m_Next != null; current = current.m_Next) {
            L label = current.m_Label;
            if (labels.contains(label)) {
                Integer number = hashMap.get(label);
                number++;
                hashMap.put(label, number);
            } else {
                hashMap.put(label, 1);
            }
        }
        for (Map.Entry<L, Integer> entry : hashMap.entrySet()) {
            if (entry.getValue() > max_value) {
                max_value = entry.getValue();
                proLabel = entry.getKey();
            }
        }
        return proLabel;
    }


    @Override
    public Iterable<L> predict(Iterable<S> testSamples) {
        long trainDataLen = this.trainSet.size();
        List<L> labelList = new ArrayList<>();
        m_kNN = 10;
        NeighborList neighborList = new NeighborList(m_kNN);
        for (S i : testSamples) {
            for (int j = 0; j < trainDataLen; ++j) {
                double tempDis = this.distanceMetrics.distance(i, this.trainSet.getSample(j));
                if (neighborList.isEmpty() || j < m_kNN || tempDis <= neighborList.m_Last.m_Distance) {
                    neighborList.insertSorted(tempDis, trainSet.getLabel(j));
                }
            }
            labelList.add(computeDistribution(neighborList));
        }
        return labelList;
    }
}
