package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.geom.Boundable;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.io.DataContent;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.io.wkt.WKTReader;
import cn.edu.cug.cs.gtl.io.wkt.WKTWriter;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Status;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.common.VariantCollection;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collection;

/*
Feature支持4种格式的字符串：
TSV ： 第一个字符串为几何对象，为一个WKT格式的字符串
       后面的每个字串串为一个要素属性值，每个属性值以\t分割
       例如：
       POLYGON ((-156.309467 20.80195,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532))\t11057904285\tH2030\t+20.8018570\t-156.3091111 ;

CSV ： 第一个字符串为几何对象，为一个WKT格式的字符串，该字符串被一对双引号包括
       后面的每个字串串为一个要素属性值，每个属性值以逗号分割
       例如：
       "POLYGON ((-156.309467 20.80195,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532))",11057904285, H2030, +20.8018570, -156.3091111 ;

SSV ： 第一个字符串为几何对象，为一个WKT格式的字符串，该字符串被一对双引号包括
       后面的每个字串串为一个要素属性值，每个属性值以一个或多个空格分割
       例如：
       "POLYGON ((-156.309467 20.80195,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532))"   11057904285   H2030   +20.8018570   -156.3091111 ;

ByteString:
     是由字节数组转换成的十进制的数构成的字符串，每个byte转换成一个整数字符串，每个整数之间以\t分割；
     通过这种字符串转换Feature是无损的。
 */
public class Feature implements DataContent, Boundable {


    private static final long serialVersionUID = 8753966554771979133L;
    protected transient FeatureType featureType = null;//指向全局的FeatureType，不需要存储
    protected String featureTypeName = "unknown";//记录FeatureType的唯一名称，需要存储
    protected Identifier identifier = Identifier.create();
    protected String name = "unknown";
    protected Geometry geometry = null;
    protected VariantCollection values = null;
    protected FeatureStatus status = new FeatureStatus(0);

    public void setFeatureType(FeatureType featureType) {
        this.featureTypeName = featureType.getName();
        this.featureType = featureType;
    }

    public void setFeatureType(String featureTypeName) {
        this.featureTypeName = featureTypeName;
    }

    public String getFeatureTypeName() {
        return this.featureTypeName;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public void setValues(VariantCollection values) {
        this.values = values;
    }

    public void setStatus(Status status) {
        if (status instanceof FeatureStatus)
            this.status = (FeatureStatus) status;
        else
            throw new IllegalArgumentException("Status must be a Feature Status Object");
    }

    public Feature() {
    }

    protected Feature(Identifier id, String name, FeatureType type, Geometry geometry, Collection<Variant> values) {
        this.featureType = type;
        this.featureTypeName = type.getName();
        this.name = name;
        this.geometry = geometry;
        this.identifier.reset(id.longValue());
        this.values = new VariantCollection(values.size());
        this.values.addAll(values);
    }

    protected Feature(Identifier id, String name, FeatureType type, Geometry geometry, VariantCollection values) {
        this.featureType = type;
        this.featureTypeName = type.getName();
        this.name = name;
        this.geometry = geometry;
        this.identifier.reset(id.longValue());
        this.values = values;
    }


    protected VariantCollection


    cloneValues() {
        return (VariantCollection) this.values.clone();
    }


    public VariantCollection getValues() {
        return values;
    }

    public FeatureType getType() {
        return this.featureType;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    public Geometry getGeometry() {
        return geometry;
    }


    @Override
    public Object clone() {
        return (Object) new Feature(
                (Identifier) (this.identifier.clone()),
                this.name,
                this.featureType,
                (Geometry) this.geometry.clone(),
                this.cloneValues()
        );
    }


    @Override
    public boolean load(DataInput in) throws IOException {
        this.status.load(in);
        this.featureTypeName = StringUtils.load(in);
        this.identifier.load(in);
        this.name = StringUtils.load(in);
        int geomType = in.readInt();
        if (geomType >= 0) {
            Geometry g = Geometry.create(geomType, 2);
            g.load(in);
            this.geometry = g;
        } else {
            this.geometry = null;
        }
        int s = in.readInt();
        if (s > 0) {
            this.values = new VariantCollection(s);
            this.values.load(in);
        } else {
            this.values = null;
        }
        this.featureType = null;
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.status.store(out);
        StringUtils.store(this.featureTypeName, out);
        this.identifier.store(out);
        StringUtils.store(this.name, out);

        if (this.geometry != null) {
            int s = this.geometry.getType();
            out.writeInt(s);
            this.geometry.store(out);
        } else {
            out.writeInt(-1);
        }


        if (this.values != null) {
            int s = this.values.size();
            out.writeInt(s);
            if (s > 0) this.values.store(out);
        } else {
            out.writeInt(0);
        }

        return true;
    }

    @Override
    public long getByteArraySize() {
        long l = this.status.getByteArraySize()
                + StringUtils.getByteArraySize(this.featureTypeName)
                + this.identifier.getByteArraySize()
                + StringUtils.getByteArraySize(this.name) + 8;
        if (this.geometry != null) l += this.geometry.getByteArraySize();
        if (this.values != null) l += this.values.getByteArraySize();
        return l;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feature)) return false;

        Feature feature = (Feature) o;

        if (featureType != null ? !featureType.equals(feature.featureType) : feature.featureType != null) return false;
        if (!getFeatureTypeName().equals(feature.getFeatureTypeName())) return false;
        if (!getIdentifier().equals(feature.getIdentifier())) return false;
        if (!getName().equals(feature.getName())) return false;
        if (getGeometry() != null ? !getGeometry().equals(feature.getGeometry()) : feature.getGeometry() != null)
            return false;
        if (getValues() != null ? !getValues().equals(feature.getValues()) : feature.getValues() != null) return false;
        return getStatus().equals(feature.getStatus());
    }

    @Override
    public int hashCode() {
        int result = featureType != null ? featureType.hashCode() : 0;
        result = 31 * result + getFeatureTypeName().hashCode();
        result = 31 * result + getIdentifier().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getGeometry() != null ? getGeometry().hashCode() : 0);
        result = 31 * result + (getValues() != null ? getValues().hashCode() : 0);
        result = 31 * result + getStatus().hashCode();
        return result;
    }


    public static Feature ssvString(String line, final FeatureType featureType) {
        //"\"POLYGON ((-156.309467 20.80195,-156.309092 20.802154,-156.309004 20.802181,-156.308841 20.801928,-156.308764 20.801774,-156.308794 20.801741,-156.308857 20.801705,-156.308952 20.801653,-156.309168 20.801543,-156.309215 20.801532,-156.30942 20.801851,-156.309428 20.801869,-156.309467 20.80195))\"                11057904285             H2030   0       3035    +20.8018570     -156.3091111\n" ;
        int i = line.lastIndexOf('\"');
        String geom = line.substring(0, i + 1).trim();
        String attributes = line.substring(i + 1).trim();

        final WKTReader wktReader = WKTReader.create();
        FeatureBuilder featureBuilder = new FeatureBuilder(featureType)
                .setIdentifier(Identifier.create())
                .setName("")
                .add(wktReader.read(geom));

        //由于有些文件的属性个数不相等，无法判断究竟有多少个属性，直接采用简单的方法将所有属性当做一个单独的字符串处理。
        if (featureType.getAttributeDescriptors().size() <= 1) {
            featureBuilder.add(attributes);
        } else {
            String[] columns = attributes.split("\\s+");
            for (i = 0; i < columns.length; ++i)
                featureBuilder.add(columns[i]);
        }

        return featureBuilder.build();
    }

    public static Feature csvString(String line, final FeatureType featureType) {
        int i = line.lastIndexOf('\"');
        String geom = line.substring(0, i + 1).trim();
        String attributes = line.substring(i + 1).trim();
        String[] columns = attributes.split(FileDataSplitter.CSV.getDelimiter());
        final WKTReader wktReader = WKTReader.create();
        FeatureBuilder featureBuilder = new FeatureBuilder(featureType)
                .setIdentifier(Identifier.create())
                .setName("")
                .add(wktReader.read(geom));

        for (i = 0; i < columns.length; ++i)
            featureBuilder.add(columns[i]);
        return featureBuilder.build();
    }

    public static Feature tsvString(String line, final FeatureType featureType) {
        String[] columns = line.split(FileDataSplitter.TSV.getDelimiter());
        final WKTReader wktReader = WKTReader.create();
        FeatureBuilder featureBuilder = new FeatureBuilder(featureType)
                .setIdentifier(Identifier.create())
                .setName("")
                .add(wktReader.read(columns[0]));

        for (int i = 1; i < columns.length; ++i)
            featureBuilder.add(columns[i]);

        return featureBuilder.build();
    }


    public static String wktString(Feature f) {
        return WKTWriter.create(2).write(f.getGeometry());
    }

    public static String tsvString(Feature g) {
        StringBuilder stringBuilder = new StringBuilder(WKTWriter.create(2).write(g.getGeometry()));
        if (g.getValues() != null) {
            for (Variant v : g.getValues()) {
                stringBuilder.append(FileDataSplitter.TSV.getDelimiter());
                stringBuilder.append(v.toString());
            }
        }
        return stringBuilder.toString();
    }

    public static String csvString(Feature g) {
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(WKTWriter.create(2).write(g.getGeometry()));
        stringBuilder.append("\"");
        if (g.getValues() != null) {
            for (Variant v : g.getValues()) {
                stringBuilder.append(FileDataSplitter.CSV.getDelimiter());
                stringBuilder.append(v.toString());
            }
        }
        return stringBuilder.toString();
    }

    public static String ssvString(Feature g) {
        StringBuilder stringBuilder = new StringBuilder("\"");
        stringBuilder.append(WKTWriter.create(2).write(g.getGeometry()));
        stringBuilder.append("\"");
        if (g.getValues() != null) {
            for (Variant v : g.getValues()) {
                stringBuilder.append(FileDataSplitter.SSV.getDelimiter());
                stringBuilder.append(v.toString());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Envelope getEnvelope() {
        Geometry g = getGeometry();
        if (g == null) return null;
        return g.getEnvelope();
    }
}
