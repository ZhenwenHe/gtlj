package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MaterialItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 材质类型
     */
    long materialType;
    /**
     * 纹理ID
     */
    long textureID;
    /**
     * 强度
     */
    float intensity;
    /**
     * 颜色
     */
    long color;
    /**
     * 在 u 纹理方向上重复的次数
     */
    double uRepeat;
    /**
     * 在 v 纹理方向上重复的次数
     */
    double vRepeat;
    /**
     * 纹理平移
     */
    float uOffset;
    /**
     * 纹理平移
     */
    float vOffset;
    /**
     * 纹理旋转
     */
    float rotAngle;
    /**
     * 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ]
     */
    float alpha;

    /**
     * MaterialItem有参构造函数
     *
     * @param matType：材质类型
     * @param textureID：纹理ID
     * @param intensity：强度
     * @param color：颜色
     * @param uRepeat：在      u 纹理方向上重复的次数
     * @param vRepeat：       在 v 纹理方向上重复的次数
     * @param uOffset：在      u 纹理方向平移
     * @param vOffset：在      v 纹理方向平移
     * @param rotAngle：纹理旋转
     * @param alpha：透明度
     */
    public MaterialItem(long matType, long textureID, float intensity, long color, double uRepeat, double vRepeat, float uOffset, float vOffset, float rotAngle, float alpha) {
        this.materialType = matType;
        this.textureID = textureID;
        this.intensity = intensity;
        this.color = color;
        this.uRepeat = uRepeat;
        this.vRepeat = vRepeat;
        this.uOffset = uOffset;
        this.vOffset = vOffset;
        this.rotAngle = rotAngle;
        this.alpha = alpha;
    }

    /**
     * MaterialItem无参构造函数
     */
    public MaterialItem() {
    }

    /**
     * 获得材质类型
     *
     * @return
     */
    public long getMaterialType() {
        return materialType;
    }

    /**
     * 设置材质类型
     *
     * @param materialType：材质类型
     */
    public void setMaterialType(long materialType) {
        this.materialType = materialType;
    }

    /**
     * 获得纹理ID
     *
     * @return：纹理ID
     */
    public long getTextureID() {
        return textureID;
    }

    /**
     * 设置纹理ID
     *
     * @param textureID：纹理ID
     */
    public void setTextureID(long textureID) {
        this.textureID = textureID;
    }

    /**
     * 获得强度
     *
     * @return：纹理ID
     */
    public float getIntensity() {
        return intensity;
    }

    /**
     * 设置强度
     *
     * @param intensity：强度
     */
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    /**
     * 获得颜色
     *
     * @return：颜色
     */
    public long getColor() {
        return color;
    }

    /**
     * 设置颜色
     *
     * @param color：颜色
     */
    public void setColor(long color) {
        this.color = color;
    }

    /**
     * 获得在 u 纹理方向上重复的次数
     *
     * @return：在 u 纹理方向上重复的次数
     */
    public double getURepeat() {
        return uRepeat;
    }

    /**
     * 设置在 u 纹理方向上重复的次数
     *
     * @param uRepeat：在 u 纹理方向上重复的次数
     */
    public void setURepeat(double uRepeat) {
        this.uRepeat = uRepeat;
    }

    /**
     * 获得在 v 纹理方向上重复的次数
     *
     * @return：在 v 纹理方向上重复的次数
     */
    public double getVRepeat() {
        return vRepeat;
    }

    /**
     * 设置在 v 纹理方向上重复的次数
     *
     * @param vRepeat：在 v 纹理方向上重复的次数
     */
    public void setVRepeat(double vRepeat) {
        this.vRepeat = vRepeat;
    }

    /**
     * 获得在 u 纹理方向平移
     *
     * @return：在 u 纹理方向平移
     */
    public float getUOffset() {
        return uOffset;
    }

    /**
     * 设置在 u 纹理方向平移
     *
     * @param uOffset：在 u 纹理方向平移
     */
    public void setUOffset(float uOffset) {
        this.uOffset = uOffset;
    }

    /**
     * 获得在 v 纹理方向平移
     *
     * @return：在 v 纹理方向平移
     */
    public float getVOffset() {
        return vOffset;
    }

    /**
     * 设置在 v 纹理方向平移
     *
     * @return：在 v 纹理方向平移
     */
    public void setVOffset(float vOffset) {
        this.vOffset = vOffset;
    }

    /**
     * 获得纹理旋转
     *
     * @return：纹理旋转
     */
    public float getRotAngle() {
        return rotAngle;
    }

    /**
     * 设置纹理旋转
     *
     * @return：纹理旋转
     */
    public void setRotAngle(float rotAngle) {
        this.rotAngle = rotAngle;
    }

    /**
     * 获得透明度
     *
     * @return：透明度
     */
    public float getAlpha() {
        return alpha;
    }

    /**
     * 设置透明度
     *
     * @param alpha：透明度
     */
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return:MaterialItem
     */
    @Override
    public MaterialItem clone() {
        MaterialItem p = new MaterialItem(this.materialType, this.textureID, this.intensity, this.color, this.uRepeat, this.vRepeat, this.uOffset, this.vOffset, this.rotAngle, this.alpha);
        return p;
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param in:表示可以读取的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean load(DataInput in) throws IOException {
        /** 材质类型 */
        materialType = in.readLong();
        /** 纹理ID */
        textureID = in.readLong();
        /** 强度 */
        intensity = in.readFloat();
        /** 颜色 */
        color = in.readLong();
        /** 在 u 纹理方向上重复的次数 */
        uRepeat = in.readDouble();
        /** 在 v 纹理方向上重复的次数 */
        vRepeat = in.readDouble();
        /** 纹理平移 */
        uOffset = in.readFloat();
        /** 纹理平移 */
        vOffset = in.readFloat();
        /** 纹理旋转 */
        rotAngle = in.readFloat();
        /** 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ] */
        alpha = in.readFloat();

        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param out:表示可以写入的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean store(DataOutput out) throws IOException {
        /** 材质类型 */
        out.writeLong(materialType);
        /** 纹理ID */
        out.writeLong(textureID);
        /** 强度 */
        out.writeFloat(intensity);
        /** 颜色 */
        out.writeLong(color);
        /** 在 u 纹理方向上重复的次数 */
        out.writeDouble(uRepeat);
        /** 在 v 纹理方向上重复的次数 */
        out.writeDouble(vRepeat);
        /** 纹理平移 */
        out.writeFloat(uOffset);
        /** 纹理平移 */
        out.writeFloat(vOffset);
        /** 纹理旋转 */
        out.writeFloat(rotAngle);
        /** 透明度  [ 0.0 (不透明)  ~  1.0 (完全透明) ] */
        out.writeFloat(alpha);

        return true;
    }

    /**
     * 对象序列化后的字节数，
     * 默认实现为将其写入一个字节数组中，然后返回该字节数
     *
     * @return:字节数
     */
    @Override
    public long getByteArraySize() {
        long len = 0;
        len = 8 + 8 + 4 + 8 + 8 + 8 + 4 + 4 + 4 + 4;
        return len;
    }
}
