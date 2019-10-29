package cn.edu.cug.cs.gtl.index.strtree;

import cn.edu.cug.cs.gtl.geom.Geometry;
import cn.edu.cug.cs.gtl.index.Entry;

import java.util.Collection;

public class DefaultVisitor extends AbstractVisitor {
    protected Collection<Object> objects = null;

    public DefaultVisitor() {
        //this.objects = new ArrayList<Object>();
    }

    @Override
    public void visitData(Entry in) {
        //this.objects.add(in);
    }

    @Override
    public void visitGeometry(Geometry in) {
        System.out.println(in.toString());
        //this.objects.add(in);
    }

    @Override
    public void visitObject(Object in) {
        //this.objects.add(in);
    }

    public Collection<Object> getObjects() {
        return objects;
    }
}
