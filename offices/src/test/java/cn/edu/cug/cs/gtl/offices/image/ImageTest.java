package cn.edu.cug.cs.gtl.offices.image;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ImageTest {

    @Test
    public void getWidth() {
    }

    @Test
    public void getHeight() {
    }

    @Test
    public void getRGB() {
    }

    @Test
    public void setRGB() {
    }

    @Test
    public void gray() {
        try {
            Image im = Image.read(new File("dat/word.jpg"));
            Image imGray = im.gray();
            imGray.write("jpg", new File("dat/word_gray.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void binary() {
        try {
            Image im = Image.read(new File("dat/word.jpg"));
            Image imGray = im.binary();
            imGray.write("jpg", new File("dat/word_binary.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void write() {
    }

    @Test
    public void testWrite() {
    }

    @Test
    public void read() {
    }

    @Test
    public void testRead() {
    }

    @Test
    public void testRead1() {
    }
}