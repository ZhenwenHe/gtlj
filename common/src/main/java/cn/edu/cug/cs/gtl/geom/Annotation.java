package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 文本标注类
 */
public class Annotation extends Point {
    private static final long serialVersionUID = -53680446996870561L;
    private String text = null;
    private Font font = null;
    private Vector baselineDirection;//基线方向


    public Annotation() {
        this.text = null;
        this.font = null;
    }

    public Annotation(int dim) {
        super(dim);
        this.text = null;
        this.font = null;
    }

    public Annotation(String text, Font font) {
        this.text = text;
        this.font = font;
    }

    public Annotation(int dim, String text, Font font) {
        super(dim);
        this.text = text;
        this.font = font;
    }

    public Annotation(double x, double y, double z, String text, Font font) {
        super(x, y, z);
        this.text = text;
        this.font = font;
    }

    public Annotation(double x, double y, String text, Font font) {
        super(x, y);
        this.text = text;
        this.font = font;
    }

    public Annotation(Vector v, String text, Font font) {
        super(v);
        this.text = text;
        this.font = font;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Vector getBaselineDirection() {
        return baselineDirection;
    }

    public void setBaselineDirection(Vector baselineDirection) {
        this.baselineDirection = baselineDirection;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        super.load(in);

        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        super.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return super.getByteArraySize();
    }

    @Override
    public Annotation clone() {
        Annotation p = (Annotation) super.clone();
        return p;
    }
}
