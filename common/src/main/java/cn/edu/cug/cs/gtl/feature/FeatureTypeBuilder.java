package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Pair;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.geom.Geometry;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.util.*;

public class FeatureTypeBuilder {

    Pair<Identifier, String> names;
    Pair<String, Object> geometryType;
    CoordinateReferenceSystem coordinateReferenceSystem;
    ArrayList<AttributeDescriptor> attributeDescriptors;

    public FeatureTypeBuilder() {
        names = new Pair<>();
        geometryType = new Pair<>();
        attributeDescriptors = new ArrayList<>();
    }

    public FeatureTypeBuilder setIdentifier(Identifier identifier) {
        names.setKey(identifier);
        return this;
    }

    public FeatureTypeBuilder setName(String name) {
        names.setValue(name);
        return this;
    }

    public FeatureTypeBuilder add(String name, Class<?> binding) {
        if (Geometry.class.isAssignableFrom(binding)) {
            geometryType.setKey(name);
            geometryType.setValue(binding);
        } else {
            attributeDescriptors.add(new AttributeDescriptor(Variant.OBJECT, binding, name));
        }
        return this;
    }

    public FeatureTypeBuilder add(String name, int type) {
        attributeDescriptors.add(new AttributeDescriptor(type, null, name));
        return this;
    }

    public FeatureTypeBuilder setCoordinateReferenceSystem(CoordinateReferenceSystem coordinateReferenceSystem) {
        this.coordinateReferenceSystem = coordinateReferenceSystem;
        return this;
    }

    public FeatureType build() {
        return new FeatureType(
                names.getKey(), names.getValue(),
                new GeometryDescriptor(
                        (Class<?>) geometryType.getValue(),
                        this.coordinateReferenceSystem,
                        geometryType.getKey(),
                        1, 1, true, null),
                attributeDescriptors);
    }

    public ArrayList<AttributeDescriptor> getAttributeDescriptors() {
        return attributeDescriptors;
    }
}
