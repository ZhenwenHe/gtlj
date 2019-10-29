package cn.edu.cug.cs.gtl.io;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;

public interface DataWriter<F extends Storable> extends Storable {


    /**
     * 将对象s写入Writer，可能是文件或内存，还有可以能是网络管道
     *
     * @param s 可序列化的对象
     * @throws IOException
     */
    void write(F s) throws IOException;

    default void write(F[] fs) throws IOException {
        for (F f : fs)
            write(f);
    }

    default void write(Iterable<F> fs) throws IOException {
        for (F f : fs)
            write(f);
    }
}
