package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class Color extends Color4f {
    private static final long serialVersionUID = 1L;


    public Color(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public Color(float r, float g, float b) {
        super(r, g, b);
    }

    public Color(byte r, byte g, byte b, byte a) {
        super(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    public Color(byte r, byte g, byte b) {
        super(r / 255.0f, g / 255.0f, b / 255.0f);
    }

    public Color(final Color c) {
        this(c.r, c.g, c.b, c.a);
    }

    public Color(int color) {
        Color4f c4f = convertToColor4f(color);
        this.r = c4f.r;
        this.g = c4f.g;
        this.b = c4f.b;
        this.a = c4f.a;
    }

    public Color(long color) {
        this((int) color);
    }

    public Color() {
    }

    @Override
    public Color clone() {
        return new Color(this);
    }

    public static Color4b convertToColor4b(int c) {
        Color4b c4b = new Color4b();
        c4b.a = (byte) ((c >> 24) & 0xff);
        c4b.b = (byte) ((c >> 16) & 0xff);
        c4b.g = (byte) ((c >> 8) & 0xff);
        c4b.r = (byte) (c & 0xff);
        return c4b;
    }

    public static Color4f convertToColor4f(Color4b c4b) {
        Color4f c4f = new Color4f();
        c4f.r = c4b.r / 255.0f;
        c4f.g = c4b.g / 255.0f;
        c4f.b = c4b.b / 255.0f;
        c4f.a = c4b.a / 255.0f;
        return c4f;
    }

    public static Color4f convertToColor4f(int c) {
        Color4f c4f = new Color4f();
        c4f.a = (byte) ((c >> 24) & 0xff) / 255.0f;
        c4f.b = (byte) ((c >> 16) & 0xff) / 255.0f;
        c4f.g = (byte) ((c >> 8) & 0xff) / 255.0f;
        c4f.r = (byte) (c & 0xff) / 255.0f;
        return c4f;
    }

    public static Color3b convertToColor3b(int c) {
        Color3b c3b = new Color3b();
        c3b.b = (byte) ((c >> 16) & 0xff);
        c3b.g = (byte) ((c >> 8) & 0xff);
        c3b.r = (byte) (c & 0xff);
        return c3b;
    }

    public static Color4b convertToColor4b(final Color4f c4f) {
        Color4b c4b = new Color4b();
        c4b.r = (byte) (c4f.r * 255);
        c4b.g = (byte) (c4f.g * 255);
        c4b.b = (byte) (c4f.b * 255);
        c4b.a = (byte) (c4f.a * 255);
        return c4b;
    }

    public static int convert(final Color4f c4f) {
        int c = 0;
        Color4b c4b = convertToColor4b(c4f);
        c = c4b.r | (((short) (c4b.g)) << 8) | (((int) (c4b.b)) << 16);
        return c;
    }

    /**
     * R = 255*(100-C)*(100-K)/10000
     * G = 255*(100-M)*(100-K)/10000
     * B = 255*(100-Y)*(100-K)/10000
     *
     * @param cmyk
     * @return
     */
    public static RGB convertToRGB(final CMYK cmyk) {
        RGB rgb = new RGB();
        rgb.R = (byte) (255 * (100 - cmyk.C) * (100 - cmyk.K) / 10000);
        rgb.G = (byte) (255 * (100 - cmyk.M) * (100 - cmyk.K) / 10000);
        rgb.B = (byte) (255 * (100 - cmyk.Y) * (100 - cmyk.K) / 10000);
        return rgb;
    }

    public static CMYK convertToCMYK(final RGB rgb, final byte K) {
        CMYK cmyk = new CMYK();
        cmyk.C = (byte) (100 - (rgb.R * 10000.0 / 255 / (100 - K)));
        cmyk.M = (byte) (100 - (rgb.G * 10000.0 / 255 / (100 - K)));
        cmyk.Y = (byte) (100 - (rgb.B * 10000.0 / 255 / (100 - K)));
        cmyk.K = K;
        return cmyk;
    }

    public static Color convertToColor(final CMYK cmyk) {
        return new Color(
                (float) ((100 - cmyk.C) * (100 - cmyk.K) / 10000.0f),
                (float) ((100 - cmyk.M) * (100 - cmyk.K) / 10000.0f),
                (float) ((100 - cmyk.Y) * (100 - cmyk.K) / 10000.0f),
                0.0f);
    }

    public static Color create(float r, float g, float b, float a) {
        return new Color(r, g, b, a);
    }

    public static Color create(float r, float g, float b) {
        return new Color(r, g, b);
    }

    public static Color create(byte r, byte g, byte b, byte a) {
        return new Color(r, g, b, a);
    }

    public static Color create(byte r, byte g, byte b) {
        return new Color(r, g, b);
    }


    /**
     * 自然界中绝大部分的可见光谱可以用红、绿和蓝三色光按不同比例和强度的混合来表示。
     * RGB分别代表着3种颜色：R代表红色，G代表绿色、B代表蓝色。
     * RGB模型也称为加色模型，通常用于光照、视频和屏幕图像编辑。
     * RGB色彩模式使用RGB模型为图像中每一个像素的RGB分量分配一个0~255范围内的强度值。
     */
    public static class RGB {
        public byte R;
        public byte G;
        public byte B;
    }

    ;

    /**
     * CMYK色彩模式以打印油墨在纸张上的光线吸收特性为基础，
     * 图像中每个像素都是由靛青（C）、品红（M）、黄（Y）和黑（K）色按照不同的比例合成。
     * 每个像素的每种印刷油墨会被分配一个百分比值，
     * 最亮（高光）的颜色分配较低的印刷油墨颜色百分比值，较暗（暗调）的颜色分配较高的百分比值。
     */
    public static class CMYK {
        public byte C;
        public byte M;
        public byte Y;
        public byte K;
    }

    ;

}
