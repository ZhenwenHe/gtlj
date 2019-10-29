package cn.edu.cug.gtl.decompose;

import org.junit.Before;
import org.junit.Test;


public class DecompositionTreeTest {
    DecompositionTree decompositionTree;

    @Before
    public void setUp() throws Exception {
        double p[] = {2, 6, 14, 20};
        double q[] = {4, 8, 16, 30};
        Envelope envelope = new Envelope(4, p, q);
        decompositionTree = new DecompositionTree(4, envelope);
    }

    //修改函数后测试成功
    @Test
    public void isPrefix() {
        //判断a是否是b的前缀
        System.out.println(decompositionTree.isPrefix(0x1089, 0x108)); //true
        System.out.println(decompositionTree.isPrefix(0x108, 0x1089)); //true
        System.out.println(decompositionTree.isPrefix(0, 0x108));      //预期应该是false，结果为true    //这里修改了函数
        System.out.println(decompositionTree.isPrefix(1, 0x108));      //true
    }

    //测试成功
    @Test
    public void getPrefix() {
        //该编码的前缀，后面传入的n代表深度为几的前缀，例如n=1，则为根的编码
        long a = 0x1096;
        System.out.println(decompositionTree.getPrefix(a, 1));   //0x1 = 1
        System.out.println(decompositionTree.getPrefix(a, 2));   //0x10 = 16
        System.out.println(decompositionTree.getPrefix(a, 3));   //0x109 = 265
        System.out.println(decompositionTree.getPrefix(a, 4));   //0x1096 = 4246
    }

    //测试成功
    @Test
    public void hasPrefix() {
        LeafNode leafNode = new LeafNode(0x123);
        LeafNode leafNode1 = new LeafNode(0x456);
        LeafNode leafNode2 = new LeafNode(0x78f);
        decompositionTree.directory.put((long) 0x123, leafNode);
        decompositionTree.directory.put((long) 0x456, leafNode1);
        decompositionTree.directory.put((long) 0x78f, leafNode2);
        System.out.println(decompositionTree.hasPrefix(0x12));
        System.out.println(decompositionTree.hasPrefix(0x34));
        System.out.println(decompositionTree.hasPrefix(0x78));
        System.out.println(decompositionTree.hasPrefix(0x4));

    }

    //测试成功
    //分解树，获取他的所有子矩形
    @Test
    public void decompose() {
        double p[] = {2, 6, 14, 20};
        double q[] = {4, 8, 16, 30};
        Envelope envelope = new Envelope(4, p, q);
        Envelope[] s = decompositionTree.decompose(envelope);
        for (int i = 0; i < s.length; i++) {
            System.out.print("de_envelope_" + String.format("%02d", i) + ": minData:{");
            for (int j = 0; j < s[i].getMinData().length - 1; j++) {
                System.out.print(s[i].getMinData()[j] + ", ");
            }
            System.out.print(s[i].getMinData()[s[i].getMinData().length - 1]);
            System.out.print("},maxData:{");
            for (int j = 0; j < s[i].getMaxData().length - 1; j++) {
                System.out.print(s[i].getMaxData()[j] + ", ");
            }
            System.out.print(s[i].getMaxData()[s[i].getMaxData().length - 1]);
            System.out.println("}");
        }
    }

    //测试成功
    //分解树，得到分解矩形中得某个，矩形编号从0开始
    @Test
    public void decompose2() {
        //结果应是   de_envelope: minData:{3.0, 6.0, 15.0, 20.0},maxData:{4.0, 7.0, 16.0, 25.0}
        double p[] = {2, 6, 14, 20};
        double q[] = {4, 8, 16, 30};
        Envelope envelope = new Envelope(4, p, q);
        Envelope s = decompositionTree.decompose(envelope, 5);
        System.out.print("de_envelope" + ": minData:{");
        for (int j = 0; j < s.getMinData().length - 1; j++) {
            System.out.print(s.getMinData()[j] + ", ");
        }
        System.out.print(s.getMinData()[s.getMinData().length - 1]);
        System.out.print("},maxData:{");
        for (int j = 0; j < s.getMaxData().length - 1; j++) {
            System.out.print(s.getMaxData()[j] + ", ");
        }
        System.out.print(s.getMaxData()[s.getMaxData().length - 1]);
        System.out.println("}");
    }

    //测试成功
    //计算一个整数标识符所处的深度
    @Test
    public void calculateDepthByIdentifer() {
        System.out.println(decompositionTree.calculateDepthByIdentifer(0));
        System.out.println(decompositionTree.calculateDepthByIdentifer(1));
        System.out.println(decompositionTree.calculateDepthByIdentifer(0x10));
        System.out.println(decompositionTree.calculateDepthByIdentifer(0x106));
        System.out.println(decompositionTree.calculateDepthByIdentifer(0x1069));
    }

    //测试成功
    //根据给定的整数编码计算得出最小矩形
    @Test
    public void calculateEnvelopeByIndentifer() {
//        0x1  : de_envelope: minData:{2.0, 6.0, 14.0, 20.0},maxData:{4.0, 8.0, 16.0, 30.0}
//        0x12 : de_envelope: minData:{2.0, 7.0, 14.0, 20.0},maxData:{3.0, 8.0, 15.0, 25.0}
//        0x123: de_envelope: minData:{2.5, 7.5, 14.0, 20.0},maxData:{3.0, 8.0, 14.5, 22.5}
        Envelope s = decompositionTree.calculateEnvelopeByIndentifer(0x123);
        System.out.print("de_envelope" + ": minData:{");
        for (int j = 0; j < s.getMinData().length - 1; j++) {
            System.out.print(s.getMinData()[j] + ", ");
        }
        System.out.print(s.getMinData()[s.getMinData().length - 1]);
        System.out.print("},maxData:{");
        for (int j = 0; j < s.getMaxData().length - 1; j++) {
            System.out.print(s.getMaxData()[j] + ", ");
        }
        System.out.print(s.getMaxData()[s.getMaxData().length - 1]);
        System.out.println("}");
    }

    //测试成功
    @Test
    public void calculateIdentiferByEnvelopeOrVertex() {
        //        0x123: de_envelope: minData:{2.5, 7.5, 14.0, 20.0},maxData:{3.0, 8.0, 14.5, 22.5}
//        double p[] = {2, 6, 14, 20};
//        double q[] = {4, 8, 16, 30};
        double p[] = {3.2, 7.5, 15.3, 22.5};
        double q[] = {3.5, 7.7, 15.7, 25.0};
//        double p[] = {2.7, 7.2, 14.4, 20.03};
//        double q[] = {3.0, 8.0, 14.5, 22.5};
        Envelope envelope = new Envelope(4, p, q);
        System.out.println(decompositionTree.calculateIdentiferByEnvelopeOrVertex(envelope, 5));

        Vertex vertex = new Vertex(4, new double[]{3.0, 7.1, 15.7, 31});
        System.out.println(decompositionTree.calculateIdentiferByEnvelopeOrVertex(vertex, 2));
    }

    //测试成功
    @Test
    public void insertPoint() {
//        double p[] = {2, 6, 14, 20};
//        double q[] = {4, 8, 16, 30};
        Vertex vertex = new Vertex(4, new double[]{3, 6.2, 15.3, 25});
        decompositionTree.insertPoint(vertex);
        Vertex vertex1 = new Vertex(4, new double[]{3.2, 6.3, 15.0, 25});
        decompositionTree.insertPoint(vertex1);
        Vertex vertex2 = new Vertex(4, new double[]{3.4, 6.2, 14.65, 20.6});
        decompositionTree.insertPoint(vertex2);
        Vertex vertex3 = new Vertex(4, new double[]{3.3, 6.2, 14.65, 20.6});
        decompositionTree.insertPoint(vertex3);
        Vertex vertex4 = new Vertex(4, new double[]{3.3, 6.9, 14.65, 26});
        decompositionTree.insertPoint(vertex4);
    }

    //insertPoint里面进行了测试，测试成功
    @Test
    public void splitNode() {
    }

    @Test
    public void remove() {
        Vertex vertex = new Vertex(4, new double[]{3, 6.2, 15.3, 25});
        decompositionTree.insertPoint(vertex);
        Vertex vertex1 = new Vertex(4, new double[]{3.2, 6.3, 15.0, 25});
        decompositionTree.insertPoint(vertex1);
        Vertex vertex2 = new Vertex(4, new double[]{3.4, 6.2, 14.65, 20.6});
        decompositionTree.insertPoint(vertex2);
        Vertex vertex3 = new Vertex(4, new double[]{3.3, 6.2, 14.65, 20.6});
        decompositionTree.insertPoint(vertex3);
        Vertex vertex4 = new Vertex(4, new double[]{3.3, 6.9, 14.65, 26});
        decompositionTree.insertPoint(vertex4);
        decompositionTree.remove(vertex4);
    }


}
