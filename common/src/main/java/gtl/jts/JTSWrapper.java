package gtl.jts;

import gtl.geom.Vertex2D;
import gtl.jts.geom.*;
import gtl.jts.io.ParseException;
import gtl.jts.io.WKTReader;
import gtl.jts.io.WKTWriter;
import gtl.jts.triangulate.VoronoiDiagramBuilder;
import gtl.geom.Triangle;
import gtl.geom.Vector;
import gtl.geom.VectorSequence;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class JTSWrapper { // 目前只支持2D


    /**
     * 利用JTS 的VoronoiDiagramBuilder根据给定的样本，进行分区
     * 返回分区矩形
     * @param samples
     * @param partitions
     * @return
     */
    public static Collection<gtl.geom.Envelope> createVoronoiPartitioning(List<gtl.geom.Envelope> samples, int partitions){
        GeometryFactory fact = new GeometryFactory();
        ArrayList<Point> subSampleList=new ArrayList<Point>();
        MultiPoint mp;
        //Take a subsample accoring to the partitions
        Vector v = null;
        for(int i=0;i<samples.size();i=i+samples.size()/partitions)
        {
            v = samples.get(i).getCenter();
            subSampleList.add(fact.createPoint(new Coordinate(v.getX(),v.getY())));
        }

        mp=fact.createMultiPoint(subSampleList.toArray(new Point[subSampleList.size()]));
        VoronoiDiagramBuilder voronoiBuilder = new VoronoiDiagramBuilder();
        voronoiBuilder.setSites(mp);
        Geometry voronoiDiagram=voronoiBuilder.getDiagram(fact);
        ArrayList<gtl.geom.Envelope> results = new ArrayList<>(voronoiDiagram.getNumGeometries());
        for(int i=0;i<voronoiDiagram.getNumGeometries();i++)
        {
            Polygon poly=(Polygon)voronoiDiagram.getGeometryN(i);
            results.add(toGTLEnvelope(poly.getEnvelopeInternal()));
        }
        return results;
    }



    public static VectorSequence toGTLVectorSequence(CoordinateSequence cs){
        int dims = 2;// cs.getDimension();
        int s = cs.size();
        double [] coords = new double[dims*s];
        int k=0;
        for(int j =0;j<s;++j){
            for(int i=0;i<dims;++i){
                coords[k]=cs.getOrdinate(j,i);
                k++;
            }
        }
        return VectorSequence.create(coords,dims);
    }

    public static Envelope toJTSEnvelope(gtl.geom.Envelope  e2d){
        gtl.jts.geom.Envelope e = new Envelope(
                e2d.getLowOrdinate(0),e2d.getHighOrdinate(0),
                e2d.getLowOrdinate(1),e2d.getHighOrdinate(1));
        return e;
    }

    public static gtl.geom.Envelope toGTLEnvelope(Envelope  e2d){
        gtl.geom.Envelope e = new gtl.geom.Envelope(
                e2d.getMinX(),e2d.getMaxX(),e2d.getMinY(),e2d.getMaxY());
        return e;
    }

    public static gtl.jts.geom.Coordinate [] toJTSCoordinate(Vertex2D[] ring){
        Coordinate[] cc = new Coordinate[ring.length];
        int i=0;
        for(Vertex2D v: ring){
            cc[i] = new Coordinate(v.x,v.y);
            ++i;
        }
        return cc;
    }

    public static gtl.jts.geom.Coordinate [] toJTSCoordinate(Vector[] ring){
        Coordinate[] cc = new Coordinate[ring.length];
        int i=0;
        for(Vector v: ring){
            cc[i] = new Coordinate(v.getX(),v.getY());
            ++i;
        }
        return cc;
    }

    public static gtl.jts.geom.Coordinate  toJTSCoordinate(Vertex2D v){
        return new Coordinate(v.x,v.y);
    }


    public static gtl.jts.geom.Coordinate  toJTSCoordinate(Vector v){
        return  new Coordinate(v.getX(),v.getY());
    }

    /**
     * 转换成JTS的Geometry
     * @param e2d
     * @return
     */
    public static Geometry toJTSGeometry(Envelope e2d){
        gtl.jts.geom.Envelope e = new Envelope(
                e2d.getMinX(),e2d.getMaxX(),e2d.getMinY(),e2d.getMaxY());
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.toGeometry(e);
    }

    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.Envelope e){
        double [] minXYZ=e.getLowCoordinates();
        double [] maxXYZ=e.getHighCoordinates();
        gtl.jts.geom.Envelope jtse = new Envelope(
                minXYZ[0],maxXYZ[0],minXYZ[1],maxXYZ[1]);
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.toGeometry(jtse);
    }

    /**
     * 转换成JTS的Geometry
     * @param
     * @return
     */
    public static Geometry toJTSGeometry(double x, double y){
        return new GeometryFactory().createPoint(new Coordinate(x,y));
    }

    public static Geometry toJTSGeometry(gtl.geom.Point p){
        //return new GeometryFactory().createPoint(new Coordinate(p.getX(),p.getY(),p.getZ()));
        return new GeometryFactory().createPoint(new Coordinate(p.getX(),p.getY()));
    }

    public static Geometry toJTSGeometry(gtl.geom.MultiPoint mp){
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        Point [] pa = new Point[mp.size()];
        for(int i=0;i<mp.size();++i){
            pa[i] = (Point)toJTSGeometry(mp.getPoint(i));
        }
        return gf.createMultiPoint(pa) ;
    }

    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.LineSegment e){
        Coordinate[] cc = new Coordinate[2];
//        cc[0]=new Coordinate(e.getStartPoint().getX(),e.getStartPoint().getY(),e.getStartPoint().getZ());
//        cc[1]=new Coordinate(e.getEndPoint().getX(),e.getEndPoint().getY(),e.getEndPoint().getZ());
        cc[0]=new Coordinate(e.getStartPoint().getX(),e.getStartPoint().getY());
        cc[1]=new Coordinate(e.getEndPoint().getX(),e.getEndPoint().getY());
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.createLineString(cc);
    }

    /**
     * 转换成JTS的Geometry
     * @param e
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.LineString e){
        VectorSequence vs = e.getVertices();
        int s = vs.size();
        Coordinate[] cc = new Coordinate[vs.size()];
        for (int i=0;i<s;++i) {
            //cc[i]=new Coordinate(vs.getX(i),vs.getY(i),vs.getZ(i));
            cc[i]=new Coordinate(vs.getX(i),vs.getY(i));
        }
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.createLineString(cc);
    }

    public static Geometry toJTSGeometry(gtl.geom.LinearRing e){
        VectorSequence vs = e.getVertices();
        int s = vs.size();
        Coordinate[] cc = new Coordinate[vs.size()];
        for (int i=0;i<s;++i) {
            //cc[i] = new Coordinate(vs.getX(i), vs.getY(i), vs.getZ(i));
            cc[i] = new Coordinate(vs.getX(i), vs.getY(i));
        }
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        return gf.createLinearRing(cc);
    }

    public static Geometry toJTSGeometry(gtl.geom.MultiLineString mp){
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        LineString [] pa = new LineString[mp.size()];
        for(int i=0;i<mp.size();++i){
            pa[i] = (LineString)toJTSGeometry(mp.getGeometry(i));
        }
        return gf.createMultiLineString(pa) ;
    }

    public static Geometry toJTSGeometry(gtl.geom.Polygon t){
        LinearRing shell =(LinearRing) toJTSGeometry(t.getExteriorRing());
        gtl.geom.LinearRing[] gholes= t.getInteriorRings();
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        if(gholes==null){
            return gf.createPolygon(shell);
        }
        else{
            LinearRing [] holes = new LinearRing[gholes.length];
            for(int i=0;i<holes.length;++i){
                holes[i]=(LinearRing)toJTSGeometry(gholes[i]);
            }
            return gf.createPolygon(shell,holes);
        }
    }

    public static Geometry toJTSGeometry(gtl.geom.MultiPolygon mp){
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        Polygon [] pa = new Polygon[mp.size()];
        for(int i=0;i<mp.size();++i){
            pa[i] = (Polygon)toJTSGeometry(mp.getGeometry(i));
        }
        return gf.createMultiPolygon(pa) ;
    }
    /**
     * 转换成JTS的Geometry
     * @param t
     * @return
     */
    public static Geometry toJTSGeometry(Triangle t){
        gtl.jts.geom.GeometryFactory gf = new GeometryFactory();
        Coordinate[] coordinates = new Coordinate[4];
        int i=0;
        for(Vector v: t.getVertices()){
            if(v.getDimension()==2)
                coordinates[i] = new Coordinate(v.getX(),v.getY());
//            else
//                coordinates[i] = new Coordinate(v.getX(),v.getY(),v.getZ());
            i++;
        }
        coordinates[3] = new Coordinate(coordinates[0]) ;
        gtl.jts.geom.Polygon p = gf.createPolygon(coordinates);
        return p;
    }


    /**
     * 将GTL Geoemtry转换成对应的 JTS Geometry
     * @param g
     * @return
     */
    public static Geometry toJTSGeometry(gtl.geom.Geometry g){
        Geometry jg = null;
        if(g instanceof gtl.geom.Point){
            jg = toJTSGeometry((gtl.geom.Point)g);
        }
        else if(g instanceof gtl.geom.MultiPoint){
            jg = toJTSGeometry((gtl.geom.MultiPoint)g);
        }
        else if(g instanceof gtl.geom.LineString){
            jg = toJTSGeometry((gtl.geom.LineString)g);
        }
        else if(g instanceof gtl.geom.MultiLineString){
            jg = toJTSGeometry((gtl.geom.MultiLineString)g);
        }
        else if(g instanceof gtl.geom.LinearRing){
            jg = toJTSGeometry((gtl.geom.LinearRing)g);
        }
        else if(g instanceof gtl.geom.Polygon){
            jg = toJTSGeometry((gtl.geom.Polygon)g);
        }
        else if(g instanceof gtl.geom.MultiPolygon){
            jg = toJTSGeometry((gtl.geom.MultiPolygon)g);
        }
        else{
            System.out.println("jts does not support this gtl geometry type");
        }
        return jg;
    }

    public static gtl.geom.Point toGTLPoint(Point geom){
        Point pp = (Point)(geom);
        return new gtl.geom.Point(pp.getX(),pp.getY());
    }

    public static gtl.geom.MultiPoint toGTLMultiPoint(MultiPoint geom){
        MultiPoint pp = (MultiPoint)(geom);
        int ng = pp.getNumGeometries();
        gtl.geom.Point[] pa = new gtl.geom.Point[ng];
        for(int i=0;i<ng;++i){
            pa[i]= toGTLPoint((Point) pp.getGeometryN(i));
        }
        return new gtl.geom.MultiPoint(pa);
    }

    public static gtl.geom.LineString toGTLLineString(LineString geom){
        LineString pp = (LineString)(geom);
        return new gtl.geom.LineString(toGTLVectorSequence(pp.getCoordinateSequence()));
    }

    public static gtl.geom.LinearRing toGTLLinearRing(LinearRing geom){
        LinearRing pp = (LinearRing)(geom);
        return new gtl.geom.LinearRing(toGTLVectorSequence(pp.getCoordinateSequence()));
    }

    public static gtl.geom.MultiLineString toGTLMultiLineString(MultiLineString geom){
        MultiLineString pp = (MultiLineString)(geom);
        int ng = pp.getNumGeometries();
        gtl.geom.LineString[] pa = new gtl.geom.LineString[ng];
        for(int i=0;i<ng;++i){
            pa[i]= toGTLLineString((LineString) pp.getGeometryN(i));
        }
        return new gtl.geom.MultiLineString(pa);
    }

    public static gtl.geom.Polygon toGTLPolygon(Polygon geom){
        Polygon pp = (Polygon)(geom);
        gtl.geom.LinearRing s= toGTLLinearRing((LinearRing) pp.getExteriorRing());
        int n = pp.getNumInteriorRing();
        if(n>0){
            gtl.geom.LinearRing[] h = new gtl.geom.LinearRing[n];
            for(int i=0;i<n;++i){
                h[i] =  toGTLLinearRing((LinearRing) pp.getInteriorRingN(i));
            }
            return new gtl.geom.Polygon(s,h);
        }
        else
            return new gtl.geom.Polygon(s);
    }

    public static gtl.geom.MultiPolygon toGTLMultiPolygon(MultiPolygon geom){
        MultiPolygon pp = (MultiPolygon)(geom);
        int ng = pp.getNumGeometries();
        gtl.geom.Polygon[] pa = new gtl.geom.Polygon[ng];
        for(int i=0;i<ng;++i){
            pa[i]= toGTLPolygon((Polygon) pp.getGeometryN(i));
        }
        return new gtl.geom.MultiPolygon(pa);
    }

    public static gtl.geom.Geometry toGTLGeometry(Geometry geom){
        if (geom instanceof Point) {
            return toGTLPoint((Point)geom);
        }
        else if(geom instanceof MultiPoint){
            return toGTLMultiPoint((MultiPoint)geom);
        }
        else if(geom instanceof LineString){
            return toGTLLineString((LineString)geom);
        }
        else if(geom instanceof LinearRing){
            return toGTLLinearRing((LinearRing)geom);
        }
        else if(geom instanceof MultiLineString){
            return toGTLMultiLineString((MultiLineString)geom);
        }
        else if(geom instanceof Polygon){
            return toGTLPolygon((Polygon)geom);
        }
        else if(geom instanceof MultiPolygon){
            return toGTLMultiPolygon((MultiPolygon)geom);
        }
        else if(geom instanceof GeometryCollection){
            GeometryCollection pp = (GeometryCollection)(geom);
            int n = pp.getNumGeometries();
            ArrayList<gtl.geom.Geometry> ga = new ArrayList<>(n);
            for(int i=0;i<n;++i){
                ga.add(toGTLGeometry(pp.getGeometryN(i)));
            }
            return new gtl.geom.GeometryCollection(ga);
        }
        else{
            System.out.println("Unknown JTS Geometry Type");
        }
        return null;
    }



    public static String writeToWKT(gtl.geom.Geometry g){
        return new WKTWriter(g.getDimension()).write(toJTSGeometry(g));
    }

    public static gtl.geom.Geometry readFromWKT(String wkt){
        try {
            Geometry g=  (new WKTReader()).read(wkt);
            return toGTLGeometry(g);
        }
        catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }
}
