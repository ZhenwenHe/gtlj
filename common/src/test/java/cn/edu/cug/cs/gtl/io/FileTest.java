package cn.edu.cug.cs.gtl.io;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class FileTest {

    @Test
    public void exists() {
    }

    @Test
    public void getFileName() {
    }

    @Test
    public void getFileNameWithoutSuffix() {
    }

    @Test
    public void splitFileName() {
    }

    @Test
    public void getDirectory() {
    }

    @Test
    public void getSuffixName() {
    }

    @Test
    public void replaceSuffixName() {
    }

    @Test
    public void readTextLines() {
    }

    @Test
    public void testReadTextLines() {
    }

    @Test
    public void testReadTextLines1() {
    }

    @Test
    public void ListFiles() {
        List<String> ls = File.listFiles("/Users/zhenwenhe/git/studies", "*.java");
        for (String s : ls) {
            System.out.println(s);
        }

    }

    @Test
    public void ListAllFiles() {
        List<String> ls = File.listFiles("/Users/zhenwenhe/git/studies", "*.*");
        for (String s : ls) {
            System.out.println(s);
        }

    }

    @Test
    public void listFilesAndDirs() {
        List<String> ls = File.listFiles("/Users/zhenwenhe/git/studies", null);
        for (String s : ls) {
            System.out.println(s);
        }

    }
}