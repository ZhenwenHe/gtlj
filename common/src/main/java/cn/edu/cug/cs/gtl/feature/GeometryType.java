package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * GeometryType 继承自AttributeType，添加了一个坐标参照系；
 * 表示几何类型；
 */
public class GeometryType extends AttributeType {


    private static final long serialVersionUID = 3912834627796788298L;
    CoordinateReferenceSystem coordinateReferenceSystem;

    /**
     * 默认构造函数，什么也不做；
     */
    public GeometryType() {
    }

    /**
     * 构造函数，传入binding和坐标参照系crs，binding传递给父类构造函数，crs直接赋值；
     *
     * @param binding 数据类型相对路径+类型名称，如Boolean.class;
     * @param crs     坐标参照系
     */
    public GeometryType(Class<?> binding, CoordinateReferenceSystem crs) {
        super(Variant.OBJECT, binding);
        coordinateReferenceSystem = crs;
    }

    /**
     * 构造函数，仅传入binding，CRS赋一个默认值DefaultGeographicCRS.WGS84；
     *
     * @param binding 数据类型相对路径+类型名称，如Boolean.class;
     */
    public GeometryType(Class<?> binding) {
        super(Variant.OBJECT, binding);
        coordinateReferenceSystem = DefaultGeographicCRS.WGS84;
    }

    /**
     * 获取坐标参照系对象；
     *
     * @return 坐标参照系；
     */
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        return coordinateReferenceSystem;
    }

    /**
     * 克隆函数；根据binding和crs 重新构造一个对象并返回；
     *
     * @return 新对象；
     */
    @Override
    public Object clone() {
        return new GeometryType(
                this.binding, this.coordinateReferenceSystem);
    }

    /**
     * 读取函数；通过输入流读取对象的属性值；
     *
     * @param in 表示可以读取的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);
        this.coordinateReferenceSystem = (CoordinateReferenceSystem) ObjectUtils.load(in);
        return true;
    }

    /**
     * 存储函数；通过输出流，将对象写入，存储；
     *
     * @param out，表示可以写入的存储对象，可能是内存、文件、管道等
     * @return
     * @throws IOException
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        ObjectUtils.store(this.coordinateReferenceSystem, out);
        return true;
    }

    /**
     * 获取对象写成字节数组后的字节数组的长度；
     *
     * @return 字节数组的长度；
     */
    @Override
    public long getByteArraySize() {
        return super.getByteArraySize() + ObjectUtils.getByteArraySize(this.coordinateReferenceSystem);
    }
}
