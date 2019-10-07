package cn.edu.cug.cs.gtl.scene;

import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.PropertySet;
import cn.edu.cug.cs.gtl.common.Status;
import cn.edu.cug.cs.gtl.geom.Envelope;
import cn.edu.cug.cs.gtl.io.DataContent;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Scene extends ArrayList<Layer> implements DataContent {
    PropertySet properties;
    Status status;
    String name;
    Identifier identifier;
    Envelope envelope;

    public Scene(int initialCapacity) {
        super(initialCapacity);
    }

    public Scene() {
    }

    public Scene(@NotNull Collection<? extends Layer> c) {
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

    public Envelope getEnvelope() {
        return envelope;
    }

    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }

    @Override
    public Identifier getIdentifier() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        return false;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        return false;
    }
}
