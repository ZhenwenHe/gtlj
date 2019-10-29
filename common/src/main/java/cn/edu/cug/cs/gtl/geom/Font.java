package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.jts.util.StringUtil;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 对应Windows上的LOGFONT结构体
 * https://docs.microsoft.com/zh-cn/windows/desktop/api/wingdi/ns-wingdi-taglogfonta
 */
public class Font implements Serializable {
    int height;
    int width;
    int escapement;
    int orientation;
    int weight;
    boolean italic;
    boolean underline;
    boolean strikeOut;
    int charSet;
    int outPrecision;
    int clipPrecision;
    int quality;
    int pitchAndFamily;
    String faceName;
    float depth;

    public Font() {
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getEscapement() {
        return escapement;
    }

    public void setEscapement(int escapement) {
        this.escapement = escapement;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isStrikeOut() {
        return strikeOut;
    }

    public void setStrikeOut(boolean strikeOut) {
        this.strikeOut = strikeOut;
    }

    public int getCharSet() {
        return charSet;
    }

    public void setCharSet(int charSet) {
        this.charSet = charSet;
    }

    public int getOutPrecision() {
        return outPrecision;
    }

    public void setOutPrecision(int outPrecision) {
        this.outPrecision = outPrecision;
    }

    public int getClipPrecision() {
        return clipPrecision;
    }

    public void setClipPrecision(int clipPrecision) {
        this.clipPrecision = clipPrecision;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getPitchAndFamily() {
        return pitchAndFamily;
    }

    public void setPitchAndFamily(int pitchAndFamily) {
        this.pitchAndFamily = pitchAndFamily;
    }

    public String getFaceName() {
        return faceName;
    }

    public void setFaceName(String faceName) {
        this.faceName = faceName;
    }

    @Override
    public Object clone() {
        Font font = new Font();
        font.setHeight(height);
        font.setWidth(width);
        font.setEscapement(escapement);
        font.setOrientation(orientation);
        font.setWeight(weight);
        font.setItalic(italic);
        font.setUnderline(underline);
        font.setStrikeOut(strikeOut);
        font.setCharSet(charSet);
        font.setOutPrecision(outPrecision);
        font.setClipPrecision(clipPrecision);
        font.setQuality(quality);
        font.setPitchAndFamily(pitchAndFamily);
        font.setFaceName(faceName);
        font.setDepth(depth);
        return font;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        height = in.readInt();
        width = in.readInt();
        escapement = in.readInt();
        orientation = in.readInt();
        weight = in.readInt();
        italic = in.readBoolean();
        underline = in.readBoolean();
        strikeOut = in.readBoolean();
        charSet = in.readInt();
        outPrecision = in.readInt();
        clipPrecision = in.readInt();
        quality = in.readInt();
        pitchAndFamily = in.readInt();
        faceName = StringUtils.load(in);
        depth = in.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(height);
        out.writeInt(width);
        out.writeInt(escapement);
        out.writeInt(orientation);
        out.writeInt(weight);
        out.writeBoolean(italic);
        out.writeBoolean(underline);
        out.writeBoolean(strikeOut);
        out.writeInt(charSet);
        out.writeInt(outPrecision);
        out.writeInt(clipPrecision);
        out.writeInt(quality);
        out.writeInt(pitchAndFamily);
        StringUtils.store(faceName, out);
        out.writeFloat(depth);
        return true;
    }
}
