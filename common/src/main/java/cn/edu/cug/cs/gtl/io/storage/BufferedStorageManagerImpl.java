package cn.edu.cug.cs.gtl.io.storage;

import cn.edu.cug.cs.gtl.common.Identifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by ZhenwenHe on 2016/12/8.
 */
class BufferedStorageManagerImpl implements BufferedStorageManager {
    private static final long serialVersionUID = 1L;
    /**
     * buffer中存放的数据项的个数最多为capacity
     * 如果超过了，则必须调用removeEntry删除一项
     * 并将删除的项存储到storageManager指向的存储设备中
     */
    int capacity;
    Map<Identifier, Entry> buffer;
    /**
     * 写入缓存的时候是否同步将内容也写入到存储设备中
     * 如果为true,则在写入缓存的同时也会向存储设备也写入
     * 如果为false,则不会同时写入到存储设备
     */
    boolean writeThrough;
    StorageManager storageManager;
    /**
     * 命中次数
     */
    long hits;

    public BufferedStorageManagerImpl(StorageManager storageManager, int capacity, boolean writeThrough) {
        this.capacity = capacity;
        this.writeThrough = writeThrough;
        this.storageManager = storageManager;
        buffer = new HashMap<Identifier, Entry>(capacity);
    }

    @Override
    public long getHits() {
        return this.hits;
    }

    /**
     * 读取指定页面内容
     *
     * @param page 页面唯一ID
     * @return
     * @throws IOException
     */
    @Override
    public byte[] loadByteArray(Identifier page) throws IOException {
        byte[] returnData = null;
        BufferedStorageManagerImpl.Entry v = this.buffer.get(page);
        if (v != null) {
            //如果包含页面的ID，则直接将缓存中的数据项返回
            ++this.hits;
            returnData = new byte[v.length];
            System.arraycopy(v.data, 0, returnData, 0, v.length);
        } else {
            //如果请求的页面ID不存在缓存中，则从存储设备中读取页面数据
            //并将该数据加入到缓存中
            returnData = this.storageManager.loadByteArray(page);
            addEntry(page, new Entry(returnData));
        }

        return returnData;
    }

    /**
     * 将字节数据写入指定页面
     *
     * @param page 页面ID，如果为-1，则选择一个新的页面写入，并修改传入page并返回
     *             如果为有效页面ID，则写入该页面。
     * @param data 需要写入的字节数组
     * @throws IOException
     */
    @Override
    public void storeByteArray(Identifier page, byte[] data) throws IOException {
        if (page.longValue() == StorageManager.NEW_PAGE) {
            //如果页面ID为NEW_PAGE，则向存储设备中新建一个页面
            //写入数据，并且返回新建的页面ID，存放在page中
            this.storageManager.storeByteArray(page, data);
            assert this.buffer.containsKey(page) == false;
            //将数据同时写入到缓存中
            addEntry(page, new Entry(data));
        } else {
            //如果页面ID不是要新建页面，则直接添加到缓存中，
            //如果writeThrough设置为true，则也同时写入缓存设备
            if (this.writeThrough) {
                this.storageManager.storeByteArray(page, data);
            }

            BufferedStorageManagerImpl.Entry e = new BufferedStorageManagerImpl.Entry(data);
            if (this.writeThrough == false) e.dirty = true;

            BufferedStorageManagerImpl.Entry it = this.buffer.get(page);
            if (it != null) {
                //如果该页面在缓存中已经存在，则替换页面中的数据
                this.buffer.put(page, e);
                if (this.writeThrough == false)
                    ++this.hits;
            } else {
                //如果该页面在缓存中不存在，则添加该页面及其中数据
                addEntry(page, e);
            }
        }
    }

    /**
     * 删除指定页面内容
     *
     * @param page
     * @throws IOException
     */
    @Override
    public void deleteByteArray(Identifier page) throws IOException {
        //删除缓存中的页面
        this.buffer.remove(page);
        //删除存储设备中的页面
        this.storageManager.deleteByteArray(page);
    }

    /**
     * 强制写入存储介质
     */
    @Override
    public void flush() throws IOException {
        Set<Map.Entry<Identifier, BufferedStorageManagerImpl.Entry>> s = this.buffer.entrySet();
        Iterator<Map.Entry<Identifier, BufferedStorageManagerImpl.Entry>> it = s.iterator();
        while (it.hasNext()) {
            Map.Entry<Identifier, BufferedStorageManagerImpl.Entry> e = it.next();
            if (e.getValue().dirty) {
                //如果数据项发生了变动，则将该数据项写入到存储设备中
                Identifier page = e.getKey();
                this.storageManager.storeByteArray(page, e.getValue().data);
            }
            //将缓存中的页面数据设置为空
            e.setValue(null);
        }
    }

    /**
     * 将缓存写回到存储设备，并将缓存清空
     */
    @Override
    public void clear() throws IOException {
        this.flush();
        this.buffer.clear();
        this.hits = 0;
    }

    public void addEntry(Identifier page, Entry entry) throws IOException {
        assert this.buffer.size() <= this.capacity;
        if (this.buffer.size() == this.capacity) {
            removeEntry();
            assert this.buffer.size() < this.capacity;
        }
        assert this.buffer.containsKey(page) == false;
        this.buffer.put(page, entry);
    }

    /**
     * 根据选择策略，选择一项删除
     */
    public void removeEntry() throws IOException {
        if (this.buffer.size() == 0) return;
        Map.Entry<Identifier, BufferedStorageManagerImpl.Entry> me = this.evictionStrategy();
        if (me == null) return;

        if (me.getValue().dirty) {//如果该项有更新，则先写入存储设备
            Identifier page = me.getKey();
            byte[] data = me.getValue().data;
            this.storageManager.storeByteArray(page, data);
        }
        this.buffer.remove(me.getKey());
    }

    @Override
    public void close() throws IOException {
        clear();
    }

    /**
     * 根据缓存删除策略，计算逐出缓存的项，并返回
     * 重载此方法，可以改变逐出缓存的项的策略
     */
    public Map.Entry<Identifier, Entry> evictionStrategy() {
        double random = Math.random();

        int entry = (int) (Math.floor(((double) this.buffer.size()) * random));

        Set<Map.Entry<Identifier, BufferedStorageManagerImpl.Entry>> s = this.buffer.entrySet();

        Iterator<Map.Entry<Identifier, BufferedStorageManagerImpl.Entry>> it = s.iterator();
        int i = 0;
        Map.Entry<Identifier, BufferedStorageManagerImpl.Entry> me = null;

        while (it.hasNext()) {
            if (i == entry) {
                me = it.next();
                break;
            }
            i += 1;
        }
        assert me != null;
        return me;
    }

    /**
     * 缓存中，页对应的数据项
     */
    class Entry {
        byte[] data;
        int length;
        boolean dirty;

        Entry(byte[] d) {
            this.data = null;
            this.dirty = false;
            this.length = d.length;
            this.data = new byte[this.length];
            System.arraycopy(d, 0, this.data, 0, this.length);
        }
    }


}
