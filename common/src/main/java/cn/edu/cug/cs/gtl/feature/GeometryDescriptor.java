package cn.edu.cug.cs.gtl.feature;

import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * 本类继承自AttributeDescriptor，没有添加任何新的属性；
 */
public class GeometryDescriptor extends AttributeDescriptor {


    private static final long serialVersionUID = -3318398426410616397L;

    /**
     * 默认构造函数，什么也不做；
     */
    public GeometryDescriptor() {
    }

    /**
     * 构造函数，传入binding 和坐标参照系crs ，构造一个GeometryType类型对象；
     * 然后调用父类构造函数；
     *
     * @param binding
     * @param crs
     * @param name
     * @param min
     * @param max
     * @param isNillable
     * @param defaultValue
     */
    protected GeometryDescriptor(Class<?> binding, CoordinateReferenceSystem crs, String name, int min, int max, boolean isNillable, Object defaultValue) {
        super(new GeometryType(binding, crs), name, min, max, isNillable, defaultValue);
    }

    /**
     * 构造函数，直接传入GeometryType 类型对象 type，以及其他属性值，调用父类构造函数；
     *
     * @param type         GeometryType 类型对象
     * @param name
     * @param min
     * @param max
     * @param isNillable
     * @param defaultValue
     */
    protected GeometryDescriptor(GeometryType type, String name, int min, int max, boolean isNillable, Object defaultValue) {
        super(type, name, min, max, isNillable, defaultValue);
    }

    /**
     * 构造函数；仅传入GeometryType 类型对象 type 和name，其他属性为默认值；
     *
     * @param type GeometryType 类型对象
     * @param name
     */
    protected GeometryDescriptor(GeometryType type, String name) {
        super(type, name, 1, 1, false, null);
    }

    /**
     * 构造函数，仅传入binding和name，根据binding构造GeometryType类型对象，然后调用父类构造函数；
     *
     * @param binding GeometryType类型对象的数据类型的相对路径+类名，如Boolean.class;
     * @param name
     */
    protected GeometryDescriptor(Class<?> binding, String name) {
        super(new GeometryType(binding), name, 1, 1, false, null);
    }

    /**
     * 获取GeometryType；
     *
     * @return GeometryType；
     */
    public GeometryType getGeometryType() {
        return (GeometryType) super.getType();
    }

    /**
     * 获取GeometryType的坐标参照系；
     *
     * @return crs；
     */
    public CoordinateReferenceSystem getCoordinateReferenceSystem() {
        return this.getGeometryType().getCoordinateReferenceSystem();
    }

    /**
     * 克隆函数，根据本对象的属性值重新构造一个新对象并返回；
     *
     * @return 新对象；
     */
    @Override
    public Object clone() {
        return new GeometryDescriptor((GeometryType) this.getType().clone(), this.name, this.minOccurs, this.maxOccurs, this.isNillable, this.defaultValue);
    }
}
