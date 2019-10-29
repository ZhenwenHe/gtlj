package cn.edu.cug.cs.gtl.geom;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

class SimpleMaterial extends Material {

    private static final long serialVersionUID = 6870683726764375734L;
    private long textureID;
    private MaterialParameter materialParameter;

    /**
     * SimpleMaterial带参构造函数
     *
     * @param textureID：材质ID
     * @param materialParameter：材质参数
     */
    public SimpleMaterial(long textureID, MaterialParameter materialParameter) {
        this(-1L, "", textureID, materialParameter);
    }

    public SimpleMaterial(long materialID, String materialName, long textureID, MaterialParameter materialParameter) {
        super(SIMPLE_MATERIAL, materialID, materialName);
        this.textureID = textureID;
        this.materialParameter = materialParameter;
    }

    /**
     * SimplwMaterial无参构造函数
     */
    public SimpleMaterial() {
        this(-1, new MaterialParameter());
    }

    /**
     * 获得材质ID
     *
     * @return
     */
    public long getTextureID() {
        return textureID;
    }

    /**
     * 设置材质ID
     *
     * @param textureID：材质ID
     */
    public void setTextureID(long textureID) {
        this.textureID = textureID;
    }

    /**
     * 获得材质参数
     *
     * @return
     */
    public MaterialParameter getMaterialParameter() {
        return materialParameter;
    }

    /**
     * 设置材质参数
     *
     * @param materialParameter：材质参数
     */
    public void setMaterialParameter(MaterialParameter materialParameter) {
        this.materialParameter = materialParameter;
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return:Material
     */
    @Override
    public Material clone() {
        SimpleMaterial sm = new SimpleMaterial(this.getMaterialID(),
                this.getMaterialName(),
                this.textureID, this.materialParameter.clone());
        return sm;
    }

    /**
     * 从存储对象中加载数据，填充本对象
     *
     * @param dataInput:表示可以读取的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean load(DataInput dataInput) throws IOException {
        super.load(dataInput);
        this.textureID = dataInput.readLong();
        int flag = dataInput.readInt();
        if (flag != 0) {
            if (this.materialParameter != null)
                this.materialParameter.load(dataInput);
            else {
                this.materialParameter = new MaterialParameter();
                this.materialParameter.load(dataInput);
            }
        }
        return true;
    }

    /**
     * 将本对象写入存储对象中，存储对象可能是内存、文件、管道等
     *
     * @param dataOutput:表示可以写入的存储对象，可能是内存、文件、管道等
     * @throws IOException
     * @return:true or false
     */
    @Override
    public boolean store(DataOutput dataOutput) throws IOException {
        super.store(dataOutput);
        dataOutput.writeLong(this.textureID);
        if (this.materialParameter == null) {
            dataOutput.writeInt(0);
        } else {

            dataOutput.writeInt(1);
            this.materialParameter.store(dataOutput);
        }
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
        long len = super.getByteArraySize() + 8 + 4;
        if (this.materialParameter != null)
            len += this.materialParameter.getByteArraySize();
        return len;
    }

}
