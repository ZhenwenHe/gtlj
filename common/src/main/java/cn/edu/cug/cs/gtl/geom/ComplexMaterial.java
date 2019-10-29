package cn.edu.cug.cs.gtl.geom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ComplexMaterial extends Material {
    private static final long serialVersionUID = 5424505437902414124L;
    private List<MaterialItem> materialItems;

    /**
     * ComplexMaterial有参构造函数
     *
     * @param materialID：材质ID
     * @param materialName：材质名称
     * @param materialItems：材质属性
     */
    public ComplexMaterial(long materialID, String materialName, List<MaterialItem> materialItems) {
        super(COMPLEX_MATERIAL, materialID, materialName);
        this.materialItems = materialItems;
    }

    /**
     * ComplexMaterial无参构造函数
     */
    public ComplexMaterial() {
        this(-1L, "", new ArrayList<>());
    }

    /**
     * 获得材质的属性
     *
     * @return：材质属性
     */
    public List<MaterialItem> getMaterialItems() {
        return Collections.unmodifiableList(this.materialItems);
    }

    /**
     * 设置材质属性
     *
     * @param materialItems：材质属性
     */
    public void setMaterialItems(List<MaterialItem> materialItems) {
        this.materialItems.clear();
        this.materialItems.addAll(materialItems);
    }

    /**
     * 拷贝本对象内容到其他同类对象
     *
     * @return:ComplexMaterial
     */
    @Override
    public ComplexMaterial clone() {
        ComplexMaterial m = (ComplexMaterial) super.clone();
        m.materialItems = new ArrayList<>(this.materialItems.size());
        for (MaterialItem mi : this.materialItems) {
            m.materialItems.add(mi.clone());
        }
        return m;
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
        super.load(in);
        int c = in.readInt();
        if (c > 0) {
            this.materialItems = new ArrayList<>(c);
            for (int i = 0; i < c; ++i) {
                MaterialItem mi = new MaterialItem();
                mi.load(in);
                this.materialItems.add(mi);
            }
        }
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
        super.store(out);
        //List<MaterialItem> materialItems;
        int c = this.materialItems.size();
        out.writeInt(c);
        if (c > 0) {
            for (MaterialItem i : this.materialItems) {
                i.store(out);
            }
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
        long len = super.getByteArraySize() + 4;
        for (MaterialItem mi : this.materialItems)
            len += mi.getByteArraySize();
        return len;
    }
}
