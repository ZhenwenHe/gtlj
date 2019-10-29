package cn.edu.cug.cs.gtl.feature;

import cn.edu.cug.cs.gtl.common.Status;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/*
#define STATUS_GV3D_SHOW			0x01	// 显示状态
#define STATUS_GV3D_HIDE			0xFE	// 隐藏状态
#define STATUS_GV3D_BUILD			0x02	// 重建状态
#define STATUS_GV3D_UNBUILD			0xFD	// 不重建状态
#define STATUS_GV3D_SELECT			0x04	// 选中状态
#define STATUS_GV3D_UNSELECT		0xFB	// 不选中状态
#define STATUS_GV3D_DELETE			0x08	// 删除状态
#define STATUS_GV3D_UNDELETE		0xF7	// 不删除状态
#define STATUS_GV3D_DOTCOLOR		0x10	// 点色状态
#define STATUS_GV3D_UNDOTCOLOR		0xEF	// 非点色状态
#define STATUS_GV3D_TEX				0x20	// 纹理状态
#define STATUS_GV3D_UNTEX			0xDF	// 非纹理状态
#define STATUS_GV3D_MEANNORMAL		0x40	// 多边形法线平均状态
#define STATUS_GV3D_UNMEANNORMAL    0xBF	// 多边形法线不平均状态
 */
public class FeatureStatus extends Status {
    public int getStatus() {
        return status;
    }

    private int status;

    public FeatureStatus(int s) {
        this.status = s;
    }

    public FeatureStatus() {
        this(0);
    }

    @Override
    public Status clone() {
        return new FeatureStatus(this.status);
    }

    @Override
    public boolean load(DataInput in) throws IOException {
        this.status = in.readInt();
        return true;
    }

    @Override
    public boolean store(DataOutput out) throws IOException {
        out.writeInt(this.status);
        return true;
    }

}
