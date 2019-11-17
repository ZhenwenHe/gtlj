package cn.edu.cug.cs.gtl.protoswrapper;

import cn.edu.cug.cs.gtl.jts.geom.GeometryFactory;
import cn.edu.cug.cs.gtl.protos.BoundingBox;
import cn.edu.cug.cs.gtl.protos.Polygon2D;
import org.geotools.geometry.GeometryBuilder;
import org.geotools.geometry.jts.WKTWriter2;
import org.geotools.referencing.crs.DefaultGeographicCRS;

import java.util.Arrays;

public class BoundingBoxWrapper {
    public static BoundingBox of(double x_min, double y_min, double x_max, double y_max){
        BoundingBox.Builder b = BoundingBox.newBuilder();
        b.setDimension(2);
        b.addOrdinate(x_min);
        b.addOrdinate(y_min);
        b.addOrdinate(x_max);
        b.addOrdinate(y_max);
        return b.build();
    }

    public static BoundingBox of(double x_min, double y_min, double z_min, double x_max, double y_max, double z_max){
        BoundingBox.Builder b = BoundingBox.newBuilder();
        b.setDimension(3);
        b.addOrdinate(x_min);
        b.addOrdinate(y_min);
        b.addOrdinate(z_min);
        b.addOrdinate(x_max);
        b.addOrdinate(y_max);
        b.addOrdinate(z_max);
        return b.build();
    }

    public static double [] getOrdinates(BoundingBox bb){
        double[] cc = new double[bb.getOrdinateCount()];
        for(int i=0;i<cc.length;++i)
            cc[i]=bb.getOrdinate(i);
        return cc;
    }

    public static double[] upper(BoundingBox bb){
        double [] cc= getOrdinates(bb);
        return Arrays.copyOfRange(cc,bb.getDimension(),bb.getOrdinateCount());
    }

    public static double getMinX(BoundingBox bb){
        return bb.getOrdinate(0);
    }

    public static double getMinY(BoundingBox bb){
        return bb.getOrdinate(1);
    }

    public static double getMaxY(BoundingBox bb){
        return bb.getOrdinate(bb.getDimension()+1);
    }

    public static double getMaxX(BoundingBox bb){
        return bb.getOrdinate(bb.getDimension());
    }

    public static double[] lower(BoundingBox bb){
        double [] cc= getOrdinates(bb);
        return Arrays.copyOfRange(cc,0,bb.getDimension());
    }

    public static boolean isValid(BoundingBox bb){
        if(bb==null) return false;
        if(bb.getDimension()<=0)
            return false;
        if(bb.getOrdinateCount()<=0)
            return false;
        return true;
    }
    public static String toWKT(BoundingBox bb){
        Polygon2D p = Polygon2DWrapper.of(bb);
        return Polygon2DWrapper.toWKT(p);
    }


}
