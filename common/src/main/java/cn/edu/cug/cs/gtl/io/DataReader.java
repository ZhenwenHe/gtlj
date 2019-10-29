package cn.edu.cug.cs.gtl.io;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

public interface DataReader<F extends Storable> extends Storable {

    /**
     * 在Reader，可能是文件、内存或管道，从offset位置开始读取byteCount个字节内容，并构建对象返回
     *
     * @param offset    偏移量,默认为-1，表示直接从当前位置读取
     * @param byteCount 相对于offset后面的字节大小，默认为-1，表示直接读取F对象
     * @return 构建的对象
     * @throws IOException
     */
    F read(int offset, int byteCount) throws IOException;

    /**
     * 是否还有后续元素可以供读取
     *
     * @return
     * @throws IOException
     */
    boolean hasNext() throws IOException;

    /**
     * 读取下一个元素
     *
     * @return
     * @throws IOException
     */
    F next() throws IOException;

}
