package cn.edu.cug.cs.gtl.io.storage;

import cn.edu.cug.cs.gtl.common.Identifier;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

public class MemoryStorageManagerTest {

    @Test
    public void loadAndStore() {
        StorageManager sm = StorageManager.createMemoryStorageManager();
        final byte[] origin = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Identifier> identifiers = new ArrayList<>(9);
        try {
            for (int i = 0; i < 9; ++i) {
                byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                Identifier identifier = sm.write(-1, bytes);
                identifiers.add(identifier);
            }

            for (Identifier i : identifiers) {
                byte[] bytes = sm.read(i.longValue());
                assertArrayEquals(bytes, origin);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void deleteByteArray() {
        StorageManager sm = StorageManager.createMemoryStorageManager();
        final byte[] origin = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Identifier> identifiers = new ArrayList<>(9);
        try {
            for (int i = 0; i < 9; ++i) {
                byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                Identifier identifier = sm.write(-1, bytes);
                identifiers.add(identifier);
            }

            sm.deleteByteArray(identifiers.get(6));
            // 删除以后通过堆栈可以看到对应的页面没有 而且emptyPages多了一页
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}