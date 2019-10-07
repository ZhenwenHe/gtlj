package gtl.scene;

import gtl.common.Identifier;
import gtl.common.Pair;
import gtl.common.PropertySet;
import gtl.common.Status;
import gtl.feature.Feature;
import gtl.feature.FeatureCollection;
import gtl.feature.FeatureType;
import gtl.io.DataContent;
import gtl.io.Serializable;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.*;

public class Layer extends ArrayList<Pair<FeatureType, FeatureCollection>> implements DataContent {
    PropertySet properties;
    Status status;
    String name;
    Identifier identifier;

    public Layer(int initialCapacity) {
        super(initialCapacity);
    }

    public Layer() {
    }

    public Layer(@NotNull Collection<? extends Pair<FeatureType, FeatureCollection>> c) {
        super(c);
    }

    public PropertySet getProperties() {
        return properties;
    }

    public void setProperties(PropertySet properties) {
        this.properties = properties;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return false;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return false;
    }

    @Override
    public Identifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
