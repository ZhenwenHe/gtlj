package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.geom.LineString;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeatureBuilderTest {

    @Test
    public void setIdentifier() {
    }

    @Test
    public void setName() {
    }

    @Test
    public void add() {
    }

    @Test
    public void add1() {
    }

    @Test
    public void add2() {
    }

    @Test
    public void add3() {
    }

    @Test
    public void add4() {
    }

    @Test
    public void add5() {
    }

    @Test
    public void add6() {
    }

    @Test
    public void add7() {
    }

    @Test
    public void add8() {
    }

    @Test
    public void build() {
        FeatureType ft = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称", Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级", Variant.INTEGER)
                .build();

        //LineString lineString=(LineString) Geometry.create(ft.getGeometryDescriptor().getGeometryType().getBinding(),3);
        assertTrue(LineString.class.equals(ft.getGeometryDescriptor().getGeometryType().getBinding()));
        LineString lineString = (LineString) Geometry.create(LineString.class, 3);
        lineString.getVertices().add(0, 0, 0);
        lineString.getVertices().add(1, 1, 1);
        lineString.getVertices().add(2, 2, 2);

        Feature f = new FeatureBuilder(ft)
                .setIdentifier(Identifier.create())
                .setName("Road_1") // 要素名称
                .add(lineString) //几何对象
                .add("八一路") //属性：道路名称
                .add(3000.0) //属性：道路长度
                .add(2)  //属性：道路等级
                .build();
    }
}