package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Property;
import cn.edu.cug.cs.gtl.common.Variant;
import cn.edu.cug.cs.gtl.common.VariantCollection;
import cn.edu.cug.cs.gtl.config.Config;
import cn.edu.cug.cs.gtl.geom.Geometry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeatureBuilder {
    protected FeatureType featureType;
    protected Identifier identifier;
    protected String name;
    protected Geometry geometry;
    protected VariantCollection values;
    private Map<String, Integer> proNameIndexMap;


    public FeatureBuilder(FeatureType featureType) {
        this.featureType = featureType;
        this.identifier = Identifier.randomIdentifier();
        this.name = new String("unknown");
        this.geometry = null;
        this.values = new VariantCollection();
        this.proNameIndexMap = new HashMap<>();
        List<AttributeDescriptor> descriptors = featureType.getDescriptors();
        for (int i = 0; i < descriptors.size(); i++) {
            proNameIndexMap.put(descriptors.get(i).getName(), i);
        }
    }

    public FeatureBuilder setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public FeatureBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public FeatureBuilder add(Geometry g) {
        if (Config.featureValidating) {
            Class<?> cls = this.featureType.getGeometryDescriptor().getGeometryType().getBinding();
            if (cls.equals(g.getClass())) {
                geometry = g;
                return this;
            }
        } else {
            geometry = g;
            return this;
        }
        return null;
    }

    public FeatureBuilder add(Variant g) {
        if (Config.featureValidating) {
            int i = this.values.size();
            List<AttributeDescriptor> attributeDescriptors = this.featureType.getAttributeDescriptors();
            int attributeCount = attributeDescriptors.size();
            if (i < attributeCount) {
                AttributeDescriptor ad = attributeDescriptors.get(i);
                if (ad.getAttributeType().getType() == g.getType()) {
                    values.add(g);
                    return this;
                }
            }
        } else {
            values.add(g);
            return this;
        }
        return null;
    }

    public FeatureBuilder set(int index, Variant v) {
        if (Config.featureValidating) {
            List<AttributeDescriptor> attributeDescriptors = this.featureType.getAttributeDescriptors();
            if (index < attributeDescriptors.size() && v.getType() == attributeDescriptors.get(index).getAttributeType().getType()) {
                values.set(index, v);
                return this;
            }
        } else {
            values.set(index, v);
            return this;
        }
        return null;
    }

    public FeatureBuilder add(byte v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(short v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(int v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(float v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(double v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(String v) {
        return add(new Variant(v));
    }

    public FeatureBuilder add(Object v) {
        return add(new Variant(v));
    }


    public FeatureBuilder set(String propName, Variant v) {
        return set(proNameIndexMap.get(propName), v);
    }

    public FeatureBuilder set(Property p) {
        return set(p.getName(), p);
    }


    public Feature build() {
        Feature f = new Feature(this.identifier,
                this.name, this.featureType, this.geometry, this.values);
        this.identifier = Identifier.randomIdentifier();
        return f;
    }

    public void clear() {
        this.identifier = Identifier.randomIdentifier();
        this.name = new String("unknown");
        this.geometry = null;
        this.values = new VariantCollection();
    }
}
