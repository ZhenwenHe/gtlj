package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.DataContent;
import cn.edu.cug.cs.gtl.util.StringUtils;
import cn.edu.cug.cs.gtl.common.Identifier;
import cn.edu.cug.cs.gtl.common.Status;
import cn.edu.cug.cs.gtl.io.DataContent;
import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.util.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Material implements DataContent {
    public static final long serialVersionUID = 1L;

    /**
     * 0-简单材质，1-复杂材质
     */
    public static final int SIMPLE_MATERIAL = 0;
    public static final int COMPLEX_MATERIAL = 1;

    /**
     * 材质类型
     */
    protected int materialType;
    /**
     * 材质ID
     */
    protected long materialID;
    /**
     * 材质名称
     */
    protected String materialName;

    protected Status status;

    public Material() {
        this.materialType = SIMPLE_MATERIAL;
        this.materialID = -1;
        this.materialName = "";
        this.status = new Status();
    }

    public Material(int materialType, long materialID, String materialName) {
        this.materialType = materialType;
        this.materialID = materialID;
        this.materialName = materialName;
        this.status = new Status();
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public long getMaterialID() {
        return materialID;
    }

    public void setMaterialID(long materialID) {
        this.materialID = materialID;
    }

    public int getMaterialType() {
        return materialType;
    }

    @Override
    public Material clone() {
        try {
            Material m = (Material) super.clone();
            m.materialID = this.materialID;
            m.materialName = this.materialName;
            m.materialType = this.materialType;
            m.status = this.status.clone();
            return m;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        /** 材质ID */
        materialID = in.readLong();
        /** 材质名称 */
        materialName = StringUtils.load(in);
        /** 材质类型 */
        materialType = in.readInt();

        this.status.load(in);
        return true;

    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        /** 材质ID */
        out.writeLong(materialID);
        /** 材质名称 */
        StringUtils.store(materialName, out);
        ;
        /** 材质类型 */
        out.writeInt(materialType);

        this.status.store(out);
        return true;
    }

    @Override
    public long getByteArraySize() {
        long len = 0;
        len = 8 + StringUtils.getByteArraySize(materialName) + 4 + this.status.getByteArraySize();
        return len;
    }

    public static Material create(int type) {
        if (type == 0)
            return new SimpleMaterial();
        else
            return new ComplexMaterial();
    }

    public static Material createMaterial(int type) {
        return create(type);
    }

    public static Material loadMaterial(DataInput in) throws IOException {
        int matType = in.readInt();
        Material m = create(matType);
        m.load(in);
        return m;
    }

    public static boolean storeMaterial(DataOutput out, Material m) throws IOException {
        int matType = m.getMaterialType();
        out.writeInt(matType);
        m.store(out);
        return true;
    }

    @Override
    public Identifier getIdentifier() {
        return Identifier.create(this.materialID);
    }

    @Override
    public String getName() {
        return getMaterialName();
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
