package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.geom.LineString;

import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CoderMalfunctionError;

import static org.junit.Assert.*;

public class FeatureTypeTest {

    /**
     * 测试FeatureType的默认构造方法
     */

    @Test
    public void constructor() {
        FeatureType featureType = new FeatureType();
        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();


    }

    /**
     * 测试FeatureType的克隆方法
     */

    @Test
    public void cloneTest() {
        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();
        FeatureType f3 = (FeatureType) ft2.clone();
        assertEquals(f3.getIdentifier().longValue(), ft2.getIdentifier().longValue());
        assertEquals(f3.getName(), ft2.getName());


    }


    /**
     * 测试FeatureType的加载方法
     */
    @Test
    public void load() {
        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();
        try {
            byte[] t = ft2.storeToByteArray();
            FeatureType f4 = new FeatureType();
            f4.loadFromByteArray(t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试FeatureType的存储方法
     */

    @Test
    public void store() {
        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();
        try {
            byte[] t = ft2.storeToByteArray();
            FeatureType f3 = (FeatureType) ft2.clone();
            byte[] t2 = f3.storeToByteArray();
            assertEquals(t.length, t2.length);
            assertArrayEquals(t, t2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getByteArraySize() {

        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();
        try {
            byte[] t = ft2.storeToByteArray();
            assertEquals(t.length, ft2.getByteArraySize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void byteToString() {
        FeatureType ft2 = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();
        try {
            String t = ft2.storeToByteString();
            FeatureType f3 = (FeatureType) ft2.clone();
            String t2 = f3.storeToByteString();
            assertTrue(t.equals(t2));
            f3.loadFromByteString(t);
            String t3 = f3.storeToByteString();
            assertTrue(t.equals(t3));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void ssvString() {
        String s = "areawater    WKT%POLYGON    ANSICODE    HYDROID   FULLNAME MTFCC     ALAND  AWATER  INTPTLAT  INTPTLON";
        FeatureType ft = FeatureType.ssvString(s);
        assertEquals(ft.getName(), "areawater");

        FeatureType ft2 = FeatureType.fromString(s);
        assertEquals(ft.getName(), "areawater");

        String ss = "\"POLYGON ((-156.309467 20.80195,-156.309092 20.802154,-156.309004 20.802181,-156.308841 20.801928,-156.308764 20.801774,-156.308794 20.801741,-156.308857 20.801705,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532,-156.30942 20.801851,-156.309428 20.801869,-156.309467 20.80195))\"                11057904285             H2030   0       3035    +20.8018570     -156.3091111\n";
        String[] sss = ss.split("  ");

        String ss2 = "\"POLYGON ((-156.309467 20.80195,-156.309092 20.802154,-156.309004 20.802181,-156.308841 20.801928,-156.308764 20.801774,-156.308794 20.801741,-156.308857 20.801705,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532,-156.30942 20.801851,-156.309428 20.801869,-156.309467 20.80195))\"                11057904285             H2030   0       3035    +20.8018570     -156.3091111\n";
        String[] sss2 = ss.split(FileDataSplitter.TSV.getDelimiter());

        int i = ss.indexOf('(');
        String tag = ss.substring(0, i).trim();
        if (tag.charAt(0) == '\"')
            tag = tag.substring(1, tag.length());
        assertEquals(tag, "POLYGON");

        i = ss.lastIndexOf('\"');
        String geom = ss.substring(0, i + 1).trim();
        String attributes = ss.substring(i + 1).trim();
        String[] columns = attributes.split("\\s+");

    }

}