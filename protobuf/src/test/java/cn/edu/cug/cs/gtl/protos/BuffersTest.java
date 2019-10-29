package cn.edu.cug.cs.gtl.protos;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

public class BuffersTest {
    @Test
    public void testInt8Buffer() {
        byte[] bytes = new byte[10];
        for (int i = 0; i < bytes.length; ++i)
            bytes[i] = (byte) i;
        ByteString b = ByteString.copyFrom(bytes);
        Int8Buffer.Builder builder = Int8Buffer.newBuilder();
        builder.setElement(b);
        Int8Buffer buf = builder.build();
        byte[] b2 = buf.toByteArray();
        try {
            Int8Buffer buf2 = Int8Buffer.parseFrom(b2);
            byte[] b3 = buf2.toByteArray();
            Assert.assertArrayEquals(b2, b3);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testInt32Buffer() {
        Int32Buffer.Builder builder = Int32Buffer.newBuilder();
        builder.addElement(12);
        builder.addElement(13);
        Int32Buffer buf = builder.build();
        for (Integer i : buf.getElementList())
            System.out.println(i);

        byte[] b2 = buf.toByteArray();
        try {
            Int32Buffer buf2 = Int32Buffer.parseFrom(b2);
            byte[] b3 = buf2.toByteArray();
            Assert.assertArrayEquals(b2, b3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUint64Buffer() {
        Uint64Buffer.Builder builder = Uint64Buffer.newBuilder();
        builder.addElement(12);
        builder.addElement(13);
        Uint64Buffer buf = builder.build();
        for (Long i : buf.getElementList())
            System.out.println(i);
    }

    @Test
    public void testFloat64Buffer() {
        Float64Buffer.Builder builder = Float64Buffer.newBuilder();
        builder.addElement(12);
        builder.addElement(13);
        Float64Buffer buf = builder.build();
        for (double i : buf.getElementList())
            System.out.println(i);
        Assert.assertTrue(buf.getElement(0) == 12.0);
        Assert.assertEquals(buf.getElementCount(), 2);
    }

    @Test
    public void testStringBuffer() {
        StringBuffer.Builder builder = StringBuffer.newBuilder();
        builder.addElement("12");
        builder.addElement("13");
        StringBuffer buf = builder.build();
        for (String i : buf.getElementList())
            System.out.println(i);
    }

    @Test
    public void testDataBuffer() {
        StringBuffer.Builder stringBufferBuilder = StringBuffer.newBuilder();
        stringBufferBuilder.addElement("12");
        stringBufferBuilder.addElement("13");
        stringBufferBuilder.addElement("18");
        StringBuffer stringBuffer = stringBufferBuilder.build();

        DataBuffer.Builder dataBufferBuilder = DataBuffer.newBuilder();
        dataBufferBuilder.setBuffer(Any.pack(stringBuffer));
        dataBufferBuilder.setType(DataBuffer.Type.STRING);
        DataBuffer dataBuffer = dataBufferBuilder.build();

        try {
            byte[] temp = dataBuffer.toByteArray();
            DataBuffer dataBuffer1 = DataBuffer.parseFrom(temp);
            StringBuffer stringBuffer1 = dataBuffer1.getBuffer().unpack(StringBuffer.class);
            Assert.assertEquals(stringBuffer1.getElement(0), "12");
            Assert.assertEquals(stringBuffer1.getElement(1), "13");
            Assert.assertEquals(stringBuffer1.getElement(2), "18");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}