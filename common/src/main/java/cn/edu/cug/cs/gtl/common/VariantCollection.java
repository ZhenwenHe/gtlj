package cn.edu.cug.cs.gtl.common;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 此类是Variant类型的ArrayList
 */
public class VariantCollection extends ArrayList<Variant> implements Serializable {

    public VariantCollection(int initialCapacity) {
        super(initialCapacity);
    }

    public VariantCollection() {
    }

    public VariantCollection(@NotNull Collection<? extends Variant> c) {
        super(c);
    }

    /**
     * 对象克隆函数；
     *
     * @return 返回本对象的克隆；
     */
    @Override
    public Object clone() {
        VariantCollection vc = new VariantCollection(this.size());
        for (Variant v : (Collection<Variant>) this)
            vc.add((Variant) v.clone());
        return vc;
    }

    /**
     * 通过输入流，读取对象的属性值；
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        int n = in.readInt();
        if (n > 0) {
            this.clear();
            this.ensureCapacity(n);
            for (int i = 0; i < n; ++i) {
                Variant v = new Variant();
                v.load(in);
                this.add(v);
            }
        } else {
            clear();
        }
        return true;
    }

    /**
     * 通过输出流，将对象存储起来；
     *
     * @param out，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(size());
        for (Variant v : (Collection<Variant>) this) {
            v.store(out);
        }
        return true;
    }

    /**
     * 获取写成的字节数组的长度；
     *
     * @return 字节数组长度
     */
    @Override
    public long getByteArraySize() {
        long len = 4;
        for (Variant v : (Collection<Variant>) this) {
            len += v.getByteArraySize();
        }
        return len;
    }
}
