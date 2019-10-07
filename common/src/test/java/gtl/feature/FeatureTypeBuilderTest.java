package gtl.feature;

import gtl.common.Identifier;
import gtl.common.Variant;
import gtl.geom.Geometry;
import gtl.geom.LineString;
import gtl.geom.Point;
import org.junit.Test;

import static org.junit.Assert.*;

public class FeatureTypeBuilderTest {

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
    public void setCoordinateReferenceSystem() {
    }

    @Test
    public void build() {
        FeatureType ft=new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName("Road")
                .setCoordinateReferenceSystem(null)
                .add("geometry", LineString.class)
                .add("道路名称",Variant.STRING)
                .add("道路长度", Variant.DOUBLE)
                .add("道路等级",Variant.INTEGER)
                .build();
    }
}