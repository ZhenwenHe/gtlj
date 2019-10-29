package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.config.Config;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

import cn.edu.cug.cs.gtl.io.File;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by hadoop on 18-9-15
 */
public class TextureTest {
    Texture textureTest = null;

    static final String JPG_FILE_NAME = Config.getTestInputDirectory() + File.separator + "images" + File.separator + "test.jpg";

    @Test
    public void cloneTest() {
        textureTest = new Texture();

        textureTest.setTextureType(0);
        textureTest.setTextureID(12345);
        textureTest.setTextureName("纹理");
        textureTest.setWrapMode(0);

        Texture textureTest2 = (Texture) textureTest.clone();
        System.out.println("纹理1类型" + textureTest.getTextureType() + " 纹理1ID:" + textureTest.getTextureID());
        System.out.println("纹理2类型" + textureTest2.getTextureType() + " 纹理2ID:" + textureTest2.getTextureID());

        textureTest.setTextureType(1);
        textureTest.setTextureID(23456);
        assertTrue(textureTest2.getTextureName().matches("纹理"));
        assertTrue(textureTest2.getTextureID() == 12345);
        assertTrue(textureTest2.getTextureType() == 0);

        System.out.println("纹理1类型" + textureTest.getTextureType() + " 纹理1ID:" + textureTest.getTextureID());
        System.out.println("纹理2类型" + textureTest2.getTextureType() + " 纹理2ID:" + textureTest2.getTextureID());
    }

    @Test
    public void load() {
        try {
            File f = new File(JPG_FILE_NAME);
            if (f.exists()) {
                BufferedImage bufferedImage = ImageIO.read(f);
                Raster r = bufferedImage.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void store() {
        try {
            Texture t = Texture.class.newInstance();
            System.out.println(t.getTextureID());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getByteArraySize() {
    }

    @Test
    public void loadFile() {
        if (File.exists(JPG_FILE_NAME)) {
            Texture t = new Texture(JPG_FILE_NAME);
            String s = File.getFileNameWithoutSuffix(JPG_FILE_NAME);
            Assert.assertTrue(t.getTextureName().compareTo(s) == 0);
            Assert.assertTrue(t.getTextureType() == Texture.TEXTURE_IMAGE_JPG);
        }
    }
}