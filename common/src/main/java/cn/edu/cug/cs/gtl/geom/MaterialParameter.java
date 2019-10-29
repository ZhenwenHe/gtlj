package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.common.tuple.Tuple2;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MaterialParameter implements Serializable {
    // flag :
    public static final int TEXTURE_FLAG_TEXTURE = 1;//纹理有效
    public static final int TEXTURE_FLAG_MATERIAL = 2;//材质有效
    public static final int TEXTURE_FLAG_COLOR = 4;//颜色有效

    // transparent:
    public static final int TEXTURE_TRANSPARENT_DISABLE = 0;//不透明
    public static final int TEXTURE_TRANSPARENT_ENABLE = 1; //透明，将和背景做混合（如玻璃）, 参数 alpha
    public static final int TEXTURE_TRANSPARENT_SPECIAL = 2; //过滤指定部分（如画树滤掉纹理中非树的部分）

    //cullface :
    public static final int TEXTURE_CULLFACE_BACK = 0;//缺省，背面剔除
    public static final int TEXTURE_CULLFACE_DISABLE = 1;//不剔除(双面可视)
    public static final int TEXTURE_CULLFACE_FRONT = 2;//正面剔除

    //depth:(深度检测)
    public static final int TEXTURE_DEPTH_ENABLE = 0;//缺省，使用缺省深度检测方式
    public static final int TEXTURE_DEPTH_DISABLE = 1;//不启用深度检测
    public static final int TEXTURE_DEPTH_ALWAYS = 2;//深度比较总是通过
    public static final int TEXTURE_DEPTH_WRITEDISABLE = 4;//深度缓冲只读


    /**
     * 标志位(纹理、材质、颜色)
     */
    int flag;
    /**
     * 透明的方式
     * -TEXTURE_TRANSPARENT_DISABLE
     * -TEXTURE_TRANSPARENT_ENABLE
     * -TEXTURE_TRANSPARENT_SPECIAL
     */
    int transparent;
    /**
     * 面剔除方式
     * -TEXTURE_CULLFACE_BACK
     * -TEXTURE_CULLFACE_DISABLE
     * -TEXTURE_CULLFACE_FRONT
     */
    int cullface;

    /**
     * 深度检测
     * TEXTURE_DEPTH_ENABLE		=0;//缺省，使用缺省深度检测方式
     * TEXTURE_DEPTH_DISABLE		=1;//不启用深度检测
     * TEXTURE_DEPTH_ALWAYS		=2;//深度比较总是通过
     * TEXTURE_DEPTH_WRITEDISABLE	=4;//深度缓冲只读
     */
    int depth;

    /**
     * 保留
     */
    int[] reserve;

    /**
     * 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ]
     */
    float alpha;

    /**
     * 环境光
     */
    long ambient;

    /**
     * 漫反射
     */
    long diffuse;

    /**
     * 镜面反射
     */
    long specular;

    /**
     * 颜色
     */
    long color;

    /**
     * 纹理平移参数
     */
    float uOffset, vOffset;

    /**
     * 纹理旋转参数（单位为度，旋转轴为 (0, 0, 1)）
     */
    float rotAngle;

    /**
     * 高光
     */
    float shininess;
    /**
     * 光照模型
     */
    float illumination;

    public MaterialParameter() {
        this.reserve = new int[4];
    }

    public MaterialParameter(int flag, int transparent, int cullface,
                             int depth, float alpha, long ambient, long diffuse,
                             long specular, long color, float uOffset, float vOffset,
                             float rotAngle, float shininess, float illumination) {
        this.flag = flag;
        this.transparent = transparent;
        this.cullface = cullface;
        this.depth = depth;
        this.alpha = alpha;
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.color = color;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
        this.rotAngle = rotAngle;
        this.shininess = shininess;
        this.illumination = illumination;
        this.reserve = new int[4];
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getTransparent() {
        return transparent;
    }

    public void setTransparent(int transparent) {
        this.transparent = transparent;
    }

    public int getCullface() {
        return cullface;
    }

    public void setCullface(int cullface) {
        this.cullface = cullface;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public long getAmbient() {
        return ambient;
    }

    public void setAmbient(long ambient) {
        this.ambient = ambient;
    }

    public long getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(long diffuse) {
        this.diffuse = diffuse;
    }

    public long getSpecular() {
        return specular;
    }

    public void setSpecular(long specular) {
        this.specular = specular;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public Tuple2<Float, Float> getOffset() {
        return new Tuple2<Float, Float>(uOffset, vOffset);
    }

    public void setOffset(float u, float v) {
        this.uOffset = u;
        this.vOffset = v;
    }


    public float getUOffset() {
        return uOffset;
    }

    public float getVOffset() {
        return vOffset;
    }

    public void setUOffset(float u) {
        this.uOffset = u;
    }

    public void setVOffset(float v) {
        this.vOffset = v;
    }

    public float getRotAngle() {
        return rotAngle;
    }

    public void setRotAngle(float rotAngle) {
        this.rotAngle = rotAngle;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public float getIllumination() {
        return illumination;
    }

    public void setIllumination(float illumination) {
        this.illumination = illumination;
    }

    @Override
    public MaterialParameter clone() {
        MaterialParameter p = new MaterialParameter(this.flag, this.transparent, this.cullface,
                this.depth, this.alpha, this.ambient, this.diffuse,
                this.specular, this.color, this.uOffset, this.vOffset,
                this.rotAngle, this.shininess, this.illumination);
        return p;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        /** 标志位(纹理、材质、颜色) */
        flag = in.readInt();
        /** 透明的方式
         * -TEXTURE_TRANSPARENT_DISABLE
         * -TEXTURE_TRANSPARENT_ENABLE
         * -TEXTURE_TRANSPARENT_SPECIAL
         */
        transparent = in.readInt();
        /** 面剔除方式
         * -TEXTURE_CULLFACE_BACK
         * -TEXTURE_CULLFACE_DISABLE
         * -TEXTURE_CULLFACE_FRONT
         */
        cullface = in.readInt();

        /** 深度检测
         * TEXTURE_DEPTH_ENABLE		=0;//缺省，使用缺省深度检测方式
         * TEXTURE_DEPTH_DISABLE		=1;//不启用深度检测
         * TEXTURE_DEPTH_ALWAYS		=2;//深度比较总是通过
         *TEXTURE_DEPTH_WRITEDISABLE	=4;//深度缓冲只读
         */
        depth = in.readInt();

        /** 保留*/
        int s = in.readInt();
        if (s > 0) {
            this.reserve = new int[s];
            for (int i = 0; i < s; ++i) {
                this.reserve[i] = in.readInt();
            }
        }


        /** 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ] */
        alpha = in.readFloat();

        /** 环境光 */
        ambient = in.readLong();

        /** 漫反射 */
        diffuse = in.readLong();

        /** 镜面反射 */
        specular = in.readLong();

        /** 颜色 */
        color = in.readLong();

        /** 纹理平移参数 */
        uOffset = in.readFloat();
        vOffset = in.readFloat();

        /** 纹理旋转参数（单位为度，旋转轴为 (0, 0, 1)） */
        rotAngle = in.readFloat();

        /** 高光*/
        shininess = in.readFloat();
        /** 光照模型 */
        illumination = in.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        /** 标志位(纹理、材质、颜色) */
        out.writeInt(flag);

        /** 透明的方式
         * -TEXTURE_TRANSPARENT_DISABLE
         * -TEXTURE_TRANSPARENT_ENABLE
         * -TEXTURE_TRANSPARENT_SPECIAL
         */
        out.writeInt(transparent);

        /** 面剔除方式
         * -TEXTURE_CULLFACE_BACK
         * -TEXTURE_CULLFACE_DISABLE
         * -TEXTURE_CULLFACE_FRONT
         */
        out.writeInt(cullface);

        /** 深度检测
         * TEXTURE_DEPTH_ENABLE		=0;//缺省，使用缺省深度检测方式
         * TEXTURE_DEPTH_DISABLE		=1;//不启用深度检测
         * TEXTURE_DEPTH_ALWAYS		=2;//深度比较总是通过
         *TEXTURE_DEPTH_WRITEDISABLE	=4;//深度缓冲只读
         */
        out.writeInt(depth);

        /** 保留*/
        int s = 0;
        if (reserve != null)
            s = reserve.length;
        out.writeInt(s);
        if (s > 0) {
            for (int v : reserve) {
                out.writeInt(v);
            }
        }


        /** 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ] */
        out.writeFloat(alpha);

        /** 环境光 */
        out.writeLong(ambient);

        /** 漫反射 */
        out.writeLong(diffuse);

        /** 镜面反射 */
        out.writeLong(specular);


        /** 颜色 */
        out.writeLong(color);


        /** 纹理平移参数 */
        out.writeFloat(uOffset);
        out.writeFloat(vOffset);

        /** 纹理旋转参数（单位为度，旋转轴为 (0, 0, 1)） */
        out.writeFloat(rotAngle);


        /** 高光*/
        out.writeFloat(shininess);

        /** 光照模型 */
        out.writeFloat(illumination);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 4 * 4 //整数
                + 4 + reserve.length * 4 //保留数组
                + 6 * 4 //浮点数
                + 4 * 8; //长整型数
        return len;
    }


}
