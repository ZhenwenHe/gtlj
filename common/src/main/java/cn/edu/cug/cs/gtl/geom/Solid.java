package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.ObjectUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个封闭的外壳Shell，构成体，可以带有一个或多个洞Hole
 * 一个Shell必须是封闭的，由多个多边形构成，
 * 一个Hole多边形封闭区域
 */
public class Solid extends Geometry implements Polyhedral {
    private static final long serialVersionUID = 1L;
    Shell shell;
    List<Hole> holes;
    Hole hole;

    public Solid() {
        this.geometryType = SOLID;
    }

    public Solid(int dim) {
        super.makeDimension(dim);
        this.geometryType = SOLID;
    }

    public Solid(Shell shell, List<Hole> holes) {
        this.shell = shell;
        this.holes = holes;
        this.geometryType = SOLID;
    }

    @Override
    public boolean isEmpty() {
        return shell.getByteArraySize() == 0;
    }

    /*@Override
    public Object clone() {
        return super.clone();
    }*/

    @Override
    public boolean load(DataInput in) throws IOException {
        this.shell.load(in);
        this.hole.load(in);
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        this.shell.store(out);
        this.hole.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        len = shell.getByteArraySize() + holes.size();
        return len;
    }

    public static class Shell extends Geometry implements Serializable {
        private static final long serialVersionUID = 1L;

        private List<Polygonal> polygonals;

        @Override
        public Solid clone() {
            return (Solid) super.clone();
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean load(DataInput in) throws IOException {
            super.load(in);
            int len = in.readInt();
            if (len > 0) {
                polygonals = new ArrayList<>();
                for (int i = 0; i < len; ++i) {
                    polygonals.add((Polygonal) ObjectUtils.load(in));
                }
            }
            return true;
        }

        @Override
        public boolean store(DataOutput out) throws IOException {
            super.store(out);
            int len = 0;
            if (this.polygonals.size() != 0) len = this.polygonals.size();
            out.writeInt(len);
            if (len > 0) {
                for (Polygonal polygonal : this.polygonals) {
                    ObjectUtils.store(polygonal, out);
                }
            }
            return true;
        }

        @Override
        public long getByteArraySize() {
            long lenth = polygonals.size();
            return lenth;
        }
    }

    public static class Hole extends Geometry implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<Polygonal> polygonals;

/*        @Override
        public Object clone() {
            Hole hole = new Hole();

            return hole;
        }*/

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean load(DataInput in) throws IOException {
            super.load(in);
            int len = in.readInt();
            if (len > 0) {
                polygonals = new ArrayList<>();
                for (int i = 0; i < len; ++i) {
                    polygonals.add((Polygonal) ObjectUtils.load(in));
                }
            }
            return true;
        }

        @Override
        public boolean store(DataOutput out) throws IOException {
            super.store(out);
            int lenth = 0;
            if (this.polygonals.size() != 0) lenth = this.polygonals.size();
            out.writeInt(lenth);
            if (lenth > 0) {
                for (Polygonal polygonal : this.polygonals) {
                    ObjectUtils.store(polygonal, out);
                }
            }
            return true;
        }

        @Override
        public long getByteArraySize() {
            long lenth = polygonals.size();
            return lenth;
        }
    }

    @Override
    public TextureParameter getTextureParameter() {
        return null;
    }

    @Override
    public void setTextureParameter(TextureParameter textureParameter) {

    }
}
