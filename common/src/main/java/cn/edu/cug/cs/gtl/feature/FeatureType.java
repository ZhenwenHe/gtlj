package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.io.DataSchema;
import cn.edu.cug.cs.gtl.io.FileDataSplitter;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.util.StringUtils;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
FeatureType支持4种格式的字符串：
TSV ： 第一个字符串为要素类型名称
       第二个字符串为几何名称和类型，名称和类型之间以%连接
       后面的每个字串串为一个要素属性名
       每个字串之间\t分割
       例如：
       county_small\tWKT%POLYGON\tSTATEFP\tCOUNTYFP\tCOUNTYNS\tGEOID\tNAME
CSV ： 第一个字符串为要素类型名称
       第二个字符串为几何名称和类型，名称和类型之间以%连接
       后面的每个字串串为一个要素属性名
       每个字串之间逗号分割
       例如：
       county_small,WKT%POLYGON,STATEFP,COUNTYFP,COUNTYNS,GEOID,NAME
SSV ： 第一个字符串为要素类型名称
       第二个字符串为几何名称和类型，名称和类型之间以%连接
       后面的每个字串串为一个要素属性名
       每个字串之间一个或多个空格分割
       例如：
       county_small WKT%POLYGON STATEFP COUNTYFP COUNTYNS GEOID NAME
ByteString:
     是由字节数组转换成的十进制的数构成的字符串，每个byte转换成一个整数字符串，每个整数之间以\t分割；
     通过这种字符串转换Feature是无损的。
 */

/**
 * 要素类型，例如道路、建筑等
 * 以道路为例，其几何对象是线装LineString，属性有道路名称，道路类型、道路长度等。
 * 其创建采用FeatureTypeBuilder,示例代码如下：
 */
public class FeatureType implements DataSchema {


    private static final long serialVersionUID = -4627070846660440083L;
    private Identifier identifier;//仅供系统内部使用，外部通过名称查找
    private String name;//要素名称，在Data
    private GeometryDescriptor defaultGeometry;//要素类型的几何类型描述
    private List<AttributeDescriptor> properties;//要素类型的属性类型集合

    /**
     *
     */
    public FeatureType() {
        this.identifier = Identifier.create();
        this.name = null;
        this.defaultGeometry = null;
        this.properties = new ArrayList<AttributeDescriptor>();
    }

    /**
     * \
     *
     * @param name
     * @param defaultGeometry
     * @param properties
     */
    public FeatureType(String name, GeometryDescriptor defaultGeometry, Collection<AttributeDescriptor> properties) {
        this.identifier = Identifier.create();
        this.name = name;
        this.defaultGeometry = defaultGeometry;
        this.properties = new ArrayList<AttributeDescriptor>();
        this.properties.addAll(properties);
    }

    public FeatureType(Identifier identifier, String name, GeometryDescriptor defaultGeometry, Collection<AttributeDescriptor> properties) {
        this.identifier = identifier;
        this.name = name;
        this.defaultGeometry = defaultGeometry;
        this.properties = new ArrayList<AttributeDescriptor>();
        this.properties.addAll(properties);
    }

    public List<AttributeDescriptor> getAttributeDescriptors() {
        return properties;
    }

    public List<AttributeDescriptor> getDescriptors() {
        ArrayList<AttributeDescriptor> aa = new ArrayList<>(properties.size() + 1);
        aa.add(defaultGeometry);
        aa.addAll(properties);
        return aa;
    }

    public AttributeDescriptor getDescriptor(String name) {
        if (name.equals(defaultGeometry.getName())) {
            return defaultGeometry;
        } else {
            for (AttributeDescriptor a : properties) {
                if (name.equals(a.getName()))
                    return a;
            }
        }
        return null;
    }

    public GeometryDescriptor getGeometryDescriptor() {
        return defaultGeometry;
    }

    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        if (defaultGeometry == null) return null;
        if (defaultGeometry.getType() == null) return null;

        return defaultGeometry.getGeometryType().getCoordinateReferenceSystem();
    }

    @Override
    public Object clone() {
        ArrayList<AttributeDescriptor> aa = new ArrayList<>(properties.size());
        for (AttributeDescriptor a : properties) {
            aa.add((AttributeDescriptor) a.clone());
        }
        FeatureType ft = new FeatureType(this.name, (GeometryDescriptor) defaultGeometry.clone(),
                (Collection<AttributeDescriptor>) aa);
        ft.identifier.reset(this.identifier.longValue());
        return ft;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        identifier.load(in);
        name = StringUtils.load(in);
        defaultGeometry = (GeometryDescriptor) ObjectUtils.load(in);
        int n = in.readInt();
        if (n <= 0)
            return false;

        if (this.properties == null) {
            this.properties = new ArrayList<>(n);
        } else {
            this.properties = new ArrayList<>(n);
        }

        for (int i = 0; i < n; ++i) {
            this.properties.add((AttributeDescriptor) ObjectUtils.load(in));
        }
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        identifier.store(out);
        StringUtils.store(name, out);
        ObjectUtils.store(defaultGeometry, out);
        out.writeInt(properties.size());
        for (AttributeDescriptor a : properties)
            ObjectUtils.store(a, out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = identifier.getByteArraySize()
                + StringUtils.getByteArraySize(name)
                + ObjectUtils.getByteArraySize(defaultGeometry)
                + 4;
        for (AttributeDescriptor a : properties)
            len += ObjectUtils.getByteArraySize(a);

        return len;
    }

    @Override
    public String toString() {
        return byteString(this);
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setProperties(List<AttributeDescriptor> properties) {
        this.properties = properties;
    }

    public static String tsvString(FeatureType ft) {
        StringBuilder stringBuilder = new StringBuilder(
                ft.getName()
        );
        stringBuilder.append(FileDataSplitter.TSV.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getName());
        stringBuilder.append(FileDataSplitter.PERCENT.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getGeometryType().getBinding().getSimpleName().toUpperCase());
        for (AttributeDescriptor v : ft.getAttributeDescriptors()) {
            stringBuilder.append(FileDataSplitter.TSV.getDelimiter());
            stringBuilder.append(v.getAttributeType().getName());
        }
        //stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static FeatureType tsvString(String tsvString) {
        String[] columns = tsvString.split(FileDataSplitter.TSV.getDelimiter());
        FeatureTypeBuilder ftb = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName(columns[0])
                .setCoordinateReferenceSystem(null);

        String[] geomInfo = columns[1].split(FileDataSplitter.PERCENT.getDelimiter());
        assert geomInfo.length == 2;
        ftb.add(geomInfo[0], Geometry.getTypeBinding(Geometry.getType(geomInfo[1])));
        for (int i = 2; i < columns.length; ++i)
            ftb.add(columns[i], Variant.STRING);
        return ftb.build();
    }


    public static String ssvString(FeatureType ft) {
        StringBuilder stringBuilder = new StringBuilder(
                ft.getName()
        );
        stringBuilder.append(FileDataSplitter.SSV.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getName());
        stringBuilder.append(FileDataSplitter.PERCENT.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getGeometryType().getBinding().getSimpleName().toUpperCase());
        for (AttributeDescriptor v : ft.getAttributeDescriptors()) {
            stringBuilder.append(FileDataSplitter.SSV.getDelimiter());
            stringBuilder.append(v.getAttributeType().getName());
        }
        //stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static FeatureType ssvString(String ssvString) {
        //只能处理一个空格分割的情况
        //String[] columns = ssvString.split(FileDataSplitter.SSV.getDelimiter());
        String[] columns = ssvString.split("\\s+");

        FeatureTypeBuilder ftb = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName(columns[0])
                .setCoordinateReferenceSystem(null);

        String[] geomInfo = columns[1].split(FileDataSplitter.PERCENT.getDelimiter());
        assert geomInfo.length == 2;
        ftb.add(geomInfo[0], Geometry.getTypeBinding(Geometry.getType(geomInfo[1])));
        for (int i = 2; i < columns.length; ++i)
            ftb.add(columns[i], Variant.STRING);
        return ftb.build();
    }

    public static String csvString(FeatureType ft) {
        StringBuilder stringBuilder = new StringBuilder(
                ft.getName()
        );
        stringBuilder.append(FileDataSplitter.CSV.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getName());
        stringBuilder.append(FileDataSplitter.PERCENT.getDelimiter());
        stringBuilder.append(ft.getGeometryDescriptor().getGeometryType().getBinding().getSimpleName().toUpperCase());
        for (AttributeDescriptor v : ft.getAttributeDescriptors()) {
            stringBuilder.append(FileDataSplitter.CSV.getDelimiter());
            stringBuilder.append(v.getAttributeType().getName());
        }
        //stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static FeatureType csvString(String csvString) {
        String[] columns = csvString.split(FileDataSplitter.CSV.getDelimiter());
        FeatureTypeBuilder ftb = new FeatureTypeBuilder()
                .setIdentifier(Identifier.create())
                .setName(columns[0])
                .setCoordinateReferenceSystem(null);

        String[] geomInfo = columns[1].split(FileDataSplitter.PERCENT.getDelimiter());
        assert geomInfo.length == 2;
        ftb.add(geomInfo[0], Geometry.getTypeBinding(Geometry.getType(geomInfo[1])));
        for (int i = 2; i < columns.length; ++i)
            ftb.add(columns[i], Variant.STRING);
        return ftb.build();
    }

    public static FeatureType byteString(String s) throws IOException {
        FeatureType ft = new FeatureType();
        ft.loadFromByteString(s);
        return ft;
    }

    public static String byteString(FeatureType ft) {
        try {
            return ft.storeToByteString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static FeatureType fromString(String s) {
        if (s.contains(FileDataSplitter.TSV.getDelimiter())) {
            return tsvString(s);
        } else if (s.contains(FileDataSplitter.CSV.getDelimiter())) {
            return csvString(s);
        } else if (s.contains(FileDataSplitter.SEMICOLON.getDelimiter())) {
            try {
                return byteString(s);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else if (s.contains(FileDataSplitter.SSV.getDelimiter())) {
            return ssvString(s);
        } else
            return null;
    }
}
