package cn.edu.cug.cs.gtl.geom;

import cn.edu.cug.cs.gtl.io.Serializable;
import cn.edu.cug.cs.gtl.io.Serializable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TextureParameter implements Serializable {
    private static final long serialVersionUID = 1L;

    private int nImageIndex;
    private Byte byFace;
    private Byte byDir;
    private Byte byTran;
    private Byte byAuto;
    private Byte byHint;
    private float fS_Coeff;
    private float fT_Coeff;
    private float fS_Offset;
    private float fT_Offset;
    private float fClampS;
    private float fClampT;
    private float fMode;
    private float fMagFilter;
    private float fMinFilter;

    public TextureParameter() {
    }

    public TextureParameter(int nImageIndex, Byte byFace, Byte byDir, Byte byTran, Byte byAuto, Byte byHint, float fS_Coeff, float fT_Coeff, float fS_Offset,
                            float fT_Offset, float fClampS, float fClampT, float fMode, float fMagFilter, float fMinFilter) {
        this.nImageIndex = nImageIndex;
        this.byFace = byFace;
        this.byDir = byDir;
        this.byTran = byTran;
        this.byAuto = byAuto;
        this.byHint = byHint;
        this.fS_Coeff = fS_Coeff;
        this.fT_Coeff = fT_Coeff;
        this.fS_Offset = fS_Offset;
        this.fT_Offset = fT_Offset;
        this.fClampS = fClampS;
        this.fClampT = fClampT;
        this.fMode = fMode;
        this.fMagFilter = fMagFilter;
        this.fMinFilter = fMinFilter;
    }

    @Override
    public Object clone() {
        return new TextureParameter(nImageIndex, byFace, byDir, byTran, byAuto, byHint, fS_Coeff, fT_Coeff,
                fS_Offset, fT_Offset, fClampS, fClampT, fMode, fMagFilter, fMinFilter);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        nImageIndex = in.readInt();
        byFace = in.readByte();
        byDir = in.readByte();
        byTran = in.readByte();
        byAuto = in.readByte();
        byHint = in.readByte();
        fS_Coeff = in.readFloat();
        fT_Coeff = in.readFloat();
        fS_Offset = in.readFloat();
        fT_Offset = in.readFloat();
        fClampS = in.readFloat();
        fClampT = in.readFloat();
        fMode = in.readFloat();
        fMagFilter = in.readFloat();
        fMinFilter = in.readFloat();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(nImageIndex);
        out.writeByte(byFace);
        out.writeByte(byDir);
        out.writeByte(byTran);
        out.writeByte(byAuto);
        out.writeByte(byHint);
        out.writeFloat(fS_Coeff);
        out.writeFloat(fT_Coeff);
        out.writeFloat(fS_Offset);
        out.writeFloat(fT_Offset);
        out.writeFloat(fClampS);
        out.writeFloat(fClampT);
        out.writeFloat(fMode);
        out.writeFloat(fMagFilter);
        out.writeFloat(fMinFilter);
        return true;
    }

    @Override
    public long getByteArraySize() {
        return 0;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getnImageIndex() {
        return nImageIndex;
    }

    public Byte getByFace() {
        return byFace;
    }

    public Byte getByDir() {
        return byDir;
    }

    public Byte getByTran() {
        return byTran;
    }

    public Byte getByAuto() {
        return byAuto;
    }

    public Byte getByHint() {
        return byHint;
    }

    public float getfS_Coeff() {
        return fS_Coeff;
    }

    public float getfT_Coeff() {
        return fT_Coeff;
    }

    public float getfS_Offset() {
        return fS_Offset;
    }

    public float getfT_Offset() {
        return fT_Offset;
    }

    public float getfClampS() {
        return fClampS;
    }

    public float getfClampT() {
        return fClampT;
    }

    public float getfMode() {
        return fMode;
    }

    public float getfMagFilter() {
        return fMagFilter;
    }

    public float getfMinFilter() {
        return fMinFilter;
    }
}
