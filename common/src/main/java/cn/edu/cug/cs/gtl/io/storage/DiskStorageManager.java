package cn.edu.cug.cs.gtl.io.storage;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

/**
 * Created by ZhenwenHe on 2016/12/9.
 */
class DiskStorageManager implements StorageManager {
    private static final long serialVersionUID = 1L;
    //数据文件，按照页面进行读写
    RandomAccessFile dataFile;

    /**
     * 索引文件结构如下：
     * 1-4：pageSize int
     * 5-8：nextPage int
     * 9-12:emptyPagesCount 空页面的数目 int
     * 13-EPC: 记录空页面的ID EPC=(13+emptyPagesCount*4)
     * EPC-(EPC+4): pageIndex中数据项数 PIC
     * Entry 的第0个页面ID int
     * Entry的数据长度  int
     * Entry中的页面总数 int
     * Entry中的每个页面的ID，包括第0个
     */
    RandomAccessFile indexFile;
    //每个页面的大小，按照字节进行计算
    int pageSize;
    //页面缓冲区，大小为pageSize
    byte[] buffer;
    //记录下一个有效的页面ID，初始化为0，每用掉一个页面后自动加1
    Identifier nextPage;
    //记录空页面，主要是删除数据回收得到的页面
    List<Identifier> emptyPages;
    //页面索引，关键字为Entry中的第一个页面ID
    Map<Identifier, Entry> pageIndex;

    /**
     * @param baseName  文件名
     * @param pageSize  指定页面存储大小
     * @param overWrite 是否覆盖原文件
     * @throws IOException
     */
    public DiskStorageManager(String baseName, int pageSize, boolean overWrite) throws IOException {
        String dataFileName = baseName + ".dat";
        String indexFileName = baseName + ".idx";
        long length = 0; // 记录文件长度

        this.nextPage = Identifier.create(StorageManager.NEW_PAGE);
        this.emptyPages = new ArrayList<Identifier>();
        this.pageIndex = new HashMap<Identifier, Entry>();
        this.buffer = null;
        //如果文件存在则以读写方式打开，如果不存在则创建文件并以读写方式打开
        this.dataFile = new RandomAccessFile(dataFileName, "rw");
        this.indexFile = new RandomAccessFile(indexFileName, "rw");

        if (overWrite) {
            //如果要覆盖原有数据，将文件长度设置为0
            this.indexFile.setLength(0L);
            this.dataFile.setLength(0L);
        }
        // get current length of file
        length = this.indexFile.length();

        //find page size
        if (length == 0 || overWrite == true) {//如果索引文件长度为0
            this.pageSize = pageSize;
            this.nextPage.reset(0);
        } else {
            //从索引文件中读取pageSize,第一个整数为pageSize,第二位整数为nextPage
            this.pageSize = (int) this.indexFile.readLong();
            this.nextPage.reset(this.indexFile.readLong());
        }

        // create buffer.
        this.buffer = new byte[pageSize];
        for (int i = 0; i < this.buffer.length; i++) this.buffer[i] = 0;

        if ((overWrite == false) && (length > 0)) {
            int count;
            Identifier page = null;
            Identifier id = null;

            // load empty pages in memory.
            count = (int) this.indexFile.readLong();

            for (int cCount = 0; cCount < count; ++cCount) {
                page = Identifier.create(indexFile.readLong());
                this.emptyPages.add(page);
            }

            // load index table in memory.
            count = (int) this.indexFile.readLong();
            for (int cCount = 0; cCount < count; ++cCount) {
                Entry e = new Entry();
                id = Identifier.create(this.indexFile.readLong());
                e.length = (int) this.indexFile.readLong();
                int count2 = (int) this.indexFile.readLong();
                for (int cCount2 = 0; cCount2 < count2; ++cCount2) {
                    page = Identifier.create(this.indexFile.readLong());
                    e.pages.add(page);
                }
                this.pageIndex.put(id, e);
            }
        }
    }

    /**
     * 读取指定页面数据
     *
     * @param page 页面唯一ID
     * @return data 将文件中的数据读取到字节数组<parm>data</parm>中
     * @throws IOException 文件IO异常
     */
    @Override
    public byte[] loadByteArray(Identifier page) throws IOException {
        //查找页面ID是否存在，如果不存在则抛出异常
        Entry e = this.pageIndex.get(page);
        if (e == null)
            throw new IOException("DiskStorageManager.loadByteArray" + page.toString());
        //记录Entry中的页面总数
        int cTotal = e.pages.size();
        //用于存放Entry中记录页面中记录的内容
        byte[] data = new byte[e.length];
        //Entry.pages中的下一个页面的下标
        int cNext = 0;
        int ptr = 0;
        int cLen = 0;
        //剩下的需要读取的内存长度
        int cRem = e.length;

        do {
            this.dataFile.seek(e.pages.get(cNext).longValue() * this.pageSize);
            //读取一个页面中的内容放在this.buffer中
            this.dataFile.read(this.buffer);
            //计算实际有效内容长度
            cLen = (cRem > this.pageSize) ? this.pageSize : cRem;
            //将this.buffer中的内容拷贝到data中，长度为cLen
            System.arraycopy(this.buffer, 0, data, ptr, cLen);
            ptr += cLen;
            cRem -= cLen;
            ++cNext;
        } while (cNext < cTotal);

        return data;
    }

    /**
     * 以字节数组将数据写入指定页面
     *
     * @param page 页面ID，如果为-1，则选择一个新的页面写入，并修改传入page并返回
     *             如果为有效页面ID，则写入该页面。
     * @param data 需要写入的字节数组
     * @throws IOException
     */
    @Override
    public void storeByteArray(Identifier page, byte[] data) throws IOException {
        if (page.longValue() == StorageManager.NEW_PAGE) {
            Entry e = new Entry();
            e.length = data.length;

            int ptr = 0;//从data数组的第0个字节开始
            Identifier cPage = Identifier.create(StorageManager.NEW_PAGE);
            int cRem = data.length;
            int cLen = 0;

            while (cRem > 0) {
                if (!this.emptyPages.isEmpty()) {
                    cPage.reset(this.emptyPages.remove(0).longValue());
                } else {
                    cPage.reset(this.nextPage.longValue());
                    this.nextPage.increase();
                }

                cLen = (cRem > this.pageSize) ? this.pageSize : cRem;
                System.arraycopy(data, ptr, this.buffer, 0, cLen);

                this.dataFile.seek(cPage.longValue() * this.pageSize);
                this.dataFile.write(this.buffer);
                ptr += cLen;
                cRem -= cLen;
                e.pages.add((Identifier) cPage.clone());
            }

            page.copyFrom(e.pages.get(0));
            this.pageIndex.put((Identifier) page.clone(), e);
        } else {
            // find the entry.
            Entry oldEntry = this.pageIndex.remove(page);
            if (oldEntry == null)
                throw new IOException("DiskStorageManager.storeByteArray: Invalid Page Exception");

            Entry e = new Entry();
            e.length = data.length;

            int ptr = 0;
            Identifier cPage = Identifier.create(StorageManager.NEW_PAGE);
            int cRem = e.length;
            int cLen, cNext = 0;

            while (cRem > 0) {
                if (cNext < oldEntry.pages.size()) {
                    cPage.copyFrom(oldEntry.pages.get(cNext));
                    ++cNext;
                } else if (!this.emptyPages.isEmpty()) {
                    cPage.copyFrom(this.emptyPages.remove(0));
                } else {
                    cPage.copyFrom(this.nextPage);
                    this.nextPage.increase();
                }

                cLen = (cRem > this.pageSize) ? this.pageSize : cRem;
                System.arraycopy(data, ptr, this.buffer, 0, cLen);

                this.dataFile.seek(cPage.longValue() * this.pageSize);
                this.dataFile.write(this.buffer);
                ptr += cLen;
                cRem -= cLen;
                e.pages.add((Identifier) cPage.clone());
            }

            while (cNext < oldEntry.pages.size()) {
                this.emptyPages.add((Identifier) oldEntry.pages.get(cNext).clone());
                ++cNext;
            }
            this.pageIndex.put((Identifier) page.clone(), e);
        }
    }

    /**
     * 删除指定页面
     *
     * @param page 要删除的页面ID
     * @throws IOException
     */
    @Override
    public void deleteByteArray(Identifier page) throws IOException {
        Entry e = this.pageIndex.remove(page);
        if (e == null)
            throw new IOException("DiskStorageManager.deleteByteArray: " + page.toString());

        for (int cIndex = 0; cIndex < e.pages.size(); ++cIndex) {
            this.emptyPages.add((Identifier) e.pages.get(cIndex).clone());
        }
    }

    /**
     * 强制写入存储介质
     *
     * @throws IOException
     */
    @Override
    public void flush() throws IOException {
        this.indexFile.seek(0);
        this.indexFile.writeLong(this.pageSize);
        this.indexFile.writeLong(this.nextPage.longValue());
        int count = this.emptyPages.size();
        this.indexFile.writeLong(count);
        Iterator<Identifier> it = this.emptyPages.iterator();
        while (it.hasNext()) {
            this.indexFile.writeLong(it.next().longValue());
        }
        count = this.pageIndex.size();
        this.indexFile.writeLong(count);

        this.pageIndex.forEach(
                (Identifier p, Entry e) -> {
                    try {
                        this.indexFile.writeLong(p.longValue());
                        this.indexFile.writeLong(e.length);
                        this.indexFile.writeLong(e.pages.size());
                        e.pages.forEach(
                                (Identifier pp) -> {
                                    try {
                                        this.indexFile.writeLong(pp.longValue());
                                    } catch (IOException ioe) {
                                        ioe.printStackTrace();
                                    }
                                }
                        );
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
        );
        //this.indexFile.flush();
        //this.dataFile.flush();
    }

    /**
     * 关闭打开的文件
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        // 在关闭文件之前先清空缓冲区，防止数据丢失
        flush();

        this.indexFile.close();
        this.dataFile.close();
    }

    /**
     * length为数据长度，如果长度超过了一个页面的长度，
     * 则会放在几个页面，页面的ID存放在pages中；
     */
    class Entry {
        int length;
        ArrayList<Identifier> pages;

        Entry() {
            pages = new ArrayList<Identifier>();
            length = 0;
        }
    }
}
