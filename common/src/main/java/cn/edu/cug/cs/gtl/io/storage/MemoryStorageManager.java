package cn.edu.cug.cs.gtl.io.storage;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
class MemoryStorageManager implements StorageManager {

    private static final long serialVersionUID = -8616598282041171368L;

    /**
     * 存放Entry集合，下标为页面ID
     */
    List<MemoryStorageManager.Entry> buffer;

    /**
     * 存放空页面ID
     */
    Stack<Identifier> emptyPages;

    public MemoryStorageManager() {
        this.buffer = new ArrayList<Entry>();
        this.emptyPages = new Stack<Identifier>();
    }

    /**
     * 读取指定页面的数据
     *
     * @param page 页面唯一ID
     * @return byte数组
     */
    @Override
    public byte[] loadByteArray(Identifier page) {
        try {
            int index = (int) page.longValue(); // 获取页面所在的索引
            MemoryStorageManager.Entry e = this.buffer.get(index); // 通过索引以字节流的方式获取页面的内容
            if (e == null) return null;
            byte[] r = null;
            r = new byte[e.length]; // 创建一个等大的字节数组
            System.arraycopy(e.data, 0, r, 0, e.length); // 将获取的数据存在一个新的字节数组中
            return r;
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节数组写入对应的页中
     *
     * @param page 页面ID，如果为-1，则选择一个新的页面写入，并修改传入page并返回
     *             如果为有效页面ID，则写入该页面;如果是无效的page，则会抛出IOException
     * @param data 需要写入的字节数组
     * @throws IOException
     */
    @Override
    public void storeByteArray(Identifier page, byte[] data) throws IOException {

        if (page.longValue() == StorageManager.NEW_PAGE) {
            // 如果是新建页
            Entry e = new Entry(data);
            if (emptyPages.empty()) {
                // 如果页面栈为空，将e放到buffer中，
                // 则新建的页面的ID为e在buffer中的下标
                buffer.add(e);
                page.reset(buffer.size() - 1);
            } else {
                // 如果页面栈不为空，则弹出栈中页面,并将页面ID设置为栈顶元素
                // 则新建的页面的ID为e在buffer中的下标
                page.reset(emptyPages.pop().longValue());
                buffer.set(page.intValue(), e);
            }
        } else {
            // 如果页面ID不是新建,则获取已有的页面及其数据
            Entry e_old = this.buffer.get(page.intValue());
            if (e_old == null) {
                throw new IOException("MemoryStorageManager.storeByteArray: Invalid Page Exception");
            }
            Entry e = new Entry(data);
            this.buffer.set(page.intValue(), e);
        }
    }

    /**
     * 删除指定页面的数据
     *
     * @param page 页面唯一ID
     */
    @Override
    public void deleteByteArray(Identifier page) {
        int index = (int) (page.longValue());
        MemoryStorageManager.Entry e = this.buffer.get(index);
        if (e == null) return;
        this.buffer.set(index, null);
        this.emptyPages.push(page);
    }

    /**
     * 强制写入存储介质
     */
    @Override
    public void flush() {
        return;
    }

    /**
     * 关闭流，关闭之前将缓存中的数据清空
     */
    @Override
    public void close() {
        flush();
    }

    /**
     * 以entry集合的形式存储数据以及数据长度
     */
    class Entry implements java.io.Serializable {

        private static final long serialVersionUID = 2805920284167090345L;

        byte[] data;
        int length;

        Entry(byte[] d) {
            this.length = d.length;
            this.data = new byte[this.length];
            System.arraycopy(d, 0, this.data, 0, this.length);
        }
    }


}
